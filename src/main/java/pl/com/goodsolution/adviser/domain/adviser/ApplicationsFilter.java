package pl.com.goodsolution.adviser.domain.adviser;

public class ApplicationsFilter {
    private Long page;
    private Long pageSize;
    private String applicationIdAsSubstring;
    private String applicationId;
    private String secret;

    public ApplicationsFilter(Long page, Long pageSize, String applicationIdAsSubstring, String applicationId, String secret) {
        this.page = page;
        this.pageSize = pageSize;
        this.applicationIdAsSubstring = applicationIdAsSubstring;
        this.applicationId = applicationId;
        this.secret = secret;
    }

    public Long getPage() {
        return page;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public String getApplicationIdAsSubstring() {
        return applicationIdAsSubstring;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getSecret() {
        return secret;
    }
}
