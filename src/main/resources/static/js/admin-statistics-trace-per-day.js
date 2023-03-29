function refresh() {
    $.ajax({
        url: "/admin/api/crs/statistics/trace-per-day?month=" + $( "#select_MONTH" ).val(),
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (data) {
        $("#records").empty();
        fillResults(data.traces);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillResults(elements) {
    elements.forEach(function(element){
        render(element);
    });
}

function render(element) {
    $('#records').append(
        "<tr>" +
            "<td class='align-middle'>" + element.date + "</td>" +
            "<td class='align-middle'>" + element.what + "</td>" +
            "<td class='align-middle'>" + element.value + "</td>" +
            "<td class='align-middle'>" + element.total + "</td>" +
        "</tr>"
    );
}








