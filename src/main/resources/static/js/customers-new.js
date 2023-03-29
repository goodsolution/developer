$(document).ready(function () {
  getNewCustomers();

  $("[action='/customers-new'] input, [action='/customers-new'] select")
    .on("change", function () {
      getNewCustomers();
    });
});

function getNewCustomers() {
  var dateFrom = $("#dateFrom").val();
  if (dateFrom != "") {
    dateFrom = "date_from=" + dateFrom;
  }

  var dateTo = $("#dateTo").val();
  if (dateTo != "") {
    dateTo = "&date_to=" + dateTo;
  }

  var orderStatusId = $("#orderStatus").children(":selected").val();
  if (orderStatusId != "") {
    orderStatusId = "&order_status_id=" + orderStatusId;
  }

  var selected = new Array();
  $("#dietId input[type=checkbox]:checked").each(function () {
    selected.push("&diet_id=" + this.value);
  });
  var dietId = selected.join("");

  $.ajax({
    url: "/api/customers-new?" + dateFrom + dateTo + orderStatusId + dietId,
    type: "get",
    dataType: "json",
    contentType: "application/json"
  })
    .done(function (data) {
      fillResults(data);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
         displayErrorInformation(jqxhr.responseText);
    });
}

function fillResults(data) {
  $("#result-container").show();
  $("#new-customers-results").empty();
  $("#new-customers-count").text(data.count);
  fillTable(data.newCustomers)
}

function fillTable(newCustomers) {
  newCustomers.forEach(function (record) {
    $("#new-customers-results")
      .append("<tr><td class='align-middle'>" + record.no + "</td>" +
        "<td class='align-middle font-weight-bold'>" +
            "<a href='#' class='text-reset'>" + record.customer + "</a></td>" +
        "<td class='align-middle font-weight-bold'>" +
            "<a href='/order?id=" + record.orderId + "' class='text-reset'>" + record.orderNumber + "</a></td>" +
        "<td class='align-middle'>" + record.value.toFixed(2) + " PLN</td>" +
        "<td class='align-middle'>" + record.orderStatus + "</td>" +
        "<td class='align-middle'>" + record.dateTime + "</td>" +
        "</tr>");
  });
}