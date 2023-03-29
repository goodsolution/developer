package pl.com.goodsolution.adviser.domain.adviser;

public enum AdviseType {
    SIMPLE_MESSAGE("P"),
    QUESTION_TEXT("Q"),
    QUESTION_DATE("D"),
    QUESTION_DATE_TIME("T"),
    QUESTION_COMBO("C");

    private String code;
    AdviseType(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    public static AdviseType fromCode(String code) {
        if (AdviseType.SIMPLE_MESSAGE.code.equals(code)) {
            return AdviseType.SIMPLE_MESSAGE;
        } else if (AdviseType.QUESTION_TEXT.code.equals(code)) {
            return AdviseType.QUESTION_TEXT;
        } else if (AdviseType.QUESTION_COMBO.code.equals(code)) {
            return AdviseType.QUESTION_COMBO;
        } else if (AdviseType.QUESTION_DATE.code.equals(code)) {
            return AdviseType.QUESTION_DATE;
        } else if (AdviseType.QUESTION_DATE_TIME.code.equals(code)) {
            return AdviseType.QUESTION_DATE_TIME;
        } else {
            throw new IllegalArgumentException("Unexpected code: " + code);
        }
    }
}
