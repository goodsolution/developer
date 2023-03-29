package pl.com.goodsolution.adviser.domain;

public class OrderCustomerData {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String street;
    private String buildingNumber;
    private String apartmentNumber;
    private String postalCode;
    private Long cityId;
    private String cityName;
    private String type;
    private String email;

    public OrderCustomerData(Long id, String firstName, String lastName, String phoneNumber, String street, String buildingNumber, String apartmentNumber, String postalCode, Long cityId, String cityName, String type, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
        this.postalCode = postalCode;
        this.cityId = cityId;
        this.cityName = cityName;
        this.type = type;
        this.email = email;
    }

    public OrderCustomerData(Long id, String firstName, String lastName, String phoneNumber, String street, String buildingNumber, String apartmentNumber, String postalCode, Long cityId, String cityName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
        this.postalCode = postalCode;
        this.cityId = cityId;
        this.cityName = cityName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
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

    public String getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }
}
