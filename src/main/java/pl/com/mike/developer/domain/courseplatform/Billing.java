package pl.com.mike.developer.domain.courseplatform;

import pl.com.mike.developer.api.courseplatform.OrderCoursesInBasketPostRequest;

public class Billing {
    private String type;
    private String firstAndLastName;
    private String companyName;
    private String street;
    private String postalCode;
    private String city;
    private String nip;
    private String country;

    public Billing(OrderCoursesInBasketPostRequest request) {
        this.type = request.getInvoiceType();
        this.firstAndLastName = request.getInvoiceFirstAndLastName();
        this.companyName = request.getInvoiceCompanyName();
        this.street = request.getInvoiceStreet();
        this.postalCode = request.getInvoicePostalCode();
        this.city = request.getInvoiceCity();
        this.nip = request.getInvoiceNip();
        this.country = request.getInvoiceCountry();
    }

    public Billing() {
    }

    public String getType() {
        return type;
    }

    public String getFirstAndLastName() {
        return firstAndLastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getStreet() {
        return street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getNip() {
        return nip;
    }

    public String getCountry() {
        return country;
    }
}
