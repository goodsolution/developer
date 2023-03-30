package pl.com.mike.developer.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderDeliveryReleaseCreateData {
    private Long orderId;
    private Long deliveryId;
    private Long orderProductId;
    private LocalDate oldDate;
    private LocalDate newDate;
    private LocalDateTime releaseDate;
    private Long userId;
    private String userName;
    private String ip;
    private String userAgent;

    public OrderDeliveryReleaseCreateData(Long orderId, Long deliveryId, Long orderProductId, LocalDate oldDate, LocalDate newDate, LocalDateTime releaseDate, Long userId, String userName, String ip, String userAgent) {
        this.orderId = orderId;
        this.deliveryId = deliveryId;
        this.orderProductId = orderProductId;
        this.oldDate = oldDate;
        this.newDate = newDate;
        this.releaseDate = releaseDate;
        this.userId = userId;
        this.userName = userName;
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public Long getOrderProductId() {
        return orderProductId;
    }

    public LocalDate getOldDate() {
        return oldDate;
    }

    public LocalDate getNewDate() {
        return newDate;
    }

    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getIp() {
        return ip;
    }

    public String getUserAgent() {
        return userAgent;
    }
}
