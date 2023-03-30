package pl.com.mike.developer.api;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class OrderPostRequest {
    private Long customerId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate purchaseDateTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime purchaseTime;
    private Long deliveryMethodId;
    private Long paymentMethodId;
    private Long driverId;
    private NewOrderProductPostRequest products;
    private String discountCode;
    private String comments;
    private Boolean demandingCustomer;
    private Boolean sendEmail;
    private Boolean driverExclude;
    private Boolean newOrderGroup;
    private Long status;


    public OrderPostRequest() {
    }

    public OrderPostRequest(Long customerId, LocalDate purchaseDateTime, LocalTime purchaseTime, Long deliveryMethodId, Long paymentMethodId, Long driverId, NewOrderProductPostRequest products, String discountCode, String comments, Boolean demandingCustomer, Boolean sendEmail, Boolean driverExclude, Boolean newOrderGroup, Long status) {
        this.customerId = customerId;
        this.purchaseDateTime = purchaseDateTime;
        this.purchaseTime = purchaseTime;
        this.deliveryMethodId = deliveryMethodId;
        this.paymentMethodId = paymentMethodId;
        this.driverId = driverId;
        this.products = products;
        this.discountCode = discountCode;
        this.comments = comments;
        this.demandingCustomer = demandingCustomer;
        this.sendEmail = sendEmail;
        this.driverExclude = driverExclude;
        this.newOrderGroup = newOrderGroup;
        this.status = status;
    }


    public Long getCustomerId() {
        return customerId;
    }

    public LocalDate getPurchaseDateTime() {
        return purchaseDateTime;
    }

    public Long getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public NewOrderProductPostRequest getProducts() {
        return products;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public String getComments() {
        return comments;
    }

    public Boolean getDemandingCustomer() {
        return demandingCustomer;
    }

    public Boolean getSendEmail() {
        return sendEmail;
    }

    public Boolean getDriverExclude() {
        return driverExclude;
    }

    public Boolean getNewOrderGroup() {
        return newOrderGroup;
    }

    public Long getStatus() {
        return status;
    }

    public LocalTime getPurchaseTime() {
        return purchaseTime;
    }

    @Override
    public String toString() {
        return "OrderPostRequest{" +
                "customerId=" + customerId +
                ", purchaseDateTime=" + purchaseDateTime +
                ", purchaseTime=" + purchaseTime +
                ", deliveryMethodId=" + deliveryMethodId +
                ", paymentMethodId=" + paymentMethodId +
                ", driverId=" + driverId +
                ", products=" + products +
                ", discountCode='" + discountCode + '\'' +
                ", comments='" + comments + '\'' +
                ", demandingCustomer=" + demandingCustomer +
                ", sendEmail=" + sendEmail +
                ", driverExclude=" + driverExclude +
                ", newOrderGroup=" + newOrderGroup +
                ", status=" + status +
                '}';
    }
}
