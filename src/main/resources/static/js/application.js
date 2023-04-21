
function showDeleteModal() {
    $('#delete-object-modal').modal('show');
}

function sendDeleteRequest() {
    $.ajax({
        url: "/api/adviser/applications/" + $("#id").text(),
        method: "delete"
    })
        .done(function () {
            $('#delete-object-modal-modal').modal('hide');
            window.location.href = '/admin/applications';
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}

function sendUpdateRequest() {
    $.ajax({
        url: "/api/adviser/applications/" + $("#id").text(),
        method: "put",
        contentType: "application/json",
        data: JSON.stringify({
            applicationId: $("#application-id").val(),
            description: $("#description").val(),
            secretKey: $("#secret-key").val()
        })
    })
        .done(function () {
            $("#operation-successful-modal").modal('show');
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}
function generateSecretKey() {
    $.ajax({
        url: "/api/public/adviser/generate-secret-key",
        method: "get"
    })
        .done(function (secretKey) {
            $("#secret-key").val(secretKey);
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}
