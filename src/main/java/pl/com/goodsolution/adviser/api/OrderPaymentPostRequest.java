package pl.com.goodsolution.adviser.api;

import java.math.BigDecimal;

public class OrderPaymentPostRequest {
    private BigDecimal amount;
    private Long paymentMethodId;


    public OrderPaymentPostRequest() {
    }

    public OrderPaymentPostRequest(BigDecimal amount, Long paymentMethodId) {
        this.amount = amount;
        this.paymentMethodId = paymentMethodId;
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }
}
