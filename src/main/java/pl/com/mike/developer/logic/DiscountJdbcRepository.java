package pl.com.mike.developer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.mike.developer.domain.AppliedDiscountData;
import pl.com.mike.developer.domain.PaymentMethodData;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@Transactional
class DiscountJdbcRepository {
    private static final Logger log = LoggerFactory.getLogger(DiscountJdbcRepository.class);
    private JdbcTemplate jdbcTemplate;

    public DiscountJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private DateTimeFormatter timeFormatterLong = DateTimeFormatter.ofPattern("H:mm:ss");
    private DateTimeFormatter timeFormatterShort = DateTimeFormatter.ofPattern("H:mm");


    PaymentMethodData getPaymentMethod(Long paymentMethodId) {
        // SELECT * FROM payment_methods LEFT JOIN payment_methods_languages ON payment_methods.payment_method_id = payment_methods_languages.payment_method_id WHERE payment_methods.payment_method_id = ".htmlentities( $payment_id )." AND payment_methods_languages.language = '".$_SESSION['lang']."' " );
        return null; //TODO finish
    }

    List<Map<String, Object>> findSaleTable(Long dietId) {
        String query = "SELECT * FROM sale_table WHERE category_id = ? AND status = 1 ";
        log.trace(query);

        return jdbcTemplate.queryForList(query, dietId);
    }

    List<Map<String, Object>> findSaleVariants(Long saleId) {
        String query = "SELECT * FROM sales_variants WHERE sale_id = ? ";
        log.trace(query);

        return jdbcTemplate.queryForList(query, saleId);
    }

    void storeAppliedDiscount(AppliedDiscountData data) {
        String query = "" +
                "INSERT INTO order_discounts_applied (" +
                "    order_id, " +
                "    discount_type,\n" +
                "    discount_value,\n" +
                "    discount_value_total,\n" +
                "    price_before,\n" +
                "    price_after,\n" +
                "    create_date" +
                ") " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";

        log.trace(query);
        jdbcTemplate.update(
                query,
                data.getOrderId(),
                data.getDiscountType().code(),
                data.getDiscountValue(),
                data.getDiscountValueTotal(),
                data.getGrossPriceBefore(),
                data.getGrossPriceAfter(),
                LocalDateTime.now()
        );

    }

    void removeAppliedDiscounts(Long orderId, DiscountType discountType) {
        String query = "DELETE FROM order_discounts_applied WHERE order_id = ? and discount_type = ? ";
        jdbcTemplate.update(query, orderId, discountType.code());
    }

    BigDecimal calculateOtherDiscountsSum(Long orderId) {
        String query = "SELECT SUM(discount_value_total) FROM order_discounts_applied " +
                "WHERE order_id = ? " +
                "AND discount_type NOT IN  ('percent', 'price') ";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, BigDecimal.class, orderId);
        } catch (EmptyResultDataAccessException ex) {
            return BigDecimal.ZERO;
        }
    }
}