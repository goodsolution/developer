
var objToDeleteId;

$(document).ready(function () {

    $('#create-modal').on('hide.bs.modal', function (e) {
        clearCreateModal();
    });

    $("#filter input, #filter select, [form='filter']").on("change", function () {
        findCustomerGroups();
    });

    findCustomerGroups();
});

function findCustomerGroups() {
    $.ajax({
        url: "/admin/api/crs/customer-groups?" + preparePaginationUrl(),
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (data) {
        $("#records").empty();
        fillResults(data.customerGroups);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillResults(customerGroups) {
    customerGroups.forEach(function(customerGroup){
        fillRow(customerGroup);
    });
}

function fillRow(customerGroup) {
    $('#records').append(
        "<tr>" +
            "<td class='align-middle'>" + customerGroup.id + "</td>" +
            "<td class='align-middle'>" + customerGroup.name + "</td>" +
            "<td class='align-middle'>" + customerGroup.createDateTime + "</td>" +
            "<td class='align-middle'>" + prepareUpdateButton(customerGroup.id) + "</td>" +
            "<td class='align-middle'>" + prepareDeleteButton(customerGroup.id) + "</td>" +
        "</tr>"
    );
}

function prepareUpdateButton(id) {
    return '<button type="button" class="btn btn-primary" onclick="goToDetailsPage(' + id + ')">Update</button>';
}

function goToDetailsPage(id) {
    window.location.href = "/admin/customer-group/" + id;
}

function prepareDeleteButton(id) {
    return '<button type="button" class="btn btn-danger" onclick="setObjectToDeleteIdAndShowModal(' + id + ')">Delete</button>';
}

function setObjectToDeleteIdAndShowModal(id) {
    objToDeleteId = id;
    $('#delete-object-modal').modal('show');
}

function clearCreateModal() {
    $("#create-name").val('');
}

function sendCreateRequest() {
    $.ajax({
        url: "/admin/api/crs/customer-groups",
        method: "post",
        contentType: "application/json",
        data: JSON.stringify({
            name: $("#create-name").val()
         })
    })
        .done(function () {
            $("#create-modal").modal('hide');
            $("#operation-successful-modal").modal('show');
            findCustomerGroups();
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}

function sendDeleteRequest(){
    $.ajax({
        url: "/admin/api/crs/customer-groups/" + objToDeleteId,
        type: "DELETE"
    })
        .done(function(response) {
            $('#delete-object-modal').modal('hide');
            $('#operation-successful-modal').modal('show');
            findCustomerGroups();
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}