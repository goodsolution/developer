package pl.com.mike.developer.api.courseplatform.external.payu;

public class CreateOrderRequestBuyer {
    private String email;
    private String language;

    public CreateOrderRequestBuyer() {
    }

    public CreateOrderRequestBuyer(String email, String language) {
        this.email = email;
        this.language = language;
    }

    public String getEmail() {
        return email;
    }

    public String getLanguage() {
        return language;
    }
}
