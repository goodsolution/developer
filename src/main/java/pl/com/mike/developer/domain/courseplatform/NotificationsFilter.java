package pl.com.mike.developer.domain.courseplatform;

import pl.com.mike.developer.logic.courseplatform.notifications.NotificationType;

public class NotificationsFilter {
    private Boolean deleted;
    private CustomerData customer;
    private NotificationType type;
    private Long limit;

    public NotificationsFilter(Boolean deleted, CustomerData customer, NotificationType type, Long limit) {
        this.deleted = deleted;
        this.customer = customer;
        this.type = type;
        this.limit = limit;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public CustomerData getCustomer() {
        return customer;
    }

    public NotificationType getType() {
        return type;
    }

    public Long getLimit() {
        return limit;
    }
}
