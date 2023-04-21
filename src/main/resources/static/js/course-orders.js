$(document).ready(function () {

    findOrders();

    $("#filter input, #filter select, [form='filter']").on("change", function () {
        findOrders();
    });

});

function findOrders() {
    $.ajax({
        url: "/admin/api/crs/orders?" + prepareUrl(),
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (response) {
        $("#records").empty();
        fillResults(response.orders);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function prepareUrl() {
    var url = "";

    var id = $("#id").val();
        if (id != "") {
            url += "&id=" + id;
        }

    var number = $("#number").val();
        if (number != "") {
            url += "&number=" + number;
        }

    var status = $("#status").find(":selected").val();
        if (status != "") {
            url += "&status=" + status;
        }

    var invoiceIssued = $('#invoice-issued').find(":selected").val();
        if (invoiceIssued != "") {
            url += "&invoice_issued=" + invoiceIssued;
        }


    url += preparePaginationUrl();

    return url;
}

function fillResults(orders) {
    orders.forEach(function(order){
        fillRow(order);
    });
}

function fillRow(order) {
    $('#records').append(
        "<tr>" +
            "<td class='align-middle'>" + order.id + "</td>" +
            "<td class='align-middle'>" + order.number + "</td>" +
            "<td class='align-middle'>" + order.customer.login + "</td>" +
            "<td class='align-middle'>" + order.purchaseDate + "</td>" +
            "<td class='align-middle'>" + order.totalPrice + " PLN</td>" +
            "<td class='align-middle'>" + prepareStatusFromDict(order.status) + "</td>" +
            "<td class='align-middle'>" + prepareDetailsButton(order.id) + "</td>" +
        "</tr>"
    );
}

function prepareStatusFromDict(status) {
    for(i=0; i<orderStatusesDict.length; i++) {
        if(orderStatusesDict[i].code === status) {
            return orderStatusesDict[i].value;
        }
    }
}

function goToDetailsPage(id) {
    window.location.href = "/admin/order/" + id;
}

function prepareDetailsButton(id) {
    return '<button type="button" class="btn btn-primary" onclick="goToDetailsPage(' + id + ')">Details</button>';
}

function clearSearchFilters() {

    $("#id").val("");
    $("#number").val("");
    $("#status").val("");
    $('#invoice-issued').val("");

    findOrders();
}