package pl.com.mike.developer.logic.courseplatform;

import java.util.HashMap;
import java.util.Map;

public enum CourseVisibilityStatus {
    VISIBLE("v"),
    INVISIBLE("i");

    private String value;

    CourseVisibilityStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private static final Map<String, CourseVisibilityStatus> valuesWithEnums;

    static {
        valuesWithEnums = new HashMap<>();
        for (CourseVisibilityStatus valueWithEnum : CourseVisibilityStatus.values()) {
            valuesWithEnums.put(valueWithEnum.value, valueWithEnum);
        }
    }

    public static CourseVisibilityStatus from(String visibilityStatus) {
        return valuesWithEnums.get(visibilityStatus);
    }
}
