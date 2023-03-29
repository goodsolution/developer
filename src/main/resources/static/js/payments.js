$(document).ready(function () {
    trace('page.show', 'payments');
    findOrders();
});

function findOrders() {
    $.ajax({
        url: "/api/crs/orders/logged-customer",
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (orders) {
        $("#records").empty();
        fillResults(orders);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillResults(orders) {
    if(orders.length == 0) {
        printInfoNoPayments();
    } else {
        printContent(orders);
    }
}

function printInfoNoPayments() {
    $('#container').append(
    '<div class="row my-5"></div>' +
    '<div class="text-center">' +
        "<h6>" + lang.payments_noPayments + "</h6>"+
    '</div>'
    );
}

function printContent(orders) {

    $("#result-container").show();

    orders.forEach(function(order){
        fillRow(order);
    });
}

function fillRow(order) {
    $('#records').append(
        "<tr>" +
            "<td class='align-middle text-center'>" + order.number + "</td>" +
            "<td class='align-middle text-center'>" + prepareDateTime(order.purchaseDate) + "</td>" +
            "<td class='align-middle text-center'>" + order.totalPrice + " PLN</td>" +
            "<td class='align-middle text-center'>" + prepareStatusFromDict(order.status) + preparePayButton(order.status, order.payuPaymentUrl) + "</td>" +
            "<td class='align-middle text-center'>" + prepareDetailsButton(order.id) + "</td>" +
        "</tr>"
    );
}

function preparePayButton(orderStatus, paymentUrl) {
    if(isOrderUnPaidOrPending(orderStatus)) {
        return " <a href='" + paymentUrl + "' type='button' class='btn btn-sm btn-success my-1 mx-1'>" + lang.payments_pay + "</a>";
    } else {
        return "";
    }
}

function isOrderUnPaidOrPending(orderStatus) {
    if(orderStatus == 'u' || orderStatus == 'w') {
        return true;
    } else {
        return false;
    }
}

function prepareStatusFromDict(status) {
    for(i=0; i<orderStatusesDict.length; i++) {
        if(orderStatusesDict[i].code === status) {
            return orderStatusesDict[i].value;
        }
    }
}

function prepareDetailsButton(orderId) {
    return '<button type="button" class="btn btn-outline-primary btn-sm" onclick="showDetails(' + orderId + ')">' + lang.payments_details + '</button>';
}

function showDetails(orderId) {
    $.ajax({
        url: "/api/crs/order-details/" + orderId,
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (response) {
        $("#courses-list-records").empty();
        $("#invoices-list-records").empty();
        fillPurchasedCourses(response.purchasedCourses);
        fillInvoices(response.invoices, response.order.totalPrice, response.order.status);
        prepareRenewPaymentWindow(response.order);
        $("#details-modal").modal('show');
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillPurchasedCourses(purchasedCourses) {
    purchasedCourses.forEach(function(purchasedCourse){
            $('#courses-list-records').append(
                "<tr>" +
                    "<td class='align-middle text-center'>" + preparePurchasedCourseTitleCell(purchasedCourse) + "</td>" +
                    "<td class='align-middle text-center'>" + purchasedCourse.price + " PLN</td>" +
                "</tr>"
            );
    });
}

function preparePurchasedCourseTitleCell(purchasedCourse) {
    if(purchasedCourse.returned == true) {
        return purchasedCourse.course.title + ' (' + lang.payments_returned + ')';
    } else {
        return purchasedCourse.course.title;
    }

}

function fillInvoices(invoices, orderTotalPrice, orderStatus) {

    if(invoices.length == 0) {
        $("#invoices-list-container").hide();

        if(orderTotalPrice != 0 && orderStatus == 'p') {
            $("#invoices").show();
            $("#no-invoices-info").show();
        } else {
            $("#invoices").hide();
            $("#no-invoices-info").hide();
        }

    } else {
        $("#invoices").show();
        $("#no-invoices-info").hide();
        $("#invoices-list-container").show();
        fillInvoicesTable(invoices);
    }
}

function fillInvoicesTable(invoices) {
    invoices.forEach(function(invoice){
            $('#invoices-list-records').append(
                "<tr>" +
                    "<td class='align-middle text-center'>" + prepareInvoiceTypeFromDict(invoice.type) + "</td>" +
                    "<td class='align-middle text-center'>" + invoice.number + "</td>" +
                    "<td class='align-middle text-center'>" + prepareDownloadInvoiceButton(invoice.id) + "</td>" +
                "</tr>"
            );
    });
}

function prepareInvoiceTypeFromDict(invoiceType) {
    for(i=0; i<invoiceTypesDict.length; i++) {
        if(invoiceTypesDict[i].code === invoiceType) {
            return invoiceTypesDict[i].value;
        }
    }
}

function prepareDownloadInvoiceButton(id) {
    return '<a type="button" class="btn btn-outline-primary btn-sm" onclick="trace(' + "'file.downloaded', 'invoice&id=" + id + "'" + ')" href="/get-invoice/' + id + '" download="invoice.pdf"><i class="fas fa-download"></i> ' + lang.payments_download + '</a>';
}

function prepareRenewPaymentWindow(order) {
    if(isOrderUnPaidOrPending(order.status)) {
        $("#renew-payment-button").attr("href", order.payuPaymentUrl);
        $("#renew-payment-window").show();
    } else {
        $("#renew-payment-window").hide();
    }
}