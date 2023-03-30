package pl.com.mike.developer.domain.itube;

public class ITubeFilter {
    private Long id;
    private String titlePl;
    private String titleEn;
    private String keywords;
    private String link;
    private Boolean create;
    private Boolean delete;
    private Long page;
    private Long pageSize;

    public ITubeFilter(Long id, String titlePl, String titleEn, String keywords, String link, Boolean create, Boolean delete) {
        this.id = id;
        this.titlePl = titlePl;
        this.titleEn = titleEn;
        this.keywords = keywords;
        this.link = link;
        this.create = create;
        this.delete = delete;
    }



    public ITubeFilter(Long id, String titlePl, String titleEn, String keywords, String link, Boolean create, Boolean delete, Long page, Long pageSize) {
        this.id = id;
        this.titlePl = titlePl;
        this.titleEn = titleEn;
        this.keywords = keywords;
        this.link = link;
        this.create = create;
        this.delete = delete;
        this.page = page;
        this.pageSize = pageSize;
    }

    public ITubeFilter(Long id) {
        this.id = id;
    }

    public ITubeFilter(String titlePl, String titleEn, String keywords, Boolean delete, Long page, Long pageSize) {
        this.titlePl = titlePl;
        this.titleEn = titleEn;
        this.keywords = keywords;
        this.delete = delete;
        this.page = page;
        this.pageSize = pageSize;
    }

    public Boolean getCreate() {
        return create;
    }

    public Boolean getDelete() {
        return delete;
    }

    public Long getId() {
        return id;
    }

    public String getTitlePl() {
        return titlePl;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getLink() {
        return link;
    }

    public Boolean isCreate() {
        return create;
    }

    public Boolean isDelete() {
        return delete;
    }

    public Long getPage() {
        return page;
    }

    public Long getPageSize() {
        return pageSize;
    }
}
