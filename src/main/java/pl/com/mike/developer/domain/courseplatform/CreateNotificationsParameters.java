package pl.com.mike.developer.domain.courseplatform;

import pl.com.mike.developer.api.courseplatform.NotificationsPostRequest;
import pl.com.mike.developer.logic.courseplatform.Language;

public class CreateNotificationsParameters {
    private String title;
    private String content;
    private String link;
    private Language language;

    public CreateNotificationsParameters(NotificationsPostRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.link = request.getLink();
        this.language = Language.from(request.getLanguage());
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

    public Language getLanguage() {
        return language;
    }
}
