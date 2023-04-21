package pl.com.mike.developer.auth;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
