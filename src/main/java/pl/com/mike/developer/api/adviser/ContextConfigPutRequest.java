package pl.com.mike.developer.api.adviser;

public class ContextConfigPutRequest {
    private Long id;
    private String applicationId;
    private String context;
    private String name;
    private String type;
    private String value;

    public ContextConfigPutRequest() {
    }

    public Long getId() {
        return id;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getContext() {
        return context;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
