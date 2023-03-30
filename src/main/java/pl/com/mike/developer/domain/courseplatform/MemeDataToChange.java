package pl.com.mike.developer.domain.courseplatform;

public class MemeDataToChange {
    private String title;
    private String language;
    private String description;
    private String keywords;
    private String code;

    public MemeDataToChange(String title, String language, String description, String keywords, String code) {
        this.title = title;
        this.language = language;
        this.description = description;
        this.keywords = keywords;
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public String getLanguage() {
        return language;
    }

    public String getDescription() {
        return description;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getCode() {
        return code;
    }
}
