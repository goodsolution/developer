
let objToDeleteId;

$(document).ready(function () {
    findApplications();

    $("#filter input, #filter select, [form='filter']").on("change", function () {
        findApplications();
    });

    $('#create-application-modal').on('hide.bs.modal', function (e) {
        $("#create-application-id").val('');
        $("#create-description").val('');
        $("#create-secret-key").val('');
    });

});

function findApplications() {
    $.ajax({
        url: "/api/adviser/applications?application_id=" + $("#filter-application-id").val() + preparePaginationUrl(),
        method: "get",
        contentType: "application/json",
        dataType: "json"
    })
        .done(function (data) {
            clearTable();
            fillResults(data);
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

function clearTable() {
    $("#records").empty();
}

function fillResults(data) {
    $("#count").text(data.totalApplicationsCount);
    fillTable(data.applications);
}

function fillTable(applications) {
    applications.forEach(function(application) {
        fillRow(application);
    });
}

function fillRow(application) {
    $('#records').append(
        "<tr>" +
        "<td class='align-middle'>" + application.id + "</td>" +
        "<td class='align-middle'>" + prepareText(application.applicationId) + "</td>" +
        "<td class='align-middle'>" + prepareText(application.description) + "</td>" +
        "<td class='align-middle'>" + prepareDetailsButton(application.id) + "</td>" +
        "<td class='align-middle'>" + prepareDeleteButton(application.id) + "</td>" +
        "</tr>"
    );
}

function prepareDetailsButton(id) {
    return '<button type="button" class="btn btn-primary" onclick="goToApplicationsDetailsPage(' + id + ')">Details</button>';
}

function prepareDeleteButton(id) {
    return '<button type="button" class="btn btn-danger" onclick="setObjectToDeleteIdAndShowModal(' + id + ')">Delete</button>';
}

function setObjectToDeleteIdAndShowModal(id) {
    objToDeleteId = id;
    $('#delete-object-modal').modal('show');
}

function sendDeleteRequest() {
    $.ajax({
        url: "/api/adviser/applications/" + objToDeleteId,
        type: "delete"
    })
        .done(function() {
            $('#delete-object-modal').modal('hide');
            $('#operation-successful-modal').modal('show');
            findApplications();
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

function goToApplicationsDetailsPage(id) {
    window.location.href = "/admin/application/" + id;
}


function sendCreateApplicationRequest() {
    $.ajax({
        url: "/api/adviser/applications",
        method: "post",
        contentType: "application/json",
        data: JSON.stringify({
            applicationId: $("#create-application-id").val(),
            description: $("#create-description").val(),
            secretKey: $("#create-secret-key").val()
        })
    })
        .done(function () {
            $("#create-application-modal").modal('hide');
            $("#operation-successful-modal").modal('show');
            findApplications();
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}

function clearSearchFiltersAndRefresh() {
    $("#filter-application-id").val('');
    findApplications();
}

function generateSecretKey() {
    $.ajax({
        url: "/api/public/adviser/generate-secret-key",
        method: "get"
    })
        .done(function (secretKey) {
            $("#create-secret-key").val(secretKey);
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}