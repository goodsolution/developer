package pl.com.mike.developer.domain;

import java.time.LocalDate;

public class CustomerNewFilter {
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Long[] categoryIds;
    private Long orderStatusId;

    public CustomerNewFilter(LocalDate dateFrom, LocalDate dateTo, Long[] categoryIds, Long orderStatusId) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.categoryIds = categoryIds;
        this.orderStatusId = orderStatusId;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public Long[] getCategoryIds() {
        return categoryIds;
    }

    public Long getOrderStatusId() {
        return orderStatusId;
    }
}
