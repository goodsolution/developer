package pl.com.goodsolution.adviser.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class OrderDeliveryOrderData {
    private Long id;
    private Long orderId;
    private Long orderProductId;
    private Long driverId;
    private LocalDate deliveryDate;
    private LocalTime hourFrom;
    private LocalTime hourTo;
    private Boolean da;
    private String daCity;
    private String daStreet;
    private String daHouseNo;
    private String daApartmentNo;
    private String daPostalCode;
    private Long status;
    private Boolean sunday;
    private LocalDate originalDeliveryDate;


    public OrderDeliveryOrderData(Long id, Long driverId, LocalDate deliveryDate, Boolean sunday, LocalDate originalDeliveryDate) {
        this.id = id;
        this.driverId = driverId;
        this.deliveryDate = deliveryDate;
        this.sunday = sunday;
        this.originalDeliveryDate = originalDeliveryDate;
    }

    public OrderDeliveryOrderData(Long id, LocalDate deliveryDate) {
        this.id = id;
        this.deliveryDate = deliveryDate;
    }

    public OrderDeliveryOrderData(Long id, LocalDate deliveryDate, Long orderId) {
        this.id = id;
        this.deliveryDate = deliveryDate;
        this.orderId = orderId;
    }

    public OrderDeliveryOrderData(Long orderId, Long orderProductId, Long driverId, LocalDate deliveryDate, LocalTime hourFrom, LocalTime hourTo, Boolean da, String daCity, String daStreet, String daHouseNo, String daApartmentNo, String daPostalCode, Long status, LocalDate originalDeliveryDate) {
        this.orderId = orderId;
        this.orderProductId = orderProductId;
        this.driverId = driverId;
        this.deliveryDate = deliveryDate;
        this.hourFrom = hourFrom;
        this.hourTo = hourTo;
        this.da = da;
        this.daCity = daCity;
        this.daStreet = daStreet;
        this.daHouseNo = daHouseNo;
        this.daApartmentNo = daApartmentNo;
        this.daPostalCode = daPostalCode;
        this.status = status;
        this.originalDeliveryDate = originalDeliveryDate;
    }

    public OrderDeliveryOrderData(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getOrderProductId() {
        return orderProductId;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public LocalTime getHourFrom() {
        return hourFrom;
    }

    public LocalTime getHourTo() {
        return hourTo;
    }

    public Long getDriverId() {
        return driverId;
    }

    public Boolean getDa() {
        return da;
    }

    public String getDaCity() {
        return daCity;
    }

    public String getDaStreet() {
        return daStreet;
    }

    public String getDaHouseNo() {
        return daHouseNo;
    }

    public String getDaApartmentNo() {
        return daApartmentNo;
    }

    public String getDaPostalCode() {
        return daPostalCode;
    }

    public Long getId() {
        return id;
    }

    public Long getStatus() {
        return status;
    }

    public Boolean getSunday() {
        return sunday;
    }

    public LocalDate getOriginalDeliveryDate() {
        return originalDeliveryDate;
    }
}
