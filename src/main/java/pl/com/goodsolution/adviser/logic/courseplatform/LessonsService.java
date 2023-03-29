package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.courseplatform.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LessonsService {

    private LessonsJdbcRepository lessonsJdbcRepository;
    private CustomerLessonDetailsService customerLessonDetailsService;
    private ModulesService modulesService;
    private CoursesService coursesService;
    private final static String DIRECTION_UP = "UP";
    private final static String DIRECTION_DOWN = "DOWN";

    public LessonsService(LessonsJdbcRepository lessonsJdbcRepository, CustomerLessonDetailsService customerLessonDetailsService, ModulesService modulesService, CoursesService coursesService) {
        this.lessonsJdbcRepository = lessonsJdbcRepository;
        this.customerLessonDetailsService = customerLessonDetailsService;
        this.modulesService = modulesService;
        this.coursesService = coursesService;
    }

    public void create(LessonData data) {

        ModuleData module;

        if(data.getModuleId() == null) {
            module = null;
        } else {
            module = modulesService.get(data.getModuleId());
        }

        CourseData course;

        if(data.getCourseId() == null) {
            throw new IllegalArgumentException("Lesson must be assigned to course");
        } else {
            course = coursesService.get(data.getCourseId());
        }

        String taskSolutionMovieLink;
        if(data.getTaskSolutionMovieLink() == null) {
            taskSolutionMovieLink = "";
        } else {
            taskSolutionMovieLink = data.getTaskSolutionMovieLink();
        }

        MovieLinkType movieLinkType;
        if(data.getMovieLinkType() == null) {
            movieLinkType = MovieLinkType.NO_MOVIE;
        } else {
            movieLinkType = data.getMovieLinkType();
        }

        LessonData lesson = new LessonData(course, data.getTitle(), data.getDescription(), data.getMovieLink(), LessonVisibilityStatus.INVISIBLE, module, LessonType.STANDARD, movieLinkType, MovieLinkType.NO_MOVIE, taskSolutionMovieLink);

        validate(lesson);

        lessonsJdbcRepository.create(lesson);
    }

    public LessonData get(Long id) {
        return lessonsJdbcRepository.get(id);
    }

    public LessonData getFirstVisibleLessonInCourse(Long courseId) {

        List<LessonData> lessonsWithoutModule = find(new LessonsFilter(courseId, LessonVisibilityStatus.VISIBLE, true, false));

        if(lessonsWithoutModule.size() > 0) {
            return lessonsWithoutModule.get(0);
        } else {
            List<ModuleData> modules = modulesService.find(new ModulesFilter(courseId, false, ModuleVisibilityStatus.VISIBLE));

            if(modules.size() > 0) {
                for (ModuleData module : modules) {
                    List<LessonData> lessons = find(new LessonsFilter(LessonVisibilityStatus.VISIBLE, module.getId(), false));
                    if(lessons.size() > 0) {
                        return lessons.get(0);
                    }
                }
            }
        }

        throw new IllegalArgumentException("This course don't have any visible lessons!");
    }

    public List<LessonData> find(LessonsFilter filter) {
        return lessonsJdbcRepository.find(filter);
    }

    public LessonData getWithCustomerActivity(Long lessonId, CustomerData customer) {
        return lessonsJdbcRepository.getWithCustomerActivity(lessonId, customer);
    }

    public List<LessonData> findWithCustomerActivity(Long courseId, CustomerData customer, Boolean withoutModule, Long moduleId) {

        List<LessonData> lessons;

        if(withoutModule != null && withoutModule) {
            lessons = find(new LessonsFilter(courseId, LessonVisibilityStatus.VISIBLE, true, false));
        } else if (moduleId != null) {
            lessons = find(new LessonsFilter(LessonVisibilityStatus.VISIBLE, moduleId, false));
        } else {
            throw new IllegalArgumentException("Can't find lessons with that filters");
        }

        List<LessonData> preparedLessons = new ArrayList<>();

        for (LessonData lesson : lessons) {
            List<CustomerLessonDetailsData> details = customerLessonDetailsService.find(new CustomerLessonDetailsFilter(lesson.getId(), customer.getId()));

            if(details.size() == 0) {
                customerLessonDetailsService.create(customer.getId(), lesson.getId());
                preparedLessons.add(new LessonData(lesson, false));
            } else {
                preparedLessons.add(new LessonData(lesson, details.get(0).getWatched()));
            }
        }

        return preparedLessons;
    }

    public void update(LessonData data) {
        validate(data);
        lessonsJdbcRepository.update(data);
    }

    public void duplicateLessons(Long fromCourseId, Long toCourseId) {
        List<LessonData> lessons = find(new LessonsFilter(fromCourseId, false));
        CourseData toCourse = coursesService.get(toCourseId);

        for (LessonData lesson : lessons) {
            validate(lesson);
            lessonsJdbcRepository.create(new LessonData(toCourse, lesson.getTitle(), lesson.getDescription(), lesson.getMovieLink(), lesson.getVisibilityStatus(), null, lesson.getType(), lesson.getTaskSolutionDescription(), lesson.getTaskSolutionMovieLink(), lesson.getMovieLinkType(), lesson.getTaskSolutionMovieLinkType()));
        }
    }

    public void updateFromPanelAdmin(LessonData updateRequest) {

        LessonData oldData = get(updateRequest.getId());

        ModuleData module;
        if(updateRequest.getModuleId() == null) {
            module = null;
        } else {
            module = modulesService.get(updateRequest.getModuleId());
        }

        if(oldData.getCourse().getType().equals(CourseType.COURSE.getCode())) {
            update(new LessonData(updateRequest.getId(), oldData.getCourse(), updateRequest.getTitle(), updateRequest.getDescription(),
                    updateRequest.getMovieLink(), oldData.getOrderNumber(), updateRequest.getVisibilityStatus(), module, oldData.getDeleteDatetime(),
                    updateRequest.getType(), updateRequest.getTaskSolutionDescription(), updateRequest.getTaskSolutionMovieLink(),
                    updateRequest.getMovieLinkType(), updateRequest.getTaskSolutionMovieLinkType()));
        } else {
            update(new LessonData(updateRequest.getId(), oldData.getCourse(), updateRequest.getTitle(), updateRequest.getDescription(),
                    updateRequest.getMovieLink(), oldData.getOrderNumber(), updateRequest.getVisibilityStatus(), module, oldData.getDeleteDatetime(),
                    oldData.getType(), oldData.getTaskSolutionDescription(), oldData.getTaskSolutionMovieLink(),
                    updateRequest.getMovieLinkType(), oldData.getTaskSolutionMovieLinkType()));
        }
    }

    public void delete(Long id) {
        LessonData lesson = get(id);
        update(new LessonData(lesson, LocalDateTime.now()));
    }

    public void deleteLessonsInModule(Long moduleId) {
        lessonsJdbcRepository.deleteLessonsInModule(moduleId, LocalDateTime.now());
    }

    public void changeOrder(Long id, String direction) {
        LessonData lesson = find(new LessonsFilter(id)).get(0);
        LessonData lessonToReplace;

        if(direction.equals(DIRECTION_UP)) {
            if(lesson.getModule() != null) {
                lessonToReplace = lessonsJdbcRepository.getFirstAboveInModule(lesson, lesson.getModule().getId());
            } else {
                lessonToReplace = lessonsJdbcRepository.getFirstAboveWithoutModule(lesson);
            }
        } else if (direction.equals(DIRECTION_DOWN)) {
            if(lesson.getModule() != null) {
                lessonToReplace = lessonsJdbcRepository.getFirstBelowInModule(lesson, lesson.getModule().getId());
            } else {
                lessonToReplace = lessonsJdbcRepository.getFirstBelowWithoutModule(lesson);
            }
        } else {
            throw new IllegalArgumentException("Incorrect direction");
        }

        Long lessonOrderNumber = lesson.getOrderNumber();

        update(new LessonData(lesson, lessonToReplace.getOrderNumber()));
        update(new LessonData(lessonToReplace, lessonOrderNumber));
    }

    public void changeModule(Long id, Long moduleId) {
        LessonData lesson = get(id);
        ModuleData module;

        if(moduleId != null) {

            module = modulesService.get(moduleId);

            if(!lesson.getCourse().getId().equals(module.getCourse().getId())) {
                throw new IllegalArgumentException("Lesson is from other course than module");
            }

        } else {
            module = null;
        }


        update(new LessonData(lesson, module));
    }

    private void validate(LessonData data) {
        validateTitle(data.getTitle());
        validateMovieLink(data.getMovieLink(), data.getMovieLinkType(), "Movie link");
        validateDescription(data.getDescription());
        validateVisibilityStatus(data.getVisibilityStatus());
        validateModule(data.getModule(), data.getCourse());
        validateType(data.getType());
        validateTaskSolutionDescription(data.getTaskSolutionDescription());
        validateMovieLink(data.getTaskSolutionMovieLink(), data.getTaskSolutionMovieLinkType(), "Task solution movie link");
    }

    private void validateModule(ModuleData module, CourseData course) {

        if(module != null) {
            if(!course.getId().equals(module.getCourse().getId())) {
                throw new IllegalArgumentException("This module is from other course");
            }

            if(!course.getType().equals(CourseType.COURSE.getCode())) {
                throw new IllegalArgumentException("Lessons in this course type can't have modules");
            }
        }
    }

    private void validateTitle(String title) {
        if(title == null || title.equals("")) {
            throw new IllegalArgumentException("Title can't be empty");
        } else if (title.length() > 200) {
            throw new IllegalArgumentException("Title too long, max 200 characters");
        }
    }

    private void validateMovieLink(String movieLink, MovieLinkType movieLinkType, String fieldName) {

        if(movieLink.length() > 500) {
            throw new IllegalArgumentException(fieldName + " too long, max 500 characters");
        }

        if(movieLinkType == null) {
            throw new IllegalArgumentException(fieldName + " type can't be null!");
        }

        switch (movieLinkType) {
            case NO_MOVIE:
                validateNoMovieLink(movieLink, fieldName);
                break;
            case YOUTUBE:
                validateYouTubeLink(movieLink, fieldName);
                break;
            case VIMEO:
                validateVimeoLink(movieLink, fieldName);
                break;
            default:
                throw new IllegalArgumentException(fieldName + "type incorrect");
        }

    }

    private void validateNoMovieLink(String movieLink, String fieldName) {
        if (!movieLink.equals("")) {
            throw new IllegalArgumentException(fieldName + " must be empty, because you chose 'no movie' link type");
        }
    }

    private void validateYouTubeLink(String movieLink, String fieldName) {
        if(movieLink.equals("")){
            throw new IllegalArgumentException(fieldName + " can't be empty. Must be from YouTube");
        } else if (!isLinkFromYouTube(movieLink)) {
            throw new IllegalArgumentException(fieldName + " must be from YouTube");
        } else if (!hasLinkEmbed(movieLink)) {
            throw new IllegalArgumentException(fieldName + " version is incorrect. Your link must have 'embed' inside");
        } else if (!isYouTubeLinkCorrect(movieLink)) {
            throw new IllegalArgumentException(fieldName + " is incorrect");
        }
    }

    private Boolean hasLinkEmbed(String movieLink) {
        Pattern pattern = Pattern.compile("/embed/");
        Matcher matcher = pattern.matcher(movieLink);
        return matcher.find();
    }

    private Boolean isLinkFromYouTube(String movieLink) {
        Pattern pattern = Pattern.compile("https://www\\.youtube\\.com.*");
        Matcher matcher = pattern.matcher(movieLink);
        return matcher.matches();
    }

    private Boolean isYouTubeLinkCorrect(String movieLink) {
        Pattern pattern = Pattern.compile("https://www\\.youtube\\.com/embed/.*");
        Matcher matcher = pattern.matcher(movieLink);
        return matcher.matches();
    }

    private void validateVimeoLink(String movieLink, String fieldName) {
        if (movieLink.equals("")) {
            throw new IllegalArgumentException(fieldName + " can't be empty. Must be from Vimeo");
        } else if(!isVimeoLinkCorrect(movieLink)) {
            throw new IllegalArgumentException(fieldName + " is incorrect, should be from Vimeo");
        }
    }

    private Boolean isVimeoLinkCorrect(String movieLink) {
        Pattern pattern = Pattern.compile("https://player\\.vimeo\\.com/video/.*");
        Matcher matcher = pattern.matcher(movieLink);
        return matcher.matches();
    }

    private void validateDescription(String description) {
        if(description.length() > 5000) {
            throw new IllegalArgumentException("Description too long, max 5000 characters");
        }
    }

    private void validateVisibilityStatus(String visibilityStatus) {
        if(!visibilityStatus.equals(LessonVisibilityStatus.INVISIBLE.getValue()) && !visibilityStatus.equals(LessonVisibilityStatus.VISIBLE.getValue())) {
            throw new IllegalArgumentException("Incorrect visibility status");
        }
    }

    private void validateType(String type) {

        if(type == null) {
            throw new IllegalArgumentException("Type can't be null");
        }

        if(!type.equals(LessonType.STANDARD.getCode()) && !type.equals(LessonType.TASK.getCode())) {
            throw new IllegalArgumentException("Incorrect type");
        }
    }

    private void validateTaskSolutionDescription(String taskSolutionDescription) {
        if(taskSolutionDescription != null && taskSolutionDescription.length() > 5000) {
            throw new IllegalArgumentException("Task solution description too long, max 5000 characters");
        }
    }

}
