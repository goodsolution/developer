package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.courseplatform.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.goodsolution.adviser.logic.adviser.JdbcUtil.*;

@Service
public class CustomerAuthoritiesJdbcRepository {

    private JdbcTemplate jdbcTemplate;
    private CourseCustomersJdbcRepository courseCustomersJdbcRepository;

    public CustomerAuthoritiesJdbcRepository(JdbcTemplate jdbcTemplate, CourseCustomersJdbcRepository courseCustomersJdbcRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.courseCustomersJdbcRepository = courseCustomersJdbcRepository;
    }

    void create(CustomerAuthorityData data) {
        String query = "INSERT INTO crs_customer_authorities (customer_id, authority) values (?, ?)";
        for (int i = 0; i < data.getAuthorities().length; i++) {
            jdbcTemplate.update(query, data.getCustomer().getId(), data.getAuthorities()[i]);
        }
    }

    void deleteAuthoritiesFromCustomer(CustomerAuthoritiesFilter filter) {
        String query = "DELETE FROM crs_customer_authorities WHERE customer_id =" + filter.getCustomerId();
        jdbcTemplate.update(query);
    }

    List<CustomerAuthorityData> find(CustomerAuthoritiesFilter filter) {

        List<CustomerAuthorityData> authorities = new ArrayList<>();

        String query = "SELECT * FROM crs_customer_authorities";

        if (filter != null) {
            query += " WHERE 1=1";

            if (filter.getAuthority() != null) {
                query += " AND authority = '" + filter.getAuthority() + "'";
            }

            if (filter.getCustomerId() != null) {
                query += " AND customer_id = " + filter.getCustomerId();
            }
        }

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        prepareAuthorities(authorities, rows);
        return authorities;
    }

    private void prepareAuthorities(List<CustomerAuthorityData> authorities, List<Map<String, Object>> rows) {
        for (Map<String, Object> row : rows) {
            authorities.add(new CustomerAuthorityData(
                    getLong(row, "id"),
                    courseCustomersJdbcRepository.find(new CustomersFilter(getLong(row, "customer_id"))).get(0),
                    getString(row, "authority")
            ));
        }
    }
}
