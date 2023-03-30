package pl.com.mike.developer.domain;

public class ProductData {
    private String name;
    private Long quantity;

    public ProductData(String name, Long quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public Long getQuantity() {
        return quantity;
    }
}
