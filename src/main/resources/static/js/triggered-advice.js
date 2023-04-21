
$(document).ready(function() {
    $("#trigger-date-time").val(triggeredAdvice.triggerDateTime.replace("T", " "));
});

function showDeleteModal() {
    $('#delete-triggered-advice-modal').modal('show');
}

function deleteTriggeredAdvice() {
    $.ajax({
        url: "/api/adviser/triggered-advices/" + triggeredAdvice.id,
        type: "DELETE"
    })
    .done(function(response) {
        $('#delete-triggered-advice-modal').modal('hide');
        window.location.href = '/admin/triggered-advices';
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

