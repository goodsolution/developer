package pl.com.mike.developer.domain;

import java.math.BigDecimal;

public class CategoryData {
    private Long id;
    private BigDecimal testSetPriceDiscount;

    public CategoryData(Long id, BigDecimal testSetPriceDiscount) {
        this.id = id;
        this.testSetPriceDiscount = testSetPriceDiscount;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getTestSetPriceDiscount() {
        return testSetPriceDiscount;
    }
}
