package pl.com.mike.developer.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderProductData {
    private Long id;
    private String dietName;
    private Long dietId;
    private String typeName;
    private Long quantity;
    private LocalDate dateFrom;
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
    private List<ExtrasData> extrasList;

    public OrderProductData(Long id, Long quantity, LocalDate dateFrom, Long days, Long productId, Long weekendOptionId,  Boolean testIndicatorCode, Long dietId) {
        this.id = id;
        this.quantity = quantity;
        this.dateFrom = dateFrom;
        this.days = days;
        this.productId = productId;
        this.weekendOptionId = weekendOptionId;
        this.testIndicatorCode = testIndicatorCode;
        this.dietId = dietId;
    }

    public OrderProductData(Long id, String dietName, Long dietId, String typeName, Long quantity, LocalDate dateFrom, Long days, String extras, BigDecimal netPrice, Integer vatPercent, BigDecimal grossPrice, String promotion, BigDecimal grossValue, String weekendsIndicator, String testIndicatorName, Boolean testIndicatorCode, Long productId, Boolean productStopped, Long weekendOptionId, List<ExtrasData> extrasList) {
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

    public LocalDate getDateFrom() {
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

    public List<ExtrasData> getExtrasList() {
        return extrasList;
    }

    public Set<Long> getExtraIds() {
        Set<Long> ids = new HashSet<>();
        if (extrasList != null) {
            for (ExtrasData extrasData : extrasList) {
                ids.add(extrasData.getId());
            }
        }
        return ids;
    }
}
