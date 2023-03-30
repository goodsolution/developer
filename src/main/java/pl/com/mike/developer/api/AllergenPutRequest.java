package pl.com.mike.developer.api;

public class AllergenPutRequest {
    private String name;

    public AllergenPutRequest() {
    }

    public AllergenPutRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
