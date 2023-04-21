$(document).ready(function() {
    fillSelects();
    setEvents();
    getCustomerData();
});

function setEvents() {
    setObjDisplayTogglingWithCheckbox('weekend-address-data', 'another-weekend-address');
    setObjDisplayTogglingWithCheckbox('invoice-data', 'invoice');
    setObjDisplayTogglingWithCheckbox('another-hours-weekend-data', 'another-hours-weekend');
}

function fillSelects() {
    fillSelectWithDictById('group', customerGroupsDict);
    fillSelectWithDictById('status', customerStatusesDict);
    fillSelectWithDictById('city', citiesDict);
    fillSelectWithDictById('weekend-city', citiesDict);
    fillSelectWithDictByCode('demanding-customer', yesNoDict);
}

function fillSelectWithDictByCode(selectId, dict) {
    dict.forEach(function(objInDict) {
        $('#' + selectId).append('<option value="' + objInDict.code + '">' + htmlEncode(objInDict.value) + '</option>');
    });
}

function fillSelectWithDictById(selectId, dict) {
    dict.forEach(function(objInDict) {
        $('#' + selectId).append('<option value="' + objInDict.id + '">' + htmlEncode(objInDict.value) + '</option>');
    });
}

function setObjDisplayTogglingWithCheckbox(objectId, checkboxId) {
    $('#' + checkboxId).click(function() {
        if($(this).is(":checked"))
        {
            $('#' + objectId).show();
        } else {
            $('#' + objectId).hide();
        }
    });
}

function getCustomerData() {
    $('#save-button').prop('disabled', true);
    $.ajax({
        url: "/api/customer/" + customerId,
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (customer) {
        fillResults(customer);
        setShowLastCustomerOrderButtonDisablingStatus(customer.ordersCount);
        $('#save-button').prop('disabled', false);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
        $('#save-button').prop('disabled', false);
    });
}

function setShowLastCustomerOrderButtonDisablingStatus(ordersCount) {
    if(ordersCount > 0) {
        $('#last-customer-order').prop('disabled', false);
    } else {
        $('#last-customer-order').prop('disabled', true);
    }
}

function fillResults(customer) {
    $('#customer-id').text(customerId);

    $('#name-and-surname').val(customer.firstAndLastName);
    $('#email').val(customer.email);
    $('#group').val(customer.groupId);
    $('#status').val(customer.active);
    $('#registration-date').val(customer.registrationDate);
    $('#registration-ip').val(customer.registrationIp);
    $('#regulation-acceptance').prop('checked', customer.regulationAccepted);
    $('#newsletter-acceptance').prop('checked', customer.newsletterAccepted);
    $('#customer-from').text(customer.customerFrom);
    $('#demanding-customer').val(customer.demanding.toString());

    $('#name').val(customer.firstName);
    $('#surname').val(customer.lastName);
    $('#phone-number').val(customer.phoneNumber);
    $('#street').val(customer.street);
    $('#house-number').val(customer.buildingNumber);
    $('#apartment-number').val(customer.apartmentNumber);
    $('#postal-code').val(customer.postalCode);
    $('#city').val(customer.cityId);

    $('#another-weekend-address').prop('checked', customer.anotherWeekendAddress);
    showIfChecked('another-weekend-address', 'weekend-address-data');
    $('#weekend-street').val(customer.weekendStreet);
    $('#weekend-house-number').val(customer.weekendHouseNo);
    $('#weekend-apartment-number').val(customer.weekendApartmentNo);
    $('#weekend-postal-code').val(customer.weekendPostalCode);
    $('#weekend-city').val(customer.weekendCityId);

    $('#invoice').prop('checked', customer.invoice);
    showIfChecked('invoice', 'invoice-data');
    $('#invoice-company-name').val(customer.invoiceCompanyName);
    $('#invoice-tax-no').val(customer.invoiceTaxNo);
    $('#invoice-street').val(customer.invoiceStreet);
    $('#invoice-house-number').val(customer.invoiceHouseNo);
    $('#invoice-apartment-number').val(customer.invoiceApartmentNo);
    $('#invoice-postal-code').val(customer.invoicePostalCode);
    $('#invoice-city').val(customer.invoiceCity);

    $('#week-preferred-hours-to').timepicker('setTime', prepareTimeFormat(customer.weekPreferredHoursTo));

    $('#another-hours-weekend').prop('checked', customer.anotherHoursWeekend);
    showIfChecked('another-hours-weekend', 'another-hours-weekend-data');
    $('#weekend-preferred-hours-to').timepicker('setTime', prepareTimeFormat(customer.weekendPreferredHoursTo));

    $('#comment').val(customer.comment);

    $('#orders-count').text(customer.ordersCount);
}

function prepareTimeFormat(time) {
    if(time == null) {
        return "";
    } else {
        return time[0] + ":" + time[1];
    }
}

function showIfChecked(checkboxId, objectId) {
    if($('#' + checkboxId).is(':checked')) {
        $('#' + objectId).show();
    }
}

function updateCustomer() {
    $('#save-button').prop('disabled', true);
    $.ajax({
        url: "/api/customer/" + customerId,
        method: "PUT",
        data: JSON.stringify({
            firstName: $('#name').val(),
            lastName: $('#surname').val(),
            phone: $('#phone-number').val(),
            street: $('#street').val(),
            houseNumber: $('#house-number').val(),
            apartmentNumber: $('#apartment-number').val(),
            postalCode: $('#postal-code').val(),
            city: $("#city option:selected").text(),

            email: $('#email').val(),
            groupId: $("#group").children("option:selected").val(),
            statusId: $("#status").children("option:selected").val(),
            demanding: getBooleanFromSelect('demanding-customer'),

            anotherWeekendAddress: $("#another-weekend-address").is(":checked"),
            weekendStreet: $('#weekend-street').val(),
            weekendHouseNumber: $('#weekend-house-number').val(),
            weekendApartmentNumber: $('#weekend-apartment-number').val(),
            weekendPostalCode: $('#weekend-postal-code').val(),
            weekendCity: $("#weekend-city option:selected").text(),

            invoice: $("#invoice").is(":checked"),
            invoiceCompanyName: $('#invoice-company-name').val(),
            invoiceTaxNo: $('#invoice-tax-no').val(),
            invoiceStreet: $('#invoice-street').val(),
            invoiceHouseNumber: $('#invoice-house-number').val(),
            invoiceApartmentNumber: $('#invoice-apartment-number').val(),
            invoicePostalCode: $('#invoice-postal-code').val(),
            invoiceCity: $('#invoice-city').val(),

            weekPreferredHoursTo: $('#week-preferred-hours-to').val(),

            anotherHoursWeekend: $("#another-hours-weekend").is(":checked"),
            weekendPreferredHoursTo: $('#weekend-preferred-hours-to').val(),

            comment: $('#comment').val()
        }),
        contentType: "application/json"
    })
    .done(function(data){
        showOperationSuccessfulModal();
        goToCustomersPage();
        $('#save-button').prop('disabled', false);
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation(jqxhr.responseText);
        $('#save-button').prop('disabled', false);
    })
}

function getBooleanFromSelect(selectId) {
    if($('#' + selectId).children("option:selected").val() == "true") {
        return true;
    } else {
        return false;
    }
}

function showOperationSuccessfulModal() {
    $('#operation-successful-modal').modal('show');
}

function goToOrderDetailsPage(orderId) {
    window.location.href = '/order?id=' + orderId;
}

function goToCustomersPage() {
    window.location.href = '/customers';
}

function showSendEmailModal() {
    $('#send-email-modal').modal('show');
}