package pl.com.mike.developer.logic.adviser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.adviser.ContextConfigData;
import pl.com.mike.developer.domain.adviser.ContextConfigsFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.mike.developer.logic.adviser.JdbcUtil.*;

@Service
public class ContextConfigsJdbcRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(ContextConfigsJdbcRepository.class);

    public ContextConfigsJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    void create(ContextConfigData data) {
        String query = "INSERT INTO context_configs (application_id, context, name, type, value) values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, data.getApplicationId(), data.getContext(), data.getName(), data.getType(), data.getValue());
    }

    ContextConfigData get(Long id) {
        String query = "SELECT * FROM context_configs WHERE id = ?;";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{id},
                    (rs, rowNum) -> new ContextConfigData(
                            getLong(rs, "id"),
                            JdbcUtil.getString(rs, "application_id"),
                            JdbcUtil.getString(rs, "context"),
                            JdbcUtil.getString(rs, "name"),
                            JdbcUtil.getString(rs, "type"),
                            JdbcUtil.getString(rs, "value")
                    ));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    List<ContextConfigData> find(ContextConfigsFilter filter) {

        String query = "SELECT * FROM context_configs";

        if(filter != null) {

            query += " WHERE 1=1";

            if (filter.getApplicationId() != null && !filter.getApplicationId().isEmpty()) {
                query += " AND application_id LIKE '%" + filter.getApplicationId() + "%'";
            }

            if (filter.getContext() != null && !filter.getContext().isEmpty()) {
                query += " AND context LIKE '%" + filter.getContext() + "%'";
            }

            if (filter.getName() != null && !filter.getName().isEmpty()) {
                query += " AND name LIKE '%" + filter.getName() + "%'";
            }

            if (filter.getType() != null && !filter.getType().isEmpty()) {
                query += " AND type LIKE '%" + filter.getType() + "%'";
            }

            query += preparePaginationQuery(filter.getPage(), filter.getPageSize());

        }

        log.trace(query);

        return prepareContextConfigsData(jdbcTemplate.queryForList(query));
    }

    void update(ContextConfigData data) {
        String query = "UPDATE context_configs SET application_id = ?, context = ?, name = ?, type = ?, value = ? WHERE id = ?";
        jdbcTemplate.update(query, data.getApplicationId(), data.getContext(), data.getName(), data.getType(), data.getValue(), data.getId());
    }

    void delete(Long id) {
        String query = "DELETE FROM context_configs WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    Long getTotalContextConfigsCount() {
        String query = "SELECT COUNT(id) AS count FROM context_configs;";
        try {
            return jdbcTemplate.queryForObject(query, (rs, rowNum) -> getLong(rs, "count"));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    private List<ContextConfigData> prepareContextConfigsData(List<Map<String, Object>> rows) {
        List<ContextConfigData> list = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            list.add(new ContextConfigData(
                    getLong(row, "id"),
                    getString(row, "application_id"),
                    getString(row, "context"),
                    getString(row, "name"),
                    getString(row, "type"),
                    getString(row, "value")
            ));
        }
        return list;
    }
}
