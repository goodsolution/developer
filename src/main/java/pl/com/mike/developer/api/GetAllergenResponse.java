package pl.com.mike.developer.api;

public class GetAllergenResponse {
    private Long id;
    private Long no;
    private String name;

    public GetAllergenResponse(Long id, Long no, String name) {
        this.id = id;
        this.no = no;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Long getNo() {
        return no;
    }

    public String getName() {
        return name;
    }
}
