package pl.com.goodsolution.adviser.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class OrderProductUpdateData {
    private Long orderId;
    private Long orderProductId;
    private Long dietId;
    private Long dietTypeId;
    private Long quantity;
    private Long days;
    private Long weekendOptionId;
    private Boolean testDay;
    private Boolean extrasOne;
    private LocalDate suspensionDate;
    private LocalDate startDate;
    private Set<Long> extraIds;
    private Boolean changePrice;
    private BigDecimal changePriceValue;

    public OrderProductUpdateData(Long orderId, Long orderProductId, Long dietId, Long dietTypeId, Long quantity, Long days, Long weekendOptionId, Boolean testDay, Boolean extrasOne, LocalDate suspensionDate, LocalDate startDate, Set<Long> extraIds, Boolean changePrice, BigDecimal changePriceValue) {
        this.orderId = orderId;
        this.orderProductId = orderProductId;
        this.dietId = dietId;
        this.dietTypeId = dietTypeId;
        this.quantity = quantity;
        this.days = days;
        this.weekendOptionId = weekendOptionId;
        this.testDay = testDay;
        this.extrasOne = extrasOne;
        this.suspensionDate = suspensionDate;
        this.startDate = startDate;
        this.extraIds = extraIds;
        this.changePrice = changePrice;
        this.changePriceValue = changePriceValue;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getOrderProductId() {
        return orderProductId;
    }

    public Long getDietId() {
        return dietId;
    }

    public Long getQuantity() {
        return quantity;
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

    public LocalDate getSuspensionDate() {
        return suspensionDate;
    }

    public Long getDietTypeId() {
        return dietTypeId;
    }

    public Set<Long> getExtraIds() {
        return extraIds;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Boolean getChangePrice() {
        return changePrice;
    }

    public BigDecimal getChangePriceValue() {
        return changePriceValue;
    }


}
