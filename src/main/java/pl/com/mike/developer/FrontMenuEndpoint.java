package pl.com.mike.developer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.mike.developer.domain.developer.CityData;
import pl.com.mike.developer.logic.developer.InvestmentSearchFilter;
import pl.com.mike.developer.logic.developer.InvestmentService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/dev/")
public class FrontMenuEndpoint {

    InvestmentService investmentService;

    public FrontMenuEndpoint(InvestmentService investmentService) {
        this.investmentService = investmentService;
    }

    @GetMapping("developers/{id}/front-menu")
    public FrontMenusGetResponse getFrontMenu(@PathVariable Long id) {
        return getFrontMenusGetResponse(id);
    }


    private FrontMenusGetResponse getFrontMenusGetResponse(Long id) {
        List<CityData> investmentCitiesByDeveloperId = investmentService.getInvestmentCitiesByDeveloperId(new InvestmentSearchFilter(id));

        return new FrontMenusGetResponse(
                new ArrayList<>(
                        Collections.singletonList(
                                new FrontMenuGetResponse(
                                        null, null, new ArrayList<>(
                                        Arrays.asList(
                                                new FrontMenuGetResponse("Cities", null, Collections.singletonList(investmentCitiesByDeveloperId)),
                                                new FrontMenuGetResponse("Contact", "/contact", null)
                                        )
                                )
                                )
                        )
                )
        );
    }

}
