package pl.com.goodsolution.adviser.domain.itube;

import java.time.LocalDateTime;

public class ITubeGetResponse {
    private Long id;
    private String title;
    private String keywords;
    private String link;
    private String createDateTime;
    private String deleteDatetime;

    public ITubeGetResponse() {
    }

    public ITubeGetResponse(Long id, String title, String keywords, String link, String createDateTime) {
        this.id = id;
        this.title = title;
        this.keywords = keywords;
        this.link = link;
        this.createDateTime = createDateTime;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
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
