package pl.com.goodsolution.adviser.api;

import java.math.BigDecimal;

public class CustomerNewGetResponse {
    private Long no;
    private Long orderId;
    private String customer;
    private String orderNumber;
    private BigDecimal value;
    private String orderStatus;
    private String dateTime;


    public CustomerNewGetResponse(Long no, Long orderId, String customer, String orderNumber, BigDecimal value, String orderStatus, String dateTime) {
        this.no = no;
        this.orderId = orderId;
        this.customer = customer;
        this.orderNumber = orderNumber;
        this.value = value;
        this.orderStatus = orderStatus;
        this.dateTime = dateTime;
    }


    public Long getNo() {
        return no;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getCustomer() {
        return customer;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getDateTime() {
        return dateTime;
    }
}
