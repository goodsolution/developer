

function showDeleteModal() {
    $('#delete-advice-modal').modal('show');
}

function deleteAdvice() {
    $.ajax({
        url: "/api/adviser/advices/" + $("#advice-id").text(),
        method: "delete"
    })
        .done(function () {
            $('#delete-advice-modal').modal('hide');
            window.location.href = '/admin/advices';
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}

function saveChanges() {
    $.ajax({
        url: "/api/adviser/advices/" + $("#advice-id").text(),
        method: "put",
        contentType: "application/json",
        data: JSON.stringify({
            appId: $("#application-id").val(),
            domain: $("#domain").val(),
            type: $("#type").val(),
            scope: $("#scope").val(),
            action: $("#action").val(),
            dataType: $("#data-type").val(),
            component: $("#component").val(),
            adviceClass: $("#class").val(),
            name: $("#name").val(),
            content: $("#content").val()
        })
    })
        .done(function () {
            $("#operation-successful-modal").modal('show');
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}
