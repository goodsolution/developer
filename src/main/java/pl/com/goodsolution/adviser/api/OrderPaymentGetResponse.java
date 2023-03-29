package pl.com.goodsolution.adviser.api;

import java.math.BigDecimal;

public class OrderPaymentGetResponse {
    private Long orderId;
    private Long paymentNo;
    private Long paymentId;
    private String dateTime;
    private BigDecimal value;
    private String paymentMethodName;
    private Long type;


    public OrderPaymentGetResponse(Long orderId, Long paymentNo, Long paymentId, String dateTime, BigDecimal value, String paymentMethodName, Long type) {
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

    public String getDateTime() {
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
        return "OrderPaymentGetResponse{" +
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
