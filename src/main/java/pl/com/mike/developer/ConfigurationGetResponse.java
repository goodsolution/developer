package pl.com.mike.developer;

public class ConfigurationGetResponse {
    private final String logoUrl;

    public ConfigurationGetResponse(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }
}
