package pl.com.mike.developer.logic.dietetics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.AllergenData;

import java.util.List;
import java.util.Map;

@Service
class DieteticsJdbcRepository {
    private static final Logger log = LoggerFactory.getLogger(DieteticsJdbcRepository.class);
    private JdbcTemplate jdbcTemplate;

    public DieteticsJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    List<Map<String, Object>> findAllergens() {
        String query = "SELECT * FROM dietetics_allergen_list WHERE table_show = 1";
        log.trace(query);

        return jdbcTemplate.queryForList(query);
    }

    void updateAllergen(AllergenData data) {
        String query = "UPDATE dietetics_allergen_list SET name = ? WHERE allergen_id = ?";
        log.trace(query);
        jdbcTemplate.update(
                query,
                data.getName(),
                data.getId()
        );

    }

    void createAllergen(AllergenData data) {
        String query = "INSERT INTO dietetics_allergen_list (name)" +
                " VALUES(?)";

        log.trace(query);
        jdbcTemplate.update(
                query,
                data.getName()
        );
    }

    void deleteAllergen(Long id) {
        String query = "UPDATE dietetics_allergen_list SET table_show = 0 WHERE allergen_id = ?";
        log.trace(query);
        jdbcTemplate.update(
                query,
                id
        );
    }
}
