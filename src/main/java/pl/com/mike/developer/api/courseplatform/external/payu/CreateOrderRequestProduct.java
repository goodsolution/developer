package pl.com.mike.developer.api.courseplatform.external.payu;

import java.math.BigInteger;

public class CreateOrderRequestProduct {
    private String name;
    private BigInteger unitPrice;
    private Integer quantity;
    private Boolean virtual;

    public CreateOrderRequestProduct() {
    }

    public CreateOrderRequestProduct(String name, BigInteger unitPrice, Integer quantity, Boolean virtual) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.virtual = virtual;
    }

    public String getName() {
        return name;
    }

    public BigInteger getUnitPrice() {
        return unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Boolean getVirtual() {
        return virtual;
    }
}
