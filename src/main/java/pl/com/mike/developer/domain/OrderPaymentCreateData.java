package pl.com.mike.developer.domain;

import java.math.BigDecimal;

public class OrderPaymentCreateData {
    private Long orderId;
    private BigDecimal amount;
    private Long paymentMethodId;

    public OrderPaymentCreateData(Long orderId, BigDecimal amount, Long paymentMethodId) {
        this.orderId = orderId;
        this.amount = amount;
        this.paymentMethodId = paymentMethodId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }
}
