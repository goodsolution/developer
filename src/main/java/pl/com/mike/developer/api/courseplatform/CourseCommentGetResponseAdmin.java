package pl.com.mike.developer.api.courseplatform;

import pl.com.mike.developer.domain.courseplatform.CourseCommentData;

public class CourseCommentGetResponseAdmin {
    private Long id;
    private CourseGetResponse course;
    private CustomerGetResponse customer;
    private String text;
    private String status;
    private String createDatetime;

    public CourseCommentGetResponseAdmin(CourseCommentData data) {
        this.id = data.getId();
        this.course = new CourseGetResponse(data.getCourse());
        this.customer = new CustomerGetResponse(data.getCustomer());
        this.text = data.getText();
        this.status = data.getStatus();
        this.createDatetime = data.getCreateDatetime().toString();
    }

    public Long getId() {
        return id;
    }

    public CourseGetResponse getCourse() {
        return course;
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
