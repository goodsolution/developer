package pl.com.mike.developer.domain.adviser;

public class ContextVariableData {
    private final Long id;
    private final String applicationId;
    private final String context;
    private final String name;
    private final AdviseDataType type;
    private final String value;
    private final String contextId;
    private final Long customerId;

    public ContextVariableData(String applicationId, String context, String name, AdviseDataType type, String value, String contextId, Long customerId) {
        this.applicationId = applicationId;
        this.context = context;
        this.name = name;
        this.type = type;
        this.value = value;
        this.contextId = contextId;
        this.customerId = customerId;
        this.id = null;
    }

    public ContextVariableData(Long id, String applicationId, String context, String name, AdviseDataType type, String value, String contextId, Long customerId) {
        this.id = id;
        this.applicationId = applicationId;
        this.context = context;
        this.name = name;
        this.type = type;
        this.value = value;
        this.contextId = contextId;
        this.customerId = customerId;
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

    public AdviseDataType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String getContextId() {
        return contextId;
    }

    public Long getCustomerId() {
        return customerId;
    }


}
