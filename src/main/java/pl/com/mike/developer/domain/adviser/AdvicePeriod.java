package pl.com.mike.developer.domain.adviser;

public enum AdvicePeriod {
    INFINITE("I"),
    ONE_TIME("O");
    private String code;

    AdvicePeriod(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    public static AdvicePeriod fromCode(String code) {
        if (AdvicePeriod.INFINITE.code.equals(code)) {
            return AdvicePeriod.INFINITE;
        } else if (AdvicePeriod.ONE_TIME.code.equals(code)) {
            return AdvicePeriod.ONE_TIME;
        } else {
            throw new IllegalArgumentException("Unexpected code: " + code);
        }
    }
}
