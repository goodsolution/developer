package pl.com.mike.developer.domain;

public class OrderInvoiceData {
    private boolean invoiceWantedStatus;
    private String companyName;
    private String nip;
    private String street;
    private String buildingNumber;
    private String apartmentNumber;
    private String postalCode;
    private Long cityId;
    private String cityName;
    private boolean invoiceIssuedStatus;
    private String invoiceWantedString;
    private String type;
    private String firstName;
    private String lastName;

    public OrderInvoiceData(boolean invoiceWantedStatus, String companyName, String nip, String street, String buildingNumber, String apartmentNumber, String postalCode, Long cityId, String cityName, boolean invoiceIssuedStatus, String invoiceWantedString, String type, String firstName, String lastName) {
        this.invoiceWantedStatus = invoiceWantedStatus;
        this.companyName = companyName;
        this.nip = nip;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
        this.postalCode = postalCode;
        this.cityId = cityId;
        this.cityName = cityName;
        this.invoiceIssuedStatus = invoiceIssuedStatus;
        this.invoiceWantedString = invoiceWantedString;
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public OrderInvoiceData(boolean invoiceWantedStatus, String companyName, String nip, String street, String buildingNumber, String apartmentNumber, String postalCode, Long cityId, String cityName, boolean invoiceIssuedStatus, String invoiceWantedString) {
        this.invoiceWantedStatus = invoiceWantedStatus;
        this.companyName = companyName;
        this.nip = nip;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
        this.postalCode = postalCode;
        this.cityId = cityId;
        this.cityName = cityName;
        this.invoiceIssuedStatus = invoiceIssuedStatus;
        this.invoiceWantedString = invoiceWantedString;
    }

    public boolean getInvoiceWantedStatus() {
        return invoiceWantedStatus;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getNip() {
        return nip;
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

    public boolean isInvoiceWantedStatus() {
        return invoiceWantedStatus;
    }

    public boolean isInvoiceIssuedStatus() {
        return invoiceIssuedStatus;
    }

    public String getInvoiceWantedString() {
        return invoiceWantedString;
    }

    public String getType() {
        return type;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
