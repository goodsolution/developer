package pl.com.mike.developer.elearning;

public class TaskDto {
    private String titlePl;
    private String titleEn;
    private String descriptionPl;
    private String descriptionEn;

    public TaskDto(String titlePl, String titleEn, String descriptionPl, String descriptionEn) {
        this.titlePl = titlePl;
        this.titleEn = titleEn;
        this.descriptionPl = descriptionPl;
        this.descriptionEn = descriptionEn;
    }

    public String getTitlePl() {
        return titlePl;
    }

    public void setTitlePl(String titlePl) {
        this.titlePl = titlePl;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getDescriptionPl() {
        return descriptionPl;
    }

    public void setDescriptionPl(String descriptionPl) {
        this.descriptionPl = descriptionPl;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }
}
