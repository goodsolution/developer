package pl.com.mike.developer.auth;

import javax.persistence.*;

@Entity
@Table(name="users")
class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String login;

    @Column(name = "password")
    private String password;

    Long getId() {
        return id;
    }

    String getLogin() {
        return login;
    }

    String getPassword() {
        return password;
    }
}
