
var objToDeleteId;

$(document).ready(function () {

    findMemes();

    $('#create-modal').on('hide.bs.modal', function (e) {
        clearCreateModal();
    });

    $("#filter input, #filter select, [form='filter']").on("change", function () {
        findMemes();
    });

    $("#upload-meme").submit(function(event) {
        $.ajax({
            type: 'POST',
            enctype: 'multipart/form-data',
            url: '/admin/api/crs/meme' + prepareUploadMemeUrl(),
            data: new FormData(this),
            contentType: false,
            cache: false,
            processData:false,
            success: function(response){
                $("#create-modal").modal('hide');
                $('#operation-successful-modal').modal('show');
                findMemes();
            },
            error: function (jqxhr, textStatus, errorThrown) {
                displayErrorInformation(jqxhr.responseText);
            }
        });
        event.preventDefault();
    });

});

function prepareUploadMemeUrl() {

    var url = "?";

    url+= "&title=" + $("#create-title").val();
    url+= "&code=" + $("#create-code").val();
    url+= "&description=" + $("#create-description").val();
    url+= "&keywords=" + $("#create-keywords").val();
    url+= "&language=" + $('#create-language').find(":selected").val();

    return url;
}

function clearCreateModal() {
    $("#create-title").val('');
    $("#create-code").val('');
    $("#create-description").val('');
    $("#create-keywords").val('');
    $("#create-meme-file").val('');
}

function findMemes() {
    $.ajax({
        url: "/admin/api/crs/memes?" + preparePaginationUrl(),
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (data) {
        $("#records").empty();
        fillResults(data.memes);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillResults(memes) {
    memes.forEach(function(meme){
        fillRow(meme);
    });
}

function fillRow(meme) {
    $('#records').append(
        "<tr>" +
            "<td class='align-middle'>" + meme.id + "</td>" +
            "<td class='align-middle'>" + meme.title + "</td>" +
            "<td class='align-middle'>" + prepareDateTime(meme.creationDatetime) + "</td>" +
            "<td class='align-middle'>" + meme.language + "</td>" +
            "<td class='align-middle'>" + meme.code + "</td>" +
            "<td class='align-middle'>" + prepareDetailsButton(meme.id) + "</td>" +
            "<td class='align-middle'>" + prepareDeleteButton(meme.id) + "</td>" +
        "</tr>"
    );
}

function prepareDetailsButton(id) {
    return '<button type="button" class="btn btn-primary" onclick="goToDetailsPage(' + id + ')">Details</button>';
}

function prepareDeleteButton(id) {
    return '<button type="button" class="btn btn-danger" onclick="setObjectToDeleteIdAndShowModal(' + id + ')">Delete</button>';
}

function setObjectToDeleteIdAndShowModal(id) {
    objToDeleteId = id;
    $('#delete-object-modal').modal('show');
}

function sendDeleteRequest(){
    $.ajax({
        url: "/admin/api/crs/meme/" + objToDeleteId,
        type: "DELETE"
    })
        .done(function(response) {
            $('#delete-object-modal').modal('hide');
            $('#operation-successful-modal').modal('show');
            findMemes();
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

function goToDetailsPage(id) {
    window.location.href = "/admin/meme/" + id;
}