package pl.com.mike.developer.api.courseplatform.external.payu;

public class AuthorizationResponse {
    private String access_token;
    private String token_type;
    private Long expires_in;
    private String grant_type;

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public String getGrant_type() {
        return grant_type;
    }
}
