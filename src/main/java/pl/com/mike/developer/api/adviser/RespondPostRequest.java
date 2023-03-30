package pl.com.mike.developer.api.adviser;

import pl.com.mike.developer.domain.adviser.AdviceResponseData;
import pl.com.mike.developer.domain.adviser.AdviseDataType;
import pl.com.mike.developer.domain.adviser.TriggeredAdviceStatus;

public class RespondPostRequest {
    private ContextRequest context;
    private String triggeredAdviceId;
    private String answer;
    private String dataType;
    private String status;

    public RespondPostRequest() {
    }

    public RespondPostRequest(ContextRequest context, String triggeredAdviceId, String answer, String dataType, String status) {
        this.context = context;
        this.triggeredAdviceId = triggeredAdviceId;
        this.answer = answer;
        this.dataType = dataType;
        this.status = status;
    }

    public ContextRequest getContext() {
        return context;
    }

    public String getAnswer() {
        return answer;
    }


    public String getTriggeredAdviceId() {
        return triggeredAdviceId;
    }

    public String getDataType() {
        return dataType;
    }

    public String getStatus() {
        return status;
    }

    AdviceResponseData toAdviceResponseData() {
        return new AdviceResponseData(this.getContext(), Long.valueOf(this.triggeredAdviceId), this.answer, TriggeredAdviceStatus.fromCode(status), this.dataType == null ? null : AdviseDataType.valueOf(this.dataType));
    }

    @Override
    public String toString() {
        return "RespondPostRequest{" +
                "context=" + context +
                ", triggeredAdviceId='" + triggeredAdviceId + '\'' +
                ", answer='" + answer + '\'' +
                ", dataType='" + dataType + '\'' +
                '}';
    }
}
