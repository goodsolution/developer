package pl.com.mike.developer.domain.courseplatform;


import pl.com.mike.developer.logic.courseplatform.Language;
import pl.com.mike.developer.logic.courseplatform.notifications.NotificationKind;
import pl.com.mike.developer.logic.courseplatform.notifications.NotificationStatus;
import pl.com.mike.developer.logic.courseplatform.notifications.NotificationType;

import java.time.LocalDateTime;

public class NotificationData {
    private Long id;
    private LocalDateTime createDatetime;
    private LocalDateTime seenDatetime;
    private LocalDateTime deleteDatetime;
    private CustomerData customer;
    private String title;
    private String content;
    private String link;
    private NotificationStatus status;
    private NotificationType type;
    private NotificationKind kind;
    private Language language;

    public NotificationData(Long id, LocalDateTime createDatetime, LocalDateTime seenDatetime, LocalDateTime deleteDatetime, CustomerData customer, String title, String content, String link, NotificationStatus status, NotificationType type, NotificationKind kind, Language language) {
        this.id = id;
        this.createDatetime = createDatetime;
        this.seenDatetime = seenDatetime;
        this.deleteDatetime = deleteDatetime;
        this.customer = customer;
        this.title = title;
        this.content = content;
        this.link = link;
        this.status = status;
        this.type = type;
        this.kind = kind;
        this.language = language;
    }

    public NotificationData(LocalDateTime createDatetime, LocalDateTime seenDatetime, LocalDateTime deleteDatetime, CustomerData customer, String title, String content, String link, NotificationStatus status, NotificationType type, NotificationKind kind, Language language) {
        this.createDatetime = createDatetime;
        this.seenDatetime = seenDatetime;
        this.deleteDatetime = deleteDatetime;
        this.customer = customer;
        this.title = title;
        this.content = content;
        this.link = link;
        this.status = status;
        this.type = type;
        this.kind = kind;
        this.language = language;
    }

    public NotificationData(CustomerData customer, String title, String content, String link, NotificationStatus status, NotificationType type, NotificationKind kind, Language language) {
        this.customer = customer;
        this.title = title;
        this.content = content;
        this.link = link;
        this.status = status;
        this.type = type;
        this.kind = kind;
        this.language = language;
    }

    public NotificationData(NotificationData data, LocalDateTime seenDatetime) {
        this.id = data.getId();
        this.createDatetime = data.getCreateDatetime();
        this.seenDatetime = seenDatetime;
        this.deleteDatetime = data.getDeleteDatetime();
        this.customer = data.getCustomer();
        this.title = data.getTitle();
        this.content = data.getContent();
        this.link = data.getLink();
        this.status = data.getStatus();
        this.type = data.getType();
        this.kind = data.getKind();
        this.language = data.getLanguage();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreateDatetime() {
        return createDatetime;
    }

    public LocalDateTime getSeenDatetime() {
        return seenDatetime;
    }

    public LocalDateTime getDeleteDatetime() {
        return deleteDatetime;
    }

    public CustomerData getCustomer() {
        return customer;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getLink() {
        return link;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public NotificationType getType() {
        return type;
    }

    public NotificationKind getKind() {
        return kind;
    }

    public Language getLanguage() {
        return language;
    }
}
