package pl.com.goodsolution.adviser.domain.courseplatform;

import pl.com.goodsolution.adviser.logic.courseplatform.CustomerToGroupService;
import pl.com.goodsolution.adviser.logic.courseplatform.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class CustomerData {

    private Long id;
    private String login;
    private String passwordHash;
    private String language;
    private Boolean regulationAccepted;
    private Boolean newsletterAccepted;
    private String invoiceType;
    private String invoiceFirstAndLastName;
    private String invoiceCompanyName;
    private String invoiceStreet;
    private String invoicePostalCode;
    private String invoiceCity;
    private String invoiceNip;
    private String invoiceCountry;
    private Boolean isEnabled;
    private AuthorData author;
    private LocalDateTime registrationDatetime;
    private String registrationIp;
    private String registrationUserAgent;
    private Boolean emailConfirmed;
    private LocalDateTime deleteDatetime;
    private String[] authorities;
    private List<CustomerToGroupData> groups;
    private String authorFirstName;
    private String authorLastName;

    public CustomerData(Long id, String login, String passwordHash, String language, Boolean regulationAccepted, Boolean newsletterAccepted, String invoiceType, String invoiceFirstAndLastName, String invoiceCompanyName, String invoiceStreet, String invoicePostalCode, String invoiceCity, String invoiceNip, String invoiceCountry, Boolean isEnabled, AuthorData author, LocalDateTime registrationDatetime, String registrationIp, String registrationUserAgent, Boolean emailConfirmed) {
        this.id = id;
        this.login = login;
        this.passwordHash = passwordHash;
        this.language = language;
        this.regulationAccepted = regulationAccepted;
        this.newsletterAccepted = newsletterAccepted;
        this.invoiceType = invoiceType;
        this.invoiceFirstAndLastName = invoiceFirstAndLastName;
        this.invoiceCompanyName = invoiceCompanyName;
        this.invoiceStreet = invoiceStreet;
        this.invoicePostalCode = invoicePostalCode;
        this.invoiceCity = invoiceCity;
        this.invoiceNip = invoiceNip;
        this.invoiceCountry = invoiceCountry;
        this.isEnabled = isEnabled;
        this.author = author;
        this.registrationDatetime = registrationDatetime;
        this.registrationIp = registrationIp;
        this.registrationUserAgent = registrationUserAgent;
        this.emailConfirmed = emailConfirmed;
    }

    public CustomerData(String login, String passwordHash, String language, Boolean regulationAccepted, Boolean newsletterAccepted, Boolean isEnabled, LocalDateTime registrationDatetime, String registrationIp, String registrationUserAgent, Boolean emailConfirmed) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.language = language;
        this.regulationAccepted = regulationAccepted;
        this.newsletterAccepted = newsletterAccepted;
        this.isEnabled = isEnabled;
        this.registrationDatetime = registrationDatetime;
        this.registrationIp = registrationIp;
        this.registrationUserAgent = registrationUserAgent;
        this.emailConfirmed = emailConfirmed;
    }

    public CustomerData(String login, String passwordHash, String language, Boolean regulationAccepted, Boolean newsletterAccepted, Boolean isEnabled, String invoiceFirstAndLastName, LocalDateTime registrationDatetime, String registrationIp, String registrationUserAgent, Boolean emailConfirmed) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.language = language;
        this.regulationAccepted = regulationAccepted;
        this.newsletterAccepted = newsletterAccepted;
        this.isEnabled = isEnabled;
        this.invoiceFirstAndLastName = invoiceFirstAndLastName;
        this.registrationDatetime = registrationDatetime;
        this.registrationIp = registrationIp;
        this.registrationUserAgent = registrationUserAgent;
        this.emailConfirmed = emailConfirmed;
    }

    public CustomerData(String login, String passwordHash, String language, Boolean regulationAccepted, Boolean newsletterAccepted, String invoiceFirstAndLastName, Boolean isEnabled, Boolean emailConfirmed, String[] authorities, HttpServletRequest httpServletRequest, List<CustomerToGroupData> groups) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.language = language;
        this.regulationAccepted = regulationAccepted;
        this.newsletterAccepted = newsletterAccepted;
        this.isEnabled = isEnabled;
        this.invoiceFirstAndLastName = invoiceFirstAndLastName;
        this.registrationDatetime = LocalDateTime.now();
        this.emailConfirmed = emailConfirmed;
        this.authorities= authorities;
        this.registrationIp = WebUtil.getClientIp(httpServletRequest);
        this.registrationUserAgent = WebUtil.getUserAgent(httpServletRequest);
        this.groups = groups;
    }

    public CustomerData(Long id, String login, String passwordHash, String language, String invoiceType, String invoiceCompanyName, String invoiceStreet, String invoicePostalCode, String invoiceCity, String invoiceNip, String invoiceCountry, Boolean regulationAccepted, Boolean newsletterAccepted, String invoiceFirstAndLastName, Boolean isEnabled, Boolean emailConfirmed, LocalDateTime registrationDatetime, String[] authorities, List<CustomerToGroupData> groups) {
        this.id = id;
        this.login = login;
        this.passwordHash = passwordHash;
        this.language = language;
        this.regulationAccepted = regulationAccepted;
        this.newsletterAccepted = newsletterAccepted;
        this.isEnabled = isEnabled;
        this.invoiceFirstAndLastName = invoiceFirstAndLastName;
        this.invoiceType = invoiceType;
        this.invoiceCompanyName = invoiceCompanyName;
        this.invoiceStreet = invoiceStreet;
        this.invoicePostalCode = invoicePostalCode;
        this.invoiceCity = invoiceCity;
        this.invoiceNip = invoiceNip;
        this.invoiceCountry = invoiceCountry;
        this.emailConfirmed = emailConfirmed;
        this.registrationDatetime = registrationDatetime;
        this.authorities = authorities;
        this.groups = groups;
    }

    public CustomerData(String login, String passwordHash, String language, Boolean regulationAccepted, Boolean newsletterAccepted, Boolean isEnabled, Boolean emailConfirmed, HttpServletRequest httpServletRequest, String[] authorities) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.language = language;
        this.regulationAccepted = regulationAccepted;
        this.newsletterAccepted = newsletterAccepted;
        this.registrationDatetime = LocalDateTime.now();
        this.isEnabled = isEnabled;
        this.emailConfirmed = emailConfirmed;
        this.registrationIp = WebUtil.getClientIp(httpServletRequest);
        this.registrationUserAgent = WebUtil.getUserAgent(httpServletRequest);
        this.authorities = authorities;
    }

    public CustomerData(String login, String passwordHash, String language, Boolean regulationAccepted, Boolean newsletterAccepted, String authorFirstName, String authorLastName, HttpServletRequest httpServletRequest, String[] authorities) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.language = language;
        this.regulationAccepted = regulationAccepted;
        this.newsletterAccepted = newsletterAccepted;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.registrationDatetime = LocalDateTime.now();
        this.registrationIp = WebUtil.getClientIp(httpServletRequest);
        this.registrationUserAgent = WebUtil.getUserAgent(httpServletRequest);
        this.authorities = authorities;
    }

    public CustomerData(CustomerData data, String passwordHash) {
        this.id = data.getId();
        this.login = data.getLogin();
        this.passwordHash = passwordHash;
        this.language = data.getLanguage();
        this.regulationAccepted = data.getRegulationAccepted();
        this.newsletterAccepted = data.getNewsletterAccepted();
        this.invoiceType = data.getInvoiceType();
        this.invoiceFirstAndLastName = data.getInvoiceFirstAndLastName();
        this.invoiceCompanyName = data.getInvoiceCompanyName();
        this.invoiceStreet = data.getInvoiceStreet();
        this.invoicePostalCode = data.getInvoicePostalCode();
        this.invoiceCity = data.getInvoiceCity();
        this.invoiceNip = data.getInvoiceNip();
        this.invoiceCountry = data.getInvoiceCountry();
        this.isEnabled = data.getEnabled();
        this.author = data.getAuthor();
        this.registrationDatetime = data.getRegistrationDatetime();
        this.registrationIp = data.getRegistrationIp();
        this.registrationUserAgent = data.getRegistrationUserAgent();
        this.emailConfirmed = data.getEmailConfirmed();
    }

    public CustomerData(CustomerData data, String[] authorities, List<CustomerToGroupData> groups) {
        this.id = data.getId();
        this.login = data.getLogin();
        this.passwordHash = data.getPasswordHash();
        this.language = data.getLanguage();
        this.regulationAccepted = data.getRegulationAccepted();
        this.newsletterAccepted = data.getNewsletterAccepted();
        this.invoiceType = data.getInvoiceType();
        this.invoiceFirstAndLastName = data.getInvoiceFirstAndLastName();
        this.invoiceCompanyName = data.getInvoiceCompanyName();
        this.invoiceStreet = data.getInvoiceStreet();
        this.invoicePostalCode = data.getInvoicePostalCode();
        this.invoiceCity = data.getInvoiceCity();
        this.invoiceNip = data.getInvoiceNip();
        this.invoiceCountry = data.getInvoiceCountry();
        this.isEnabled = data.getEnabled();
        this.author = data.getAuthor();
        this.registrationDatetime = data.getRegistrationDatetime();
        this.registrationIp = data.getRegistrationIp();
        this.registrationUserAgent = data.getRegistrationUserAgent();
        this.emailConfirmed = data.getEmailConfirmed();
        this.authorities = authorities;
        this.groups = groups;
    }

    public CustomerData(CustomerData data, Billing billing) {
        this.id = data.getId();
        this.login = data.getLogin();
        this.passwordHash = data.getPasswordHash();
        this.language = data.getLanguage();
        this.regulationAccepted = data.getRegulationAccepted();
        this.newsletterAccepted = data.getNewsletterAccepted();
        this.invoiceType = billing.getType();
        this.invoiceFirstAndLastName = billing.getFirstAndLastName();
        this.invoiceCompanyName = billing.getCompanyName();
        this.invoiceStreet = billing.getStreet();
        this.invoicePostalCode = billing.getPostalCode();
        this.invoiceCity = billing.getCity();
        this.invoiceNip = billing.getNip();
        this.invoiceCountry = billing.getCountry();
        this.isEnabled = data.getEnabled();
        this.author = data.getAuthor();
        this.registrationDatetime = data.getRegistrationDatetime();
        this.registrationIp = data.getRegistrationIp();
        this.registrationUserAgent = data.getRegistrationUserAgent();
        this.emailConfirmed = data.getEmailConfirmed();
    }

    public CustomerData(CustomerData data, String language, Boolean newsletterAccepted) {
        this.id = data.getId();
        this.login = data.getLogin();
        this.passwordHash = data.getPasswordHash();
        this.language = language;
        this.regulationAccepted = data.getRegulationAccepted();
        this.newsletterAccepted = newsletterAccepted;
        this.invoiceType = data.getInvoiceType();
        this.invoiceFirstAndLastName = data.getInvoiceFirstAndLastName();
        this.invoiceCompanyName = data.getInvoiceCompanyName();
        this.invoiceStreet = data.getInvoiceStreet();
        this.invoicePostalCode = data.getInvoicePostalCode();
        this.invoiceCity = data.getInvoiceCity();
        this.invoiceNip = data.getInvoiceNip();
        this.invoiceCountry = data.getInvoiceCountry();
        this.isEnabled = data.getEnabled();
        this.author = data.getAuthor();
        this.registrationDatetime = data.getRegistrationDatetime();
        this.registrationIp = data.getRegistrationIp();
        this.registrationUserAgent = data.getRegistrationUserAgent();
        this.emailConfirmed = data.getEmailConfirmed();
        this.authorities = data.getAuthorities();
    }

    public CustomerData(CustomerData data, AuthorData author) {
        this.id = data.getId();
        this.login = data.getLogin();
        this.passwordHash = data.getPasswordHash();
        this.language = data.getLanguage();
        this.regulationAccepted = data.getRegulationAccepted();
        this.newsletterAccepted = data.getNewsletterAccepted();
        this.invoiceType = data.getInvoiceType();
        this.invoiceFirstAndLastName = data.getInvoiceFirstAndLastName();
        this.invoiceCompanyName = data.getInvoiceCompanyName();
        this.invoiceStreet = data.getInvoiceStreet();
        this.invoicePostalCode = data.getInvoicePostalCode();
        this.invoiceCity = data.getInvoiceCity();
        this.invoiceNip = data.getInvoiceNip();
        this.invoiceCountry = data.getInvoiceCountry();
        this.isEnabled = data.getEnabled();
        this.author = author;
        this.registrationDatetime = data.getRegistrationDatetime();
        this.registrationIp = data.getRegistrationIp();
        this.registrationUserAgent = data.getRegistrationUserAgent();
        this.emailConfirmed = data.getEmailConfirmed();
        this.authorities = data.getAuthorities();
    }

    public CustomerData(CustomerData data, String registrationIp, String registrationUserAgent) {
        this.id = data.getId();
        this.login = data.getLogin();
        this.passwordHash = data.getPasswordHash();
        this.language = data.getLanguage();
        this.regulationAccepted = data.getRegulationAccepted();
        this.newsletterAccepted = data.getNewsletterAccepted();
        this.invoiceType = data.getInvoiceType();
        this.invoiceFirstAndLastName = data.getInvoiceFirstAndLastName();
        this.invoiceCompanyName = data.getInvoiceCompanyName();
        this.invoiceStreet = data.getInvoiceStreet();
        this.invoicePostalCode = data.getInvoicePostalCode();
        this.invoiceCity = data.getInvoiceCity();
        this.invoiceNip = data.getInvoiceNip();
        this.invoiceCountry = data.getInvoiceCountry();
        this.isEnabled = data.getEnabled();
        this.author = data.getAuthor();
        this.registrationDatetime = data.getRegistrationDatetime();
        this.registrationIp = registrationIp;
        this.registrationUserAgent = registrationUserAgent;
        this.emailConfirmed = data.getEmailConfirmed();
    }

    public CustomerData(CustomerData data, Boolean emailConfirmed) {
        this.id = data.getId();
        this.login = data.getLogin();
        this.passwordHash = data.getPasswordHash();
        this.language = data.getLanguage();
        this.regulationAccepted = data.getRegulationAccepted();
        this.newsletterAccepted = data.getNewsletterAccepted();
        this.invoiceType = data.getInvoiceType();
        this.invoiceFirstAndLastName = data.getInvoiceFirstAndLastName();
        this.invoiceCompanyName = data.getInvoiceCompanyName();
        this.invoiceStreet = data.getInvoiceStreet();
        this.invoicePostalCode = data.getInvoicePostalCode();
        this.invoiceCity = data.getInvoiceCity();
        this.invoiceNip = data.getInvoiceNip();
        this.invoiceCountry = data.getInvoiceCountry();
        this.isEnabled = data.getEnabled();
        this.author = data.getAuthor();
        this.registrationDatetime = data.getRegistrationDatetime();
        this.registrationIp = data.getRegistrationIp();
        this.registrationUserAgent = data.getRegistrationUserAgent();
        this.emailConfirmed = emailConfirmed;
    }

    public CustomerData(Long id, String login, String passwordHash, String language, Boolean regulationAccepted, Boolean newsletterAccepted, String invoiceFirstAndLastName, Boolean isEnabled, LocalDateTime registrationDatetime) {
        this.id = id;
        this.login = login;
        this.passwordHash = passwordHash;
        this.language = language;
        this.regulationAccepted = regulationAccepted;
        this.newsletterAccepted = newsletterAccepted;
        this.invoiceFirstAndLastName = invoiceFirstAndLastName;
        this.isEnabled = isEnabled;
        this.registrationDatetime = registrationDatetime;
    }

    public CustomerData(CustomerData data, LocalDateTime deleteDatetime) {
        this.id = data.getId();
        this.login = data.getLogin();
        this.passwordHash = data.getPasswordHash();
        this.language = data.getLanguage();
        this.regulationAccepted = data.getRegulationAccepted();
        this.newsletterAccepted = data.getNewsletterAccepted();
        this.invoiceType = data.getInvoiceType();
        this.invoiceFirstAndLastName = data.getInvoiceFirstAndLastName();
        this.invoiceCompanyName = data.getInvoiceCompanyName();
        this.invoiceStreet = data.getInvoiceStreet();
        this.invoicePostalCode = data.getInvoicePostalCode();
        this.invoiceCity = data.getInvoiceCity();
        this.invoiceNip = data.getInvoiceNip();
        this.invoiceCountry = data.getInvoiceCountry();
        this.isEnabled = data.getEnabled();
        this.author = data.getAuthor();
        this.emailConfirmed = data.getEmailConfirmed();
        this.registrationDatetime = data.getRegistrationDatetime();
        this.registrationIp = data.getRegistrationIp();
        this.registrationUserAgent = data.getRegistrationUserAgent();
        this.deleteDatetime = deleteDatetime;
    }

    public Long getId() {
        return id;
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

    public Boolean getEnabled() {
        return isEnabled;
    }

    public AuthorData getAuthor() {
        return author;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public LocalDateTime getRegistrationDatetime() {
        return registrationDatetime;
    }

    public String getRegistrationIp() {
        return registrationIp;
    }

    public String getRegistrationUserAgent() {
        return registrationUserAgent;
    }

    public Boolean getEmailConfirmed() {
        return emailConfirmed;
    }

    public LocalDateTime getDeleteDatetime() {
        return deleteDatetime;
    }

    public String[] getAuthorities() {
        return authorities;
    }

    public List<CustomerToGroupData> getGroups() {
        return groups;
    }

    @Override
    public String toString() {
        return "CustomerData{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", language='" + language + '\'' +
                ", regulationAccepted=" + regulationAccepted +
                ", newsletterAccepted=" + newsletterAccepted +
                ", invoiceType='" + invoiceType + '\'' +
                ", invoiceFirstAndLastName='" + invoiceFirstAndLastName + '\'' +
                ", invoiceCompanyName='" + invoiceCompanyName + '\'' +
                ", invoiceStreet='" + invoiceStreet + '\'' +
                ", invoicePostalCode='" + invoicePostalCode + '\'' +
                ", invoiceCity='" + invoiceCity + '\'' +
                ", invoiceNip='" + invoiceNip + '\'' +
                ", invoiceCountry='" + invoiceCountry + '\'' +
                ", isEnabled=" + isEnabled +
                ", author=" + author +
                ", registrationDatetime=" + registrationDatetime +
                ", registrationIp='" + registrationIp + '\'' +
                ", registrationUserAgent='" + registrationUserAgent + '\'' +
                ", emailConfirmed=" + emailConfirmed +
                ", deleteDatetime=" + deleteDatetime +
                ", authorities=" + Arrays.toString(authorities) +
                ", authorFirstName='" + authorFirstName + '\'' +
                ", authorLastName='" + authorLastName + '\'' +
                '}';
    }
}
