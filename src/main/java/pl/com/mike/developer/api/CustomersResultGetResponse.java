package pl.com.mike.developer.api;

import java.util.List;

public class CustomersResultGetResponse {
    private Long count;
    private List<CustomersGetResponse> customers;

    public CustomersResultGetResponse(Long count, List<CustomersGetResponse> customers) {
        this.count = count;
        this.customers = customers;
    }

    public Long getCount() {
        return count;
    }

    public List<CustomersGetResponse> getCustomers() {
        return customers;
    }
}
