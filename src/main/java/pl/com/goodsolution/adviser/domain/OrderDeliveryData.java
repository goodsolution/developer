package pl.com.goodsolution.adviser.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class OrderDeliveryData {
    private Long id;
    private Long no;
    private String productName;
    private String driver;
    private LocalDate date;
    private LocalDate deliveryDate;
    private LocalTime hourFrom;
    private LocalTime hourTo;
    private String address;
    private Long cityId;
    private int numberOfPhotos;
    private Integer status;
    private LocalDateTime deliveredDate;
    private Boolean weekend;
    private Integer dayOfWeekValue;




    public OrderDeliveryData(Long id, Long no, String productName, String driver, LocalDate date, LocalDate deliveryDate, LocalTime hourFrom, LocalTime hourTo, String address, Long cityId, int numberOfPhotos, Integer status, LocalDateTime deliveredDate, Boolean weekend, Integer dayOfWeekValue) {
        this.id = id;
        this.no = no;
        this.productName = productName;
        this.driver = driver;
        this.date = date;
        this.deliveryDate = deliveryDate;
        this.hourFrom = hourFrom;
        this.hourTo = hourTo;
        this.address = address;
        this.cityId = cityId;
        this.numberOfPhotos = numberOfPhotos;
        this.status = status;
        this.deliveredDate = deliveredDate;
        this.weekend = weekend;
        this.dayOfWeekValue = dayOfWeekValue;
    }

    public OrderDeliveryData(Long id, Long no, String productName, String driver, LocalTime hourFrom, LocalTime hourTo) {
        this.id = id;
        this.no = no;
        this.productName = productName;
        this.driver = driver;
        this.hourFrom = hourFrom;
        this.hourTo = hourTo;
    }


    public Long getId() {
        return id;
    }

    public Long getNo() {
        return no;
    }

    public String getProductName() {
        return productName;
    }

    public String getDriver() {
        return driver;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getHourFrom() {
        return hourFrom;
    }

    public LocalTime getHourTo() {
        return hourTo;
    }

    public String getAddress() {
        return address;
    }

    public Long getCityId() {
        return cityId;
    }

    public int getNumberOfPhotos() {
        return numberOfPhotos;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public Integer getStatus() {
        return status;
    }

    public LocalDateTime getDeliveredDate() {
        return deliveredDate;
    }

    public Boolean getWeekend() {
        return weekend;
    }

    public Integer getDayOfWeekValue() {
        return dayOfWeekValue;
    }
}
