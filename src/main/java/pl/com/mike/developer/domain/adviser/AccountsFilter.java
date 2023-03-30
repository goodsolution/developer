package pl.com.mike.developer.domain.adviser;

public class AccountsFilter {
    private Long page;
    private Long pageSize;
    private String name;

    public AccountsFilter(Long page, Long pageSize, String name) {
        this.page = page;
        this.pageSize = pageSize;
        this.name = name;
    }

    public Long getPage() {
        return page;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public String getName() {
        return name;
    }
}
