package pl.com.mike.developer.domain;

import java.time.LocalDateTime;

public class PeopleData {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDateTime createDateTime;
    private LocalDateTime deleteDatetime;
    private LocalDateTime modifyDatetime;



    public PeopleData(Long id, String firstName, String lastName, String email, String phone, LocalDateTime createDateTime, LocalDateTime deleteDatetime, LocalDateTime modifyDatetime) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.createDateTime = createDateTime;
        this.deleteDatetime = deleteDatetime;
        this.modifyDatetime = modifyDatetime;
    }
    public PeopleData(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;

    }
    public PeopleData(PeopleData data, LocalDateTime deleteDatetime) {
        this.id = data.getId();
        this.firstName = data.getFirstName();
        this.lastName = data.getLastName();
        this.email = data.getEmail();
        this.phone = data.getPhone();
        this.createDateTime = data.getCreateDateTime();
        this.deleteDatetime = deleteDatetime;
    }

    public LocalDateTime getDeleteDatetime() {
        return deleteDatetime;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getContent() {
        return lastName;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDateTime getModifyDatetime() {
        return modifyDatetime;
    }
}
