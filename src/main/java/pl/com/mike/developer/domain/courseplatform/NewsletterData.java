package pl.com.mike.developer.domain.courseplatform;

import java.util.HashMap;
import java.util.Map;

public class NewsletterData {
    private Map<String, NewsletterContentData> content = new HashMap<>();

    public NewsletterData() {
    }

    public NewsletterData(Map<String, NewsletterContentData> content) {
        this.content = content;
    }

    public Map<String, NewsletterContentData> getContent() {
        return content;
    }
}
