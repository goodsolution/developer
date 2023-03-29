package pl.com.goodsolution.adviser.api;

import pl.com.goodsolution.adviser.domain.StatisticData;

import java.math.BigDecimal;
import java.util.List;

public class StatisticsResultGetResponse {
    private List<StatisticData> records;
    private BigDecimal sum;
    private Long allOrdersCount;
    private Long customersWhoOrderedCount;
    private Long customersWhoDidNotContinueCount;
    private BigDecimal customersWhoDidNotContinuePercent;


    public StatisticsResultGetResponse(List<StatisticData> records, BigDecimal sum, Long allOrdersCount, Long customersWhoOrderedCount, Long customersWhoDidNotContinueCount, BigDecimal customersWhoDidNotContinuePercent) {
        this.records = records;
        this.sum = sum;
        this.allOrdersCount = allOrdersCount;
        this.customersWhoOrderedCount = customersWhoOrderedCount;
        this.customersWhoDidNotContinueCount = customersWhoDidNotContinueCount;
        this.customersWhoDidNotContinuePercent = customersWhoDidNotContinuePercent;
    }


    public List<StatisticData> getRecords() {
        return records;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public Long getAllOrdersCount() {
        return allOrdersCount;
    }

    public Long getCustomersWhoOrderedCount() {
        return customersWhoOrderedCount;
    }

    public Long getCustomersWhoDidNotContinueCount() {
        return customersWhoDidNotContinueCount;
    }

    public BigDecimal getCustomersWhoDidNotContinuePercent() {
        return customersWhoDidNotContinuePercent;
    }
}
