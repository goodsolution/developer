package pl.com.goodsolution.adviser.domain;

public class ProductTypeData {
    private Long id;
    private Long no;
    private String type;


    public ProductTypeData(Long id, Long no, String type) {
        this.id = id;
        this.no = no;
        this.type = type;
    }

    public ProductTypeData(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    public ProductTypeData(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }


    public String getType() {
        return type;
    }

    public Long getNo() {
        return no;
    }
}



