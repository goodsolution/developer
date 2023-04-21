package pl.com.mike.developer.domain.itube;

public class ITubeGetResponseAdmin {
    private Long id;
    private String titlePl;
    private String titleEn;
    private String keywords;
    private String link;
    private String createDateTime;
    private String deleteDatetime;

    public ITubeGetResponseAdmin() {
    }

    public ITubeGetResponseAdmin(Long id, String titlePl, String titleEn, String keywords, String link, String createDateTime) {
        this.id = id;
        this.titlePl = titlePl;
        this.titleEn = titleEn;
        this.keywords = keywords;
        this.link = link;
        this.createDateTime = createDateTime;
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

    public String getCreateDateTime() {
        return createDateTime;
    }

    public String getDeleteDatetime() {
        return deleteDatetime;
    }
}
