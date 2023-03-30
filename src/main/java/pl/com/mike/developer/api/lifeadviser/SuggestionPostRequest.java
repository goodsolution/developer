package pl.com.mike.developer.api.lifeadviser;

public class SuggestionPostRequest {

    private String suggestion;
    private String applicationId;
    private String domain;
    private String domainId;
    private String secret;

    public SuggestionPostRequest() {
    }

    public SuggestionPostRequest(String suggestion, String applicationId, String domain, String domainId, String secret) {
        this.suggestion = suggestion;
        this.applicationId = applicationId;
        this.domain = domain;
        this.domainId = domainId;
        this.secret = secret;
    }

    public String getSuggestion() {
        return suggestion;
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
