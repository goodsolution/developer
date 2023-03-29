package pl.com.goodsolution.adviser.logic.courseplatform;

public enum CourseSaleStatus {
    OPEN("o"),
    CLOSED("c");

    private String code;

    CourseSaleStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
