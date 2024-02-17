package pl.com.mike.developer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.mike.developer.config.ApplicationConfig;
import pl.com.mike.developer.logic.developer.DeveloperSearchFilter;
import pl.com.mike.developer.logic.developer.DeveloperService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/system/")
public class ConfigEndpoint {
    private final DeveloperService developerService;
    private final ApplicationConfig applicationConfig;

    public ConfigEndpoint(DeveloperService developerService, ApplicationConfig applicationConfig) {
        this.developerService = developerService;
        this.applicationConfig = applicationConfig;
    }

    @GetMapping("developers/logo")
    public LogoUrlGetResponse getLogoUrl() {
        return new LogoUrlGetResponse(
                getUrl(developerService.getDeveloperByCode(new DeveloperSearchFilter(applicationConfig.getSystemCode())).getLogoUrl())
        );
    }

    @GetMapping("code")
    public CodeGetResponse getCodeResponse() {
        return new CodeGetResponse(
                applicationConfig.getSystemCode()
        );
    }

    @GetMapping("config")
    public ResponseEntity<Map<String, Object>> getConfig() {
        Map<String, Object> config = new HashMap<>();

        config.put("production", false);
        config.put("logoDeveloperEndpoint", "https://localhost:8081/api/system/developers/logo");
        config.put("citiesEndpoint", "https://localhost:8081/api/system/cities");
        config.put("citiesByDeveloperCode", "https://localhost:8081/api/system/citiesNamesByDeveloperCode");
        config.put("systemCodeEndpoint", "https://localhost:8081/api/system/code");
        config.put("frontMenuDeveloperEndpoint", "https://localhost:8081/api/system/developers/front-menu");
        config.put("investmentsEndpoint", "https://localhost:8081/api/system/investments");
        config.put("developerBySystemCodeEndpoint", "https://localhost:8081/api/system/developers/code");
        config.put("premisesByInvestmentIdEndpoint", "https://localhost:8081/api/system/premises/investment");

        return ResponseEntity.ok(config);
    }



    private String getUrl(String logoFileName) {
        return applicationConfig.getSystemAsset() + applicationConfig.getSystemCode() + logoFileName;
    }

}