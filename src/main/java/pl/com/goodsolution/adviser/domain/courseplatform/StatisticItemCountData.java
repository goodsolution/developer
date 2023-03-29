package pl.com.goodsolution.adviser.domain.courseplatform;

import java.time.LocalDateTime;

public class StatisticItemCountData {
    private String name;
    private Long count;

    public StatisticItemCountData(String name, Long count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public Long getCount() {
        return count;
    }
}


