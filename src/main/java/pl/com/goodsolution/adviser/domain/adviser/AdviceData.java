package pl.com.goodsolution.adviser.domain.adviser;

import pl.com.goodsolution.adviser.api.adviser.AdvicePostRequest;

public class AdviceData {

    private Long id;
    private String appId;
    private String context;
    private String content;
    private AdviseType type;
    private String title;
    private String variableName;
    private String executionCondition;
    private AdviceComboData comboJson;
    private AdviceStatus status;
    private String responseExecutionCondition;
    private String responseTrueContent;
    private String responseElseContent;

    public AdviceData(Long id, String appId, String context, String content, AdviseType type, String title, String variableName, String executionCondition, AdviceComboData comboJson, AdviceStatus status, String responseExecutionCondition, String responseTrueContent, String responseElseContent) {
        this.id = id;
        this.appId = appId;
        this.context = context;
        this.content = content;
        this.type = type;
        this.title = title;
        this.variableName = variableName;
        this.executionCondition = executionCondition;
        this.comboJson = comboJson;
        this.status = status;
        this.responseExecutionCondition = responseExecutionCondition;
        this.responseTrueContent = responseTrueContent;
        this.responseElseContent = responseElseContent;
    }

    public AdviceData(String appId, String context, String content, AdviseType type, String title, String variableName, String executionCondition, AdviceComboData comboJson, AdviceStatus status, Long triggeredAdviceId) {
        this.appId = appId;
        this.context = context;
        this.content = content;
        this.type = type;
        this.title = title;
        this.variableName = variableName;
        this.executionCondition = executionCondition;
        this.comboJson = comboJson;
        this.status = status;
    }

    public static AdviceData of (AdvicePostRequest request) {
        return new AdviceData(
                request.getAppId(),
                request.getContext(),
                request.getContent(),
                AdviseType.fromCode(request.getType()),
                request.getTitle(),
                request.getVariableName(),
                request.getExecutionCondition(),
                null, //request.getComboJson(), TODO
                AdviceStatus.fromCode(request.getStatus()),
                1L//TODO
        );
    }

    public Long getId() {
        return id;
    }

    public String getAppId() {
        return appId;
    }

    public String getContext() {
        return context;
    }

    public String getContent() {
        return content;
    }

    public AdviseType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getVariableName() {
        return variableName;
    }

    public String getExecutionCondition() {
        return executionCondition;
    }

    public AdviceComboData getComboJson() {
        return comboJson;
    }

    public AdviceStatus getStatus() {
        return status;
    }

    public String getResponseExecutionCondition() {
        return responseExecutionCondition;
    }

    public String getResponseTrueContent() {
        return responseTrueContent;
    }

    public String getResponseElseContent() {
        return responseElseContent;
    }

    @Override
    public String toString() {
        return "AdviceData{" +
                "id=" + id +
                ", appId='" + appId + '\'' +
                ", context='" + context + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", variableName='" + variableName + '\'' +
                ", executionCondition='" + executionCondition + '\'' +
                ", comboJson='" + comboJson + '\'' +
                ", status=" + status +
                '}';
    }
}
