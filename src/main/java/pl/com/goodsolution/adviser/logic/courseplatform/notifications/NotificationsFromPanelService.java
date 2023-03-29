package pl.com.goodsolution.adviser.logic.courseplatform.notifications;

import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.courseplatform.CreateNotificationsParameters;
import pl.com.goodsolution.adviser.domain.courseplatform.CustomerData;
import pl.com.goodsolution.adviser.domain.courseplatform.CustomersFilter;
import pl.com.goodsolution.adviser.domain.courseplatform.NotificationData;
import pl.com.goodsolution.adviser.logic.courseplatform.CourseCustomersService;
import pl.com.goodsolution.adviser.logic.courseplatform.Language;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationsFromPanelService {
    private final CourseCustomersService courseCustomersService;
    private final NotificationsService notificationsService;
    private final NotificationsValidator notificationsValidator;

    public NotificationsFromPanelService(CourseCustomersService courseCustomersService, NotificationsService notificationsService, NotificationsValidator notificationsValidator) {
        this.courseCustomersService = courseCustomersService;
        this.notificationsService = notificationsService;
        this.notificationsValidator = notificationsValidator;
    }

    public void createNotifications(CreateNotificationsParameters parameters) {
        validateParameters(parameters);
        List<CustomerData> customers = courseCustomersService.find(new CustomersFilter(parameters.getLanguage()));
        List<NotificationData> notifications = prepareNotifications(customers, parameters);
        notificationsService.validateAndCreate(notifications);
    }

    private void validateParameters(CreateNotificationsParameters parameters) {
        notificationsValidator.validateTitle(parameters.getTitle());
        notificationsValidator.validateContent(parameters.getContent());
        notificationsValidator.validateLink(parameters.getLink());
        notificationsValidator.validateLanguage(parameters.getLanguage());
    }

    private List<NotificationData> prepareNotifications(List<CustomerData> customers, CreateNotificationsParameters parameters) {
        List<NotificationData> notifications = new ArrayList<>();
        for (CustomerData customer : customers) {
            notifications.add(prepareNotification(customer, parameters));
        }
        return notifications;
    }

    private NotificationData prepareNotification(CustomerData customer, CreateNotificationsParameters parameters) {
        String title = parameters.getTitle();
        String content = parameters.getContent();
        String link = parameters.getLink();
        NotificationStatus status = NotificationStatus.SENT;
        NotificationType type = NotificationType.PLATFORM;
        NotificationKind kind = NotificationKind.FROM_PANEL_ADMIN;
        Language language = parameters.getLanguage();
        return new NotificationData(customer, title, content, link, status, type, kind, language);
    }
}
