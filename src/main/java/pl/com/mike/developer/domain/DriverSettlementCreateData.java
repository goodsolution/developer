package pl.com.mike.developer.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DriverSettlementCreateData {
    private Long deliveryId;
    private LocalDateTime dateTime;
    private BigDecimal amount;
    private Long driverId;
    private String address;

    public DriverSettlementCreateData(Long deliveryId, LocalDateTime dateTime, BigDecimal amount, Long driverId, String address) {
        this.deliveryId = deliveryId;
        this.dateTime = dateTime;
        this.amount = amount;
        this.driverId = driverId;
        this.address = address;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Long getDriverId() {
        return driverId;
    }

    public String getAddress() {
        return address;
    }
}
