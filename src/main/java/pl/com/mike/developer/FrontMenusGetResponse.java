package pl.com.mike.developer;

import java.util.List;

public class FrontMenusGetResponse {
    List<FrontMenuGetResponse> menus;

    public FrontMenusGetResponse(List<FrontMenuGetResponse> menus) {
        this.menus = menus;
    }

    public List<FrontMenuGetResponse> getMenus() {
        return menus;
    }

}
