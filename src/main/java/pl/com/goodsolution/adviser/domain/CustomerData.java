package pl.com.goodsolution.adviser.domain;


import java.time.LocalTime;

public class CustomerData {
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

    private String registrationDate;
    private String registrationIp;
    private Boolean isRegulationAccepted;
    private Boolean isNewsletterAccepted;
    private Boolean demanding;
    private String type;
    private Long defaultDriverId;
    private Long cityId;
    private Long weekendCityId;
    private String firstAddressLine;
    private String secondAddressLine;
    private Long ordersCount;
    private String customerFrom;


    public CustomerData(Long id, String firstName, String lastName, String phoneNumber, String street, String buildingNumber, String apartmentNumber, String postalCode, String city, String email, Long groupId, String active, Boolean demanding, Boolean anotherWeekendAddress, String weekendStreet, String weekendHouseNo, String weekendApartmentNo, String weekendPostalCode, String weekendCity, Boolean invoice, String invoiceCompanyName, String invoiceTaxNo, String invoiceStreet, String invoiceHouseNo, String invoiceApartmentNo, String invoicePostalCode, String invoiceCity, LocalTime weekPreferredHoursTo, Boolean anotherHoursWeekend, LocalTime weekendPreferredHoursTo, String comment) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.email = email;
        this.groupId = groupId;
        this.active = active;
        this.demanding = demanding;
        this.anotherWeekendAddress = anotherWeekendAddress;
        this.weekendStreet = weekendStreet;
        this.weekendHouseNo = weekendHouseNo;
        this.weekendApartmentNo = weekendApartmentNo;
        this.weekendPostalCode = weekendPostalCode;
        this.weekendCity = weekendCity;
        this.invoice = invoice;
        this.invoiceCompanyName = invoiceCompanyName;
        this.invoiceTaxNo = invoiceTaxNo;
        this.invoiceStreet = invoiceStreet;
        this.invoiceHouseNo = invoiceHouseNo;
        this.invoiceApartmentNo = invoiceApartmentNo;
        this.invoicePostalCode = invoicePostalCode;
        this.invoiceCity = invoiceCity;
        this.weekPreferredHoursTo = weekPreferredHoursTo;
        this.anotherHoursWeekend = anotherHoursWeekend;
        this.weekendPreferredHoursTo = weekendPreferredHoursTo;
        this.comment = comment;
    }

    public CustomerData(Long id, String firstName, String lastName, String buildingNumber, String apartmentNumber, String postalCode, String city, String email, String type, String firstAddressLine, String secondAddressLine, String registrationDate, Long groupId, String active, Long ordersCount) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.email = email;
        this.type = type;
        this.firstAddressLine = firstAddressLine;
        this.secondAddressLine = secondAddressLine;
        this.registrationDate = registrationDate;
        this.groupId = groupId;
        this.active = active;
        this.ordersCount = ordersCount;
    }


    public CustomerData(Long id, String firstName, String lastName, String phoneNumber, String street, String buildingNumber, String apartmentNumber, String postalCode, String city, String email, Long groupId, String active, String registrationDate, String registrationIp, Boolean isRegulationAccepted, Boolean isNewsletterAccepted, Boolean demanding, Boolean anotherWeekendAddress, String weekendStreet, String weekendHouseNo, String weekendApartmentNo, String weekendPostalCode, String weekendCity, Boolean invoice, String invoiceCompanyName, String invoiceTaxNo, String invoiceStreet, String invoiceHouseNo, String invoiceApartmentNo, String invoicePostalCode, String invoiceCity, LocalTime weekPreferredHoursTo, Boolean anotherHoursWeekend, LocalTime weekendPreferredHoursTo, String comment, String type, Long ordersCount, String customerFrom) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.email = email;
        this.groupId = groupId;
        this.active = active;
        this.registrationDate = registrationDate;
        this.registrationIp = registrationIp;
        this.isRegulationAccepted = isRegulationAccepted;
        this.isNewsletterAccepted = isNewsletterAccepted;
        this.demanding = demanding;
        this.anotherWeekendAddress = anotherWeekendAddress;
        this.weekendStreet = weekendStreet;
        this.weekendHouseNo = weekendHouseNo;
        this.weekendApartmentNo = weekendApartmentNo;
        this.weekendPostalCode = weekendPostalCode;
        this.weekendCity = weekendCity;
        this.invoice = invoice;
        this.invoiceCompanyName = invoiceCompanyName;
        this.invoiceTaxNo = invoiceTaxNo;
        this.invoiceStreet = invoiceStreet;
        this.invoiceHouseNo = invoiceHouseNo;
        this.invoiceApartmentNo = invoiceApartmentNo;
        this.invoicePostalCode = invoicePostalCode;
        this.invoiceCity = invoiceCity;
        this.weekPreferredHoursTo = weekPreferredHoursTo;
        this.anotherHoursWeekend = anotherHoursWeekend;
        this.weekendPreferredHoursTo = weekendPreferredHoursTo;
        this.comment = comment;
        this.type = type;
        this.ordersCount = ordersCount;
        this.customerFrom = customerFrom;
    }

    public CustomerData(CustomerData customer, Long defaultDriverId, Long cityId, Long weekendCityId, String customerFrom) {
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
        this.ordersCount = customer.getOrdersCount();
        this.defaultDriverId = defaultDriverId;
        this.cityId = cityId;
        this.weekendCityId = weekendCityId;
        this.customerFrom = customerFrom;
    }

    public CustomerData(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

    }

    public CustomerData(String firstName, String lastName, String street, String buildingNumber, String apartmentNumber, String postalCode, String city, String email, Long groupId, String active, String customerFrom, String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.email = email;
        this.groupId = groupId;
        this.active = active;
        this.customerFrom = customerFrom;
        this.type = type;
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

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }

    public String getInvoiceTaxNo() {
        return invoiceTaxNo;
    }

    public String getInvoiceCompanyName() {
        return invoiceCompanyName;
    }

    public Boolean getInvoice() {
        return invoice;
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

    public Long getDefaultDriverId() {
        return defaultDriverId;
    }

    public Boolean getDemanding() {
        return demanding;
    }

    public Long getCityId() {
        return cityId;
    }

    public String getFirstAddressLine() {
        return firstAddressLine;
    }

    public String getSecondAddressLine() {
        return secondAddressLine;
    }

    public String getRegistrationDate() {
        return registrationDate;
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

    public String getCustomerFrom() {
        return customerFrom;
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

    public String getComment() {
        return comment;
    }

    public Long getWeekendCityId() {
        return weekendCityId;
    }

    @Override
    public String toString() {
        return "CustomerData{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", street='" + street + '\'' +
                ", buildingNumber='" + buildingNumber + '\'' +
                ", apartmentNumber='" + apartmentNumber + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", groupId=" + groupId +
                ", active='" + active + '\'' +
                ", anotherWeekendAddress=" + anotherWeekendAddress +
                ", weekendStreet='" + weekendStreet + '\'' +
                ", weekendHouseNo='" + weekendHouseNo + '\'' +
                ", weekendApartmentNo='" + weekendApartmentNo + '\'' +
                ", weekendPostalCode='" + weekendPostalCode + '\'' +
                ", weekendCity='" + weekendCity + '\'' +
                ", invoice=" + invoice +
                ", invoiceCompanyName='" + invoiceCompanyName + '\'' +
                ", invoiceTaxNo='" + invoiceTaxNo + '\'' +
                ", invoiceStreet='" + invoiceStreet + '\'' +
                ", invoiceHouseNo='" + invoiceHouseNo + '\'' +
                ", invoiceApartmentNo='" + invoiceApartmentNo + '\'' +
                ", invoicePostalCode='" + invoicePostalCode + '\'' +
                ", invoiceCity='" + invoiceCity + '\'' +
                ", weekPreferredHoursTo=" + weekPreferredHoursTo +
                ", anotherHoursWeekend=" + anotherHoursWeekend +
                ", weekendPreferredHoursTo=" + weekendPreferredHoursTo +
                ", comment='" + comment + '\'' +
                ", registrationDate='" + registrationDate + '\'' +
                ", registrationIp='" + registrationIp + '\'' +
                ", isRegulationAccepted=" + isRegulationAccepted +
                ", isNewsletterAccepted=" + isNewsletterAccepted +
                ", demanding=" + demanding +
                ", type='" + type + '\'' +
                ", defaultDriverId=" + defaultDriverId +
                ", cityId=" + cityId +
                ", weekendCityId=" + weekendCityId +
                ", firstAddressLine='" + firstAddressLine + '\'' +
                ", secondAddressLine='" + secondAddressLine + '\'' +
                ", ordersCount=" + ordersCount +
                ", customerFrom='" + customerFrom + '\'' +
                '}';
    }
}
