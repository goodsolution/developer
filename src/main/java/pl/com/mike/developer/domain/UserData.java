package pl.com.mike.developer.domain;

import pl.com.mike.developer.domain.adviser.AccountData;

public class UserData {
    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private String passwordHash;
    private AccountData account;
    private Long roleId;
    private Long accountId;


    public UserData(UserData user, AccountData account) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.login = user.getLogin();
        this.passwordHash = user.getPasswordHash();
        this.account = account;
        this.roleId = user.getRoleId();
        this.accountId = user.getAccountId();
    }

    public UserData(String login, String passwordHash, AccountData account, Long roleId) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.account = account;
        this.roleId = roleId;

    }

    public UserData(String login, String passwordHash, Long roleId) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.roleId = roleId;
    }

    public UserData(Long id, String firstName, String lastName, Long accountId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountId = accountId;
    }

    public UserData(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getId() {
        return id;
    }

    public AccountData getAccount() {
        return account;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Long getRoleId() {
        return roleId;
    }

    public Long getAccountId() {
        return accountId;
    }
}
