package pl.com.mike.developer.api.courseplatform;

import pl.com.mike.developer.domain.courseplatform.MemeData;

public class MemeGetResponse {
    private Long id;
    private String title;
    private String fileName;
    private String description;
    private String keywords;
    private String creationDatetime;
    private String language;
    private String code;

    public MemeGetResponse(MemeData data) {
        this.id = data.getId();
        this.title = data.getTitle();
        this.fileName = data.getFileName();
        this.description = data.getDescription();
        this.keywords = data.getKeywords();
        this.creationDatetime = data.getCreationDatetime().toString();
        this.language = data.getLanguage();
        this.code = data.getCode();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getFileName() {
        return fileName;
    }

    public String getDescription() {
        return description;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getCreationDatetime() {
        return creationDatetime;
    }

    public String getLanguage() {
        return language;
    }

    public String getCode() {
        return code;
    }
}
