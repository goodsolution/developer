package pl.com.goodsolution.adviser.domain.courseplatform;

import java.time.LocalDateTime;

public class LessonCommentData {
    private Long id;
    private LessonData lesson;
    private CustomerData customer;
    private String text;
    private String status;
    private LocalDateTime createDatetime;
    private LocalDateTime deleteDatetime;

    public LessonCommentData(LessonData lesson, CustomerData customer, String text, String status, LocalDateTime createDatetime, LocalDateTime deleteDatetime) {
        this.lesson = lesson;
        this.customer = customer;
        this.text = text;
        this.status = status;
        this.createDatetime = createDatetime;
        this.deleteDatetime = deleteDatetime;
    }

    public LessonCommentData(Long id, LessonData lesson, CustomerData customer, String text, String status, LocalDateTime createDatetime, LocalDateTime deleteDatetime) {
        this.id = id;
        this.lesson = lesson;
        this.customer = customer;
        this.text = text;
        this.status = status;
        this.createDatetime = createDatetime;
        this.deleteDatetime = deleteDatetime;
    }

    public LessonCommentData(LessonCommentData data, LocalDateTime deleteDatetime) {
        this.id = data.getId();
        this.lesson = data.getLesson();
        this.customer = data.getCustomer();
        this.text = data.getText();
        this.status = data.getStatus();
        this.createDatetime = data.getCreateDatetime();
        this.deleteDatetime = deleteDatetime;
    }

    public LessonCommentData(LessonCommentData data, String status) {
        this.id = data.getId();
        this.lesson = data.getLesson();
        this.customer = data.getCustomer();
        this.text = data.getText();
        this.status = status;
        this.createDatetime = data.getCreateDatetime();
        this.deleteDatetime = data.getDeleteDatetime();
    }

    public Long getId() {
        return id;
    }

    public LessonData getLesson() {
        return lesson;
    }

    public CustomerData getCustomer() {
        return customer;
    }

    public String getText() {
        return text;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreateDatetime() {
        return createDatetime;
    }

    public LocalDateTime getDeleteDatetime() {
        return deleteDatetime;
    }
}
