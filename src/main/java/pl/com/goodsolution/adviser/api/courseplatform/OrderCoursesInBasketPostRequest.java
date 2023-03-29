package pl.com.goodsolution.adviser.api.courseplatform;

public class OrderCoursesInBasketPostRequest {
    private String invoiceType;
    private String invoiceFirstAndLastName;
    private String invoiceCompanyName;
    private String invoiceStreet;
    private String invoicePostalCode;
    private String invoiceCity;
    private String invoiceNip;
    private String invoiceCountry;

    public OrderCoursesInBasketPostRequest() {
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

    public String getInvoiceCountry() {
        return invoiceCountry;
    }
}
