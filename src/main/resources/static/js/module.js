
function sendDeleteRequest(){
    $.ajax({
        url: "/admin/api/crs/module/" + module.id,
        type: "DELETE"
    })
        .done(function(response) {
            $('#delete-object-modal').modal('hide');
            window.location.href = '/admin/course/' + module.course.id;
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

function showDeleteModal() {
    $('#delete-object-modal').modal('show');
}

function sendUpdateRequest() {
    $.ajax({
        url: "/admin/api/crs/module/" + module.id,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify({
            name: $("#name").val(),
            visibilityStatus: $("#visibility-status").val()
        })
    })
        .done(function () {
            $("#operation-successful-modal").modal('show');
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}