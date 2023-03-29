package pl.com.goodsolution.adviser.api;

import java.math.BigDecimal;
import java.util.List;

public class OrderProductGetResponse {
    private Long id;
    private String dietName;
    private Long dietId;
    private String typeName;
    private Long quantity;
    private String dateFrom;
    private Long days;
    private String extras;
    private BigDecimal netPrice;
    private Integer vatPercent;
    private BigDecimal grossPrice;
    private String promotion;
    private BigDecimal grossValue;
    private String weekendsIndicator;
    private String testIndicatorName;
    private Boolean testIndicatorCode;
    private Long productId;
    private Boolean productStopped;
    private Long weekendOptionId;
    private List<OrderExtrasGetResponse> extrasList;


    public OrderProductGetResponse(Long id, String dietName, Long dietId, String typeName, Long quantity, String dateFrom, Long days, String extras, BigDecimal netPrice, Integer vatPercent, BigDecimal grossPrice, String promotion, BigDecimal grossValue, String weekendsIndicator, String testIndicatorName, Boolean testIndicatorCode, Long productId, Boolean productStopped, Long weekendOptionId, List<OrderExtrasGetResponse> extrasList) {
        this.id = id;
        this.dietName = dietName;
        this.dietId = dietId;
        this.typeName = typeName;
        this.quantity = quantity;
        this.dateFrom = dateFrom;
        this.days = days;
        this.extras = extras;
        this.netPrice = netPrice;
        this.vatPercent = vatPercent;
        this.grossPrice = grossPrice;
        this.promotion = promotion;
        this.grossValue = grossValue;
        this.weekendsIndicator = weekendsIndicator;
        this.testIndicatorName = testIndicatorName;
        this.testIndicatorCode = testIndicatorCode;
        this.productId = productId;
        this.productStopped = productStopped;
        this.weekendOptionId = weekendOptionId;
        this.extrasList = extrasList;
    }


    public Long getId() {
        return id;
    }

    public String getDietName() {
        return dietName;
    }

    public Long getDietId() {
        return dietId;
    }

    public String getTypeName() {
        return typeName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public Long getDays() {
        return days;
    }

    public String getExtras() {
        return extras;
    }

    public BigDecimal getNetPrice() {
        return netPrice;
    }

    public Integer getVatPercent() {
        return vatPercent;
    }

    public BigDecimal getGrossPrice() {
        return grossPrice;
    }

    public String getPromotion() {
        return promotion;
    }

    public BigDecimal getGrossValue() {
        return grossValue;
    }

    public String getWeekendsIndicator() {
        return weekendsIndicator;
    }

    public String getTestIndicatorName() {
        return testIndicatorName;
    }

    public Boolean getTestIndicatorCode() {
        return testIndicatorCode;
    }

    public Long getProductId() {
        return productId;
    }

    public Boolean getProductStopped() {
        return productStopped;
    }

    public Long getWeekendOptionId() {
        return weekendOptionId;
    }

    public List<OrderExtrasGetResponse> getExtrasList() {
        return extrasList;
    }
}
