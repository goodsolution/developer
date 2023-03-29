package pl.com.goodsolution.adviser.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.goodsolution.adviser.domain.*;

import java.time.LocalDateTime;

@Service
@Transactional
class EmailsJdbcRepository {
    private static final Logger log = LoggerFactory.getLogger(EmailsJdbcRepository.class);
    private JdbcTemplate jdbcTemplate;

    public EmailsJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    void createOrderSentEmail(Long orderId, Long historyId, String title, String message, Long sendBy) {
        String query = "" +
                "INSERT INTO order_mail_sends (order_id, history_id, title, message, send_by, date) " +
                "VALUES(?, ?, ?, ?, ?, ?)";

        log.trace(query);
        jdbcTemplate.update(
                query,
                orderId,
                historyId,
                title,
                message,
                sendBy,
                LocalDateTime.now()
        );
    }

    void createSentEmailHistory(String sender,  String recipient, String title,  String message) {
        String query = "" +
                "INSERT INTO history_mail (sender, recipient, title, message, date) " +
                "VALUES(?, ?, ?, ?, ?)";

        log.trace(query);
        jdbcTemplate.update(
                query,
                sender,
                recipient,
                title,
                message,
                LocalDateTime.now()
        );
    }

    TemplateData getTemplate(Long templateId) {
        String query = "SELECT * FROM templates WHERE template_id = ?";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{templateId},
                    (rs, rowNum) -> {
                        TemplateData data = new TemplateData(
                                rs.getLong("template_id"),
                                rs.getString("template_name"),
                                rs.getString("template_email"),
                                rs.getString("template_sms"),
                                rs.getString("send_email").equals("1") ? true : false,
                                rs.getString("send_sms").equals("1") ? true : false

                        );
                        return data;
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    MailConfigData getMailConfig() {
        String query = "SELECT * FROM mail_config WHERE id = 1";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query,
                    (rs, rowNum) -> {
                        MailConfigData data = new MailConfigData(
                                rs.getLong("id"),
                                rs.getString("type"),
                                rs.getString("smtp_server"),
                                rs.getString("default_email"),
                                rs.getLong("port"),
                                rs.getString("encryption"),
                                rs.getString("user"),
                                rs.getString("password"),
                                rs.getString("header_from"),
                                rs.getString("new_order_email"),
                                rs.getString("smtp_auth"),
                                rs.getString("export_email"),
                                rs.getString("test_email")
                        );
                        return data;
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    EmailHistoryData getLastHistoryEmail(String email) {
        String query = "SELECT * FROM history_mail WHERE recipient = ? ORDER BY id DESC LIMIT 1";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{email},
                    (rs, rowNum) -> {
                        EmailHistoryData data = new EmailHistoryData(
                                rs.getLong("id")
                        );
                        return data;
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
        //$q = mysqli_query($db, "SELECT * FROM history_mail WHERE recipient = '".$logged_buyer_email."' ORDER BY id DESC LIMIT 1");
//        $history = mysqli_fetch_assoc($q);
//        $data['order_id'] = $order_id;
//        $data['title'] = $title;
//        $data['msg'] = $html;
//        $data['history_id'] = $history['id'];
//        $data['send_by'] = $_SESSION['admin_login']['id']

    }

}
