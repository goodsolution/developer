package pl.com.mike.developer.domain;

import java.math.BigDecimal;

public class ProductDetailData {
    private String name;
    private Long quantity;
    private Long taxId;
    private BigDecimal netPriceRetail;
    private BigDecimal grossPriceRetail;
    private Long categoryId;

    public ProductDetailData(String name, Long quantity, Long taxId, BigDecimal netPriceRetail, BigDecimal grossPriceRetail, Long categoryId) {
        this.name = name;
        this.quantity = quantity;
        this.taxId = taxId;
        this.netPriceRetail = netPriceRetail;
        this.grossPriceRetail = grossPriceRetail;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Long getTaxId() {
        return taxId;
    }

    public BigDecimal getNetPriceRetail() {
        return netPriceRetail;
    }

    public BigDecimal getGrossPriceRetail() {
        return grossPriceRetail;
    }

    public Long getCategoryId() {
        return categoryId;
    }
}
