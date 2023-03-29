
var invoiceToDeleteId;
var purchasedCourseToReturnId;

$(document).ready(function () {
    $("#status").val(prepareStatusFromDict(order.status));
    $("#invoice-type").val(prepareBillingTypeFromDict(order.invoiceType));
    findInvoices();
    findPurchasedCourses();
});


function prepareStatusFromDict(status) {
    for(i=0; i<orderStatusesDict.length; i++) {
        if(orderStatusesDict[i].code === status) {
            return orderStatusesDict[i].value;
        }
    }
}

function prepareBillingTypeFromDict(billingType) {
    for(i=0; i<billingTypesDict.length; i++) {
        if(billingTypesDict[i].code === billingType) {
            return billingTypesDict[i].value;
        }
    }
}

function findInvoices() {
    $.ajax({
        url: "/admin/api/crs/invoices?order_id=" + order.id,
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (response) {
        $("#invoices").empty();
        fillInvoices(response.invoices);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}


function fillInvoices(invoices) {
    invoices.forEach(function(invoice){
        $('#invoices').append(
            "<tr>" +
                "<td class='align-middle'>" + invoice.id + "</td>" +
                "<td class='align-middle'>" + prepareInvoiceTypeFromDict(invoice.type) + "</td>" +
                "<td class='align-middle'>" + invoice.number + "</td>" +
                "<td class='align-middle'>" + prepareDownloadInvoiceButton(invoice.id) + "</td>" +
                "<td class='align-middle'>" + prepareDeleteInvoiceButton(invoice.id) + "</td>" +
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

function prepareDownloadInvoiceButton(invoiceId) {
    return '<a type="button" class="btn btn-primary btn-sm" href="/admin/get-invoice/' + invoiceId + '" download="invoice.pdf">Download</a>';
}

function prepareDeleteInvoiceButton(invoiceId) {
    return '<button type="button" class="btn btn-danger btn-sm" onclick="prepareInvoiceToDelete(' + invoiceId + ')">Delete</button>';
}

function prepareInvoiceToDelete(id) {
    invoiceToDeleteId = id;
    $('#delete-invoice-modal').modal('show');
}

function sendDeleteInvoiceRequest() {
    $.ajax({
        url: "/admin/api/crs/invoice/" + invoiceToDeleteId,
        type: "DELETE",
        contentType: "application/json"
    })
    .done(function () {
        findInvoices();
        $('#delete-invoice-modal').modal('hide');
        $('#operation-successful-modal').modal('show');
        location.reload();
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function findPurchasedCourses() {
    $.ajax({
        url: "/admin/api/crs/purchased-courses?order_id=" + order.id,
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (response) {
        $("#purchased-courses").empty();
        fillPurchasedCourses(response.purchasedCourses);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillPurchasedCourses(purchasedCourses) {
    purchasedCourses.forEach(function(purchasedCourse){
        $('#purchased-courses').append(
            "<tr>" +
                "<td class='align-middle'>" + purchasedCourse.course.title + "</td>" +
                "<td class='align-middle'>" + purchasedCourse.price + " PLN</td>" +
                "<td class='align-middle'>" + purchasedCourse.returned + "</td>" +
                "<td class='align-middle'>" + prepareReturnPurchasedCourseButton(purchasedCourse.id) + "</td>" +
            "</tr>"
        );
    });
}

function prepareReturnPurchasedCourseButton(id) {
    return '<button class="btn btn-danger btn-sm" onclick="warnBeforeCourseReturning(' + id + ')">Return</button>'
}

function warnBeforeCourseReturning(id) {
    purchasedCourseToReturnId = id;
    $('#return-purchased-course-modal').modal('show');
}

function setPurchasedCourseReturned() {
    $.ajax({
        url: "/admin/api/crs/return/purchased-course/" + purchasedCourseToReturnId,
        type: "PUT",
        contentType: "application/json"
    })
    .done(function () {
        findPurchasedCourses();
        $('#return-purchased-course-modal').modal('hide');
        $('#operation-successful-modal').modal('show');
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function cancelOrder() {
    $.ajax({
        url: "/admin/api/crs/order/" + order.id + "/cancel",
        type: "DELETE",
        contentType: "application/json"
    })
    .done(function () {
        $('#cancel-order-modal').modal('hide');
        $('#operation-successful-modal').modal('show');
        location.reload();
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}