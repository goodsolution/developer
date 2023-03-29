package pl.com.goodsolution.adviser.api.courseplatform;

import java.math.BigDecimal;

public class CoursePostRequest {
    private String title;
    private Long authorId;
    private BigDecimal price;
    private String language;
    private String description;
    private String code;
    private String type;

    public CoursePostRequest() {
    }

    public String getTitle() {
        return title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getLanguage() {
        return language;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }
}
