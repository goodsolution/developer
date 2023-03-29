package pl.com.goodsolution.adviser.api.adviser;

import java.util.List;

public class TriggeredAdvicesRequestGetResponse {
    private Long count;
    private List<TriggeredAdviceGetResponse> triggeredAdvices;

    public TriggeredAdvicesRequestGetResponse(Long count, List<TriggeredAdviceGetResponse> triggeredAdvices) {
        this.count = count;
        this.triggeredAdvices = triggeredAdvices;
    }

    public Long getCount() {
        return count;
    }

    public List<TriggeredAdviceGetResponse> getTriggeredAdvices() {
        return triggeredAdvices;
    }
}
