package pl.com.mike.developer.api.courseplatform;

public class LessonCommentPostRequest {
    private String text;
    private Long lessonId;

    public LessonCommentPostRequest() {
    }

    public String getText() {
        return text;
    }

    public Long getLessonId() {
        return lessonId;
    }
}
