package pl.com.mike.developer.domain;

import java.math.BigDecimal;

public class SaleTableData {
    private Long id;
    private BigDecimal discount;

    public SaleTableData(Long id, BigDecimal discount) {
        this.id = id;
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getDiscount() {
        return discount;
    }
}
