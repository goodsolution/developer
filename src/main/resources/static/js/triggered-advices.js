
var objToDeleteId;

$(document).ready(function() {
    $("#filter input, #filter select, [form='filter']").on("change", function () {
        findTriggeredAdvices();
    });
    findTriggeredAdvices();
});

function findTriggeredAdvices() {
    $.ajax({
        url: "/api/adviser/triggered-advices?" + prepareUrl() + preparePaginationUrl(),
        type: "get",
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

    var triggerDateTimeFrom = $("#trigger-date-time-from").val();
    if(triggerDateTimeFrom != "") {
        url+= "&trigger_date_time_from=" + triggerDateTimeFrom;
    }

    var triggerDateTimeTo = $("#trigger-date-time-to").val();
    if(triggerDateTimeTo != "") {
        url+= "&trigger_date_time_to=" + triggerDateTimeTo;
    }

    return url;
}

function clearTable() {
    $("#records").empty();
}

function fillResults(data) {
    $("#count").text(data.count);
    fillTable(data.triggeredAdvices);
}

function fillTable(triggeredAdvices) {
    triggeredAdvices.forEach(function(triggeredAdvice) {
        fillRow(triggeredAdvice);
    });
}

function fillRow(triggeredAdvice) {
    $('#records').append(
        "<tr>" +
            "<td class='align-middle'>" + triggeredAdvice.id + "</td>" +
            "<td class='align-middle'>" + triggeredAdvice.name + "</td>" +
            "<td class='align-middle'>" + triggeredAdvice.appId + "</td>" +
            "<td class='align-middle'>" + triggeredAdvice.domain + "</td>" +
            "<td class='align-middle'>" + triggeredAdvice.triggerDateTime.replace("T", " ") + "</td>" +
            "<td class='align-middle'>" + fillButtonsCell(triggeredAdvice.id) + "</td>" +
        "</tr>"
    );
}

function fillButtonsCell(id) {
    var buttonCell = '<button type="button" class="btn btn-primary m-1" onclick="goToTriggeredAdviceDetailsPage(' + id + ')">Details</button>' +
    '<button type="button" class="btn btn-danger m-1" onclick="setObjectToDeleteIdAndShowModal(' + id + ')">Delete</button>';
    return buttonCell;
}


function clearSearchFiltersAndRefresh() {
    $("#name").val("");
    $("#application-id").val("");
    $("#domain").val("");
    $("#trigger-date-time-from").val("");
    $("#trigger-date-time-to").val("");
    findTriggeredAdvices();
}

function setObjectToDeleteIdAndShowModal(id) {
    objToDeleteId = id;
    $('#delete-triggered-advice-modal').modal('show');
}

function deleteTriggeredAdvice() {
    $.ajax({
        url: "/api/adviser/triggered-advices/" + objToDeleteId,
        type: "DELETE"
    })
    .done(function(response) {
        $('#delete-triggered-advice-modal').modal('hide');
        $('#operation-successful-modal').modal('show');
        findTriggeredAdvices();
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function goToTriggeredAdviceDetailsPage(id) {
    window.location.href = '/admin/triggered-advice/' + id;
}

