package pl.com.mike.developer.api.lifeadviser;

public class TriggeredAdviceDeleteRequest {
    private String applicationId;
    private String domain;
    private String domainId;
    private String secret;

    public TriggeredAdviceDeleteRequest() {
    }

    public TriggeredAdviceDeleteRequest(String applicationId, String domain, String domainId, String secret) {
        this.applicationId = applicationId;
        this.domain = domain;
        this.domainId = domainId;
        this.secret = secret;
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

}
