package pl.com.mike.developer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.mike.developer.logic.developer.InvestmentSearchFilter;
import pl.com.mike.developer.logic.developer.InvestmentService;
import pl.com.mike.developer.logic.developer.PremiseSearchFilter;
import pl.com.mike.developer.logic.developer.PremiseService;
import pl.com.mike .developer.web.ConverterToResponse;

@RestController
@RequestMapping("api/dev/")
public class DeveloperEndpoint {

    private final PremiseService premiseService;
    private final InvestmentService investmentService;

    public DeveloperEndpoint(PremiseService premiseService, InvestmentService investmentService) {
        this.premiseService = premiseService;
        this.investmentService = investmentService;
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

    @GetMapping("developers/{id}/investments/cities")
    public InvestmentCitiesByDeveloperGetResponse getInvestmentCitiesByDeveloperId(@PathVariable Long id) {
        return new InvestmentCitiesByDeveloperGetResponse(investmentService.getInvestmentCitiesByDeveloperId(new InvestmentSearchFilter(id)));
    }

}
