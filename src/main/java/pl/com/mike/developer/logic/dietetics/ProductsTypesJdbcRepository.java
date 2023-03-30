package pl.com.mike.developer.logic.dietetics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.ProductTypeData;

import java.util.List;
import java.util.Map;

@Service
class ProductsTypesJdbcRepository {
    private static final Logger log = LoggerFactory.getLogger(ProductsTypesJdbcRepository.class);
    private JdbcTemplate jdbcTemplate;

    public ProductsTypesJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    List<Map<String, Object>> findProducts() {
        String query = "SELECT * FROM dietetics_products_types WHERE status = 1";
        log.trace(query);

        return jdbcTemplate.queryForList(query);
    }

    void updateProduct(ProductTypeData data) {
        String query = "UPDATE dietetics_products_types SET type = ? WHERE id = ?";
        log.trace(query);
        jdbcTemplate.update(
                query,
                data.getType(),
                data.getId()
        );

    }

    void createProduct(ProductTypeData data) {
        String query = "INSERT INTO dietetics_products_types (type)"+
                " VALUES(?)";

        log.trace(query);
        jdbcTemplate.update(
                query,
                data.getType()
        );
    }

    void deleteProduct(Long id) {
        String query = "UPDATE dietetics_products_types SET status = 0 WHERE id =  ?";
        log.trace(query);
        jdbcTemplate.update(
                query,
                id
        );
    }
}
