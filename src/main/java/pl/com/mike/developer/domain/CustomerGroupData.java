package pl.com.mike.developer.domain;

public class CustomerGroupData {

    private Long id;
    private String name;
    private Integer discount;
    private Integer status;

    public CustomerGroupData(Long id, String name, Integer discount, Integer status) {
        this.id = id;
        this.name = name;
        this.discount = discount;
        this.status = status;
    }

    public CustomerGroupData(String name, Integer discount, Integer status) {
        this.name = name;
        this.discount = discount;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getDiscount() {
        return discount;
    }

    public Integer getStatus() {
        return status;
    }
}
