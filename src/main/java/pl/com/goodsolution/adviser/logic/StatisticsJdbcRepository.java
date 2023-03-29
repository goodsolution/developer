package pl.com.goodsolution.adviser.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.StatisticsFilter;

import java.util.List;
import java.util.Map;

@Service
class StatisticsJdbcRepository {
    private static final Logger log = LoggerFactory.getLogger(StatisticsJdbcRepository.class);
    private JdbcTemplate jdbcTemplate;

    StatisticsJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    List<Map<String, Object>> findStatistics(StatisticsFilter filter) {

        String query = "" +
                "select ";
        query += findStatisticsColumns();
        query += "from orders ord, " +
                "delivery_orders del_ord, " +
                "order_products_pdiscount pdi," +
                "orders_products op " +
                "where " +
                " del_ord.order_product_id = op.id " +
                "and del_ord.order_product_id = pdi.order_product_id " +
                "and del_ord.order_product_id = op.id " +
                "and del_ord.order_id = ord.order_id " +
                "AND ord.order_status_id NOT IN (6, 1) " +
                "AND del_ord.status NOT IN ('-2', '3') ";



        query = findStatisticsConditions(query, filter);
        query += "ORDER BY ddate ASC ";
        log.trace(query);

        return jdbcTemplate.queryForList(query);
    }

    private String findStatisticsColumns() {
        return "ord.order_id, " +
                "customer_name, " +
                "customer_surname, " +
                "user_order_id, " +
                "delivery_date," +
                "discount_price_for_pice, " +
                "ord.logged_buyer_email, " +
                "IF(\n" +
                "\t\t\tsunday =1,\n " +
                "\t\t\tDATE_ADD( delivery_date, INTERVAL 1 DAY ),\n " +
                "\t\t \tdelivery_date\n " +
                "\t\t) AS ddate, " +
                "(select CONCAT(name, ' ', surname) from delivery_driver del_driv where del_ord.driver_id=del_driv.id) AS driver, " +
                "(select count(*) from orders ord_inner where ord_inner.logged_buyer_email = ord.logged_buyer_email) AS orders_count " +
                " ";
    }

    private String findStatisticsConditions(String query, StatisticsFilter filter) {
        query += "AND IF(\n" +
                "\t\t\tsunday = 1,\n" +
                "\t\t \tDATE_ADD( delivery_date, INTERVAL 1 DAY ),\n" +
                "\t\t \tdelivery_date\n" +
                "\t\t) >= '" + filter.getDateFrom() +  "' ";
        query += "AND IF(\n" +
                "\t\t \tsunday = 1,\n" +
                "\t\t \tDATE_ADD( delivery_date, INTERVAL 1 DAY ),\n" +
                "\t\t \tdelivery_date\n" +
                "\t\t) <= '" + filter.getDateTo() + "' ";
        if (filter.getDriverId() != null) {
            query += "and driver_id = " + filter.getDriverId() + " ";
        }
        if (filter.getShippingId() != null) {
            query += "and shipping_id = " + filter.getShippingId() + " ";
        }
        if (filter.getPaymentId() != null) {
            query += "and payment_id = " + filter.getPaymentId() + " ";
        }
        if (filter.getPaymentStatus() != null && !filter.getPaymentStatus().isEmpty()) {
            query += "and ord.payment_status = '" + filter.getPaymentStatus() + "' ";
        }
        if (filter.getCategoryIds() != null && filter.getCategoryIds().length > 0) {
            query += "and exists (" +
                    "select * " +
                    "from orders_products ord_prd " +
                    "where ord_prd.order_id = ord.order_id " +
                    "and category_id in (" + getCategoryIds(filter.getCategoryIds()) + ") " +
                    ") ";
        }

        return query;
    }

    private String getCategoryIds( Long[] ids){
        String result = "";
        for (int i = 0; i < ids.length; i++) {
            result += ids[i];
            if (i <ids.length-1) {
                result += ", ";
            }
        }
        return result;
    }

}
