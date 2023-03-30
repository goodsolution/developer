package pl.com.mike.developer.domain.courseplatform;

import pl.com.mike.developer.logic.courseplatform.OrderStatus;
import pl.com.mike.developer.logic.courseplatform.PayuIntegrationOrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CourseOrderData {
    private Long id;
    private String number;
    private CustomerData customer;
    private LocalDateTime purchaseDate;
    private BigDecimal totalPrice;
    private String status;
    private String invoiceType;
    private String invoiceFirstAndLastName;
    private String invoiceCompanyName;
    private String invoiceStreet;
    private String invoicePostalCode;
    private String invoiceCity;
    private String invoiceNip;
    private Boolean invoiceIssued;
    private String payuOrderId;
    private String payuPaymentUrl;
    private String invoiceCountry;
    private String payuIntegrationStatus;

    public CourseOrderData(Long id, String number, CustomerData customer, LocalDateTime purchaseDate, BigDecimal totalPrice, String status, String invoiceType, String invoiceFirstAndLastName, String invoiceCompanyName, String invoiceStreet, String invoicePostalCode, String invoiceCity, String invoiceNip, Boolean invoiceIssued, String payuOrderId, String payuPaymentUrl, String invoiceCountry, String payuIntegrationStatus) {
        this.id = id;
        this.number = number;
        this.customer = customer;
        this.purchaseDate = purchaseDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.invoiceType = invoiceType;
        this.invoiceFirstAndLastName = invoiceFirstAndLastName;
        this.invoiceCompanyName = invoiceCompanyName;
        this.invoiceStreet = invoiceStreet;
        this.invoicePostalCode = invoicePostalCode;
        this.invoiceCity = invoiceCity;
        this.invoiceNip = invoiceNip;
        this.invoiceIssued = invoiceIssued;
        this.payuOrderId = payuOrderId;
        this.payuPaymentUrl = payuPaymentUrl;
        this.invoiceCountry = invoiceCountry;
        this.payuIntegrationStatus = payuIntegrationStatus;
    }

    public CourseOrderData(String number, CustomerData customer, LocalDateTime purchaseDate, BigDecimal totalPrice, String status, Billing billing, String payuIntegrationStatus) {
        this.number = number;
        this.customer = customer;
        this.purchaseDate = purchaseDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.invoiceType = billing.getType();
        this.invoiceFirstAndLastName = billing.getFirstAndLastName();
        this.invoiceCompanyName = billing.getCompanyName();
        this.invoiceStreet = billing.getStreet();
        this.invoicePostalCode = billing.getPostalCode();
        this.invoiceCity = billing.getCity();
        this.invoiceNip = billing.getNip();
        this.invoiceCountry = billing.getCountry();
        this.payuIntegrationStatus = payuIntegrationStatus;
    }

    public CourseOrderData(CourseOrderData data, OrderStatus orderStatus) {
        this.id = data.getId();
        this.number = data.getNumber();
        this.customer = data.getCustomer();
        this.purchaseDate = data.getPurchaseDate();
        this.totalPrice = data.getTotalPrice();
        this.status = orderStatus.getValue();
        this.invoiceType = data.getInvoiceType();
        this.invoiceFirstAndLastName = data.getInvoiceFirstAndLastName();
        this.invoiceCompanyName = data.getInvoiceCompanyName();
        this.invoiceStreet = data.getInvoiceStreet();
        this.invoicePostalCode = data.getInvoicePostalCode();
        this.invoiceCity = data.getInvoiceCity();
        this.invoiceNip = data.getInvoiceNip();
        this.invoiceIssued = data.getInvoiceIssued();
        this.payuOrderId = data.getPayuOrderId();
        this.payuPaymentUrl = data.getPayuPaymentUrl();
        this.invoiceCountry = data.getInvoiceCountry();
        this.payuIntegrationStatus = data.getPayuIntegrationStatus();

    }

    public CourseOrderData(CourseOrderData data, PayuIntegrationOrderStatus payuIntegrationOrderStatus) {
        this.id = data.getId();
        this.number = data.getNumber();
        this.customer = data.getCustomer();
        this.purchaseDate = data.getPurchaseDate();
        this.totalPrice = data.getTotalPrice();
        this.status = data.getStatus();
        this.invoiceType = data.getInvoiceType();
        this.invoiceFirstAndLastName = data.getInvoiceFirstAndLastName();
        this.invoiceCompanyName = data.getInvoiceCompanyName();
        this.invoiceStreet = data.getInvoiceStreet();
        this.invoicePostalCode = data.getInvoicePostalCode();
        this.invoiceCity = data.getInvoiceCity();
        this.invoiceNip = data.getInvoiceNip();
        this.invoiceIssued = data.getInvoiceIssued();
        this.payuOrderId = data.getPayuOrderId();
        this.payuPaymentUrl = data.getPayuPaymentUrl();
        this.invoiceCountry = data.getInvoiceCountry();
        this.payuIntegrationStatus = payuIntegrationOrderStatus.getStatus();

    }

    public CourseOrderData(CourseOrderData data, String payuOrderId, String payuPaymentUrl) {
        this.id = data.getId();
        this.number = data.getNumber();
        this.customer = data.getCustomer();
        this.purchaseDate = data.getPurchaseDate();
        this.totalPrice = data.getTotalPrice();
        this.status = data.getStatus();
        this.invoiceType = data.getInvoiceType();
        this.invoiceFirstAndLastName = data.getInvoiceFirstAndLastName();
        this.invoiceCompanyName = data.getInvoiceCompanyName();
        this.invoiceStreet = data.getInvoiceStreet();
        this.invoicePostalCode = data.getInvoicePostalCode();
        this.invoiceCity = data.getInvoiceCity();
        this.invoiceNip = data.getInvoiceNip();
        this.invoiceIssued = data.getInvoiceIssued();
        this.payuOrderId = payuOrderId;
        this.payuPaymentUrl = payuPaymentUrl;
        this.invoiceCountry = data.getInvoiceCountry();
        this.payuIntegrationStatus = data.getPayuIntegrationStatus();
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public CustomerData getCustomer() {
        return customer;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public String getInvoiceFirstAndLastName() {
        return invoiceFirstAndLastName;
    }

    public String getInvoiceCompanyName() {
        return invoiceCompanyName;
    }

    public String getInvoiceStreet() {
        return invoiceStreet;
    }

    public String getInvoicePostalCode() {
        return invoicePostalCode;
    }

    public String getInvoiceCity() {
        return invoiceCity;
    }

    public String getInvoiceNip() {
        return invoiceNip;
    }

    public Boolean getInvoiceIssued() {
        return invoiceIssued;
    }

    public String getPayuOrderId() {
        return payuOrderId;
    }

    public String getPayuPaymentUrl() {
        return payuPaymentUrl;
    }

    public String getInvoiceCountry() {
        return invoiceCountry;
    }

    public String getPayuIntegrationStatus() {
        return payuIntegrationStatus;
    }
}
