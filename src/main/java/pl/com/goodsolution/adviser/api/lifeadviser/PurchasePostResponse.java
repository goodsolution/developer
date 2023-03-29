package pl.com.goodsolution.adviser.api.lifeadviser;

public class PurchasePostResponse {

    private Long purchasedCategoryId;

    public PurchasePostResponse(Long purchasedCategoryId) {
        this.purchasedCategoryId = purchasedCategoryId;
    }

    public Long getPurchasedCategoryId() {
        return purchasedCategoryId;
    }
}
