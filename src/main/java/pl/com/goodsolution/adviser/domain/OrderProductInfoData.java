package pl.com.goodsolution.adviser.domain;

import java.time.LocalDate;

public class OrderProductInfoData {
    private Long id;
    private Long orderId;
    private Long days;
    private Long quantity;
    private Long weekendOptionId;
    private LocalDate dateFrom;
    private Boolean stopped;


    public OrderProductInfoData(Long id, Long orderId, Long days, Long quantity, Long weekendOptionId, LocalDate dateFrom, Boolean stopped) {
        this.id = id;
        this.orderId = orderId;
        this.days = days;
        this.quantity = quantity;
        this.weekendOptionId = weekendOptionId;
        this.dateFrom = dateFrom;
        this.stopped = stopped;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getDays() {
        return days;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Long getWeekendOptionId() {
        return weekendOptionId;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public Boolean getStopped() {
        return stopped;
    }
}
