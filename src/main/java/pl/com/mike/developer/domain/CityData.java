package pl.com.mike.developer.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CityData {
    private Long id;
    private Long defaultDriverId;
    private LocalDate dateTo;
    private Boolean discount;
    private BigDecimal discountValue;

    public CityData(Long id, Long defaultDriverId, LocalDate dateTo, Boolean discount, BigDecimal discountValue) {
        this.id = id;
        this.defaultDriverId = defaultDriverId;
        this.dateTo = dateTo;
        this.discount = discount;
        this.discountValue = discountValue;
    }

    public Long getId() {
        return id;
    }

    public Long getDefaultDriverId() {
        return defaultDriverId;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public Boolean getDiscount() {
        return discount;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }
}
