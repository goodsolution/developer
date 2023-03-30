package pl.com.mike.developer.api.adviser;

public class PullGetRequest {
    private String context;
    private String id;

    public PullGetRequest() {
    }

    public PullGetRequest(String context, String id) {
        this.context = context;
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public String getId() {
        return id;
    }
}
