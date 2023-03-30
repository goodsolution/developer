package pl.com.mike.developer.logic.adviser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.adviser.ApplicationData;
import pl.com.mike.developer.domain.adviser.ApplicationsFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ApplicationsJdbcRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(AccountsJdbcRepository.class);

    public ApplicationsJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    void create(ApplicationData data) {
        String query = "INSERT INTO applications (application_id, description, secret_key) values (?, ?, ?)";
        jdbcTemplate.update(query, data.getApplicationId(), data.getDescription(), data.getSecretKey());
    }

    ApplicationData get(Long id) {
        String query = "SELECT * FROM applications WHERE id = ?;";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{id},
                    (rs, rowNum) -> new ApplicationData(
                            JdbcUtil.getLong(rs, "id"),
                            JdbcUtil.getString(rs, "application_id"),
                            JdbcUtil.getString(rs, "description"),
                            JdbcUtil.getString(rs, "secret_key")
                    ));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    List<ApplicationData> find(ApplicationsFilter filter) {

        String query = "SELECT * FROM applications";

        if(filter != null) {

            query += " WHERE 1=1";

            if (filter.getApplicationIdAsSubstring() != null && !filter.getApplicationIdAsSubstring().isEmpty()) {
                query += " AND application_id = '" + filter.getApplicationIdAsSubstring() + "'";
            }
            if (filter.getApplicationId() != null && !filter.getApplicationId().isEmpty()) {
                query += " AND application_id = '" + filter.getApplicationId() + "'";
            }
            if (filter.getSecret() != null) {
                query += " AND secret_key = '" + filter.getSecret() + "'";
            }

            query += JdbcUtil.preparePaginationQuery(filter.getPage(), filter.getPageSize());

        }

        return prepareApplicationsData(jdbcTemplate.queryForList(query));
    }

    void update(ApplicationData data) {
        String query = "UPDATE applications SET application_id = ?, description = ?, secret_key = ? WHERE id = ?";
        jdbcTemplate.update(query, data.getApplicationId(), data.getDescription(), data.getSecretKey(), data.getId());
    }

    void delete(Long id) {
        String query = "DELETE FROM applications WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    Long getTotalApplicationsCount() {
        String query = "SELECT COUNT(id) AS count FROM applications;";
        try {
            return jdbcTemplate.queryForObject(query, (rs, rowNum) -> JdbcUtil.getLong(rs, "count"));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    Boolean checkIfSecretKeyTaken(String secretKey) {
        String query = "SELECT COUNT(id) as count FROM applications WHERE secret_key = ?;";
        Long count;
        try {
            count = jdbcTemplate.queryForObject(query, new Object[]{secretKey},(rs, rowNum) -> JdbcUtil.getLong(rs, "count"));
            return count != 0;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    private List<ApplicationData> prepareApplicationsData(List<Map<String, Object>> rows) {
        List<ApplicationData> list = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            list.add(new ApplicationData(
                    JdbcUtil.getLong(row, "id"),
                    JdbcUtil.getString(row, "application_id"),
                    JdbcUtil.getString(row, "description"),
                    JdbcUtil.getString(row, "secret_key")
            ));
        }
        return list;
    }
}
