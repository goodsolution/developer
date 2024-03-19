package pl.com.mike.developer.domain.developer;

import java.math.BigDecimal;

public interface AggregatedValues {
    BigDecimal getMinPrice();
    BigDecimal getMaxPrice();
}
