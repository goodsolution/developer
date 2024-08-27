package pl.com.mike.developer.elearning.service;

public enum Reason {
    OK("Validation OK"),
    UNKNOWN("Validation failed - unknown reason"),
    EMPTY("Validation failed - empty text");

    private final String displayName;

    Reason(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }


}
