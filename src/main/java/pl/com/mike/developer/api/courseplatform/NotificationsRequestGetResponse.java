package pl.com.mike.developer.api.courseplatform;

import java.util.List;

public class NotificationsRequestGetResponse {
    private final List<NotificationGetResponse> notifications;

    public NotificationsRequestGetResponse(List<NotificationGetResponse> notifications) {
        this.notifications = notifications;
    }

    public List<NotificationGetResponse> getNotifications() {
        return notifications;
    }
}
