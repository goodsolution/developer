$(document).ready(function () {

    $("#operation-successful-button").click(function () {
        goToITubesPage();
    });

    $("#operation-successful-modal").on('hide.bs.modal', function () {
        goToITubesPage();
    });

});

function sendUpdateRequest() {
    $.ajax({
        url: "/admin/api/crs/itube-page/" + itube.id,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify({
            titlePl: $("#titlePl").val(),
            titleEn: $("#titleEn").val(),
            keywords: $("#keywords").val(),
            link: $("#link").val(),
        })
    })
        .done(function () {
            $("#operation-successful-modal").modal('show');
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}

function goToITubesPage() {
    window.location.href = "/admin/itubes/";
}