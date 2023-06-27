package pl.com.mike.developer;

import org.springframework.web.bind.annotation.*;
import pl.com.mike.developer.domain.developer.PremiseData;
import pl.com.mike.developer.logic.developer.PremiseSearchFilter;
import pl.com.mike.developer.logic.developer.PremiseService;
import pl.com.mike.developer.web.ConverterToResponse;

@RestController
@RequestMapping("api/dev/")
public class DeveloperEndpoint {

    private final PremiseService premiseService;

    public DeveloperEndpoint(PremiseService premiseService) {
        this.premiseService = premiseService;
    }

    @GetMapping("premises")
    public PremisesGetResponse getAllPremisesData() {
        return ConverterToResponse.premisesDataToResponse(premiseService.getAllPremises());
    }

    @GetMapping("premises/{id}")
    public PremiseGetResponse getPremiseDataById(@PathVariable Long id) {
        return ConverterToResponse.premiseDataToResponse(premiseService.getPremiseById(new PremiseSearchFilter(id))
        );
    }


//    @GetMapping("premises")
//    public List<PremiseData> getAllPremisesData() {
//        return premiseService.getAllPremisesData();
//    }

//    @GetMapping("premises/{id}")
//    public PremiseData getPremiseDataById(@PathVariable Long id) {
//        return premiseService.getPremiseDataById(new PremiseSearchFilter(id));
//    }

    @PatchMapping("/premise/")
    public void updatePremise(@RequestBody PremiseData request) {
        premiseService.update(new PremiseData());
    }


}
