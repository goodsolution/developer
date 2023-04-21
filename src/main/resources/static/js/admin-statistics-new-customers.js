$(document).ready(function () {
    refresh();
});

function refresh() {
    $.ajax({
            url: "/admin/api/crs/statistic/new-customers",
            type: "get",
            dataType: "json",
            contentType: "application/json"
        })
        .done(function (data) {
            printResults(data);
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

function printResults(records) {
    records.forEach(function(record){
        append(record);
    });
}

function append(record) {
    $('#records').append(
               '<tr>'+
                     '<td class="align-middle">' + record.login + '</td>'+
                     '<td class="align-middle">' + record.registrationDateTime + '</td>'+ //TODO
                 '</tr>'
    );
}


