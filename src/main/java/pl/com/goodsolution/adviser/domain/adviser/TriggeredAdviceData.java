package pl.com.goodsolution.adviser.domain.adviser;

import java.time.LocalDateTime;

public class TriggeredAdviceData {
    private Long id;
    private Long adviceId;
    private String appId;
    private String context;
    private String contextId;
    private String content;
    private String title;
    private AdviseType type;
    private String lang;
    private AdviseDataType dataType;
    private TriggeredAdviceStatus status;
    private LocalDateTime triggerDateTime;
    private String variableName;
    private String responseValue;
    private LocalDateTime responseDateTime;
    private LocalDateTime removeDateTime;
    private Long customerId;
    private String executionCondition;
    private String comboJson;

    public TriggeredAdviceData(Long id, Long adviceId, String appId, String context, String contextId, String content, String title, AdviseType type, String lang, AdviseDataType dataType, TriggeredAdviceStatus status, String variableName, String comboJson, String responseValue) {
        this.id = id;
        this.adviceId = adviceId;
        this.appId = appId;
        this.context = context;
        this.contextId = contextId;
        this.content = content;
        this.title = title;
        this.type = type;
        this.lang = lang;
        this.dataType = dataType;
        this.status = status;
        this.variableName = variableName;
        this.comboJson = comboJson;
        this.responseValue = responseValue;
    }

    private TriggeredAdviceData(Long adviceId, String appId, String context, String contextId, String content, String title, AdviseType type, String lang, AdviseDataType dataType, TriggeredAdviceStatus status, String variableName, Long customerId, String executionCondition, String comboJson) {
        this.adviceId = adviceId;
        this.appId = appId;
        this.context = context;
        this.contextId = contextId;
        this.content = content;
        this.title = title;
        this.type = type;
        this.lang = lang;
        this.dataType = dataType;
        this.status = status;
        this.variableName = variableName;
        this.customerId = customerId;
        this.executionCondition = executionCondition;
        this.comboJson = comboJson;
    }

    private TriggeredAdviceData(Long id, Long adviceId, String appId, String context, String contextId, String content, String title, AdviseType type, String lang, AdviseDataType dataType, TriggeredAdviceStatus status, String variableName, Long customerId, String executionCondition, String comboJson, String responseValue) {
        this.id = id;
        this.adviceId = adviceId;
        this.appId = appId;
        this.context = context;
        this.contextId = contextId;
        this.content = content;
        this.title = title;
        this.type = type;
        this.lang = lang;
        this.dataType = dataType;
        this.status = status;
        this.variableName = variableName;
        this.customerId = customerId;
        this.executionCondition = executionCondition;
        this.comboJson = comboJson;
        this.responseValue = responseValue;
    }

    public static TriggeredAdviceData of(AdviceData data, Long customerId) {
        return new TriggeredAdviceData(
                data.getId(), data.getAppId(), data.getContext(), null, data.getContent(), data.getTitle(), data.getType(), null, null, TriggeredAdviceStatus.TRIGGERED, data.getVariableName(),
                // Long adviceId, String appId, String context, String contextId, String content, String title, AdviseType type, String lang, String dataType, String status, String variableName
                customerId, data.getExecutionCondition(), data.getComboJson() == null ? null :data.getComboJson().toComboJsonColumnValue());
    }

    public static TriggeredAdviceData of(TriggeredAdviceData data, TriggeredAdviceStatus status, String responseValue, AdviseDataType adviseDataType) {
        return new TriggeredAdviceData(
                data.getId(), data.getAdviceId(), data.getAppId(), data.getContext(), null, data.getContent(), data.getTitle(), data.getType(), null, adviseDataType, status, data.getVariableName(),
                data.getCustomerId(), data.getExecutionCondition(), data.getComboJson(), responseValue);
    }

    private void setFields(TriggeredAdviceData data) {
        this.id = data.getId();
        this.adviceId = data.getAdviceId();
        this.appId = data.getAppId();
        this.context = data.getContext();
        this.contextId = data.getContextId();
        this.content = data.getContent();
        this.type = data.getType();
        this.lang = data.getLang();
        this.dataType = data.getDataType();
        //this.status = data.getStatus();
        this.triggerDateTime = data.getTriggerDateTime();
        this.responseValue = data.getResponseValue();
        this.responseDateTime = data.getResponseDateTime();
    }

    public TriggeredAdviceData(Long id, TriggeredAdviceData data) {
        setFields(data);
        this.id = id;
    }

    public TriggeredAdviceData(LocalDateTime removeDateTime, TriggeredAdviceData data) {
        setFields(data);
        this.removeDateTime = removeDateTime;
    }


    public Long getId() {
        return id;
    }

    public Long getAdviceId() {
        return adviceId;
    }

    public String getAppId() {
        return appId;
    }

    public String getContext() {
        return context;
    }

    public String getContextId() {
        return contextId;
    }

    public String getContent() {
        return content;
    }

    public AdviseType getType() {
        return type;
    }

    public String getLang() {
        return lang;
    }

    public AdviseDataType getDataType() {
        return dataType;
    }

    public TriggeredAdviceStatus getStatus() {
        return status;
    }

    public LocalDateTime getTriggerDateTime() {
        return triggerDateTime;
    }

    public String getResponseValue() {
        return responseValue;
    }

    public LocalDateTime getResponseDateTime() {
        return responseDateTime;
    }

    public LocalDateTime getRemoveDateTime() {
        return removeDateTime;
    }

    public String getTitle() {
        return title;
    }

    public String getVariableName() {
        return variableName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getExecutionCondition() {
        return executionCondition;
    }

    public String getComboJson() {
        return comboJson;
    }



    @Override
    public String toString() {
        return "TriggeredAdviceData{" +
                "id=" + id +
                ", adviceId=" + adviceId +
                ", appId='" + appId + '\'' +
                ", domain='" + context + '\'' +
                ", domainId='" + contextId + '\'' +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", lang='" + lang + '\'' +
                ", dataType='" + dataType + '\'' +
                ", status='" + status + '\'' +
                ", triggerDateTime=" + triggerDateTime +
                ", variableName='" + variableName + '\'' +
                ", responseValue='" + responseValue + '\'' +
                ", responseDateTime=" + responseDateTime +
                ", removeDateTime=" + removeDateTime +
                '}';
    }


}
