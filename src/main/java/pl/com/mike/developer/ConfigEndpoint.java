package pl.com.mike.developer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.mike.developer.config.ApplicationConfig;
import pl.com.mike.developer.logic.developer.DeveloperSearchFilter;
import pl.com.mike.developer.logic.developer.DeveloperService;

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

    @GetMapping("homePageImagePath")
    public HomePageImagePathGetResponse getHomePageImagePathResponse() {
        return new HomePageImagePathGetResponse(
                getHomePageImagePathUrl()
        );
    }

    private String getHomePageImagePathUrl() {
        return applicationConfig.getSystemAsset() + applicationConfig.getSystemCode() + applicationConfig.getSystemHomePagePhoto();
    }


    private String getUrl(String logoFileName) {
        return applicationConfig.getSystemAsset() + applicationConfig.getSystemCode() + logoFileName;
    }


}
