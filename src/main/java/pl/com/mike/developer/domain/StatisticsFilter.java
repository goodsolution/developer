package pl.com.mike.developer.domain;

import java.time.LocalDate;

public class StatisticsFilter {
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Long driverId;
    private Long[] categoryIds;
    private String paymentStatus;
    private Long paymentId;
    private Long shippingId;

    public StatisticsFilter(LocalDate dateFrom, LocalDate dateTo, Long driverId, Long[] categoryIds, String paymentStatus, Long paymentId, Long shippingId) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.driverId = driverId;
        this.categoryIds = categoryIds;
        this.paymentStatus = paymentStatus;
        this.paymentId = paymentId;
        this.shippingId = shippingId;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public Long getDriverId() {
        return driverId;
    }

    public Long[] getCategoryIds() {
        return categoryIds;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public Long getShippingId() {
        return shippingId;
    }
}
