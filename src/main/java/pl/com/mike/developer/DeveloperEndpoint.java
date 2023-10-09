package pl.com.mike.developer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.mike.developer.config.ApplicationConfig;
import pl.com.mike.developer.logic.developer.*;
import pl.com.mike.developer.web.ConverterToResponse;

@RestController
@RequestMapping("api/dev/")
public class DeveloperEndpoint {

    private final PremiseService premiseService;

    private final DeveloperService developerService;
    private final ApplicationConfig applicationConfig;


    public DeveloperEndpoint(PremiseService premiseService, DeveloperService developerService, ApplicationConfig applicationConfig) {
        this.premiseService = premiseService;
        this.developerService = developerService;
        this.applicationConfig = applicationConfig;
    }

    @GetMapping("premises")
    public PremisesGetResponse getAllPremises() {
        return new PremisesGetResponse(
                ConverterToResponse.premisesDataToResponse(
                        premiseService.getAllPremises()
                ));
    }

    @GetMapping("premises/{id}")
    public PremisesGetResponse getPremiseById(@PathVariable Long id) {
        return new PremisesGetResponse(
                ConverterToResponse.premisesDataToResponse(
                        premiseService.getPremiseDataById(new PremiseSearchFilter(id))
                ));
    }

    @GetMapping("developers/{id}")
    public DevelopersGetResponse getDeveloperById(@PathVariable Long id) {
        return new DevelopersGetResponse(
                ConverterToResponse.developersDataToResponse(
                        developerService.getDeveloperById(new DeveloperSearchFilter(id))
                )
        );
    }

    @GetMapping("developers/code")
    public DevelopersGetResponse getDeveloperByCode() {
        return new DevelopersGetResponse(
                ConverterToResponse.developerDataToResponse(
                        developerService.getDeveloperByCode(new DeveloperSearchFilter(applicationConfig.getSystemCode()))
                )
        );
    }


}
