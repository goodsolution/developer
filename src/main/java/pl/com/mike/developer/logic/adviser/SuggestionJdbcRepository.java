package pl.com.mike.developer.logic.adviser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.adviser.SuggestionData;

@Service
public class SuggestionJdbcRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(SuggestionJdbcRepository.class);

    public SuggestionJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    void create(SuggestionData data) {
        String query = "INSERT INTO suggestions (application_id, domain, domain_id, suggestion) value (?, ?, ?, ?)";
        jdbcTemplate.update(query, data.getApplicationId(), data.getDomain(), data.getDomainId(), data.getSuggestion());
    }


}
