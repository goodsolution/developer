package pl.com.goodsolution.adviser.domain.courseplatform;



import java.time.LocalDateTime;

public class TokenData {
    private Long id;
    private String value;
    private LocalDateTime expirationDatetime;
    private CustomerData customer;

    public TokenData(Long id, String value, LocalDateTime expirationDatetime, CustomerData customer) {
        this.id = id;
        this.value = value;
        this.expirationDatetime = expirationDatetime;
        this.customer = customer;
    }

    public TokenData(String value, LocalDateTime expirationDatetime, CustomerData customer) {
        this.value = value;
        this.expirationDatetime = expirationDatetime;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public LocalDateTime getExpirationDatetime() {
        return expirationDatetime;
    }

    public CustomerData getCustomer() {
        return customer;
    }
}
