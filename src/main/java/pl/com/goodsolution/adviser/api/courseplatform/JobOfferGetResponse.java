package pl.com.goodsolution.adviser.api.courseplatform;

import pl.com.goodsolution.adviser.domain.JobOfferData;

public class JobOfferGetResponse {
    private Long id;
    private String title;
    private String content;
    private String createDateTime;

    public JobOfferGetResponse() {
    }

    public JobOfferGetResponse(JobOfferData jobOfferData) {
        this.id = jobOfferData.getId();
        this.title = jobOfferData.getTitle();
        this.content = jobOfferData.getContent();
        this.createDateTime = jobOfferData.getCreateDateTime().toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }
}