package pl.com.mike.developer.domain.adviser;

public class AdvisePullData {
    private String context;
    private Long customerId;
    private String appId;
    private String lang;
    private String country;
    private String secret;

    public AdvisePullData(String context, Long customerId, String appId, String lang, String country, String secret) {
        this.context = context;
        this.customerId = customerId;
        this.appId = appId;
        this.lang = lang;
        this.country = country;
        this.secret = secret;
    }

    public String getContext() {
        return context;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getAppId() {
        return appId;
    }

    public String getLang() {
        return lang;
    }

    public String getCountry() {
        return country;
    }

    public String getSecret() {
        return secret;
    }
}
