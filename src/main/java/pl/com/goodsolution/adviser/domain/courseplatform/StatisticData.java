package pl.com.goodsolution.adviser.domain.courseplatform;

import pl.com.goodsolution.adviser.logic.courseplatform.StatisticType;

import java.math.BigDecimal;

public class StatisticData {
    private StatisticType type;
    private BigDecimal value;

    public StatisticData(StatisticType type, BigDecimal value) {
        this.type = type;
        this.value = value;
    }

    public StatisticType getType() {
        return type;
    }

    public BigDecimal getValue() {
        return value;
    }
}


