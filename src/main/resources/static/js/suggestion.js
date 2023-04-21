
function sendSuggestionRequest() {
    $.ajax({
        url: "/api/public/life_adviser/suggestion",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            suggestion: $("#suggestion").val(),
                        applicationId: 'life-adviser',
                        domain:'main-stream',
                        domainId:'2',
                        secret:'2b2221ed-43d3-4834-8bd9-a0454f17037f-1592039601774-718d6284-2250-4af7-b700-2cd067e0cc83'
        })
    })
        .done(function () {
            $("#operation-successful-modal").modal('show');
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}