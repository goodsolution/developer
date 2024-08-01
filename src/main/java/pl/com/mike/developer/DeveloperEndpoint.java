package pl.com.mike.developer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.mike.developer.config.developer.ApplicationConfig;
import pl.com.mike.developer.domain.developer.Developer;
import pl.com.mike.developer.domain.developer.DeveloperData;
import pl.com.mike.developer.logic.developer.DeveloperSearchFilter;
import pl.com.mike.developer.logic.developer.DeveloperService;

import java.util.List;

@RestController
@RequestMapping("api/developers/")
public class DeveloperEndpoint {

    private final DeveloperService developerService;
    private final ApplicationConfig applicationConfig;

    public DeveloperEndpoint(DeveloperService developerService,
                             ApplicationConfig applicationConfig) {
        this.developerService = developerService;
        this.applicationConfig = applicationConfig;
    }

    @GetMapping("{id}")
    public DevelopersGetResponse getDeveloperById(@PathVariable Long id) {
        return new DevelopersGetResponse(
                ConverterToResponse.developersDataToResponse(
                        developerService.getDeveloperById(new DeveloperSearchFilter(id))
                ));
    }

    @GetMapping("code")
    public DevelopersGetResponse getDeveloperByCode() {
        return new DevelopersGetResponse(
                ConverterToResponse.developerDataToResponse(
                        developerService.getDeveloperByCode(
                                new DeveloperSearchFilter(applicationConfig.getSystemCode())
                        )
                ));
    }

    @GetMapping
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
                        .toList()
        );
    }

    @PostMapping("register")
    public ResponseEntity<?> registerDeveloper(@RequestBody DeveloperData developerData) {
        DeveloperData developer = developerService.createDeveloper(developerData);
        return ResponseEntity.ok(developer);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateDeveloper(@PathVariable Long id, @RequestBody DeveloperData developerData) {
        DeveloperData developer = developerService.updateDeveloper(id, developerData);
        return ResponseEntity.ok(developer);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> softDeleteDeveloper(@PathVariable Long id) {
        developerService.softDeleteDeveloper(id);
        return ResponseEntity.noContent().build();
    }

}
