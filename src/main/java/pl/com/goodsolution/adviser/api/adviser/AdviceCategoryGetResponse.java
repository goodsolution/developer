package pl.com.goodsolution.adviser.api.adviser;

import java.math.BigDecimal;

public class AdviceCategoryGetResponse {
    private String name;
    private String applicationId;
    private String context;
    private BigDecimal price;
    private String currencyCode;

    public AdviceCategoryGetResponse(String name, String applicationId, String context, BigDecimal price, String currencyCode) {
        this.name = name;
        this.applicationId = applicationId;
        this.context = context;
        this.price = price;
        this.currencyCode = currencyCode;
    }

    public String getName() {
        return name;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getContext() {
        return context;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }
}
