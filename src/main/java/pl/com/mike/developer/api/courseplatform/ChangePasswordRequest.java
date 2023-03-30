package pl.com.mike.developer.api.courseplatform;

public class ChangePasswordRequest {

    private String actualPasswordHash;
    private String newPasswordHash;

    public ChangePasswordRequest() {
    }

    public String getActualPasswordHash() {
        return actualPasswordHash;
    }

    public String getNewPasswordHash() {
        return newPasswordHash;
    }
}
