package pl.com.mike.developer.domain;

import java.math.BigDecimal;

public class DeliveryData {
    private Long id;
    private String dataType;
    private String firstName;
    private String lastName;
    private String companyName;
    private String street;
    private String buildingNumber;
    private String apartmentNumber;
    private String postalCode;
    private Long cityId;
    private String cityName;
    private String operatorName;
    private Long quantity;
    private BigDecimal price;

    public DeliveryData(Long id, String dataType, String firstName, String lastName, String companyName, String street, String buildingNumber, String apartmentNumber, String postalCode, Long cityId, String cityName, String operatorName, Long quantity, BigDecimal price) {
        this.id = id;
        this.dataType = dataType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
        this.postalCode = postalCode;
        this.cityId = cityId;
        this.cityName = cityName;
        this.operatorName = operatorName;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getDataType() {
        return dataType;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCompanyName() {
        return companyName;
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

    public Long getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
