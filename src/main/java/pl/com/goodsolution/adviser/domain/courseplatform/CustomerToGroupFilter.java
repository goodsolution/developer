package pl.com.goodsolution.adviser.domain.courseplatform;

public class CustomerToGroupFilter {
    private Long id;
    private Long customerId;
    private Long page;
    private Long pageSize;

    public CustomerToGroupFilter(Long id, Long customerId, Long page, Long pageSize) {
        this.id = id;
        this.customerId = customerId;
        this.page = page;
        this.pageSize = pageSize;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getPage() {
        return page;
    }

    public Long getPageSize() {
        return pageSize;
    }
}
