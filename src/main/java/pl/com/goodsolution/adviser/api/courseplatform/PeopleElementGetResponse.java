package pl.com.goodsolution.adviser.api.courseplatform;

import pl.com.goodsolution.adviser.domain.PeopleData;

public class PeopleElementGetResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;


    public PeopleElementGetResponse() {
    }

    public PeopleElementGetResponse(PeopleData people) {
        this.id = people.getId();
        this.firstName = people.getFirstName();
        this.lastName = people.getLastName();
        this.email = people.getEmail();
        this.phone = people.getPhone();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}