package pl.com.goodsolution.adviser.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.goodsolution.adviser.auth.AuthenticatedUser;
import pl.com.goodsolution.adviser.config.EmailConfig;
import pl.com.goodsolution.adviser.domain.*;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class EmailsService {
    private static final Logger log = LoggerFactory.getLogger(EmailsService.class);
    private EmailsJdbcRepository emailsJdbcRepository;
    private ProductsService productsService;
    private DeliveryService deliveryService;
    private DictionariesService dictionariesService;
    private AuthenticatedUser authenticatedUser;
    private JavaMailSender mailSender;
    private Environment env;
    private EmailConfig emailConfig;

    public EmailsService(EmailsJdbcRepository emailsJdbcRepository, ProductsService productsService, DeliveryService deliveryService, DictionariesService dictionariesService, AuthenticatedUser authenticatedUser, JavaMailSender mailSender, Environment env, EmailConfig emailConfig) {
        this.emailsJdbcRepository = emailsJdbcRepository;
        this.productsService = productsService;
        this.deliveryService = deliveryService;
        this.dictionariesService = dictionariesService;
        this.authenticatedUser = authenticatedUser;
        this.mailSender = mailSender;
        this.env = env;
        this.emailConfig = emailConfig;
    }

    TemplateData getTemplate(Long templateId, boolean change) {
        TemplateData templateToChange = emailsJdbcRepository.getTemplate(templateId);
        if (withoutChange(change)) {
            return templateToChange;
        } else {
            String contentEmail = templateToChange.getContentEmail();
            String contentSms = templateToChange.getContentSms();
            contentEmail = replaceEmailContent(contentEmail);
            contentSms = replaceSmsContent(contentSms);
            TemplateData template = new TemplateData(templateToChange.getId(), templateToChange.getName(), contentEmail,
                    contentSms, templateToChange.getSendEmail(), templateToChange.getSendSms());
            return template;
        }
    }

    private boolean withoutChange(boolean change) {
        return !change;
    }

    private String replaceEmailContent(String content) {
        Map<String, String> replaceData = new HashMap<>();
        replaceData.put("_USER_", "<input class=\"Text0\" disabled=\"disabled\" style=\"border-radius: 5px; border: none; color: white; background: #333333; width:9em; padding: 5px; text-align: center; margin: 10px 0;\" type=\"text\" value=\"Imię i nazwisko\" />");
        replaceData.put("_EMAIL_", "<input class=\"Text1\" disabled=\"disabled\" style=\"border-radius: 5px; border: none; color: white; background: #333333; width:8.5em; padding: 5px; text-align: center; margin: 10px 0;\" type=\"text\" value=\"Email\" />");
        replaceData.put("_DIET_END_", "<input class=\"Text2\" disabled=\"disabled\" style=\"border-radius: 5px; border: none; color: white; background: #333333; width:13em; padding: 5px; text-align: center; margin: 10px 0;\" type=\"text\" value=\"Data zakończenia diety\" />");
        replaceData.put("_DANE_ZAMOWIENIA_", "<input class=\"Text3\" disabled=\"disabled\" style=\"border-radius: 5px; border: none; color: white; background: #333333; width:13em; padding: 5px; text-align: center; margin: 10px 0;\" type=\"text\" value=\"Dane zamówienia\" />");
        replaceData.put("_KWOTA_ZAMOWIENIA_", "<input class=\"Text5\" disabled=\"disabled\" style=\"border-radius: 5px; border: none; color: white; background: #333333; width:13em; padding: 5px; text-align: center; margin: 10px 0;\" type=\"text\" value=\"Kwota zamówienia\" />");
        replaceData.put("_NR_ZAMOWIENIA_", "<input class=\"Text4\" disabled=\"disabled\" style=\"border-radius: 5px; border: none; color: white; background: #333333; width:13em; padding: 5px; text-align: center; margin: 10px 0;\" type=\"text\" value=\"Nr. zamówienia\" />");

        for (String key : replaceData.keySet()) {
            content = content.replace(key, replaceData.get(key));
        }
        return content;
    }

    private String replaceSmsContent(String content) {
        Map<String, String> replaceData = new HashMap<>();
        replaceData.put("_USER_", "<input class=\"Text0\" disabled=\"disabled\" style=\"border-radius: 5px; border: none; color: white; background: #333333; width:9em; padding: 5px; text-align: center; margin: 10px 0;\" type=\"text\" value=\"Imię i nazwisko\" />");
        replaceData.put("_EMAIL_", "<input class=\"Text1\" disabled=\"disabled\" style=\"border-radius: 5px; border: none; color: white; background: #333333; width:8.5em; padding: 5px; text-align: center; margin: 10px 0;\" type=\"text\" value=\"Email\" />");
        replaceData.put("_DIET_END_", "<input class=\"Text2\" disabled=\"disabled\" style=\"border-radius: 5px; border: none; color: white; background: #333333; width:13em; padding: 5px; text-align: center; margin: 10px 0;\" type=\"text\" value=\"Data zakończenia diety\" />");
        replaceData.put("_KWOTA_ZAMOWIENIA_", "<input class=\"Text3\" disabled=\"disabled\" style=\"border-radius: 5px; border: none; color: white; background: #333333; width:13em; padding: 5px; text-align: center; margin: 10px 0;\" type=\"text\" value=\"Dane zamówienia\" />");
        replaceData.put("_NR_ZAMOWIENIA_", "<input class=\"Text5\" disabled=\"disabled\" style=\"border-radius: 5px; border: none; color: white; background: #333333; width:13em; padding: 5px; text-align: center; margin: 10px 0;\" type=\"text\" value=\"Kwota zamówienia\" />");

        for (String key : replaceData.keySet()) {
            content = content.replace(key, replaceData.get(key));
        }
        return content;
    }

    void send(MailConfigData mailConfig, String recipient, String from, String subject, String msg, Object attachment) {
        log.debug("Sending email title: " + subject + " from: " + from);

        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mail, true);
        } catch (MessagingException e) {
            log.error("mail sending error(1): " + e.getMessage());
        }
        try {
            helper.setSubject(subject);
            if (isEnvironment("prod")) {
                helper.setTo(recipient);
            } else {
                helper.setTo("pkbiker@wp.pl");
                helper.setSubject("TEST " + subject);
            }
            helper.setText(msg, true);
            helper.setFrom(new InternetAddress(from, "masterdieta.pl"));
            helper.setReplyTo("kontakt@masterdieta.pl");
        } catch (UnsupportedEncodingException e) {
            log.error("mail sending error(unsupported): " + e.getMessage());
        } catch (MessagingException e) {
            log.error("mail sending error(2): " + e.getMessage());
        }
        if (!emailConfig.getEmailsDisabled()) {
            if (isTestEnvironment()) {
                if (mailConfig.getTestEmail() != null) {
                    sendToTestAddresses(mailConfig, mail, helper);
                } else {
                    throw new IllegalArgumentException("Test email is not set");
                }
            } else {
                mailSender.send(mail);
            }
        }
        createSentEmailHistory(from, recipient, subject, msg);
    }

    private void sendToTestAddresses(MailConfigData mailConfig, MimeMessage mail, MimeMessageHelper helper) {
        String[] addresses = mailConfig.getTestEmail().split(";");
        for (String address : addresses) {
            try {
                helper.setTo(address);
            } catch (MessagingException e) {
                log.error("test mail error: " + e.getMessage());
            }
            log.debug("sending test mail to: " + address);
            mailSender.send(mail);
        }
    }

    private boolean isTestEnvironment() {
        return isEnvironment("test") || isEnvironment("testlocal");
    }

    private boolean isEnvironment(String environment) {
        return env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals(environment);
    }

    void createOrderSentEmail(Long orderId, Long historyId, String title, String message, Long sentBy) {
        emailsJdbcRepository.createOrderSentEmail(orderId, historyId, title, message, sentBy);
    }

    void createSentEmailHistory(String sender, String recipient, String title, String message) {
        emailsJdbcRepository.createSentEmailHistory(sender, recipient, title, message);
    }

    public void sendOrderPaid(OrderDetailsData order, List<OrderProductCreateData> items) {
        if (order == null) {
            throw new IllegalArgumentException("order is null");
        }
        String title = "Potwierdzenie zapłaty";
        String data = getData(order, items, false);

        Long templateId = 2L;
        if (order.getPaymentMethodId() == 1) templateId = 2L;
        if (order.getPaymentMethodId() == 2) templateId = 4L;
        if (order.getPaymentMethodId() == 3) templateId = 5L;

        TemplateData template = getTemplate(templateId, false);
        if (template.getSendEmail()) {
            String contentEmail = template.getContentEmail();
            contentEmail = contentEmail.replace("_USER_", order.getCustomerForOrder().getFirstName() + " " + order.getCustomerForOrder().getLastName());
            contentEmail = contentEmail.replace("_EMAIL_", order.getCustomerForOrder().getEmail());
            contentEmail = contentEmail.replace("_NR_ZAMOWIENIA_", order.getOrderId());
            contentEmail = contentEmail.replace("_KWOTA_ZAMOWIENIA_", getOrderBasketSum(order) + " PLN");
            contentEmail = contentEmail.replace("_DANE_ZAMOWIENIA_", data);

            String html = getOrderPaidHtml(contentEmail);
            MailConfigData mailConfig = getMailConfig();
            send(mailConfig, order.getCustomerForOrder().getEmail(), mailConfig.getDefaultEmail(), title, html, null);

            EmailHistoryData lastHistoryEmail = getLastHistoryEmail(order.getCustomerForOrder().getEmail());
            if (lastHistoryEmail == null) {
                throw new IllegalArgumentException("Nie moge pobrać ostatniego maila dla " + order.getCustomerForOrder().getEmail());
            }
            log.debug("bef createOrderSentEmail order.getId(): " + order.getId() + " lastHistoryEmail.getId(): " + lastHistoryEmail.getId());
            createOrderSentEmail(order.getId(), lastHistoryEmail.getId(), title, html, authenticatedUser.getUserId());
        }
    }

    private String getOrderBasketSum(OrderDetailsData order) {
        return order.getOrderBasketSum().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    private String getOrderPaidHtml(String content) {
        return "<body style=\"background-color: #f2f2f2;\">\n" +
                "<div marginwidth=\"0\" marginheight=\"0\" style=\"background:#f2f2f2;margin:0;padding:0;width:100%!important\" bgcolor=\"#FAFAFA\">\n" +
                "<center>\n" +
                "<table style=\"background-color: #f2f2f2\" width=\"100%\" border=\"0\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td height=\"30\" width=\"600\"></td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border: 1px solid #333333;background-color: white;font-family: Tahoma;\">\n" +
                "<tr>\n" +
                "<td height=\"30\" colspan=\"5\" width=\"600\">\n" +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td width=\"20\"></td>\n" +
                "<td height=\"85\" colspan=\"3\" width=\"556\" valign=\"top\" style=\"text-align: center\"><img src=\"https://masterdieta.pl/images/mail_banner.jpg\" alt=\"banner\" height=\"100px\" width=\"500px\" /></td>\n" +
                "<td width=\"20\"></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td colspan=\"5\" width=\"600\" height=\"30\">\n" +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td width=\"20\"></td>\n" +
                "<td height=\"60\" valign=\"top\" width=\"560\" colspan=\"3\" style=\"color: #4c4c4c; font-size: 15px;\">\n" +
                ""+ content +"\n" +
                "</td>\n" +
                "<td width=\"20\"></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td width=\"20\"></td>\n" +
                "<td colspan=\"3\" valign=\"bottom\" width=\"560\" height=\"30\" style=\"font-size: 14px;\">\n" +
                "Pozdrawiamy<br />\n" +
                "<span style=\"font-weight: 600; color: #231f20; font-size: 14px;\">\n" +
                "Zespół Master Dieta\n" +
                "</span>\n" +
                "</td>\n" +
                "<td width=\"20\"></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td height=\"30\" colspan=\"5\" width=\"600\">\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<table style=\"background-color: #f2f2f2\" width=\"100%\" border=\"0\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td height=\"30\" width=\"600\"></td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "</center>\n" +
                "</div>\n" +
                "</body>";
    }

    public void sendUpdateDiet(OrderDetailsData order, List<OrderProductCreateData> items) {
        if (order == null) {
            throw new IllegalArgumentException("order is null");
        }
        String data = getData(order, items, true);
        String deliveryText = getDeliveryText(order, items);

        Long templateId = 1L;
        if (order.getPaymentMethodId() == 1) templateId = 1L;
        if (order.getPaymentMethodId() == 2) templateId = 8L;
        if (order.getPaymentMethodId() == 3) templateId = 7L;

        TemplateData template = getTemplate(templateId, false);

        String contentEmail = template.getContentEmail();
        contentEmail = contentEmail.replace("_USER_", order.getCustomerForOrder().getFirstName() + " " + order.getCustomerForOrder().getLastName());
        contentEmail = contentEmail.replace("_EMAIL_", order.getCustomerForOrder().getEmail());
        contentEmail = contentEmail.replace("_NR_ZAMOWIENIA_", order.getOrderId());
        contentEmail = contentEmail.replace("_KWOTA_ZAMOWIENIA_", getOrderBasketSum(order) + " PLN");
        contentEmail = contentEmail.replace("_DANE_ZAMOWIENIA_", data + deliveryText);

        String html = getUpdateDietHtml(contentEmail);
        MailConfigData mailConfig = getMailConfig(); //imp cache
        String title = "Podsumowanie zamówienia";
        send(mailConfig, order.getCustomerForOrder().getEmail(), "admin@masterdieta.pl", title, html, null);
        EmailHistoryData lastHistoryEmail = getLastHistoryEmail(order.getCustomerForOrder().getEmail());
        if (lastHistoryEmail == null) {
            throw new IllegalArgumentException("Nie moge pobrać ostatniego maila dla " + order.getCustomerForOrder().getEmail());
        }
        log.debug("bef createOrderSentEmail order.getId(): " + order.getId() + " lastHistoryEmail.getId(): " + lastHistoryEmail.getId());
        createOrderSentEmail(order.getId(), lastHistoryEmail.getId(), title, html, authenticatedUser.getUserId());
    }

    private String getDeliveryText(OrderDetailsData order, List<OrderProductCreateData> items) {
        String deliveryText = "<b>Terminarz dostaw</b><br /><br /><table style=\"width: 100%; border-collapse: collapse;\"><tr>";
        deliveryText += "        <th style=\"border: 1px solid black; padding: 5px\">";
        deliveryText += "        Dieta";
        deliveryText += "        </th>";
        deliveryText += "<th style=\"border: 1px solid black; padding: 5px\">";
        deliveryText += "       Zakres dni";
        deliveryText += "        </th>";
        deliveryText += "</tr>";

        for (OrderProductCreateData item : items) {

        }

        deliveryText += "</table>";
        /*
	foreach($products as $product) {



		$DateArr = array();

		$qDelivery = mysqli_query( $db, "
			SELECT *,
				IF( sunday =1, DATE_ADD( delivery_date, INTERVAL 1 DAY ), delivery_date ) AS ddate
			FROM delivery_orders
			WHERE
				order_product_id = '". $product['id'] ."' ORDER BY ddate ASC ");

		while( $delivery = mysqli_fetch_assoc( $qDelivery ) ) {
			if( empty($DateArr) ) {
				$tmpDate = $delivery['ddate'];
				$DateArr[] = $tmpDate;
			} else {
				$ts1 = strtotime( $tmpDate );
				$ts2 = strtotime( $delivery['ddate'] );
				$resultD = ( $ts2 - $ts1 ) / 86400;
				$tmpDate = $delivery['ddate'];
				if( $resultD > 1 ) {
					$allDateArr[] = $DateArr;
					$DateArr = array();
				}
				if (!in_array($delivery['ddate'], $DateArr)) {
					$DateArr[] = $delivery['ddate'];
				}
			}
		}

		if( !empty( $DateArr ) ) {
			$allDateArr[] = $DateArr;
		}

		foreach( $allDateArr as $dateArr2 ) {
			$deliveryText .= '<tr>';
				$deliveryText .= '<td style="border: 1px solid black; padding: 5px; text-align: center">';
					$deliveryText .= getCategoryNameFromProduct($db, $product['product_id'], $_SESSION['lang']) . " " . $name;
				$deliveryText .= '</td>';
				$deliveryText .= '<td style="border: 1px solid black; padding: 5px; text-align: center">';
					if (count( $dateArr2 ) == 1) $deliveryText .= $dateArr2[0];
					else {
						$deliveryText .= $dateArr2[0] . " - " . $dateArr2[ count( $dateArr2 ) - 1 ];
					}
				$deliveryText .= '</td>';
			$deliveryText .= '</tr>';
		}
		$allDateArr = array();
	}



         */
        return deliveryText;
    }

    private String getUpdateDietHtml(String content) {
        return "<body style=\"background-color: #f2f2f2;\">\n" +
                "<div marginwidth=\"0\" marginheight=\"0\" style=\"background:#f2f2f2;margin:0;padding:0;width:100%!important\" bgcolor=\"#FAFAFA\">\n" +
                "<center>\n" +
                "<table style=\"background-color: #f2f2f2\" width=\"100%\" border=\"0\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td height=\"30\" width=\"600\"></td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border: 1px solid #333333;background-color: white;font-family: Tahoma;\">\n" +
                "<tr>\n" +
                "<td height=\"30\" colspan=\"5\" width=\"600\">\n" +
                "\n" +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td width=\"20\"></td>\n" +
                "<td height=\"85\" colspan=\"3\" width=\"556\" valign=\"top\" style=\"text-align: center\"><img src=\"https://masterdieta.pl/images/mail-banner.jpg\" height=\"100px\" width=\"500px\" /></td>\n" +
                "<td width=\"20\"></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td colspan=\"5\" width=\"600\" height=\"30\">\n" +
                "\n" +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td width=\"20\"></td>\n" +
                "<td height=\"60\" valign=\"top\" width=\"560\" colspan=\"3\" style=\"color: #4c4c4c; font-size: 15px;\">\n" +
                "" + content + "\n" +
                "</td>\n" +
                "<td width=\"20\"></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td width=\"20\"></td>\n" +
                "<td colspan=\"3\" valign=\"bottom\" width=\"560\" height=\"50\" style=\"font-size: 14px;\">\n" +
                "Pozdrawiamy<br />\n" +
                "<span style=\"font-weight: 600; color: #231f20; font-size: 14px;\">\n" +
                "Zespół Master Dieta\n" +
                "</span>\n" +
                "</td>\n" +
                "<td width=\"20\"></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td height=\"30\" colspan=\"5\" width=\"600\">\n" +
                "\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<table style=\"background-color: #f2f2f2\" width=\"100%\" border=\"0\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td height=\"30\" width=\"600\"></td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "</center>\n" +
                "</div>\n" +
                "</body>";
    }

    public void sendOrderNew(OrderDetailsData order, List<OrderProductCreateData> items) {
        if (order == null) {
            throw new IllegalArgumentException("order is null");
        }
        String title = "Nowe zamówienie";
        String data = getData(order, items,  false);
        TemplateData template = getTemplate(9L, false);

        String contentEmail = template.getContentEmail();
        contentEmail = contentEmail.replace("_USER_", order.getCustomerForOrder().getFirstName() + " " + order.getCustomerForOrder().getLastName());
        contentEmail = contentEmail.replace("_EMAIL_", order.getCustomerForOrder().getEmail());
        contentEmail = contentEmail.replace("_NR_ZAMOWIENIA_", order.getOrderId());
        contentEmail = contentEmail.replace("_KWOTA_ZAMOWIENIA_", getOrderBasketSum(order) + " PLN");
        contentEmail = contentEmail.replace("_DANE_ZAMOWIENIA_", data);

        String html = getOrderNewHtml(contentEmail);
        MailConfigData mailConfig = getMailConfig(); //imp cache

        String[] addresses = mailConfig.getNewOrderEmail().split(";");
        for (String address : addresses) {
            send(mailConfig, address, mailConfig.getDefaultEmail(), title, html, null);
        }
    }

    private String getOrderNewHtml(String content) {
        return "<body style=\"background-color: #f2f2f2;\">\n" +
                "<div marginwidth=\"0\" marginheight=\"0\" style=\"background:#f2f2f2;margin:0;padding:0;width:100%!important\" bgcolor=\"#FAFAFA\">\n" +
                "<center>\n" +
                "<table style=\"background-color: #f2f2f2\" width=\"100%\" border=\"0\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td height=\"30\" width=\"600\"></td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border: 1px solid #333333;background-color: white;font-family: Tahoma;\">\n" +
                "<tr>\n" +
                "<td height=\"30\" colspan=\"5\" width=\"600\">\n" +
                "\n" +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td width=\"20\"></td>\n" +
                "<td height=\"85\" colspan=\"3\" width=\"556\" valign=\"top\" style=\"text-align: center\"><img src=\"https://masterdieta.pl/images/mail-banner.jpg\" height=\"100px\" width=\"500px\" /></td>\n" +
                "<td width=\"20\"></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td colspan=\"5\" width=\"600\" height=\"30\">\n" +
                "\n" +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td width=\"20\"></td>\n" +
                "<td height=\"60\" valign=\"top\" width=\"560\" colspan=\"3\" style=\"color: #4c4c4c; font-size: 15px;\">\n" +
                "" + content + "\n" +
                "</td>\n" +
                "<td width=\"20\"></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td width=\"20\"></td>\n" +
                "<td colspan=\"3\" valign=\"bottom\" width=\"560\" height=\"50\" style=\"font-size: 14px;\">\n" +
                "Pozdrawiamy<br />\n" +
                "<span style=\"font-weight: 600; color: #231f20; font-size: 14px;\">\n" +
                "Zespół Master Dieta\n" +
                "</span>\n" +
                "</td>\n" +
                "<td width=\"20\"></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td height=\"30\" colspan=\"5\" width=\"600\">\n" +
                "\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<table style=\"background-color: #f2f2f2\" width=\"100%\" border=\"0\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td height=\"30\" width=\"600\"></td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "</center>\n" +
                "</div>\n" +
                "</body>";
    }

    public void sendOrderRequested(OrderDetailsData order, List<OrderProductCreateData> items) {
        if (order == null) {
            throw new IllegalArgumentException("order is null");
        }
        String title = "Złożenie zamówienia";
        String data = getData(order, items, false);

        Long templateId = 1L;
        if (order.getPaymentMethodId() == 1) templateId = 1L;
        if (order.getPaymentMethodId() == 2) templateId = 8L;
        if (order.getPaymentMethodId() == 3) templateId = 7L;

        TemplateData template = getTemplate(templateId, false);

        String contentEmail = template.getContentEmail();
        contentEmail = contentEmail.replace("_USER_", order.getCustomerForOrder().getFirstName() + " " + order.getCustomerForOrder().getLastName());
        contentEmail = contentEmail.replace("_EMAIL_", order.getCustomerForOrder().getEmail());
        contentEmail = contentEmail.replace("_NR_ZAMOWIENIA_", order.getOrderId());
        contentEmail = contentEmail.replace("_KWOTA_ZAMOWIENIA_", getOrderBasketSum(order) + " PLN");
        contentEmail = contentEmail.replace("_DANE_ZAMOWIENIA_", data);

        String html = getOrderRequestedHtml(contentEmail);
        MailConfigData mailConfig = getMailConfig();
        send(mailConfig, order.getCustomerForOrder().getEmail(), mailConfig.getDefaultEmail(), title, html, null);

        EmailHistoryData lastHistoryEmail = getLastHistoryEmail(order.getCustomerForOrder().getEmail());
        if (lastHistoryEmail == null) {
            throw new IllegalArgumentException("Nie moge pobrać ostatniego maila dla " + order.getCustomerForOrder().getEmail());
        }
        log.debug("bef createOrderSentEmail order.getId(): " + order.getId() + " lastHistoryEmail.getId(): " + lastHistoryEmail.getId());
        createOrderSentEmail(order.getId(), lastHistoryEmail.getId(), title, html, authenticatedUser.getUserId());
    }

    private String getOrderRequestedHtml(String content) {
        String html = "<body style=\"background-color: #f2f2f2;\">" +
                "            <div marginwidth=\"0\" marginheight=\"0\" style=\"background:#f2f2f2;margin:0;padding:0;width:100%!important\" bgcolor=\"#FAFAFA\">" +
                "                <center>" +
                "                    <table style=\"background-color: #f2f2f2\" width=\"100%\" border=\"0\">" +
                "                                <tbody>" +
                "                                        <tr>" +
                "                                                <td height=\"30\" width=\"600\"></td>\n" +
                "                                        </tr>" +
                "                                </tbody>" +
                "                        </table>" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border: 1px solid #333333;background-color: white;font-family: Tahoma;\">" +
                "            <tr>" +
                "                <td height=\"30\" colspan=\"5\" width=\"600\">" +
                "" +
                "                </td>" +
                "            </tr>" +
                "            <tr>" +
                "                    <td width=\"20\"></td>" +
                "                    <td height=\"85\" colspan=\"3\" width=\"556\" valign=\"top\" style=\"text-align: center\"><img src=\"https://masterdieta.pl/images/mail-banner.jpg\" height=\"100px\" width=\"500px\" /></td>" +
                "                    <td width=\"20\"></td>" +
                "            </tr>" +
                "            <tr>" +
                "                <td colspan=\"5\" width=\"600\" height=\"30\">" +
                "" +
                "                </td>" +
                "            </tr>" +
                "            <tr>" +
                "                <td width=\"20\"></td>" +
                "                <td height=\"60\" valign=\"top\" width=\"560\" colspan=\"3\" style=\"color: #4c4c4c; font-size: 15px;\">" +
                "                    " + content + "" +
                "                </td>" +
                "                <td width=\"20\"></td>" +
                "            </tr>" +
                "            <tr>" +
                "                <td width=\"20\"></td>" +
                "                <td colspan=\"3\" valign=\"bottom\" width=\"560\" height=\"50\" style=\"font-size: 14px;\">" +
                "                Pozdrawiamy<br />" +
                "                    <span style=\"font-weight: 600; color: #231f20; font-size: 14px;\">" +
                "                    Zespół Master Dieta" +
                "                    </span>" +
                "                </td>" +
                "                <td width=\"20\"></td>" +
                "            </tr>" +
                "            <tr>" +
                "                <td height=\"30\" colspan=\"5\" width=\"600\">" +
                "\n" +
                "                </td>" +
                "            </tr>" +
                "        </table>" +
                "        <table style=\"background-color: #f2f2f2\" width=\"100%\" border=\"0\">" +
                "                    <tbody>" +
                "                            <tr>" +
                "                                    <td height=\"30\" width=\"600\"></td>" +
                "                            </tr>" +
                "                    </tbody>" +
                "            </table>" +
                "        </center>" +
                "        </div>" +
                "        </body>";
        return html;
    }

    private String getData(OrderDetailsData order, List<OrderProductCreateData> items, boolean fromAndTo) {
        OrderCustomerData customer = order.getCustomerForOrder();
        OrderWeekendAddressData weekendAddress = order.getOrderWeekendAddress();
        String data = "";
        data += "<b>Dane zamówienia</b><br /><br />";
        data += "Dane dostawy<br /><br />";
        data += "<b>Imię</b>: " + order.getCustomerForOrder().getFirstName() + "<br />";
        data += "<b>Nazwisko</b>: " + order.getCustomerForOrder().getLastName() + "<br />";
        data += "<b>Godziny dostawy</b>: do " + order.getHoursTo() + "<br />";
        if (order.getWeekendHoursStatus())
            data += "<b>Godziny dostawy w weekendy</b>: do " + order.getWeekendHoursTo() + "<br />";
        data += "<b>Adres dostawy</b>: " + order.getCustomerForOrder().getCityName() +
                ", ul. " + order.getCustomerForOrder().getStreet() + " " + order.getCustomerForOrder().getBuildingNumber();
        if (customer.getApartmentNumber() != null && !customer.getApartmentNumber().isEmpty())
            data += "/" + customer.getApartmentNumber();
        data += "<br />";
        if (weekendAddress.getWeekendAddressStatus()) {
            data += "<b>Adres dostawy w weekendy</b>: " + weekendAddress.getCityName() + ", ul. " + weekendAddress.getStreet() + " " + weekendAddress.getBuildingNumber();
            if (weekendAddress.getApartmentNumber() != null && !weekendAddress.getApartmentNumber().isEmpty())
                data += "/" + weekendAddress.getApartmentNumber();
        }
        data += "<br />Produkty<br /><br />";
        for (OrderProductCreateData item : items) {
            String productName = productsService.getProductNameInLanguage(item.getDietTypeId(), Language.PL.getCode()); // IMP Move from here
            String dietName = dictionariesService.getDictionaryValueById(item.getDietId(), DictionaryType.DIETS, Language.PL);
            data += "<b>Dieta</b>: " + dietName + " " + productName + "<br />";
            if (item.getTestDay()) {
                data += "<b>Na ile</b>: Jeden dzień testowy<br />";
            } else {
                data += "<b>Na ile</b>: " + item.getDays() + " dni<br />";
            }
            if (fromAndTo) {
                data += "<b>Od kiedy</b>: " + deliveryService.getMinDeliveryDateForProductInOrder(item.getOrderProductId()) + "<br />";
                data += "<b>Do kiedy</b>: " + deliveryService.getMaxDeliveryDateForProductInOrder(item.getOrderProductId())  + "<br />";
            } else {
                data += "<b>Od kiedy</b>: " + item.getDateFrom() + "<br />";
            }
            data += "<b>Weekendy</b>: " + dictionariesService.getDictionaryValueById(item.getWeekendOptionId(), DictionaryType.WEEKEND_OPTIONS, Language.PL) + "<br />";
            data += "<b>Ilość zestawów</b>: " + item.getQuantity() + "<br />";
            data += "<br />";


        }
        return data;
    }

    public MailConfigData getMailConfig() {
        return emailsJdbcRepository.getMailConfig();
    }

    public EmailHistoryData getLastHistoryEmail(String email) {
        return emailsJdbcRepository.getLastHistoryEmail(email);
    }
}

