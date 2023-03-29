package pl.com.goodsolution.adviser.api;

public class AllergenPostRequest {
    private String name;

    public AllergenPostRequest() {
    }

    public AllergenPostRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
