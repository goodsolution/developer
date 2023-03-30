package pl.com.mike.developer.domain.courseplatform;

public class MemesFilter {
    private Long id;
    private Long page;
    private Long pageSize;
    private String code;

    public MemesFilter(Long page, Long pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public MemesFilter(Long id) {
        this.id = id;
    }

    public MemesFilter(String code) {
        this.code = code;
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

    public String getCode() {
        return code;
    }
}
