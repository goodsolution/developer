package pl.com.mike.developer.api.courseplatform;

import java.util.List;

public class CustomerPostRequest {

    private String login;
    private String passwordHash;
    private String language;
    private Boolean regulationAccepted;
    private Boolean newsletterAccepted;
    private String authorFirstName;
    private String authorLastName;
    private Boolean isEnabled;
    private String invoiceFirstAndLastName;
    private Boolean isEmailConfirmed;
    private String[] authorities;
    private List<Long> groupIds;


    public CustomerPostRequest() {
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

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public String getInvoiceFirstAndLastName() {
        return invoiceFirstAndLastName;
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
