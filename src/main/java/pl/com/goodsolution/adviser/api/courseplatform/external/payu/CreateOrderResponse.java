package pl.com.goodsolution.adviser.api.courseplatform.external.payu;

public class CreateOrderResponse {
    private String redirectUri;
    private String orderId;
    private String extOrderId;
    private CreateOrderResponseStatus status;

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getExtOrderId() {
        return extOrderId;
    }

    public CreateOrderResponseStatus getStatus() {
        return status;
    }
}
