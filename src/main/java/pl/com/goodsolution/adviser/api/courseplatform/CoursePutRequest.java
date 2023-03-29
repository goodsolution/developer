package pl.com.goodsolution.adviser.api.courseplatform;

import java.math.BigDecimal;

public class CoursePutRequest {
    private String title;
    private BigDecimal price;
    private String language;
    private String description;
    private Long authorId;
    private String visibilityStatus;
    private String code;
    private String saleStatus;

    public CoursePutRequest() {
    }

    public String getTitle() {
        return title;
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

    public Long getAuthorId() {
        return authorId;
    }

    public String getVisibilityStatus() {
        return visibilityStatus;
    }

    public String getCode() {
        return code;
    }

    public String getSaleStatus() {
        return saleStatus;
    }
}
