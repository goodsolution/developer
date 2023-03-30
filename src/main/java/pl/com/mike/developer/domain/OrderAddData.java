package pl.com.mike.developer.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class OrderAddData {
    private Long no;
    private Long id;
    private String firstName;
    private String lastName;
    private String orderId;

    private BigDecimal value;
    private String currency = "PLN";
    private Long deliveryMethodId;
    private String deliveryMethodName;
    private Long paymentMethodId;
    private String paymentMethodName;
    private Long paymentStatusId;
    private String paymentStatusName;
    private String paymentStatusCode;
    private Integer daysLeft;
    private Long orderStatusId;
    private String orderStatusName;
    private String orderStatusCode;
    private String discountName;
    private Boolean demandingCustomer;
    private String paymentStyle;
    private String daysLeftStyle;
    private String orderStatusStyle;
    private Boolean nextOrder;
    private String nextOrderIndicator;
    private String demandingCustomerIndicator;
    private Boolean receipt;
    private Boolean invoiceWanted;
    private Boolean invoiceIssued;


    private Long customerId;
    private LocalDateTime purchaseDateTime;
    private Boolean sendEmail;
    private Boolean newOrderGroup;
    private Boolean exceptionDriver;
    private Long driverId;
    private String comment;

    private List<OrderProductCreateData> products = Collections.emptyList();


    public OrderAddData(Long customerId, LocalDateTime purchaseDateTime, Long paymentMethodId, Long orderStatusId, Boolean sendEmail, Boolean newOrderGroup, Boolean exceptionDriver, Long driverId, String comment, Long deliveryMethodId, List<OrderProductCreateData> products, String discountName) {
        this.customerId = customerId;
        this.purchaseDateTime = purchaseDateTime;
        this.paymentMethodId = paymentMethodId;
        this.deliveryMethodId = deliveryMethodId;
        this.orderStatusId = orderStatusId;
        this.sendEmail = sendEmail;
        this.newOrderGroup = newOrderGroup;
        this.exceptionDriver = exceptionDriver;
        this.driverId = driverId;
        this.comment = comment;
        this.products = products;
        this.discountName = discountName;
    }

    public Long getNo() {
        return no;
    }

    public String getOrderId() {
        return orderId;
    }

    public LocalDateTime getPurchaseDateTime() {
        return purchaseDateTime;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }

    public Long getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public String getDeliveryMethodName() {
        return deliveryMethodName;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public Long getPaymentStatusId() {
        return paymentStatusId;
    }

    public String getPaymentStatusName() {
        return paymentStatusName;
    }

    public String getPaymentStatusCode() {
        return paymentStatusCode;
    }

    public Integer getDaysLeft() {
        return daysLeft;
    }

    public Long getOrderStatusId() {
        return orderStatusId;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public String getOrderStatusCode() {
        return orderStatusCode;
    }

    public String getDiscountName() {
        return discountName;
    }

    public Boolean getDemandingCustomer() {
        return demandingCustomer;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPaymentStyle() {
        return paymentStyle;
    }

    public String getDaysLeftStyle() {
        return daysLeftStyle;
    }

    public String getOrderStatusStyle() {
        return orderStatusStyle;
    }

    public Boolean getNextOrder() {
        return nextOrder;
    }

    public String getNextOrderIndicator() {
        return nextOrderIndicator;
    }

    public String getDemandingCustomerIndicator() {
        return demandingCustomerIndicator;
    }

    public Boolean getReceipt() {
        return receipt;
    }

    public Boolean getInvoiceWanted() {
        return invoiceWanted;
    }

    public Boolean getInvoiceIssued() {
        return invoiceIssued;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public List<OrderProductCreateData> getProducts() {
        return products;
    }

    public Boolean getSendEmail() {
        return sendEmail;
    }

    public Boolean getNewOrderGroup() {
        return newOrderGroup;
    }

    public Boolean getExceptionDriver() {
        return exceptionDriver;
    }

    public Long getDriverId() {
        return driverId;
    }

    public String getComment() {
        return comment;
    }

}
