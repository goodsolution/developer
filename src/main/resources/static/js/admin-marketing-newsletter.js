$(document).ready(function () {


});

function send() {

    $.ajax({
            url: "/admin/api/crs/marketing/newsletter",
            type: "post",
            //dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({
                        content: {
                            pl: {
                                title: $("#title_pl").val(),
                                html: $("#html_pl").val()
                            },
                            en: {
                                title: $("#title_en").val(),
                                html: $("#html_en").val()
                            }
                        }
            })
        })
        .done(function (data) {
            $("#operation-successful-modal").modal('show');
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}



