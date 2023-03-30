package pl.com.mike.developer.logic.courseplatform;

public enum LessonVisibilityStatus {
    VISIBLE("v"),
    INVISIBLE("i");

    private String value;

    LessonVisibilityStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
