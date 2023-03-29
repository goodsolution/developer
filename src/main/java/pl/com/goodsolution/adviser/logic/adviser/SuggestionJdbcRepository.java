package pl.com.goodsolution.adviser.logic.adviser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.adviser.AccountData;
import pl.com.goodsolution.adviser.domain.adviser.AccountsFilter;
import pl.com.goodsolution.adviser.domain.adviser.SuggestionData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.goodsolution.adviser.logic.adviser.JdbcUtil.*;

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
