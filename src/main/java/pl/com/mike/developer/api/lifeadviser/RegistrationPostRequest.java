package pl.com.mike.developer.api.lifeadviser;

public class RegistrationPostRequest {

    private String login;
    private String passwordHash;
    private String applicationId;
    private String domain;
    private String secret;
    private Boolean newsletterAccepted;
    private Boolean regulationsAccepted;

    public RegistrationPostRequest() {
    }

    public RegistrationPostRequest(String login, String passwordHash, String applicationId, String domain, String secret, Boolean newsletterAccepted, Boolean regulationsAccepted) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.applicationId = applicationId;
        this.domain = domain;
        this.secret = secret;
        this.newsletterAccepted = newsletterAccepted;
        this.regulationsAccepted = regulationsAccepted;
    }

    public String getLogin() {
        return login;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getDomain() {
        return domain;
    }


    public String getSecret() {
        return secret;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Boolean getNewsletterAccepted() {
        return newsletterAccepted;
    }

    public Boolean getRegulationsAccepted() {
        return regulationsAccepted;
    }
}
