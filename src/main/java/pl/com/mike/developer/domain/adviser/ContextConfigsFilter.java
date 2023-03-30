package pl.com.mike.developer.domain.adviser;

public class ContextConfigsFilter {

    private final Long page;
    private final Long pageSize;
    private final String applicationId;
    private final String name;
    private final String context;
    private final String type;

    public ContextConfigsFilter(Long page, Long pageSize, String applicationId, String name, String context, String type) {
        this.page = page;
        this.pageSize = pageSize;
        this.applicationId = applicationId;
        this.name = name;
        this.context = context;
        this.type = type;
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
}
