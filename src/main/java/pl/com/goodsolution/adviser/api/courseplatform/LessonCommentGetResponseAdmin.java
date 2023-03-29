package pl.com.goodsolution.adviser.api.courseplatform;


import pl.com.goodsolution.adviser.domain.courseplatform.LessonCommentData;

public class LessonCommentGetResponseAdmin {
    private Long id;
    private LessonGetResponse lesson;
    private CustomerGetResponse customer;
    private String text;
    private String status;
    private String createDatetime;

    public LessonCommentGetResponseAdmin(LessonCommentData data) {
        this.id = data.getId();
        this.lesson = new LessonGetResponse(data.getLesson());
        this.customer = new CustomerGetResponse(data.getCustomer());
        this.text = data.getText();
        this.status = data.getStatus();
        this.createDatetime = data.getCreateDatetime().toString();
    }

    public Long getId() {
        return id;
    }

    public LessonGetResponse getLesson() {
        return lesson;
    }

    public CustomerGetResponse getCustomer() {
        return customer;
    }

    public String getText() {
        return text;
    }

    public String getStatus() {
        return status;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }
}
