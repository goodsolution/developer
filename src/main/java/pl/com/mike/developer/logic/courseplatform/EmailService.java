package pl.com.mike.developer.logic.courseplatform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.com.mike.developer.config.ApplicationConfig;
import pl.com.mike.developer.domain.DictionaryData;
import pl.com.mike.developer.domain.courseplatform.*;
import pl.com.mike.developer.logic.DictionariesService;
import pl.com.mike.developer.logic.DictionaryType;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Locale;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private JavaMailSender javaMailSender;
    private ApplicationConfig applicationConfig;
    private TemplateEngine templateEngine;
    private DictionariesService dictionariesService;
    private SentEmailsService sentEmailsService;
    private TokensService tokensService;

    public EmailService(JavaMailSender javaMailSender, ApplicationConfig applicationConfig, TemplateEngine templateEngine, DictionariesService dictionariesService, SentEmailsService sentEmailsService, TokensService tokensService) {
        this.javaMailSender = javaMailSender;
        this.applicationConfig = applicationConfig;
        this.templateEngine = templateEngine;
        this.dictionariesService = dictionariesService;
        this.sentEmailsService = sentEmailsService;
        this.tokensService = tokensService;
    }

    public void sendAfterRegistrationMail(CustomerData recipient, Locale locale) {
        Context context = new Context(locale);
        context.setVariable("loginPageUrl", applicationConfig.getCoursePlatformUrl() + "/login");
        context.setVariable("emailConfirmationLink", applicationConfig.getCoursePlatformUrl() + "/confirm-email/?token=" + tokensService.prepareAndCreate(recipient).getValue());
        String content = templateEngine.process("mail-after-registration", context);
        String title = chooseTitle(EmailTitle.AFTER_REGISTRATION, locale);
        sendEmail(recipient.getLogin(), title, content);
        sentEmailsService.create(new SentEmailData(null, recipient.getId(), title, content, locale.toLanguageTag(), MailEvent.AFTER_REGISTRATION.getCode()));
    }

    public void sendAfterOrderMail(CustomerData recipient, Locale locale, CourseOrderData order, List<CourseData> boughtCourses) {
        Context context = new Context(locale);
        context.setVariable("pageUrl", applicationConfig.getCoursePlatformUrl());
        context.setVariable("order", order);
        context.setVariable("boughtCourses", boughtCourses);
        String content = templateEngine.process("mail-after-order", context);
        String title = chooseTitle(EmailTitle.AFTER_ORDER, locale);
        sendEmail(recipient.getLogin(), title, content);
        sentEmailsService.create(new SentEmailData(order.getId(), recipient.getId(), title, content, locale.toLanguageTag(), MailEvent.AFTER_ORDER.getCode()));
    }

    public void sendAfterPasswordChangeMail(CustomerData recipient) {
        Locale locale = Locale.forLanguageTag(recipient.getLanguage());
        Context context = new Context(locale);
        context.setVariable("pageUrl", applicationConfig.getCoursePlatformUrl());
        String content = templateEngine.process("mail-after-password-change", context);
        String title = chooseTitle(EmailTitle.AFTER_PASSWORD_CHANGE, locale);
        sendEmail(recipient.getLogin(), title, content);
        sentEmailsService.create(new SentEmailData(null, recipient.getId(), title, content, locale.toLanguageTag(), MailEvent.AFTER_PASSWORD_CHANGE.getCode()));
    }

    public void sendPasswordResetMail(PasswordResetTokenData token) {
        Locale locale = Locale.forLanguageTag(token.getCustomer().getLanguage());
        Context context = new Context(locale);
        context.setVariable("passwordRestartUrl", applicationConfig.getCoursePlatformUrl() + "/reset-password?token=" + token.getValue());
        context.setVariable("tokenValidityTime", applicationConfig.getPasswordResetTokenValidityTimeInMinutes());
        String content = templateEngine.process("mail-password-reset", context);
        String title = chooseTitle(EmailTitle.PASSWORD_RESET, locale);
        sendEmail(token.getCustomer().getLogin(), title, content);
        sentEmailsService.create(new SentEmailData(null, token.getCustomer().getId(), title, content, locale.toLanguageTag(), MailEvent.PASSWORD_RESET.getCode()));
    }

    @Transactional
    public void sendNewsletterMail(CustomerData customer, NewsletterData newsletter) {
        Locale locale = Locale.forLanguageTag(customer.getLanguage());
        Context context = getContextForNewsletter(newsletter, locale);
        String content = templateEngine.process("mail-newsletter", context);
        String title = chooseTitle(EmailTitle.NEWSLETTER, locale);
        sendEmail(customer.getLogin(), title, content);
        sentEmailsService.create(new SentEmailData(null, customer.getId(), title, content, locale.toLanguageTag(), MailEvent.NEWSLETTER.getCode()));
    }

    public void sendEmailConfirmationLink(CustomerData customer) {
        Locale locale = Locale.forLanguageTag(customer.getLanguage());
        Context context = new Context(locale);
        context.setVariable("emailConfirmationLink", applicationConfig.getCoursePlatformUrl() + "/confirm-email/?token=" + tokensService.prepareAndCreate(customer).getValue());
        String content = templateEngine.process("mail-email-confirmation-link", context);
        String title = chooseTitle(EmailTitle.EMAIL_CONFIRMATION_LINK, locale);
        sendEmail(customer.getLogin(), title, content);
        sentEmailsService.create(new SentEmailData(null, customer.getId(), title, content, locale.toLanguageTag(), MailEvent.EMAIL_CONFIRMATION_LINK.getCode()));
        log.info("Successfully sent email confirmation link to customer with ID {}", customer.getId());
    }

    private Context getContextForNewsletter(NewsletterData newsletter, Locale locale) {
        Context context = new Context(locale);
        context.setVariable("loginPageUrl", applicationConfig.getCoursePlatformUrl() + "/login");
        context.setVariable("title", newsletter.getContent().get(locale.getLanguage()).getTitle());
        context.setVariable("content", newsletter.getContent().get(locale.getLanguage()).getHtml());
        return context;
    }

    private void sendEmail(String recipientMail, String subject, String content) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(recipientMail);
            helper.setReplyTo(applicationConfig.getReplyToEmail());
            helper.setFrom(applicationConfig.getFromEmail());
            helper.setSubject(subject);
            helper.setText(content, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            try {
                javaMailSender.send(mail);
            } catch (Exception ex) {
                log.error("Sending email error: " + ex.getMessage());
            }
        }).start();

    }

    private String chooseTitle(EmailTitle emailTitle, Locale locale) {

        List<DictionaryData> list = dictionariesService.getDictionary(DictionaryType.EMAIL_TITLES, locale);

        for (DictionaryData dictionaryData : list) {
            if(dictionaryData.getCode().equals(emailTitle.getCode())) {
                return dictionaryData.getValue();
            }
        }

        throw new IllegalArgumentException("No email title!");

    }

}
