
var objToDeleteId;

$(document).ready(function () {

    findContactsPeople();

    $('#create-modal').on('hide.bs.modal', function (e) {
        clearCreateModal();
    });

    $("#filter input, #filter select, [form='filter']").on("change", function () {
        findContactsPeople();
    });

});

function sendCreateRequest() {
    $.ajax({
        url: '/admin/api/crs/contacts-people',
        method: 'POST',
        contentType: "application/json",
        data: JSON.stringify({
            firstName: $("#create-first-name").val(),
            lastName: $("#create-last-name").val(),
            email: $("#create-email").val(),
            phone: $("#create-phone").val()
        }),
    })
        .done(function () {
            $("#create-modal").modal('hide');
            $("#operation-successful-modal").modal('show');
            findContactsPeople();
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}

function prepareUploadMemeUrl() {
    var url = "?";
    url+= "&title=" + $("#create-title").val();
    url+= "&content=" + $("#create-content").val();
    url+= "&language=" + $('#create-language').find(":selected").val();
    return url;
}

function clearCreateModal() {
    $("#create-title").val('');
    $("#create-content").val('');
}

function findContactsPeople() {
    $.ajax({
        url: "/admin/api/crs/contacts-people?" + preparePaginationUrl(),
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (data) {
        $("#records").empty();
        fillResults(data.people);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillResults(elements) {
    elements.forEach(function(element){
        render(element);
    });
}

function render(element) {
    $('#records').append(
        "<tr>" +
            "<td class='align-middle'>" + element.firstName + "</td>" +
            "<td class='align-middle'>" + element.lastName + "</td>" +
            "<td class='align-middle'>" + element.email + "</td>" +
            "<td class='align-middle'>" + element.phone + "</td>" +
            "<td class='align-middle'>" + prepareDeleteButton(element.id) + "</td>" +
        "</tr>"
    );
}

function prepareDetailsButton(id) {
    return '<button type="button" class="btn btn-primary" onclick="goToDetailsPage(' + id + ')">Details</button>';
}

function prepareDeleteButton(id) {
    return '<button type="button" class="btn btn-danger" onclick="setObjectToDeleteIdAndShowModal(' + id + ')">' + lang.Delete + '</button>';
}

function setObjectToDeleteIdAndShowModal(id) {
    objToDeleteId = id;
    $('#delete-object-modal').modal('show');
}

function sendDeleteRequest(){
    $.ajax({
        url: "/admin/api/crs/contacts-people/" + objToDeleteId,
        type: "DELETE"
    })
        .done(function(response) {
            $('#delete-object-modal').modal('hide');
            $('#operation-successful-modal').modal('show');
            findContactsPeople();
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

function goToDetailsPage(id) {
    window.location.href = "/admin/meme/" + id;
}