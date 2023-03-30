package pl.com.mike.developer.api.adviser;

import pl.com.mike.developer.domain.adviser.TriggeredAdviceData;

import java.util.List;

public class TriggeredAdviceGetResponse {

    private Long id;
    private Long adviceId;
    private String appId;
    private String domain;
    private String domainId;
    private String content;
    private String type;
    private String lang;
    private String dataType;
    private String status;
    private String triggerDateTime;
    private String title;
    private String responseValue;
    private List<AdviceAnswerOptionGetResponse> answerOptions;

    public TriggeredAdviceGetResponse(Long id, String title, String appId, String domain, String triggerDateTime, String content, String responseValue) {
        this.id = id;
        this.title = title;
        this.appId = appId;
        this.domain = domain;
        this.triggerDateTime = triggerDateTime;
        this.content = content;
        this.responseValue = responseValue;
    }

    public TriggeredAdviceGetResponse(TriggeredAdviceData data) {
        this.id = data.getId();
        this.adviceId = data.getAdviceId();
        this.appId = data.getAppId();
        this.domain = data.getContext();
        this.domainId = data.getContextId();
        this.content = data.getContent();
        this.type = data.getType().code();
        this.lang = data.getLang();
        this.dataType = data.getDataType().name();
        this.status = data.getStatus().code();
        this.triggerDateTime = data.getTriggerDateTime().toString();
        this.answerOptions = prepareAnswerOptions(data);
    }

    private List<AdviceAnswerOptionGetResponse> prepareAnswerOptions(TriggeredAdviceData data) {
//        if (data.getAnswerOptions() == null) {
//            return Collections.emptyList();
//        }
        return null;//data.getAnswerOptions().stream().map(o -> new AdviceAnswerOptionGetResponse(o.getName(), o.getValue())).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
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

    public String getDataType() {
        return dataType;
    }

    public String getStatus() {
        return status;
    }

    public List<AdviceAnswerOptionGetResponse> getAnswerOptions() {
        return answerOptions;
    }

    public String getResponseValue() {
        return responseValue;
    }
}
