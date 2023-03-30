package pl.com.mike.developer.api.lifeadviser;

public class TriggeredAdviceGetResponse {

    private Long id;
    private Long adviceId;
    private String appId;
    private String domain;
    private String domainId;
    private String content;
    private String type;
    private String lang;
    private String scope;
    private String action;
    private String dataType;
    private String component;
    private Long score;
    private String status;
    private String triggerDateTime;
    private String name;

    public TriggeredAdviceGetResponse(Long id, String content, Long score, String name) {
        this.id = id;
        this.content = content;
        this.score = score;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAppId() {
        return appId;
    }

    public String getDomain() {
        return domain;
    }

    public String getTriggerDateTime() {
        return triggerDateTime;
    }

    public Long getAdviceId() {
        return adviceId;
    }

    public String getDomainId() {
        return domainId;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public String getLang() {
        return lang;
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

    public Long getScore() {
        return score;
    }

    public String getStatus() {
        return status;
    }
}
