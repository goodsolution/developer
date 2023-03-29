package pl.com.goodsolution.adviser.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CommonJdbcRepository {
    private static final Logger log = LoggerFactory.getLogger(CommonJdbcRepository.class);
    private JdbcTemplate jdbcTemplate;

    public CommonJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long getLastInsertedId() {
        String query;
        query = "select last_insert_id() ";
        log.trace(query);
        return jdbcTemplate.queryForObject(query, Long.class);
    }

}
