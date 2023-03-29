package pl.com.goodsolution.adviser.api.courseplatform;

import pl.com.goodsolution.adviser.domain.courseplatform.CustomerData;
import pl.com.goodsolution.adviser.domain.courseplatform.CustomerToGroupData;

import java.util.List;

public class CustomerGetResponse {
    private Long id;
    private String login;
    private String language;
    private String invoiceType;
    private String invoiceFirstAndLastName;
    private String invoiceCompanyName;
    private String invoiceStreet;
    private String invoicePostalCode;
    private String invoiceCity;
    private String invoiceNip;
    private String invoiceCountry;
    private Boolean newsletterAccepted;
    private Boolean regulationAccepted;
    private String passwordHash;
    private String registrationDatetime;
    private Boolean isEnabled;
    private String registrationIp;
    private String userAgent;
    private Boolean emailConfirmed;
    private String[] authorities;
    private List<CustomerToGroupData> groups;

    public CustomerGetResponse(CustomerData data) {
        this.id = data.getId();
        this.login = data.getLogin();
        this.language = data.getLanguage();
        this.invoiceType = data.getInvoiceType();
        this.invoiceFirstAndLastName = (data.getInvoiceFirstAndLastName() != null) ? data.getInvoiceFirstAndLastName() : "";
        this.invoiceCompanyName = data.getInvoiceCompanyName();
        this.invoiceStreet = data.getInvoiceStreet();
        this.invoicePostalCode = data.getInvoicePostalCode();
        this.invoiceCity = data.getInvoiceCity();
        this.invoiceNip = data.getInvoiceNip();
        this.invoiceCountry = data.getInvoiceCountry();
        this.newsletterAccepted = data.getNewsletterAccepted();
        this.regulationAccepted = data.getRegulationAccepted();
        this.passwordHash = data.getPasswordHash();
        this.registrationDatetime = data.getRegistrationDatetime().toString().replace("T", " ");
        this.isEnabled = data.getEnabled();
        this.registrationIp = data.getRegistrationIp();
        this.userAgent = data.getRegistrationUserAgent();
        this.emailConfirmed = data.getEmailConfirmed();
        this.authorities = data.getAuthorities();
        this.groups = data.getGroups();
    }


    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getLanguage() {
        return language;
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

    public Boolean getNewsletterAccepted() {
        return newsletterAccepted;
    }

    public Boolean getRegulationAccepted() {
        return regulationAccepted;
    }
    public String getPasswordHash() {
        return passwordHash;
    }

    public String getRegistrationDatetime() {
        return registrationDatetime;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public String getRegistrationIp() {
        return registrationIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Boolean getEmailConfirmed() {
        return emailConfirmed;
    }

    public String[] getAuthorities() {
        return authorities;
    }

    public List<CustomerToGroupData> getGroups() {
        return groups;
    }
}
