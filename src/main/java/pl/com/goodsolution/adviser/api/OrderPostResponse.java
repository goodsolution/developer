package pl.com.goodsolution.adviser.api;

public class OrderPostResponse {
    private Long newOrderId;


    public OrderPostResponse(Long newOrderId) {
        this.newOrderId = newOrderId;
    }


    public Long getNewOrderId() {
        return newOrderId;
    }
}
