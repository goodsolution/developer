package pl.com.goodsolution.adviser.api.courseplatform;

import pl.com.goodsolution.adviser.domain.courseplatform.CourseOrderData;

public class CourseOrderAdminGetResponse {
    private Long id;
    private String number;
    private CustomerGetResponse customer;
    private String purchaseDate;
    private String totalPrice;
    private String status;
    private String invoiceType;
    private String invoiceFirstAndLastName;
    private String invoiceCompanyName;
    private String invoiceStreet;
    private String invoicePostalCode;
    private String invoiceCity;
    private String invoiceNip;
    private Boolean invoiceIssued;

    public CourseOrderAdminGetResponse(CourseOrderData data) {
        this.id = data.getId();
        this.number = data.getNumber();
        this.customer = new CustomerGetResponse(data.getCustomer());
        this.purchaseDate = data.getPurchaseDate().toString();
        this.totalPrice = data.getTotalPrice().toString();
        this.status = data.getStatus();
        this.invoiceType = data.getInvoiceType();
        this.invoiceFirstAndLastName = data.getInvoiceFirstAndLastName();
        this.invoiceCompanyName = data.getInvoiceCompanyName();
        this.invoiceStreet = data.getInvoiceStreet();
        this.invoicePostalCode = data.getInvoicePostalCode();
        this.invoiceCity = data.getInvoiceCity();
        this.invoiceNip = data.getInvoiceNip();
        this.invoiceIssued = data.getInvoiceIssued();
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public CustomerGetResponse getCustomer() {
        return customer;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getTotalPrice() {
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
}
