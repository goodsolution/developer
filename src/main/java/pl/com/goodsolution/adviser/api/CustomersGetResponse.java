package pl.com.goodsolution.adviser.api;

public class CustomersGetResponse {
    private Long id;
    private String name;
    private String surname;
    private String addressFirstLine;
    private String addressSecondLine;
    private String houseNumber;
    private String apartmentNumber;
    private String postalCode;
    private String city;
    private String email;
    private String registrationDate;
    private String type;
    private Long groupId;
    private String active;
    private Long ordersCount;

    public CustomersGetResponse(Long id, String name, String surname, String addressFirstLine, String addressSecondLine, String houseNumber, String apartmentNumber, String postalCode, String city, String email, String registrationDate, String type, Long groupId, String active, Long ordersCount) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.addressFirstLine = addressFirstLine;
        this.addressSecondLine = addressSecondLine;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.email = email;
        this.registrationDate = registrationDate;
        this.type = type;
        this.groupId = groupId;
        this.active = active;
        this.ordersCount = ordersCount;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddressFirstLine() {
        return addressFirstLine;
    }

    public String getAddressSecondLine() {
        return addressSecondLine;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public String getType() {
        return type;
    }

    public Long getGroupId() {
        return groupId;
    }

    public String getActive() {
        return active;
    }

    public Long getOrdersCount() {
        return ordersCount;
    }
}
