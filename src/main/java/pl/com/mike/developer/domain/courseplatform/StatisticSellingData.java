package pl.com.mike.developer.domain.courseplatform;

import java.math.BigDecimal;

public class StatisticSellingData {
    private String name;
    private BigDecimal count;
    private BigDecimal sum;

    public StatisticSellingData(String name, BigDecimal count, BigDecimal sum) {
        this.name = name;
        this.count = count;
        this.sum = sum;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCount() {
        return count;
    }

    public BigDecimal getSum() {
        return sum;
    }
}


