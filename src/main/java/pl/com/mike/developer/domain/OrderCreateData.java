package pl.com.mike.developer.domain;

import pl.com.mike.developer.logic.Language;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class OrderCreateData {
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
    private Integer daysLeft;
    private Long orderStatusId;
    private String orderStatusName;
    private String discountName;
    private Boolean w; //TODO what is it, change name
    private String paymentStyle;
    private String daysLeftStyle;
    private String orderStatusStyle;
    private Boolean nextOrder;
    private String nextOrderIndicator;
    private String wIndicator;
    private Boolean receipt;
    private Boolean invoice;
    private String invoiceIndicator;

    private String customerFirstName;
    private String customerLastName;
    private String customerType;
    private String userOrderId;
    private Boolean group;
    private Long driverId;
    private String comment;
    private Boolean exceptionDriver;
    private Language lang;
    private String ip;
    private String adminComment;

    private OrderCustomerData customerData;
    private OrderWeekendAddressData orderWeekendAddress;
    private OrderInvoiceData orderInvoice;
    private PaymentMethodData paymentMethod;
    private DeliveryData deliveryMethod;

    private BigDecimal basketSum;
    private BigDecimal basketSumNo;

    private LocalTime weekHourOf;
    private LocalTime weekHourTo;

    private LocalTime weekendHourOf;
    private LocalTime weekendHourTo;
    private String buyFrom;
    private Boolean anotherHoursWeekend;
    private BigDecimal cityDiscount;



    public OrderCreateData(String customerFirstName, String customerLastName, String customerType, String userOrderId, Boolean group, Long driverId) {
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerType = customerType;
        this.userOrderId = userOrderId;
        this.group = group;
        this.driverId = driverId;
    }

    //TODO
    public OrderCreateData(String customerType, LocalDateTime purchaseDate, Long deliveryMethodId, Long paymentMethodId, Long driverId, Long orderStatusId, String userOrderId, Boolean group, String comment, Boolean exceptionDriver, Language lang, String ip, OrderCustomerData customerData, OrderWeekendAddressData orderWeekendAddress, OrderInvoiceData orderInvoice, PaymentMethodData paymentMethod, DeliveryData deliveryMethod, BigDecimal basketSum, BigDecimal basketSumNo, LocalTime weekHourOf, LocalTime weekHourTo, LocalTime weekendHourOf, LocalTime weekendHourTo, String buyFrom, Boolean anotherHoursWeekend, BigDecimal cityDiscount) {
        this.customerType = customerType;
        this.orderStatusId = orderStatusId;
        this.userOrderId = userOrderId;
        this.purchaseDate = purchaseDate;
        this.deliveryMethodId = deliveryMethodId;
        this.paymentMethodId = paymentMethodId;
        this.driverId = driverId;
        this.customerType = customerType;
        this.group = group;
        this.comment = comment;
        this.exceptionDriver = exceptionDriver;
        this.lang = lang;
        this.ip = ip;
        this.customerData = customerData;
        this.orderWeekendAddress = orderWeekendAddress;
        this.orderInvoice = orderInvoice;
        this.paymentMethod = paymentMethod;
        this.deliveryMethod = deliveryMethod;
        this.basketSum = basketSum;
        this.basketSumNo = basketSumNo;
        this.weekHourOf = weekHourOf;
        this.weekHourTo = weekHourTo;
        this.weekendHourOf = weekendHourOf;
        this.weekendHourTo = weekendHourTo;
        this.buyFrom = buyFrom;
        this.anotherHoursWeekend = anotherHoursWeekend;
        this.cityDiscount = cityDiscount;
    }

    public OrderCreateData(Long no, Long id, String firstName, String lastName, String orderId, LocalDateTime purchaseDate, BigDecimal value, Long deliveryMethodId, String deliveryMethodName, Long paymentMethodId, String paymentMethodName, Long paymentStatusId, String paymentStatusName, Integer daysLeft, Long orderStatusId, String orderStatusName, String discountName, Boolean w, String paymentStyle, String daysLeftStyle, String orderStatusStyle, Boolean nextOrder, String nextOrderIndicator, String wIndicator, Boolean receipt, Boolean invoice, String invoiceIndicator) {
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
        this.daysLeft = daysLeft;
        this.orderStatusId = orderStatusId;
        this.orderStatusName = orderStatusName;
        this.discountName = discountName;
        this.w = w;
        this.paymentStyle = paymentStyle;
        this.daysLeftStyle = daysLeftStyle;
        this.orderStatusStyle = orderStatusStyle;
        this.nextOrder = nextOrder;
        this.nextOrderIndicator = nextOrderIndicator;
        this.wIndicator = wIndicator;
        this.receipt = receipt;
        this.invoice = invoice;
        this.invoiceIndicator = invoiceIndicator;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public String getUserOrderId() {
        return userOrderId;
    }

    public String getCustomerType() {
        return customerType;
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

    public Integer getDaysLeft() {
        return daysLeft;
    }

    public Long getOrderStatusId() {
        return orderStatusId;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public String getDiscountName() {
        return discountName;
    }

    public Boolean getW() {
        return w;
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

    public String getwIndicator() {
        return wIndicator;
    }

    public Boolean getReceipt() {
        return receipt;
    }

    public Boolean getInvoice() {
        return invoice;
    }

    public String getInvoiceIndicator() {
        return invoiceIndicator;
    }

    public Boolean getGroup() {
        return group;
    }

    public Long getDriverId() {
        return driverId;
    }

    public String getComment() {
        return comment;
    }

    public Boolean getExceptionDriver() {
        return exceptionDriver;
    }

    public Language getLang() {
        return lang;
    }

    public OrderCustomerData getCustomerData() {
        return customerData;
    }

    public static OrderCreateData fakeOrder() {
        return new OrderCreateData(1L,  1L, "Paweł", "Kowalik", "md0001", LocalDateTime.now(), BigDecimal.valueOf(23.55), 1L, "Odbór osobisty", 1L, "Gotówka", 1L, "Opłacone", 12, 1L, "Zrealizowane", "family", Boolean.FALSE, "", "", "", true, "Tak", "Tak", Boolean.TRUE, Boolean.TRUE, "Tak");
    }

    public OrderWeekendAddressData getOrderWeekendAddress() {
        return orderWeekendAddress;
    }

    public OrderInvoiceData getOrderInvoice() {
        return orderInvoice;
    }

    public BigDecimal getBasketSum() {
        return basketSum;
    }

    public BigDecimal getBasketSumNo() {
        return basketSumNo;
    }

    public String getIp() {
        return ip;
    }

    public PaymentMethodData getPaymentMethod() {
        return paymentMethod;
    }

    public DeliveryData getDeliveryMethod() {
        return deliveryMethod;
    }

    public LocalTime getWeekHourOf() {
        return weekHourOf;
    }

    public LocalTime getWeekHourTo() {
        return weekHourTo;
    }

    public LocalTime getWeekendHourOf() {
        return weekendHourOf;
    }

    public LocalTime getWeekendHourTo() {
        return weekendHourTo;
    }

    public String getBuyFrom() {
        return buyFrom;
    }

    public Boolean getAnotherHoursWeekend() {
        return anotherHoursWeekend;
    }

    public BigDecimal getCityDiscount() {
        return cityDiscount;
    }

    public String getAdminComment() {
        return adminComment;
    }
}
