package pl.com.mike.developer.domain.courseplatform;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class MemeData {
    private Long id;
    private String title;
    private String fileName;
    private String description;
    private String keywords;
    private LocalDateTime creationDatetime;
    private String language;
    private String code;

    private MultipartFile file;

    public MemeData(String title, String description, String keywords, String language, MultipartFile file, String code) {
        this.title = title;
        this.description = description;
        this.keywords = keywords;
        this.language = language;
        this.file = file;
        this.code = code;
    }

    public MemeData(String title, String fileName, String description, String keywords, LocalDateTime creationDatetime, String language, String code) {
        this.title = title;
        this.fileName = fileName;
        this.description = description;
        this.keywords = keywords;
        this.creationDatetime = creationDatetime;
        this.language = language;
        this.code = code;
    }

    public MemeData(Long id, String title, String fileName, String description, String keywords, LocalDateTime creationDatetime, String language, String code) {
        this.id = id;
        this.title = title;
        this.fileName = fileName;
        this.description = description;
        this.keywords = keywords;
        this.creationDatetime = creationDatetime;
        this.language = language;
        this.code = code;
    }

    public MemeData(MemeData data, String fileName) {
        this.id = data.getId();
        this.title = data.getTitle();
        this.fileName = fileName;
        this.description = data.getDescription();
        this.keywords = data.getKeywords();
        this.creationDatetime = data.getCreationDatetime();
        this.language = data.getLanguage();
        this.code = data.getCode();
    }

    public MemeData(MemeData data, MemeDataToChange dataToChange) {
        this.id = data.getId();
        this.title = dataToChange.getTitle();
        this.fileName = data.getFileName();
        this.description = dataToChange.getDescription();
        this.keywords = dataToChange.getKeywords();
        this.creationDatetime = data.getCreationDatetime();
        this.language = dataToChange.getLanguage();
        this.code = dataToChange.getCode();
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

    public LocalDateTime getCreationDatetime() {
        return creationDatetime;
    }

    public String getLanguage() {
        return language;
    }

    public MultipartFile getFile() {
        return file;
    }

    public String getCode() {
        return code;
    }
}
