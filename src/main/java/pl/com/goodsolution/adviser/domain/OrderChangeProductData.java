package pl.com.goodsolution.adviser.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderChangeProductData {
    private String productName;
    private Long quantity;
    private BigDecimal price;
    private LocalDate dateFrom;
    private Long daysLeft;


    public OrderChangeProductData(String productName, Long quantity, BigDecimal price, LocalDate dateFrom, Long daysLeft) {
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

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public Long getDaysLeft() {
        return daysLeft;
    }
}
