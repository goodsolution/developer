package pl.com.goodsolution.adviser.domain.courseplatform;

import pl.com.goodsolution.adviser.api.courseplatform.external.payu.CreateOrderResponseStatus;

public class PayuCreateOrderResponseStatusData {
    private String statusCode;
    private String statusDesc;

    public PayuCreateOrderResponseStatusData(CreateOrderResponseStatus createOrderResponseStatus) {
        this.statusCode = createOrderResponseStatus.getStatusCode();
        this.statusDesc = createOrderResponseStatus.getStatusDesc();
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusDesc() {
        return statusDesc;
    }
}
