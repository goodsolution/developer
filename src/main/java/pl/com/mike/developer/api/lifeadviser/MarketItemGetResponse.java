package pl.com.mike.developer.api.lifeadviser;

public class MarketItemGetResponse {

    private Long id;

    private String name;
    private String tags;
    private String description;
    private String pricePerMonth;
    private String pricePerQuarter;
    private String pricePerHalfYear;
    private String pricePerYear;

    public MarketItemGetResponse(Long id, String name, String tags, String description, String pricePerMonth, String pricePerQuarter, String pricePerHalfYear, String pricePerYear) {
        this.id = id;
        this.name = name;
        this.tags = tags;
        this.description = description;
        this.pricePerMonth = pricePerMonth;
        this.pricePerQuarter = pricePerQuarter;
        this.pricePerHalfYear = pricePerHalfYear;
        this.pricePerYear = pricePerYear;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTags() {
        return tags;
    }

    public String getDescription() {
        return description;
    }

    public String getPricePerMonth() {
        return pricePerMonth;
    }

    public String getPricePerQuarter() {
        return pricePerQuarter;
    }

    public String getPricePerHalfYear() {
        return pricePerHalfYear;
    }

    public String getPricePerYear() {
        return pricePerYear;
    }
}
