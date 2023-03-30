package pl.com.mike.developer.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class OrderProductPutRequest {
    private Long dietId;
    private Long typeId;
    private Long quantity;
    private Long days;
    private Long weekendOptionId;
    private Boolean testDay;
    private Boolean extrasOne;
    private LocalDate suspensionDate;
    private LocalDate startDate;
    private Boolean changePrice;
    private BigDecimal changePriceValue;
    private Set<Long> extras;


    public OrderProductPutRequest() {
    }

    public OrderProductPutRequest(Long dietId, Long typeId, Long quantity, Long days, Long weekendOptionId, Boolean testDay, Boolean extrasOne, LocalDate suspensionDate, LocalDate startDate, Boolean changePrice, BigDecimal changePriceValue, Set<Long> extras) {
        this.dietId = dietId;
        this.typeId = typeId;
        this.quantity = quantity;
        this.days = days;
        this.weekendOptionId = weekendOptionId;
        this.testDay = testDay;
        this.extrasOne = extrasOne;
        this.suspensionDate = suspensionDate;
        this.startDate = startDate;
        this.changePrice = changePrice;
        this.changePriceValue = changePriceValue;
        this.extras = extras;
    }


    public Long getDietId() {
        return dietId;
    }

    public Long getTypeId() {
        return typeId;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public Boolean getChangePrice() {
        return changePrice;
    }

    public BigDecimal getChangePriceValue() {
        return changePriceValue;
    }

    public Set<Long> getExtras() {
        return extras;
    }

    @Override
    public String toString() {
        return "OrderProductPutRequest{" +
                "dietId=" + dietId +
                ", typeId=" + typeId +
                ", quantity=" + quantity +
                ", days=" + days +
                ", weekendOptionId=" + weekendOptionId +
                ", testDay=" + testDay +
                ", extrasOne=" + extrasOne +
                ", suspensionDate=" + suspensionDate +
                ", startDate=" + startDate +
                ", changePrice=" + changePrice +
                ", changePriceValue=" + changePriceValue +
                ", extras=" + extras +
                '}';
    }
}
