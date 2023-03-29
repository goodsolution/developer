
let sendNotificationsButton;
let notificationsProcessingButton;

$(document).ready(function () {
    sendNotificationsButton = $("#send-notifications-button");
    notificationsProcessingButton = $("#notifications-processing-button");
});


function sendNotifications() {
    setButtonProcessing();
    $.ajax({
        url: "/admin/api/crs/notifications",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            title: $("#title").val(),
            content: $("#content").val(),
            link: $("#link").val(),
            language: $("#language").val()
        })
    })
        .done(function () {
            setButtonReady();
            $("#operation-successful-modal").modal('show');
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            setButtonReady();
            displayErrorInformation(jqxhr.responseText);
        })
}

function setButtonReady() {
    notificationsProcessingButton.hide();
    sendNotificationsButton.show();
}

function setButtonProcessing() {
    sendNotificationsButton.hide();
    notificationsProcessingButton.show();
}