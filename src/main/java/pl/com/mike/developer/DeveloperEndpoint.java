package pl.com.mike.developer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    private static final Logger logger = LoggerFactory.getLogger(DeveloperEndpoint.class);

    public DeveloperEndpoint(PremiseService premiseService, DeveloperService developerService, ApplicationConfig applicationConfig) {
        this.premiseService = premiseService;
        this.developerService = developerService;
        this.applicationConfig = applicationConfig;
    }

    @GetMapping("premises/investment/{id}")
    public PremisesGetResponse getPremisesByInvestmentId(@PathVariable Long id) {
        return new PremisesGetResponse(
                ConverterToResponse.premisesDataToResponse(
                        premiseService.getPremiseDataByInvestmentId(new PremiseSearchFilter(id))
                ));
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

    @GetMapping("/premises/investment/{id}/{priceFunction}")
    public PremisesGetResponse getMaxTotalPremisePriceByInvestmentId(@PathVariable Long id, @PathVariable String priceFunction) {
        if (!"min".equals(priceFunction) && !"max".equals(priceFunction)) {
            logger.error("Invalid price function. Only 'min' or 'max' are accepted.");
        }
        return new PremisesGetResponse(
                ConverterToResponse.premisesDataToResponse(
                        premiseService.findPriceByInvestmentId(id, priceFunction)
                ));
    }

}
