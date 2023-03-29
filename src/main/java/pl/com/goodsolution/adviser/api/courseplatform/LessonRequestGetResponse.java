package pl.com.goodsolution.adviser.api.courseplatform;

public class LessonRequestGetResponse {
    private LessonGetResponse lesson;

    public LessonRequestGetResponse(LessonGetResponse lesson) {
        this.lesson = lesson;
    }

    public LessonGetResponse getLesson() {
        return lesson;
    }
}
