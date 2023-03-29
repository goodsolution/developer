package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.courseplatform.*;
import java.util.List;

@Service
public class StatisticService {
    private StatisticJdbcRepository statisticJdbcRepository;

    public StatisticService(StatisticJdbcRepository statisticJdbcRepository) {
        this.statisticJdbcRepository = statisticJdbcRepository;
    }

    public StatisticData refresh(StatisticType type, String param, Integer number) {
        return statisticJdbcRepository.refresh(type, param, number);
    }

    public List<StatisticSellingData> findTopSellingProductsForMoney() {
        return statisticJdbcRepository.findTopSellingProductsForMoney();
    }

    public List<StatisticNewCustomerData> findNewCustomers() {
        return statisticJdbcRepository.findNewCustomers();
    }

    public List<StatisticItemCountData> findVisitedLandings() {
        return statisticJdbcRepository.findVisitedLandings();
    }

    public List<StatisticCourseCompletionData> findCourseCompletion(StatisticCompletionFilter filter) {
        return statisticJdbcRepository.findCourseCompletion(filter);
    }
}
