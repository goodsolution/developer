package pl.com.mike.developer.logic.courseplatform;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.courseplatform.CustomerGroupData;
import pl.com.mike.developer.domain.courseplatform.CustomerGroupsFilter;
import pl.com.mike.developer.logic.CommonJdbcRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.mike.developer.logic.adviser.JdbcUtil.*;

@Service
public class CustomerGroupsJdbcRepository {

    private JdbcTemplate jdbcTemplate;
    private CommonJdbcRepository commonJdbcRepository;

    public CustomerGroupsJdbcRepository(JdbcTemplate jdbcTemplate, CommonJdbcRepository commonJdbcRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.commonJdbcRepository = commonJdbcRepository;
    }

    public Long create(CustomerGroupData data) {
        String query = "INSERT INTO crs_customer_groups (create_datetime, name) values (?, ?);";
        jdbcTemplate.update(query, data.getCreateDataTime(), data.getName());
        return commonJdbcRepository.getLastInsertedId();
    }

    public List<CustomerGroupData> find(CustomerGroupsFilter filter) {
        List<CustomerGroupData> customerGroups = new ArrayList<>();
        String query = "SELECT * FROM crs_customer_groups";

        if(filter != null) {
            query += " WHERE delete_datetime is NULL";
            if(filter.getId() != null) {
                query += " AND id = " + filter.getId();
            }
            if (filter.getDeleted() != null) {
                if (filter.getDeleted()) {
                    query += " AND delete_datetime IS NOT NULL";
                } else {
                    query += " AND delete_datetime IS NULL";
                }
            }
            query += preparePaginationQuery(filter.getPage(), filter.getPageSize());
        }


        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        for (Map<String, Object> row : rows) {
            customerGroups.add(new CustomerGroupData(
                    getLong(row, "id"),
                    getDateTime(row, "create_datetime"),
                    getString(row, "name")
            ));
        }
        return customerGroups;
    }

    public void update(CustomerGroupData data) {
        String query = "UPDATE crs_customer_groups SET name = ? WHERE id = ?;";
        jdbcTemplate.update(query, data.getName(), data.getId());
    }

    public void delete(Long id) {
        String query = "UPDATE crs_customer_groups SET delete_datetime = ? WHERE id = ?;";
        jdbcTemplate.update(query, LocalDateTime.now(), id);
    }
}
