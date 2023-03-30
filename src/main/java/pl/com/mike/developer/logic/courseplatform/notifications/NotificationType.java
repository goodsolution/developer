package pl.com.mike.developer.logic.courseplatform.notifications;

import java.util.HashMap;
import java.util.Map;

public enum NotificationType {
    PLATFORM("p"),
    EMAIL("e");

    private String code;

    NotificationType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    private static final Map<String, NotificationType> codesWithEnums;

    static {
        codesWithEnums = new HashMap<>();
        for (NotificationType codeWithEnum : NotificationType.values()) {
            codesWithEnums.put(codeWithEnum.code, codeWithEnum);
        }
    }

    public static NotificationType from(String code) {
        return codesWithEnums.get(code);
    }
}
