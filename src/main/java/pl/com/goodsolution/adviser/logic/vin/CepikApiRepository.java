package pl.com.goodsolution.adviser.logic.vin;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.com.goodsolution.adviser.config.ApplicationConfig;
import pl.com.goodsolution.adviser.domain.vin.*;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

@Service
public class CepikApiRepository {
    private RestTemplate restTemplate;
    private ApplicationConfig applicationConfig;

    public CepikApiRepository(RestTemplate restTemplate, ApplicationConfig applicationConfig) {
        this.restTemplate = restTemplate;
        this.applicationConfig = applicationConfig;
    }

    Dictionaries getDictionaries(DictionariesFilter filter) {
        return restTemplate.getForObject(applicationConfig.getUrl() + "/slowniki" + prepareDictionaryUrl(filter), Dictionaries.class);
    }

    public Optional<Dictionary> getDictionary(DictionariesFilter filter) {
        try {
            ResponseEntity<Dictionary> responseEntity = restTemplate.getForEntity(applicationConfig.getUrl() + "/slowniki/" + filter.getId() + prepareDictionaryUrl(filter), Dictionary.class);
            return Optional.of(responseEntity.getBody());
        } catch (HttpClientErrorException e) {
            return Optional.empty();
        }
    }

    public Optional<Dictionary> getDictionaryVoivodeship() {
        try {
            ResponseEntity<Dictionary> responseEntity = restTemplate.getForEntity(applicationConfig.getUrl() + "/slowniki/wojewodztwa", Dictionary.class);
            return Optional.of(responseEntity.getBody());
        } catch (HttpClientErrorException e) {
            return Optional.empty();
        }
    }

    public Optional<Dictionary> getDictionaryBrand() {
        try {
            ResponseEntity<Dictionary> responseEntity = restTemplate.getForEntity(applicationConfig.getUrl() + "/slowniki/marka", Dictionary.class);
            return Optional.of(responseEntity.getBody());
        } catch (HttpClientErrorException e) {
            return Optional.empty();
        }
    }

    public Optional<Dictionary> getDictionaryVehicleKind() {
        try {
            ResponseEntity<Dictionary> responseEntity = restTemplate.getForEntity(applicationConfig.getUrl() + "/slowniki/rodzaj-pojazdu", Dictionary.class);
            return Optional.of(responseEntity.getBody());
        } catch (HttpClientErrorException e) {
            return Optional.empty();
        }
    }

    public Optional<Dictionary> getDictionaryVehicleOrigin() {
        try {
            ResponseEntity<Dictionary> responseEntity = restTemplate.getForEntity(applicationConfig.getUrl() + "/slowniki/pochodzenie-pojazdu", Dictionary.class);
            return Optional.of(responseEntity.getBody());
        } catch (HttpClientErrorException e) {
            return Optional.empty();
        }
    }

    public Optional<Dictionary> getDictionaryVehicleProductionMethod() {
        try {
            ResponseEntity<Dictionary> responseEntity = restTemplate.getForEntity(applicationConfig.getUrl() + "/slowniki/sposob-produkcji", Dictionary.class);
            return Optional.of(responseEntity.getBody());
        } catch (HttpClientErrorException e) {
            return Optional.empty();
        }
    }

    public Optional<Dictionary> getDictionaryVehicleFuelKind() {
        try {
            ResponseEntity<Dictionary> responseEntity = restTemplate.getForEntity(applicationConfig.getUrl() + "/slowniki/rodzaj-paliwa", Dictionary.class);
            return Optional.of(responseEntity.getBody());
        } catch (HttpClientErrorException e) {
            return Optional.empty();
        }
    }

    Optional<Car> get(Long id) {
        try {
            ResponseEntity<Car> responseEntity = restTemplate.getForEntity(applicationConfig.getUrl() + "/pojazdy/" + id, Car.class);
            return Optional.ofNullable(responseEntity.getBody());
        } catch (HttpClientErrorException e) {
            return Optional.empty();
        }
    }

    Optional<Cars> find(CarsFilter filter) {
        try {
            ResponseEntity<Cars> responseEntity = restTemplate.getForEntity(applicationConfig.getUrl() + prepareCarsUrl(filter), Cars.class);
            return Optional.ofNullable(responseEntity.getBody());
        } catch (HttpClientErrorException e) {
            return Optional.empty();
        }
    }

    private String prepareDictionaryUrl(DictionariesFilter filter) {
        String query = "?";
        boolean wasFilter = false;
        if (filter.getLimit() != null) {
            query += "limit=" + filter.getLimit();
            wasFilter = true;
        }
        if (filter.getPage() != null) {
            query += (wasFilter ? "&" : "") + "page=" + filter.getPage();
        }
        return query;
    }

    private String prepareCarsUrl(CarsFilter filter) {
        String query = "/pojazdy?";
        boolean wasFilter = false;
        if (filter.getVoivodeshipCode() != null) {
            query += "wojewodztwo=" + filter.getVoivodeshipCode();
            wasFilter = true;
        }
        if (filter.getDateFrom() != null) {
            query += (wasFilter ? "&" : "") + "data-od=" + filter.getDateFrom().format(DateTimeFormatter.BASIC_ISO_DATE);
            wasFilter = true;
        }
        if (filter.getDateTo() != null) {
            query += (wasFilter ? "&" : "") + "data-do=" + filter.getDateTo().format(DateTimeFormatter.BASIC_ISO_DATE);
            wasFilter = true;
        }
        if (filter.getDateType() != null) {
            query += (wasFilter ? "&" : "") + "typ-daty=" + filter.getDateType();
            wasFilter = true;
        }
        if (filter.getVin() != null) {
            query += (wasFilter ? "&" : "") + "vin=" + filter.getVin();
            wasFilter = true;
        }
        if (filter.getRegistered() != null) {
            query += (wasFilter ? "&" : "") + "tylko-zarejestrowane=" + filter.getRegistered();
            wasFilter = true;
        }
        if (filter.getAreShownAllFields() != null) {
            query += (wasFilter ? "&" : "") + "pokaz-wszystkie-pola=" + filter.getAreShownAllFields();
            wasFilter = true;
        }
        if (filter.getFields() != null) {
            query += (wasFilter ? "&" : "") + "fields=" + Arrays.toString(filter.getFields()).replace("[", "").replace("]", "").replace(" ", "").trim();
            wasFilter = true;
        }
        if (filter.getLimit() != null) {
            query += (wasFilter ? "&" : "") + "limit=" + filter.getLimit();
            wasFilter = true;
        }
        if (filter.getPage() != null) {
            query += (wasFilter ? "&" : "") + "page=" + filter.getPage();
            wasFilter = true;
        }
        if (filter.getSort() != null) {
            query += (wasFilter ? "&" : "") + "sort=" + Arrays.toString(filter.getSort()).replace("[", "").replace("]", "").replace(" ", "").trim();
            wasFilter = true;
        }
        if (filter.getDateOfFirstRegistration() != null) {
            query += (wasFilter ? "&" : "") + "filter[data-pierwszej-rejestracji-w-kraju]=" + filter.getDateOfFirstRegistration();
            wasFilter = true;
        }
        if (filter.getBrand() != null) {
            query += (wasFilter ? "&" : "") + "filter[marka]=" + filter.getBrand();
            wasFilter = true;
        }
        if (filter.getModel() != null) {
            query += (wasFilter ? "&" : "") + "filter[model]=" + filter.getModel();
            wasFilter = true;
        }
        if (filter.getCarCategory() != null) {
            query += (wasFilter ? "&" : "") + "filter[kategoria-pojazdu]=" + filter.getCarCategory();
            wasFilter = true;
        }
        if (filter.getType() != null) {
            query += (wasFilter ? "&" : "") + "filter[typ]=" + filter.getType();
            wasFilter = true;
        }
        if (filter.getVariant() != null) {
            query += (wasFilter ? "&" : "") + "filter[wariant]=" + filter.getVariant();
            wasFilter = true;
        }
        if (filter.getVersion() != null) {
            query += (wasFilter ? "&" : "") + "filter[wersja]=" + filter.getVersion();
            wasFilter = true;
        }
        if (filter.getCarKind() != null) {
            query += (wasFilter ? "&" : "") + "filter[rodzaj-pojazdu]=" + filter.getCarKind();
            wasFilter = true;
        }
        if (filter.getCarSubKind() != null) {
            query += (wasFilter ? "&" : "") + "filter[podrodzaj-pojazdu]=" + filter.getCarSubKind();
            wasFilter = true;
        }
        if (filter.getCarPurpose() != null) {
            query += (wasFilter ? "&" : "") + "filter[przeznaczenie-pojazdu]=" + filter.getCarPurpose();
            wasFilter = true;
        }
        if (filter.getCarOrigin() != null) {
            query += (wasFilter ? "&" : "") + "filter[pochodzenie-pojazdu]=" + filter.getCarOrigin();
            wasFilter = true;
        }
        if (filter.getCarNamePlateKind() != null) {
            query += (wasFilter ? "&" : "") + "filter[rodzaj-tabliczki-znamionowej]=" + filter.getCarNamePlateKind();
            wasFilter = true;
        }
        if (filter.getCarProductionMethod() != null) {
            query += (wasFilter ? "&" : "") + "filter[sposob-produkcji]=" + filter.getCarProductionMethod();
            wasFilter = true;
        }
        if (filter.getCarProductionYear() != null) {
            query += (wasFilter ? "&" : "") + "filter[rok-produkcji]=" + filter.getCarProductionYear();
            wasFilter = true;
        }
        if (filter.getInitialRegistrationDateInCountry() != null) {
            query += (wasFilter ? "&" : "") + "filter[data-pierwszej-rejestracji-w-kraju]=" + filter.getInitialRegistrationDateInCountry();
            wasFilter = true;
        }
        if (filter.getLastRegistrationDateInCountry() != null) {
            query += (wasFilter ? "&" : "") + "filter[data-ostatniej-rejestracji-w-kraju]=" + filter.getLastRegistrationDateInCountry();
            wasFilter = true;
        }
        if (filter.getRegistrationDateAbroad() != null) {
            query += (wasFilter ? "&" : "") + "filter[data-rejestracji-za-granica]=" + filter.getRegistrationDateAbroad();
            wasFilter = true;
        }
        if (filter.getEngineDisplacement() != null) {
            query += (wasFilter ? "&" : "") + "filter[pojemnosc-skokowa-silnika]=" + filter.getEngineDisplacement();
            wasFilter = true;
        }
        if (filter.getMotorPowerToUnladenWeightRatioMotorcycle() != null) {
            query += (wasFilter ? "&" : "") + "filter[stosunek-mocy-silnika-do-masy-wlasnej-motocykle]=" + filter.getMotorPowerToUnladenWeightRatioMotorcycle();
            wasFilter = true;
        }
        if (filter.getEngineNetPower() != null) {
            query += (wasFilter ? "&" : "") + "filter[moc-netto-silnika]=" + filter.getEngineNetPower();
            wasFilter = true;
        }
        if (filter.getHybridEngineNetPower() != null) {
            query += (wasFilter ? "&" : "") + "filter[moc-netto-silnika-hybrydowego]=" + filter.getHybridEngineNetPower();
            wasFilter = true;
        }
        if (filter.getOwnMass() != null) {
            query += (wasFilter ? "&" : "") + "filter[masa-wlasna]=" + filter.getOwnMass();
            wasFilter = true;
        }
        if (filter.getMassOfVehicleReadyToRun() != null) {
            query += (wasFilter ? "&" : "") + "filter[masa-pojazdu-gotowego-do-jazdy]=" + filter.getMassOfVehicleReadyToRun();
            wasFilter = true;
        }
        if (filter.getPermissibleGrossWeight() != null) {
            query += (wasFilter ? "&" : "") + "filter[dopuszczalna-masa-calkowita]=" + filter.getPermissibleGrossWeight();
            wasFilter = true;
        }
        if (filter.getMaxTotalMass() != null) {
            query += (wasFilter ? "&" : "") + "filter[max-masa-calkowita]=" + filter.getMaxTotalMass();
            wasFilter = true;
        }
        if (filter.getPermissiblePackage() != null) {
            query += (wasFilter ? "&" : "") + "filter[dopuszczalna-ladownosc]=" + filter.getPermissiblePackage();
            wasFilter = true;
        }
        if (filter.getMaxPackage() != null) {
            query += (wasFilter ? "&" : "") + "filter[max-ladownosc]=" + filter.getMaxPackage();
            wasFilter = true;
        }
        if (filter.getPermissibleTotalMassOfVehiclesSet() != null) {
            query += (wasFilter ? "&" : "") + "filter[dopuszczalna-masa-calkowita-zespolu-pojazdow]=" + filter.getPermissibleTotalMassOfVehiclesSet();
            wasFilter = true;
        }
        if (filter.getAxlesNumber() != null) {
            query += (wasFilter ? "&" : "") + "filter[liczba-osi]=" + filter.getAxlesNumber();
            wasFilter = true;
        }
        if (filter.getPermissibleAxleLoad() != null) {
            query += (wasFilter ? "&" : "") + "filter[dopuszczalny-nacisk-osi]=" + filter.getPermissibleAxleLoad();
            wasFilter = true;
        }
        if (filter.getMaxAxleLoad() != null) {
            query += (wasFilter ? "&" : "") + "filter[dopuszczalny-nacisk-osi]=" + filter.getMaxAxleLoad();
            wasFilter = true;
        }
        if (filter.getMaxTotalWeightTrailerWithBrake() != null) {
            query += (wasFilter ? "&" : "") + "filter[max-masa-calkowita-przyczepy-z-hamulcem]=" + filter.getMaxTotalWeightTrailerWithBrake();
            wasFilter = true;
        }
        if (filter.getMaxTotalWeightTrailerWithoutBrake() != null) {
            query += (wasFilter ? "&" : "") + "filter[max-masa-calkowita-przyczepy-bez-hamulca]=" + filter.getMaxTotalWeightTrailerWithoutBrake();
            wasFilter = true;
        }
        if (filter.getPlacesNumberInTotal() != null) {
            query += (wasFilter ? "&" : "") + "filter[liczba-miejsc-ogolem]=" + filter.getPlacesNumberInTotal();
            wasFilter = true;
        }
        if (filter.getSeatsNumber() != null) {
            query += (wasFilter ? "&" : "") + "filter[liczba-miejsc-siedzacych]=" + filter.getSeatsNumber();
            wasFilter = true;
        }
        if (filter.getStandingPlacesNumber() != null) {
            query += (wasFilter ? "&" : "") + "filter[liczba-miejsc-stojacych]=" + filter.getStandingPlacesNumber();
            wasFilter = true;
        }
        if (filter.getFuelType() != null) {
            query += (wasFilter ? "&" : "") + "filter[rodzaj-paliwa]=" + filter.getFuelType();
            wasFilter = true;
        }
        if (filter.getFirstAlternativefuelType() != null) {
            query += (wasFilter ? "&" : "") + "filter[rodzaj-pierwszego-paliwa-alternatywnego]=" + filter.getFirstAlternativefuelType();
            wasFilter = true;
        }
        if (filter.getSecondAlternativefuelType() != null) {
            query += (wasFilter ? "&" : "") + "filter[rodzaj-drugiego-paliwa-alternatywnego]=" + filter.getSecondAlternativefuelType();
            wasFilter = true;
        }
        if (filter.getAverageFuelConsumption() != null) {
            query += (wasFilter ? "&" : "") + "filter[srednie-zuzycie-paliwa]=" + filter.getAverageFuelConsumption();
            wasFilter = true;
        }
        if (filter.getCo2EmissionLevel() != null) {
            query += (wasFilter ? "&" : "") + "filter[poziom-emisji-co2]=" + filter.getCo2EmissionLevel();
            wasFilter = true;
        }
        if (filter.getSuspensionType() != null) {
            query += (wasFilter ? "&" : "") + "filter[rodzaj-zawieszenia]=" + filter.getSuspensionType();
            wasFilter = true;
        }
        if (filter.getEquipmentAndTypeOfRadarDevice() != null) {
            query += (wasFilter ? "&" : "") + "filter[wyposazenie-i-rodzaj-urzadzenia-radarowego]=" + filter.getEquipmentAndTypeOfRadarDevice();
            wasFilter = true;
        }
        if (filter.getSteeringRightHandSide() != null) {
            query += (wasFilter ? "&" : "") + "filter[kierownica-po-prawej-stronie]=" + filter.getSteeringRightHandSide();
            wasFilter = true;
        }
        if (filter.getSteeringRightHandSideOrigin() != null) {
            query += (wasFilter ? "&" : "") + "filter[kierownica-po-prawej-stronie-pierwotnie]=" + filter.getSteeringRightHandSideOrigin();
            wasFilter = true;
        }
        if (filter.getCatalystAbsorber() != null) {
            query += (wasFilter ? "&" : "") + "filter[katalizator-pochlaniacz]=" + filter.getCatalystAbsorber();
            wasFilter = true;
        }
        if (filter.getManufacturerName() != null) {
            query += (wasFilter ? "&" : "") + "filter[nazwa-producenta]=" + filter.getManufacturerName();
            wasFilter = true;
        }
        if (filter.getCarTransportInstituteCode() != null) {
            query += (wasFilter ? "&" : "") + "filter[kod-instytutu-transaportu-samochodowego]=" + filter.getCarTransportInstituteCode();
            wasFilter = true;
        }
        if (filter.getWheelBaseSteeringOtherAxles() != null) {
            query += (wasFilter ? "&" : "") + "filter[rozstaw-kol-osi-kierowanej-pozostalych-osi]=" + filter.getWheelBaseSteeringOtherAxles();
            wasFilter = true;
        }
        if (filter.getMaxTrackWheel() != null) {
            query += (wasFilter ? "&" : "") + "filter[max-rozstaw-kol]=" + filter.getMaxTrackWheel();
            wasFilter = true;
        }
        if (filter.getAvgTrackWheel() != null) {
            query += (wasFilter ? "&" : "") + "filter[avg-rozstaw-kol]=" + filter.getAvgTrackWheel();
            wasFilter = true;
        }
        if (filter.getMinTrackWheel() != null) {
            query += (wasFilter ? "&" : "") + "filter[min-rozstaw-kol]=" + filter.getMinTrackWheel();
            wasFilter = true;
        }
        if (filter.getExhaustEmissionReduction() != null) {
            query += (wasFilter ? "&" : "") + "filter[redukcja-emisji-spalin]=" + filter.getExhaustEmissionReduction();
            wasFilter = true;
        }
        if (filter.getEncodingTypeSubtypeDestiny() != null) {
            query += (wasFilter ? "&" : "") + "filter[rodzaj-kodowania-rodzaj-podrodzaj-przeznaczenie]=" + filter.getEncodingTypeSubtypeDestiny();
            wasFilter = true;
        }
        if (filter.getCodeKindSubgenusDestiny() != null) {
            query += (wasFilter ? "&" : "") + "filter[kod-rodzaj-podrodzaj-przeznaczenie]=" + filter.getCodeKindSubgenusDestiny();
            wasFilter = true;
        }
        if (filter.getVehicleDeregistrationDate() != null) {
            query += (wasFilter ? "&" : "") + "filter[data-wyrejestrowania-pojazdu]=" + filter.getVehicleDeregistrationDate();
            wasFilter = true;
        }
        if (filter.getDataInputDate() != null) {
            query += (wasFilter ? "&" : "") + "filter[data-wprowadzenia-danych]=" + filter.getDataInputDate();
            wasFilter = true;
        }
        if (filter.getRegistrationVoivodship() != null) {
            query += (wasFilter ? "&" : "") + "filter[rejestracja-wojewodztwo]=" + filter.getRegistrationVoivodship();
            wasFilter = true;
        }
        if (filter.getRegistrationCommune() != null) {
            query += (wasFilter ? "&" : "") + "filter[rejestracja-gmina]=" + filter.getRegistrationCommune();
            wasFilter = true;
        }
        if (filter.getRegistrationDistrict() != null) {
            query += (wasFilter ? "&" : "") + "filter[rejestracja-powiat]=" + filter.getRegistrationDistrict();
            wasFilter = true;
        }
        if (filter.getOwnerVoivodship() != null) {
            query += (wasFilter ? "&" : "") + "filter[wlasciciel-wojewodztwo]=" + filter.getOwnerVoivodship();
            wasFilter = true;
        }
        if (filter.getOwnerDistrict() != null) {
            query += (wasFilter ? "&" : "") + "filter[wlasciciel-powiat]=" + filter.getOwnerDistrict();
            wasFilter = true;
        }
        if (filter.getOwnerCommune() != null) {
            query += (wasFilter ? "&" : "") + "filter[wlasciciel-gmina]=" + filter.getOwnerCommune();
            wasFilter = true;
        }
        if (filter.getOwnerVoivodshipCode() != null) {
            query += (wasFilter ? "&" : "") + "filter[wlasciciel-wojewodztwo-kod]=" + filter.getOwnerVoivodshipCode();
            wasFilter = true;
        }
        if (filter.getCo2EmissionsAlternativeFuel1() != null) {
            query += (wasFilter ? "&" : "") + "filter[poziom-emisji-co2-paliwo-alternatywne-1]=" + filter.getCo2EmissionsAlternativeFuel1();
            wasFilter = true;
        }
        return query;


    }
}























