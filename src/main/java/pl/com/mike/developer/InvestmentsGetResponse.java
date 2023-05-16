package pl.com.mike.developer;

import java.util.List;

public class InvestmentsGetResponse {
    List<InvestmentGetResponse> investmentsGetResponse;

    public InvestmentsGetResponse(List<InvestmentGetResponse> investmentsGetResponse) {
        this.investmentsGetResponse = investmentsGetResponse;
    }

    public List<InvestmentGetResponse> getInvestmentsGetResponse() {
        return investmentsGetResponse;
    }

    public void setInvestmentsGetResponse(List<InvestmentGetResponse> investmentsGetResponse) {
        this.investmentsGetResponse = investmentsGetResponse;
    }


}
