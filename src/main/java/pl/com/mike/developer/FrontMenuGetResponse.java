package pl.com.mike.developer;

import java.util.List;

public class FrontMenuGetResponse {
    private String name;
    private String url;
    private List<Object> children;

    public FrontMenuGetResponse(String name, String url, List<Object> menus) {
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

    public List<Object> getChildren() {
        return children;
    }

}
