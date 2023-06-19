package pl.com.mike.developer.logic.developer;

public class InvestmentSearchFilter {

    private Long id;
    private String name;
    private String description;

    private String addressCountry;

    private String addressVoivodeship;

    private String addressCity;

    private String addressStreet;

    private Long developerId;

    public InvestmentSearchFilter(Long developerId) {
        this.developerId = developerId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(Long developerId) {
        this.developerId = developerId;
    }
}
