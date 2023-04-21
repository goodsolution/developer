package pl.com.mike.developer.domain;

import java.time.LocalDateTime;

public class JobOfferData {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDateTime;
    private LocalDateTime deleteDatetime;

    public JobOfferData(Long id, String title, String content, LocalDateTime createDateTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDateTime = createDateTime;
    }

    public JobOfferData(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public JobOfferData(JobOfferData data, LocalDateTime deleteDatetime) {
        this.id = data.getId();
        this.title = data.getTitle();
        this.content = data.getContent();
        this.createDateTime = data.getCreateDateTime();
        this.deleteDatetime = deleteDatetime;
    }

    public JobOfferData(Long id, String title, String content, LocalDateTime createDateTime, LocalDateTime deleteDatetime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDateTime = createDateTime;
        this.deleteDatetime = deleteDatetime;
    }

    public LocalDateTime getDeleteDatetime() {
        return deleteDatetime;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

}
