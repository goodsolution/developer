package pl.com.goodsolution.adviser.domain.courseplatform;

import java.time.LocalDateTime;

public class PasswordResetTokenData {
    private Long id;
    private CustomerData customer;
    private String value;
    private LocalDateTime expirationDatetime;
    private Boolean used;

    public PasswordResetTokenData(Long id, CustomerData customer, String value, LocalDateTime expirationDatetime, Boolean used) {
        this.id = id;
        this.customer = customer;
        this.value = value;
        this.expirationDatetime = expirationDatetime;
        this.used = used;
    }

    public PasswordResetTokenData(CustomerData customer, String value, LocalDateTime expirationDatetime, Boolean used) {
        this.customer = customer;
        this.value = value;
        this.expirationDatetime = expirationDatetime;
        this.used = used;
    }

    public PasswordResetTokenData(PasswordResetTokenData data, Boolean used) {
        this.id = data.getId();
        this.customer = data.getCustomer();
        this.value = data.getValue();
        this.expirationDatetime = data.getExpirationDatetime();
        this.used = used;
    }

    public Long getId() {
        return id;
    }

    public CustomerData getCustomer() {
        return customer;
    }

    public String getValue() {
        return value;
    }

    public LocalDateTime getExpirationDatetime() {
        return expirationDatetime;
    }

    public Boolean getUsed() {
        return used;
    }
}
