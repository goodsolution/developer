package pl.com.mike.developer.domain.courseplatform;

public class CustomerLessonDetailsFilter {
    private Long lessonId;
    private Long customerId;

    public CustomerLessonDetailsFilter(Long lessonId, Long customerId) {
        this.lessonId = lessonId;
        this.customerId = customerId;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public Long getCustomerId() {
        return customerId;
    }
}
