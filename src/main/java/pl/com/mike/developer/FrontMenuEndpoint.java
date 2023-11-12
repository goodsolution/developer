package pl.com.mike.developer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.mike.developer.auth.Menu;
import pl.com.mike.developer.auth.MenuService;
import pl.com.mike.developer.config.ApplicationConfig;
import pl.com.mike.developer.logic.developer.DeveloperService;
import pl.com.mike.developer.logic.developer.InvestmentService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/dev/")
public class FrontMenuEndpoint {

    private final InvestmentService investmentService;
    private final DeveloperService developerService;
    private final ApplicationConfig applicationConfig;
    private final MenuService menuService;

    public FrontMenuEndpoint(InvestmentService investmentService, DeveloperService developerService, ApplicationConfig applicationConfig, MenuService menuService) {
        this.investmentService = investmentService;
        this.developerService = developerService;
        this.applicationConfig = applicationConfig;
        this.menuService = menuService;
    }

    @GetMapping("developers/front-menu")
    public FrontMenusGetResponse getFrontMenu() {
        return getFrontMenusGetResponse();
    }

    private FrontMenusGetResponse getFrontMenusGetResponse() {
        return new FrontMenusGetResponse(
                menuService.getMenu(applicationConfig.getSystemCode()).stream()
                        .map(x -> new FrontMenuGetResponse(x.getName(), x.getUrl(), getResponses(x.getChildren())))
                        .collect(Collectors.toList())
        );
    }

    List<FrontMenuGetResponse> getResponses(List<Menu> menus) {
        List<FrontMenuGetResponse> frontMenuGetResponses = new ArrayList<>();
        for (Menu menu : menus) {
            frontMenuGetResponses.add(new FrontMenuGetResponse(menu.getName(), menu.getUrl(), getResponses(menu.getChildren())));
        }
        return frontMenuGetResponses;
    }

}
