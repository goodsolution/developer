package pl.com.goodsolution.adviser.domain.courseplatform;

public class PurchasedCoursesFilter {
    private Long id;
    private Long orderId;

    public PurchasedCoursesFilter(Long orderId) {
        this.orderId = orderId;
    }

    public PurchasedCoursesFilter(Long id, Long orderId) {
        this.id = id;
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getId() {
        return id;
    }
}
