package pl.com.mike.developer.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CityDiscountData {
    private Long cityId;
    private LocalDate dateTo;
    private BigDecimal discountValue;

    public CityDiscountData(Long cityId, LocalDate dateTo, BigDecimal discountValue) {
        this.cityId = cityId;
        this.dateTo = dateTo;
        this.discountValue = discountValue;
    }

    public Long getCityId() {
        return cityId;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }
}
