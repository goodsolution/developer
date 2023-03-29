package pl.com.goodsolution.adviser.api.adviser;

import java.util.List;

public class ContextConfigsRequestGetResponse {
    private final List<ContextConfigGetResponse> contextConfigs;
    private final Long totalContextConfigsCount;

    public ContextConfigsRequestGetResponse(List<ContextConfigGetResponse> contextConfigs, Long totalContextConfigsCount) {
        this.contextConfigs = contextConfigs;
        this.totalContextConfigsCount = totalContextConfigsCount;
    }

    public List<ContextConfigGetResponse> getContextConfigs() {
        return contextConfigs;
    }

    public Long getTotalContextConfigsCount() {
        return totalContextConfigsCount;
    }
}
