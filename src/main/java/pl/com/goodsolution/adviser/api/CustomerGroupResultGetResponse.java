package pl.com.goodsolution.adviser.api;

import java.util.List;

public class CustomerGroupResultGetResponse {

    private Long quantity;
    private List<CustomerGroupGetResult> groups;

    public CustomerGroupResultGetResponse(Long quantity, List<CustomerGroupGetResult> groups) {
        this.quantity = quantity;
        this.groups = groups;
    }

    public Long getQuantity() {
        return quantity;
    }

    public List<CustomerGroupGetResult> getGroups() {
        return groups;
    }
}
