package pl.com.mike.developer.api.adviser;

public class AdvicePostRequest {
    private String appId;
    private String context;
    private String content;
    private String type;
    private String title;
    private String variableName;
    private String executionCondition;
    private String comboJson;
    private String status;

    public AdvicePostRequest() {
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

    public String getType() {
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

    public String getComboJson() {
        return comboJson;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "AdvicePostRequest{" +
                "appId='" + appId + '\'' +
                ", context='" + context + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", variableName='" + variableName + '\'' +
                ", executionCondition='" + executionCondition + '\'' +
                ", comboJson='" + comboJson + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
