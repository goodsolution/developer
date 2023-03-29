$(document).ready(function () {
  findImages();

  $("[action='/statistics'] input, [action='/statistics'] select")
    .on("change", function () {
      findImages();
    });
});

function findImages() {
  var selected = new Array();
  $("#dietId input[type=checkbox]:checked").each(function () {
    selected.push("&diet_id=" + this.value);
  });
  var dietId = selected.join("");
  var page_size = $("#page_size").children(":selected").val();
  if (page_size != "") {
    page_size = "&page_size=" + page_size;
  }

  var page = $("#page").val();
  if (page != "") {
    page = "&page=" + page;
  }


  $.ajax({
    url: "/api/gallery?" +
      dietId + page + page_size,
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
  $("#records").empty();
  data.forEach(function (record) {
    $("#records").append(
      "<tr>"
       +"<td><img style=\"width: 100px\" src=\""+ "gallery/" + record.fileName +"\"></td>"
       +"<td><button type=\"button\" onclick=\"window.location.href='gallery-image?id=" + record.id +"'\">Modyfikuj</button></td>"
       +"<td><button type=\"button\" onclick=\"openRemoveConfirmationModal(" + record.id +")\">Usuń</button></td>"
       +"</tr>");
  });
}

function deleteRecord(id) {
    deleteImage(id);
}

function deleteImage(id) {

     $.ajax({
            url: "/api/gallery/image/" + id + "",
            type: "DELETE"
        })
        .done(function(response) {
            findImages();
            $("#delete-confirmation-modal").modal("hide");
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

function openRemoveConfirmationModal(id) {
    var $modal = $("#delete-confirmation-modal");
    $modal.find("#deleteConfirmButton").attr({"data-record-id": id});
    $modal.modal("show");
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
