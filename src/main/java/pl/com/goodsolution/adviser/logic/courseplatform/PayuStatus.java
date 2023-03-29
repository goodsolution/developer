package pl.com.goodsolution.adviser.logic.courseplatform;

public enum PayuStatus {
    COMPLETED("COMPLETED"),
    CANCELED("CANCELED"),
    PENDING("PENDING");
    private String value;

    PayuStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
