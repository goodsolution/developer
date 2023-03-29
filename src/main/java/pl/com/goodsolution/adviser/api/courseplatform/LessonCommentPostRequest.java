package pl.com.goodsolution.adviser.api.courseplatform;

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
