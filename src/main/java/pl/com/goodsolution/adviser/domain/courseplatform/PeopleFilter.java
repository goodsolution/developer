package pl.com.goodsolution.adviser.domain.courseplatform;

public class PeopleFilter {
    private Long id;
    private Long page;
    private Long pageSize;
    private Boolean deleted;

    public PeopleFilter(Long page, Long pageSize, Boolean deleted) {
        this.page = page;
        this.pageSize = pageSize;
        this.deleted = deleted;
    }

    public PeopleFilter(Long id) {
        this.id = id;
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
