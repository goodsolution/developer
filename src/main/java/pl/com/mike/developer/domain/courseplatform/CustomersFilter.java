package pl.com.mike.developer.domain.courseplatform;

import pl.com.mike.developer.logic.courseplatform.Language;

public class CustomersFilter {

    private Long id;
    private String login;
    private Boolean newsletterAccepted;
    private Boolean isEnabled;
    private Long limit;
    private Boolean emailConfirmed;
    private Language language;
    private Long page;
    private Long pageSize;
    private String invoiceFirstAndLastName;
    private String loginLike;


    public CustomersFilter(String loginLike, Long page, Long pageSize, String invoiceFirstAndLastName) {
        this.loginLike = loginLike;
        this.page = page;
        this.pageSize = pageSize;
        this.invoiceFirstAndLastName = invoiceFirstAndLastName;
    }

    public CustomersFilter(String login) {
        this.login = login;
    }

    public CustomersFilter(Long id) {
        this.id = id;
    }

    public CustomersFilter(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public CustomersFilter(Boolean newsletterAccepted, Boolean isEnabled, Long limit, Boolean emailConfirmed) {
        this.newsletterAccepted = newsletterAccepted;
        this.isEnabled = isEnabled;
        this.limit = limit;
        this.emailConfirmed = emailConfirmed;
    }

    public CustomersFilter(Language language) {
        this.language = language;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public Boolean getNewsletterAccepted() {
        return newsletterAccepted;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public Long getLimit() {
        return limit;
    }

    public Boolean getEmailConfirmed() {
        return emailConfirmed;
    }

    public Language getLanguage() {
        return language;
    }

    public Long getPage() {
        return page;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public String getInvoiceFirstAndLastName() {
        return invoiceFirstAndLastName;
    }

    public String getLoginLike() {
        return loginLike;
    }
}
