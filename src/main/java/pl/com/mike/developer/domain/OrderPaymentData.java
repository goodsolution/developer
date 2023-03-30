package pl.com.mike.developer.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderPaymentData {
    private Long orderId;
    private Long paymentNo;
    private Long paymentId;
    private LocalDateTime dateTime;
    private BigDecimal value;
    private String paymentMethodName;
    private Long type;


    public OrderPaymentData(Long orderId, Long paymentNo, Long paymentId, LocalDateTime dateTime, BigDecimal value, String paymentMethodName, Long type) {
        this.orderId = orderId;
        this.paymentNo = paymentNo;
        this.paymentId = paymentId;
        this.dateTime = dateTime;
        this.value = value;
        this.paymentMethodName = paymentMethodName;
        this.type = type;
    }


    public Long getOrderId() {
        return orderId;
    }

    public Long getPaymentNo() {
        return paymentNo;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public Long getType() {
        return type;
    }


    @Override
    public String toString() {
        return "OrderPaymentData{" +
                "orderId=" + orderId +
                ", paymentNo=" + paymentNo +
                ", paymentId=" + paymentId +
                ", dateTime=" + dateTime +
                ", value=" + value +
                ", paymentMethodName='" + paymentMethodName + '\'' +
                ", type=" + type +
                '}';
    }
}
