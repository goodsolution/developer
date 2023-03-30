package pl.com.mike.developer.api.courseplatform;

import java.util.List;

public class LessonCommentsRequestGetResponseAdmin {
    private List<LessonCommentGetResponseAdmin> lessonComments;

    public LessonCommentsRequestGetResponseAdmin(List<LessonCommentGetResponseAdmin> lessonComments) {
        this.lessonComments = lessonComments;
    }

    public List<LessonCommentGetResponseAdmin> getLessonComments() {
        return lessonComments;
    }
}
