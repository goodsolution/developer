package pl.com.goodsolution.adviser.domain;

import java.math.BigDecimal;

public class OrderProductModifyData {
    private Long id;
    private Long productId;
    private Long quantity;
    private Long dietId;
    private String name;
    private Long taxId;
    private BigDecimal taxValue;
    private BigDecimal netPriceForPiece;
    private BigDecimal netPricee;
    private BigDecimal priceForPiece;
    private BigDecimal price;
    private Long days;
    private Long weekendOptionId;
    private Boolean extras;
    private Boolean test;
    private Boolean priceChange;


    public OrderProductModifyData(Long id, Long productId, Long quantity, Long dietId, String name, Long taxId, BigDecimal taxValue, BigDecimal netPriceForPiece, BigDecimal net_pricee, BigDecimal priceForPiece, BigDecimal price, Long days, Long weekendOptionId, Boolean extras, Boolean test, Boolean priceChange) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.dietId = dietId;
        this.name = name;
        this.taxId = taxId;
        this.taxValue = taxValue;
        this.netPriceForPiece = netPriceForPiece;
        this.netPricee = net_pricee;
        this.priceForPiece = priceForPiece;
        this.price = price;
        this.days = days;
        this.weekendOptionId = weekendOptionId;
        this.extras = extras;
        this.test = test;
        this.priceChange = priceChange;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Long getDietId() {
        return dietId;
    }

    public Long getTaxId() {
        return taxId;
    }

    public BigDecimal getTaxValue() {
        return taxValue;
    }

    public BigDecimal getNetPriceForPiece() {
        return netPriceForPiece;
    }

    public BigDecimal getNetPricee() {
        return netPricee;
    }

    public BigDecimal getPriceForPiece() {
        return priceForPiece;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getDays() {
        return days;
    }

    public Long getWeekendOptionId() {
        return weekendOptionId;
    }

    public Boolean getExtras() {
        return extras;
    }

    public Boolean getTest() {
        return test;
    }

    public Boolean getPriceChange() {
        return priceChange;
    }

    public String getName() {
        return name;
    }
}
