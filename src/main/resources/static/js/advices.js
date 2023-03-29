
var objToDeleteId;

$(document).ready(function () {

    $("#filter input, #filter select, [form='filter']").on("change", function () {
        findAdvices();
    });

    $('#create-advice-modal').on('hide.bs.modal', function (e) {
        clearCreateAdviceModal();
    });

    findAdvices();
});

function findAdvices() {
    $.ajax({
        url: "/api/adviser/advices?" + prepareUrl() + preparePaginationUrl(),
        method: "get",
        dataType: "json",
        contentType: "application/json"
    })
        .done(function (data) {
            clearTable();
            fillResults(data);
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

function prepareUrl() {
    var url = "";

    var name = $("#name").val();
    if(name != "") {
        url+= "&name=" + name;
    }

    var applicationId = $("#application-id").val();
    if(applicationId != "") {
        url+= "&application_id=" + applicationId;
    }

    var domain = $("#domain").val();
    if(domain != "") {
        url+= "&domain=" + domain;
    }

    return url;
}

function clearTable() {
    $("#records").empty();
}

function fillResults(data) {
    $("#count").text(data.totalAdvicesCount);
    fillTable(data.advices);
}

function fillTable(advices) {
    advices.forEach(function(advice) {
        fillRow(advice);
    });
}

function fillRow(advice) {
    $('#records').append(
        "<tr>" +
            "<td class='align-middle'>" + advice.id + "</td>" +
            "<td class='align-middle'>" + prepareText(advice.name) + "</td>" +
            "<td class='align-middle'>" + prepareText(advice.appId) + "</td>" +
            "<td class='align-middle'>" + prepareText(advice.domain)+ "</td>" +
            "<td class='align-middle'>" + prepareDetailsButton(advice.id) + "</td>" +
            "<td class='align-middle'>" + prepareDeleteButton(advice.id) + "</td>" +
        "</tr>"
    );
}

function prepareDetailsButton(id) {
    return '<button type="button" class="btn btn-primary" onclick="goToAdviceDetailsPage(' + id + ')">Details</button>';
}

function prepareDeleteButton(id) {
    return '<button type="button" class="btn btn-danger" onclick="setObjectToDeleteIdAndShowModal(' + id + ')">Delete</button>';
}

function goToAdviceDetailsPage(id) {
    window.location.href = "/admin/advice/" + id;
}

function setObjectToDeleteIdAndShowModal(id) {
    objToDeleteId = id;
    $('#delete-advice-modal').modal('show');
}

function deleteAdvice() {
    $.ajax({
        url: "/api/adviser/advices/" + objToDeleteId,
        type: "DELETE"
    })
        .done(function(response) {
            $('#delete-advice-modal').modal('hide');
            $('#operation-successful-modal').modal('show');
            findAdvices();
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

function clearSearchFiltersAndRefresh() {
    $("#name").val("");
    $("#application-id").val("");
    $("#domain").val("");
    findAdvices();
}

function sendCreateAdviceRequest() {
    $.ajax({
        url: "/api/adviser/advices",
        method: "post",
        contentType: "application/json",
        data: JSON.stringify({
            appId: $("#create-advice-app-id").val(),
            domain: $("#create-advice-domain").val(),
            type: $("#create-advice-type").val(),
            scope: $("#create-advice-scope").val(),
            action: $("#create-advice-action").val(),
            dataType: $("#create-advice-data-type").val(),
            component: $("#create-advice-component").val(),
            adviceClass: $("#create-advice-class").val(),
            name: $("#create-advice-name").val(),
            content: $("#create-advice-content").val()
        })
    })
        .done(function () {
            $("#create-advice-modal").modal('hide');
            $("#operation-successful-modal").modal('show');
            findAdvices();
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}

function clearCreateAdviceModal() {
    $("#create-advice-app-id").val('');
    $("#create-advice-domain").val('');
    $("#create-advice-type").val('');
    $("#create-advice-scope").val('');
    $("#create-advice-action").val('');
    $("#create-advice-data-type").val('');
    $("#create-advice-component").val('');
    $("#create-advice-class").val('');
    $("#create-advice-name").val('');
    $("#create-advice-content").val('');
}