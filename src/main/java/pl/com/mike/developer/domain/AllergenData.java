package pl.com.mike.developer.domain;

public class AllergenData {
    private Long id;
    private Long no;
    private String name;

    public AllergenData(Long id, Long no, String name) {
        this.id = id;
        this.no = no;
        this.name = name;
    }

    public AllergenData(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public AllergenData(String name) {
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
