package pl.com.goodsolution.adviser.domain;

import java.util.List;

public class ProductDemandData {
    private Long no;
    private String diet;
    private List<ProductData> products;

    public ProductDemandData(Long no, String diet, List<ProductData> products) {
        this.no = no;
        this.diet = diet;
        this.products = products;
    }

    public Long getNo() {
        return no;
    }

    public String getDiet() {
        return diet;
    }

    public List<ProductData> getProducts() {
        return products;
    }
}
