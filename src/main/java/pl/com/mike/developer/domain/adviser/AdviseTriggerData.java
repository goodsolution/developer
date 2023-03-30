package pl.com.mike.developer.domain.adviser;

public class AdviseTriggerData {
   private String id;
   private String domain;
   private String appId;
   private AdviseScope scope;
   private String action;
   private String secret;

    public AdviseTriggerData(String id, String domain, String appId, AdviseScope scope, String action, String secret) {
        this.id = id;
        this.domain = domain;
        this.appId = appId;
        this.scope = scope;
        this.action = action;
        this.secret = secret;
    }

    public String getId() {
        return id;
    }

    public String getDomain() {
        return domain;
    }

    public AdviseScope getScope() {
        return scope;
    }

    public String getAction() {
        return action;
    }

    public String getAppId() {
        return appId;
    }

    public String getSecret() {
        return secret;
    }
}
