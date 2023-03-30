package pl.com.mike.developer.api.courseplatform;

public class NotificationsPostRequest {
    private String title;
    private String content;
    private String link;
    private String language;

    public NotificationsPostRequest() {
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

    public String getLanguage() {
        return language;
    }
}
