package pl.com.mike.developer.logic.courseplatform;

public enum CourseCommentVisibilityStatus {
    VISIBLE("v"),
    INVISIBLE("i");

    private String value;

    CourseCommentVisibilityStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
