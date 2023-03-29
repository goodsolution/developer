package pl.com.goodsolution.adviser.api.lifeadviser;

public class PurchasePostRequest {
    private Long categoryId;

    public PurchasePostRequest() {
    }

    public PurchasePostRequest(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() {
        return categoryId;
    }
}
