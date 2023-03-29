package pl.com.goodsolution.adviser.api.adviser;

public class ContextConfigGetResponse {
    private Long id;
    private String applicationId;
    private String context;
    private String name;
    private String type;
    private String value;

    public ContextConfigGetResponse(Long id, String applicationId, String context, String name, String type, String value) {
        this.id = id;
        this.applicationId = applicationId;
        this.context = context;
        this.name = name;
        this.type = type;
        this.value = value;
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
