package pl.com.goodsolution.adviser.api.courseplatform;

import java.util.List;

public class CustomerGroupsRequestGetResponse {
    private List<CustomerGroupGetResponse> customerGroups;

    public CustomerGroupsRequestGetResponse(List<CustomerGroupGetResponse> customerGroups) {
        this.customerGroups = customerGroups;
    }

    public List<CustomerGroupGetResponse> getCustomerGroups() {
        return customerGroups;
    }
}
