package pl.com.goodsolution.adviser.domain.adviser;

import java.math.BigDecimal;

public class AdviceCategoryData {
    private Long id;
    private String applicationId;
    private String context;
    private String name;
    private String description;
    private String tags;
    private BigDecimal price;
    private String currencyCode;
    private String period_code;
    private BigDecimal pricePerMonth;
    private BigDecimal pricePerQuarter;
    private BigDecimal pricePerHalfYear;
    private BigDecimal pricePerYear;

    public AdviceCategoryData(Long id, String applicationId, String context, String name, String description, String tags, BigDecimal price, String currencyCode, String period_code, BigDecimal pricePerMonth, BigDecimal pricePerQuarter, BigDecimal pricePerHalfYear, BigDecimal pricePerYear) {
        this.id = id;
        this.applicationId = applicationId;
        this.context = context;
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.price = price;
        this.currencyCode = currencyCode;
        this.period_code = period_code;
        this.pricePerMonth = pricePerMonth;
        this.pricePerQuarter = pricePerQuarter;
        this.pricePerHalfYear = pricePerHalfYear;
        this.pricePerYear = pricePerYear;
    }

    public Long getId() {
        return id;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getContext() {
        return context;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTags() {
        return tags;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getPeriod_code() {
        return period_code;
    }

    public BigDecimal getPricePerMonth() {
        return pricePerMonth;
    }

    public BigDecimal getPricePerQuarter() {
        return pricePerQuarter;
    }

    public BigDecimal getPricePerHalfYear() {
        return pricePerHalfYear;
    }

    public BigDecimal getPricePerYear() {
        return pricePerYear;
    }
}
