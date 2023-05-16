package pl.com.mike.developer;

public class InvestmentGetResponse {
    private Long id;
    private String name;
    private String description;
    private String addressCountry;
    private String addressVoivodeship;
    private String addressCity;
    private String addressStreet;
    private Long developerId;

    public InvestmentGetResponse(Long id, String name, String description, String addressCountry, String addressVoivodeship, String addressCity, String addressStreet, Long developerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.addressCountry = addressCountry;
        this.addressVoivodeship = addressVoivodeship;
        this.addressCity = addressCity;
        this.addressStreet = addressStreet;
        this.developerId = developerId;
    }

    public InvestmentGetResponse() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public String getAddressVoivodeship() {
        return addressVoivodeship;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public Long getDeveloperId() {
        return developerId;
    }

    public String getDescription() {
        return description;
    }
}
