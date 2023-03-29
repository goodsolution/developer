
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
        url: "/api/public/life_adviser/market?" + prepareUrl() + preparePaginationUrl(),
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
    url+= "&search=" + $("#search").val();
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
    $("#count").text(data.totalAdvicesCount);
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
//            "<td class='align-middle'>" + advice.id + "</td>" +
            "<td class='align-middle'>" + prepareText(record.name) + "</td>" +
            "<td class='align-middle'>" + prepareText(record.description) + "</td>" +
            "<td class='align-middle'>" + prepareText(record.tags)+ "</td>" +
            "<td class='align-middle'>" + prepareText(record.pricePerMonth) + "</td>" +
            "<td class='align-middle'>" + preparePurchaseButton(record.id, 'M') + "</td>" +
            "<td class='align-middle'>" + prepareText(record.pricePerQuarter) + "</td>" +
            "<td class='align-middle'>" + preparePurchaseButton(record.id, 'Q') + "</td>" +
            "<td class='align-middle'>" + prepareText(record.pricePerHalfYear) + "</td>" +
            "<td class='align-middle'>" + preparePurchaseButton(record.id, 'H') + "</td>" +
            "<td class='align-middle'>" + prepareText(record.pricePerYear)+ "</td>" +
            "<td class='align-middle'>" + preparePurchaseButton(record.id, 'Y') + "</td>" +
//            "<td class='align-middle'>" + prepareDeleteButton(advice.id) + "</td>" +
        "</tr>"
    );
}

function prepareDetailsButton(id) {
    return '<button type="button" class="btn btn-primary" onclick="goToAdviceDetailsPage(' + id + ')">Details</button>';
}

function preparePurchaseButton(id, period) {
    return '<button type="button" class="btn btn-primary" onclick="purchase(' + id + ', \'' + period + '\')">Purchase</button>';
}

function prepareDeleteButton(id) {
    return '<button type="button" class="btn btn-danger" onclick="setObjectToDeleteIdAndShowModal(' + id + ')">Delete</button>';
}

function purchase(id, period) {
    $.ajax({
            url: "/api/public/life_adviser/purchase-start",
            method: "post",
            contentType: "application/json",
            data: JSON.stringify({
                applicationId: "life-adviser",
                domain: "main-stream",
                domainId: 71,
	            secret:"2b2221ed-43d3-4834-8bd9-a0454f17037f-1592039601774-718d6284-2250-4af7-b700-2cd067e0cc83",
	            period: period,
	            categoryId: id
	        })
    })
    .done(function (result) {
            finishPurchase(result.purchasedCategoryId);
    })
    .fail(function (jqxhr, textStatus, errorThrown) {
                displayErrorInformation(jqxhr.responseText);
    });

}

function finishPurchase(purchasedCategoryId) {
    //alert("purchasedCategoryId: " + purchasedCategoryId);
        $.ajax({
                url: "/api/public/life_adviser/purchase-finish/" + purchasedCategoryId,
                method: "put",
                contentType: "application/json",
                data: JSON.stringify({
                    applicationId: "life-adviser",
                    domain: "main-stream",
                    domainId: 71,
    	            secret:"2b2221ed-43d3-4834-8bd9-a0454f17037f-1592039601774-718d6284-2250-4af7-b700-2cd067e0cc83",
    	            externalTransactionId: "453HSNNSNJDS4353" + purchasedCategoryId
    	        })
        })
        .done(function (result) {
                $("#operation-successful-modal").modal('show');
                find();
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
                    displayErrorInformation(jqxhr.responseText);
        });
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
            find();
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