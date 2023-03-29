package pl.com.goodsolution.adviser.logic.courseplatform.notifications;

import java.util.HashMap;
import java.util.Map;

public enum NotificationKind {
    EMAIL_CONFIRMATION_REMINDER("e"),
    FROM_PANEL_ADMIN("p");

    private String code;

    NotificationKind(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    private static final Map<String, NotificationKind> codesWithEnums;

    static {
        codesWithEnums = new HashMap<>();
        for (NotificationKind codeWithEnum : NotificationKind.values()) {
            codesWithEnums.put(codeWithEnum.code, codeWithEnum);
        }
    }

    public static NotificationKind from(String code) {
        return codesWithEnums.get(code);
    }
}
