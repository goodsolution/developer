package pl.com.goodsolution.adviser.api.adviser;

public class ContextRequest {
    private String context;
    private String customerId;
    private String appId;
    private String secret;
    private String lang;

    public ContextRequest() {
    }

    public ContextRequest(String context, String customerId, String appId, String secret, String lang) {
        this.context = context;
        this.customerId = customerId;
        this.appId = appId;
        this.secret = secret;
        this.lang = lang;
    }

    public String getContext() {
        return context;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getAppId() {
        return appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getLang() {
        return lang;
    }

    @Override
    public String toString() {
        return "ContextRequest{" +
                "context='" + context + '\'' +
                ", customerId='" + customerId + '\'' +
                ", appId='" + appId + '\'' +
                ", secret='" + secret + '\'' +
                ", lang='" + lang + '\'' +
                '}';
    }
}
