package pl.com.goodsolution.adviser.api;

import java.math.BigDecimal;

public class OrderChangeProductGetResponse {
    private String productName;
    private Long quantity;
    private BigDecimal price;
    private String dateFrom;
    private Long daysLeft;

    public OrderChangeProductGetResponse(String productName, Long quantity, BigDecimal price, String dateFrom, Long daysLeft) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.dateFrom = dateFrom;
        this.daysLeft = daysLeft;
    }

    public String getProductName() {
        return productName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public Long getDaysLeft() {
        return daysLeft;
    }
}
