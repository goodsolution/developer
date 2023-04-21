package pl.com.mike.developer.domain.courseplatform;

public class CustomerToGroupData {
    private Long id;
    private Long customerId;
    private Long customerGroupId;

    public CustomerToGroupData(Long customerGroupId) {
        this.customerGroupId = customerGroupId;
    }

    public CustomerToGroupData(Long customerId, Long customerGroupId) {
        this.customerId = customerId;
        this.customerGroupId = customerGroupId;
    }

    public CustomerToGroupData(Long id, Long customerId, Long customerGroupId) {
        this.id = id;
        this.customerId = customerId;
        this.customerGroupId = customerGroupId;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getCustomerGroupId() {
        return customerGroupId;
    }

}
