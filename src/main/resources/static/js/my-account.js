$(document).ready(function() {
    trace('page.show', 'my-account');
});

function performPasswordChange() {
    $("#change-password").prop("disabled", true);

    var actualPassword = $("#actual-password").val();
    var newPassword = $("#new-password").val();
    var repeatedNewPassword = $("#repeated-new-password").val();

    if(arePasswordsValid(newPassword, repeatedNewPassword)) {
        sendPasswordChangeRequest(actualPassword, newPassword);
    } else {
        $("#change-password").prop("disabled", false);
    }
}

function sendPasswordChangeRequest(actualPassword, newPassword) {
   $.ajax({
           url: "/api/crs/change-password",
           method: "post",
           contentType: "application/json",
           data: JSON.stringify(
               {
                   actualPasswordHash: hash(actualPassword),
                   newPasswordHash: hash(newPassword)
               })
       })
           .done(function () {
               window.location.href = '/data-changed-successfully';
           })
           .fail(function(jqxhr, textStatus, errorThrown){
               $("#change-password").prop("disabled", false);
               showError(prepareErrorMessage(jqxhr.responseText));
           });
}

function showError(text) {
    $("#password-change-error-alert-text").text(text);
    $("#password-change-error-alert").removeClass('d-none');
}

function sendLoggedCustomerPutRequest() {
    $.ajax({
    url: "/api/crs/logged-customer",
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(
            {
                language: $("#language").find(":selected").val(),
                newsletterAccepted: $('#newsletter-accepted').is(":checked")
            })
    })
    .done(function () {
        $('#operation-successful-modal').modal('show');
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function sendEmailConfirmationLink() {
    $.ajax({
        url: "/api/crs/send/email-confirmation-link",
        method: "POST",
        contentType: "application/json"
    })
        .done(function () {
            $("#email-not-confirmed-alert").hide();
            $("#email-confirmation-link-sent-alert").show();
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}
