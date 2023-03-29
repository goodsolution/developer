package pl.com.goodsolution.adviser.api.adviser;

public class ApplicationGetResponse {
    private Long id;
    private String applicationId;
    private String description;
    private String secretKey;

    public ApplicationGetResponse(Long id, String applicationId, String description) {
        this.id = id;
        this.applicationId = applicationId;
        this.description = description;
    }

    public ApplicationGetResponse(Long id, String applicationId, String description, String secretKey) {
        this.id = id;
        this.applicationId = applicationId;
        this.description = description;
        this.secretKey = secretKey;
    }

    public Long getId() {
        return id;
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
