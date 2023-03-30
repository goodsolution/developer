package pl.com.mike.developer.logic.courseplatform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.com.mike.developer.domain.courseplatform.CustomerData;
import pl.com.mike.developer.domain.courseplatform.NewsletterData;

@Service
public class NewsletterService {
    private static final Logger log = LoggerFactory.getLogger(NewsletterService.class);
    private SentEmailsService sentEmailsService;
    private EmailService emailService;

    public NewsletterService(SentEmailsService sentEmailsService, EmailService emailService) {
        this.sentEmailsService = sentEmailsService;
        this.emailService = emailService;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendNewsletterToCustomer(CustomerData customer, NewsletterData newsletter) {
        log.debug("Start sending newsletter message to customer: " + customer.getId());
        emailService.sendNewsletterMail(customer, newsletter);
        log.debug("Newsletter message sent to customer: " + customer.getId());
    }
}
