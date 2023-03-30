package pl.com.mike.developer.api.adviser;

import java.util.List;

public class AdvicesRequestGetResponse {
    private Long totalAdvicesCount;
    private List<AdviceGetResponse> advices;

    public AdvicesRequestGetResponse(Long totalAdvicesCount, List<AdviceGetResponse> advices) {
        this.totalAdvicesCount = totalAdvicesCount;
        this.advices = advices;
    }

    public Long getTotalAdvicesCount() {
        return totalAdvicesCount;
    }

    public List<AdviceGetResponse> getAdvices() {
        return advices;
    }
}
