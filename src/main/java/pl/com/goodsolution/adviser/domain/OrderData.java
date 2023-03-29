package pl.com.goodsolution.adviser.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderData {
    private Long no;
    private Long id;
    private String firstName;
    private String lastName;
    private String orderId;
    private LocalDateTime purchaseDate;
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
    private String statusChangeSource;



    public OrderData(Long no, Long id, String firstName, String lastName, String orderId, LocalDateTime purchaseDate, BigDecimal value, Long deliveryMethodId, String deliveryMethodName, Long paymentMethodId, String paymentMethodName, Long paymentStatusId, String paymentStatusName, String paymentStatusCode, Integer daysLeft, Long orderStatusId, String orderStatusName, String orderStatusCode, String discountName, Boolean demandingCustomer, String paymentStyle, String daysLeftStyle, String orderStatusStyle, Boolean nextOrder, String nextOrderIndicator, String demandingCustomerIndicator, Boolean receipt, Boolean invoiceWanted, Boolean invoiceIssued, Long customerId, String statusChangeSource) {
        this.no = no;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.orderId = orderId;
        this.purchaseDate = purchaseDate;
        this.value = value;
        this.deliveryMethodId = deliveryMethodId;
        this.deliveryMethodName = deliveryMethodName;
        this.paymentMethodId = paymentMethodId;
        this.paymentMethodName = paymentMethodName;
        this.paymentStatusId = paymentStatusId;
        this.paymentStatusName = paymentStatusName;
        this.paymentStatusCode = paymentStatusCode;
        this.daysLeft = daysLeft;
        this.orderStatusId = orderStatusId;
        this.orderStatusName = orderStatusName;
        this.orderStatusCode = orderStatusCode;
        this.discountName = discountName;
        this.demandingCustomer = demandingCustomer;
        this.paymentStyle = paymentStyle;
        this.daysLeftStyle = daysLeftStyle;
        this.orderStatusStyle = orderStatusStyle;
        this.nextOrder = nextOrder;
        this.nextOrderIndicator = nextOrderIndicator;
        this.demandingCustomerIndicator = demandingCustomerIndicator;
        this.receipt = receipt;
        this.invoiceWanted = invoiceWanted;
        this.invoiceIssued = invoiceIssued;
        this.customerId = customerId;
        this.statusChangeSource = statusChangeSource;
    }

    public Long getNo() {
        return no;
    }

    public String getOrderId() {
        return orderId;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
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

    public String getStatusChangeSource() {
        return statusChangeSource;
    }
}
