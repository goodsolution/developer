package pl.com.mike.developer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.mike.developer.config.ApplicationConfig;
import pl.com.mike.developer.logic.developer.DeveloperSearchFilter;
import pl.com.mike.developer.logic.developer.DeveloperService;
import pl.com.mike.developer.logic.developer.InvestmentService;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/system/")
public class FrontMenuEndpoint {

    private final InvestmentService investmentService;
    private final DeveloperService developerService;

    private final ApplicationConfig applicationConfig;

    public FrontMenuEndpoint(InvestmentService investmentService, DeveloperService developerService, ApplicationConfig applicationConfig) {
        this.investmentService = investmentService;
        this.developerService = developerService;
        this.applicationConfig = applicationConfig;
    }

    @GetMapping("developers/front-menu")
    public FrontMenusGetResponse getFrontMenu() {
        return getFrontMenusGetResponse();
    }

    private FrontMenusGetResponse getFrontMenusGetResponse() {
        return new FrontMenusGetResponse(
                new ArrayList<>(Arrays.asList(
//                        new FrontMenuGetResponse(getDeveloperName(applicationConfig.getSystemCode()), "/name", Collections.emptyList()),
                        new FrontMenuGetResponse("Cities", "/cities", getCities()),
                        new FrontMenuGetResponse("Contact", "/developer/contact", Collections.emptyList())
                ))
        );
    }

    private String getDeveloperName(String code) {
        return developerService.getDeveloperByCode(new DeveloperSearchFilter(code)).getName();
    }

    private List<FrontMenuGetResponse> getCities() {
        return investmentService.getInvestmentCitiesByDeveloperCode().stream()
                .map(x -> new FrontMenuGetResponse(x.getName(), "", Collections.emptyList()))
                .collect(Collectors.toList());
    }

}
