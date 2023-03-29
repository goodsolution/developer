package pl.com.goodsolution.adviser.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderPaymentResultData {
    private List<OrderPaymentData> payments = new ArrayList<>();
    private BigDecimal sum;
    private BigDecimal toPayLeft;
    private BigDecimal refund;

    public OrderPaymentResultData(List<OrderPaymentData> payments, BigDecimal sum, BigDecimal toPayLeft, BigDecimal refund) {
        this.payments = payments;
        this.sum = sum;
        this.toPayLeft = toPayLeft;
        this.refund = refund;
    }


    public List<OrderPaymentData> getPayments() {
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
        return "OrderPaymentResultData{" +
                "payments=" + payments +
                ", sum=" + sum +
                ", toPayLeft=" + toPayLeft +
                ", refund=" + refund +
                '}';
    }
}
