package pl.com.goodsolution.adviser.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.goodsolution.adviser.domain.CategoryData;
import pl.com.goodsolution.adviser.domain.ProductDetailData;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
class ProductsJdbcRepository {
    private static final Logger log = LoggerFactory.getLogger(ProductsJdbcRepository.class);
    private JdbcTemplate jdbcTemplate;

    public ProductsJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    List<Map<String, Object>> findProductDemands(LocalDate date, Long offset, Long limit) {
        String query = "" +
                "select ";
        query += findProductDemandsColumns();
        query += "from delivery_orders \n" +
                "LEFT JOIN orders_products ON delivery_orders.order_product_id = orders_products.id\n " +
                "LEFT JOIN orders ON orders.order_id = orders_products.order_id " +
                "where 1=1 ";
        query = findProductDemandsConditions(query, date);
        query += groupBy();
        query += orderBy();
        //query += "LIMIT " + offset + ", " + limit;
        log.trace(query);

        return jdbcTemplate.queryForList(query);
    }

    private String groupBy() {
        return "GROUP BY orders_products.category_id, orders_products.product_id, delivery_orders.id ";
    }

    private String orderBy() {
        return "ORDER BY category_name, product_name ";
    }

    private String findProductDemandsColumns() {
        return "(SELECT categories_languages.name FROM categories_languages WHERE categories_languages.category_id = orders_products.category_id AND language = '" + Language.PL.getCode() + "') AS category_name, " +
                "(SELECT products_languages.name FROM products_languages WHERE products_languages.product_id = orders_products.product_id AND language = '" + Language.PL.getCode() + "') AS product_name, " +
                "SUM(quantity)  AS quantity, " +
                "delivery_orders.id AS delivery_orders_id ";
    }

    private String findProductDemandsConditions(String query, LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (DayOfWeek.SUNDAY.equals(dayOfWeek)) {
            date = date.minusDays(1);
        }
        query += "AND order_status_id != 6 " +
                "AND delivery_orders.status NOT IN ('-2', '3') " +
                "AND delivery_date = '" + date + "' ";

        if (DayOfWeek.SUNDAY.equals(dayOfWeek)) {
            query += "AND sunday = 1 ";
        } else {
            query += "AND sunday = 0 ";
        }
        return query;
    }


    boolean checkProductOrderGroup(Long deliveryId) {
        String query = "SELECT Count(*) as c FROM delivery_orders WHERE group_for = " + deliveryId;
        //log.trace(query);
        for (Map row : jdbcTemplate.queryForList(query)) {
            if ((long) row.get("c") > 0) {
                return true;
            }
            ;
        }
        return false;
    }

    ProductDetailData getProduct(Long productId) {
        String query = "SELECT * FROM products WHERE product_id = ?";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{productId},
                    (rs, rowNum) -> {
                        ProductDetailData data = new ProductDetailData(
                                rs.getString("name"),
                                rs.getLong("quantity"),
                                rs.getLong("tax_id"),
                                rs.getBigDecimal("net_price_retail"),
                                rs.getBigDecimal("gross_price_retail"),
                                rs.getLong("category_id"));
                        return data;
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    Set<Long> getAllExclusiveID() {
        String query = "SELECT * FROM diet_discount_exclusive";
        log.trace(query);
        List<Map<String, Object>>  rows = jdbcTemplate.queryForList(query);
        Set<Long> ids = new HashSet<>();
        for (Map row : rows) {
            ids.add(Long.valueOf(String.valueOf(row.get("diet_id"))));
        }
        return ids;
    }

    BigDecimal getDietDiscount(Long days) {
        String query = "SELECT * FROM diet_discount WHERE days <= ? ORDER BY days DESC LIMIT 1";
        log.trace(query);
        List<Map<String, Object>>  rows = jdbcTemplate.queryForList(query, days);
        BigDecimal discount = null;
        for (Map row : rows) {
            discount = BigDecimal.valueOf((Float) row.get("discount"));
        }
        return discount;
    }

    BigDecimal getTaxValue(Long taxId) {
        String query = "SELECT * FROM tax_table WHERE active = 1 AND tax_id = ?";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{taxId},
                    (rs, rowNum) -> {
                        return rs.getBigDecimal("value");
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    CategoryData getCategory(Long categoryId) {
        String query = "SELECT * FROM categories WHERE category_id = ?";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{categoryId},
                    (rs, rowNum) -> {
                        CategoryData categoryData = new CategoryData(
                                rs.getLong("category_id"),
                                rs.getBigDecimal("test_set_price_discount")
                        );
                        return categoryData;
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    BigDecimal getExtrasPrice(Long extraId) {
        String query = "SELECT * FROM extras WHERE id = ?";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{extraId},
                    (rs, rowNum) -> {
                        return rs.getBigDecimal("gross_price");
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    String getProductNameInLanguage(Long productId, String lang) {
        String query = "SELECT * FROM products_languages WHERE product_id = ? AND language = ?";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{productId, lang},
                    (rs, rowNum) -> {
                        return rs.getString("name");
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
}
