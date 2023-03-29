package pl.com.goodsolution.adviser.api.courseplatform;


public class NotificationGetResponse {
    private String createDatetime;
    private String title;
    private String content;
    private String link;
    private Boolean seen;

    public NotificationGetResponse(String createDatetime, String title, String content, String link, Boolean seen) {
        this.createDatetime = createDatetime;
        this.title = title;
        this.content = content;
        this.link = link;
        this.seen = seen;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getLink() {
        return link;
    }

    public Boolean getSeen() {
        return seen;
    }
}
