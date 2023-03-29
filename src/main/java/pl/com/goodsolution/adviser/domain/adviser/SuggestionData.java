package pl.com.goodsolution.adviser.domain.adviser;

public class SuggestionData {
    private String suggestion;
    private String applicationId;
    private String domain;
    private String domainId;

    public SuggestionData(String suggestion, String applicationId, String domain, String domainId) {
        this.suggestion = suggestion;
        this.applicationId = applicationId;
        this.domain = domain;
        this.domainId = domainId;
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
}
