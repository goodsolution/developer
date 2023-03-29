
var customerToDeleteId;

var sortRecordsBy = 'id';
var recordsArrangement = 'ASC';

$(document).ready(function() {
    setDefaultPageSize();
    setInteractionWithForms();
    fillSelectsWithDictionaries();
    findCustomers();
});

function fillSelectsWithDictionaries() {
    fillSelectWithDict('group-id', customerGroupsDict);
    fillSelectWithDict('new-customer-group-id', customerGroupsDict);
    fillSelectWithDict('status-id', customerStatusesDict);
    fillSelectWithDict('new-customer-status-id', customerStatusesDict);
}

function fillSelectWithDict(selectId, dict) {
    dict.forEach(function(objInDict) {
        $('#' + selectId + '').append('<option value="' + objInDict.id + '">' + htmlEncode(objInDict.value) + '</option>');
    });
}

function setDefaultPageSize() {
    $("#page_size").val(100);
}

function setInteractionWithForms() {
    $("#filter input, #filter select, [form='filter']").on("change", function () {
        findCustomers();
    });
}

function findCustomers() {
    $.ajax({
        url: "/api/customers?" + prepareUrl(),
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (data) {
        setSortingArrow();
        fillResults(data);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function prepareUrl() {
    var url = "";

    var page_size = $("#page_size").children(":selected").val();
        if (page_size != "") {
            url += "&page_size=" + page_size;
        }

    var page = $("#page").val();
        if (page != "" && !isNaN(page)) {
            url += "&page=" + page;
        }

    var nameAndSurname = $("#name-and-surname").val();
        if (nameAndSurname != "") {
            url += "&name_and_surname=" + nameAndSurname;
        }

    var email = $("#email").val();
        if (email != "") {
            url += "&email=" + email;
        }

    var groupId = $("#group-id").children(":selected").val();
        if (groupId != "" && groupId != 0) {
            url += "&group_id=" + groupId;
        }

    var statusId = $("#status-id").children(":selected").val();
        if (statusId != "" && statusId != 0) {
            url += "&status_id=" + statusId;
        }

    url += "&sort_records_by=" + sortRecordsBy;

    url += "&records_arrangement=" + recordsArrangement;

    return url;
}

function fillResults(data) {
    fillCustomersCounter(data.count);
    clearTable();
    fillTable(data.customers);
}

function clearTable() {
    $("#records").empty();
}

function fillCustomersCounter(count) {
    $('#count').text(count);
}

function fillTable(customers) {
    customers.forEach(function(record) {
        prepareRowInTable(record);
    });
}

function prepareRowInTable(record) {
    $('#records').append(
        "<tr>" +
            "<td class='align-middle'> <input type='checkbox'> </td>" +
            "<td class='align-middle'>" + htmlEncode(record.name) + " " + htmlEncode(record.surname) + "</td>" +
            "<td class='align-middle'>" + prepareAddressCell(record) + "</td>" +
            "<td class='align-middle'>" + htmlEncode(record.email) + "</td>" +
            "<td class='align-middle'>" + record.registrationDate + "</td>" +
            "<td class='align-middle'>" + getDictValueByCode(customerTypesDict, record.type) + "</td>" +
            "<td class='align-middle'>" + getDictValueById(customerGroupsDict, record.groupId) + "</td>" +
            "<td class='align-middle'>" + record.ordersCount + "</td>" +
            "<td class='align-middle'>" + getDictValueById(customerStatusesDict, record.active) + "</td>" +
            "<td class='align-middle'>" + prepareButtonsCell(record.id) + "</td>" +
        "</tr>"
    );
}

function getDictValueById(dict, id) {
    var value = "";

    for (var i = 0; i < dict.length; i++) {
        if(id == dict[i].id) {
            value = dict[i].value;
            break;
        }
    }

    return htmlEncode(value);
}

function getDictValueByCode(dict, code) {
    var value = "";

    for (var i = 0; i < dict.length; i++) {
        if(code == dict[i].code) {
            value = dict[i].value;
            break;
        }
    }

    return htmlEncode(value);
}

function prepareAddressCell(record) {

    var addressFirstLine = record.addressFirstLine;
    var addressSecondLine = record.addressSecondLine;
    var houseNumber = record.houseNumber;
    var apartmentNumber = record.apartmentNumber;
    var postalCode = record.postalCode;
    var city = record.city;

    var addressCell = "";

    addressCell+= addressFirstLine;

    if(addressCell != "" && addressSecondLine != "") {
        addressCell+= " ";
    }
    addressCell+= addressSecondLine;

    if(addressCell != "" && houseNumber != "") {
        addressCell+= ", ";
    }
    addressCell+= houseNumber;

    if(houseNumber != "" && apartmentNumber != "") {
        addressCell+= "&#47;";
    } else if(addressCell != "" && houseNumber == "" && apartmentNumber != "") {
        addressCell+= ", ";
    }
    addressCell+= apartmentNumber;

    if(addressCell != "" && postalCode != "") {
        addressCell+= ", ";
    }
    addressCell+= postalCode;

    if(addressCell != "" && city != "") {
        if(postalCode != "") {
            addressCell+= " ";
        } else {
            addressCell+= ", ";
        }
    }
    addressCell+= city;

    return htmlEncode(addressCell);
}

function prepareButtonsCell(customerId) {

    var buttonCell =
    '<button type="button" onclick="goToCustomerDetailsPage(' + customerId + ')" class="d-inline">Podgląd</button>' +
    '<button type="button" class="d-inline" onclick="setCustomerIdToDeleteAndShowModal(' + customerId + ')">Usuń</button>' +
    '<button type="button" class="d-block mx-auto" onclick="openAddOrderModal(' + customerId + ')">Nowe zamówienie</button>';

    return buttonCell;
}

function goToCustomerDetailsPage(customerId) {
    window.location.href = '/customer?&id=' + customerId;
}

function setCustomerIdToDeleteAndShowModal(customerId) {
    setCustomerToDeleteId(customerId);
    showDeleteCustomerModal();
}

function setCustomerToDeleteId(customerId) {
    customerToDeleteId = customerId;
}

function showDeleteCustomerModal() {
    $('#delete-customer-modal').modal('show');
}

function deleteCustomer(id) {
    $.ajax({
        url: "/api/customers/" + id,
        type: "DELETE"
    })
    .done(function(response) {
        hideDeleteCustomerModal();
        findCustomers();
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function hideDeleteCustomerModal() {
    $('#delete-customer-modal').modal('hide');
}

function clearSearchFiltersAndRefresh() {
    clearSearchFilers();
    findCustomers();
}

function clearSearchFilers() {
    $("#name-and-surname").val("");
    $("#email").val("");
    $("#group-id").val(0);
    $("#status-id").val(0);
}

function setSortPropsAndRefresh(sortRecordsByReq) {
    setRecordsSorting(sortRecordsByReq);
    findCustomers();
}

function setRecordsSorting(sortRecordsByReq) {
    if(sortRecordsByReq != sortRecordsBy) {
        sortRecordsBy = sortRecordsByReq;
        recordsArrangement = 'ASC';
    } else {
        if(recordsArrangement == 'ASC') {
            recordsArrangement = 'DESC';
        } else {
            recordsArrangement = 'ASC';
        }
    }
}

function setSortingArrow() {
    clearAllSortingArrows();
    showSortingArrow(getSortingArrowImageSrc(), getSortingArrowImageId());
}

function clearAllSortingArrows() {
    $(".sorting-arrow-image").attr("src", "");
}

function getSortingArrowImageSrc() {
    if(recordsArrangement == "ASC") {
        return "img/downArrow.png";
    } else if(recordsArrangement == "DESC") {
        return "img/upArrow.png";
    } else {
        return "";
    }
}

function getSortingArrowImageId() {
    if(sortRecordsBy == "name") {
        return "name-and-surname-sorting-arrow-image";
    } else if(sortRecordsBy == "address_line_1") {
        return "address-sorting-arrow-image";
    } else if(sortRecordsBy == "email") {
        return "email-sorting-arrow-image";
    } else if(sortRecordsBy == "registration_date") {
        return "registration-date-sorting-arrow-image";
    } else if(sortRecordsBy == "type") {
        return "type-sorting-arrow-image";
    } else if(sortRecordsBy == "group_id") {
        return "group-id-sorting-arrow-image";
    } else if(sortRecordsBy == "orders_count") {
        return "orders-count-sorting-arrow-image";
    } else if(sortRecordsBy == "active") {
        return "active-count-sorting-arrow-image";
    } else {
        return "";
    }
}

function showSortingArrow(imageSrc, imageId) {
    if(imageSrc != "" && imageId != ""){
        $("#" + imageId).attr("src", imageSrc);
    }
}

function postNewCustomerAndGoToDetailsPage() {
    postNewCustomer().done(function(postedCustomerId){
        goToCustomerDetailsPage(postedCustomerId);
    });
}

function postNewCustomer() {
    var postedCustomerId = $.ajax({
    url: "/api/customers",
    method: "POST",
    data: JSON.stringify({
        mail: $("#new-customer-mail").val(),
        group: $("#new-customer-group-id").val(),
        status: $("#new-customer-status-id").val(),
        name: $("#new-customer-name").val(),
        surname: $("#new-customer-surname").val(),
        street: $("#new-customer-street").val(),
        houseNumber: $("#new-customer-house-number").val(),
        apartmentNumber: $("#new-customer-apartment-number").val(),
        postalCode: $("#new-customer-postal-code").val(),
        city: $("#new-customer-city").val(),
        customerFrom: "admin",
        type: "retail"
    }),
        contentType: "application/json",
        dataType: "json"
    })
    .done(function (response) {
        hideAddCustomerModal();
        showOperationSuccessfulModal();
        findCustomers();
    })
    .fail(function(jqxhr, textStatus, errorThrown){
         displayErrorInformation(jqxhr.responseText);
    });
    return postedCustomerId;
}

function clearFieldsInAddCustomerModal() {
    $("#new-customer-mail").val("");
    $("#new-customer-group-id").prop("selectedIndex", 0);
    $("#new-customer-status-id").prop("selectedIndex", 0);
    $("#new-customer-name").val("");
    $("#new-customer-surname").val("");
    $("#new-customer-street").val("");
    $("#new-customer-house-number").val("");
    $("#new-customer-apartment-number").val("");
    $("#new-customer-postal-code").val("");
    $("#new-customer-city").val("");
}

function showClearedAddCustomerModal() {
    clearFieldsInAddCustomerModal();
    showAddCustomerModal();
}

function showAddCustomerModal() {
    $('#add-customer-modal').modal('show');
}

function hideAddCustomerModal() {
    $('#add-customer-modal').modal('hide');
}

function showOperationSuccessfulModal() {
    $('#operation-successful-modal').modal('show');
}