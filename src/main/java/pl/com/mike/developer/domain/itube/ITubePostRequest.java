package pl.com.mike.developer.domain.itube;

import java.time.LocalDateTime;

public class ITubePostRequest {
    private Long id;
    private String titlePl;
    private String titleEn;
    private String keywords;
    private String link;
    private LocalDateTime createDateTime;
    private LocalDateTime deleteDatetime;

    public ITubePostRequest() {
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
