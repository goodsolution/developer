package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.courseplatform.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourseCommentsService {

    private CoursesService coursesService;
    private CourseCustomersService courseCustomersService;
    private CourseCommentsJdbcRepository courseCommentsJdbcRepository;
    private CustomerAuthoritiesService customerAuthoritiesService;

    public CourseCommentsService(CoursesService coursesService, CourseCustomersService courseCustomersService, CourseCommentsJdbcRepository courseCommentsJdbcRepository, CustomerAuthoritiesService customerAuthoritiesService) {
        this.coursesService = coursesService;
        this.courseCustomersService = courseCustomersService;
        this.courseCommentsJdbcRepository = courseCommentsJdbcRepository;
        this.customerAuthoritiesService = customerAuthoritiesService;
    }

    public void createByLoggedCustomer(String text, Long courseId) {
        validateText(text);
        CourseData course = getCourseAndValidate(courseId);
        CustomerData customer = getLoggedCustomerAndValidate();
        validateIfCustomerOwnCourse(customer, course);
        CourseCommentData courseComment = new CourseCommentData(course, customer, text, CourseCommentVisibilityStatus.INVISIBLE.getValue(), LocalDateTime.now());
        courseCommentsJdbcRepository.create(courseComment);
    }

    public List<CourseCommentData> findForPanelAdmin(Long page, Long pageSize) {

        CustomerData loggedCustomer = courseCustomersService.getLoggedCustomer();

        if(customerAuthoritiesService.hasCustomerAuthority(loggedCustomer, CustomerAuthority.ADMIN)) {
            return courseCommentsJdbcRepository.find(new CourseCommentsFilter(false, page, pageSize));
        } else if (customerAuthoritiesService.hasCustomerAuthority(loggedCustomer, CustomerAuthority.TEACHER)) {
            return courseCommentsJdbcRepository.find(new CourseCommentsFilter(false, page, pageSize, loggedCustomer.getAuthor().getId()));
        } else {
            throw new IllegalArgumentException("You don't have permissions to this endpoint");
        }
    }

    public List<CourseCommentData> findForCoursePage(Long courseId) {
        return courseCommentsJdbcRepository.find(new CourseCommentsFilter(false, CourseCommentVisibilityStatus.VISIBLE, courseId));
    }

    public void update(Long id, String visibilityStatus) {
        validateStatus(visibilityStatus);
        CourseCommentData data = courseCommentsJdbcRepository.find(new CourseCommentsFilter(id)).get(0);
        CourseCommentData updatedData = new CourseCommentData(data, visibilityStatus);
        courseCommentsJdbcRepository.update(updatedData);
    }

    public void delete(Long id) {
        CourseCommentData data = courseCommentsJdbcRepository.find(new CourseCommentsFilter(id)).get(0);
        CourseCommentData updatedData = new CourseCommentData(data, LocalDateTime.now());
        courseCommentsJdbcRepository.update(updatedData);
    }

    private void validateText(String text) {
        if(text == null || text.equals("")) {
            throw new IllegalArgumentException("Opinion can't be empty");
        } else if (text.length() > 5000) {
            throw new IllegalArgumentException("Opinion too long, max 5000 characters");
        }
    }

    private CourseData getCourseAndValidate(Long courseId) {
        try {
            return  coursesService.find(new CoursesFilter(courseId)).get(0);
        } catch (IndexOutOfBoundsException ex) {
            throw new IllegalArgumentException("Incorrect course ID");
        }
    }

    private CustomerData getLoggedCustomerAndValidate() {

        CustomerData customer = courseCustomersService.getLoggedCustomer();

        if(customer == null) {
            throw new IllegalArgumentException("Your are logged out. Please log in and retry");
        }

        return customer;
    }

    private void validateIfCustomerOwnCourse(CustomerData customer, CourseData course) {
        if(!coursesService.hasCustomerCourse(customer, course)) {
            throw new IllegalArgumentException("You don't have this course!");
        }
    }

    private void validateStatus(String visibilityStatus) {
        if(visibilityStatus == null || visibilityStatus.equals("")) {
            throw new IllegalArgumentException("Visibility status can't be empty");
        } else if (visibilityStatus.length() != 1) {
            throw new IllegalArgumentException("Visibility status must have only one character");
        } else if (!visibilityStatus.equals(CourseCommentVisibilityStatus.INVISIBLE.getValue()) &&
                !visibilityStatus.equals(CourseCommentVisibilityStatus.VISIBLE.getValue())) {
            throw new IllegalArgumentException("Visibility status incorrect");
        }
    }

}
