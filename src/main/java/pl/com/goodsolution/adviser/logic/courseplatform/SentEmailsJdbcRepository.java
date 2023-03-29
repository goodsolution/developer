package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.courseplatform.SentEmailData;

@Service
public class SentEmailsJdbcRepository {

    private JdbcTemplate jdbcTemplate;

    public SentEmailsJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    void create(SentEmailData data) {
        String query = "insert into crs_sent_emails (order_id, customer_id, title, content, language, event, create_datetime) values (?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(query, data.getOrderId(), data.getCustomerId(), data.getTitle(), data.getContent(), data.getLanguage(), data.getEvent(), data.getCreateDatetime());
    }
}
