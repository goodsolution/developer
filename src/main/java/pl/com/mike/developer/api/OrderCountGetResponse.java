package pl.com.mike.developer.api;

public class OrderCountGetResponse {
    private Long count;

    public OrderCountGetResponse(Long count) {
        this.count = count;
    }

    public Long getCount() {
        return count;
    }
}
