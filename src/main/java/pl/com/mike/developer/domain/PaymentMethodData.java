package pl.com.mike.developer.domain;

import java.math.BigDecimal;

public class PaymentMethodData {
    private Long id;
    private String name;
    private String operator;
    private BigDecimal fee;
    private String status;

    public PaymentMethodData(Long id, String name, String operator, BigDecimal fee, String status) {
        this.id = id;
        this.name = name;
        this.operator = operator;
        this.fee = fee;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOperator() {
        return operator;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public String getStatus() {
        return status;
    }
}
