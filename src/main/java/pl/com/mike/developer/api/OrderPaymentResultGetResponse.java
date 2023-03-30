package pl.com.mike.developer.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderPaymentResultGetResponse {
    private List<OrderPaymentGetResponse> payments = new ArrayList<>();
    private BigDecimal sum;
    private BigDecimal toPayLeft;
    private BigDecimal refund;

    public OrderPaymentResultGetResponse(List<OrderPaymentGetResponse> payments, BigDecimal sum, BigDecimal toPayLeft, BigDecimal refund) {
        this.payments = payments;
        this.sum = sum;
        this.toPayLeft = toPayLeft;
        this.refund = refund;
    }


    public List<OrderPaymentGetResponse> getPayments() {
        return payments;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public BigDecimal getToPayLeft() {
        return toPayLeft;
    }

    public BigDecimal getRefund() {
        return refund;
    }


    @Override
    public String toString() {
        return "OrderPaymentResultGetResponse{" +
                "payments=" + payments +
                ", sum=" + sum +
                ", toPayLeft=" + toPayLeft +
                ", refund=" + refund +
                '}';
    }
}
