package pl.com.goodsolution.adviser.domain.courseplatform;

public class CustomerGroupsFilter {
    private Long id;
    private Long page;
    private Long pageSize;
    private Boolean deleted;

    public CustomerGroupsFilter(Long id) {
        this.id = id;
    }

    public CustomerGroupsFilter(Long page, Long pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public CustomerGroupsFilter(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public Long getPage() {
        return page;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public Boolean getDeleted() {
        return deleted;
    }
}
