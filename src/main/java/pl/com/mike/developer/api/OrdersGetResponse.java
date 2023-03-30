package pl.com.mike.developer.api;

public class OrdersGetResponse {
    private Long id;
    private Long no;
    private String orderNumber;
    private String firstName;
    private String lastName;
    private String purchaseDateTime;
    private String value;
    private String currency;
    private Long deliveryMethodId;
    private String deliveryMethodName;
    private Long paymentMethodId;
    private String paymentMethodName;
    private Long paymentStatusId;
    private String paymentStatusName;
    private String paymentStatusCode;
    private Integer daysLeft;
    private String nextOrderIndicator;
    private Long orderStatusId;
    private String orderStatusName;
    private String orderStatusCode;
    private String discountCode;
    private Boolean invoiceWanted;
    private Boolean invoiceIssued;
    private Boolean receipt;
    private Boolean demandingCustomer;
    private Long customerId;
    private String paymentStyle;
    private String daysLeftStyle;
    private String orderStatusStyle;
    private String statusChangeSource;


    public OrdersGetResponse(Long id, Long no, String orderNumber, String firstName, String lastName, String purchaseDateTime, String value, String currency, Long deliveryMethodId, String deliveryMethodName, Long paymentMethodId, String paymentMethodName, Long paymentStatusId, String paymentStatusName, String paymentStatusCode, Integer daysLeft, String nextOrderIndicator, Long orderStatusId, String orderStatusName, String orderStatusCode, String discountCode, Boolean invoiceWanted, Boolean invoiceIssued, Boolean receipt, Boolean demandingCustomer, Long customerId, String paymentStyle, String daysLeftStyle, String orderStatusStyle, String statusChangeSource) {
        this.id = id;
        this.no = no;
        this.orderNumber = orderNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.purchaseDateTime = purchaseDateTime;
        this.value = value;
        this.currency = currency;
        this.deliveryMethodId = deliveryMethodId;
        this.deliveryMethodName = deliveryMethodName;
        this.paymentMethodId = paymentMethodId;
        this.paymentMethodName = paymentMethodName;
        this.paymentStatusId = paymentStatusId;
        this.paymentStatusName = paymentStatusName;
        this.paymentStatusCode = paymentStatusCode;
        this.daysLeft = daysLeft;
        this.nextOrderIndicator = nextOrderIndicator;
        this.orderStatusId = orderStatusId;
        this.orderStatusName = orderStatusName;
        this.orderStatusCode = orderStatusCode;
        this.discountCode = discountCode;
        this.invoiceWanted = invoiceWanted;
        this.invoiceIssued = invoiceIssued;
        this.receipt = receipt;
        this.demandingCustomer = demandingCustomer;
        this.customerId = customerId;
        this.paymentStyle = paymentStyle;
        this.daysLeftStyle = daysLeftStyle;
        this.orderStatusStyle = orderStatusStyle;
        this.statusChangeSource = statusChangeSource;
    }


    public Long getId() {
        return id;
    }

    public Long getNo() {
        return no;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPurchaseDateTime() {
        return purchaseDateTime;
    }

    public String getValue() {
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

    public String getNextOrderIndicator() {
        return nextOrderIndicator;
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

    public String getDiscountCode() {
        return discountCode;
    }

    public Boolean getInvoiceWanted() {
        return invoiceWanted;
    }

    public Boolean getInvoiceIssued() {
        return invoiceIssued;
    }

    public Boolean getReceipt() {
        return receipt;
    }

    public Boolean getDemandingCustomer() {
        return demandingCustomer;
    }

    public Long getCustomerId() {
        return customerId;
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

    public String getStatusChangeSource() {
        return statusChangeSource;
    }
}
