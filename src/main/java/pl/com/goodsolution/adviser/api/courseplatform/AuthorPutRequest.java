package pl.com.goodsolution.adviser.api.courseplatform;

public class AuthorPutRequest {
    private Long id;
    private String firstName;
    private String lastName;

    public AuthorPutRequest() {
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
