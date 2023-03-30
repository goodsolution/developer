package pl.com.mike.developer.api;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class OrderDeliveryPutRequest {
    private Long cityId;
    private String street;
    private String buildingNumber;
    private String apartmentNumber;
    private String postalCode;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime suspensionDate;

    private Boolean complete;

    private LocalTime hourFrom;
    private LocalTime hourTo;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    public OrderDeliveryPutRequest() {
    }

    public OrderDeliveryPutRequest(LocalTime hourFrom, LocalTime hourTo) {
        this.hourFrom = hourFrom;
        this.hourTo = hourTo;
    }

    public OrderDeliveryPutRequest(Long cityId, String street, String buildingNumber, String apartmentNumber, String postalCode, LocalDateTime suspensionDate, Boolean complete, LocalTime hourFrom, LocalTime hourTo) {
        this.cityId = cityId;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
        this.postalCode = postalCode;
        this.suspensionDate = suspensionDate;
        this.complete = complete;
        this.hourFrom = hourFrom;
        this.hourTo = hourTo;
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
}
