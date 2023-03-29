package pl.com.goodsolution.adviser.logic.courseplatform.notifications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import pl.com.goodsolution.adviser.domain.courseplatform.CustomerData;
import pl.com.goodsolution.adviser.domain.courseplatform.NotificationData;
import pl.com.goodsolution.adviser.logic.courseplatform.CourseCustomersService;
import pl.com.goodsolution.adviser.logic.courseplatform.Language;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConfirmEmailNotificationsService {

    private static final Logger log = LoggerFactory.getLogger(ConfirmEmailNotificationsService.class);

    private static final String LINK = "/account-settings";

    private final NotificationsService notificationsService;
    private final CourseCustomersService courseCustomersService;

    public ConfirmEmailNotificationsService(NotificationsService notificationsService, CourseCustomersService courseCustomersService) {
        this.notificationsService = notificationsService;
        this.courseCustomersService = courseCustomersService;
    }

    @Scheduled(cron = "0 0 4 * * *")
    private void createNotifications() {
        long startTime = System.currentTimeMillis();
        log.info("Start confirm email notifications creating");
        List<CustomerData> recipients = courseCustomersService.findConfirmEmailNotificationRecipients();
        List<NotificationData> notifications = prepareNotifications(recipients);
        notificationsService.validateAndCreate(notifications);
        long timeTaken = System.currentTimeMillis() - startTime;
        log.info("Successfully created {} confirm email notifications in {} ms", notifications.size(), timeTaken);
    }

    private List<NotificationData> prepareNotifications(List<CustomerData> recipients) {
        List<NotificationData> notifications = new ArrayList<>();
        for (CustomerData recipient : recipients) {
            notifications.add(prepareNotification(recipient));
        }
        return notifications;
    }

    private NotificationData prepareNotification(CustomerData recipient) {
        Language language = Language.from(recipient.getLanguage());
        String title = prepareTitle(language);
        String content = prepareContent(language);
        NotificationStatus status = NotificationStatus.SENT;
        NotificationType type = NotificationType.PLATFORM;
        NotificationKind kind = NotificationKind.EMAIL_CONFIRMATION_REMINDER;
        return new NotificationData(recipient, title, content, LINK, status, type, kind, language);
    }

    private String prepareTitle(Language language) {
        if(language == Language.POLISH) {
            return "Potwierdź email";
        } else {
            return "Confirm email";
        }
    }

    private String prepareContent(Language language) {
        if(language == Language.POLISH) {
            return "Kliknij w powiadomienie aby przejść do ustawień";
        } else {
            return "Click on the notification to go to settings";
        }
    }
}
