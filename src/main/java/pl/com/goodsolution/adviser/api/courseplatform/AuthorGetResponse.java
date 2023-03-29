package pl.com.goodsolution.adviser.api.courseplatform;

import pl.com.goodsolution.adviser.domain.courseplatform.AuthorData;

public class AuthorGetResponse {
    private Long id;
    private String firstName;
    private String lastName;

    public AuthorGetResponse(AuthorData data) {
        this.id = data.getId();
        this.firstName = data.getFirstName();
        this.lastName = data.getLastName();
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
