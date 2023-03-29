package pl.com.goodsolution.adviser.domain.courseplatform;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class BasketData implements Serializable {
    private List<CourseData> courses;
    private BigDecimal totalPrice;

    public BasketData(List<CourseData> courses, BigDecimal totalPrice) {
        this.courses = courses;
        this.totalPrice = totalPrice;
    }

    public List<CourseData> getCourses() {
        return courses;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
