var objToDeleteId;

$(document).ready(function () {

    findITubes();

    $('#create-modal').on('hide.bs.modal', function () {
        clearCreateModal();
    });

    $("#filter input, #filter select, [form='filter']").on("change", function () {
        findITubes();
    });

    $('#create-modal').on('keypress',function(e) {
        if(e.which === 13) {
            sendCreateRequest();
        }
    });

});

function sendCreateRequest() {
    $.ajax({
        url: '/admin/api/crs/itubes',
        method: 'POST',
        contentType: "application/json",
        data: JSON.stringify({
            titlePl: $("#create-title-pl").val(),
            titleEn: $("#create-title-en").val(),
            keywords: $("#create-keywords").val(),
            link: $("#create-link").val()
        }),
    })
        .done(function () {
            clearCreateModal();
            $("#create-modal").modal('hide');
            $("#operation-successful-modal").modal('show');
            findITubes();
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}

function clearCreateModal() {
    $("#create-title-pl").val('');
    $("#create-title-en").val('');
    $("#create-keywords").val('');
    $("#create-link").val('');
}

function findITubes() {
    $.ajax({
        url: "/admin/api/crs/itubes?" + preparePaginationUrl(),
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
        .done(function (data) {
            $("#records").empty();
            fillResults(data.iTubesGetResponseAdmin);
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        });
}

function fillResults(elements) {
    elements.forEach(function (element) {
        render(element);
    });
}

function render(element) {
    $('#records').append(
        "<tr>" +
        "<td class='align-middle'>" + element.id + "</td>" +
        "<td class='align-middle'>" + element.titlePl + "</td>" +
        "<td class='align-middle'>" + element.titleEn + "</td>" +
        "<td class='align-middle'>" + element.keywords + "</td>" +
        "<td class='align-middle'>" + element.link + "</td>" +
        "<td class='align-middle'>" + prepareDateTime(element.createDateTime) + "</td>" +
        "<td class='align-middle'>" + prepareDetailsButton(element.id) + "</td>" +
        "<td class='align-middle'>" + prepareDeleteButton(element.id) + "</td>" +
        "</tr>"
    );
}

function prepareDetailsButton(id) {
    return '<button type="button" class="btn btn-primary" onclick="goToDetailsPage(' + id + ')">' + lang.Edit + '</button>';
}

function prepareDeleteButton(id) {
    return '<button type="button" class="btn btn-danger" onclick="setObjectToDeleteIdAndShowModal(' + id + ')">' + lang.Delete + '</button>';
}

function setObjectToDeleteIdAndShowModal(id) {
    objToDeleteId = id;
    $('#delete-object-modal').modal('show');
}

function sendDeleteRequest() {
    $.ajax({
        url: "/admin/api/crs/itubes/" + objToDeleteId,
        type: "DELETE"
    })
        .done(function (response) {
            $('#delete-object-modal').modal('hide');
            $('#operation-successful-modal').modal('show');
            findITubes();
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        });
}

function goToDetailsPage(id) {
    window.location.href = "/admin/itube-page/" + id;
}