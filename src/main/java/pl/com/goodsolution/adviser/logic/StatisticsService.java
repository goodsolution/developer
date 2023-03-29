package pl.com.goodsolution.adviser.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.api.StatisticsResultGetResponse;
import pl.com.goodsolution.adviser.domain.StatisticData;
import pl.com.goodsolution.adviser.domain.StatisticsFilter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
public class StatisticsService {
    private static final Logger log = LoggerFactory.getLogger(StatisticsService.class);
    private StatisticsJdbcRepository statisticsJdbcRepository;

    public StatisticsService(StatisticsJdbcRepository statisticsJdbcRepository) {
        this.statisticsJdbcRepository = statisticsJdbcRepository;
    }

    public StatisticsResultGetResponse getStatisticsGetResponse(LocalDate dateFrom, LocalDate dateTo, Long driverId, Long[] categoryIds, String paymentStatus, Long paymentId, Long shippingId) {
        List<StatisticData> data = findStatistics(new StatisticsFilter(dateFrom == null ? LocalDate.now() : dateFrom, dateTo == null ? LocalDate.now() : dateTo, driverId, categoryIds, paymentStatus, paymentId, shippingId));
        Long customersWhoDidNotContinueCount = getCustomersWhoDidNotContinueCount(data);
        Long customersWhoOrderedCount = getCustomersWhoOrderedCount(data);
        return new StatisticsResultGetResponse(
                data,
                getSum(data),
                getAllOrdersCount(data),
                customersWhoOrderedCount,
                customersWhoDidNotContinueCount,
                getCustomersWhoDidNotContinuePercent(customersWhoDidNotContinueCount, customersWhoOrderedCount)
        );
    }

    private List<StatisticData> findStatistics(StatisticsFilter filter) {
        long startTime = System.currentTimeMillis();
        List<Map<String, Object>> rows = statisticsJdbcRepository.findStatistics(filter);
        long timeTaken = System.currentTimeMillis() - startTime;
        log.info("Time Taken by {} is {}", "findStatistics", timeTaken);
        Long no = 1L;
        List<StatisticData> list = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LL-dd");
        for (Map row : rows) {
            LocalDate dateTime = null;
            if (row.get("delivery_date") != null) {
                dateTime = LocalDate.parse(String.valueOf(row.get("delivery_date")), formatter);
            }
            StatisticData data = new StatisticData(
                    Long.valueOf((Integer) row.get("order_id")),
                    no++,
                    (String) row.get("customer_name") + " " + (String) row.get("customer_surname"),
                    (String) row.get("user_order_id"),
                    (String) row.get("driver"),
                    dateTime,
                    BigDecimal.valueOf((Float) row.get("discount_price_for_pice")),
                    (Long) row.get("orders_count"),
                    (String) row.get("logged_buyer_email")
            );
            list.add(data);
        }
        return list;
    }

    private BigDecimal getSum(List<StatisticData> data) {
        return data.stream().map(StatisticData::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Long getAllOrdersCount(List<StatisticData> list) {
        return list.stream().filter(distinctByKey(StatisticData::getId)).count();
    }

    private Long getCustomersWhoOrderedCount(List<StatisticData> list) {
        return list.stream().filter(distinctByKey(StatisticData::getCustomerEmail)).count(); //IMP customerId
    }

    private Long getCustomersWhoDidNotContinueCount(List<StatisticData> list) {
        return list.stream().filter(o -> o.getOrdersCount().longValue() == 1L).count();
    }

    private BigDecimal getCustomersWhoDidNotContinuePercent(Long customersWhoDidNotContinueCount, Long customersWhoOrderedCount) {
        // <?php echo round(($prolong / count($customers)) * 100, 2) ?>
        if (customersWhoOrderedCount.longValue() == 0L) {
            return BigDecimal.ZERO;
        }
        return (BigDecimal.valueOf(customersWhoDidNotContinueCount).divide(BigDecimal.valueOf(customersWhoOrderedCount), 2, BigDecimal.ROUND_HALF_UP)).multiply(BigDecimal.valueOf(100L));
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
