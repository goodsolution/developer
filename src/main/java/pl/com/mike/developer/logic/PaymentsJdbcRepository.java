package pl.com.mike.developer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.mike.developer.domain.OrderPaymentChangeData;
import pl.com.mike.developer.domain.OrderPaymentMethodChangeData;
import pl.com.mike.developer.domain.PaymentMethodData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
class PaymentsJdbcRepository {
    private static final Logger log = LoggerFactory.getLogger(PaymentsJdbcRepository.class);
    private JdbcTemplate jdbcTemplate;

    public PaymentsJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private DateTimeFormatter timeFormatterLong = DateTimeFormatter.ofPattern("H:mm:ss");
    private DateTimeFormatter timeFormatterShort = DateTimeFormatter.ofPattern("H:mm");

    PaymentMethodData getPaymentMethod(Long paymentMethodId) {
        String query = "SELECT pm.payment_method_id as payment_method_id, name, operator, fee, active FROM payment_methods pm " +
                "LEFT JOIN payment_methods_languages pml " +
                "ON pm.payment_method_id = pml.payment_method_id " +
                "WHERE pm.payment_method_id = ? " +
                "AND pml.language = ? ";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{paymentMethodId, Language.PL.getCode()},
                    (rs, rowNum) -> {
                        PaymentMethodData data = new PaymentMethodData(
                                rs.getLong("payment_method_id"),
                                rs.getString("name"),
                                rs.getString("operator"),
                                rs.getBigDecimal("fee"),
                                rs.getString("active"));
                        return data;
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    void updatePaymentStatusOnOrder(Long orderId, Long paymentStatusId) {
        String query = "" +
                "UPDATE orders SET payment_status = ?, payment_update_date = ? WHERE order_id = ?  ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                paymentStatusId,
                LocalDateTime.now(),
                orderId
        );
    }

    void updatePaymentUpdateDateOnOrder(Long orderId) {
        String query = "" +
                "UPDATE orders SET payment_update_date = ? WHERE order_id = ?  ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                LocalDateTime.now(),
                orderId
        );
    }

    void createPaymentStatusChange(OrderPaymentChangeData data) {
        String query = "" +
                "INSERT INTO order_payments_changes " +
                "(order_id, payment_status, user_id, datetime) " +
                "VALUES " +
                "(?, ?, ?, ? )  ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                data.getOrderId(),
                data.getPaymentStatusId(),
                data.getUserId(),
                LocalDateTime.now()
        );
    }

    void createPaymentMethodChange(OrderPaymentMethodChangeData data) {
        String query = "" +
                "INSERT INTO `order_payment_changes` " +
                "(`order_id`, `old_method`, `new_method`, `changed_by`) " +
                "VALUES (?, ?, ?, ? ) ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                data.getOrderId(),
                data.getOldPaymentMethodId(),
                data.getNewPaymentMethodId(),
                data.getUserId()
        );
    }

    void updatePaymentMethodOnOrder(Long orderId, Long paymentMethodId, String paymentCompanyName) {
        String query = "" +
                "UPDATE orders " +
                "SET payment_id = ?, payment_company_name = ? " +
                "WHERE order_id = ?  ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                paymentMethodId,
                paymentCompanyName,
                orderId
        );
    }
}