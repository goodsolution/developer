package pl.com.mike.developer.domain.courseplatform;

public class CourseAttachmentsFilter {
    private Long courseId;
    private Boolean deleted;
    private Long id;

    public CourseAttachmentsFilter(Long courseId, Boolean deleted) {
        this.courseId = courseId;
        this.deleted = deleted;
    }

    public CourseAttachmentsFilter(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public Long getId() {
        return id;
    }
}
