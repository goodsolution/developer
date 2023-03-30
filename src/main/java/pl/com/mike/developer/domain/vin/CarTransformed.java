package pl.com.mike.developer.domain.vin;

public class CarTransformed {
    private Long id;
    private String type;
    private String brand;
    private String model;
    private String carKind;
    private String dateOfFirstRegistration;
    private String vin;
    private String carCategory;
    private String variant;
    private String version;
    private String carSubKind;
    private String carPurpose;
    private String carOrigin;
    private String carNamePlateKind;
    private String carProductionMethod;
    private String carProductionYear;
    private String initialRegistrationDateInCountry;
    private String lastRegistrationDateInCountry;
    private String registrationDateAbroad;
    private Double engineDisplacement;
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
    private Boolean hook;
    private Boolean steeringRightHandSide;
    private Boolean steeringRightHandSideOrigin;
    private Boolean catalystAbsorber;
    private String manufacturerName;
    private String carTransportInstituteCode;
    private Double wheelBaseSteeringOtherAxles;
    private Double maxTrackWheel;
    private Double avgTrackWheel;
    private Boolean minTrackWheel;
    private Double exhaustEmissionReduction;
    private String encodingTypeSubtypeDestiny;
    private String codeKindSubgenusDestiny;
    private String vehicleDeregistrationDate;
    private String vehicleDeregistrationReason;
    private String dataInputDate;
    private String registrationVoivodship;
    private String registrationCommune;
    private String registrationDistrict;
    private String ownerVoivodship;
    private String ownerDistrict;
    private String ownerCommune;
    private String ownerVoivodshipCode;
    private String voivodshipCode;
    private Double co2EmissionsAlternativeFuel1;
    private Integer count;
    private Integer page;
    private Integer limit;

    public CarTransformed(Long id, String type, String brand, String model, String carKind, String dateOfFirstRegistration, String vin, String carCategory, String variant, String version, String carSubKind, String carPurpose, String carOrigin, String carNamePlateKind, String carProductionMethod, String carProductionYear, String initialRegistrationDateInCountry, String lastRegistrationDateInCountry, String registrationDateAbroad, Double engineDisplacement, Double motorPowerToUnladenWeightRatioMotorcycle, Double engineNetPower, Double hybridEngineNetPower, Double ownMass, Double massOfVehicleReadyToRun, Double permissibleGrossWeight, Double maxTotalMass, Double permissiblePackage, Double maxPackage, Double permissibleTotalMassOfVehiclesSet, Double axlesNumber, Double permissibleAxleLoad, Double maxAxleLoad, Double maxTotalWeightTrailerWithBrake, Double maxTotalWeightTrailerWithoutBrake, Integer placesNumberInTotal, Integer seatsNumber, Integer standingPlacesNumber, String fuelType, String firstAlternativefuelType, String secondAlternativefuelType, Double averageFuelConsumption, Double co2EmissionLevel, String suspensionType, String equipmentAndTypeOfRadarDevice, Boolean hook, Boolean steeringRightHandSide, Boolean steeringRightHandSideOrigin, Boolean catalystAbsorber, String manufacturerName, String carTransportInstituteCode, Double wheelBaseSteeringOtherAxles, Double maxTrackWheel, Double avgTrackWheel, Boolean minTrackWheel, Double exhaustEmissionReduction, String encodingTypeSubtypeDestiny, String codeKindSubgenusDestiny, String vehicleDeregistrationDate, String vehicleDeregistrationReason, String dataInputDate, String registrationVoivodship, String registrationCommune, String registrationDistrict, String ownerVoivodship, String ownerDistrict, String ownerCommune, String ownerVoivodshipCode, String voivodshipCode, Double co2EmissionsAlternativeFuel1) {
        this.id = id;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.carKind = carKind;
        this.dateOfFirstRegistration = dateOfFirstRegistration;
        this.vin = vin;
        this.carCategory = carCategory;
        this.variant = variant;
        this.version = version;
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

    public CarTransformed(Long id, String type, String brand, String model, String carKind, String dateOfFirstRegistration, String vin) {
        this.id = id;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.carKind = carKind;
        this.dateOfFirstRegistration = dateOfFirstRegistration;
        this.vin = vin;
    }

    public CarTransformed(CarData carData, CarAttributes carAttributes, String vin, CarMeta carMeta) {
        this.id = carData.getId();
        this.type = carData.getType();
        this.brand = carAttributes.getBrand();
        this.model = carAttributes.getModel();
        this.carKind = carAttributes.getCarKind();
        this.dateOfFirstRegistration = carAttributes.getInitialRegistrationDateInCountry();
        this.vin = vin;
        this.carCategory = carAttributes.getCarCategory();
        this.variant = carAttributes.getVariant();
        this.version = carAttributes.getVersion();
        this.carSubKind = carAttributes.getCarSubKind();
        this.carPurpose = carAttributes.getCarPurpose();
        this.carOrigin = carAttributes.getCarOrigin();
        this.carNamePlateKind = carAttributes.getCarNamePlateKind();
        this.carProductionMethod = carAttributes.getCarProductionMethod();
        this.carProductionYear = carAttributes.getCarProductionYear();
        this.initialRegistrationDateInCountry = carAttributes.getInitialRegistrationDateInCountry();
        this.lastRegistrationDateInCountry = carAttributes.getLastRegistrationDateInCountry();
        this.registrationDateAbroad = carAttributes.getRegistrationDateAbroad();
        this.engineDisplacement = carAttributes.getEngineDisplacement();
        this.motorPowerToUnladenWeightRatioMotorcycle = carAttributes.getMotorPowerToUnladenWeightRatioMotorcycle();
        this.engineNetPower = carAttributes.getEngineNetPower();
        this.hybridEngineNetPower = carAttributes.getHybridEngineNetPower();
        this.ownMass = carAttributes.getOwnMass();
        this.massOfVehicleReadyToRun = carAttributes.getMassOfVehicleReadyToRun();
        this.permissibleGrossWeight = carAttributes.getPermissibleGrossWeight();
        this.maxTotalMass = carAttributes.getMaxTotalMass();
        this.permissiblePackage = carAttributes.getPermissiblePackage();
        this.maxPackage = carAttributes.getMaxPackage();
        this.permissibleTotalMassOfVehiclesSet = carAttributes.getPermissibleTotalMassOfVehiclesSet();
        this.axlesNumber = carAttributes.getAxlesNumber();
        this.permissibleAxleLoad = carAttributes.getPermissibleAxleLoad();
        this.maxAxleLoad = carAttributes.getMaxAxleLoad();
        this.maxTotalWeightTrailerWithBrake = carAttributes.getMaxTotalWeightTrailerWithBrake();
        this.maxTotalWeightTrailerWithoutBrake = carAttributes.getMaxTotalWeightTrailerWithoutBrake();
        this.placesNumberInTotal = carAttributes.getPlacesNumberInTotal();
        this.seatsNumber = carAttributes.getSeatsNumber();
        this.standingPlacesNumber = carAttributes.getStandingPlacesNumber();
        this.fuelType = carAttributes.getFuelType();
        this.firstAlternativefuelType = carAttributes.getFirstAlternativefuelType();
        this.secondAlternativefuelType = carAttributes.getSecondAlternativefuelType();
        this.averageFuelConsumption = carAttributes.getAverageFuelConsumption();
        this.co2EmissionLevel = carAttributes.getCo2EmissionLevel();
        this.suspensionType = carAttributes.getSuspensionType();
        this.equipmentAndTypeOfRadarDevice = carAttributes.getEquipmentAndTypeOfRadarDevice();
        this.hook = carAttributes.getHook();
        this.steeringRightHandSide = carAttributes.getSteeringRightHandSide();
        this.steeringRightHandSideOrigin = carAttributes.getSteeringRightHandSideOrigin();
        this.catalystAbsorber = carAttributes.getCatalystAbsorber();
        this.manufacturerName = carAttributes.getManufacturerName();
        this.carTransportInstituteCode = carAttributes.getCarTransportInstituteCode();
        this.wheelBaseSteeringOtherAxles = carAttributes.getWheelBaseSteeringOtherAxles();
        this.maxTrackWheel = carAttributes.getMaxTrackWheel();
        this.avgTrackWheel = carAttributes.getAvgTrackWheel();
        this.minTrackWheel = carAttributes.getMinTrackWheel();
        this.exhaustEmissionReduction = carAttributes.getExhaustEmissionReduction();
        this.encodingTypeSubtypeDestiny = carAttributes.getEncodingTypeSubtypeDestiny();
        this.codeKindSubgenusDestiny = carAttributes.getCodeKindSubgenusDestiny();
        this.vehicleDeregistrationDate = carAttributes.getVehicleDeregistrationDate();
        this.vehicleDeregistrationReason = carAttributes.getVehicleDeregistrationReason();
        this.dataInputDate = carAttributes.getDataInputDate();
        this.registrationVoivodship = carAttributes.getRegistrationVoivodship();
        this.registrationCommune = carAttributes.getRegistrationCommune();
        this.registrationDistrict = carAttributes.getRegistrationDistrict();
        this.ownerVoivodship = carAttributes.getOwnerVoivodship();
        this.ownerDistrict = carAttributes.getOwnerDistrict();
        this.ownerCommune = carAttributes.getOwnerCommune();
        this.ownerVoivodshipCode = carAttributes.getOwnerVoivodshipCode();
        this.voivodshipCode = carAttributes.getVoivodshipCode();
        this.co2EmissionsAlternativeFuel1 = carAttributes.getCo2EmissionsAlternativeFuel1();
        this.count = carMeta.getCount();
        this.page = carMeta.getPage();
        this.limit = carMeta.getLimit();
    }

    public CarTransformed(Long id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getLimit() {
        return limit;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getCarKind() {
        return carKind;
    }

    public String getDateOfFirstRegistration() {
        return dateOfFirstRegistration;
    }

    public String getVin() {
        return vin;
    }

    public String getCarCategory() {
        return carCategory;
    }

    public String getVariant() {
        return variant;
    }

    public String getVersion() {
        return version;
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

    public UpdateVehicle toUpdateVehicle() {
        return new UpdateVehicle(getId(), getType(), getBrand(), getModel(), getCarKind(), getDateOfFirstRegistration(), getVin(), getCarCategory(),
                getVariant(), getVersion(), getCarSubKind(), getCarPurpose(), getCarOrigin(), getCarNamePlateKind(), getCarProductionMethod(), getCarProductionYear(),
                getInitialRegistrationDateInCountry(), getLastRegistrationDateInCountry(), getRegistrationDateAbroad(), getEngineDisplacement(), getMotorPowerToUnladenWeightRatioMotorcycle(),
                getEngineNetPower(), getHybridEngineNetPower(), getOwnMass(), getMassOfVehicleReadyToRun(), getPermissibleGrossWeight(), getMaxTotalMass(),
                getPermissiblePackage(), getMaxPackage(), getPermissibleTotalMassOfVehiclesSet(), getAxlesNumber(), getPermissibleAxleLoad(), getMaxAxleLoad(),
                getMaxTotalWeightTrailerWithBrake(), getMaxTotalWeightTrailerWithoutBrake(), getPlacesNumberInTotal(), getSeatsNumber(), getStandingPlacesNumber(),
                getFuelType(), getFirstAlternativefuelType(), getSecondAlternativefuelType(), getAverageFuelConsumption(), getCo2EmissionLevel(), getSuspensionType(),
                getEquipmentAndTypeOfRadarDevice(), getHook(), getSteeringRightHandSide(), getSteeringRightHandSideOrigin(), getCatalystAbsorber(), getManufacturerName(),
                getCarTransportInstituteCode(), getWheelBaseSteeringOtherAxles(), getMaxTrackWheel(), getAvgTrackWheel(), getMinTrackWheel(), getExhaustEmissionReduction(),
                getEncodingTypeSubtypeDestiny(), getCodeKindSubgenusDestiny(), getVehicleDeregistrationDate(), getVehicleDeregistrationReason(), getDataInputDate(),
                getRegistrationVoivodship(), getRegistrationCommune(), getRegistrationDistrict(), getOwnerVoivodship(), getOwnerDistrict(), getOwnerCommune(),
                getOwnerVoivodshipCode(), getVoivodshipCode(), getCo2EmissionsAlternativeFuel1()
        );
    }

    public UpdateVehicles toUpdateVehicles() {
        return new UpdateVehicles(getId(), getType(), getBrand(), getModel(), getCarKind(), getDateOfFirstRegistration(), getVin(), getCarCategory(),
                getVariant(), getVersion(), getCarSubKind(), getCarPurpose(), getCarOrigin(), getCarNamePlateKind(), getCarProductionMethod(), getCarProductionYear(),
                getInitialRegistrationDateInCountry(), getLastRegistrationDateInCountry(), getRegistrationDateAbroad(), getEngineDisplacement(), getMotorPowerToUnladenWeightRatioMotorcycle(),
                getEngineNetPower(), getHybridEngineNetPower(), getOwnMass(), getMassOfVehicleReadyToRun(), getPermissibleGrossWeight(), getMaxTotalMass(),
                getPermissiblePackage(), getMaxPackage(), getPermissibleTotalMassOfVehiclesSet(), getAxlesNumber(), getPermissibleAxleLoad(), getMaxAxleLoad(),
                getMaxTotalWeightTrailerWithBrake(), getMaxTotalWeightTrailerWithoutBrake(), getPlacesNumberInTotal(), getSeatsNumber(), getStandingPlacesNumber(),
                getFuelType(), getFirstAlternativefuelType(), getSecondAlternativefuelType(), getAverageFuelConsumption(), getCo2EmissionLevel(), getSuspensionType(),
                getEquipmentAndTypeOfRadarDevice(), getHook(), getSteeringRightHandSide(), getSteeringRightHandSideOrigin(), getCatalystAbsorber(), getManufacturerName(),
                getCarTransportInstituteCode(), getWheelBaseSteeringOtherAxles(), getMaxTrackWheel(), getAvgTrackWheel(), getMinTrackWheel(), getExhaustEmissionReduction(),
                getEncodingTypeSubtypeDestiny(), getCodeKindSubgenusDestiny(), getVehicleDeregistrationDate(), getVehicleDeregistrationReason(), getDataInputDate(),
                getRegistrationVoivodship(), getRegistrationCommune(), getRegistrationDistrict(), getOwnerVoivodship(), getOwnerDistrict(), getOwnerCommune(),
                getOwnerVoivodshipCode(), getVoivodshipCode(), getCo2EmissionsAlternativeFuel1()
        );
    }

}
