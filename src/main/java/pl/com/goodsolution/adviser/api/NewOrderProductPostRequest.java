package pl.com.goodsolution.adviser.api;

import java.time.LocalDate;
import java.util.Arrays;

public class NewOrderProductPostRequest {
    private Long[] dietId;
    private Long[] dietTypeId;
    private Long[] days;
    private Boolean[] testDay;
    private LocalDate[] startDate;
    private Long[] weekendOptionId;
    private Boolean[] extrasOne;
    private Long[] quantity;
    private String[] extras;


    public NewOrderProductPostRequest() {
    }

    public NewOrderProductPostRequest(Long[] dietId, Long[] dietTypeId, Long[] days, Boolean[] testDay, LocalDate[] startDate, Long[] weekendOptionId, Boolean[] extrasOne, Long[] quantity, String[] extras) {
        this.dietId = dietId;
        this.dietTypeId = dietTypeId;
        this.days = days;
        this.testDay = testDay;
        this.startDate = startDate;
        this.weekendOptionId = weekendOptionId;
        this.extrasOne = extrasOne;
        this.quantity = quantity;
        this.extras = extras;
    }


    public Long[] getDietId() {
        return dietId;
    }

    public Long[] getDietTypeId() {
        return dietTypeId;
    }

    public Long[] getDays() {
        return days;
    }

    public Boolean[] getTestDay() {
        return testDay;
    }

    public LocalDate[] getStartDate() {
        return startDate;
    }

    public Long[] getWeekendOptionId() {
        return weekendOptionId;
    }

    public Boolean[] getExtrasOne() {
        return extrasOne;
    }

    public Long[] getQuantity() {
        return quantity;
    }

    public String[] getExtras() {
        return extras;
    }

    @Override
    public String toString() {
        return "NewOrderProductPostRequest{" +
                "dietId=" + Arrays.toString(dietId) +
                ", dietTypeId=" + Arrays.toString(dietTypeId) +
                ", days=" + Arrays.toString(days) +
                ", testDay=" + Arrays.toString(testDay) +
                ", startDate=" + Arrays.toString(startDate) +
                ", weekendOptionId=" + Arrays.toString(weekendOptionId) +
                ", extrasOne=" + Arrays.toString(extrasOne) +
                ", quantity=" + Arrays.toString(quantity) +
                ", extras=" + Arrays.toString(extras) +
                '}';
    }
}
