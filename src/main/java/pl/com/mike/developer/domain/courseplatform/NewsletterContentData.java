package pl.com.mike.developer.domain.courseplatform;

public class NewsletterContentData {

    private String html;
    private String title;

    public NewsletterContentData() {
    }

    public NewsletterContentData(String html, String title) {
        this.html = html;
        this.title = title;
    }

    public String getHtml() {
        return html;
    }

    public String getTitle() {
        return title;
    }
}
