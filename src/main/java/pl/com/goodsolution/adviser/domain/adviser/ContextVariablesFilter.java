package pl.com.goodsolution.adviser.domain.adviser;


public class ContextVariablesFilter {

    private final Long page;
    private final Long pageSize;
    private final String applicationId;
    private final String name;
    private final String context;
    private final String type;
    private final String contextId;
    private final Long customerId;

    public ContextVariablesFilter(Long page, Long pageSize, String applicationId, String name, String context, String type, String contextId, Long customerId) {
        this.page = page;
        this.pageSize = pageSize;
        this.applicationId = applicationId;
        this.name = name;
        this.context = context;
        this.type = type;
        this.contextId = contextId;
        this.customerId = customerId;
    }

    public Long getPage() {
        return page;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getName() {
        return name;
    }

    public String getContext() {
        return context;
    }

    public String getType() {
        return type;
    }

    public String getContextId() {
        return contextId;
    }

    public Long getCustomerId() {
        return customerId;
    }
}
