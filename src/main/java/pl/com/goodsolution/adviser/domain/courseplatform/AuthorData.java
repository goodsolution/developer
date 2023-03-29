package pl.com.goodsolution.adviser.domain.courseplatform;

import java.io.Serializable;

public class AuthorData implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;

    public AuthorData(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public AuthorData(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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
