package pl.com.mike.developer;

import pl.com.mike.developer.domain.developer.AggregatedValues;

import java.math.BigDecimal;

public class PremiseAggregatedValuesGetResponse implements AggregatedValues {
    private final BigDecimal minPrice;
    private final BigDecimal maxPrice;
    private final Integer minRoomCount;
    private final Integer maxRoomCount;

    public PremiseAggregatedValuesGetResponse(AggregatedValues values) {
        this.minPrice = values.getMinPrice();
        this.maxPrice = values.getMaxPrice();
        this.minRoomCount = values.getMinRoomCount();
        this.maxRoomCount = values.getMaxRoomCount();
    }

    @Override
    public BigDecimal getMinPrice() {
        return minPrice;
    }

    @Override
    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    @Override
    public Integer getMinRoomCount() { return minRoomCount; }

    @Override
    public Integer getMaxRoomCount() { return maxRoomCount; }

}
