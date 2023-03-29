function sendDeleteRequest(){
    $.ajax({
        url: "/admin/api/crs/author/" + author.id,
        type: "DELETE"
    })
        .done(function(response) {
            window.location.href = '/admin/authors';
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

function sendUpdateRequest() {
    $.ajax({
        url: "/admin/api/crs/author/" + author.id,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify({
            firstName: $("#first-name").val(),
            lastName: $("#last-name").val(),
        })
    })
        .done(function () {
            $("#operation-successful-modal").modal('show');
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}