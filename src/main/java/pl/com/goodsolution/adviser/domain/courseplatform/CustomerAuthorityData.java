package pl.com.goodsolution.adviser.domain.courseplatform;

public class CustomerAuthorityData {
    private Long id;
    private CustomerData customer;
    private String authority;
    private String[] authorities;


    public CustomerAuthorityData(Long id, CustomerData customer, String authority) {
        this.id = id;
        this.customer = customer;
        this.authority = authority;
    }

    public CustomerAuthorityData(CustomerData customer, String[] authorities) {
        this.customer = customer;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public CustomerData getCustomer() {
        return customer;
    }

    public String getAuthority() {
        return authority;
    }

    public String[] getAuthorities() {
        return authorities;
    }
}

