let objToDeleteId;

$(document).ready(function () {

    $('#create-context-config-modal').on('hide.bs.modal', function (e) {
        clearCreateModal();
    });

    $("#filter input, #filter select, [form='filter']").on("change", function () {
        findContextConfigs();
    });

    findContextConfigs();
});

function findContextConfigs() {
    $.ajax({
        url: "/api/adviser/context-configs?" + prepareUrl() + preparePaginationUrl(),
        method: "get",
        dataType: "json",
        contentType: "application/json"
    })
        .done(function (data) {
            $("#records").empty();
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

    var context = $("#context").val();
    if(context != "") {
        url+= "&context=" + context;
    }

    var type = $("#type").val();
    if(type != "") {
        url+= "&type=" + type;
    }

    return url;
}

function fillResults(data) {
    $("#count").text(data.totalContextConfigsCount);
    fillTable(data.contextConfigs);
}

function fillTable(contextConfigs) {
    contextConfigs.forEach(function(contextConfig) {
        fillRow(contextConfig);
    });
}

function fillRow(contextConfig) {
    $('#records').append(
        "<tr>" +
        "<td class='align-middle'>" + contextConfig.id + "</td>" +
        "<td class='align-middle'>" + prepareText(contextConfig.applicationId) + "</td>" +
        "<td class='align-middle'>" + prepareText(contextConfig.context) + "</td>" +
        "<td class='align-middle'>" + prepareText(contextConfig.name) + "</td>" +
        "<td class='align-middle'>" + prepareText(contextConfig.type) + "</td>" +
        "<td class='align-middle'>" + prepareDetailsButton(contextConfig.id) + "</td>" +
        "<td class='align-middle'>" + prepareDeleteButton(contextConfig.id) + "</td>" +
        "</tr>"
    );
}

function prepareDetailsButton(id) {
    return '<button type="button" class="btn btn-primary" onclick="goToDetailsPage(' + id + ')">Details</button>';
}

function prepareDeleteButton(id) {
    return '<button type="button" class="btn btn-danger" onclick="setObjectToDeleteIdAndShowModal(' + id + ')">Delete</button>';
}

function clearSearchFiltersAndRefresh() {
    $("#name").val("");
    $("#application-id").val("");
    $("#context").val("");
    $("#type").val("");
    findContextConfigs();
}

function setObjectToDeleteIdAndShowModal(id) {
    objToDeleteId = id;
    $('#delete-object-modal').modal('show');
}

function sendDeleteRequest() {
    $.ajax({
        url: "/api/adviser/context-configs/" + objToDeleteId,
        type: "delete"
    })
        .done(function() {
            $('#delete-object-modal').modal('hide');
            $('#operation-successful-modal').modal('show');
            findContextConfigs();
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

function goToDetailsPage(id) {
    window.location.href = "/admin/context-config/" + id;
}

function sendCreateRequest() {
    $.ajax({
        url: "/api/adviser/context-configs",
        method: "post",
        contentType: "application/json",
        data: JSON.stringify({
            applicationId: $("#create-application-id").val(),
            context: $("#create-context").val(),
            name: $("#create-name").val(),
            type: $("#create-type").val(),
            value: $("#create-value").val()
        })
    })
        .done(function () {
            $("#create-context-config-modal").modal('hide');
            $("#operation-successful-modal").modal('show');
            findContextConfigs();
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}

function clearCreateModal() {
    $("#create-application-id").val(''),
    $("#create-context").val(''),
    $("#create-name").val(''),
    $("#create-type").val(''),
    $("#create-value").val('')
}
