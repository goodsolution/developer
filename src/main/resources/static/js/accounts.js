let objToDeleteId;

$(document).ready(function () {
    findAccounts();

    $("#filter input, #filter select, [form='filter']").on("change", function () {
        findAccounts();
    });

    $('#create-account-modal').on('hide.bs.modal', function (e) {
        $("#create-account-name").val('');
    });

});

function findAccounts() {
    $.ajax({
        url: "/api/adviser/accounts?name=" + $("#name").val() + preparePaginationUrl(),
        method: "get",
        contentType: "application/json",
        dataType: "json"
    })
        .done(function (data) {
            clearTable();
            fillResults(data);
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

function clearTable() {
    $("#records").empty();
}

function fillResults(data) {
    $("#count").text(data.totalAccountsCount);
    fillTable(data.accounts);
}

function fillTable(accounts) {
    accounts.forEach(function(account) {
        fillRow(account);
    });
}

function fillRow(account) {
    $('#records').append(
        "<tr>" +
            "<td class='align-middle'>" + account.id + "</td>" +
            "<td class='align-middle'>" + prepareText(account.name) + "</td>" +
            "<td class='align-middle'>" + prepareDetailsButton(account.id) + "</td>" +
            "<td class='align-middle'>" + prepareDeleteButton(account.id) + "</td>" +
        "</tr>"
    );
}

function prepareDetailsButton(id) {
    return '<button type="button" class="btn btn-primary" onclick="goToAccountDetailsPage(' + id + ')">Details</button>';
}

function prepareDeleteButton(id) {
    return '<button type="button" class="btn btn-danger" onclick="setObjectToDeleteIdAndShowModal(' + id + ')">Delete</button>';
}

function sendCreateAccountRequest() {
    $.ajax({
        url: "/api/adviser/accounts",
        method: "post",
        contentType: "application/json",
        data: JSON.stringify({
            name: $("#create-account-name").val()
        })
    })
        .done(function () {
            $("#create-account-modal").modal('hide');
            $("#operation-successful-modal").modal('show');
            findAccounts();
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}

function setObjectToDeleteIdAndShowModal(id) {
    objToDeleteId = id;
    $('#delete-object-modal').modal('show');
}

function sendDeleteRequest() {
    $.ajax({
        url: "/api/adviser/accounts/" + objToDeleteId,
        type: "delete"
    })
        .done(function() {
            $('#delete-object-modal').modal('hide');
            $('#operation-successful-modal').modal('show');
            findAccounts();
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

function goToAccountDetailsPage(id) {
    window.location.href = "/admin/account/" + id;
}

function clearSearchFiltersAndRefresh() {
    $("#name").val("");
    findAccounts();
}