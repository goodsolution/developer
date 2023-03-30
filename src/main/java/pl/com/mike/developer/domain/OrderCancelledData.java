package pl.com.mike.developer.domain;

import java.time.LocalDateTime;

public class OrderCancelledData {
    private String reason;
    private Long userId;
    private LocalDateTime date;
    private UserData userData;

    public OrderCancelledData(String reason, Long userId, LocalDateTime date, UserData userData) {
        this.reason = reason;
        this.userId = userId;
        this.date = date;
        this.userData = userData;
    }

    public String getReason() {
        return reason;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public UserData getUserData() {
        return userData;
    }
}
