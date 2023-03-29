package pl.com.goodsolution.adviser.api;

public class OrderDeliveryGetResponse {
    private Long id;
    private Long no;
    private Long cityId;
    private String cityName;
    private String street;
    private String buildingNumber;
    private String apartmentNumber;
    private String postalCode;


    private String address;
    private String completionDateTime;
    private String hourFrom; //format: "H:mm"
    private String hourTo; //format: "H:mm"

    private String productName;
    private String driver;
    private String date;
    private int numberOfPhotos;
    private String deliveredDateTime;
    private Integer status;
    private Boolean weekend;
    private String deliveryDate;

    public OrderDeliveryGetResponse() {
    }

    public OrderDeliveryGetResponse(Long id, Long no, Long cityId, String cityName, String street, String buildingNumber, String apartmentNumber, String postalCode, String address, String completionDateTime, String hourFrom, String hourTo, String productName, String driver, String date, int numberOfPhotos, String deliveredDateTime, Integer status, Boolean weekend, String deliveryDate) {
        this.id = id;
        this.no = no;
        this.cityId = cityId;
        this.cityName = cityName;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
        this.postalCode = postalCode;
        this.address = address;
        this.completionDateTime = completionDateTime;
        this.hourFrom = hourFrom;
        this.hourTo = hourTo;
        this.productName = productName;
        this.driver = driver;
        this.date = date;
        this.numberOfPhotos = numberOfPhotos;
        this.deliveredDateTime = deliveredDateTime;
        this.status = status;
        this.weekend = weekend;
        this.deliveryDate = deliveryDate;
    }


    public Long getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
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

    public String getAddress() {
        return address;
    }

    public String getCompletionDateTime() {
        return completionDateTime;
    }

    public String getHourFrom() {
        return hourFrom;
    }

    public String getHourTo() {
        return hourTo;
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

    public String getDate() {
        return date;
    }

    public int getNumberOfPhotos() {
        return numberOfPhotos;
    }

    public String getDeliveredDateTime() {
        return deliveredDateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public Boolean getWeekend() {
        return weekend;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }
}
