package pl.com.goodsolution.adviser.logic.courseplatform;

public enum OrderStatus {
    UNPAID("u"),
    PAID("p"),
    CANCELED("c"),
    PENDING("w");

    private String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
