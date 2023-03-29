$(document).ready(function () {
    trace('page.show', 'my-advise-categories');
    find();
});

function find() {
    $.ajax({
        url: "/api/adviser/my-categories?application_id=inner-voice&context=inner-voice",
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (categories) {
        $("#records").empty();
        fillResults(categories);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillResults(categories) {

    printContent(categories);

}

function printContent(orders) {
    orders.forEach(function(category){
        fillRow(category);
    });
}

function fillRow(category) {
    $('#records').append(
        "<tr>" +
            "<td class='align-middle text-center'>" + category.name + "</td>" +
            "<td class='align-middle text-center'>" + prepareSubscriptionButtons(category) + "</td>" +
        "</tr>"
    );
}

function prepareSubscriptionButtons(category) {
    if(category.id == null) {
        return " <button onclick=\"subscribe(" + category.categoryId + ")\" type='button' class='btn btn-sm btn-success my-1 mx-1'>Subskrybuj</a>";
    } else {
        return " <button onclick=\"unsubscribe(" + category.id + ")\" type='button' class='btn btn-sm btn-danger my-1 mx-1'>Przestań subskrybować</a>";
    }
}

function subscribe(id) {
      $.ajax({
          url: "/api/adviser/subscribe",
          method: "POST",
          data: JSON.stringify({
              categoryId: id
          }),
          contentType: "application/json",
          dataType: "json",
      })
      .done(function (data) {
            find();
      })
      .fail(function(jqxhr, textStatus, errorThrown){
            $(adviserPanelTagId).addClass("hide");
            go = true;
      });
}

function unsubscribe(id) {
        $.ajax({
            url: "/api/adviser/unsubscribe/" + id,
            type: "delete",
            contentType: "application/json"
        })
        .done(function () {
            find();
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

