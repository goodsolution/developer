package pl.com.mike.developer.logic.courseplatform;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.courseplatform.CustomerData;
import pl.com.mike.developer.domain.courseplatform.CustomersFilter;
import pl.com.mike.developer.domain.courseplatform.TokenData;

import java.util.List;

import static pl.com.mike.developer.logic.adviser.JdbcUtil.*;

@Service
public class TokensJdbcRepository {

    private JdbcTemplate jdbcTemplate;
    private CourseCustomersJdbcRepository courseCustomersJdbcRepository;

    public TokensJdbcRepository(JdbcTemplate jdbcTemplate, CourseCustomersJdbcRepository courseCustomersJdbcRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.courseCustomersJdbcRepository = courseCustomersJdbcRepository;
    }

    void create(TokenData data) {

        Long customerId;
        if(data.getCustomer() != null) {
            customerId = data.getCustomer().getId();
        } else {
            customerId = null;
        }

        String query = "INSERT INTO crs_tokens (value, expiration_datetime, customer_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, data.getValue(), data.getExpirationDatetime(), customerId);
    }

    TokenData get(String value) {
        try {
            String query = "SELECT * FROM crs_tokens WHERE value = ?;";
            return jdbcTemplate.queryForObject(query, new Object[]{value},
                    (rs, rowNum) -> {

                        Long customerId = getLong(rs, "customer_id");
                        CustomerData customer;
                        if (customerId == null) {
                            customer = null;
                        } else {
                            List<CustomerData> customers = courseCustomersJdbcRepository.find(new CustomersFilter(customerId));
                            if(customers.size() == 1) {
                                customer = customers.get(0);
                            } else {
                                throw new IllegalArgumentException("Found more than one customer with ID: " + customerId);
                            }
                        }

                        return new TokenData(
                                getLong(rs, "id"),
                                getString(rs, "value"),
                                getDateTime(rs, "expiration_datetime"),
                                customer
                        );
                    });
        } catch (EmptyResultDataAccessException ex) {
            throw new IllegalArgumentException("There is no token with value: " + value);
        } catch (IncorrectResultSizeDataAccessException ex) {
            throw new IllegalArgumentException("There is more than one token with value: " + value);
        }
    }
}
