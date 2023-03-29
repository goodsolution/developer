package pl.com.goodsolution.adviser.domain.adviser;

import pl.com.goodsolution.adviser.api.adviser.ContextRequest;

public class AdviceResponseData {
    private ContextRequest context;
    private Long triggeredAdviceId;
    private String answer;
    private TriggeredAdviceStatus status;
    private AdviseDataType adviseDataType;

    public AdviceResponseData() {
    }

    public AdviceResponseData(ContextRequest context, Long triggeredAdviceId, String answer, TriggeredAdviceStatus status, AdviseDataType adviseDataType) {
        this.context = context;
        this.triggeredAdviceId = triggeredAdviceId;
        this.answer = answer;
        this.status = status;
        this.adviseDataType = adviseDataType;
    }

    public ContextRequest getContext() {
        return context;
    }

    public TriggeredAdviceStatus getStatus() {
        return status;
    }

    public String getAnswer() {
        return answer;
    }


    public Long getTriggeredAdviceId() {
        return triggeredAdviceId;
    }

    public AdviseDataType getAdviseDataType() {
        return adviseDataType;
    }

    @Override
    public String toString() {
        return "AdviceResponseData{" +
                "context=" + context +
                ", triggeredAdviceId=" + triggeredAdviceId +
                ", answer='" + answer + '\'' +
                ", status=" + status +
                ", adviseDataType=" + adviseDataType +
                '}';
    }
}
