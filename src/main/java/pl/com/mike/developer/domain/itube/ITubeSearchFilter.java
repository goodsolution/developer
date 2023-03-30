package pl.com.mike.developer.domain.itube;

public class ITubeSearchFilter {

    private Long id;
    private String phrase;
    private Long page;
    private Long pageSize;

    private Boolean delete;

    public ITubeSearchFilter(String phrase, Long page, Long pageSize, Boolean delete) {
        this.phrase = phrase;
        this.page = page;
        this.pageSize = pageSize;
        this.delete = delete;
    }

    public ITubeSearchFilter(Long page, Long pageSize, Boolean delete) {
        this.page = page;
        this.pageSize = pageSize;
        this.delete = delete;
    }

    public ITubeSearchFilter(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Boolean getDelete() {
        return delete;
    }

    public String getPhrase() {
        return phrase;
    }

    public Long getPage() {
        return page;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public Boolean isDelete() {
        return delete;
    }
}
