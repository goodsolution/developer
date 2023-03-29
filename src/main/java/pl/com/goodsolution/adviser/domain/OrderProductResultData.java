package pl.com.goodsolution.adviser.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderProductResultData {
    private List<OrderProductData> products = new ArrayList<>();
    private BigDecimal priceBeforeDiscount;
    private BigDecimal priceAfterDiscount;

    public OrderProductResultData(List<OrderProductData> products, BigDecimal priceBeforeDiscount, BigDecimal priceAfterDiscount) {
        this.products = products;
        this.priceBeforeDiscount = priceBeforeDiscount;
        this.priceAfterDiscount = priceAfterDiscount;
    }

    public List<OrderProductData> getProducts() {
        return products;
    }

    public BigDecimal getPriceBeforeDiscount() {
        return priceBeforeDiscount;
    }

    public BigDecimal getPriceAfterDiscount() {
        return priceAfterDiscount;
    }
}
