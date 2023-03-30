package pl.com.mike.developer.logic.courseplatform.notifications;

import java.util.HashMap;
import java.util.Map;

public enum NotificationStatus {
    SENT("s"),
    WAITING("w");

    private String code;

    NotificationStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    private static final Map<String, NotificationStatus> codesWithEnums;

    static {
        codesWithEnums = new HashMap<>();
        for (NotificationStatus codeWithEnum : NotificationStatus.values()) {
            codesWithEnums.put(codeWithEnum.code, codeWithEnum);
        }
    }

    public static NotificationStatus from(String code) {
        return codesWithEnums.get(code);
    }
}
