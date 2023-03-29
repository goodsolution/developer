var orderId;
var orderStatusIdOld;

var dictCities;
var dictDiets;
var dictProducts;
var dictExtras;
var dictWeekendOption;
var dictYesNo;

$(document).ready(function() {
    id = $("#currentId").attr("data-id");

});

function save(id) {
    //alert("id: " + id);
    if (id == null) {
        addImage();
    } else {
        modifyImage(id);
    }
}

function addImage() {
  var selected = new Array();
  $("#dietId input[type=checkbox]:checked").each(function () {
    selected.push("&dietIds=" + this.value);
  });
  var dietId = selected.join("");
  var fileName = $("#file_name_uploaded").text();
  var kind = "&kind=" + $("#imageKind").val();
    $.ajax({
        url: "/api/gallery/image?fileName="+fileName+dietId+kind,
        method: "POST",
//        data: JSON.stringify({
//            dietId: $("#newProductDiet").children("option:selected").val(),
//            dietTypeId: $("#newProductType").children("option:selected").val(),
//            days: $("#newProductDays").val(),
//            testDay: $("#newProductTestDay").children("option:selected").val(),
//            dateFrom: $("#new_product_start_date").val(),
//            weekendOptionId: $("#newProductWeekendOption").children("option:selected").val(),
//            extrasOne: $("#extrasOne").is(":checked"),
//            quantity: $("#newProductQuantity").val(),
//            extras: extrasIds
//        }),
        contentType: "application/json",
    })
    .done(function(response) {
        $("#operation-successful-modal").modal("show");
        window.location.assign("/gallery");
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation(jqxhr.responseText);
    });
}

function modifyImage(id) {
  var selected = new Array();
  $("#dietId input[type=checkbox]:checked").each(function () {
    selected.push("&dietIds=" + this.value);
  });
  var dietId = selected.join("");
  var kind = "&kind=" + $("#imageKind").val();
  var fileName = $("#file_name_uploaded").text();
    $.ajax({
        url: "/api/gallery/image/" + id + "?fileName="+fileName+dietId+kind,
        method: "PUT",
        contentType: "application/json"
    })
    .done(function(response) {
         $("#operation-successful-modal").modal("show");
         window.location.assign("/gallery");
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
         displayErrorInformation(jqxhr.responseText);
    });
}