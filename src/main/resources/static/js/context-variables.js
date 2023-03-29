$(document).ready(function () {

    $("#filter input, #filter select, [form='filter']").on("change", function () {
        findContextVariables();
    });

    findContextVariables();
});

function findContextVariables() {
    $.ajax({
        url: "/api/adviser/context-variables?" + prepareUrl() + preparePaginationUrl(),
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

    var contextId = $("#context-id").val();
    if(contextId != "") {
        url+= "&context_id=" + contextId;
    }

    return url;
}

function fillResults(data) {
    $("#count").text(data.totalContextVariablesCount);
    fillTable(data.contextVariables);
}

function fillTable(contextVariables) {
    contextVariables.forEach(function(contextVariable) {
        fillRow(contextVariable);
    });
}

function fillRow(contextVariable) {
    $('#records').append(
        "<tr>" +
        "<td class='align-middle'>" + contextVariable.id + "</td>" +
        "<td class='align-middle'>" + prepareText(contextVariable.applicationId) + "</td>" +
        "<td class='align-middle'>" + prepareText(contextVariable.context) + "</td>" +
        "<td class='align-middle'>" + prepareText(contextVariable.name) + "</td>" +
        "<td class='align-middle'>" + prepareText(contextVariable.type) + "</td>" +
        "<td class='align-middle'>" + prepareText(contextVariable.value) + "</td>" +
        "<td class='align-middle'>" + prepareText(contextVariable.contextId) + "</td>" +
        "</tr>"
    );
}

function clearSearchFiltersAndRefresh() {
    $("#name").val("");
    $("#application-id").val("");
    $("#context").val("");
    $("#type").val("");
    $("#context-id").val("");
    findContextVariables();
}