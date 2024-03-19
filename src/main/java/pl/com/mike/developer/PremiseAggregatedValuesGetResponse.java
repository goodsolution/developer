package pl.com.mike.developer;

import pl.com.mike.developer.domain.developer.AggregatedValues;

import java.math.BigDecimal;

public class PremiseAggregatedValuesGetResponse implements AggregatedValues {
    private final BigDecimal minPrice;
    private final BigDecimal maxPrice;

    public PremiseAggregatedValuesGetResponse(BigDecimal minPrice, BigDecimal maxPrice) {
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
