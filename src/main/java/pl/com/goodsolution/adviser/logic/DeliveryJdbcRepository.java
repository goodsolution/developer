package pl.com.goodsolution.adviser.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.goodsolution.adviser.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@Transactional
class DeliveryJdbcRepository {
    private static final Logger log = LoggerFactory.getLogger(DeliveryJdbcRepository.class);
    private JdbcTemplate jdbcTemplate;

    public DeliveryJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private DateTimeFormatter timeFormatterLong = DateTimeFormatter.ofPattern("H:mm:ss");
    private DateTimeFormatter timeFormatterShort = DateTimeFormatter.ofPattern("H:mm");

    Long checkIfDayIsExcluded(LocalDate date) {
        String query = "SELECT Count(*) FROM delivery_date_replace WHERE delivery_date = ?";
        log.trace(query);
        return jdbcTemplate.queryForObject(query, Long.class, date);
    }

    Long getDeliveryMaxPriorityCount(Long driverId,  LocalDate date) {
        String query = "SELECT Count(*) FROM delivery_orders WHERE delivery_date = ? AND driver_id = ? ORDER BY priority DESC LIMIT 1";
        log.trace(query);
        return jdbcTemplate.queryForObject(query, Long.class, date, driverId);
    }

    Long getDeliveryMaxPriority(Long driverId,  LocalDate date) {
        String query = "SELECT * FROM delivery_orders WHERE delivery_date = ? AND driver_id = ? ORDER BY priority DESC LIMIT 1";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{date, driverId},
                    (rs, rowNum) -> {
                        return rs.getLong("priority");
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    Long getDeliveryDateReplaceCount(LocalDate date) {
        String query = "SELECT Count(*) FROM delivery_date_replace WHERE delivery_date = ? AND exclusion = 0";
        log.trace(query);
        return jdbcTemplate.queryForObject(query, Long.class, date);
    }

    Long getChangeDayCount(LocalDate date) {
        String query = "SELECT * FROM delivery_date_replace WHERE delivery_date = '\". $data .\"' AND exclusion = 0";
        log.trace(query);
        return jdbcTemplate.queryForObject(query, Long.class, date);
    }

    Long checkDeliveryForProduct(Long orderProductId) {
        String query = "SELECT Count(*) FROM delivery_orders WHERE order_product_id = ?";
        log.trace(query);
        return jdbcTemplate.queryForObject(query, Long.class, orderProductId);
    }

    void deleteDeliveryOrderForProductNotCancelled(Long orderProductId) {
        String query = "DELETE FROM delivery_orders WHERE order_product_id = ? AND status != 1  ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                orderProductId
        );
    }

    void stopOrderDelivery(Long orderProductId, LocalDate stopDate) {
        String query = "" +
                "UPDATE delivery_orders SET status = '-2' WHERE order_product_id = ? AND delivery_date >= ? AND status != 3";

        log.trace(query);
        jdbcTemplate.update(
                query,
                orderProductId,
                stopDate
        );
    }

    Long getDeliveredForProductCount(Long orderProductId) { //TODO imp
        String query = "select count(*) from ( " +
                "SELECT Count(*) FROM delivery_orders WHERE order_product_id = ? AND status = 1 GROUP BY IF( sunday =1, DATE_ADD( delivery_date, INTERVAL 1 DAY ) , delivery_date )" +
                ") As counter ";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, Long.class, orderProductId);
        } catch (EmptyResultDataAccessException ex) {
            return 0L;
        }
    }

    LocalDate getLastDeliveryDate(Long orderProductId) {
        String query = "SELECT *, IF( sunday =1, DATE_ADD( delivery_date, INTERVAL 1 DAY ) , delivery_date ) as ddate FROM delivery_orders WHERE order_product_id = ? AND status = 1 ORDER BY ddate LIMIT 1";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{orderProductId},
                    (rs, rowNum) -> rs.getTimestamp("ddate").toLocalDateTime().toLocalDate());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    Long getAllDeliveryDaysCount(Long orderProductId) {
        String query = "SELECT Count(*) FROM delivery_orders WHERE order_product_id = ?";
        log.trace(query);
        return jdbcTemplate.queryForObject(query, Long.class, orderProductId);
    }

    void createDeliveryChangeLog(OrderDeliveryChangeLogData data) {
        String query = "" +
                "INSERT INTO delivery_changes (order_id, order_product_id, delivery_id, type, delivery_changes.before, delivery_changes.after, edited_by, edited_on) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        log.trace(query);
        jdbcTemplate.update(
                query,
                data.getOrderId(),
                data.getProductId(),
                data.getDeliveryId(),
                data.getType(),
                data.getBefore(),
                data.getAfter(),
                data.getEditedBy(),
                LocalDateTime.now()
        );
    }

    void stopDeliveryUpdate(Long deliveryId) {
        String query = "" +
                "UPDATE delivery_orders SET status = '-2', delete_day = ?, sunday = 0 WHERE id = ?";

        log.trace(query);
        jdbcTemplate.update(
                query,
                LocalDate.now(),
                deliveryId
        );
    }


    void startDeliveryUpdate(Long deliveryId, LocalDate date, boolean sunday, LocalDate originalDate) {
        String query = "" +
                "UPDATE delivery_orders SET status = 0, delivery_date = ?, sunday = ?, original_delivery_date=? WHERE id= ? ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                date,
                sunday ? "1" : "0",
                originalDate,
                deliveryId
        );
    }

    void createDeliveryRelease(OrderDeliveryReleaseCreateData data) {
        String query = "" +
                "INSERT INTO `delivery_relases` (`delivery_id`, `order_id`, `order_product_id`, `old_date`, `new_date`, `relase_stamp`, `user_id`, `username`, `ip`, `user_agent`) " +
                "VALUES ( " +
                "?," +
                "? ," +
                "?," +
                "?," +
                "?, " +
                "?, " +
                "?," +
                "?," +
                "?, " +
                "?" +
                ")";

        log.trace(query);
        jdbcTemplate.update(
                query,
                data.getDeliveryId(),
                data.getOrderId(),
                data.getOrderProductId(),
                data.getOldDate(),
                data.getNewDate() != null ?  data.getNewDate() : null,
                data.getReleaseDate(),
                data.getUserId(),
                data.getUserName(),
                data.getIp(),
                data.getUserAgent()
                /*
                "'\". $delivery_id .\"'," +
                        " '\". $details['order_id'] .\"' ," +
                        " '\". $details['order_product_id'] .\"'," +
                        " '\". $details['delivery_date'] .\"'," +
                        " '', " +
                        "'\". date(\"Y-m-d H:i:s\") .\"', " +
                        "'\". $_SESSION['admin_login']['id'] .\"'," +
                        " '\". $_SESSION['admin_login']['username'] .\"'," +
                        " '\". $ip .\"', " +
                        "'\". $_SERVER['HTTP_USER_AGENT'] .\"'" +

                 */
        );
    }

    OrderDeliveryOrderData getDeliveryOrder(Long deliveryId) {
        String query = "SELECT * FROM delivery_orders WHERE id = ?";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{deliveryId},
                    (rs, rowNum) -> {
                        OrderDeliveryOrderData data = new OrderDeliveryOrderData(
                                rs.getLong("order_id"),
                                rs.getLong("order_product_id"),
                                rs.getLong("driver_id"),
                                rs.getTimestamp("delivery_date").toLocalDateTime().toLocalDate(),
                                rs.getTime("dh_from") != null ? rs.getTime("dh_from").toLocalTime() : null,
                                rs.getTime("dh_to") != null ? rs.getTime("dh_to").toLocalTime() : null,
                                rs.getInt("da") == 1,
                                rs.getString("da_city"),//daCity,
                                rs.getString("da_street"),//daStreet,
                                rs.getString("da_house_no"),//daHouseNo,
                                rs.getString("da_apartament_no"),//daApartmentNo,
                                rs.getString("da_postal_code"),//daPostalCode
                                rs.getLong("status"),
                                rs.getTimestamp("original_delivery_date") != null ? rs.getTimestamp("original_delivery_date").toLocalDateTime().toLocalDate() : null);
                        return data;
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    Long checkDeliveryInSundayCount(Long orderId, LocalDate date) {
        String query = "SELECT Count(*) FROM delivery_orders WHERE delivery_date = ? AND status != '-2' AND order_id = ?";
        log.trace(query);
        return jdbcTemplate.queryForObject(query, Long.class, date, orderId);
    }

    List<Map<String, Object>> findReleasedDeliveriesForOrder(Long orderId) {
        String query = "SELECT *, " +
                "IF( sunday =1, DATE_ADD( delivery_date, INTERVAL 1 DAY ) , delivery_date ) AS ddate, " +
                "(SELECT CONCAT(name, ' ', surname) FROM delivery_driver dd WHERE dd.id = driver_id) AS driver," +
                "(SELECT categories_languages.name FROM products AS pr LEFT JOIN categories_languages ON pr.category_id = categories_languages.category_id WHERE pr.product_id = op.product_id AND categories_languages.language = '" + Language.PL.getCode() + "' ) AS category_name  " +
                "FROM delivery_orders do " +
                "JOIN orders_products op " +
                "ON op.id = do.order_product_id " +
                "WHERE do.status = '-2' AND do.order_id = ? ";
        log.trace(query);

        return jdbcTemplate.queryForList(query, orderId);
    }


    Long getGroupedDeliveriesCount(Long deliveryId) {
        String query = "SELECT count(*) FROM delivery_orders WHERE status = 3 AND group_for = ?";
        log.trace(query);
        return jdbcTemplate.queryForObject(query, Long.class, deliveryId);
    }

    List<Map<String, Object>> getGroupedDeliveries(Long deliveryId) {
        String query = "SELECT * FROM delivery_orders WHERE status = 3 AND group_for = ? ";
        log.trace(query);
        return jdbcTemplate.queryForList(query, deliveryId);
    }

    DeliverySettingData getDeliverySettings(String setting) {
        String query = "SELECT * FROM delivery_settings WHERE setting = ?";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{setting},
                    (rs, rowNum) -> {
                        DeliverySettingData data = new DeliverySettingData(
                                rs.getLong("id"),
                                rs.getString("setting"),
                                DateTimeUtil.parseHour(rs.getString("value"))
                        );
                        return data;
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    Long getDeliveryDeletedDays(Long orderProductId) {
        String query = "SELECT count(*) FROM delivery_orders WHERE status = '-2' AND order_product_id = ?";
        log.trace(query);
        return jdbcTemplate.queryForObject(query, Long.class, orderProductId);
    }

    Long checkIfDayIsExclusion(LocalDate date) {
        String query = "SELECT Count(*) FROM delivery_date_replace WHERE delivery_date = ? AND exclusion = 1";
        log.trace(query);
        return jdbcTemplate.queryForObject(query, Long.class, date);
    }

    List<Map<String, Object>> getDeliveryOrdersReleased(Long orderProductId, Long limit) {
        String query = " SELECT * FROM delivery_orders WHERE order_product_id = ? AND status = '-2' LIMIT ?";
        log.trace(query);

        return jdbcTemplate.queryForList(query, orderProductId, limit);
    }

    void updateDeliveryOrderToStart(Long deliveryId, LocalDate date, Long priority, boolean sunday, LocalDate originalDate) {
        String query = "UPDATE delivery_orders SET delivery_date = ?, status = 0, priority = ?, sunday = ?, original_delivery_date=? WHERE id = ?";

        log.trace(query);
        jdbcTemplate.update(
                query,
                date,
                priority,
                sunday ? "1" : "0",
                originalDate,
                deliveryId
        );
    }

    Long getDeliveryOrdersCount(Long orderId) {
        String query = "SELECT count(*) FROM delivery_orders WHERE order_id = ? ";
        log.trace(query);
        return jdbcTemplate.queryForObject(query, Long.class, orderId);
    }

    void updateDeliveryStatusForOrder(Long orderId, String status) {
        String query = "" +
                "UPDATE delivery_orders SET status = ? WHERE order_id = ? ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                status,
                orderId
        );
    }

    List<Map<String, Object>> findDeliveryOrdersGroupByDeliveryDate(Long orderProductId) {
        String query = " SELECT * FROM delivery_orders WHERE order_product_id = ? GROUP BY delivery_date";
        log.trace(query);

        return jdbcTemplate.queryForList(query, orderProductId);
    }

    void updateDeliveryStatus(Long id, String status) {
        String query = "" +
                "UPDATE delivery_orders SET status = ? WHERE id = ? ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                status,
                id
        );
    }

    void updateDeliveryGroupFor(Long deliveryId, Long orderProductId, LocalDate deliveryDate, String status) {
        String query = "" +
                "UPDATE delivery_orders " +
                "SET group_for = ? WHERE order_product_id = ? AND delivery_date = ? AND status = ? ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                deliveryId,
                orderProductId,
                deliveryDate,
                status
        );
    }

    void deleteOrderDeliveries(Long orderId) {
        String query = "DELETE FROM delivery_orders WHERE order_id = ? ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                orderId
        );
    }

    BigDecimal getDeliveryDiscount(Long deliveryId) {
        String query = "SELECT * FROM delivery_discount WHERE delivery_id =  ?";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{deliveryId},
                    (rs, rowNum) -> {
                        return rs.getBigDecimal("value");
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    DeliveryMethodData getDeliveryMethod(Long deliveryMethodId) {
        String query = "SELECT * FROM types_of_shipments_languages " +
                "WHERE shipment_type_id = ? AND language = ?";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{deliveryMethodId, Language.PL.getCode()},
                    (rs, rowNum) -> {
                        return new DeliveryMethodData(
                                rs.getLong("id"),
                                rs.getString("name")
                        );
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    LocalDate getMinDeliveryDateForProductInOrder(Long orderProductId) {
        String query = "SELECT *, " +
                "IF( sunday =1, DATE_ADD( delivery_date, INTERVAL 1 DAY ), delivery_date ) AS ddate " +
                "FROM delivery_orders " +
                "WHERE " +
                "order_product_id = ? ORDER BY ddate ASC LIMIT 1";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{orderProductId},
                    (rs, rowNum) -> {
                        return rs.getTimestamp("ddate").toLocalDateTime().toLocalDate();
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    LocalDate getMaxDeliveryDateForProductInOrder(Long orderProductId) {
        String query = "SELECT *, " +
                "IF( sunday =1, DATE_ADD( delivery_date, INTERVAL 1 DAY ), delivery_date ) AS ddate " +
                "FROM delivery_orders " +
                "WHERE " +
                "order_product_id = ? ORDER BY ddate DESC LIMIT 1";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{orderProductId},
                    (rs, rowNum) -> {
                        return rs.getTimestamp("ddate").toLocalDateTime().toLocalDate();
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }


    void updateOrderDeliveryMethod(Long orderId, Long deliveryMethodId) {
        String query = "" +
                "UPDATE orders SET shipping_id = ? WHERE order_id = ?  ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                deliveryMethodId,
                orderId
        );
    }

    void updateDriverOnDeliveriesForOrder(Long orderId, Long driverId) {
        String query = "" +
                "UPDATE delivery_orders SET driver_id = ? WHERE order_id = ? AND status = 0  ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                driverId,
                orderId
        );
    }

    void updateDriverOnDeliveriesForOrderWithoutStatus(Long orderId, Long driverId) {
        String query = "" +
                "UPDATE delivery_orders SET driver_id = ? WHERE order_id = ?  ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                driverId,
                orderId
        );
    }

    void updateDriverOnDelivery(Long deliveryId, Long driverId) {
        String query = "" +
                "UPDATE delivery_orders SET driver_id = ? WHERE id = ?  ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                driverId,
                deliveryId
        );
    }

    List<Map<String, Object>> getDeliveriesForOrder(Long orderId) {
        String query = "SELECT * FROM delivery_orders WHERE order_id = ? AND status = 0 ";


        log.trace(query);

        return jdbcTemplate.queryForList(query, orderId);
    }

    Long getDeliveryForOrderWithStatus(Long orderId, String status) {
        String query = "SELECT COUNT(*) FROM delivery_orders WHERE order_id = ? AND status = ?";
        log.trace(query);
        return jdbcTemplate.queryForObject(query, Long.class, orderId, status);
    }

    void updateDriverToUngroup(Long orderId) {
        String query = "" +
                "UPDATE delivery_orders SET status = 0 AND group_for = NULL WHERE order_id = ?  ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                orderId
        );
    }

    void setDeliveryAsDelivered(Long deliveryId) {
        String query = "" +
                "UPDATE delivery_orders SET status = 1, delivery_timestamp = ?, by_cron = 1 WHERE id = ?";

        log.trace(query);
        jdbcTemplate.update(
                query,
                LocalDateTime.now(),
                deliveryId
        );
    }

    List<Map<String, Object>> findDeliveryToDeliveredTwoDays() {
        String query = "SELECT * FROM `delivery_orders` WHERE `delivery_date` = CURDATE() - INTERVAL 2 DAY AND `status` = 0";
        log.trace(query);

        return jdbcTemplate.queryForList(query);
    }

    List<Map<String, Object>> findUndelivered() {
        String query = "SELECT *,\n" +
                "\t\t\t\tIF( sunday =1, DATE_ADD( delivery_date, INTERVAL 1 DAY ) , delivery_date ) AS ddate,\n" +
                "\t\t\t\tdelivery_orders.id as delivery_id\n" +
                "\t\t\tFROM `delivery_orders`\n" +
                "\t\t\tLEFT JOIN orders ON orders.order_id = delivery_orders.order_id\n" +
                "\t\t\tWHERE\n" +
                "\t\t\t\tdelivery_orders.status = 0 AND\n" +
                "\t\t\t\torders.shipping_id = 2 AND\n" +
                "\t\t\t\torders.order_status_id != 6 AND\n" +
                "\t\t\t\torders.order_status_id != 4 AND\n" +
                "\t\t\t\torders.order_status_id != 7 AND\n" +
                "\t\t\t\tIF( sunday =1, DATE_ADD( delivery_date, INTERVAL 1 DAY ) , delivery_date ) = ? \n" +
                "\t\t\tORDER BY delivery_date DESC";
        log.trace(query);


        /*
        		$result = [];
		$date = date("Y-m-d");
		$maxDate = date("Y-m-d", strtotime($date . ' - ' . $dayDiff . ' days'));

		$q = "SELECT *,
				IF( sunday =1, DATE_ADD( delivery_date, INTERVAL 1 DAY ) , delivery_date ) AS ddate,
				delivery_orders.id as delivery_id
			FROM `delivery_orders`
			LEFT JOIN orders ON orders.order_id = delivery_orders.order_id
			WHERE
				delivery_orders.status = 0 AND
				orders.shipping_id = 2 AND
				orders.order_status_id != 6 AND
				orders.order_status_id != 4 AND
				orders.order_status_id != 7 AND
				IF( sunday =1, DATE_ADD( delivery_date, INTERVAL 1 DAY ) , delivery_date ) = '". $maxDate ."'
			ORDER BY delivery_date DESC";
		$query = mysqli_query($db, $q);
         */

        return jdbcTemplate.queryForList(query, LocalDate.now().minusDays(1));
    }
}