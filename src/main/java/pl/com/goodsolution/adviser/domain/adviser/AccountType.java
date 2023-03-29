package pl.com.goodsolution.adviser.domain.adviser;

public enum AccountType {
    ADVISER_PLATFORM("A"),
    LIFE_ADVISER("L");
    private String code;

    AccountType(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    public static AccountType fromCode(String code) {
        if (AccountType.ADVISER_PLATFORM.code.equals(code)) {
            return AccountType.ADVISER_PLATFORM;
        } else if (AccountType.LIFE_ADVISER.code.equals(code)) {
            return AccountType.LIFE_ADVISER;
        } else {
            throw new IllegalArgumentException("Unexpected code: " + code);
        }
    }
}
