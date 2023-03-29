package pl.com.goodsolution.adviser.domain.adviser;

public class AdviceCategoriesFilter {

    private Long page;
    private Long pageSize;
    private String tagsAsSubstring;
    private String searchAsSubstring;
    private String applicationId;
    private String context;
    private Long id;

    public AdviceCategoriesFilter(Long id) {
        this.id = id;
    }

    public AdviceCategoriesFilter(Long page, Long pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public AdviceCategoriesFilter(Long page, Long pageSize, String tagsAsSubstring, String searchAsSubstring, String applicationId, String context) {
        this.page = page;
        this.pageSize = pageSize;
        this.tagsAsSubstring = tagsAsSubstring;
        this.searchAsSubstring = searchAsSubstring;
        this.applicationId = applicationId;
        this.context = context;
    }

    public Long getPage() {
        return page;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public String getTagsAsSubstring() {
        return tagsAsSubstring;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getContext() {
        return context;
    }

    public Long getId() {
        return id;
    }

    public String getSearchAsSubstring() {
        return searchAsSubstring;
    }
}
