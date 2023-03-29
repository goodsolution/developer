package pl.com.goodsolution.adviser.domain.courseplatform;

import pl.com.goodsolution.adviser.logic.courseplatform.CourseSaleStatus;
import pl.com.goodsolution.adviser.logic.courseplatform.CourseVisibilityStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CourseData implements Serializable {

    private Long id;
    private String title;
    private String description;
    private String imageName;
    private BigDecimal price;
    private String language;
    private AuthorData author;
    private String visibilityStatus;
    private LocalDateTime deleteDatetime;
    private String code;
    private String type;
    private String saleStatus;

    private Long authorId;

    public CourseData(Long id, String title, String description, String imageName, BigDecimal price, String language, AuthorData author, String visibilityStatus, LocalDateTime deleteDatetime, String code, String type, String saleStatus) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageName = imageName;
        this.price = price;
        this.language = language;
        this.author = author;
        this.visibilityStatus = visibilityStatus;
        this.deleteDatetime = deleteDatetime;
        this.code = code;
        this.type = type;
        this.saleStatus = saleStatus;
    }

    public CourseData(String title, String description, String imageName, BigDecimal price, String language, AuthorData author, CourseVisibilityStatus visibilityStatus, String code, String type, CourseSaleStatus saleStatus) {
        this.title = title;
        this.description = description;
        this.imageName = imageName;
        this.price = price;
        this.language = language;
        this.author = author;
        this.visibilityStatus = visibilityStatus.getValue();
        this.code = code;
        this.type = type;
        this.saleStatus = saleStatus.getCode();
    }


    public CourseData(CourseData data, String imageName) {
        this.id = data.getId();
        this.title = data.getTitle();
        this.description = data.getDescription();
        this.imageName = imageName;
        this.price = data.getPrice();
        this.language = data.getLanguage();
        this.author = data.getAuthor();
        this.visibilityStatus = data.getVisibilityStatus();
        this.deleteDatetime = data.getDeleteDatetime();
        this.code = data.getCode();
        this.type = data.getType();
        this.saleStatus = data.getSaleStatus();
    }

    public CourseData(CourseData data, LocalDateTime deleteDatetime) {
        this.id = data.getId();
        this.title = data.getTitle();
        this.description = data.getDescription();
        this.imageName = data.getImageName();
        this.price = data.getPrice();
        this.language = data.getLanguage();
        this.author = data.getAuthor();
        this.visibilityStatus = data.getVisibilityStatus();
        this.deleteDatetime = deleteDatetime;
        this.code = data.getCode();
        this.type = data.getType();
        this.saleStatus = data.getSaleStatus();
    }

    public CourseData(String title, String description, BigDecimal price, String language, Long authorId, String code, String type) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.language = language;
        this.authorId = authorId;
        this.code = code;
        this.type = type;
    }

    public CourseData(Long id, String title, String description, BigDecimal price, String language, String visibilityStatus, Long authorId, String code, String saleStatus) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.language = language;
        this.visibilityStatus = visibilityStatus;
        this.authorId = authorId;
        this.code = code;
        this.saleStatus = saleStatus;
    }

    public CourseData(CourseData data, String title, CourseVisibilityStatus visibilityStatus, String code) {
        this.id = data.getId();
        this.title = title;
        this.description = data.getDescription();
        this.imageName = data.getImageName();
        this.price = data.getPrice();
        this.language = data.getLanguage();
        this.author = data.getAuthor();
        this.visibilityStatus = visibilityStatus.getValue();
        this.deleteDatetime = data.getDeleteDatetime();
        this.code = code;
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

    public BigDecimal getPrice() {
        return price;
    }

    public String getLanguage() {
        return language;
    }

    public AuthorData getAuthor() {
        return author;
    }

    public String getVisibilityStatus() {
        return visibilityStatus;
    }

    public LocalDateTime getDeleteDatetime() {
        return deleteDatetime;
    }

    public String getCode() {
        return code;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getType() {
        return type;
    }

    public String getSaleStatus() {
        return saleStatus;
    }
}
