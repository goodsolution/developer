package pl.com.mike.developer.logic.instagram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.mike.developer.domain.InstagramData;

import java.time.LocalDateTime;

@Service
@Transactional
class InstagramJdbcRepository {
    private static final Logger log = LoggerFactory.getLogger(InstagramJdbcRepository.class);
    private JdbcTemplate jdbcTemplate;

    public InstagramJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    void create(InstagramData data) {
        String query = "" +
                "INSERT INTO instagram_images " +
                "(href, src, alt, title, refresh_date) " +
                "VALUES (?, ?, ?, ?, ?)  ";

        //log.trace(query);
        jdbcTemplate.update(
                query,
                data.getHref(),
                data.getSrc(),
                data.getAlt(),
                data.getTitle(),
                LocalDateTime.now()
        );
    }

    int removeAll() {
        return jdbcTemplate.update("DELETE FROM instagram_images");
    }

}
