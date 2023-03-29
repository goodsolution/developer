package pl.com.goodsolution.adviser.api.courseplatform;

public class UnseenNotificationsCountRequestGetResponse {
    private Long count;

    public UnseenNotificationsCountRequestGetResponse(Long count) {
        this.count = count;
    }

    public Long getCount() {
        return count;
    }
}
