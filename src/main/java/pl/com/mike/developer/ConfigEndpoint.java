package pl.com.mike.developer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.com.mike.developer.config.ApplicationConfig;
import pl.com.mike.developer.logic.developer.CityService;
import pl.com.mike.developer.logic.developer.DeveloperSearchFilter;
import pl.com.mike.developer.logic.developer.DeveloperService;
import pl.com.mike.developer.logic.developer.InvestmentService;

import java.util.Collections;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/system/")
public class ConfigEndpoint {
    private final DeveloperService developerService;
    private final InvestmentService investmentService;
    private final CityService cityService;
    private final ApplicationConfig applicationConfig;

    public ConfigEndpoint(DeveloperService developerService, InvestmentService investmentService, CityService cityService, ApplicationConfig applicationConfig) {
        this.developerService = developerService;
        this.investmentService = investmentService;
        this.cityService = cityService;
        this.applicationConfig = applicationConfig;
    }

    @GetMapping("developers/logo")
    public LogoUrlGetResponse getLogoUrl() {
        return new LogoUrlGetResponse(
                getUrl(developerService.getDeveloperByCode(new DeveloperSearchFilter(applicationConfig.getSystemCode())).getLogoUrl())
        );
    }

    @GetMapping("code")
    public CodeGetResponse getCodeResponse() {
        return new CodeGetResponse(
                applicationConfig.getSystemCode()
        );
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

    @GetMapping("investmentsByCity")
    public InvestmentsByCityGetResponse getInvestmentsByCityResponse(@RequestParam(name = "city", required = true) String city) {
        return new InvestmentsByCityGetResponse(
                investmentService.getInvestmentsByDeveloperCodeAndCity(city)
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

    private String getUrl(String logoFileName) {
        return applicationConfig.getSystemAsset() + applicationConfig.getSystemCode() + logoFileName;
    }


}
