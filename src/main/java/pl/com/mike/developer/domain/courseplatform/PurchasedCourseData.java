package pl.com.mike.developer.domain.courseplatform;

import java.math.BigDecimal;

public class PurchasedCourseData {
    private Long id;
    private CourseOrderData order;
    private CourseData course;
    private BigDecimal price;
    private Boolean returned;

    public PurchasedCourseData(Long id, CourseOrderData order, CourseData course, BigDecimal price, Boolean returned) {
        this.id = id;
        this.order = order;
        this.course = course;
        this.price = price;
        this.returned = returned;
    }

    public PurchasedCourseData(CourseOrderData order, CourseData course, BigDecimal price, Boolean returned) {
        this.order = order;
        this.course = course;
        this.price = price;
        this.returned = returned;
    }

    public PurchasedCourseData(PurchasedCourseData data, Boolean returned) {
        this.id = data.getId();
        this.order = data.getOrder();
        this.course = data.getCourse();
        this.price = data.getPrice();
        this.returned = returned;
    }

    public Long getId() {
        return id;
    }

    public CourseOrderData getOrder() {
        return order;
    }

    public CourseData getCourse() {
        return course;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Boolean getReturned() {
        return returned;
    }
}
