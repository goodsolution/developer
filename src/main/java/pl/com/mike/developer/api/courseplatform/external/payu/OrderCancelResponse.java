package pl.com.mike.developer.api.courseplatform.external.payu;

public class OrderCancelResponse {
    private String orderId;
    private Long extOrderId;
    private Status status;

    public OrderCancelResponse() {
    }

    public String getOrderId() {
        return orderId;
    }

    public Long getExtOrderId() {
        return extOrderId;
    }

    public Status getStatus() {
        return status;
    }
}
