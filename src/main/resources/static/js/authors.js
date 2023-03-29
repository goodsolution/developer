
var objToDeleteId;

$(document).ready(function () {

    $('#create-modal').on('hide.bs.modal', function (e) {
        clearCreateModal();
    });

    $("#filter input, #filter select, [form='filter']").on("change", function () {
        findAuthors();
    });

    findAuthors();
});

function findAuthors() {
    $.ajax({
        url: "/admin/api/crs/authors?" + preparePaginationUrl(),
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (data) {
        $("#records").empty();
        fillResults(data.authors);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillResults(authors) {
    authors.forEach(function(author){
        fillRow(author);
    });
}

function fillRow(author) {
    $('#records').append(
        "<tr>" +
            "<td class='align-middle'>" + author.id + "</td>" +
            "<td class='align-middle'>" + author.firstName + " " + author.lastName + "</td>" +
            "<td class='align-middle'>" + prepareDetailsButton(author.id) + "</td>" +
            "<td class='align-middle'>" + prepareDeleteButton(author.id) + "</td>" +
        "</tr>"
    );
}

function prepareDetailsButton(id) {
    return '<button type="button" class="btn btn-primary" onclick="goToDetailsPage(' + id + ')">Details</button>';
}

function goToDetailsPage(id) {
    window.location.href = "/admin/author/" + id;
}

function prepareDeleteButton(id) {
    return '<button type="button" class="btn btn-danger" onclick="setObjectToDeleteIdAndShowModal(' + id + ')">Delete</button>';
}

function setObjectToDeleteIdAndShowModal(id) {
    objToDeleteId = id;
    $('#delete-object-modal').modal('show');
}

function clearCreateModal() {
    $("#create-first-name").val('');
    $("#create-last-name").val('');
}

function sendCreateRequest() {
    $.ajax({
        url: "/admin/api/crs/author",
        method: "post",
        contentType: "application/json",
        data: JSON.stringify({
            firstName: $("#create-first-name").val(),
            lastName: $("#create-last-name").val()
        })
    })
        .done(function () {
            $("#create-modal").modal('hide');
            $("#operation-successful-modal").modal('show');
            findAuthors();
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}

function sendDeleteRequest(){
    $.ajax({
        url: "/admin/api/crs/author/" + objToDeleteId,
        type: "DELETE"
    })
        .done(function(response) {
            $('#delete-object-modal').modal('hide');
            $('#operation-successful-modal').modal('show');
            findAuthors();
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}