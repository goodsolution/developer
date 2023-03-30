package pl.com.mike.developer.domain.adviser;

public class AdviceVariablesFilter {
    private Long customerId;

    public AdviceVariablesFilter(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }
}
