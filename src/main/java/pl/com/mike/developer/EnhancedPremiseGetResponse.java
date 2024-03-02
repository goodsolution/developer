package pl.com.mike.developer;

import java.math.BigDecimal;

public class EnhancedPremiseGetResponse {
    private final BigDecimal minPrice;
    private final BigDecimal maxPrice;

    public EnhancedPremiseGetResponse(BigDecimal minPrice, BigDecimal maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }
}
