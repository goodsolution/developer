package pl.com.mike.developer.domain.courseplatform;

public class LessonAttachmentsFilter {
    private Long lessonId;
    private Boolean deleted;

    public LessonAttachmentsFilter(Long lessonId, Boolean deleted) {
        this.lessonId = lessonId;
        this.deleted = deleted;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public Boolean getDeleted() {
        return deleted;
    }
}
