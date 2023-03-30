package pl.com.mike.developer.api;

public class CustomerGroupPostRequest {
    private String name;
    private Integer discount;
    private Integer status;

    public CustomerGroupPostRequest() {
    }

    public CustomerGroupPostRequest(String name, Integer discount, Integer status) {
        this.name = name;
        this.discount = discount;
        this.status = status;
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

    @Override
    public String toString() {
        return "CustomerGroupPostRequest{" +
                "name='" + name + '\'' +
                ", discount=" + discount +
                ", status=" + status +
                '}';
    }
}
