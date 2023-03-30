package pl.com.mike.developer.domain;

import java.time.LocalDate;

public class OrdersFilter {
    private String firstName;
    private String lastName;
    private String orderId;

    private LocalDate orderDateFrom;
    private LocalDate orderDateTo;
    private String firstNameAndLastName;
    private LocalDate deliveryDateFrom;
    private LocalDate deliveryDateTo;
    private Long paymentStatusId;
    private String discountCode;
    private Long paymentMethodId;
    private Long driverId;
    private Long deliveryMethodId;
    private Long dietId;
    private Long orderStatusId;
    private Long ordersFinishingInDays;
    private String city;
    private LocalDate markedAsPaidDateFrom;
    private LocalDate markedAsPaidDateTo;
    private Boolean invoice;
    private String phone;

    public OrdersFilter(String firstName, String lastName, String orderId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.orderId = orderId;
    }

    public OrdersFilter(LocalDate orderDateFrom, LocalDate orderDateTo, String firstNameAndLastName, LocalDate deliveryDateFrom, LocalDate deliveryDateTo, Long paymentStatusId, String discountCode, Long paymentMethodId, Long driverId, Long deliveryMethodId, Long dietId, Long orderStatusId, Long ordersFinishingInDays, String city, LocalDate markedAsPaidDateFrom, LocalDate markedAsPaidDateTo, Boolean invoice, String orderId, String phone) {
        this.orderDateFrom = orderDateFrom;
        this.orderDateTo = orderDateTo;
        this.firstNameAndLastName = firstNameAndLastName;
        this.deliveryDateFrom = deliveryDateFrom;
        this.deliveryDateTo = deliveryDateTo;
        this.paymentStatusId = paymentStatusId;
        this.discountCode = discountCode;
        this.paymentMethodId = paymentMethodId;
        this.driverId = driverId;
        this.deliveryMethodId = deliveryMethodId;
        this.dietId = dietId;
        this.orderStatusId = orderStatusId;
        this.ordersFinishingInDays = ordersFinishingInDays;
        this.city = city;
        this.markedAsPaidDateFrom = markedAsPaidDateFrom;
        this.markedAsPaidDateTo = markedAsPaidDateTo;
        this.invoice = invoice;
        this.orderId = orderId;
        this.phone = phone;
    }

    public LocalDate getOrderDateFrom() {
        return orderDateFrom;
    }

    public LocalDate getOrderDateTo() {
        return orderDateTo;
    }

    public String getFirstNameAndLastName() {
        return firstNameAndLastName;
    }

    public LocalDate getDeliveryDateFrom() {
        return deliveryDateFrom;
    }

    public LocalDate getDeliveryDateTo() {
        return deliveryDateTo;
    }

    public Long getPaymentStatusId() {
        return paymentStatusId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public Long getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public Long getDietId() {
        return dietId;
    }

    public Long getOrderStatusId() {
        return orderStatusId;
    }

    public Long getOrdersFinishingInDays() {
        return ordersFinishingInDays;
    }

    public String getCity() {
        return city;
    }

    public LocalDate getMarkedAsPaidDateFrom() {
        return markedAsPaidDateFrom;
    }

    public LocalDate getMarkedAsPaidDateTo() {
        return markedAsPaidDateTo;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Boolean getInvoice() {
        return invoice;
    }

    public String getPhone() {
        return phone;
    }
}
