package pl.com.goodsolution.adviser.logic;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.ViewerData;

@Service
public class ViewersJdbcRepository {
    private JdbcTemplate jdbcTemplate;

    public ViewersJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    ViewerData get(String sessionId) {
        try {
            String query = "SELECT * FROM viewers WHERE SESSION_ID = ? ORDER BY id ASC LIMIT 1";
            return jdbcTemplate.queryForObject(query, new Object[]{sessionId},
                    (rs, rowNum) -> new ViewerData(rs.getLong("id"), rs.getString("SESSION_ID")));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
}
