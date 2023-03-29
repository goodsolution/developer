$(document).ready(function () {
    $( "#operation-successful-button" ).click(function() {
        goToDetailsPage();
    });
    $("#operation-successful-modal").on('hide.bs.modal', function(){
        goToDetailsPage();
    });
});

function sendDeleteRequest(){
    $.ajax({
        url: "/admin/api/crs/customer-group/" + customerGroup.id,
        type: "DELETE"
    })
        .done(function(response) {
            window.location.href = '/admin/customer-groups';
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

function sendUpdateRequest() {
    $.ajax({
        url: "/admin/api/crs/customer-group/" + customerGroup.id,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify({
            name: $("#name").val(),
        })
    })
        .done(function () {
            $("#operation-successful-modal").modal('show');

        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}
function goToDetailsPage() {
    window.location.href = "/admin/customer-groups/";
}