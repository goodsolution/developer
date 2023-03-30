package pl.com.mike.developer.logic.courseplatform.notifications;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.courseplatform.CustomerData;
import pl.com.mike.developer.domain.courseplatform.NotificationData;
import pl.com.mike.developer.domain.courseplatform.NotificationsFilter;
import pl.com.mike.developer.logic.courseplatform.Language;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationsService {

    private NotificationsJdbcRepository notificationsJdbcRepository;
    private NotificationsValidator notificationsValidator;

    public NotificationsService(NotificationsJdbcRepository notificationsJdbcRepository, NotificationsValidator notificationsValidator) {
        this.notificationsJdbcRepository = notificationsJdbcRepository;
        this.notificationsValidator = notificationsValidator;
    }

    public void validateAndCreate(NotificationData data) {

        LocalDateTime createDatetime = LocalDateTime.now();
        CustomerData customer = data.getCustomer();
        String title = data.getTitle();
        String content = data.getContent();
        String link = data.getLink();
        NotificationStatus status = data.getStatus();
        NotificationType type = data.getType();
        NotificationKind kind = data.getKind();
        Language language = data.getLanguage();

        NotificationData toCreate = new NotificationData(createDatetime, null, null,
                customer, title, content, link, status, type, kind, language);

        notificationsValidator.validate(toCreate);
        notificationsJdbcRepository.create(toCreate);
    }

    public void validateAndUpdate(NotificationData data) {
        notificationsValidator.validate(data);
        notificationsJdbcRepository.update(data);
    }

    @Transactional
    public void validateAndCreate(List<NotificationData> notifications) {
        for (NotificationData notification : notifications) {
            validateAndCreate(notification);
        }
    }

    public List<NotificationData> find(NotificationsFilter filter) {
        return notificationsJdbcRepository.find(filter);
    }

    public Long unseenNotificationsCount(CustomerData customer) {
        return notificationsJdbcRepository.unseenNotificationsCount(customer);
    }

    @Transactional
    public void setNotificationsSeen(List<NotificationData> notifications) {

        List<NotificationData> unseenNotifications = notifications.stream().filter(n -> n.getSeenDatetime() == null).collect(Collectors.toList());

        for (NotificationData unseenNotification : unseenNotifications) {
            validateAndUpdate(new NotificationData(unseenNotification, LocalDateTime.now()));
        }
    }
}
