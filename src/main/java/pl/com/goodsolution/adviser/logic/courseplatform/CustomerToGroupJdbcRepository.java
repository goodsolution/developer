package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.courseplatform.CustomerGroupData;
import pl.com.goodsolution.adviser.domain.courseplatform.CustomerGroupsFilter;
import pl.com.goodsolution.adviser.domain.courseplatform.CustomerToGroupData;
import pl.com.goodsolution.adviser.domain.courseplatform.CustomerToGroupFilter;
import pl.com.goodsolution.adviser.logic.CommonJdbcRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.goodsolution.adviser.logic.adviser.JdbcUtil.*;

@Service
public class CustomerToGroupJdbcRepository {

    private JdbcTemplate jdbcTemplate;
    private CommonJdbcRepository commonJdbcRepository;

    public CustomerToGroupJdbcRepository(JdbcTemplate jdbcTemplate, CommonJdbcRepository commonJdbcRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.commonJdbcRepository = commonJdbcRepository;
    }

    public Long create(CustomerToGroupData data) {
        String query = "INSERT INTO crs_customer_to_group (customer_id, customer_group_id) values (?, ?);";
        jdbcTemplate.update(query, data.getCustomerId(), data.getCustomerGroupId());
        return commonJdbcRepository.getLastInsertedId();
    }

    public List<CustomerToGroupData> find(CustomerToGroupFilter filter) {
        List<CustomerToGroupData> customerToGroups = new ArrayList<>();
        String query = "SELECT * FROM crs_customer_to_group";

        if(filter != null) {
            query += " WHERE 1=1 ";
            if(filter.getId() != null) {
                query += " AND id = " + filter.getId();
            }
            if (filter.getCustomerId() != null){
                query += " AND customer_id = " + filter.getCustomerId();
            }
            query += preparePaginationQuery(filter.getPage(), filter.getPageSize());
        }

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        for (Map<String, Object> row : rows) {
            customerToGroups.add(new CustomerToGroupData(
                    getLong(row, "id"),
                    getLong(row, "customer_id"),
                    getLong(row, "customer_group_id")
            ));
        }
        return customerToGroups;
    }

    public void update(CustomerToGroupData data) {
        String query = "UPDATE crs_customer_to_group SET customer_id = ?, group_id = ? WHERE id = ?;";
        jdbcTemplate.update(query, data.getCustomerId(), data.getCustomerGroupId(), data.getId());
    }

    public void delete(Long id) {
            String query = "DELETE FROM crs_customer_to_group WHERE customer_id = ?;";
            jdbcTemplate.update(query, id);
    }
}
