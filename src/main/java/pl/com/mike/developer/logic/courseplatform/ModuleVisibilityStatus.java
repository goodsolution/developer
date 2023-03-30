package pl.com.mike.developer.logic.courseplatform;

public enum  ModuleVisibilityStatus {
    VISIBLE("v"),
    INVISIBLE("i");

    private String value;

    ModuleVisibilityStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
