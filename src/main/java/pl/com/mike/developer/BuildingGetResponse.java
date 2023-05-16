package pl.com.mike.developer;

public class BuildingGetResponse {

    private Long id;
    private String name;
    private String addressCountry;
    private String addressVoivodeship;
    private String addressCity;
    private String addressStreet;
    private String addressBuildingNumber;
    private String addressPostalCode;
    private Long investmentId;

    public BuildingGetResponse(Long id, String name, String addressCountry, String addressVoivodeship, String addressCity, String addressStreet, String addressBuildingNumber, String addressPostalCode, Long investmentId) {
        this.id = id;
        this.name = name;
        this.addressCountry = addressCountry;
        this.addressVoivodeship = addressVoivodeship;
        this.addressCity = addressCity;
        this.addressStreet = addressStreet;
        this.addressBuildingNumber = addressBuildingNumber;
        this.addressPostalCode = addressPostalCode;
        this.investmentId = investmentId;
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

    public String getAddressVoivodeship() {
        return addressVoivodeship;
    }

    public void setAddressVoivodeship(String addressVoivodeship) {
        this.addressVoivodeship = addressVoivodeship;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
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

    public String getAddressPostalCode() {
        return addressPostalCode;
    }

    public void setAddressPostalCode(String addressPostalCode) {
        this.addressPostalCode = addressPostalCode;
    }

    public Long getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(Long investmentId) {
        this.investmentId = investmentId;
    }
}
