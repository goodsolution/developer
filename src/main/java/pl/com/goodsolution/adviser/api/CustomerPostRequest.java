package pl.com.goodsolution.adviser.api;

public class CustomerPostRequest {

    private String mail;
    private Long group;
    private String status;
    private String name;
    private String surname;
    private String street;
    private String houseNumber;
    private String apartmentNumber;
    private String postalCode;
    private String city;
    private String customerFrom;
    private String type;

    public CustomerPostRequest() {
    }

    public CustomerPostRequest(String mail, Long group, String status, String name, String surname, String street, String houseNumber, String apartmentNumber, String postalCode, String city, String customerFrom, String type) {
        this.mail = mail;
        this.group = group;
        this.status = status;
        this.name = name;
        this.surname = surname;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.customerFrom = customerFrom;
        this.type = type;
    }

    public String getMail() {
        return mail;
    }

    public Long getGroup() {
        return group;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getStreet() {
        return street;
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

    public String getCustomerFrom() {
        return customerFrom;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "CustomerPostRequest{" +
                "mail='" + mail + '\'' +
                ", group=" + group +
                ", status='" + status + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", apartmentNumber='" + apartmentNumber + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
