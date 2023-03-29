package pl.com.goodsolution.adviser.domain.adviser;

public class AdvicePurchasedCategoriesFilter {

    private final Long page;
    private final Long pageSize;
    private final String domainId;
    private final String applicationId;
    private final String domain;

    public AdvicePurchasedCategoriesFilter(Long page, Long pageSize) {
        this.page = page;
        this.pageSize = pageSize;
        this.domainId = null;
        this.applicationId = null;
        this.domain = null;
    }

    public AdvicePurchasedCategoriesFilter(Long page, Long pageSize, String domainId, String applicationId, String domain) {
        this.page = page;
        this.pageSize = pageSize;
        this.domainId = domainId;
        this.applicationId = applicationId;
        this.domain = domain;
    }

    public Long getPage() {
        return page;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public String getDomainId() {
        return domainId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getDomain() {
        return domain;
    }
}
