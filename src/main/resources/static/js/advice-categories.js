$(document).ready(function () {

    $("#filter input, #filter select, [form='filter']").on("change", function () {
        findAdviceCategories();
    });

    findAdviceCategories();
});

function findAdviceCategories() {
    $.ajax({
        url: "/api/adviser/advice-categories?" + preparePaginationUrl(),
        method: "get",
        dataType: "json",
        contentType: "application/json"
    })
        .done(function (data) {
            $("#records").empty();
            fillResults(data);
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

function fillResults(data) {
    $("#count").text(data.totalAdviceCategoriesCount);
    fillTable(data.adviceCategories);
}

function fillTable(adviceCategories) {
    adviceCategories.forEach(function(adviceCategory) {
        fillRow(adviceCategory);
    });
}

function fillRow(adviceCategory) {
    $('#records').append(
        "<tr>" +
        "<td class='align-middle'>" + prepareText(adviceCategory.name) + "</td>" +
        "<td class='align-middle'>" + prepareText(adviceCategory.applicationId) + "</td>" +
        "<td class='align-middle'>" + prepareText(adviceCategory.context) + "</td>" +
        "<td class='align-middle'>" + adviceCategory.price + " " + adviceCategory.currencyCode + "</td>" +
        "</tr>"
    );
}