package pl.com.mike.developer.domain;

public class OrderWeekendAddressData {
    private boolean weekendAddressStatus;
    private String street;
    private String buildingNumber;
    private String apartmentNumber;
    private String postalCode;
    private Long cityId;
    private String cityName;

    public OrderWeekendAddressData(boolean weekendAddressStatus, String street, String buildingNumber, String apartmentNumber, String postalCode, Long cityId, String cityName) {
        this.weekendAddressStatus = weekendAddressStatus;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
        this.postalCode = postalCode;
        this.cityId = cityId;
        this.cityName = cityName;
    }

    public boolean getWeekendAddressStatus() {
        return weekendAddressStatus;
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
}
