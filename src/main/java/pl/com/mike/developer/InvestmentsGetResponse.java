package pl.com.mike.developer;

import java.util.List;

public class InvestmentsGetResponse {
    private final List<InvestmentGetResponse> investments;

    public InvestmentsGetResponse(List<InvestmentGetResponse> investments) {
        this.investments = investments;
    }

    public List<InvestmentGetResponse> getInvestments() {
        return investments;
    }
}
