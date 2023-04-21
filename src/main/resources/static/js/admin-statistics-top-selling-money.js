$(document).ready(function () {
    refresh();
});

function refresh() {
    $.ajax({
            url: "/admin/api/crs/statistic/top-selling-products-for-money",
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
                     '<td class="align-middle">' + record.name + '</td>'+
                     '<td class="align-middle">' + record.count + '</td>'+
                     '<td class="align-middle">' + record.sum + '</td>'+
                 '</tr>'
    );
}


