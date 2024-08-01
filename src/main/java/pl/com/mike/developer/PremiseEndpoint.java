package pl.com.mike.developer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.mike.developer.domain.developer.PremiseData;
import pl.com.mike.developer.logic.developer.PremiseSearchFilter;
import pl.com.mike.developer.logic.developer.PremiseService;

@RestController
@RequestMapping("api/premises/")
public class PremiseEndpoint {
    private final PremiseService premiseService;

    public PremiseEndpoint(PremiseService premiseService) {
        this.premiseService = premiseService;
    }

    @GetMapping("investment/{id}/enhancedPremiseData")
    public PremiseAggregatedValuesGetResponse getMinAndMaxTotalPremisePriceByInvestmentId(@PathVariable Long id) {
        return premiseService.findPremisePriceRangeByInvestmentId(id);
    }

    @GetMapping("investment/{id}")
    public PremisesGetResponse getPremisesByInvestmentId(
            @PathVariable Long id,
            @RequestParam(name = "languageCode", required = false) String languageCode) {
        return new PremisesGetResponse(
                ConverterToResponse.premisesDataToResponse(
                        premiseService.getPremiseDataByInvestmentId(new PremiseSearchFilter(id, languageCode))
                ));
    }

    @GetMapping("{id}")
    public PremisesGetResponse getPremiseById(
            @PathVariable Long id,
            @RequestParam(name = "languageCode", required = false) String languageCode) {
        return new PremisesGetResponse(
                ConverterToResponse.premisesDataToResponse(
                        premiseService.getPremiseDataById(new PremiseSearchFilter(id, languageCode))
                ));
    }

    @PostMapping
    public ResponseEntity<PremisePostResponse> createPremise(@RequestBody PremiseData request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new PremisePostResponse(premiseService.createPremiseData(request)));
    }

    @PutMapping("{id}")
    public ResponseEntity<PremisePutResponse> updatePremise(@PathVariable Long id, @RequestBody PremiseData premiseDatadata) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new PremisePutResponse(premiseService.updatePremise(id, premiseDatadata))
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void>  softDeletePremise(@PathVariable Long id) {
        premiseService.softDeletePremise(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}