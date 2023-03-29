package pl.com.goodsolution.adviser.domain;

public class OrderPaymentMethodChangeData {
    private Long orderId;
    private Long oldPaymentMethodId;
    private Long newPaymentMethodId;
    private Long userId;

    public OrderPaymentMethodChangeData(Long orderId, Long oldPaymentMethodId, Long newPaymentMethodId, Long userId) {
        this.orderId = orderId;
        this.oldPaymentMethodId = oldPaymentMethodId;
        this.newPaymentMethodId = newPaymentMethodId;
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getOldPaymentMethodId() {
        return oldPaymentMethodId;
    }

    public Long getNewPaymentMethodId() {
        return newPaymentMethodId;
    }

    public Long getUserId() {
        return userId;
    }
}
