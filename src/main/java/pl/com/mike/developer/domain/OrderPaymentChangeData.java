package pl.com.mike.developer.domain;

public class OrderPaymentChangeData {
    private Long orderId;
    private Long paymentStatusId;
    private Long userId;


    public OrderPaymentChangeData(Long orderId, Long paymentStatusId, Long userId) {
        this.orderId = orderId;
        this.paymentStatusId = paymentStatusId;
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getPaymentStatusId() {
        return paymentStatusId;
    }

    public Long getUserId() {
        return userId;
    }
}
