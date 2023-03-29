package pl.com.goodsolution.adviser.api.courseplatform;

public class BasketCoursesQuantityGetResponse {
    private Long quantity;

    public BasketCoursesQuantityGetResponse(Long quantity) {
        this.quantity = quantity;
    }

    public Long getQuantity() {
        return quantity;
    }
}
