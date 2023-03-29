$(document).ready(function () {
    fillRoles();
    fillGroups();
});

function fillRoles() {
       customer.authorities.forEach(function(authority){
           $('#'+authority).attr('checked', true);
       })
}
function fillGroups() {
       customer.groups.forEach(function(group){
           $('#customer-group-' + group.customerGroupId).attr('checked', true);
       })
}
function sendDeleteRequest(){
    $.ajax({
        url: "/admin/api/crs/customer/" + customer.id,
        type: "DELETE"
    })
        .done(function(response) {
            window.location.href = '/admin/customers';
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

 function performPasswordChange() {

        $("#change-password-button").prop( "disabled", true );

        var password = $("#new-password").val();
        var repeatedPassword = $("#repeated-new-password").val();

        if(arePasswordsValid(password, repeatedPassword)) {
            sendPasswordChangeRequest(password);
        } else {
            $("#change-password-button").prop( "disabled", false );
        }
}


function sendPasswordChangeRequest(password) {
       $.ajax({
               url: "/admin/api/crs/customer-change-password/"  + customer.id,
               method: "put",
               contentType: "application/json",
               data: JSON.stringify(
                   {
                       newPasswordHash: hash(password),
                   })
           })
               .done(function () {
                   $("#operation-successful-modal").modal('show');
               })
               .fail(function(jqxhr, textStatus, errorThrown){
                   $("#change-password-button").prop("disabled", false);
                   showError(prepareErrorMessage(jqxhr.responseText));
               });
}






function sendUpdateRequest() {
    $.ajax({
        url: "/admin/api/crs/customer/" + customer.id,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify({
            invoiceFirstAndLastName: $("#first-and-last-name").val(),
            login: $("#login").val(),
            invoiceType: $("#invoice-type").find(":selected").val(),
            invoiceCompanyName: $("#invoice-company-name").val(),
            invoiceStreet: $("#invoice-street").val(),
            invoicePostalCode: $("#invoice-postal-code").val(),
            invoiceCity: $("#invoice-city").val(),
            invoiceNip: $("#invoice-nip").val(),
            invoiceCountry: $("#invoice-country").find(":selected").val(),
            language: $("#language").find(":selected").val(),
            isEnabled: $("#enabled").find(":selected").val(),
            registrationDatetime: $("#registration-datetime").val(),
            regulationAccepted: $("#regulation").find(":selected").val(),
            newsletterAccepted: $("#newsletter").find(":selected").val(),
            isEmailConfirmed: $("#email-confirmed").find(":selected").val(),
            authorities : getAuthorities(),
            groupIds : getGroupIds()

        })
    })
        .done(function () {
            $("#operation-successful-modal").modal('show');
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
            $("#save-changes-button").prop( "disabled", false );
            showError(prepareErrorMessage(jqxhr.responseText));
        })
}
function getAuthorities(){
         var authorities = [];
         for(i = 0; i < authorityDict.length; i++) {
             if($('#'+authorityDict[i].code).is(":checked")){
                 authorities.push(authorityDict[i].code);
             }
         }
          return authorities;
 }
 function getGroupIds(){
         var groupIds = [];
         for(i = 0; i < customerGroups.length; i++) {
             if($('#customer-group-'+customerGroups[i].id).is(":checked")){
                 groupIds.push(customerGroups[i].id);
             }
         }
          return groupIds;
 }

function showError(text) {
     $("#error-alert-text").text(text);
     $("#error-alert").removeClass('d-none');
}
