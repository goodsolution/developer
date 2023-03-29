var orderId;
var i;

$(document).ready(function () {
  $("#result-container").hide();
  $("#filter input, #filter select, [form='filter']")
    .on("change", function () {
      getOrders();
    });

  $("#reset_button").click(function() {
    window.location.reload();
  });

  var order_status_id = $("#order_status_id").children(":selected").val();
  if (order_status_id == 100) {
        getOrders();
  }
});

function getOrders() {
  var first_and_last_name = $("#first_and_last_name").val();
  if (first_and_last_name != "") {
    first_and_last_name = "first_and_last_name=" + first_and_last_name;
  }

  var date_purchased_from = $("#date_purchased_from").val();
  if (date_purchased_from != "") {
    date_purchased_from = "&date_purchased_from=" + date_purchased_from;
  }

  var date_purchased_to = $("#date_purchased_to").val();
  if (date_purchased_to != "") {
    date_purchased_to = "&date_purchased_to=" + date_purchased_to;
  }

  var payment_status_id = $("#payment_status_id").children(":selected").val();
  if (payment_status_id != "") {
    payment_status_id = "&payment_status_id=" + payment_status_id;
  }

  var order_id = $("#order_id").val();
  if (order_id != "") {
    order_id = "&order_id=" + order_id;
  }

  var discount_code = $("#discount_code").val();
  if (discount_code != "") {
    discount_code = "&discount_code=" + discount_code;
  }

  var payment_method_id = $("#payment_method_id").children(":selected").val();
  if (payment_method_id != "") {
    payment_method_id = "&payment_method_id=" + payment_method_id;
  }

  var driver_id = $("#driver_id").children(":selected").val();
  if (driver_id != "") {
    driver_id = "&driver_id=" + driver_id;
  }

  var is_invoice = $("#is_invoice").children(":selected").val();
  if (is_invoice != "") {
    is_invoice = "&is_invoice=" + is_invoice;
  }

  var date_delivery_from = $("#date_delivery_from").val();
  if (date_delivery_from != "") {
    date_delivery_from = "&date_delivery_from=" + date_delivery_from;
  }

  var date_delivery_to = $("#date_delivery_to").val();
  if (date_delivery_to != "") {
    date_delivery_to = "&date_delivery_to=" + date_delivery_to;
  }

  var delivery_method_id = $("#delivery_method_id").children(":selected").val();
  if (delivery_method_id != "") {
    delivery_method_id = "&delivery_method_id=" + delivery_method_id;
  }

  var diet_id = $("#diet_id").children(":selected").val();
  if (diet_id != "") {
    diet_id = "&diet_id=" + diet_id;
  }

  var order_status_id = $("#order_status_id").children(":selected").val();
  if (order_status_id != "") {
    order_status_id = "&order_status_id=" + order_status_id;
  }

  var days_to_finish = $("#days_to_finish").val();
  if (days_to_finish != "") {
    days_to_finish = "&days_to_finish=" + days_to_finish;
  }

  var city = $("#city").children(":selected").val();
  if (city != "") {
    city = "&city=" + city;
  }

  var date_marked_as_paid_from = $("#date_marked_as_paid_from").val();
  if (date_marked_as_paid_from != "") {
    date_marked_as_paid_from = "&date_marked_as_paid_from=" + date_marked_as_paid_from;
  }

  var date_marked_as_paid_to = $("#date_marked_as_paid_to").val();
  if (date_marked_as_paid_to != "") {
    date_marked_as_paid_to = "&date_marked_as_paid_to=" + date_marked_as_paid_to;
  }

   var phone = $("#phone").val();
   if (phone != "") {
     phone = "&phone=" + phone;
   }

  var page_size = $("#page_size").children(":selected").val();
  if (page_size != "") {
    page_size = "&page_size=" + page_size;
  }

  var page = $("#page").val();
  if (page != "") {
    page = "&page=" + page;
  }

  $.ajax({
    url: "/api/orders?" + 
      first_and_last_name + 
      date_purchased_from + 
      date_purchased_to +
      date_purchased_to + 
      payment_status_id + 
      order_id + 
      discount_code +
      payment_method_id +
      driver_id +
      is_invoice +
      date_delivery_from +
      date_delivery_to +
      delivery_method_id +
      diet_id +
      order_status_id +
      days_to_finish +
      city +
      date_marked_as_paid_from +
      date_marked_as_paid_to +
      phone +
      page_size +
      page,
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
  $("#records").empty();

  $("#total").text(Number(data.sum).toFixed(2));
  $("#count").text(data.count);
  fillTable(data.orders);
}

function fillTable(orders) {
    orders.forEach(function (record) {

        var shipmentCell;
        var shipmentTypeOptions = shipmentTypes.map(function(option) {
            var shipmentTypeSelected = "";
            if (option.id == record.deliveryMethodId) {
                shipmentTypeSelected = "selected";
            }
            return "<option value='" + option.id + "' " + shipmentTypeSelected + ">" +
                option.value + "</option>";
        });
        shipmentCell = "<td class='align-middle'>" +
            "<select name='deliveryMethod' data-orderId='" + record.id + "' >" +
            shipmentTypeOptions.join("") +
            "</select>" +
            "</td>";


    var paymentCell;
    if (record.paymentMethodId == 3) {
          paymentCell = "<td class='align-middle'>" + record.paymentMethodName + "</td>";
    } else {
        var paymentMethodOptions = paymentMethods.map(function(option) {
            var paymentMethodSelected = "";
            if (option.id == record.paymentMethodId) {
                paymentMethodSelected = "selected";
            }
            return "<option value='" + option.id + "' " + paymentMethodSelected + ">" +
                option.value + "</option>";
        });
        paymentCell = "<td class='align-middle'>" +
            "<select name='paymentMethod' data-orderId='" + record.id + "' >" +
            paymentMethodOptions.join("") +
            "</select>" +
            "</td>";
    };

    var backgroundPayment = "";
    if (record.paymentStyle != null) {
            backgroundPayment = "style='" + record.paymentStyle + "'";
    }
    var paymentStatusCell;
        if (record.paymentMethodId == 3 && record.paymentStatusId != 0) {
                  paymentStatusCell = "<td class='align-middle' " + backgroundPayment + ">" +
                    record.paymentStatusName + "</td>";
        } else {
              var paymentStatusOptions = paymentStatuses.map(function(option) {
                  var paymentStatusSelected = "";
                  if (option.id == record.paymentStatusId) {
                    paymentStatusSelected = "selected";
                  }
                  return "<option value='" + option.id + "' " + paymentStatusSelected + ">" +
                    option.value + "</option>";
              });
              paymentStatusCell = "<td class='align-middle' "+ backgroundPayment +">" +
                "<select name='paymentStatus' data-orderId='" + record.id + "' >" +
                paymentStatusOptions.join("") +
                "</select>" +
                "</td>";
        }

    var daysLeftCell;
    var backgroundForDaysLeft = "";
    if (record.daysLeftStyle != null) {
        backgroundForDaysLeft = "style='"+ record.daysLeftStyle +"'";
    }
    daysLeftCell = "<td class='align-middle' " + backgroundForDaysLeft + ">" +
        record.daysLeft + " (" + record.nextOrderIndicator + ")</td>"


    var orderStatusCell;
    var backgroundForOrderStatus = "";
    var orderStatusOptions = orderStatuses.map(function(option) {
        var orderStatusSelected = "";
        if (option.id == record.orderStatusId) {
            orderStatusSelected = "selected";
        }
        return "<option value='" + option.id + "' " + orderStatusSelected + ">" +
            option.value + "</option>";
        });

    if (record.orderStatusStyle != null) {
        backgroundForOrderStatus = "style='" + record.orderStatusStyle + "'";
    }
    orderStatusCell = "<td class='align-middle' " + backgroundForOrderStatus + ">" +
        "<select name='orderStatus' data-orderId='" + record.id + "' data-statusId='" + record.orderStatusId + "' >" +
        orderStatusOptions.join("") +
        "</select>" +
        "</td>";


    var discountCell;
    if (record.discountCode == "") {
        record.discountCode = "---"
    }
    discountCell = "<td class='align-middle'>" +
        record.discountCode +
        "</td>"


    var invoiceCell;
    var invoiceCellValue;
    var backgroundColorForInvoiceCell;
    if (record.invoiceWanted == false) {
        invoiceCellValue = "Nie";
    } else if (record.invoiceWanted == true && record.invoiceIssued == true) {
        backgroundColorForInvoiceCell = " style='background-color: #27ae60;' ";
        invoiceCellValue = "Tak</br><span class='lead'></span>";
    } else if (record.invoiceWanted == true && record.invoiceIssued == false) {
        backgroundColorForInvoiceCell = " style='background-color: #00adfb; color: white;' ";
       optionalCheckMark = '<input type="button" class="check-button-white" onclick="openInvoiceConfirmationModal(' + record.id + ')" data-id="' + record.id + '" />'; //"&#10004;";
       invoiceCellValue = "Tak</br><span class='lead'>" + optionalCheckMark + "</span>";
    }
//    else {
//        var invoiceCellOptions = yesNo.map(function(option) {
//            var invoiceBooleanCode = (option.code == 'true');
//            var invoiceSelected = "";
//            if (record.invoice == invoiceBooleanCode) {
//                invoiceCellValue = option.value;
//                invoiceSelected = "selected";
//            }
//            return "<option value='" + option.code + "' " + invoiceSelected + ">" +
//                               option.value + "</option>";
//        });
//        invoiceCellValue = "<select name='invoice' data-orderId='" + record.id + "' >" +
//            invoiceCellOptions.join("") +
//            "</select>";
//    }
    invoiceCell = "<td class='align-middle'" + backgroundColorForInvoiceCell + ">" +
        invoiceCellValue
        "</td>";

    var receiptCell;
    var backgroundForReceiptCell, optionalCheckMark;
    if (record.receipt == false) {
        optionalCheckMark = '<input type="button" class="save-receipt" onclick="openReceiptConfirmationModal(' + record.id + ')" data-id="' + record.id + '" />'; //"&#10004;";
        backgroundForReceiptCell = "";
    } else {
        optionalCheckMark = "";
        backgroundForReceiptCell = "style='background-color: #2bd480;'";
    }
    receiptCell = "<td class='align-middle lead' " + backgroundForReceiptCell + ">" +
        optionalCheckMark +
        "</td>";


    var demandingCustomerCell;
    var demandingCustomerValue;
    var demandingCustomerOptions = yesNo.map(function(option) {
        var demandingCustomerCode = (option.code == 'true');
        var demandingCustomerSelected = "";
        if (record.demandingCustomer == demandingCustomerCode) {
            demandingCustomerValue = option.value;
            demandingCustomerSelected = "selected";
        }
        return "<option value='" + option.code + "' " + demandingCustomerSelected + ">" +
            option.value + "</option>";
    });
    demandingCustomerCell = "<td class='align-middle'>" +
        "<select name='demandingCustomer' data-orderId='" + record.id + "' data-customer-id='" + record.customerId + "' >" +
        demandingCustomerOptions +
        "</select>" +
        "</td>"


    var buttonCell;
    var deleteOrderModal =
        "<div class=\"modal\" id=\"delete-order-modal-" + record.id + "\">" +
        "<div class=\"modal-dialog modal-dialog-centered\">" +
            "<div class=\"modal-content bg-red p-2\">" +
                '<div class="modal-body w-100">' +
                    '<button type="button" class="close" data-dismiss="modal">&times;</button>' +
                    '<p> </p>' +
                    '<p class="font-weight-bold text-center">Czy na pewno chcesz usunąć to zamówienie?</p>' +
                    '<p class="text-center">' +
                        '<button type="button" id="orderDeleteConfirmButton"' +
                            'onclick="deleteOrder(' + record.id + ');">' +
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

    var address = "/order?id=" + record.id;
    buttonCell = "<td class='align-middle'>" +
        '<a href="' + address + '"><button>Podgląd</button></a>' +
        '<button type="button" data-toggle="modal" data-target="#delete-order-modal-' + record.id + '">Usuń</button>' +
        deleteOrderModal +
        "</td>";

    var orderNumberCell = "";
    if (record.statusChangeSource != 'T') {
        orderNumberCell = "<td class='align-middle'>" + record.orderNumber + "</td>";
    } else {
        orderNumberCell = "<td class='align-middle' style='background-color: #00adfb;'>" + record.orderNumber + "</td>";
    }

    $("#records").append(
      "<tr><td class='align-middle'>" + record.no + "</td>" +
      orderNumberCell +
      "<td class='align-middle'>" + record.firstName + " " + record.lastName + "</td>" +
      "<td class='align-middle'>" + record.purchaseDateTime.replace("T", " ") + "</td>" +
      "<td class='align-middle'>" + Number(record.value).toFixed(2) + " " + record.currency + "</td>" +
      shipmentCell +
      paymentCell +
      paymentStatusCell +
      daysLeftCell +
      orderStatusCell +
      discountCell +
      invoiceCell +
      receiptCell +
      demandingCustomerCell +
      buttonCell +
      "</tr>");
  });
}


/* UPDATES */
$(document).ready(function () {
    $("tbody").on( "change", "select", function() {

        //sendOrderPutRequest($(this).attr("data-orderId"));
        orderId = $(this).attr("data-orderId")
        name = $(this).attr("name");
        //alert(name);
        if (name == 'deliveryMethod') {
            openChangeOrderDeliveryMethodConfirmationModal();
        } else if (name == 'paymentMethod') {
            openChangeOrderPaymentMethodConfirmationModal();
            //alert('paymentMethod');
        } else if (name == 'paymentStatus') {
            //alert('paymentStatus');
            openChangeOrderPaymentStatusConfirmationModal();
        } else if (name == 'orderStatus') {
            openChangeOrderStatusConfirmationModal();
        } else if (name == 'demandingCustomer') {
            openChangeOrderDemandingCustomerConfirmationModal();
            //alert('demandingCustomer');
        }
    });
});

function openChangeOrderDemandingCustomerConfirmationModal() {
    var $modal = $("#order-demanding-customer-confirmation-modal");
    $modal.modal("show");
}

function openChangeOrderDeliveryMethodConfirmationModal() {
    var $modal = $("#order-delivery-method-confirmation-modal");
    $modal.modal("show");
}

function openChangeOrderPaymentMethodConfirmationModal() {
    var $modal = $("#order-payment-method-confirmation-modal");
    $modal.modal("show");
}

function openChangeOrderStatusConfirmationModal() {
    var $modal = $("#order-status-confirmation-modal");
    $modal.modal("show");
}

function openChangeOrderPaymentStatusConfirmationModal() {
    var $modal = $("#order-payment-status-confirmation-modal");
    $modal.modal("show");
}

function changeOrderDemandingCustomer() {
    customerId = $("select[name='demandingCustomer'][data-orderId='" + orderId + "']").attr("data-customer-id");
    value = $("select[name='demandingCustomer'][data-orderId='" + orderId + "']").children("option:selected").val();
    sendOrderDemandingCustomerPutRequest(orderId, customerId, value);
}

function changeOrderPaymentMethod() {
   value = $("select[name='paymentMethod'][data-orderId='" + orderId + "']").children("option:selected").val();
   sendOrderChangePutRequest(orderId, name, value);
}

function changeOrderDeliveryMethod() {
   value = $("select[name='deliveryMethod'][data-orderId='" + orderId + "']").children("option:selected").val();
   sendOrderChangePutRequest(orderId, name, value);
}

function changeOrderPaymentStatus() {
    value = $("select[name='paymentStatus'][data-orderId='" + orderId + "']").children("option:selected").val();
    sendOrderChangePutRequest(orderId, name, value);
}

function changeOrderStatus() {
oldStatus = $("select[name='orderStatus'][data-orderId='" + orderId + "']").attr("data-statusId");
            //alert('orderStatus');
            newStatus = $("select[name='orderStatus'][data-orderId='" + orderId + "']").children("option:selected").val();
            if (newStatus == 7) {
                // dialogDateStopOrder
                //alert(1);
                openOrderStopModal();
            } else if (newStatus == 5 && oldStatus == 7) {
                //alert(2);
                // dialogDateStartOrder
                openOrderStartModal();
            } else if (newStatus == 6) {
               // alert(3);
                // cancel-form
                openOrderCancelReasonModal();
            } else {
                // czy na pewno
                //alert(4);
                sendOrderChangePutRequest(orderId, name, newStatus);
            }
}
function sendOrderChangePutRequest(orderId, what, value) {
    $.ajax({
        url: "/api/orders/" + orderId + "/change/"+ what +"/value/" + value,
        method: "PUT",
        contentType: "application/json"
    })
    .done(function(response) {
        $("#operation-successful-modal").modal("show");
        getOrders();
    })
    .fail(function(jqxhr, textStatus, errorThrown){
         displayErrorInformation(jqxhr.responseText);
    });
}

function sendOrderDemandingCustomerPutRequest(orderId, customerId, state) {
    $.ajax({
        url: "/api/orders/" + orderId + "/demanding_customer/"+ customerId +"/value/" + state,
        method: "PUT",
        contentType: "application/json"
    })
    .done(function(response){
        $("#operation-successful-modal").modal("show");
    })
    .fail(function(jqxhr, textStatus, errorThrown){
         displayErrorInformation(jqxhr.responseText);
    });
}

function sendOrderPutRequest(orderId) {
    var data = {
        deliveryMethodId: $("select[name='deliveryMethod'][data-orderId='" + orderId + "']").children("option:selected").val(),
        paymentMethodId: $("select[name='paymentMethod'][data-orderId='" + orderId + "']").children("option:selected").val(),
        paymentStatusId: $("select[name='paymentStatus'][data-orderId='" + orderId + "']").children("option:selected").val(),
        orderStatusId: $("select[name='orderStatus'][data-orderId='" + orderId + "']").children("option:selected").val(),
        receipt: $("select[name='receipt'][data-orderId='" + orderId + "']").children("option:selected").val(),
        invoiceWanted: $("select[name='invoice'][data-orderId='" + orderId + "']").children("option:selected").val(),
        demandingCustomer: $("select[name='demandingCustomer'][data-orderId='" + orderId + "']").children("option:selected").val()
    }

    $.ajax({
        url: "/api/orders/" + orderId + "",
        method: "PUT",
        data: JSON.stringify(data),
        contentType: "application/json"
    })
    .fail(function(jqxhr, textStatus, errorThrown){
         displayErrorInformation(jqxhr.responseText);
    });
}


//The "Usuń" button
function deleteOrder(orderId) {
    var $modal = $("#delete-order-modal-" + orderId);
    $.ajax({
        url: "/api/orders/" + orderId + "",
        type: "DELETE"
    })
    .done(function(response) {
        getOrders();
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
    $modal.modal("hide");
}

function openOrderCancelReasonModal() {
    var $modal = $("#cancel-order-reason-modal");
                    //$modal.find(".confirmButton").attr({"data-paymentId": paymentId});
    $modal.find("#cancel_reason").val('');
    $modal.modal("show");
}

function cancelOrderWithReason(reason) {
    //alert("orderId: " + orderId + " " + reason);
    cancelOrderWithReasonPutRequest(orderId, reason)
    .done(function(data){
        $("#cancel-order-reason-modal").modal("hide");
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
    });
}

function openOrderStartModal() {
    var $modal = $("#start-order-modal");
    $modal.find("#start_order_date")
    .datepicker({
            dateFormat: "yy-mm-dd"
        });
    //$modal.find(".confirmButton").attr({"data-paymentId": paymentId});
    $modal.modal("show");
}

function openOrderStopModal() {
    var $modal = $("#stop-order-modal");
    $modal.find("#stop_order_date")
    .datepicker({
        dateFormat: "yy-mm-dd"
    });
    //$modal.find(".confirmButton").attr({"data-paymentId": paymentId});
    $modal.modal("show");
}

function startOrder(date) {
    //alert(date);
    sendStartDietPutRequest(orderId, date)
    .done(function(data){
        getOrders();
        closeOrderStartModal();
    })
    .fail(function(jqxhr, textStatus, errorThrown){
             displayErrorInformation(jqxhr.responseText);
    });
}

function closeOrderStartModal() {
    $("#start-order-modal").modal("hide");
}

function stopOrder(date) {
    //alert(date);
    sendStopDietPutRequest(orderId, date)
    .done(function(data){
        getOrders();
        closeOrderStopModal();
    })
    .fail(function(jqxhr, textStatus, errorThrown){
             displayErrorInformation(jqxhr.responseText);
     });
}

function closeOrderStopModal() {
    $("#stop-order-modal").modal("hide");
}


function sendStartDietPutRequest(orderId, date) {
    return $.ajax({
        url: "/api/orders/" + orderId + "/start_diet/"+ date,
        method: "PUT",
        contentType: "application/json"
    })
}

function sendStopDietPutRequest(orderId, date) {
    return $.ajax({
        url: "/api/orders/" + orderId + "/stop_diet/"+ date,
        method: "PUT",
        contentType: "application/json"
    })
}

function cancelOrderWithReasonPutRequest(orderId, cancelReason) {
    return $.ajax({
        url: "/api/orders/" + orderId + "/cancel_order",
        method: "PUT",
        data: JSON.stringify({
            reason : cancelReason
        }),
        contentType: "application/json"
    });
}

function openReceiptConfirmationModal(orderId) {
    var $modal = $("#receipt-confirmation-modal");
    $modal.find("#receipt_order_id").val(orderId);
    $modal.modal("show");
}

function markReceipt(orderId) {
    sendOrderChangePutRequest(orderId, 'receipt', true);
}

function openInvoiceConfirmationModal(orderId) {
    var $modal = $("#invoice-confirmation-modal");
    $modal.find("#invoice_order_id").val(orderId);
    $modal.modal("show");
}

function markInvoice(orderId) {
    sendOrderChangePutRequest(orderId, 'invoice', true);
}

