package pl.com.mike.developer.domain;

public class DiscountData {
    private Long id;
    private String code;
    private String value;
    private String discountType;
    private String type;

    public DiscountData(Long id, String code) {
        this.id = id;
        this.code = code;
    }

    public DiscountData(Long id, String code, String type) {
        this.id = id;
        this.code = code;
        this.type = type;
    }

    public DiscountData(Long id, String code, String value, String discountType, String type) {
        this.id = id;
        this.code = code;
        this.value = value;
        this.discountType = discountType;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public String getDiscountType() {
        return discountType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
