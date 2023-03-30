package pl.com.mike.developer.domain.itube;

import java.time.LocalDateTime;

public class ITubeData {
    private Long id;
    private String titlePl;
    private String titleEn;
    private String keywords;
    private String link;
    private LocalDateTime createDateTime;
    private LocalDateTime deleteDatetime;

    public ITubeData(Long id, String titlePl, String titleEn, String keywords, String link, LocalDateTime createDateTime, LocalDateTime deleteDatetime) {
        this.id = id;
        this.titlePl = titlePl;
        this.titleEn = titleEn;
        this.keywords = keywords;
        this.link = link;
        this.createDateTime = createDateTime;
        this.deleteDatetime = deleteDatetime;
    }

    public ITubeData(String titlePl, String titleEn, String keywords, String link, LocalDateTime createDateTime, LocalDateTime deleteDatetime) {
        this.titlePl = titlePl;
        this.titleEn = titleEn;
        this.keywords = keywords;
        this.link = link;
        this.createDateTime = createDateTime;
        this.deleteDatetime = deleteDatetime;
    }

    public ITubeData(String titlePl, String titleEn, String keywords, String link) {
        this.titlePl = titlePl;
        this.titleEn = titleEn;
        this.keywords = keywords;
        this.link = link;
    }

    public ITubeData(String link) {
        this.link = link;
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

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public LocalDateTime getDeleteDatetime() {
        return deleteDatetime;
    }
}
