package pl.com.goodsolution.adviser.domain.courseplatform;

import java.time.LocalDateTime;

public class CourseCommentData {
    private Long id;
    private CourseData course;
    private CustomerData customer;
    private String text;
    private String status;
    private LocalDateTime createDatetime;
    private LocalDateTime deleteDatetime;

    public CourseCommentData(Long id, CourseData course, CustomerData customer, String text, String status, LocalDateTime createDatetime, LocalDateTime deleteDatetime) {
        this.id = id;
        this.course = course;
        this.customer = customer;
        this.text = text;
        this.status = status;
        this.createDatetime = createDatetime;
        this.deleteDatetime = deleteDatetime;
    }

    public CourseCommentData(CourseData course, CustomerData customer, String text, String status, LocalDateTime createDatetime) {
        this.course = course;
        this.customer = customer;
        this.text = text;
        this.status = status;
        this.createDatetime = createDatetime;
    }

    public CourseCommentData(CourseCommentData data, LocalDateTime deleteDatetime) {
        this.id = data.getId();
        this.course = data.getCourse();
        this.customer = data.getCustomer();
        this.text = data.getText();
        this.status = data.getStatus();
        this.createDatetime = data.getCreateDatetime();
        this.deleteDatetime = deleteDatetime;
    }

    public CourseCommentData(CourseCommentData data, String visibilityStatus) {
        this.id = data.getId();
        this.course = data.getCourse();
        this.customer = data.getCustomer();
        this.text = data.getText();
        this.status = visibilityStatus;
        this.createDatetime = data.getCreateDatetime();
        this.deleteDatetime = data.getDeleteDatetime();
    }


    public Long getId() {
        return id;
    }

    public CourseData getCourse() {
        return course;
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
