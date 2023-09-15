package pl.com.mike.developer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.mike.developer.config.ApplicationConfig;
import pl.com.mike.developer.logic.developer.DeveloperSearchFilter;
import pl.com.mike.developer.logic.developer.DeveloperService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/dev/")
public class DeveloperLogoEndpoint {
    private final DeveloperService developerService;
    private final ApplicationConfig applicationConfig;

    public DeveloperLogoEndpoint(DeveloperService developerService, ApplicationConfig applicationConfig) {
        this.developerService = developerService;
        this.applicationConfig = applicationConfig;
    }

    @GetMapping("developers/{id}/logo")
    public LogoUrlGetResponse getLogoUrl(@PathVariable Long id) {
        return new LogoUrlGetResponse(getUrl(
                developerService.getDeveloperById(new DeveloperSearchFilter(id)).stream()
                        .findFirst()
                        .orElseThrow(NoSuchElementException::new)
                        .getLogoUrl()
        ));
    }

    private String getUrl(String logoUrl) {
        return "angular/assets/img/" + applicationConfig.getDeveloperCode() + "/" + logoUrl;
    }

}
