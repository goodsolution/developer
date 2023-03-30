package pl.com.mike.developer.logic.courseplatform;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.courseplatform.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LessonCommentsService {

    private LessonCommentsJdbcRepository lessonCommentsJdbcRepository;
    private LessonsService lessonsService;
    private CourseCustomersService courseCustomersService;
    private CoursesService coursesService;
    private CustomerAuthoritiesService customerAuthoritiesService;

    public LessonCommentsService(LessonCommentsJdbcRepository lessonCommentsJdbcRepository, LessonsService lessonsService, CourseCustomersService courseCustomersService, CoursesService coursesService, CustomerAuthoritiesService customerAuthoritiesService) {
        this.lessonCommentsJdbcRepository = lessonCommentsJdbcRepository;
        this.lessonsService = lessonsService;
        this.courseCustomersService = courseCustomersService;
        this.coursesService = coursesService;
        this.customerAuthoritiesService = customerAuthoritiesService;
    }

    public void create(String text, Long lessonId) {
        CustomerData customer = getCustomerAndValidate();
        LessonData lesson = getLessonAndValidate(lessonId);
        validateIfCustomerOwnLesson(customer, lesson);
        validateText(text);

        LessonCommentData lessonComment = new LessonCommentData(lesson, customer, text, LessonCommentStatus.VISIBLE.getValue(), LocalDateTime.now(), null);
        lessonCommentsJdbcRepository.create(lessonComment);
    }

    public List<LessonCommentData> findForLessonWatchPage(Long lessonId) {
        CustomerData customer = getCustomerAndValidate();
        LessonData lesson = getLessonAndValidate(lessonId);
        validateIfCustomerOwnLesson(customer, lesson);

        LessonCommentsFilter filer = new LessonCommentsFilter(lesson.getId(), false, LessonCommentStatus.VISIBLE);
        return lessonCommentsJdbcRepository.find(filer);
    }

    public List<LessonCommentData> findForPanelAdmin(Long page, Long pageSize) {

        CustomerData loggedCustomer = courseCustomersService.getLoggedCustomer();

        if(customerAuthoritiesService.hasCustomerAuthority(loggedCustomer, CustomerAuthority.ADMIN)) {
            return lessonCommentsJdbcRepository.find(new LessonCommentsFilter(false, page, pageSize));
        } else if (customerAuthoritiesService.hasCustomerAuthority(loggedCustomer, CustomerAuthority.TEACHER)) {
            return lessonCommentsJdbcRepository.find(new LessonCommentsFilter(false, page, pageSize, loggedCustomer.getAuthor().getId()));
        } else {
            throw new IllegalArgumentException("You don't have permissions to this endpoint");
        }
    }

    public void update(Long id, String status) {
        validateStatus(status);
        LessonCommentData data = lessonCommentsJdbcRepository.find(new LessonCommentsFilter(id)).get(0);
        LessonCommentData updatedData = new LessonCommentData(data, status);
        lessonCommentsJdbcRepository.update(updatedData);
    }

    public void delete(Long id) {
        LessonCommentData data = lessonCommentsJdbcRepository.find(new LessonCommentsFilter(id)).get(0);
        LessonCommentData updatedData = new LessonCommentData(data, LocalDateTime.now());
        lessonCommentsJdbcRepository.update(updatedData);
    }

    private CustomerData getCustomerAndValidate() {
        CustomerData customer = courseCustomersService.getLoggedCustomer();

        if(customer == null) {
            throw new IllegalArgumentException("You must be logged");
        }

        return customer;
    }

    private LessonData getLessonAndValidate(Long lessonId) {
        List<LessonData> lessons = lessonsService.find(new LessonsFilter(lessonId));

        if(lessons.size() == 0) {
            throw new IllegalArgumentException("Incorrect lesson ID: " + lessonId);
        }

        return lessons.get(0);
    }

    private void validateIfCustomerOwnLesson(CustomerData customer, LessonData lesson) {
        if(!coursesService.hasCustomerCourse(customer, lesson.getCourse())) {
            throw new IllegalArgumentException("You don't own course this lesson is in");
        }
    }

    private void validateText(String text) {
        if(text == null || text.equals("")) {
            throw new IllegalArgumentException("Comment can't be empty!");
        } else if (text.length() > 5000) {
            throw new IllegalArgumentException("Comment too long, max 5000 characters");
        }
    }

    private void validateStatus(String status) {
        if(status == null || status.equals("")) {
            throw new IllegalArgumentException("Status can't be empty");
        } else if (status.length() != 1) {
            throw new IllegalArgumentException("Status must have only one character");
        } else if (!status.equals(LessonCommentStatus.INVISIBLE.getValue()) &&
                !status.equals(LessonCommentStatus.VISIBLE.getValue())) {
            throw new IllegalArgumentException("Status incorrect");
        }
    }
}
