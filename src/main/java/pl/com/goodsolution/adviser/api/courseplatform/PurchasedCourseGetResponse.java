package pl.com.goodsolution.adviser.api.courseplatform;

import pl.com.goodsolution.adviser.domain.courseplatform.CourseData;
import pl.com.goodsolution.adviser.domain.courseplatform.CourseOrderData;
import pl.com.goodsolution.adviser.domain.courseplatform.PurchasedCourseData;

import java.math.BigDecimal;

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
