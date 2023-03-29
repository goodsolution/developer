package pl.com.goodsolution.adviser.domain;

import pl.com.goodsolution.adviser.logic.DiscountType;

import java.math.BigDecimal;

public class AppliedDiscountData {
    private Long id;
    private Long orderId;
    private DiscountType discountType;
    private BigDecimal discountValue;
    private BigDecimal discountValueTotal;
    private BigDecimal grossPriceBefore;
    private BigDecimal grossPriceAfter;

    public AppliedDiscountData(Long orderId, DiscountType discountType, BigDecimal discountValue, BigDecimal discountValueTotal, BigDecimal grossPriceBefore, BigDecimal grossPriceAfter) {
        this.orderId = orderId;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.discountValueTotal = discountValueTotal;
        this.grossPriceBefore = grossPriceBefore;
        this.grossPriceAfter = grossPriceAfter;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public BigDecimal getDiscountValueTotal() {
        return discountValueTotal;
    }

    public BigDecimal getGrossPriceBefore() {
        return grossPriceBefore;
    }

    public BigDecimal getGrossPriceAfter() {
        return grossPriceAfter;
    }
}
