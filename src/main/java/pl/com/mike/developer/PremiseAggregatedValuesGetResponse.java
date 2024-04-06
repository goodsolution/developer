package pl.com.mike.developer;

import pl.com.mike.developer.domain.developer.AggregatedValues;

import java.math.BigDecimal;

public class PremiseAggregatedValuesGetResponse implements AggregatedValues {
    private final BigDecimal minPrice;
    private final BigDecimal maxPrice;

    public PremiseAggregatedValuesGetResponse(AggregatedValues values) {
        this.minPrice = values.getMinPrice();
        this.maxPrice = values.getMaxPrice();
    }

    @Override
    public BigDecimal getMinPrice() {
        return minPrice;
    }

    @Override
    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

}
