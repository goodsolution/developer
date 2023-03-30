package pl.com.mike.developer.logic.courseplatform;

public enum LessonType {
    STANDARD("s"),
    TASK("t");

    private String code;

    LessonType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
