package pl.com.mike.developer.logic.courseplatform;

public enum PayuIntegrationOrderStatus {
    BEFORE_AUTH("a"),
    BEFORE_CREATE_ORDER("b"),
    AFTER_CREATE_ORDER("c"),
    AFTER_ORDER_CANCEL("d"),
    AFTER_ORDER_PENDING("e"),
    AFTER_ORDER_COMPLETED("f");

    //Link to docs https://developers.payu.com/pl/restapi.html#notifications

    //Possible transactions lifecycle:
    //a>b>c>e>f
    //a>b>c>e>d
    //a>b>c>d

    private String status;

    PayuIntegrationOrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
