package pl.com.mike.developer;

public class DeveloperGetResponse {

    private Long id;
    private String name;

    private String addressCountry;

    private String addressStreet;

    private String addressBuildingNumber;

    private String addressFlatNumber;

    private String addressPostalCode;

    private String telephoneNumber;

    private String faxNumber;
    private String email;

    private String taxIdentificationNumber;

    private Integer cityId;

    private Integer voivodeshipId;

    private String logoUrl;

    public DeveloperGetResponse(Long id, String name, String addressCountry, String addressStreet, String addressBuildingNumber, String addressFlatNumber, String addressPostalCode, String telephoneNumber, String faxNumber, String email, String taxIdentificationNumber, Integer cityId, Integer voivodeshipId, String logoUrl) {
        this.id = id;
        this.name = name;
        this.addressCountry = addressCountry;
        this.addressStreet = addressStreet;
        this.addressBuildingNumber = addressBuildingNumber;
        this.addressFlatNumber = addressFlatNumber;
        this.addressPostalCode = addressPostalCode;
        this.telephoneNumber = telephoneNumber;
        this.faxNumber = faxNumber;
        this.email = email;
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.cityId = cityId;
        this.voivodeshipId = voivodeshipId;
        this.logoUrl = logoUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressBuildingNumber() {
        return addressBuildingNumber;
    }

    public void setAddressBuildingNumber(String addressBuildingNumber) {
        this.addressBuildingNumber = addressBuildingNumber;
    }

    public String getAddressFlatNumber() {
        return addressFlatNumber;
    }

    public void setAddressFlatNumber(String addressFlatNumber) {
        this.addressFlatNumber = addressFlatNumber;
    }

    public String getAddressPostalCode() {
        return addressPostalCode;
    }

    public void setAddressPostalCode(String addressPostalCode) {
        this.addressPostalCode = addressPostalCode;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getVoivodeshipId() {
        return voivodeshipId;
    }

    public void setVoivodeshipId(Integer voivodeshipId) {
        this.voivodeshipId = voivodeshipId;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
