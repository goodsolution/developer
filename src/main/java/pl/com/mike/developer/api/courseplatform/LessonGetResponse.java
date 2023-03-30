package pl.com.mike.developer.api.courseplatform;

import pl.com.mike.developer.domain.courseplatform.LessonData;

public class LessonGetResponse {
    private Long id;
    private CourseGetResponse course;
    private String title;
    private String description;
    private String movieLink;
    private Long orderNumber;
    private Boolean watched;
    private String visibilityStatus;
    private Long moduleId;
    private String type;
    private String taskSolutionDescription;
    private String taskSolutionMovieLink;
    private String movieLinkType;
    private String taskSolutionMovieLinkType;

    public LessonGetResponse(LessonData data) {
        this.id = data.getId();
        this.course = new CourseGetResponse(data.getCourse());
        this.title = data.getTitle();
        this.description = data.getDescription();
        this.movieLink = data.getMovieLink();
        this.orderNumber = data.getOrderNumber();
        this.watched = data.getWatched();
        this.visibilityStatus = data.getVisibilityStatus();
        this.moduleId = data.getModule() == null ? null : data.getModule().getId();
        this.type = data.getType();
        this.taskSolutionDescription = data.getTaskSolutionDescription();
        this.taskSolutionMovieLink = data.getTaskSolutionMovieLink();
        this.movieLinkType = data.getMovieLinkType().getCode();
        this.taskSolutionMovieLinkType = data.getTaskSolutionMovieLinkType().getCode();
    }

    public Long getId() {
        return id;
    }

    public CourseGetResponse getCourse() {
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

    public String getType() {
        return type;
    }

    public String getTaskSolutionDescription() {
        return taskSolutionDescription;
    }

    public String getTaskSolutionMovieLink() {
        return taskSolutionMovieLink;
    }

    public String getMovieLinkType() {
        return movieLinkType;
    }

    public String getTaskSolutionMovieLinkType() {
        return taskSolutionMovieLinkType;
    }
}
