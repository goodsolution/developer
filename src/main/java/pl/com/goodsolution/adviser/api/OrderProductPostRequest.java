package pl.com.goodsolution.adviser.api;

import java.time.LocalDate;
import java.util.Set;

public class OrderProductPostRequest {
    private Long dietId;
    private Long dietTypeId;
    private Long quantity;
    private LocalDate dateFrom;
    private Long days;
    private Long weekendOptionId;
    private Boolean testDay;
    private Boolean extrasOne;
    private Set<Long> extras;


    public OrderProductPostRequest() {
    }

    public OrderProductPostRequest(Long dietId, Long dietTypeId, Long quantity, LocalDate dateFrom, Long days, Long weekendOptionId, Boolean testDay, Boolean extrasOne, Set<Long> extras) {
        this.dietId = dietId;
        this.dietTypeId = dietTypeId;
        this.quantity = quantity;
        this.dateFrom = dateFrom;
        this.days = days;
        this.weekendOptionId = weekendOptionId;
        this.testDay = testDay;
        this.extrasOne = extrasOne;
        this.extras = extras;
    }


    public Long getDietId() {
        return dietId;
    }

    public Long getDietTypeId() {
        return dietTypeId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public Long getDays() {
        return days;
    }

    public Long getWeekendOptionId() {
        return weekendOptionId;
    }

    public Boolean getTestDay() {
        return testDay;
    }

    public Boolean getExtrasOne() {
        return extrasOne;
    }

    public Set<Long> getExtras() {
        return extras;
    }

    @Override
    public String toString() {
        return "OrderProductPostRequest{" +
                "dietId=" + dietId +
                ", dietTypeId=" + dietTypeId +
                ", quantity=" + quantity +
                ", dateFrom=" + dateFrom +
                ", days=" + days +
                ", weekendOptionId=" + weekendOptionId +
                ", testDay=" + testDay +
                ", extrasOne=" + extrasOne +
                ", extras=" + extras +
                '}';
    }
}
