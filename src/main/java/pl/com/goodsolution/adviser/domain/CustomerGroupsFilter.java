package pl.com.goodsolution.adviser.domain;

public class CustomerGroupsFilter {

    private String sortBy;
    private Long page;
    private Long pageSize;
    private String orderBy;

    public CustomerGroupsFilter(String sortBy, Long page, Long pageSize, String orderBy) {
        this.sortBy = sortBy;
        this.page = page;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
    }

    public String getSortBy() {
        return sortBy;
    }

    public Long getPage() {
        return page;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }
}
