
function performUserRegistration() {

    $("#register-button").prop( "disabled", true );

    var login = $("#login").val();
    var password = $("#password").val();
    var repeatedPassword = $("#repeated-password").val();
    var language = $("#language").find(":selected").val();

    if(arePasswordsValid(password, repeatedPassword)) {
        sendUserPostRequest(login, password, language);
    } else {
        $("#register-button").prop( "disabled", false );
    }
}

function sendUserPostRequest(login, password, language) {
   $.ajax({
           url: "/api/crs/register-teacher",
           method: "post",
           contentType: "application/json",
           data: JSON.stringify(
               {
                   login: login,
                   passwordHash: hash(password),
                   language: language,
                   regulationAccepted: $("#regulation").prop('checked'),
                   newsletterAccepted: $("#newsletter").prop('checked'),
                   authorFirstName: $("#first-name").val(),
                   authorLastName: $("#last-name").val(),
               })
       })
           .done(function () {
               window.location.href = '/registered-successfully';
           })
           .fail(function(jqxhr, textStatus, errorThrown){
               $("#register-button").prop( "disabled", false );
               showError(prepareErrorMessage(jqxhr.responseText));
           });
}

function showError(text) {
    $("#error-alert-text").text(text);
    $("#error-alert").removeClass('d-none');
}
