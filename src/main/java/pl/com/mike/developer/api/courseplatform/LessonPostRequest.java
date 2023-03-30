package pl.com.mike.developer.api.courseplatform;

public class LessonPostRequest {
    private Long courseId;
    private String title;
    private String description;
    private String movieLink;
    private Long moduleId;
    private String movieLinkType;

    public LessonPostRequest() {
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

    public Long getCourseId() {
        return courseId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public String getMovieLinkType() {
        return movieLinkType;
    }
}
