package pl.com.mike.developer.api.courseplatform;

import java.util.List;

public class CustomerPutRequest {

    private String login;
    private String passwordHash;
    private String language;
    private Boolean regulationAccepted;
    private Boolean newsletterAccepted;
    private Boolean isEnabled;
    private String invoiceFirstAndLastName;
    private String invoiceType;
    private String invoiceCompanyName;
    private String invoiceStreet;
    private String invoicePostalCode;
    private String invoiceCity;
    private String invoiceNip;
    private String invoiceCountry;
    private String registrationDatetime;
    private Boolean isEmailConfirmed;
    private String[] authorities;
    private List<Long> groupIds;

    public CustomerPutRequest() {
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getLanguage() {
        return language;
    }

    public Boolean getRegulationAccepted() {
        return regulationAccepted;
    }

    public Boolean getNewsletterAccepted() {
        return newsletterAccepted;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public String getInvoiceFirstAndLastName() {
        return invoiceFirstAndLastName;
    }

    public String getInvoiceType() {
        return invoiceType;
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

    public String getRegistrationDatetime() {
        return registrationDatetime;
    }

    public Boolean getIsEmailConfirmed() {
        return isEmailConfirmed;
    }

    public String[] getAuthorities() {
        return authorities;
    }

    public List<Long> getGroupIds() {
        return groupIds;
    }
}
