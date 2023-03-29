package pl.com.goodsolution.adviser.domain;

import java.time.LocalTime;

public class OrderDeliveryChangeHoursData {
    private Long orderId;
    private Long deliveryId;
    private LocalTime hourFrom;
    private LocalTime hourTo;

    public OrderDeliveryChangeHoursData(Long orderId, Long deliveryId, LocalTime hourFrom, LocalTime hourTo) {
        this.orderId = orderId;
        this.deliveryId = deliveryId;
        this.hourFrom = hourFrom;
        this.hourTo = hourTo;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public LocalTime getHourFrom() {
        return hourFrom;
    }

    public LocalTime getHourTo() {
        return hourTo;
    }
}
