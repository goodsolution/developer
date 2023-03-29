package pl.com.goodsolution.adviser.domain;

import java.util.List;

public class ProductDemandResultData {
    private Long sum;
    private List<ProductDemandData> products;

    public ProductDemandResultData(Long sum, List<ProductDemandData> products) {
        this.sum = sum;
        this.products = products;
    }

    public Long getSum() {
        return sum;
    }

    public List<ProductDemandData> getProducts() {
        return products;
    }
}
