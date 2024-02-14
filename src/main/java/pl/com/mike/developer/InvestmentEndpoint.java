package pl.com.mike.developer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.mike.developer.logic.developer.CityService;
import pl.com.mike.developer.logic.developer.InvestmentService;

import java.util.stream.Collectors;

@RestController
@RequestMapping("api/")
public class InvestmentEndpoint {
    private final InvestmentService investmentService;
    private final CityService cityService;

    public InvestmentEndpoint(InvestmentService investmentService, CityService cityService) {
        this.investmentService = investmentService;
        this.cityService = cityService;
    }

    @GetMapping("cities")
    public CitiesGetResponse getCitiesResponse() {
        return new CitiesGetResponse(
                cityService.getCitiesByDeveloperCode()
                        .stream()
                        .map(city -> new CityGetResponse(
                                city.getId(),
                                city.getName(),
                                city.getVoivodeshipId()
                        ))
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("citiesNamesByDeveloperCode")
    public CitiesGetResponse getCitiesNamesByDeveloperCode() {
        return new CitiesGetResponse(
                investmentService.getInvestmentCitiesByDeveloperCode()
                        .stream()
                        .map(city -> new CityGetResponse(
                                null,
                                city,
                                null
                        ))
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("investments")
    public InvestmentsGetResponse getInvestmentsResponse() {
        return new InvestmentsGetResponse(
                investmentService.getInvestmentsByDeveloperCode()
                        .stream()
                        .map(investment -> new InvestmentGetResponse(
                                investment.getId(),
                                investment.getName(),
                                investment.getDescription(),
                                investment.getAddressCountry(),
                                investment.getAddressStreet(),
                                investment.getDeveloperId(),
                                investment.getCityId(),
                                investment.getVoivodeshipId()
                        ))
                        .collect(Collectors.toList())
        );
    }

}
