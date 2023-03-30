package pl.com.mike.developer.domain;

public class ExtrasData {
    private Long id;

    public ExtrasData(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ExtrasData{" +
                "id=" + id +
                '}';
    }
}
