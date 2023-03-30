package pl.com.mike.developer.domain.vin;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CarsFilter {
    private Long pageSize;
    private Long pages;
    private Long cepikId;
    private LocalDateTime lastRefreshDate;
    private String voivodeshipCode;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String dateType;
    private String vin;
    private Boolean isRegistered;
    private Boolean areShownAllFields;
    private String[] fields;
    private Integer limit;
    private Integer page;
    private Integer count;
    private String[] sort;
    private LocalDate dateOfFirstRegistration;
    private String brand;
    private String model;
    private String carCategory;
    private String type;
    private String variant;
    private String version;
    private String carKind;
    private String carSubKind;
    private String carPurpose;
    private String carOrigin;
    private String carNamePlateKind;
    private String carProductionMethod;
    private String carProductionYear;
    private LocalDate initialRegistrationDateInCountry;
    private LocalDate lastRegistrationDateInCountry;
    private LocalDate registrationDateAbroad;
    private Integer engineDisplacement;
    private Double motorPowerToUnladenWeightRatioMotorcycle;
    private Double engineNetPower;
    private Double hybridEngineNetPower;
    private Double ownMass;
    private Double massOfVehicleReadyToRun;
    private Double permissibleGrossWeight;
    private Double maxTotalMass;
    private Double permissiblePackage;
    private Double maxPackage;
    private Double permissibleTotalMassOfVehiclesSet;
    private Double axlesNumber;
    private Double permissibleAxleLoad;
    private Double maxAxleLoad;
    private Double maxTotalWeightTrailerWithBrake;
    private Double maxTotalWeightTrailerWithoutBrake;
    private Integer placesNumberInTotal;
    private Integer seatsNumber;
    private Integer standingPlacesNumber;
    private String fuelType;
    private String firstAlternativefuelType;
    private String secondAlternativefuelType;
    private Double averageFuelConsumption;
    private Double co2EmissionLevel;
    private String suspensionType;
    private String equipmentAndTypeOfRadarDevice;
    private Boolean steeringRightHandSide;
    private Boolean steeringRightHandSideOrigin;
    private Boolean catalystAbsorber;
    private String manufacturerName;
    private String carTransportInstituteCode;
    private Double wheelBaseSteeringOtherAxles;
    private Double maxTrackWheel;
    private Double avgTrackWheel;
    private Double minTrackWheel;
    private String exhaustEmissionReduction;
    private String encodingTypeSubtypeDestiny;
    private String codeKindSubgenusDestiny;
    private LocalDate vehicleDeregistrationDate;
    private String vehicleDeregistrationReason;
    private LocalDate dataInputDate;
    private String registrationVoivodship;
    private String registrationCommune;
    private String registrationDistrict;
    private String ownerVoivodship;
    private String ownerDistrict;
    private String ownerCommune;
    private String ownerVoivodshipCode;

    public Long getPageSize() {
        return pageSize;
    }

    public Long getPages() {
        return pages;
    }

    private Double co2EmissionsAlternativeFuel1;

    public CarsFilter(Long pages, Long pageSize, Long cepikId, String voivodeshipCode, LocalDate dateFrom, LocalDate dateTo, String dateType, String vin, Boolean isRegistered, Boolean areShownAllFields, String[] fields, Integer limit, Integer page, String[] sort, LocalDate dateOfFirstRegistration, String brand, String model, String carCategory, String type, String variant, String version, String carKind, String carSubKind, String carPurpose, String carOrigin, String carNamePlateKind, String carProductionMethod, String carProductionYear, LocalDate initialRegistrationDateInCountry, LocalDate lastRegistrationDateInCountry, LocalDate registrationDateAbroad, Integer engineDisplacement, Double motorPowerToUnladenWeightRatioMotorcycle, Double engineNetPower, Double hybridEngineNetPower, Double ownMass, Double massOfVehicleReadyToRun, Double permissibleGrossWeight, Double maxTotalMass, Double permissiblePackage, Double maxPackage, Double permissibleTotalMassOfVehiclesSet, Double axlesNumber, Double permissibleAxleLoad, Double maxAxleLoad, Double maxTotalWeightTrailerWithBrake, Double maxTotalWeightTrailerWithoutBrake, Integer placesNumberInTotal, Integer seatsNumber, Integer standingPlacesNumber, String fuelType, String firstAlternativefuelType, String secondAlternativefuelType, Double averageFuelConsumption, Double co2EmissionLevel, String suspensionType, String equipmentAndTypeOfRadarDevice, Boolean steeringRightHandSide, Boolean steeringRightHandSideOrigin, Boolean catalystAbsorber, String manufacturerName, String carTransportInstituteCode, Double wheelBaseSteeringOtherAxles, Double maxTrackWheel, Double avgTrackWheel, Double minTrackWheel, String exhaustEmissionReduction, String encodingTypeSubtypeDestiny, String codeKindSubgenusDestiny, LocalDate vehicleDeregistrationDate, String vehicleDeregistrationReason, LocalDate dataInputDate, String registrationVoivodship, String registrationCommune, String registrationDistrict, String ownerVoivodship, String ownerDistrict, String ownerCommune, String ownerVoivodshipCode, Double co2EmissionsAlternativeFuel1) {
        this.pages = pages;
        this.pageSize = pageSize;
        this.cepikId = cepikId;
        this.voivodeshipCode = voivodeshipCode;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.dateType = dateType;
        this.vin = vin;
        this.isRegistered = isRegistered;
        this.areShownAllFields = areShownAllFields;
        this.fields = fields;
        this.limit = limit;
        this.page = page;
        this.sort = sort;
        this.dateOfFirstRegistration = dateOfFirstRegistration;
        this.brand = brand;
        this.model = model;
        this.carCategory = carCategory;
        this.type = type;
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
        this.co2EmissionsAlternativeFuel1 = co2EmissionsAlternativeFuel1;
    }

    public CarsFilter(Long cepikId) {
        this.cepikId = cepikId;
    }

    public CarsFilter(String voivodeshipCode, LocalDate dateFrom) {
        this.voivodeshipCode = voivodeshipCode;
        this.dateFrom = dateFrom;
    }

    public CarsFilter(String voivodeshipCode, LocalDate dateFrom, LocalDate dateTo) {
        this.voivodeshipCode = voivodeshipCode;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public LocalDateTime getLastRefreshDate() {
        return lastRefreshDate;
    }

    public String getVoivodeshipCode() {
        return voivodeshipCode;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public String getDateType() {
        return dateType;
    }

    public Boolean getRegistered() {
        return isRegistered;
    }

    public Boolean getAreShownAllFields() {
        return areShownAllFields;
    }

    public String[] getFields() {
        return fields;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getCount() {
        return count;
    }

    public String[] getSort() {
        return sort;
    }

    public LocalDate getDateOfFirstRegistration() {
        return dateOfFirstRegistration;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getCarCategory() {
        return carCategory;
    }

    public String getType() {
        return type;
    }

    public String getVariant() {
        return variant;
    }

    public String getVersion() {
        return version;
    }

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

    public LocalDate getInitialRegistrationDateInCountry() {
        return initialRegistrationDateInCountry;
    }

    public LocalDate getLastRegistrationDateInCountry() {
        return lastRegistrationDateInCountry;
    }

    public LocalDate getRegistrationDateAbroad() {
        return registrationDateAbroad;
    }

    public Integer getEngineDisplacement() {
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

    public Double getMinTrackWheel() {
        return minTrackWheel;
    }

    public String getExhaustEmissionReduction() {
        return exhaustEmissionReduction;
    }

    public String getEncodingTypeSubtypeDestiny() {
        return encodingTypeSubtypeDestiny;
    }

    public String getCodeKindSubgenusDestiny() {
        return codeKindSubgenusDestiny;
    }

    public LocalDate getVehicleDeregistrationDate() {
        return vehicleDeregistrationDate;
    }

    public String getVehicleDeregistrationReason() {
        return vehicleDeregistrationReason;
    }

    public LocalDate getDataInputDate() {
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

    public Double getCo2EmissionsAlternativeFuel1() {
        return co2EmissionsAlternativeFuel1;
    }

    public String getVin() {
        return vin;
    }

    public Long getCepikId() {
        return cepikId;
    }
}