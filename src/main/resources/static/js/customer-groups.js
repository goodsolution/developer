
var dictCustomerGroupStatuses;

var recordsSortBy = "group_id";
var recordsOrderBy = "ASC";

$(document).ready(function () {

  findCustomerGroups();

   $("#filter input, #filter select, [form='filter']")
    .on("change", function () {
      findCustomerGroups();
    });

  $("[action='/customer-groups'] input, [action='/customer-groups'] select")
    .on("change", function () {
      findCustomerGroups();
    });

    getDictionaryCustomerGroupStatuses();

});

function findCustomerGroups() {

  setSortingArrow();

  $.ajax({
    url: createFindCustomerGroupsUrl(),
    type: "get",
    contentType: "application/json"
  })
    .done(function (data) {
      fillResults(data);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
         displayErrorInformation(jqxhr.responseText);
    });
}

function createFindCustomerGroupsUrl() {

    var sort_by = recordsSortBy;
    if(sort_by != "") {
        sort_by = "&sort_by=" + sort_by;
    }

    var page = $("#page").val();
    if(page != "") {
        if(!isNaN(page)) {
            page = "&page=" + page;
        } else {
            page = "";
        }
    }

    var page_size = $("#page_size").children(":selected").val();
    if(page_size != "") {
        page_size = "&page_size=" + page_size;
    }

    var order_by = recordsOrderBy;
    if(order_by != "") {
        order_by = "&order_by=" + order_by;
    }

    return "/api/customer-groups?" + sort_by + page + page_size + order_by;
}

function fillResults(data) {
  $("#result-container").show();
  $("#customer-groups-results").empty();
  $("#customer-groups-count").text(data.quantity);
  fillTable(data.groups)
}

function fillTable(groups) {
  groups.forEach(function (record) {

  var deleteOrderModal =
          "<div class=\"modal\" id=\"delete-group-modal-" + record.groupId + "\">" +
          "<div class=\"modal-dialog modal-dialog-centered\">" +
              "<div class=\"modal-content bg-yellow p-2\">" +
                  '<div class="modal-body w-100">' +
                      '<button type="button" class="close" data-dismiss="modal">&times;</button>' +
                      '<p> </p>' +
                      '<p class="font-weight-bold text-center">Czy na pewno chcesz usunąć tę grupę?</p>' +
                      '<p class="text-center">' +
                          '<button type="button" id="orderDeleteConfirmButton"' +
                              'onclick="deleteGroup(' + record.groupId + ');">' +
                              "Tak" +
                          "</button>" +
                          '<button type="button" data-dismiss="modal">' +
                              'Anuluj' +
                          '</button>' +
                      '</p>' +
                  '</div>' +
              '</div>' +
          '</div>' +
      '</div>';

    var groupStatusCell;
    var groupStatusOptions = groupStatuses.map(function(option) {
        var groupStatusSelected = "";
        if (option.id == record.status) {
            groupStatusSelected = "selected";
        }
        return "<option value='" + option.id + "' " + groupStatusSelected + ">" +
            option.value + "</option>";
        });

    groupStatusCell = "<select id='groupStatusSelect-" + record.groupId + "' name='groupStatus' onchange='sendCustomerGroupStatusChangePutRequest(" + record.groupId + ")'>" +
        groupStatusOptions.join("") +
        "</select>";

    $("#customer-groups-results")
      .append("<tr>" +
        "<td class='align-middle'><input type='checkbox' class='recordCheckbox'></td>" +
        "<td class='align-middle'>" + record.groupId + "</td>" +
        "<td class='align-middle'><input type='text' id='groupNameInput-" + record.groupId + "' value='" + htmlEncode(record.name) + "' onchange='sendCustomerGroupNameChangePutRequest(" + record.groupId + ")'></td>" +
        "<td class='align-middle'>" + record.numberOfUsers + "</td>" +
        "<td class='align-middle'>" + groupStatusCell + "</td>" +
        "<td class='align-middle'> <button type='button' data-toggle='modal' data-target='#delete-group-modal-" + record.groupId + "'>Usuń</button>" + deleteOrderModal + "</td>" +
        "</tr>");
  });
}

function deleteGroup(groupId) {
    var $modal = $("#delete-group-modal-" + groupId);
    $.ajax({
        url: "/api/customer-groups/" + groupId,
        type: "DELETE"
    })
    .done(function(response) {
        findCustomerGroups();
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
    $modal.modal("hide");
}

function getDictionaryCustomerGroupStatuses() {
    var result;
    $.ajax({
            url: "/api/dictionary/" + "CUSTOMER_GROUP_STATUSES",
            type: "GET",
            dataType: "json"
        })
    .done(function(response) {
            dictCustomerGroupStatuses = response;
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation("Cannot get dict: " + name + " due to: " + jqxhr.responseText);
    });
}

function sendCustomerGroupStatusChangePutRequest(groupId) {
    var status = $("#groupStatusSelect-" + groupId).children("option:selected").val();
    $.ajax({
        url: "/api/customer-groups/" + groupId + "?" +
        "&status=" + status,
        method: "PUT",
        contentType: "application/json"
    })
    .done(function(response) {
        findCustomerGroups();
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function openAddCustomerGroupModal() {
    var $modal = $("#add-customer-group-modal");

    $modal.find("#new_customer_group_name").val('');
    $modal.find("#new_customer_group_discount").val('0');

    var status = $modal.find("#new_customer_group_status");
        status.empty();
        dictCustomerGroupStatuses.forEach(function(dic){
             opt = '<option value="'+ dic.id +'">' + dic.value + '</option>';
             status.append(opt);
        });

    $modal.modal("show");
}

function postNewCustomerGroupAndReturn() {
    postNewCustomerGroup()
    .done(function(response){
        $("#add-customer-group-modal").modal("hide");
        $("#operation-successful-modal").modal("show");
        findCustomerGroups();
    });
}

function postNewCustomerGroup() {
        var request = $.ajax({
        url: "/api/customer-groups",
        method: "POST",
        data: JSON.stringify({
            name: $("#new_customer_group_name").val(),
            discount: $("#new_customer_group_discount").val(),
            status: $("#new_customer_group_status").val()
        }),
        contentType: "application/json",
    })
    .fail(function(jqxhr, textStatus, errorThrown){
         displayErrorInformation(jqxhr.responseText);
    });

    return request;
}

function sendCustomerGroupNameChangePutRequest(groupId){
    var name = $("#groupNameInput-" + groupId).val();
    $.ajax({
            url: "/api/customer-groups/" + groupId + "?" +
            "&name=" + name,
            method: "PUT",
            contentType: "application/json"
        })
        .done(function(response) {
            findCustomerGroups();
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

function setSortPropsAndFindCustomerGroups(sortBy) {
    setOrderingBy(sortBy)
    setSorting(sortBy);
    findCustomerGroups();
}

function setOrderingBy(sortBy) {
    if (recordsSortBy == sortBy) {
        changeOrderingBy();
    } else {
        recordsOrderBy = "ASC";
    }
}

function changeOrderingBy() {
    if(recordsOrderBy == "ASC") {
        recordsOrderBy = "DESC";
    } else {
        recordsOrderBy = "ASC";
    }
}

function setSorting(sortBy) {
    recordsSortBy = sortBy;
}

function toggleCheckboxes(isMainCheckboxChecked) {
    $('.recordCheckbox').prop('checked', isMainCheckboxChecked);
}

function setSortingArrow() {

    $(".sortingArrowImage").attr("src", "");

    var imageSrc = "";
    var imageId = "";

    if(recordsOrderBy == "ASC") {
        imageSrc = "img/downArrow.png";
    } else if(recordsOrderBy == "DESC") {
        imageSrc = "img/upArrow.png";
    }

    if(recordsSortBy == "group_id") {
        imageId = "groupIdSortingArrowImage";
    } else if(recordsSortBy == "name") {
        imageId = "nameSortingArrowImage";
    } else if(recordsSortBy == "number_of_users") {
        imageId = "numberOfUsersSortingArrowImage";
    } else if(recordsSortBy == "status") {
        imageId = "statusSortingArrowImage";
    }

    if(imageSrc != "" && imageId != ""){
        $("#" + imageId).attr("src", imageSrc);
    }
}