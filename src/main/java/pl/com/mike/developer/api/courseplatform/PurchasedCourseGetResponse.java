package pl.com.mike.developer.api.courseplatform;

import pl.com.mike.developer.domain.courseplatform.PurchasedCourseData;

public class PurchasedCourseGetResponse {
    private Long id;
    private CourseOrderGetResponse order;
    private CourseGetResponse course;
    private String price;
    private Boolean returned;

    public PurchasedCourseGetResponse(PurchasedCourseData data) {
        this.id = data.getId();
        this.order = new CourseOrderGetResponse(data.getOrder());
        this.course = new CourseGetResponse(data.getCourse());
        this.price = data.getPrice().toString();
        this.returned = data.getReturned();
    }

    public Long getId() {
        return id;
    }

    public CourseOrderGetResponse getOrder() {
        return order;
    }

    public CourseGetResponse getCourse() {
        return course;
    }

    public String getPrice() {
        return price;
    }

    public Boolean getReturned() {
        return returned;
    }
}
