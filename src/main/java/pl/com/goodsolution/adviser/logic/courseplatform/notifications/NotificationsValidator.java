package pl.com.goodsolution.adviser.logic.courseplatform.notifications;

import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.courseplatform.CustomerData;
import pl.com.goodsolution.adviser.domain.courseplatform.NotificationData;
import pl.com.goodsolution.adviser.logic.courseplatform.Language;

@Service
public class NotificationsValidator {

    public void validate(NotificationData notification) {
        validateIfNull(notification);
        validateCustomer(notification.getCustomer());
        validateTitle(notification.getTitle());
        validateContent(notification.getContent());
        validateLink(notification.getLink());
        validateStatus(notification.getStatus());
        validateType(notification.getType());
        validateKind(notification.getKind());
        validateLanguage(notification.getLanguage());
    }

    public void validateIfNull(NotificationData notification) {
        if(notification == null) {
            throw new IllegalArgumentException("Notification can't be null");
        }
    }

    public void validateCustomer(CustomerData customer) {
        if(customer == null) {
            throw new IllegalArgumentException("Customer is empty or incorrect");
        } else if(customer.getId() == null) {
            throw new IllegalArgumentException("Incorrect customer ID");
        }
    }

    public void validateTitle(String title) {
        if(title == null || title.equals("")) {
            throw new IllegalArgumentException("Title can't be empty");
        } else if(title.length() > 20) {
            throw new IllegalArgumentException("Title too long, max 20 characters");
        }
    }

    public void validateContent(String content) {
        if(content == null || content.equals("")) {
            throw new IllegalArgumentException("Content can't be empty");
        } else if (content.length() > 100) {
            throw new IllegalArgumentException("Content too long, max 100 characters");
        }
    }

    public void validateLink(String link) {
        if(link == null || link.equals("")) {
            throw new IllegalArgumentException("Link can't be empty");
        } else if (link.length() > 500) {
            throw new IllegalArgumentException("Link too long, max 500 characters");
        }
    }

    public void validateStatus(NotificationStatus status) {
        if(status == null) {
            throw new IllegalArgumentException("Notification status is empty or incorrect");
        }
    }

    public void validateType(NotificationType type) {
        if(type == null) {
            throw new IllegalArgumentException("Notification type is empty or incorrect");
        }
    }

    public void validateKind(NotificationKind kind) {
        if(kind == null) {
            throw new IllegalArgumentException("Notification kind is empty or incorrect");
        }
    }

    public void validateLanguage(Language language) {
        if(language == null) {
            throw new IllegalArgumentException("Notification language is empty or incorrect");
        }
    }

}
