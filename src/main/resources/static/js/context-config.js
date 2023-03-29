function showDeleteModal() {
    $('#delete-object-modal').modal('show');
}

function sendDeleteRequest() {
    $.ajax({
        url: "/api/adviser/context-configs/" + $("#id").text(),
        method: "delete"
    })
        .done(function () {
            $('#delete-object-modal').modal('hide');
            window.location.href = '/admin/context-configs';
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}

function saveChanges() {
    $.ajax({
        url: "/api/adviser/context-configs/" + $("#id").text(),
        method: "put",
        contentType: "application/json",
        data: JSON.stringify({
            applicationId: $("#application-id").val(),
            context: $("#context").val(),
            name: $("#name").val(),
            type: $("#type").val(),
            value: $("#value").val()
        })
    })
        .done(function () {
            $("#operation-successful-modal").modal('show');
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}