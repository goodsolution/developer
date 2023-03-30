package pl.com.mike.developer.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class OrderChangeData {
    private Long no;
    private LocalDateTime dateTime;
    private String name;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String street;
    private String buildingNumber;
    private String apartmentNumber;
    private String postalCode;
    private String cityName;
    private LocalTime hourFrom;
    private LocalTime hourTo;
    private String demandingCustomerIndicator;
    private String paymentMethodName;
    private String cardPaymentIndicator;
    private String paymentStatus;
    private String deliveryMethodName;
    private Long id;

    private List<OrderChangeProductData> products;


    public OrderChangeData(Long no, LocalDateTime dateTime, String name, String firstName, String lastName, String phoneNumber, String street, String buildingNumber, String apartmentNumber, String postalCode, String cityName, LocalTime hourFrom, LocalTime hourTo, String demandingCustomerIndicator, String paymentMethodName, String cardPaymentIndicator, String paymentStatus, String deliveryMethodName, Long id, List<OrderChangeProductData> products) {
        this.no = no;
        this.dateTime = dateTime;
        this.name = name;
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
        this.id = id;
        this.products = products;
    }


    public Long getNo() {
        return no;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getName() {
        return name;
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

    public LocalTime getHourFrom() {
        return hourFrom;
    }

    public LocalTime getHourTo() {
        return hourTo;
    }

    public List<OrderChangeProductData> getProducts() {
        return products;
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

    public Long getId() {
        return id;
    }
}
