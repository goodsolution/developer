package pl.com.goodsolution.adviser.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderDeliveryOrderCreateData {
    private Long orderId;
    private Long productId;
    private Long driverId;
    private LocalDate deliveryDate;
    private Long priority;
    private Boolean sunday;
    private String status;
    private LocalDateTime timestamp;
    private Boolean weekend;
    private Integer dayOfWeekValue;
    private Long orderProductId;
    private LocalDate originalDeliveryDate;

    public OrderDeliveryOrderCreateData(Long orderId, Long productId, Long driverId, LocalDate deliveryDate, Long priority, Boolean sunday, String status, Boolean weekend, Integer dayOfWeekValue, Long orderProductId, LocalDate originalDeliveryDate) {
        this.orderId = orderId;
        this.productId = productId;
        this.driverId = driverId;
        this.deliveryDate = deliveryDate;
        this.priority = priority;
        this.sunday = sunday;
        this.status = status;
        this.weekend = weekend;
        this.dayOfWeekValue = dayOfWeekValue;
        this.orderProductId = orderProductId;
        this.originalDeliveryDate = originalDeliveryDate;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public Long getPriority() {
        return priority;
    }

    public Boolean getSunday() {
        return sunday;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Boolean getWeekend() {
        return weekend;
    }

    public Integer getDayOfWeekValue() {
        return dayOfWeekValue;
    }

    public Long getOrderProductId() {
        return orderProductId;
    }

    public LocalDate getOriginalDeliveryDate() {
        return originalDeliveryDate;
    }
}
