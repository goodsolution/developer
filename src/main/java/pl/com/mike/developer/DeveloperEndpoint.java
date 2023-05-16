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
    private final PremiseService premiseService;
    private final InvestmentService investmentService;
    private final BuildingService buildingService;
    private final DeveloperService developerService;

    public DeveloperEndpoint(PremiseService premiseService, InvestmentService investmentService, BuildingService buildingService, DeveloperService developerService) {
        this.premiseService = premiseService;
        this.investmentService = investmentService;
        this.buildingService = buildingService;
        this.developerService = developerService;
    }

    @GetMapping("developer/{id}")
    public DevelopersGetResponse getDeveloperDataById(@PathVariable Long id) {
        return new DevelopersGetResponse(ConverterToResponse.developerDataToResponse(
                developerService.getDeveloperDataById(new DeveloperSearchFilter(id)))
        );
    }

    @GetMapping("premise/{id}")
    public PremisesGetResponse getPremiseDataById(@PathVariable Long id) {
        return new PremisesGetResponse(ConverterToResponse.premiseDataToResponse(
                premiseService.getPremiseDataById(new PremiseSearchFilter(id)))
        );
    }

    @GetMapping("building/{id}")
    public BuildingsGetResponse getBuildingDataById(@PathVariable Long id) {
        return new BuildingsGetResponse(ConverterToResponse.buildingDataToResponse(
                buildingService.getBuildingDataById(new BuildingSearchFilter(id)))
        );
    }

    @GetMapping("investment/{id}")
    public InvestmentsGetResponse getInvestmentDataByDeveloperId(@PathVariable Long id) {
        return new InvestmentsGetResponse(
                ConverterToResponse.investmentDataToResponse(
                        investmentService.getInvestmentDataByDeveloperId(new InvestmentSearchFilter(id))
                )
        );
    }

}