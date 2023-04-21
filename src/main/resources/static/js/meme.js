$(document).ready(function () {
    $("#creation-datetime").val(prepareDateTime(meme.creationDatetime));

    $("#upload-meme-photo").submit(function(event) {
            $.ajax({
                type: 'PUT',
                enctype: 'multipart/form-data',
                url: '/admin/api/crs/meme/' + meme.id + "/photo",
                data: new FormData(this),
                contentType: false,
                cache: false,
                processData:false,
                success: function(response){
                    window.location.reload();
                },
                error: function (jqxhr, textStatus, errorThrown) {
                    displayErrorInformation(jqxhr.responseText);
                }
            });
            event.preventDefault();
        });
});

function sendDeleteRequest(){
    $.ajax({
        url: "/admin/api/crs/meme/" + meme.id,
        type: "DELETE"
    })
        .done(function(response) {
            window.location.href = '/admin/memes';
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

function sendUpdateRequest() {
    $.ajax({
        url: "/admin/api/crs/meme/" + meme.id,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify({
            title: $("#title").val(),
            language: $('#language').find(":selected").val(),
            description: $("#description").val(),
            keywords: $("#keywords").val(),
            code: $("#code").val()
        })
    })
        .done(function () {
            $("#operation-successful-modal").modal('show');
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}