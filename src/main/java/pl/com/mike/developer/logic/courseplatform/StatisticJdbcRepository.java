package pl.com.mike.developer.logic.courseplatform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.api.courseplatform.StatisticOption;
import pl.com.mike.developer.domain.courseplatform.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.mike.developer.logic.adviser.JdbcUtil.*;

@Service
public class StatisticJdbcRepository {

    private static final Logger log = LoggerFactory.getLogger(StatisticJdbcRepository.class);
    private JdbcTemplate jdbcTemplate;
    private CoursesJdbcRepository coursesJdbcRepository;

    public StatisticJdbcRepository(JdbcTemplate jdbcTemplate, CoursesJdbcRepository coursesJdbcRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.coursesJdbcRepository = coursesJdbcRepository;
    }

    StatisticData refresh(StatisticType type, String param, Integer number) {
        StatisticData statisticData = getStatistic(type, param, number);
        update(statisticData);
        return statisticData;
    }

    List<StatisticSellingData> findTopSellingProductsForMoney() {
        String query = "SELECT title, count(*) as count, sum(pur.price) as sum " +
                "FROM crs_orders ord, crs_purchased_courses pur, crs_courses cur " +
                "WHERE total_price > 0.0 AND ord.id = pur.order_id AND pur.course_id = cur.id " +
                "AND pur.returned = 0 AND ord.status = 'p' " +
                "GROUP BY title " +
                "ORDER BY count DESC, sum desc " +
                "LIMIT 20";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        List<StatisticSellingData> list = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            list.add(new StatisticSellingData(
                    getString(row, "title"),
                    getBigDecimal(row, "count"),
                    getBigDecimal(row, "sum")
            ));
        }
        return list;
    }

    List<StatisticNewCustomerData> findNewCustomers() {
        String query = "SELECT * FROM crs_customers " +
                "WHERE registration_datetime BETWEEN DATE_SUB(NOW(), INTERVAL 7 DAY) AND NOW() " +
                "and is_enabled = 1 " +
                "order by registration_datetime DESC " +
                "limit 20";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        List<StatisticNewCustomerData> list = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            list.add(new StatisticNewCustomerData(
                    getString(row, "login"),
                    getDateTime(row, "registration_datetime")
            ));
        }
        return list;
    }

    List<StatisticItemCountData> findVisitedLandings() {
        String query = "SELECT value, count(*) as c FROM crs_action_trace WHERE what = 'page.show' and value LIKE '%landing%' group by value order by c DESC";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        List<StatisticItemCountData> list = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            list.add(new StatisticItemCountData(
                    getString(row, "value"),
                    getLong(row, "c")
            ));
        }
        return list;
    }

    List<StatisticCourseCompletionData> findCourseCompletion(StatisticCompletionFilter filter) {
        String query = "SELECT \n" +
                "\tlogin, \n" +
                "\tcrs_cs.title, \n" +
                "\tIFNULL(ROUND(\n" +
                "\t\t(\n" +
                "\t\t\tSELECT COUNT(*) \n" +
                "\t\t\tFROM crs_customer_lesson_details AS cld \n" +
                "\t\t\tWHERE cld.customer_id = crs_cust.id AND cld.course_id = crs_pc.course_id AND cld.watched = 1\n" +
                "\t\t) \n" +
                "\t\t/ \n" +
                "\t\t(\n" +
                "\t\t\tSELECT COUNT(*) \n" +
                "\t\t\tFROM crs_customer_lesson_details AS cld \n" +
                "\t\t\tWHERE cld.customer_id = crs_cust.id AND cld.course_id = crs_pc.course_id \n" +
                "\t\t)\n" +
                "\t\t* 100.00\n" +
                "\t, 2),0) AS percent \n" +
                "FROM crs_customers AS crs_cust \n" +
                "JOIN crs_orders as crs_ord ON (crs_cust.id = crs_ord.customer_id) \n" +
                "JOIN crs_purchased_courses AS crs_pc ON (crs_ord.id = crs_pc.order_id)\n" +
                "JOIN crs_courses AS crs_cs ON (crs_pc.course_id = crs_cs.id)\n" +
                "ORDER BY percent DESC";
        if (filter != null) {
            query += preparePaginationQuery(filter.getPage(), filter.getPageSize());
        }
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        List<StatisticCourseCompletionData> list = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            list.add(new StatisticCourseCompletionData(
                    getString(row, "login"),
                    getString(row, "title"),
                    getBigDecimal(row, "percent", 2, RoundingMode.HALF_UP)
            ));
        }
        return list;
    }

    private StatisticData getStatistic(StatisticType type, String param, Integer value) {
        if (type.equals(StatisticType.NUMBER_OF_VISIBLE_COURSES)) {
            List<CourseData> result = coursesJdbcRepository.find(new CoursesFilter(false, CourseVisibilityStatus.VISIBLE, "c"));
            return new StatisticData(type, BigDecimal.valueOf(result != null ? result.size() : 0));
        } else if (type.equals(StatisticType.NUMBER_OF_NO_FREE_ORDERS)) {
            return new StatisticData(type, getNumberOfNoFreeOrders(param));
        } else if (type.equals(StatisticType.NUMBER_OF_FREE_ORDERS)) {
            return new StatisticData(type, getNumberOfFreeOrders(param));
        } else if (type.equals(StatisticType.REGISTERED_CUSTOMERS_IN_LAST)) {
            return new StatisticData(type, getNumberOfLastRegisteredCustomers(param, value));
        } else {
            throw new IllegalArgumentException("Invalid type: " + type);
        }
    }

    private BigDecimal getNumberOfFreeOrders(String param) {
        String query = prepareQueryWithConditions(StatisticOption.valueOf(param), "total_price = 0.0");
        Long result = jdbcTemplate.queryForObject(query, (rs, rowNum) -> Long.valueOf(rs.getString("count")));
        return BigDecimal.valueOf(result);
    }

    private BigDecimal getNumberOfNoFreeOrders(String param) {
        String query = prepareQueryWithConditions(StatisticOption.valueOf(param), "total_price > 0.0");
        Long result = jdbcTemplate.queryForObject(query, (rs, rowNum) -> Long.valueOf(rs.getString("count")));
        return BigDecimal.valueOf(result);
    }

    private BigDecimal getNumberOfLastRegisteredCustomers(String param, Integer value) {
        String query = prepareQueryForLastRegisteredCustomers(StatisticOption.valueOf(param), value);
        Long result = jdbcTemplate.queryForObject(query, (rs, rowNum) -> Long.valueOf(rs.getString("count")));
        return BigDecimal.valueOf(result);
    }


    private String prepareQueryWithConditions(StatisticOption statisticOption, String condition) {
        String query = "SELECT count(*) as count FROM crs_orders WHERE ";

        if (statisticOption.equals(StatisticOption.TODAY)) {
            query += "purchase_date = '" + LocalDate.now() + "'";

        } else if (statisticOption.equals(StatisticOption.YESTERDAY)) {
            query += "purchase_date = '" + LocalDate.now().minusDays(1) + "'";

        } else if (statisticOption.equals(StatisticOption.LAST_WEEK)) {
            query += "purchase_date between '" + getDayOfTheBeginningOfLastWeek() + "' AND '" + getDayOfTheEndOfLastWeek() + "'";

        } else if (statisticOption.equals(StatisticOption.LAST_MONTH)) {
            query += "purchase_date between '" + getDayOfTheBeginningOfLastMonth() + "' AND '" + getDayOfTheEndOfLastMonth() + "'";

        } else if (statisticOption.equals(StatisticOption.LAST_YEAR)) {
            query += "purchase_date between '" + getDayOfTheBeginningOfLastYear() + "' AND '" + getDayOfTheEndOfLastYear() + "'";
        }

        if (!statisticOption.equals(StatisticOption.TOTAL)) {
            query += " AND ";
        }
        query += condition;
        return query;
    }

    private String prepareQueryForLastRegisteredCustomers(StatisticOption statisticOption, Integer value) {
        String query = "SELECT count(*) as count FROM crs_customers WHERE registration_datetime between '";

        if (statisticOption.equals(StatisticOption.HOURS)) {
            query += Timestamp.valueOf(LocalDateTime.now().minusHours(value)) + "' AND '" + Timestamp.valueOf(LocalDateTime.now()) + "'";

        } else if (statisticOption.equals(StatisticOption.DAYS)) {
            query += LocalDate.now().minusDays(value) + "' AND '" + LocalDate.now() + "'";

        } else if (statisticOption.equals(StatisticOption.MONTHS)) {
            query += LocalDate.now().minusMonths(value) + "' AND '" + LocalDate.now() + "'";

        } else if (statisticOption.equals(StatisticOption.YEARS)) {
            query += LocalDate.now().minusYears(value) + "' AND '" + LocalDate.now() + "'";
        }

        log.trace(query);
        return query;

    }

    private LocalDate getDayOfTheEndOfLastYear() {
        return LocalDate.now().minusYears(1).withDayOfYear(LocalDate.now().minusYears(1).lengthOfYear());
    }

    private LocalDate getDayOfTheBeginningOfLastYear() {
        return LocalDate.now().minusYears(1).withDayOfYear(1);
    }

    private LocalDate getDayOfTheEndOfLastMonth() {
        return LocalDate.now().minusMonths(1).withDayOfMonth(LocalDate.now().minusMonths(1).lengthOfMonth());
    }

    private LocalDate getDayOfTheBeginningOfLastMonth() {
        return LocalDate.now().minusMonths(1).withDayOfMonth(1);
    }

    private LocalDate getDayOfTheEndOfLastWeek() {
        return LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue());
    }

    private LocalDate getDayOfTheBeginningOfLastWeek() {
        return LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() + 6);
    }

    void update(StatisticData data) {
        //TODO update in db
    }


}
