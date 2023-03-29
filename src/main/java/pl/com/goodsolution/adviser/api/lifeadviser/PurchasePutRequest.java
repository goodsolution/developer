package pl.com.goodsolution.adviser.api.lifeadviser;

public class PurchasePutRequest {
    private String externalTransactionId;
    private String domainId;
    private String applicationId;
    private String domain;
    private String secret;

    public PurchasePutRequest() {
    }

    public PurchasePutRequest(String externalTransactionId, String domainId, String applicationId, String domain, String secret) {
        this.externalTransactionId = externalTransactionId;
        this.domainId = domainId;
        this.applicationId = applicationId;
        this.domain = domain;
        this.secret = secret;
    }

    public String getExternalTransactionId() {
        return externalTransactionId;
    }

    public String getDomainId() {
        return domainId;
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
}
