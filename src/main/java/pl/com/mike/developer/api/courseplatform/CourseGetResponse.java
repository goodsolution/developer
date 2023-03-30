package pl.com.mike.developer.api.courseplatform;

import pl.com.mike.developer.domain.courseplatform.CourseData;

public class CourseGetResponse {

    private Long id;
    private String title;
    private String description;
    private String imageName;
    private String price;
    private String language;
    private AuthorGetResponse author;
    private String visibilityStatus;
    private String code;
    private String type;
    private String saleStatus;

    public CourseGetResponse(CourseData data) {
        this.id = data.getId();
        this.title = data.getTitle();
        this.description = data.getDescription();
        this.imageName = data.getImageName();
        this.price = data.getPrice().toString();
        this.language = data.getLanguage();
        this.author = new AuthorGetResponse(data.getAuthor());
        this.visibilityStatus = data.getVisibilityStatus();
        this.code = data.getCode();
        this.type = data.getType();
        this.saleStatus = data.getSaleStatus();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageName() {
        return imageName;
    }

    public String getPrice() {
        return price;
    }

    public String getLanguage() {
        return language;
    }

    public AuthorGetResponse getAuthor() {
        return author;
    }

    public String getVisibilityStatus() {
        return visibilityStatus;
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public String getSaleStatus() {
        return saleStatus;
    }
}
