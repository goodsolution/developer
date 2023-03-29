package pl.com.goodsolution.adviser.api.lifeadviser;

public class MyCategoriesGetResponse {

    private Long id;
    private String name;
    private Long categoryId;

    public MyCategoriesGetResponse(Long id, String name, Long categoryId) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getCategoryId() {
        return categoryId;
    }
}
