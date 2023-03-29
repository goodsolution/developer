package pl.com.goodsolution.adviser.api.lifeadviser;

public class OptionsPutRequest {
    private String applicationId;
    private String domain;
    private String domainId;
    private String secret;
    private Integer preferredHourFrom;
    private Integer preferredHourTo;
    private Boolean allDay;
    private Boolean newsletterAccepted;

    public OptionsPutRequest() {
    }

    public OptionsPutRequest(String applicationId, String domain, String domainId, String secret, Integer preferredHourFrom, Integer preferredHourTo, Boolean allDay, Boolean newsletterAccepted) {
        this.applicationId = applicationId;
        this.domain = domain;
        this.domainId = domainId;
        this.secret = secret;
        this.preferredHourFrom = preferredHourFrom;
        this.preferredHourTo = preferredHourTo;
        this.allDay = allDay;
        this.newsletterAccepted = newsletterAccepted;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getDomain() {
        return domain;
    }

    public String getDomainId() {
        return domainId;
    }

    public String getSecret() {
        return secret;
    }

    public Integer getPreferredHourFrom() {
        return preferredHourFrom;
    }

    public Integer getPreferredHourTo() {
        return preferredHourTo;
    }

    public Boolean getAllDay() {
        return allDay;
    }

    public Boolean getNewsletterAccepted() {
        return newsletterAccepted;
    }
}
