package pl.com.mike.developer.domain.adviser;

public enum TriggeredAdviceStatus {
    TRIGGERED("T"),
    LATER("L"),
    STORED("S"),
    REJECTED("R")
    ;
    private String code;

    TriggeredAdviceStatus(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    public static TriggeredAdviceStatus fromCode(String code) {
        if (TriggeredAdviceStatus.TRIGGERED.code.equals(code)) {
            return TriggeredAdviceStatus.TRIGGERED;
        } else if (TriggeredAdviceStatus.LATER.code.equals(code)) {
            return TriggeredAdviceStatus.LATER;
        } else if (TriggeredAdviceStatus.STORED.code.equals(code)) {
            return TriggeredAdviceStatus.STORED;
        } else if (TriggeredAdviceStatus.REJECTED.code.equals(code)) {
            return TriggeredAdviceStatus.REJECTED;
        } else {
            throw new IllegalArgumentException("Unexpected code: " + code);
        }
    }
}
