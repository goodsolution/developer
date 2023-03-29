package pl.com.goodsolution.adviser.api.courseplatform;

import java.util.List;

public class LessonCommentsRequestGetResponsePublic {
    private List<LessonCommentGetResponsePublic> lessonComments;

    public LessonCommentsRequestGetResponsePublic(List<LessonCommentGetResponsePublic> lessonComments) {
        this.lessonComments = lessonComments;
    }

    public List<LessonCommentGetResponsePublic> getLessonComments() {
        return lessonComments;
    }
}
