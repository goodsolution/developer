$(document).ready(function () {
    trace('page.show', 'my-advises');
    find();
});

function find() {
    $.ajax({
        url: "/api/adviser/triggered-advices?application_id=inner-voice&context=inner-voice&customer_id=" + loggedCustomer.id + "&status=S&mode=ADVISES",
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (advises) {
        $("#records").empty();
        fillResults(advises.triggeredAdvices);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillResults(advises) {

    printContent(advises);

}

function printContent(advises) {
    advises.forEach(function(advise){
        fillRow(advise);
    });
}

function fillRow(advise) {
    $('#records').append(
        "<tr>" +
            "<td class='align-middle text-center'>" + advise.title + "</td>" +
            "<td class='align-middle text-center'>" + advise.content + "</td>" +
        "</tr>"
    );
}

