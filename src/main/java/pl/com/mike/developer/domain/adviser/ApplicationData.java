package pl.com.mike.developer.domain.adviser;

public class ApplicationData {
    private Long id;
    private String applicationId;
    private String description;
    private String secretKey;

    public ApplicationData(Long id, String applicationId, String description, String secretKey) {
        this.id = id;
        this.applicationId = applicationId;
        this.description = description;
        this.secretKey = secretKey;
    }

    public ApplicationData(String applicationId, String description, String secretKey) {
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
