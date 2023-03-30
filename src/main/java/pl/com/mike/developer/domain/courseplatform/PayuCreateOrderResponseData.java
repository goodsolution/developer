package pl.com.mike.developer.domain.courseplatform;

import pl.com.mike.developer.api.courseplatform.external.payu.CreateOrderResponse;

public class PayuCreateOrderResponseData {
    private String redirectUri;
    private String orderId;
    private String extOrderId;
    private PayuCreateOrderResponseStatusData status;

    public PayuCreateOrderResponseData(CreateOrderResponse createOrderResponse) {
        this.redirectUri = createOrderResponse.getRedirectUri();
        this.orderId = createOrderResponse.getOrderId();
        this.extOrderId = createOrderResponse.getExtOrderId();
        this.status = new PayuCreateOrderResponseStatusData(createOrderResponse.getStatus());
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getExtOrderId() {
        return extOrderId;
    }

    public PayuCreateOrderResponseStatusData getStatus() {
        return status;
    }
}
