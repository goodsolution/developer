package pl.com.goodsolution.adviser.auth;

import java.util.Arrays;
import java.util.List;

class Menu {
    private String name;
    private String url;
    private Permissions[] permissions;
    private List<Menu> children;


    public Menu(String name, String url, Permissions[] permissions, List<Menu> children) {
        this.name = name;
        this.url = url;
        this.permissions = permissions;
        this.children = children;
    }

    boolean hasChildren() {
        return !getChildren().isEmpty();
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public Permissions[] getPermissions() {
        return permissions;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", permissions=" + Arrays.toString(permissions) +
                ", children=" + children +
                '}';
    }
}
