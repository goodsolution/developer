package pl.com.mike.developer.api.courseplatform;

public class UnseenNotificationsCountRequestGetResponse {
    private Long count;

    public UnseenNotificationsCountRequestGetResponse(Long count) {
        this.count = count;
    }

    public Long getCount() {
        return count;
    }
}
