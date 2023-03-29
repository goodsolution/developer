package pl.com.goodsolution.adviser.domain.courseplatform;

public class CustomerAuthoritiesFilter {
    private Long customerId;
    private String authority;

    public CustomerAuthoritiesFilter(Long customerId, String authority) {
        this.customerId = customerId;
        this.authority = authority;
    }

    public CustomerAuthoritiesFilter(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getAuthority() {
        return authority;
    }
}
