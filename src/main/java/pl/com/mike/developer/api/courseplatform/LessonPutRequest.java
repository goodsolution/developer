package pl.com.mike.developer.api.courseplatform;

public class LessonPutRequest {
    private String title;
    private String description;
    private String movieLink;
    private String visibilityStatus;
    private Long moduleId;
    private String type;
    private String taskSolutionDescription;
    private String taskSolutionMovieLink;
    private String movieLinkType;
    private String taskSolutionMovieLinkType;

    public LessonPutRequest() {
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
