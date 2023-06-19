package pl.com.mike.developer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.mike.developer.logic.developer.*;
import pl.com.mike.developer.web.ConverterToResponse;

@RestController
@RequestMapping("api/dev/")
public class DeveloperEndpoint {
    private final DeveloperService developerService;

    public DeveloperEndpoint(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @GetMapping("developers/{id}")
    public DevelopersGetResponse getDeveloperDataById(@PathVariable Long id) {
        return new DevelopersGetResponse(ConverterToResponse.developerDataToResponse(
                developerService.getDeveloperDataById(new DeveloperSearchFilter(id)))
        );
    }

    @GetMapping("developers/{id}/investments/buildings/premises/count")
    public AvailablePremisesByCityGetResponse getAvailablePremiseByCity(@PathVariable Long id) {
        return new AvailablePremisesByCityGetResponse(
                developerService.getAvailablePremisesByCity(new DeveloperSearchFilter(id))
        );
    }

}
