package pl.com.mike.developer.api.adviser;

public class ApplicationPutRequest {
    private String applicationId;
    private String description;
    private String secretKey;

    public ApplicationPutRequest() {
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getDescription() {
        return description;
    }

    public String getSecretKey() {
        return secretKey;
    }
}
