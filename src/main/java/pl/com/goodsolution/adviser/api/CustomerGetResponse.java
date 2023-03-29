package pl.com.goodsolution.adviser.api;

import pl.com.goodsolution.adviser.domain.CustomerData;

import java.time.LocalTime;

public class CustomerGetResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String street;
    private String buildingNumber;
    private String apartmentNumber;
    private String postalCode;
    private String city;
    private String email;
    private Long groupId;
    private String active;
    private String registrationDate;
    private String registrationIp;
    private Boolean isRegulationAccepted;
    private Boolean isNewsletterAccepted;
    private Boolean demanding;

    private Boolean anotherWeekendAddress;
    private String weekendStreet;
    private String weekendHouseNo;
    private String weekendApartmentNo;
    private String weekendPostalCode;
    private String weekendCity;

    private Boolean invoice;
    private String invoiceCompanyName;
    private String invoiceTaxNo;
    private String invoiceStreet;
    private String invoiceHouseNo;
    private String invoiceApartmentNo;
    private String invoicePostalCode;
    private String invoiceCity;

    private LocalTime weekPreferredHoursTo;

    private Boolean anotherHoursWeekend;
    private LocalTime weekendPreferredHoursTo;

    private String comment;
    private String type;

    private Long defaultDriverId;
    private Long cityId;
    private Long weekendCityId;

    private String firstAndLastName;
    private Long ordersCount;

    private String customerFrom;

    public CustomerGetResponse(CustomerData customer) {
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.phoneNumber = customer.getPhoneNumber();
        this.street = customer.getStreet();
        this.buildingNumber = customer.getBuildingNumber();
        this.apartmentNumber = customer.getApartmentNumber();
        this.postalCode = customer.getPostalCode();
        this.city = customer.getCity();
        this.email = customer.getEmail();
        this.groupId = customer.getGroupId();
        this.active = customer.getActive();
        this.registrationDate = customer.getRegistrationDate();
        this.registrationIp = customer.getRegistrationIp();
        this.isRegulationAccepted = customer.getRegulationAccepted();
        this.isNewsletterAccepted = customer.getNewsletterAccepted();
        this.demanding = customer.getDemanding();
        this.anotherWeekendAddress = customer.getAnotherWeekendAddress();
        this.weekendStreet = customer.getWeekendStreet();
        this.weekendHouseNo = customer.getWeekendHouseNo();
        this.weekendApartmentNo = customer.getWeekendApartmentNo();
        this.weekendPostalCode = customer.getWeekendPostalCode();
        this.weekendCity = customer.getWeekendCity();
        this.invoice = customer.getInvoice();
        this.invoiceCompanyName = customer.getInvoiceCompanyName();
        this.invoiceTaxNo = customer.getInvoiceTaxNo();
        this.invoiceStreet = customer.getInvoiceStreet();
        this.invoiceHouseNo = customer.getInvoiceHouseNo();
        this.invoiceApartmentNo = customer.getInvoiceApartmentNo();
        this.invoicePostalCode = customer.getInvoicePostalCode();
        this.invoiceCity = customer.getInvoiceCity();
        this.weekPreferredHoursTo = customer.getWeekPreferredHoursTo();
        this.anotherHoursWeekend = customer.getAnotherHoursWeekend();
        this.weekendPreferredHoursTo = customer.getWeekendPreferredHoursTo();
        this.comment = customer.getComment();
        this.type = customer.getType();
        this.defaultDriverId = customer.getDefaultDriverId();
        this.cityId = customer.getCityId();
        this.weekendCityId = customer.getWeekendCityId();
        this.firstAndLastName = firstName + " " + lastName;
        this.ordersCount = customer.getOrdersCount();
        this.customerFrom = customer.getCustomerFrom();
    }

    public CustomerGetResponse(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.firstAndLastName = firstName + " " + lastName;
    }

    public CustomerGetResponse(Long id, String firstAndLastName, String email) {
        this.id = id;
        this.firstAndLastName = firstAndLastName;
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

    public String getFirstAndLastName() {
        return firstAndLastName;
    }

    public String getEmail() {
        return email;
    }

    public Long getDefaultDriverId() {
        return defaultDriverId;
    }

    public Boolean getDemanding() {
        return demanding;
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

    public String getCity() {
        return city;
    }

    public Long getGroupId() {
        return groupId;
    }

    public String getActive() {
        return active;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public String getRegistrationIp() {
        return registrationIp;
    }

    public Boolean getRegulationAccepted() {
        return isRegulationAccepted;
    }

    public Boolean getNewsletterAccepted() {
        return isNewsletterAccepted;
    }

    public Boolean getAnotherWeekendAddress() {
        return anotherWeekendAddress;
    }

    public String getWeekendStreet() {
        return weekendStreet;
    }

    public String getWeekendHouseNo() {
        return weekendHouseNo;
    }

    public String getWeekendApartmentNo() {
        return weekendApartmentNo;
    }

    public String getWeekendPostalCode() {
        return weekendPostalCode;
    }

    public String getWeekendCity() {
        return weekendCity;
    }

    public Boolean getInvoice() {
        return invoice;
    }

    public String getInvoiceCompanyName() {
        return invoiceCompanyName;
    }

    public String getInvoiceTaxNo() {
        return invoiceTaxNo;
    }

    public String getInvoiceStreet() {
        return invoiceStreet;
    }

    public String getInvoiceHouseNo() {
        return invoiceHouseNo;
    }

    public String getInvoiceApartmentNo() {
        return invoiceApartmentNo;
    }

    public String getInvoicePostalCode() {
        return invoicePostalCode;
    }

    public String getInvoiceCity() {
        return invoiceCity;
    }

    public LocalTime getWeekPreferredHoursTo() {
        return weekPreferredHoursTo;
    }

    public Boolean getAnotherHoursWeekend() {
        return anotherHoursWeekend;
    }

    public LocalTime getWeekendPreferredHoursTo() {
        return weekendPreferredHoursTo;
    }

    public String getComment() {
        return comment;
    }

    public String getType() {
        return type;
    }

    public Long getCityId() {
        return cityId;
    }

    public Long getWeekendCityId() {
        return weekendCityId;
    }

    public Long getOrdersCount() {
        return ordersCount;
    }

    public String getCustomerFrom() {
        return customerFrom;
    }
}
