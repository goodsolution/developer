package pl.com.goodsolution.adviser.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class OrderProductCreateData {
    private Long orderId;
    private Long dietId;
    private Long dietTypeId;
    private Long quantity;
    private LocalDate dateFrom;
    private Long days;
    private Long weekendOptionId;
    private Boolean testDay;
    private Boolean extrasOne;
    private Set<Long> extraIds;
    private BigDecimal price;
    private Long orderProductId;

    public OrderProductCreateData(Long orderId, Long dietId, Long dietTypeId, Long quantity, LocalDate dateFrom, Long days, Long weekendOptionId, Boolean testDay, Boolean extrasOne, Set<Long> extraIds, Long orderProductId) {
        this.orderId = orderId;
        this.dietId = dietId;
        this.dietTypeId = dietTypeId;
        this.quantity = quantity;
        this.dateFrom = dateFrom;
        this.days = days;
        this.weekendOptionId = weekendOptionId;
        this.testDay = testDay;
        this.extrasOne = extrasOne;
        this.extraIds = extraIds;
        this.orderProductId = orderProductId;
    }

    public OrderProductCreateData(Long orderId, Long dietId, Long dietTypeId, Long quantity, LocalDate dateFrom, Long days, Long weekendOptionId, Boolean testDay, Boolean extrasOne, Set<Long> extraIds) {
        this.orderId = orderId;
        this.dietId = dietId;
        this.dietTypeId = dietTypeId;
        this.quantity = quantity;
        this.dateFrom = dateFrom;
        this.days = days;
        this.weekendOptionId = weekendOptionId;
        this.testDay = testDay;
        this.extrasOne = extrasOne;
        this.extraIds = extraIds;
    }

    public OrderProductCreateData(Long orderId, Long dietId, Long dietTypeId, Long quantity, LocalDate dateFrom, Long days, Long weekendOptionId, Boolean testDay, Boolean extrasOne, Set<Long> extraIds, BigDecimal price) {
        this.orderId = orderId;
        this.dietId = dietId;
        this.dietTypeId = dietTypeId;
        this.quantity = quantity;
        this.dateFrom = dateFrom;
        this.days = days;
        this.weekendOptionId = weekendOptionId;
        this.testDay = testDay;
        this.extrasOne = extrasOne;
        this.extraIds = extraIds;
        this.price = price;
    }

    public Long getOrderId() {
        return orderId;
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

    public Set<Long> getExtraIds() {
        return extraIds;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setOrderId(Long orderId) { //TODO to delete after code change
        this.orderId = orderId;
    }

    public Long getOrderProductId() {
        return orderProductId;
    }
}
