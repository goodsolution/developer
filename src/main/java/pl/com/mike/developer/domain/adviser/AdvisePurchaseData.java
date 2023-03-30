package pl.com.mike.developer.domain.adviser;

public class AdvisePurchaseData {
    private Long id;
    private Long categoryId;
    private Long customerId;

    public AdvisePurchaseData(Long categoryId, Long customerId) {
        this.categoryId = categoryId;
        this.customerId = customerId;
    }

    public Long getId() {
        return id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Long getCustomerId() {
        return customerId;
    }
}
