package pl.com.goodsolution.adviser.api.vin;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import pl.com.goodsolution.adviser.domain.vin.*;
import pl.com.goodsolution.adviser.logic.vin.CepikService;
import pl.com.goodsolution.adviser.logic.vin.DictionariesFilter;
import pl.com.goodsolution.adviser.logic.vin.VehicleService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/vin")
public class VinEndpoint {

    private CepikService cepikService;
    private VehicleService vehicleService;

    public VinEndpoint(CepikService cepikService, VehicleService vehicleService) {
        this.cepikService = cepikService;
        this.vehicleService = vehicleService;
    }

    @GetMapping("/dictionary")
    public Dictionaries getDictionaries(
            @RequestParam(name = "limit", required = false) String limit,
            @RequestParam(name = "page", required = false) String page
    ) {
        return cepikService.getDictionaries(new DictionariesFilter(limit, page));
    }

    @GetMapping("/dictionaryCepik/{id}")
    public Dictionary getDictionary(
            @PathVariable String id,
            @RequestParam(name = "limit", required = false) String limit,
            @RequestParam(name = "page", required = false) String page
    ) {
        return cepikService.getDictionary(new DictionariesFilter(id, limit, page)).get();
    }

    @GetMapping("/dictionary/{id}")
    public Dictionary getDictionaryTransformed(
            @PathVariable String id,
            @RequestParam(name = "limit", required = false) String limit,
            @RequestParam(name = "page", required = false) String page
    ) {
        return cepikService.getDictionary(new DictionariesFilter(id, limit, page)).get();
    }

    @GetMapping("/car/{id}")
    public CarTransformed getCar(@PathVariable Long id) {
        return cepikService.getVehicle(new CarsFilter(id));
    }

    @GetMapping("/cars/{pages}")
    public CarsTransformed getCar(
            @PathVariable Long pages,
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "voivodeship_code") String voivodeshipCode,
            @RequestParam(name = "date_from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(name = "date_to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @RequestParam(name = "date_type", required = false) String dateType,
            @RequestParam(name = "vin", required = false) String vin,
            @RequestParam(name = "is_registered", required = false) Boolean isRegistered,
            @RequestParam(name = "are_shown_all_fields", required = false) Boolean areShownAllFields,
            @RequestParam(name = "fields", required = false) String[] fields,
            @RequestParam(name = "limit", required = false) Integer limit,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "sort", required = false) String[] sort,
            @RequestParam(name = "dateOfFirstRegistration", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfFirstRegistration,
            @RequestParam(name = "brand", required = false) String brand,
            @RequestParam(name = "model", required = false) String model,
            @RequestParam(name = "carCategory", required = false) String carCategory,
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "variant", required = false) String variant,
            @RequestParam(name = "version", required = false) String version,
            @RequestParam(name = "carKind", required = false) String carKind,
            @RequestParam(name = "carSubKind", required = false) String carSubKind,
            @RequestParam(name = "carPurpose", required = false) String carPurpose,
            @RequestParam(name = "carOrigin", required = false) String carOrigin,
            @RequestParam(name = "carNamePlateKind", required = false) String carNamePlateKind,
            @RequestParam(name = "carProductionMethod", required = false) String carProductionMethod,
            @RequestParam(name = "carProductionYear", required = false) String carProductionYear,
            @RequestParam(name = "initialRegistrationDateInCountry", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate initialRegistrationDateInCountry,
            @RequestParam(name = "lastRegistrationDateInCountry", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastRegistrationDateInCountry,
            @RequestParam(name = "registrationDateAbroad", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate registrationDateAbroad,
            @RequestParam(name = "engineDisplacement", required = false) Integer engineDisplacement,
            @RequestParam(name = "motorPowerToUnladenWeightRatioMotorcycle", required = false) Double motorPowerToUnladenWeightRatioMotorcycle,
            @RequestParam(name = "engineNetPower", required = false) Double engineNetPower,
            @RequestParam(name = "hybridEngineNetPower", required = false) Double hybridEngineNetPower,
            @RequestParam(name = "ownMass", required = false) Double ownMass,
            @RequestParam(name = "massOfVehicleReadyToRun", required = false) Double massOfVehicleReadyToRun,
            @RequestParam(name = "permissibleGrossWeight", required = false) Double permissibleGrossWeight,
            @RequestParam(name = "maxTotalMass", required = false) Double maxTotalMass,
            @RequestParam(name = "permissiblePackage", required = false) Double permissiblePackage,
            @RequestParam(name = "maxPackage", required = false) Double maxPackage,
            @RequestParam(name = "permissibleTotalMassOfVehiclesSet", required = false) Double permissibleTotalMassOfVehiclesSet,
            @RequestParam(name = "axlesNumber", required = false) Double axlesNumber,
            @RequestParam(name = "permissibleAxleLoad", required = false) Double permissibleAxleLoad,
            @RequestParam(name = "maxAxleLoad", required = false) Double maxAxleLoad,
            @RequestParam(name = "maxTotalWeightTrailerWithBrake", required = false) Double maxTotalWeightTrailerWithBrake,
            @RequestParam(name = "maxTotalWeightTrailerWithoutBrake", required = false) Double maxTotalWeightTrailerWithoutBrake,
            @RequestParam(name = "placesNumberInTotal", required = false) Integer placesNumberInTotal,
            @RequestParam(name = "seatsNumber", required = false) Integer seatsNumber,
            @RequestParam(name = "standingPlacesNumber", required = false) Integer standingPlacesNumber,
            @RequestParam(name = "fuelType", required = false) String fuelType,
            @RequestParam(name = "firstAlternativefuelType", required = false) String firstAlternativefuelType,
            @RequestParam(name = "secondAlternativefuelType", required = false) String secondAlternativefuelType,
            @RequestParam(name = "averageFuelConsumption", required = false) Double averageFuelConsumption,
            @RequestParam(name = "co2EmissionLevel", required = false) Double co2EmissionLevel,
            @RequestParam(name = "suspensionType", required = false) String suspensionType,
            @RequestParam(name = "equipmentAndTypeOfRadarDevice", required = false) String equipmentAndTypeOfRadarDevice,
            @RequestParam(name = "steeringRightHandSide", required = false) Boolean steeringRightHandSide,
            @RequestParam(name = "steeringRightHandSideOrigin", required = false) Boolean steeringRightHandSideOrigin,
            @RequestParam(name = "catalystAbsorber", required = false) Boolean catalystAbsorber,
            @RequestParam(name = "manufacturerName", required = false) String manufacturerName,
            @RequestParam(name = "carTransportInstituteCode", required = false) String carTransportInstituteCode,
            @RequestParam(name = "wheelBaseSteeringOtherAxles", required = false) Double wheelBaseSteeringOtherAxles,
            @RequestParam(name = "maxTrackWheel", required = false) Double maxTrackWheel,
            @RequestParam(name = "avgTrackWheel", required = false) Double avgTrackWheel,
            @RequestParam(name = "minTrackWheel", required = false) Double minTrackWheel,
            @RequestParam(name = "exhaustEmissionReduction", required = false) String exhaustEmissionReduction,
            @RequestParam(name = "encodingTypeSubtypeDestiny", required = false) String encodingTypeSubtypeDestiny,
            @RequestParam(name = "codeKindSubgenusDestiny", required = false) String codeKindSubgenusDestiny,
            @RequestParam(name = "vehicleDeregistrationDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate vehicleDeregistrationDate,
            @RequestParam(name = "vehicleDeregistrationReason", required = false) String vehicleDeregistrationReason,
            @RequestParam(name = "dataInputDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInputDate,
            @RequestParam(name = "registrationVoivodship", required = false) String registrationVoivodship,
            @RequestParam(name = "registrationCommune", required = false) String registrationCommune,
            @RequestParam(name = "registrationDistrict", required = false) String registrationDistrict,
            @RequestParam(name = "ownerVoivodship", required = false) String ownerVoivodship,
            @RequestParam(name = "ownerDistrict", required = false) String ownerDistrict,
            @RequestParam(name = "ownerCommune", required = false) String ownerCommune,
            @RequestParam(name = "ownerVoivodshipCode", required = false) String ownerVoivodshipCode,
            @RequestParam(name = "co2EmissionsAlternativeFuel1", required = false) Double co2EmissionsAlternativeFuel1
    ) {

        return vehicleService.findCars(new CarsFilter(
                pages,
                12L,
                id,
                voivodeshipCode,
                dateFrom,
                dateTo,
                dateType,
                vin,
                isRegistered,
                areShownAllFields,
                fields,
                limit,
                page,
                sort,
                dateOfFirstRegistration,
                brand,
                model,
                carCategory,
                type,
                variant,
                version,
                carKind,
                carSubKind,
                carPurpose,
                carOrigin,
                carNamePlateKind,
                carProductionMethod,
                carProductionYear,
                initialRegistrationDateInCountry,
                lastRegistrationDateInCountry,
                registrationDateAbroad,
                engineDisplacement,
                motorPowerToUnladenWeightRatioMotorcycle,
                engineNetPower,
                hybridEngineNetPower,
                ownMass,
                massOfVehicleReadyToRun,
                permissibleGrossWeight,
                maxTotalMass,
                permissiblePackage,
                maxPackage,
                permissibleTotalMassOfVehiclesSet,
                axlesNumber,
                permissibleAxleLoad,
                maxAxleLoad,
                maxTotalWeightTrailerWithBrake,
                maxTotalWeightTrailerWithoutBrake,
                placesNumberInTotal,
                seatsNumber,
                standingPlacesNumber,
                fuelType,
                firstAlternativefuelType,
                secondAlternativefuelType,
                averageFuelConsumption,
                co2EmissionLevel,
                suspensionType,
                equipmentAndTypeOfRadarDevice,
                steeringRightHandSide,
                steeringRightHandSideOrigin,
                catalystAbsorber,
                manufacturerName,
                carTransportInstituteCode,
                wheelBaseSteeringOtherAxles,
                maxTrackWheel,
                avgTrackWheel,
                minTrackWheel,
                exhaustEmissionReduction,
                encodingTypeSubtypeDestiny,
                codeKindSubgenusDestiny,
                vehicleDeregistrationDate,
                vehicleDeregistrationReason,
                dataInputDate,
                registrationVoivodship,
                registrationCommune,
                registrationDistrict,
                ownerVoivodship,
                ownerDistrict,
                ownerCommune,
                ownerVoivodshipCode,
                co2EmissionsAlternativeFuel1));
    }
}