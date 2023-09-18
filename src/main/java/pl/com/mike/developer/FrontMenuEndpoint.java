package pl.com.mike.developer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.mike.developer.domain.developer.CityData;
import pl.com.mike.developer.domain.developer.DeveloperData;
import pl.com.mike.developer.logic.developer.DeveloperSearchFilter;
import pl.com.mike.developer.logic.developer.DeveloperService;
import pl.com.mike.developer.logic.developer.InvestmentSearchFilter;
import pl.com.mike.developer.logic.developer.InvestmentService;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/dev/")
public class FrontMenuEndpoint {

    private final InvestmentService investmentService;
    private final DeveloperService developerService;

    public FrontMenuEndpoint(InvestmentService investmentService, DeveloperService developerService) {
        this.investmentService = investmentService;
        this.developerService = developerService;
    }

    @GetMapping("developers/{id}/front-menu")
    public FrontMenusGetResponse getFrontMenu(@PathVariable Long id) {
        return getFrontMenusGetResponse(id);
    }

    private FrontMenusGetResponse getFrontMenusGetResponse(Long id) {
        return new FrontMenusGetResponse(
                new ArrayList<>(Arrays.asList(
                        new FrontMenuGetResponse(getDeveloperName(id), "/name", Collections.emptyList()),
                        new FrontMenuGetResponse("Cities", "/", getCities(id)),
                        new FrontMenuGetResponse("Contact", "developer/contact", Collections.emptyList()),
                        new FrontMenuGetResponse("Language", "/language", Collections.emptyList())
                ))
        );
    }

    private String getDeveloperName(Long id) {
        List<DeveloperData> developerById = developerService.getDeveloperById(new DeveloperSearchFilter(id));
        return developerById.stream()
                .findFirst()
                .orElseThrow(NoSuchElementException::new)
                .getName();
    }

    private List<FrontMenuGetResponse> getCities(Long id) {
        return investmentService.getInvestmentCitiesByDeveloperId(new InvestmentSearchFilter(id)).stream()
                .map(x -> new FrontMenuGetResponse(x.getName(), "", Collections.emptyList()))
                .collect(Collectors.toList());
    }

}
