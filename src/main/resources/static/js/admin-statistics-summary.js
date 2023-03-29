$(document).ready(function () {


});

function refreshStatistic(type) {
    $.ajax({
            url: "/admin/api/crs/statistic/" + type,
            type: "get",
            dataType: "json",
            contentType: "application/json"
        })
        .done(function (data) {
            $("#" + data.type).html(data.value);
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

function refreshStatisticWithParam(type, param) {
    var select = document.getElementById(param);
    var choice = select.value;
    $.ajax({
            url: "/admin/api/crs/statistic/" + type + "?param=" + choice,
            type: "get",
            dataType: "json",
            contentType: "application/json"
        })
        .done(function (data) {
            $("#" + data.type).html(data.value);
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}


function refreshStatisticWithValue(type, param, value) {
    var select = document.getElementById(param);
    var choice = select.value;

    var number = document.getElementById(value);
    var numberValue = number.value;

    if (numberValue >= 1 && numberValue <= 100){
        $.ajax({
            url: "/admin/api/crs/statistic/" + type + "?param=" + choice+ "&value=" + numberValue,
            type: "get",
            dataType: "json",
            contentType: "application/json"
        })
        .done(function (data) {
            $("#" + data.type).html(data.value);
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });

    } else {
        var error = document.getElementById("error")
        error.textContent = "You can enter only numbers in 1-100 range"
        error.style.color = "red"
        }
}

