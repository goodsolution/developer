package pl.com.mike.developer.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderProductResultGetResponse {
    private List<OrderProductGetResponse> products = new ArrayList<>();
    private BigDecimal priceBeforeDiscount;
    private BigDecimal priceAfterDiscount;

    public OrderProductResultGetResponse(List<OrderProductGetResponse> products, BigDecimal priceBeforeDiscount, BigDecimal priceAfterDiscount) {
        this.products = products;
        this.priceBeforeDiscount = priceBeforeDiscount;
        this.priceAfterDiscount = priceAfterDiscount;
    }

    public List<OrderProductGetResponse> getProducts() {
        return products;
    }

    public BigDecimal getPriceBeforeDiscount() {
        return priceBeforeDiscount;
    }

    public BigDecimal getPriceAfterDiscount() {
        return priceAfterDiscount;
    }
}
