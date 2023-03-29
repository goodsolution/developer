package pl.com.goodsolution.adviser.logic.courseplatform;

public enum EmailTitle {
    AFTER_REGISTRATION("r"),
    AFTER_ORDER("o"),
    AFTER_PASSWORD_CHANGE("p"),
    PASSWORD_RESET("n"),
    NEWSLETTER("e"),
    EMAIL_CONFIRMATION_LINK("c");

    private String code;

    EmailTitle(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
