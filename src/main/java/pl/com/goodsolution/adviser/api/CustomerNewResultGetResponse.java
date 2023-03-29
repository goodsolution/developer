package pl.com.goodsolution.adviser.api;

import java.util.List;

public class CustomerNewResultGetResponse {
    private Long count;
    private List<CustomerNewGetResponse> newCustomers;

    public CustomerNewResultGetResponse(Long count, List<CustomerNewGetResponse> newCustomers) {
        this.count = count;
        this.newCustomers = newCustomers;
    }

    public Long getCount() {
        return count;
    }

    public List<CustomerNewGetResponse> getNewCustomers() {
        return newCustomers;
    }
}
