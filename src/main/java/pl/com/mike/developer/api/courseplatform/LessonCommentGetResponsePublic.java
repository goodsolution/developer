package pl.com.mike.developer.api.courseplatform;

import pl.com.mike.developer.domain.courseplatform.LessonCommentData;

public class LessonCommentGetResponsePublic {
    private Long id;
    private String text;
    private String createDatetime;

    public LessonCommentGetResponsePublic(LessonCommentData data) {
        this.id = data.getId();
        this.text = data.getText();
        this.createDatetime = data.getCreateDatetime().toString();
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }
}
