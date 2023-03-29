package pl.com.goodsolution.adviser.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class OrderDeliveryUpdateData {
    private Long orderId;
    private Long deliveryId;
    private Long cityId;
    private String street;
    private String buildingNumber;
    private String apartmentNumber;
    private String postalCode;
    private LocalDateTime suspensionDate;
    private Boolean complete;
    private LocalTime hourFrom;
    private LocalTime hourTo;
    private LocalDate startDate;
    private String userAgent;

    public OrderDeliveryUpdateData(Long orderId, Long deliveryId, Long cityId, String street, String buildingNumber, String apartmentNumber, String postalCode, LocalDateTime suspensionDate, Boolean complete, LocalTime hourFrom, LocalTime hourTo, LocalDate startDate, String userAgent) {
        this.orderId = orderId;
        this.deliveryId = deliveryId;
        this.cityId = cityId;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
        this.postalCode = postalCode;
        this.suspensionDate = suspensionDate;
        this.complete = complete;
        this.hourFrom = hourFrom;
        this.hourTo = hourTo;
        this.startDate = startDate;
        this.userAgent = userAgent;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public Long getCityId() {
        return cityId;
    }

    public String getStreet() {
        return street;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public LocalDateTime getSuspensionDate() {
        return suspensionDate;
    }

    public Boolean getComplete() {
        return complete;
    }

    public LocalTime getHourFrom() {
        return hourFrom;
    }

    public LocalTime getHourTo() {
        return hourTo;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public String getUserAgent() {
        return userAgent;
    }
}
