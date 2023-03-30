package pl.com.mike.developer.api.courseplatform;

import java.math.BigDecimal;
import java.util.List;

public class BasketGetResponse {

    private List<CourseGetResponse> courses;
    private String totalPrice;

    public BasketGetResponse(List<CourseGetResponse> courses, BigDecimal totalPrice) {
        this.courses = courses;
        this.totalPrice = totalPrice.toString();
    }

    public List<CourseGetResponse> getCourses() {
        return courses;
    }

    public String getTotalPrice() {
        return totalPrice;
    }
}
