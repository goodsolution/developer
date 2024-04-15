package pl.com.mike.developer;

import org.springframework.web.bind.annotation.*;
import pl.com.mike.developer.config.ApplicationConfig;
import pl.com.mike.developer.logic.developer.DeveloperSearchFilter;
import pl.com.mike.developer.logic.developer.DeveloperService;
import pl.com.mike.developer.logic.developer.PremiseSearchFilter;
import pl.com.mike.developer.logic.developer.PremiseService;
import pl.com.mike.developer.web.ConverterToResponse;

@RestController
@RequestMapping("api/")
public class DeveloperEndpoint {

    private final PremiseService premiseService;
    private final DeveloperService developerService;
    private final ApplicationConfig applicationConfig;

    public DeveloperEndpoint(PremiseService premiseService,
                             DeveloperService developerService,
                             ApplicationConfig applicationConfig) {
        this.premiseService = premiseService;
        this.developerService = developerService;
        this.applicationConfig = applicationConfig;
    }

    @GetMapping("premises/investment/{id}/enhancedPremiseData")
    public PremiseAggregatedValuesGetResponse getMinAndMaxTotalPremisePriceByInvestmentId(@PathVariable Long id) {
        return premiseService.findPremisePriceRangeByInvestmentId(id);
    }

    @GetMapping("premises/investment/{id}")
    public PremisesGetResponse getPremisesByInvestmentId(
            @PathVariable Long id,
            @RequestParam(name = "languageCode", required = false) String languageCode) {
        return new PremisesGetResponse(
                ConverterToResponse.premisesDataToResponse(
                        premiseService.getPremiseDataByInvestmentId(new PremiseSearchFilter(id, languageCode))
                ));
    }

    @GetMapping("premises/{id}")
    public PremisesGetResponse getPremiseById(
            @PathVariable Long id,
            @RequestParam(name = "languageCode", required = false) String languageCode) {
        return new PremisesGetResponse(
                ConverterToResponse.premisesDataToResponse(
                        premiseService.getPremiseDataById(new PremiseSearchFilter(id, languageCode))
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
