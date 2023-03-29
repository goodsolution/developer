package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.courseplatform.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.goodsolution.adviser.logic.adviser.JdbcUtil.*;

@Service
public class PasswordResetTokensJdbcRepository {

    private JdbcTemplate jdbcTemplate;
    private CourseCustomersService courseCustomersService;

    public PasswordResetTokensJdbcRepository(JdbcTemplate jdbcTemplate, CourseCustomersService courseCustomersService) {
        this.jdbcTemplate = jdbcTemplate;
        this.courseCustomersService = courseCustomersService;
    }

    void create (PasswordResetTokenData data) {
        String query = "INSERT INTO crs_password_reset_tokens (customer_id, value, expiration_datetime, used) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(query, data.getCustomer().getId(), data.getValue(), data.getExpirationDatetime(), data.getUsed());
    }

    List<PasswordResetTokenData> find(PasswordResetTokensFilter filter) {

        String query = "SELECT * FROM crs_password_reset_tokens";

        if(filter != null) {

            query += " WHERE 1=1";

            if(filter.getValue() != null) {
                query += " AND value = '" + filter.getValue() + "'";
            }

        }


        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        List<PasswordResetTokenData> list = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            list.add(new PasswordResetTokenData(
                    getLong(row, "id"),
                    courseCustomersService.find(new CustomersFilter(getLong(row, "customer_id"))).get(0),
                    getString(row, "value"),
                    getDateTime(row, "expiration_datetime"),
                    getBoolean(row, "used")
            ));
        }
        return list;
    }

    void update(PasswordResetTokenData data) {
        String query = "UPDATE crs_password_reset_tokens SET customer_id = ?, value = ?, expiration_datetime = ?, used = ? WHERE id = ?";
        jdbcTemplate.update(query, data.getCustomer().getId(), data.getValue(), data.getExpirationDatetime(), data.getUsed(), data.getId());
    }
}
