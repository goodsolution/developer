package pl.com.mike.developer.api.adviser;

public class AdvicePutRequest {
    private String appId;
    private String domain;
    private String content;
    private String type;
    private String scope;
    private String action;
    private String dataType;
    private String component;
    private String adviceClass;
    private String name;

    public AdvicePutRequest() {
    }

    public String getAppId() {
        return appId;
    }

    public String getDomain() {
        return domain;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public String getScope() {
        return scope;
    }

    public String getAction() {
        return action;
    }

    public String getDataType() {
        return dataType;
    }

    public String getComponent() {
        return component;
    }

    public String getAdviceClass() {
        return adviceClass;
    }

    public String getName() {
        return name;
    }
}
