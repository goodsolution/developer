package pl.com.goodsolution.adviser.api;

public class GetProductTypeResponse {
    private Long id;
    private Long no;
    private String type;

    public GetProductTypeResponse(Long id, Long no, String type) {
        this.id = id;
        this.no = no;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public Long getNo() {
        return no;
    }

    public String getType() {
        return type;
    }
}
