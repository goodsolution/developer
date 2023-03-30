package pl.com.mike.developer.api;

public class CancelOrderPutRequest {
    private String reason;

    public CancelOrderPutRequest() {
    }

    public CancelOrderPutRequest(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
