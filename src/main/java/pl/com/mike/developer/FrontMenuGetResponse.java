package pl.com.mike.developer;

import java.util.List;

public class FrontMenuGetResponse {
    private String name;
    private String url;
    private List<FrontMenuGetResponse> children;

    public FrontMenuGetResponse(String name, String url, List<FrontMenuGetResponse> menus) {
        this.name = name;
        this.url = url;
        this.children = menus;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public List<FrontMenuGetResponse> getChildren() {
        return children;
    }

}
