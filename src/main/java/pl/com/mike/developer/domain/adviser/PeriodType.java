package pl.com.mike.developer.domain.adviser;

public enum PeriodType {
    MONTH("M"),
    QUARTER("Q"),
    HALF_YEAR("H"),
    YEAR("Y");
    private String code;

    PeriodType(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    public static PeriodType fromCode(String code) {
        if (PeriodType.MONTH.code.equals(code)) {
            return PeriodType.MONTH;
        } else if (PeriodType.QUARTER.code.equals(code)) {
            return PeriodType.QUARTER;
        } else if (PeriodType.HALF_YEAR.code.equals(code)) {
            return PeriodType.HALF_YEAR;
        } else if (PeriodType.YEAR.code.equals(code)) {
            return PeriodType.YEAR;
        } else {
            throw new IllegalArgumentException("Unexpected code: " + code);
        }
    }
}
