package pl.com.mike.developer.domain.vin;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CarAttributes {
    @JsonProperty("marka")
    private String brand;
    @JsonProperty("kategoria-pojazdu")
    private String carCategory;
    @JsonProperty("typ")
    private String type;
    private String model;
    @JsonProperty("wariant")
    private String variant;
    @JsonProperty("wersja")
    private String version;
    @JsonProperty("rodzaj-pojazdu")
    private String carKind;
    @JsonProperty("podrodzaj-pojazdu")
    private String carSubKind;
    @JsonProperty("przeznaczenie-pojazdu")
    private String carPurpose;
    @JsonProperty("pochodzenie-pojazdu")
    private String carOrigin;
    @JsonProperty("rodzaj-tabliczki-znamionowej")
    private String carNamePlateKind;
    @JsonProperty("sposob-produkcji")
    private String carProductionMethod;
    @JsonProperty("rok-produkcji")
    private String carProductionYear;
    @JsonProperty("data-pierwszej-rejestracji-w-kraju")
    private String initialRegistrationDateInCountry;
    @JsonProperty("data-ostatniej-rejestracji-w-kraju")
    private String lastRegistrationDateInCountry;
    @JsonProperty("data-rejestracji-za-granica")
    private String registrationDateAbroad;
    @JsonProperty("pojemnosc-skokowa-silnika")
    private Double engineDisplacement;
    @JsonProperty("stosunek-mocy-silnika-do-masy-wlasnej-motocykle")
    private Double motorPowerToUnladenWeightRatioMotorcycle;
    @JsonProperty("moc-netto-silnika")
    private Double engineNetPower;
    @JsonProperty("moc-netto-silnika-hybrydowego")
    private Double hybridEngineNetPower;
    @JsonProperty("masa-wlasna")
    private Double ownMass;
    @JsonProperty("masa-pojazdu-gotowego-do-jazdy")
    private Double massOfVehicleReadyToRun;
    @JsonProperty("dopuszczalna-masa-calkowita")
    private Double permissibleGrossWeight;
    @JsonProperty("max-masa-calkowita")
    private Double maxTotalMass;
    @JsonProperty("dopuszczalna-ladownosc")
    private Double permissiblePackage;
    @JsonProperty("max-ladownosc")
    private Double maxPackage;
    @JsonProperty("dopuszczalna-masa-calkowita-zespolu-pojazdow")
    private Double permissibleTotalMassOfVehiclesSet;
    @JsonProperty("liczba-osi")
    private Double axlesNumber;
    @JsonProperty("dopuszczalny-nacisk-osi")
    private Double permissibleAxleLoad;
    @JsonProperty("maksymalny-nacisk-osi")
    private Double maxAxleLoad;
    @JsonProperty("max-masa-calkowita-przyczepy-z-hamulcem")
    private Double maxTotalWeightTrailerWithBrake;
    @JsonProperty("max-masa-calkowita-przyczepy-bez-hamulca")
    private Double maxTotalWeightTrailerWithoutBrake;
    @JsonProperty("liczba-miejsc-ogolem")
    private Integer placesNumberInTotal;
    @JsonProperty("liczba-miejsc-siedzacych")
    private Integer seatsNumber;
    @JsonProperty("liczba-miejsc-stojacych")
    private Integer standingPlacesNumber;
    @JsonProperty("rodzaj-paliwa")
    private String fuelType;
    @JsonProperty("rodzaj-pierwszego-paliwa-alternatywnego")
    private String firstAlternativefuelType;
    @JsonProperty("rodzaj-drugiego-paliwa-alternatywnego")
    private String secondAlternativefuelType;
    @JsonProperty("srednie-zuzycie-paliwa")
    private Double averageFuelConsumption;
    @JsonProperty("poziom-emisji-co2")
    private Double co2EmissionLevel;
    @JsonProperty("rodzaj-zawieszenia")
    private String suspensionType;
    @JsonProperty("wyposazenie-i-rodzaj-urzadzenia-radarowego")
    private String equipmentAndTypeOfRadarDevice;
    @JsonProperty("hak")
    private Boolean hook;
    @JsonProperty("kierownica-po-prawej-stronie")
    private Boolean steeringRightHandSide;
    @JsonProperty("kierownica-po-prawej-stronie-pierwotnie")
    private Boolean steeringRightHandSideOrigin;
    @JsonProperty("katalizator-pochlaniacz")
    private Boolean catalystAbsorber;
    @JsonProperty("nazwa-producenta")
    private String manufacturerName;
    @JsonProperty("kod-instytutu-transaportu-samochodowego")
    private String carTransportInstituteCode;
    @JsonProperty("rozstaw-kol-osi-kierowanej-pozostalych-osi")
    private Double wheelBaseSteeringOtherAxles;
    @JsonProperty("max-rozstaw-kol")
    private Double maxTrackWheel;
    @JsonProperty("avg-rozstaw-kol")
    private Double avgTrackWheel;
    @JsonProperty("min-rozstaw-kol")
    private Boolean minTrackWheel;
    @JsonProperty("redukcja-emisji-spalin")
    private Double exhaustEmissionReduction;
    @JsonProperty("data-pierwszej-rejestracji")
    private String dateOfFirstRegistration;
    @JsonProperty("rodzaj-kodowania-rodzaj-podrodzaj-przeznaczenie")
    private String encodingTypeSubtypeDestiny;
    @JsonProperty("kod-rodzaj-podrodzaj-przeznaczenie")
    private String codeKindSubgenusDestiny;
    @JsonProperty("data-wyrejestrowania-pojazdu")
    private String vehicleDeregistrationDate;
    @JsonProperty("przyczyna-wyrejestrowania-pojazdu")
    private String vehicleDeregistrationReason;
    @JsonProperty("data-wprowadzenia-danych")
    private String dataInputDate;
    @JsonProperty("rejestracja-wojewodztwo")
    private String registrationVoivodship;
    @JsonProperty("rejestracja-gmina")
    private String registrationCommune;
    @JsonProperty("rejestracja-powiat")
    private String registrationDistrict;
    @JsonProperty("wlasciciel-wojewodztwo")
    private String ownerVoivodship;
    @JsonProperty("wlasciciel-powiat")
    private String ownerDistrict;
    @JsonProperty("wlasciciel-gmina")
    private String ownerCommune;
    @JsonProperty("wlasciciel-wojewodztwo-kod")
    private String ownerVoivodshipCode;
    @JsonProperty("wojewodztwo-kod")
    private String voivodshipCode;
    @JsonProperty("poziom-emisji-co2-paliwo-alternatywne-1")
    private Double co2EmissionsAlternativeFuel1;

    public CarAttributes(String brand, String carCategory, String type, String model, String variant, String version, String carKind, String carSubKind, String carPurpose,
                         String carOrigin, String carNamePlateKind, String carProductionMethod, String carProductionYear, String initialRegistrationDateInCountry,
                         String lastRegistrationDateInCountry, String registrationDateAbroad, Double engineDisplacement, Double motorPowerToUnladenWeightRatioMotorcycle,
                         Double engineNetPower, Double hybridEngineNetPower, Double ownMass, Double massOfVehicleReadyToRun, Double permissibleGrossWeight,
                         Double maxTotalMass, Double permissiblePackage, Double maxPackage, Double permissibleTotalMassOfVehiclesSet,
                         Double axlesNumber, Double permissibleAxleLoad, Double maxAxleLoad, Double maxTotalWeightTrailerWithBrake, Double maxTotalWeightTrailerWithoutBrake,
                         Integer placesNumberInTotal, Integer seatsNumber, Integer standingPlacesNumber, String fuelType, String firstAlternativefuelType,
                         String secondAlternativefuelType, Double averageFuelConsumption, Double co2EmissionLevel, String suspensionType,
                         String equipmentAndTypeOfRadarDevice, Boolean hook, Boolean steeringRightHandSide, Boolean steeringRightHandSideOrigin,
                         Boolean catalystAbsorber, String manufacturerName, String carTransportInstituteCode, Double wheelBaseSteeringOtherAxles, Double maxTrackWheel,
                         Double avgTrackWheel, Boolean minTrackWheel, Double exhaustEmissionReduction, String dateOfFirstRegistration, String encodingTypeSubtypeDestiny,
                         String codeKindSubgenusDestiny, String vehicleDeregistrationDate, String vehicleDeregistrationReason, String dataInputDate,
                         String registrationVoivodship, String registrationCommune, String registrationDistrict, String ownerVoivodship, String ownerDistrict,
                         String ownerCommune, String ownerVoivodshipCode, String voivodshipCode, Double co2EmissionsAlternativeFuel1) {
        this.brand = brand;
        this.carCategory = carCategory;
        this.type = type;
        this.model = model;
        this.variant = variant;
        this.version = version;
        this.carKind = carKind;
        this.carSubKind = carSubKind;
        this.carPurpose = carPurpose;
        this.carOrigin = carOrigin;
        this.carNamePlateKind = carNamePlateKind;
        this.carProductionMethod = carProductionMethod;
        this.carProductionYear = carProductionYear;
        this.initialRegistrationDateInCountry = initialRegistrationDateInCountry;
        this.lastRegistrationDateInCountry = lastRegistrationDateInCountry;
        this.registrationDateAbroad = registrationDateAbroad;
        this.engineDisplacement = engineDisplacement;
        this.motorPowerToUnladenWeightRatioMotorcycle = motorPowerToUnladenWeightRatioMotorcycle;
        this.engineNetPower = engineNetPower;
        this.hybridEngineNetPower = hybridEngineNetPower;
        this.ownMass = ownMass;
        this.massOfVehicleReadyToRun = massOfVehicleReadyToRun;
        this.permissibleGrossWeight = permissibleGrossWeight;
        this.maxTotalMass = maxTotalMass;
        this.permissiblePackage = permissiblePackage;
        this.maxPackage = maxPackage;
        this.permissibleTotalMassOfVehiclesSet = permissibleTotalMassOfVehiclesSet;
        this.axlesNumber = axlesNumber;
        this.permissibleAxleLoad = permissibleAxleLoad;
        this.maxAxleLoad = maxAxleLoad;
        this.maxTotalWeightTrailerWithBrake = maxTotalWeightTrailerWithBrake;
        this.maxTotalWeightTrailerWithoutBrake = maxTotalWeightTrailerWithoutBrake;
        this.placesNumberInTotal = placesNumberInTotal;
        this.seatsNumber = seatsNumber;
        this.standingPlacesNumber = standingPlacesNumber;
        this.fuelType = fuelType;
        this.firstAlternativefuelType = firstAlternativefuelType;
        this.secondAlternativefuelType = secondAlternativefuelType;
        this.averageFuelConsumption = averageFuelConsumption;
        this.co2EmissionLevel = co2EmissionLevel;
        this.suspensionType = suspensionType;
        this.equipmentAndTypeOfRadarDevice = equipmentAndTypeOfRadarDevice;
        this.hook = hook;
        this.steeringRightHandSide = steeringRightHandSide;
        this.steeringRightHandSideOrigin = steeringRightHandSideOrigin;
        this.catalystAbsorber = catalystAbsorber;
        this.manufacturerName = manufacturerName;
        this.carTransportInstituteCode = carTransportInstituteCode;
        this.wheelBaseSteeringOtherAxles = wheelBaseSteeringOtherAxles;
        this.maxTrackWheel = maxTrackWheel;
        this.avgTrackWheel = avgTrackWheel;
        this.minTrackWheel = minTrackWheel;
        this.exhaustEmissionReduction = exhaustEmissionReduction;
        this.dateOfFirstRegistration = dateOfFirstRegistration;
        this.encodingTypeSubtypeDestiny = encodingTypeSubtypeDestiny;
        this.codeKindSubgenusDestiny = codeKindSubgenusDestiny;
        this.vehicleDeregistrationDate = vehicleDeregistrationDate;
        this.vehicleDeregistrationReason = vehicleDeregistrationReason;
        this.dataInputDate = dataInputDate;
        this.registrationVoivodship = registrationVoivodship;
        this.registrationCommune = registrationCommune;
        this.registrationDistrict = registrationDistrict;
        this.ownerVoivodship = ownerVoivodship;
        this.ownerDistrict = ownerDistrict;
        this.ownerCommune = ownerCommune;
        this.ownerVoivodshipCode = ownerVoivodshipCode;
        this.voivodshipCode = voivodshipCode;
        this.co2EmissionsAlternativeFuel1 = co2EmissionsAlternativeFuel1;
    }

    public CarAttributes() {
    }

    @JsonProperty("carBrand")
    public String getBrand() {
        return brand;
    }

    public String getCarCategory() {
        return carCategory;
    }

    public String getType() {
        return type;
    }

    public String getModel() {
        return model;
    }

    public String getVariant() {
        return variant;
    }

    public String getVersion() {
        return version;
    }

    @JsonProperty("carKind")
    public String getCarKind() {
        return carKind;
    }

    public String getCarSubKind() {
        return carSubKind;
    }

    public String getCarPurpose() {
        return carPurpose;
    }

    public String getCarOrigin() {
        return carOrigin;
    }

    public String getCarNamePlateKind() {
        return carNamePlateKind;
    }

    public String getCarProductionMethod() {
        return carProductionMethod;
    }

    public String getCarProductionYear() {
        return carProductionYear;
    }

    @JsonProperty("initialRegistrationDateInCountry")
    public String getInitialRegistrationDateInCountry() {
        return initialRegistrationDateInCountry;
    }

    public String getLastRegistrationDateInCountry() {
        return lastRegistrationDateInCountry;
    }

    public String getRegistrationDateAbroad() {
        return registrationDateAbroad;
    }

    public Double getEngineDisplacement() {
        return engineDisplacement;
    }

    public Double getMotorPowerToUnladenWeightRatioMotorcycle() {
        return motorPowerToUnladenWeightRatioMotorcycle;
    }

    public Double getEngineNetPower() {
        return engineNetPower;
    }

    public Double getHybridEngineNetPower() {
        return hybridEngineNetPower;
    }

    public Double getOwnMass() {
        return ownMass;
    }

    public Double getMassOfVehicleReadyToRun() {
        return massOfVehicleReadyToRun;
    }

    public Double getPermissibleGrossWeight() {
        return permissibleGrossWeight;
    }

    public Double getMaxTotalMass() {
        return maxTotalMass;
    }

    public Double getPermissiblePackage() {
        return permissiblePackage;
    }

    public Double getMaxPackage() {
        return maxPackage;
    }

    public Double getPermissibleTotalMassOfVehiclesSet() {
        return permissibleTotalMassOfVehiclesSet;
    }

    public Double getAxlesNumber() {
        return axlesNumber;
    }

    public Double getPermissibleAxleLoad() {
        return permissibleAxleLoad;
    }

    public Double getMaxAxleLoad() {
        return maxAxleLoad;
    }

    public Double getMaxTotalWeightTrailerWithBrake() {
        return maxTotalWeightTrailerWithBrake;
    }

    public Double getMaxTotalWeightTrailerWithoutBrake() {
        return maxTotalWeightTrailerWithoutBrake;
    }

    public Integer getPlacesNumberInTotal() {
        return placesNumberInTotal;
    }

    public Integer getSeatsNumber() {
        return seatsNumber;
    }

    public Integer getStandingPlacesNumber() {
        return standingPlacesNumber;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getFirstAlternativefuelType() {
        return firstAlternativefuelType;
    }

    public String getSecondAlternativefuelType() {
        return secondAlternativefuelType;
    }

    public Double getAverageFuelConsumption() {
        return averageFuelConsumption;
    }

    public Double getCo2EmissionLevel() {
        return co2EmissionLevel;
    }

    public String getSuspensionType() {
        return suspensionType;
    }

    public String getEquipmentAndTypeOfRadarDevice() {
        return equipmentAndTypeOfRadarDevice;
    }

    public Boolean getHook() {
        return hook;
    }

    public Boolean getSteeringRightHandSide() {
        return steeringRightHandSide;
    }

    public Boolean getSteeringRightHandSideOrigin() {
        return steeringRightHandSideOrigin;
    }

    public Boolean getCatalystAbsorber() {
        return catalystAbsorber;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public String getCarTransportInstituteCode() {
        return carTransportInstituteCode;
    }

    public Double getWheelBaseSteeringOtherAxles() {
        return wheelBaseSteeringOtherAxles;
    }

    public Double getMaxTrackWheel() {
        return maxTrackWheel;
    }

    public Double getAvgTrackWheel() {
        return avgTrackWheel;
    }

    public Boolean getMinTrackWheel() {
        return minTrackWheel;
    }

    public Double getExhaustEmissionReduction() {
        return exhaustEmissionReduction;
    }

    public String getDateOfFirstRegistration() {
        return dateOfFirstRegistration;
    }

    public String getEncodingTypeSubtypeDestiny() {
        return encodingTypeSubtypeDestiny;
    }

    public String getCodeKindSubgenusDestiny() {
        return codeKindSubgenusDestiny;
    }

    public String getVehicleDeregistrationDate() {
        return vehicleDeregistrationDate;
    }

    public String getVehicleDeregistrationReason() {
        return vehicleDeregistrationReason;
    }

    public String getDataInputDate() {
        return dataInputDate;
    }

    public String getRegistrationVoivodship() {
        return registrationVoivodship;
    }

    public String getRegistrationCommune() {
        return registrationCommune;
    }

    public String getRegistrationDistrict() {
        return registrationDistrict;
    }

    public String getOwnerVoivodship() {
        return ownerVoivodship;
    }

    public String getOwnerDistrict() {
        return ownerDistrict;
    }

    public String getOwnerCommune() {
        return ownerCommune;
    }

    public String getOwnerVoivodshipCode() {
        return ownerVoivodshipCode;
    }

    public String getVoivodshipCode() {
        return voivodshipCode;
    }

    public Double getCo2EmissionsAlternativeFuel1() {
        return co2EmissionsAlternativeFuel1;
    }
}
