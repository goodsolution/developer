package pl.com.mike.developer.api.adviser;

import pl.com.mike.developer.domain.adviser.AdviceComboData;

public class PullGetResponse {
    private Long id;
    private String title;
    private String content;
    private String variableName;
    private String type;
    private Long triggeredAdviceId;
    private AdviceComboData comboJson;

    public PullGetResponse(Long id, String title, String content, String variableName, String type, Long triggeredAdviceId, AdviceComboData comboJson) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.variableName = variableName;
        this.type = type;
        this.triggeredAdviceId = triggeredAdviceId;
        this.comboJson = comboJson;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getVariableName() {
        return variableName;
    }

    public String getType() {
        return type;
    }

    public Long getTriggeredAdviceId() {
        return triggeredAdviceId;
    }

    public AdviceComboData getComboJson() {
        return comboJson;
    }
}
