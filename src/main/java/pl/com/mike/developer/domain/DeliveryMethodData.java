package pl.com.mike.developer.domain;

public class DeliveryMethodData {
    private Long id;
    private String name;


    public DeliveryMethodData(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
