package pl.com.mike.developer.logic.adviser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.adviser.AdviseDataType;
import pl.com.mike.developer.domain.adviser.ContextVariableData;
import pl.com.mike.developer.domain.adviser.ContextVariablesFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.mike.developer.logic.adviser.JdbcUtil.*;

@Service
public class ContextVariablesJdbcRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(ContextVariablesJdbcRepository.class);

    public ContextVariablesJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    void create(ContextVariableData data) {
        String query = "INSERT INTO context_vars (" +
                "    application_id ,\n" +
                "    context,\n" +
                "    name,\n" +
                "    type,\n" +
                "    value," +
                "    context_id, " +
                "    customer_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, data.getApplicationId(), data.getContext(), data.getName(), data.getType().name(), data.getValue(), data.getContextId(), data.getCustomerId());
    }

    void update(ContextVariableData data) {
        String query = "UPDATE context_vars SET application_id = ?, context = ?, name = ?, type = ?, value = ?, context_id = ? WHERE id = ?";
        jdbcTemplate.update(query, data.getApplicationId(), data.getContext(), data.getName(), data.getType().name(), data.getValue(), data.getContextId(), data.getId());
    }

    List<ContextVariableData> find(ContextVariablesFilter filter) {

        String query = "SELECT * FROM context_vars";

        if(filter != null) {

            query += " WHERE 1=1";

            if (filter.getApplicationId() != null && !filter.getApplicationId().isEmpty()) {
                query += " AND application_id = '" + filter.getApplicationId() + "'";
            }

            if (filter.getContext() != null && !filter.getContext().isEmpty()) {
                query += " AND context = '" + filter.getContext() + "'";
            }

            if (filter.getName() != null && !filter.getName().isEmpty()) {
                query += " AND name = '" + filter.getName() + "'";
            }

            if (filter.getType() != null && !filter.getType().isEmpty()) {
                query += " AND type = '" + filter.getType() + "'";
            }

            if (filter.getContextId() != null  && !filter.getContextId().isEmpty()) {
                query += " AND context_id = '" + filter.getContextId() + "'";
            }

            if (filter.getCustomerId() != null) {
                query += " AND customer_id = '" + filter.getCustomerId() + "'";
            }

            query += preparePaginationQuery(filter.getPage(), filter.getPageSize());

        }

        log.trace(query);

        return prepareContextVariablesData(jdbcTemplate.queryForList(query));
    }

    Long getTotalContextVariablesCount() {
        String query = "SELECT COUNT(id) AS count FROM context_vars;";
        try {
            return jdbcTemplate.queryForObject(query, (rs, rowNum) -> getLong(rs, "count"));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    private List<ContextVariableData> prepareContextVariablesData(List<Map<String, Object>> rows) {
        List<ContextVariableData> list = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            list.add(new ContextVariableData(
                    getLong(row, "id"),
                    getString(row, "application_id"),
                    getString(row, "context"),
                    getString(row, "name"),
                    AdviseDataType.valueOf(getString(row, "type")),
                    getString(row, "value"),
                    getString(row, "context_id"),
                    getLong(row, "customer_id")));
        }
        return list;
    }
}
