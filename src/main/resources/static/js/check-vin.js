var currentPage = 1;

$(document).ready(function () {
    clearCreateModal();

    document.addEventListener('keyup', (e) => {
        if (e.code === "Enter") {
            find();
        }
    })

});

function goToNextPage() {
    currentPage++;
    performPageChange();
}

function goToPreviousPage() {
    currentPage--;
    performPageChange();
}

function performPageChange() {
    $("#current-page").text(currentPage);

    if (currentPage === 1) {
        $("#next-page-button").addClass("disabled");
    } else {
        $("#next-page-button").removeClass("disabled");
    }
    $("#records").empty();
    find();
}

function refresh() {
    // trace("button.search",$("#search").val());
    find();
}

function find() {
    $.ajax({
        url: "/api/vin/cars/" + currentPage + prepareUrl(),
        type: "GET",
        dataType: "json",
        contentType: "application/json"
    })
        .done(function (data) {
            $("#results").empty();
            $('#no-data-found').empty();
            fillResults(data.cars);
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        });
}

function fillResults(cars) {
    // if (cars.isEmptyObject()){
    //     showNoDataInfo()
    // }
    cars.forEach(function (car) {
        render(car);
    });

}

function render(car) {
    $('#results').append(
        "<tr>" +
        "<td class='align-middle'>" + car.id + "</td>" +
        "<td class='align-middle'>" + car.type + "</td>" +
        "<td class='align-middle'>" + car.vin + "</td>" +
        "<td class='align-middle'>" + car.brand + "</td>" +
        "<td class='align-middle'>" + car.model + "</td>" +
        "<td class='align-middle'>" + car.carKind + "</td>" +
        "<td class='align-middle'>" + car.dateOfFirstRegistration + "</td>" +
        "<td class='align-middle'>" + prepareDetailsButton(car.id) + "</td>" +
        "</tr>"
    );
}

function prepareDetailsButton(id) {
    return '<button type="button" class="btn btn-primary" onclick="goToDetailsPage(' + id + ')">Details</button>';
}

function goToDetailsPage(id) {
    window.location.href = "/vehicle/" + id;
}

function showNoDataInfo() {
    $('#no-data-found-info').append(
        "<div class='alert alert-info'>" +
        "<strong>Sorry, na data found, please try again!</strong>" +
        "<div/>"
    );
}

function hideNoDataInfo() {
    $('#no-data-found-info').hide();
}

function clearCreateModal() {
    $("#voivodeship").val("");
    $("#brand").val("");
    $("#carKind").val("");
    $("#fuelType").val("");
    $("#carOrigin").val("");
    $("#carProductionMethod").val("");
}

function prepareUrl() {
    var url = "?";
    var voivodeship = $("#voivodeship").find(":selected").val();
    if (voivodeship !== "") {
        url += "&voivodeship_code=" + voivodeship;
    }
    var date_from = $("#date-from").val();
    if (date_from !== "") {
        url += "&date_from=" + date_from;
    }
    var date_to = $("#date-to").val();
    if (date_to !== "") {
        url += "&date_to=" + date_to;
    }
    var date_type = $("#date-type").val();
    if (date_type !== "") {
        url += "&date_type=" + date_type;
    }
    var vin = $("#vin").val();
    if (vin !== "") {
        url += "&vin=" + vin;
    }
    var brand = $("#brand").find(":selected").val();
    if (brand !== undefined) {
        url += "&brand=" + brand;
    }
    var model = $("#model").val();
    if (model !== "") {
        url += "&model=" + model;
    }
    var carCategory = $("#carCategory").val();
    if (carCategory !== "") {
        url += "&carCategory=" + carCategory;
    }
    var carKind = $("#carKind").find(":selected").val();
    if (carKind !== undefined) {
        url += "&carKind=" + carKind;
    }
    var type = $("#type").val();
    if (type !== "") {
        url += "&type=" + type;
    }
    var version = $("#version").val();
    if (version !== "") {
        url += "&version=" + version;
    }
    var variant = $("#variant").val();
    if (variant !== "") {
        url += "&variant=" + variant;
    }
    var carSubKind = $("#carSubKind").val();
    if (carSubKind !== "") {
        url += "&carSubKind=" + carSubKind;
    }
    var carPurpose = $("#carPurpose").val();
    if (carPurpose !== "") {
        url += "&carPurpose=" + carPurpose;
    }
    var carOrigin = $("#carOrigin").find(":selected").val();
    if (carOrigin !== undefined) {
        url += "&carOrigin=" + carOrigin;
    }
    var carNamePlateKind = $("#carNamePlateKind").val();
    if (carNamePlateKind !== "") {
        url += "&carNamePlateKind=" + carNamePlateKind;
    }
    var carProductionMethod = $("#carProductionMethod").find(":selected").val();
    if (carProductionMethod !== undefined) {
        url += "&carProductionMethod=" + carProductionMethod;
    }
    var carProductionYear = $("#carProductionYear").val();
    if (carProductionYear !== "") {
        url += "&carProductionYearv=" + carProductionYear;
    }
    var initialRegistrationDateInCountry = $("#initialRegistrationDateInCountry").val();
    if (initialRegistrationDateInCountry !== "") {
        url += "&initialRegistrationDateInCountry=" + initialRegistrationDateInCountry;
    }
    var lastRegistrationDateInCountry = $("#lastRegistrationDateInCountry").val();
    if (lastRegistrationDateInCountry !== "") {
        url += "&lastRegistrationDateInCountry=" + lastRegistrationDateInCountry;
    }
    var registrationDateAbroad = $("#registrationDateAbroad").val();
    if (registrationDateAbroad !== "") {
        url += "&registrationDateAbroad=" + registrationDateAbroad;
    }
    var engineDisplacement = $("#engineDisplacement").val();
    if (engineDisplacement !== "") {
        url += "&engineDisplacement=" + engineDisplacement;
    }
    var motorPowerToUnladenWeightRatioMotorcycle = $("#motorPowerToUnladenWeightRatioMotorcycle").val();
    if (motorPowerToUnladenWeightRatioMotorcycle !== "") {
        url += "&motorPowerToUnladenWeightRatioMotorcycle=" + motorPowerToUnladenWeightRatioMotorcycle;
    }
    var engineNetPower = $("#engineNetPower").val();
    if (engineNetPower !== "") {
        url += "&engineNetPower=" + engineNetPower;
    }
    var hybridEngineNetPower = $("#hybridEngineNetPower").val();
    if (hybridEngineNetPower !== "") {
        url += "&hybridEngineNetPower=" + hybridEngineNetPower;
    }
    var ownMass = $("#ownMass").val();
    if (ownMass !== "") {
        url += "&ownMass=" + ownMass;
    }
    var massOfVehicleReadyToRun = $("#massOfVehicleReadyToRun").val();
    if (massOfVehicleReadyToRun !== "") {
        url += "&massOfVehicleReadyToRun=" + massOfVehicleReadyToRun;
    }
    var permissibleGrossWeight = $("#permissibleGrossWeight").val();
    if (permissibleGrossWeight !== "") {
        url += "&permissibleGrossWeight=" + permissibleGrossWeight;
    }
    var maxTotalMass = $("#maxTotalMass").val();
    if (maxTotalMass !== "") {
        url += "&maxTotalMass=" + maxTotalMass;
    }
    var permissiblePackage = $("#permissiblePackage").val();
    if (permissiblePackage !== "") {
        url += "&permissiblePackage=" + permissiblePackage;
    }
    var maxPackage = $("#maxPackage").val();
    if (maxPackage !== "") {
        url += "&maxPackage=" + maxPackage;
    }
    var permissibleTotalMassOfVehiclesSet = $("#permissibleTotalMassOfVehiclesSet").val();
    if (permissibleTotalMassOfVehiclesSet !== "") {
        url += "&permissibleTotalMassOfVehiclesSet=" + permissibleTotalMassOfVehiclesSet;
    }
    var axlesNumber = $("#axlesNumber").val();
    if (axlesNumber !== "") {
        url += "&axlesNumber=" + axlesNumber;
    }
    var permissibleAxleLoad = $("#permissibleAxleLoad").val();
    if (permissibleAxleLoad !== "") {
        url += "&permissibleAxleLoad=" + permissibleAxleLoad;
    }
    var maxAxleLoad = $("#maxAxleLoad").val();
    if (maxAxleLoad !== "") {
        url += "&maxAxleLoad=" + maxAxleLoad;
    }
    var maxTotalWeightTrailerWithBrake = $("#maxTotalWeightTrailerWithBrake").val();
    if (maxTotalWeightTrailerWithBrake !== "") {
        url += "&maxTotalWeightTrailerWithBrake=" + maxTotalWeightTrailerWithBrake;
    }
    var maxTotalWeightTrailerWithoutBrake = $("#maxTotalWeightTrailerWithoutBrake").val();
    if (maxTotalWeightTrailerWithoutBrake !== "") {
        url += "&maxTotalWeightTrailerWithoutBrake=" + maxTotalWeightTrailerWithoutBrake;
    }
    var placesNumberInTotal = $("#placesNumberInTotal").val();
    if (placesNumberInTotal !== "") {
        url += "&placesNumberInTotal=" + placesNumberInTotal;
    }
    var seatsNumber = $("#seatsNumber").val();
    if (seatsNumber !== "") {
        url += "&seatsNumber=" + seatsNumber;
    }
    var standingPlacesNumber = $("#standingPlacesNumber").val();
    if (standingPlacesNumber !== "") {
        url += "&standingPlacesNumber=" + standingPlacesNumber;
    }
    var fuelType = $("#fuelType").find(":selected").val();
    if (fuelType !== undefined) {
        url += "&fuelType=" + fuelType;
    }
    var firstAlternativefuelType = $("#firstAlternativefuelType").val();
    if (firstAlternativefuelType !== "") {
        url += "&firstAlternativefuelType=" + firstAlternativefuelType;
    }
    var secondAlternativefuelType = $("#secondAlternativefuelType").val();
    if (secondAlternativefuelType !== "") {
        url += "&secondAlternativefuelType=" + secondAlternativefuelType;
    }
    var averageFuelConsumption = $("#averageFuelConsumption").val();
    if (averageFuelConsumption !== "") {
        url += "&averageFuelConsumption=" + averageFuelConsumption;
    }
    var co2EmissionLevel = $("#co2EmissionLevel").val();
    if (co2EmissionLevel !== "") {
        url += "&co2EmissionLevel=" + co2EmissionLevel;
    }
    var suspensionType = $("#suspensionType").val();
    if (suspensionType !== "") {
        url += "&suspensionType=" + suspensionType;
    }
    var equipmentAndTypeOfRadarDevice = $("#equipmentAndTypeOfRadarDevice").val();
    if (equipmentAndTypeOfRadarDevice !== "") {
        url += "&equipmentAndTypeOfRadarDevice=" + equipmentAndTypeOfRadarDevice;
    }
    var steeringRightHandSide = $("#steeringRightHandSide").val();
    if (steeringRightHandSide !== "") {
        url += "&steeringRightHandSide=" + steeringRightHandSide;
    }
    var steeringRightHandSideOrigin = $("#steeringRightHandSideOrigin").val();
    if (steeringRightHandSideOrigin !== "") {
        url += "&steeringRightHandSideOrigin=" + steeringRightHandSideOrigin;
    }
    var catalystAbsorber = $("#catalystAbsorber").val();
    if (catalystAbsorber !== "") {
        url += "&catalystAbsorber=" + catalystAbsorber;
    }
    var manufacturerName = $("#manufacturerName").val();
    if (manufacturerName !== "") {
        url += "&manufacturerName=" + manufacturerName;
    }
    var carTransportInstituteCode = $("#carTransportInstituteCode").val();
    if (carTransportInstituteCode !== "") {
        url += "&carTransportInstituteCode=" + carTransportInstituteCode;
    }
    var wheelBaseSteeringOtherAxles = $("#wheelBaseSteeringOtherAxles").val();
    if (wheelBaseSteeringOtherAxles !== "") {
        url += "&wheelBaseSteeringOtherAxles=" + wheelBaseSteeringOtherAxles;
    }
    var maxTrackWheel = $("#maxTrackWheel").val();
    if (maxTrackWheel !== "") {
        url += "&maxTrackWheel=" + maxTrackWheel;
    }
    var avgTrackWheel = $("#avgTrackWheel").val();
    if (avgTrackWheel !== "") {
        url += "&avgTrackWheel=" + avgTrackWheel;
    }
    var minTrackWheel = $("#minTrackWheel").val();
    if (minTrackWheel !== "") {
        url += "&minTrackWheel=" + minTrackWheel;
    }
    var exhaustEmissionReduction = $("#exhaustEmissionReduction").val();
    if (exhaustEmissionReduction !== "") {
        url += "&exhaustEmissionReduction=" + exhaustEmissionReduction;
    }
    var encodingTypeSubtypeDestiny = $("#encodingTypeSubtypeDestiny").val();
    if (encodingTypeSubtypeDestiny !== "") {
        url += "&encodingTypeSubtypeDestiny=" + encodingTypeSubtypeDestiny;
    }
    var codeKindSubgenusDestiny = $("#codeKindSubgenusDestiny").val();
    if (codeKindSubgenusDestiny !== "") {
        url += "&codeKindSubgenusDestiny=" + codeKindSubgenusDestiny;
    }
    var vehicleDeregistrationDate = $("#vehicleDeregistrationDate").val();
    if (vehicleDeregistrationDate !== "") {
        url += "&vehicleDeregistrationDate=" + vehicleDeregistrationDate;
    }
    var vehicleDeregistrationReason = $("#vehicleDeregistrationReason").val();
    if (vehicleDeregistrationReason !== "") {
        url += "&vehicleDeregistrationReason=" + vehicleDeregistrationReason;
    }
    var dataInputDate = $("#dataInputDate").val();
    if (dataInputDate !== "") {
        url += "&dataInputDate=" + dataInputDate;
    }
    var registrationVoivodship = $("#registrationVoivodship").val();
    if (registrationVoivodship !== "") {
        url += "&registrationVoivodship=" + registrationVoivodship;
    }
    var registrationCommune = $("#registrationCommune").val();
    if (registrationCommune !== "") {
        url += "&registrationCommune=" + registrationCommune;
    }
    var registrationDistrict = $("#registrationDistrict").val();
    if (registrationDistrict !== "") {
        url += "&registrationDistrict=" + registrationDistrict;
    }
    var ownerVoivodship = $("#ownerVoivodship").val();
    if (ownerVoivodship !== "") {
        url += "&ownerVoivodship=" + ownerVoivodship;
    }
    var ownerDistrict = $("#ownerDistrict").val();
    if (ownerDistrict !== "") {
        url += "&ownerDistrict=" + ownerDistrict;
    }
    var ownerCommune = $("#ownerCommune").val();
    if (ownerCommune !== "") {
        url += "&ownerCommune=" + ownerCommune;
    }
    var ownerVoivodshipCode = $("#ownerVoivodshipCode").val();
    if (ownerVoivodshipCode !== "") {
        url += "&ownerVoivodshipCode=" + ownerVoivodshipCode;
    }
    var co2EmissionsAlternativeFuel1 = $("#co2EmissionsAlternativeFuel1").val();
    if (co2EmissionsAlternativeFuel1 !== "") {
        url += "&co2EmissionsAlternativeFuel1=" + co2EmissionsAlternativeFuel1;
    }
    return url;
}








