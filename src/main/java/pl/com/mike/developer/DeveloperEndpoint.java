package pl.com.mike.developer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.mike.developer.logic.developer.PremiseSearchFilter;
import pl.com.mike.developer.logic.developer.PremiseService;
import pl.com.mike.developer.web.ConverterToResponse;

@RestController
@RequestMapping("api/dev/")
public class DeveloperEndpoint {

    private final PremiseService premiseService;

    public DeveloperEndpoint(PremiseService premiseService) {
        this.premiseService = premiseService;
    }

    @GetMapping("premise/{id}")
    public PremisesGetResponse getPremiseDataById(@PathVariable Long id) {
        return new PremisesGetResponse(ConverterToResponse.premiseDataToResponse(
                premiseService.getPremiseDataById(new PremiseSearchFilter(id)))
        );
    }

}
