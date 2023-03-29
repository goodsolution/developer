
var objToDeleteId;

$(document).ready(function () {

    $("#filter input, #filter select, [form='filter']").on("change", function () {
        find();
    });

    $('#create-advice-modal').on('hide.bs.modal', function (e) {
        clearCreateAdviceModal();
    });

    find();
});

function find() {
    $.ajax({
        url: "/api/public/life_adviser/newest?" + prepareUrl(),
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
    url+= "&application_id=" + 'life-adviser';
    url+= "&domain=" + 'main-stream';
    url+= "&domain_id=" + '2';
    url+= "&secret=" + '2b2221ed-43d3-4834-8bd9-a0454f17037f-1592039601774-718d6284-2250-4af7-b700-2cd067e0cc83';
//    var name = $("#name").val();
//    if(name != "") {
//        url+= "&name=" + name;
//    }
//
//    var applicationId = $("#application-id").val();
//    if(applicationId != "") {
//        url+= "&application_id=" + applicationId;
//    }
//
//    var domain = $("#domain").val();
//    if(domain != "") {
//        url+= "&domain=" + domain;
//    }

    return url;
}

function clearTable() {
    $("#records").empty();
}

function fillResults(data) {
    //$("#count").text(data.totalAdvicesCount);
    fillTable(data);
}

function fillTable(advices) {
    advices.forEach(function(advice) {
        fillRow(advice);
    });
}

function fillRow(record) {
    $('#records').append(
        "<tr>" +
            //"<td class='align-middle'>" + advice.id + "</td>" +
            "<td class='align-middle'>" + prepareText(record.name) + "</td>" +
            "<td class='align-middle'>" + prepareText(record.content) + "</td>" +
            //"<td class='align-middle'>" + prepareText(advice.domain)+ "</td>" +
//            "<td class='align-middle'>" + prepareDetailsButton(advice.id) + "</td>" +
//            "<td class='align-middle'>" + prepareDeleteButton(advice.id) + "</td>" +
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
            find();
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

function clearSearchFiltersAndRefresh() {
    $("#name").val("");
    $("#application-id").val("");
    $("#domain").val("");
    find();
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