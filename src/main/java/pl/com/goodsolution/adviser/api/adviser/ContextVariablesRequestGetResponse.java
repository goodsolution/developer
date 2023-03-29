package pl.com.goodsolution.adviser.api.adviser;

import java.util.List;

public class ContextVariablesRequestGetResponse {
    private final List<ContextVariableGetResponse> ContextVariables;
    private final Long totalContextVariablesCount;

    public ContextVariablesRequestGetResponse(List<ContextVariableGetResponse> contextVariables, Long totalContextVariablesCount) {
        ContextVariables = contextVariables;
        this.totalContextVariablesCount = totalContextVariablesCount;
    }

    public List<ContextVariableGetResponse> getContextVariables() {
        return ContextVariables;
    }

    public Long getTotalContextVariablesCount() {
        return totalContextVariablesCount;
    }
}
