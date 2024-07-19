package pl.com.mike.developer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.mike.developer.config.developer.ApplicationConfig;
import pl.com.mike.developer.domain.developer.Developer;
import pl.com.mike.developer.domain.developer.DeveloperData;
import pl.com.mike.developer.logic.developer.DeveloperSearchFilter;
import pl.com.mike.developer.logic.developer.DeveloperService;
import pl.com.mike.developer.logic.developer.PremiseSearchFilter;
import pl.com.mike.developer.logic.developer.PremiseService;

import java.util.List;
import java.util.stream.Collectors;

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
                ));
    }

    @GetMapping("developers/code")
    public DevelopersGetResponse getDeveloperByCode() {
        return new DevelopersGetResponse(
                ConverterToResponse.developerDataToResponse(
                        developerService.getDeveloperByCode(
                                new DeveloperSearchFilter(applicationConfig.getSystemCode())
                        )
                ));
    }

    @GetMapping("developers")
    public DevelopersGetResponse getAllActiveDevelopers() {
        List<Developer> allActiveDevelopers = developerService.getAllActiveDevelopers();
        return new DevelopersGetResponse(
                allActiveDevelopers.stream()
                        .map(developer -> new DeveloperGetResponse(
                                developer.getId(),
                                developer.getName(),
                                developer.getAddressCountry(),
                                developer.getAddressStreet(),
                                developer.getAddressBuildingNumber(),
                                developer.getAddressFlatNumber(),
                                developer.getAddressPostalCode(),
                                developer.getTelephoneNumber(),
                                developer.getFaxNumber(),
                                developer.getEmail(),
                                developer.getTaxIdentificationNumber(),
                                developer.getDeveloperCity().getId(),
                                developer.getLogoUrl(),
                                developer.getCode(),
                                developer.getCreatedAt(),
                                developer.getUpdatedAt(),
                                developer.getDeletedAt()
                        ))
                        .collect(Collectors.toList())
        );
    }

    @PostMapping("developers/register")
    public ResponseEntity<?> registerDeveloper(@RequestBody DeveloperData developerData) {
        DeveloperData developer = developerService.createDeveloper(developerData);
        return ResponseEntity.ok(developer);
    }

    @PutMapping("developers/{id}")
    public ResponseEntity<?> updateDeveloper(@PathVariable Long id, @RequestBody DeveloperData developerData) {
        DeveloperData developer = developerService.updateDeveloper(id, developerData);
        return ResponseEntity.ok(developer);
    }

    @DeleteMapping("developers/{id}")
    public ResponseEntity<Void> softDeleteDeveloper(@PathVariable Long id) {
        developerService.softDeleteDeveloper(id);
        return ResponseEntity.noContent().build();
    }

}
