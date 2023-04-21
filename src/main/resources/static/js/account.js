
function showDeleteModal() {
    $('#delete-object-modal').modal('show');
}

function sendDeleteRequest() {
    $.ajax({
        url: "/api/adviser/accounts/" + $("#id").text(),
        method: "delete"
    })
        .done(function () {
            $('#delete-account-modal').modal('hide');
            window.location.href = '/admin/accounts';
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}

function sendUpdateAccountRequest() {
    $.ajax({
        url: "/api/adviser/accounts/" + $("#id").text(),
        method: "put",
        contentType: "application/json",
        data: JSON.stringify({
            name: $("#name").val()
        })
    })
        .done(function () {
            $("#operation-successful-modal").modal('show');
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}