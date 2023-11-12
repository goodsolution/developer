package pl.com.mike.developer;

import java.util.List;

public class FrontMenuGetResponse {

    private String name;
    private String url;
    private List<FrontMenuGetResponse> children;

    public FrontMenuGetResponse(String name, String url, List<FrontMenuGetResponse> children) {
        this.name = name;
        this.url = url;
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<FrontMenuGetResponse> getChildren() {
        return children;
    }

    public void setChildren(List<FrontMenuGetResponse> children) {
        this.children = children;
    }
}
