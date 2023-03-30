package pl.com.mike.developer.domain.adviser;

public class AdvicePurchasedCategoryData {
    private Long id;
    private String name;
    private String description;
    private Long categoryId;

    public AdvicePurchasedCategoryData(Long id, String name, String description, Long categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

}
