package pl.com.goodsolution.adviser.auth;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
