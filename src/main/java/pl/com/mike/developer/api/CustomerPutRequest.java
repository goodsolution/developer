package pl.com.mike.developer.api;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

public class CustomerPutRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private String street;
    private String houseNumber;
    private String apartmentNumber;
    private String postalCode;
    private String city;
    private String email;
    private Long groupId;
    private String statusId;
    private Boolean demanding;
    private Boolean anotherWeekendAddress;
    private String weekendStreet;
    private String weekendHouseNumber;
    private String weekendApartmentNumber;
    private String weekendPostalCode;
    private String weekendCity;
    private Boolean invoice;
    private String invoiceCompanyName;
    private String invoiceTaxNo;
    private String invoiceStreet;
    private String invoiceHouseNumber;
    private String invoiceApartmentNumber;
    private String invoicePostalCode;
    private String invoiceCity;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalTime weekPreferredHoursTo;
    private Boolean anotherHoursWeekend;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalTime weekendPreferredHoursTo;
    private String comment;

    public CustomerPutRequest() {
    }

    public CustomerPutRequest(String firstName, String lastName, String phone, String street, String houseNumber, String apartmentNumber, String postalCode, String city, String email, Long groupId, String statusId, Boolean demanding, Boolean anotherWeekendAddress, String weekendStreet, String weekendHouseNumber, String weekendApartmentNumber, String weekendPostalCode, String weekendCity, Boolean invoice, String invoiceCompanyName, String invoiceTaxNo, String invoiceStreet, String invoiceHouseNumber, String invoiceApartmentNumber, String invoicePostalCode, String invoiceCity, LocalTime weekPreferredHoursTo, Boolean anotherHoursWeekend, LocalTime weekendPreferredHoursTo, String comment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.email = email;
        this.groupId = groupId;
        this.statusId = statusId;
        this.demanding = demanding;
        this.anotherWeekendAddress = anotherWeekendAddress;
        this.weekendStreet = weekendStreet;
        this.weekendHouseNumber = weekendHouseNumber;
        this.weekendApartmentNumber = weekendApartmentNumber;
        this.weekendPostalCode = weekendPostalCode;
        this.weekendCity = weekendCity;
        this.invoice = invoice;
        this.invoiceCompanyName = invoiceCompanyName;
        this.invoiceTaxNo = invoiceTaxNo;
        this.invoiceStreet = invoiceStreet;
        this.invoiceHouseNumber = invoiceHouseNumber;
        this.invoiceApartmentNumber = invoiceApartmentNumber;
        this.invoicePostalCode = invoicePostalCode;
        this.invoiceCity = invoiceCity;
        this.weekPreferredHoursTo = weekPreferredHoursTo;
        this.anotherHoursWeekend = anotherHoursWeekend;
        this.weekendPreferredHoursTo = weekendPreferredHoursTo;
        this.comment = comment;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
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

    public String getEmail() {
        return email;
    }

    public Long getGroupId() {
        return groupId;
    }

    public String getStatusId() {
        return statusId;
    }

    public Boolean getAnotherWeekendAddress() {
        return anotherWeekendAddress;
    }

    public String getWeekendStreet() {
        return weekendStreet;
    }

    public String getWeekendHouseNumber() {
        return weekendHouseNumber;
    }

    public String getWeekendApartmentNumber() {
        return weekendApartmentNumber;
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

    public String getInvoiceHouseNumber() {
        return invoiceHouseNumber;
    }

    public String getInvoiceApartmentNumber() {
        return invoiceApartmentNumber;
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

    public Boolean getDemanding() {
        return demanding;
    }

    @Override
    public String toString() {
        return "CustomerPutRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", apartmentNumber='" + apartmentNumber + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", cityId=" + city +
                ", email='" + email + '\'' +
                ", groupId=" + groupId +
                ", statusId='" + statusId + '\'' +
                ", anotherWeekendAddress=" + anotherWeekendAddress +
                ", weekendStreet='" + weekendStreet + '\'' +
                ", weekendHouseNumber='" + weekendHouseNumber + '\'' +
                ", weekendApartmentNumber='" + weekendApartmentNumber + '\'' +
                ", weekendPostalCode='" + weekendPostalCode + '\'' +
                ", weekendCityId=" + weekendCity +
                ", invoice=" + invoice +
                ", invoiceCompanyName='" + invoiceCompanyName + '\'' +
                ", invoiceTaxNo='" + invoiceTaxNo + '\'' +
                ", invoiceStreet='" + invoiceStreet + '\'' +
                ", invoiceHouseNumber='" + invoiceHouseNumber + '\'' +
                ", invoiceApartmentNumber='" + invoiceApartmentNumber + '\'' +
                ", invoicePostalCode='" + invoicePostalCode + '\'' +
                ", invoiceCity='" + invoiceCity + '\'' +
                ", weekPreferredHoursTo=" + weekPreferredHoursTo +
                ", anotherHoursWeekend=" + anotherHoursWeekend +
                ", weekendPreferredHoursTo=" + weekendPreferredHoursTo +
                ", comment='" + comment + '\'' +
                '}';
    }
}
