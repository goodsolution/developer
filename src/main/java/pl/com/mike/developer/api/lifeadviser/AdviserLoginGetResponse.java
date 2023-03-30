package pl.com.mike.developer.api.lifeadviser;

public class AdviserLoginGetResponse {
     private Long userId;

    public AdviserLoginGetResponse(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
