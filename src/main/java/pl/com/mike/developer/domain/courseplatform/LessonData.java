package pl.com.mike.developer.domain.courseplatform;

import pl.com.mike.developer.logic.courseplatform.LessonType;
import pl.com.mike.developer.logic.courseplatform.LessonVisibilityStatus;
import pl.com.mike.developer.logic.courseplatform.MovieLinkType;

import java.time.LocalDateTime;

public class LessonData {
    private Long id;
    private CourseData course;
    private String title;
    private String description;
    private String movieLink;
    private Long orderNumber;
    private String visibilityStatus;
    private ModuleData module;
    private LocalDateTime deleteDatetime;
    private String type;
    private String taskSolutionDescription;
    private String taskSolutionMovieLink;
    private MovieLinkType movieLinkType;
    private MovieLinkType taskSolutionMovieLinkType;

    private Boolean watched;
    private Long moduleId;
    private Long courseId;

    public LessonData(Long id, String title, String description, String movieLink, String visibilityStatus, Long moduleId, String type, String taskSolutionDescription, String taskSolutionMovieLink, MovieLinkType movieLinkType, MovieLinkType taskSolutionMovieLinkType) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.movieLink = movieLink;
        this.visibilityStatus = visibilityStatus;
        this.moduleId = moduleId;
        this.type = type;
        this.taskSolutionDescription = taskSolutionDescription;
        this.taskSolutionMovieLink = taskSolutionMovieLink;
        this.movieLinkType = movieLinkType;
        this.taskSolutionMovieLinkType = taskSolutionMovieLinkType;
    }

    public LessonData(Long id, CourseData course, String title, String description, String movieLink, Long orderNumber, String visibilityStatus, ModuleData module, LocalDateTime deleteDatetime, String type, String taskSolutionDescription, String taskSolutionMovieLink, MovieLinkType movieLinkType, MovieLinkType taskSolutionMovieLinkType) {
        this.id = id;
        this.course = course;
        this.title = title;
        this.description = description;
        this.movieLink = movieLink;
        this.orderNumber = orderNumber;
        this.visibilityStatus = visibilityStatus;
        this.module = module;
        this.deleteDatetime = deleteDatetime;
        this.type = type;
        this.taskSolutionDescription = taskSolutionDescription;
        this.taskSolutionMovieLink = taskSolutionMovieLink;
        this.movieLinkType = movieLinkType;
        this.taskSolutionMovieLinkType = taskSolutionMovieLinkType;
    }

    public LessonData(String title, String description, String movieLink, Long moduleId, Long courseId, MovieLinkType movieLinkType) {
        this.title = title;
        this.description = description;
        this.movieLink = movieLink;
        this.moduleId = moduleId;
        this.courseId = courseId;
        this.movieLinkType = movieLinkType;
    }

    public LessonData(CourseData course, String title, String description, String movieLink, LessonVisibilityStatus visibilityStatus, ModuleData module, LessonType type, MovieLinkType movieLinkType, MovieLinkType taskSolutionMovieLinkType, String taskSolutionMovieLink) {
        this.course = course;
        this.title = title;
        this.description = description;
        this.movieLink = movieLink;
        this.visibilityStatus = visibilityStatus.getValue();
        this.module = module;
        this.type = type.getCode();
        this.movieLinkType = movieLinkType;
        this.taskSolutionMovieLinkType = taskSolutionMovieLinkType;
        this.taskSolutionMovieLink = taskSolutionMovieLink;
    }

    public LessonData(CourseData course, String title, String description, String movieLink, String visibilityStatus, ModuleData module, String type, String taskSolutionDescription, String taskSolutionMovieLink, MovieLinkType movieLinkType, MovieLinkType taskSolutionMovieLinkType) {
        this.course = course;
        this.title = title;
        this.description = description;
        this.movieLink = movieLink;
        this.visibilityStatus = visibilityStatus;
        this.module = module;
        this.type = type;
        this.taskSolutionDescription = taskSolutionDescription;
        this.taskSolutionMovieLink = taskSolutionMovieLink;
        this.movieLinkType = movieLinkType;
        this.taskSolutionMovieLinkType = taskSolutionMovieLinkType;
    }

    public LessonData(Long id, CourseData course, String title, String description, String movieLink, Long orderNumber, Boolean watched, String visibilityStatus, ModuleData module, LocalDateTime deleteDatetime, String type, String taskSolutionDescription, String taskSolutionMovieLink, MovieLinkType movieLinkType, MovieLinkType taskSolutionMovieLinkType) {
        this.id = id;
        this.course = course;
        this.title = title;
        this.description = description;
        this.movieLink = movieLink;
        this.orderNumber = orderNumber;
        this.watched = watched;
        this.visibilityStatus = visibilityStatus;
        this.module = module;
        this.deleteDatetime = deleteDatetime;
        this.type = type;
        this.taskSolutionDescription = taskSolutionDescription;
        this.taskSolutionMovieLink = taskSolutionMovieLink;
        this.movieLinkType = movieLinkType;
        this.taskSolutionMovieLinkType = taskSolutionMovieLinkType;
    }

    public LessonData(LessonData data, Long orderNumber) {
        this.id = data.getId();
        this.course = data.getCourse();
        this.title = data.getTitle();
        this.description = data.getDescription();
        this.movieLink = data.getMovieLink();
        this.orderNumber = orderNumber;
        this.visibilityStatus = data.getVisibilityStatus();
        this.module = data.getModule();
        this.deleteDatetime = data.getDeleteDatetime();
        this.type = data.getType();
        this.taskSolutionDescription = data.getTaskSolutionDescription();
        this.taskSolutionMovieLink = data.getTaskSolutionMovieLink();
        this.movieLinkType = data.getMovieLinkType();
        this.taskSolutionMovieLinkType = data.getTaskSolutionMovieLinkType();
    }

    public LessonData(LessonData data, Boolean watched) {
        this.id = data.getId();
        this.course = data.getCourse();
        this.title = data.getTitle();
        this.description = data.getDescription();
        this.movieLink = data.getMovieLink();
        this.orderNumber = data.getOrderNumber();
        this.watched = watched;
        this.visibilityStatus = data.getVisibilityStatus();
        this.module = data.getModule();
        this.deleteDatetime = data.getDeleteDatetime();
        this.type = data.getType();
        this.taskSolutionDescription = data.getTaskSolutionDescription();
        this.taskSolutionMovieLink = data.getTaskSolutionMovieLink();
        this.movieLinkType = data.getMovieLinkType();
        this.taskSolutionMovieLinkType = data.getTaskSolutionMovieLinkType();
    }

    public LessonData(LessonData data, ModuleData module) {
        this.id = data.getId();
        this.course = data.getCourse();
        this.title = data.getTitle();
        this.description = data.getDescription();
        this.movieLink = data.getMovieLink();
        this.orderNumber = data.getOrderNumber();
        this.visibilityStatus = data.getVisibilityStatus();
        this.module = module;
        this.deleteDatetime = data.getDeleteDatetime();
        this.type = data.getType();
        this.taskSolutionDescription = data.getTaskSolutionDescription();
        this.taskSolutionMovieLink = data.getTaskSolutionMovieLink();
        this.movieLinkType = data.getMovieLinkType();
        this.taskSolutionMovieLinkType = data.getTaskSolutionMovieLinkType();
    }

    public LessonData(LessonData data, LocalDateTime deleteDatetime) {
        this.id = data.getId();
        this.course = data.getCourse();
        this.title = data.getTitle();
        this.description = data.getDescription();
        this.movieLink = data.getMovieLink();
        this.orderNumber = data.getOrderNumber();
        this.visibilityStatus = data.getVisibilityStatus();
        this.module = data.getModule();
        this.deleteDatetime = deleteDatetime;
        this.type = data.getType();
        this.taskSolutionDescription = data.getTaskSolutionDescription();
        this.taskSolutionMovieLink = data.getTaskSolutionMovieLink();
        this.movieLinkType = data.getMovieLinkType();
        this.taskSolutionMovieLinkType = data.getTaskSolutionMovieLinkType();
    }



    public Long getId() {
        return id;
    }

    public CourseData getCourse() {
        return course;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getMovieLink() {
        return movieLink;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public Boolean getWatched() {
        return watched;
    }

    public String getVisibilityStatus() {
        return visibilityStatus;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public ModuleData getModule() {
        return module;
    }

    public LocalDateTime getDeleteDatetime() {
        return deleteDatetime;
    }

    public String getType() {
        return type;
    }

    public String getTaskSolutionDescription() {
        return taskSolutionDescription;
    }

    public String getTaskSolutionMovieLink() {
        return taskSolutionMovieLink;
    }

    public MovieLinkType getMovieLinkType() {
        return movieLinkType;
    }

    public MovieLinkType getTaskSolutionMovieLinkType() {
        return taskSolutionMovieLinkType;
    }
}
