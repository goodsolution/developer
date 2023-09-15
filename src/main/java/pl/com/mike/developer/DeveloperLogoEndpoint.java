package pl.com.mike.developer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.mike.developer.logic.developer.DeveloperSearchFilter;
import pl.com.mike.developer.logic.developer.DeveloperService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/dev/")
public class DeveloperLogoEndpoint {
    private final DeveloperService developerService;

    public DeveloperLogoEndpoint(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @GetMapping("developers/{id}/logo")
    public String getLogoUrl(@PathVariable Long id) {
        return developerService.getDeveloperById(new DeveloperSearchFilter(id)).stream()
                .findFirst()
                .orElseThrow(NoSuchElementException::new)
                .getLogoUrl();
    }

}
