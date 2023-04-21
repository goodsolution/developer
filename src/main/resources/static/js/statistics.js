$(document).ready(function () {
  getStatistics();

  $("[action='/statistics'] input, [action='/statistics'] select")
    .on("change", function () {
      getStatistics();
    });
});

function getStatistics() {
  var dateFrom = $("#dateFrom").val();
  if (dateFrom != "") {
    dateFrom = "date_from=" + dateFrom;
  }

  var dateTo = $("#dateTo").val();
  if (dateTo != "") {
    dateTo = "&date_to=" + dateTo;
  }

  var drivers = $("#drivers").children(":selected").val();
  if (drivers != "") {
    drivers = "&drivers=" + drivers;
  }

  var selected = new Array();
  $("#dietId input[type=checkbox]:checked").each(function () {
    selected.push("&diet_id=" + this.value);
  });
  var dietId = selected.join("");

  var paymentMethod = $("#paymentMethod").children(":selected").val();
  if (paymentMethod != "") {
    paymentMethod = "&payment_method=" + paymentMethod;
  }

  var paymentStatus = $("#paymentStatus").children(":selected").val();
  if (paymentStatus != "") {
    paymentStatus = "&payment_status=" + paymentStatus;
  }

  var shipmentType = $("#shipmentType").children(":selected").val();
  if (shipmentType != "") {
    shipmentType = "&shipment_type=" + shipmentType;
  }

  $.ajax({
    url: "/api/statistics?" + dateFrom + dateTo + drivers +
      dietId + paymentMethod + paymentStatus + shipmentType,
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

  $("#sum").text(data.sum.toFixed(2));

  $("#allOrdersCount").text(data.allOrdersCount);
  $("#customersWhoOrderedCount").text(data.customersWhoOrderedCount);
  $("#customersWhoDidNotContinueCount").text(data.customersWhoDidNotContinueCount);
  $("#customersWhoDidNotContinuePercent").text(data.customersWhoDidNotContinuePercent);

  $("#records").empty();
  data.records.forEach(function (record) {
    $("#records").append(
      "<tr><td class='align-middle'>" + record.no + "</td>" +
      "<td class='align-middle font-weight-bold'><a href='#' class='text-reset'>" + record.customer + "</a></td>" +
      "<td class='align-middle font-weight-bold'>" +
            "<a href='/order?id=" + record.id + "' class='text-reset'>" + record.orderNumber + "</a></td>" +
      "<td class='align-middle'>" + record.driver + "</td>" +
      "<td class='align-middle'>" + (record.date == null ? "" : changeDateArrayToISODate(record.date)) + "</td>" +
      "<td class='align-middle'><span>" + record.value.toFixed(2) + "</span> PLN</td>" +
      "</tr>");
  });
}

function changeDateArrayToISODate(dateArray) {
    var month = dateArray[1];
    if (month.toString().length == 1) {
        month = "0" + dateArray[1];
    }

    var day = dateArray[2];
    if (day.toString().length == 1) {
            day = "0" + dateArray[2];
    }

    return dateArray[0] + "-" + month + "-" + day;
}
