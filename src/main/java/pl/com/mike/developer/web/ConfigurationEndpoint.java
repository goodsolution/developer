package pl.com.mike.developer.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.mike.developer.ConfigurationGetResponse;
import pl.com.mike.developer.config.ApplicationConfig;
import pl.com.mike.developer.logic.developer.DeveloperSearchFilter;
import pl.com.mike.developer.logic.developer.DeveloperService;

@RestController
@RequestMapping("api/system/")
public class ConfigurationEndpoint {
    private final DeveloperService developerService;
    private final ApplicationConfig applicationConfig;

    public ConfigurationEndpoint(DeveloperService developerService, ApplicationConfig applicationConfig) {
        this.developerService = developerService;
        this.applicationConfig = applicationConfig;
    }

    @GetMapping("configuration")
    public ConfigurationGetResponse getConfiguration() {
        return new ConfigurationGetResponse(
                getLogoUrl(developerService.getDeveloperByCode(
                        new DeveloperSearchFilter(applicationConfig.getSystemCode()))
                        .getLogoUrl())
        );
    }

    private String getLogoUrl(String logoFileName) {
        return applicationConfig.getSystemAsset() + applicationConfig.getSystemCode() + logoFileName;
    }

}
