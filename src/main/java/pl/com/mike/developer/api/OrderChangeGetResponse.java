package pl.com.mike.developer.api;

import java.util.List;

public class OrderChangeGetResponse {
    private Long no;
    private Long id;
    private String name;
    private String dateTime;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String street;
    private String buildingNumber;
    private String apartmentNumber;
    private String postalCode;
    private String cityName;
    private String hourFrom;
    private String hourTo;
    private String demandingCustomerIndicator;
    private String paymentMethodName;
    private String cardPaymentIndicator;
    private String paymentStatus;
    private String deliveryMethodName;

    private List<OrderChangeProductGetResponse> products;

    public OrderChangeGetResponse(Long no, Long id, String name, String dateTime) {
        this.no = no;
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    public OrderChangeGetResponse(Long no, Long id, String name, String dateTime, String firstName, String lastName, String phoneNumber, String street, String buildingNumber, String apartmentNumber, String postalCode, String cityName, String hourFrom, String hourTo, String demandingCustomerIndicator, String paymentMethodName, String cardPaymentIndicator, String paymentStatus, String deliveryMethodName, List<OrderChangeProductGetResponse> products) {
        this.no = no;
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
        this.postalCode = postalCode;
        this.cityName = cityName;
        this.hourFrom = hourFrom;
        this.hourTo = hourTo;
        this.demandingCustomerIndicator = demandingCustomerIndicator;
        this.paymentMethodName = paymentMethodName;
        this.cardPaymentIndicator = cardPaymentIndicator;
        this.paymentStatus = paymentStatus;
        this.deliveryMethodName = deliveryMethodName;
        this.products = products;
    }

    public Long getNo() {
        return no;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDateTime() {
        return dateTime;
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

    public String getCityName() {
        return cityName;
    }

    public String getHourFrom() {
        return hourFrom;
    }

    public String getHourTo() {
        return hourTo;
    }

    public String getDemandingCustomerIndicator() {
        return demandingCustomerIndicator;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public String getCardPaymentIndicator() {
        return cardPaymentIndicator;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getDeliveryMethodName() {
        return deliveryMethodName;
    }

    public List<OrderChangeProductGetResponse> getProducts() {
        return products;
    }
}
