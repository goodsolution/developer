package pl.com.goodsolution.adviser.domain.adviser;

public enum PurchasedCategoryStatus {
    STARTED("S"),
    FINISHED("F");
    private String code;

    PurchasedCategoryStatus(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    public static PurchasedCategoryStatus fromCode(String code) {
        if (PurchasedCategoryStatus.STARTED.code.equals(code)) {
            return PurchasedCategoryStatus.STARTED;
        } else if (PurchasedCategoryStatus.FINISHED.code.equals(code)) {
            return PurchasedCategoryStatus.FINISHED;
        } else {
            throw new IllegalArgumentException("Unexpected code: " + code);
        }
    }
}
