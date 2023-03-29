$(document).ready(function () {
    $("#filter input, #filter select").on("change", function () {
      refresh();
    });

    $("#reset_button").click(function() {
        $("#filter input").val("");
        refresh();
    });
});


$(document).ready(function () {
    $("tbody").on( "change", "select", function() {

        //sendOrderPutRequest($(this).attr("data-orderId"));
        orderId = $(this).attr("data-id")
        name = $(this).attr("name");
        //alert(name + " orderId + " + orderId);
        if (name == 'deliveryMethod') {
            value = $("select[name='deliveryMethod'][data-id='" + orderId + "']").children("option:selected").val();
            sendOrderChangePutRequest(orderId, name, value);
        } else if (name == 'paymentMethod') {
            //alert('paymentMethod');
            value = $("select[name='paymentMethod'][data-id='" + orderId + "']").children("option:selected").val();
            sendOrderChangePutRequest(orderId, name, value);
        } else if (name == 'paymentStatus') {
            //alert('paymentStatus');
             value = $("select[name='paymentStatus'][data-id='" + orderId + "']").children("option:selected").val();
             sendOrderChangePutRequest(orderId, name, value);
        } else if (name == 'orderStatus') {
            //alert('orderStatus');
            oldStatus = $("select[name='orderStatus'][data-id='" + orderId + "']").attr("data-statusId");
            newStatus = $("select[name='orderStatus'][data-id='" + orderId + "']").children("option:selected").val();
            if (newStatus == 7) {
                // dialogDateStopOrder
                //alert(1);
                openOrderStopModal();
            } else if (newStatus == 5 && oldStatus == 7) {
                //alert(2);
                // dialogDateStartOrder
                openOrderStartModal();
            } else if (newStatus == 6) {
                // impossible
            } else {
                // impossible
            }
            //sendOrderChangePutRequest(orderId, name, newStatus);
        } else if (name == 'demandingCustomer') {
            //alert('demandingCustomer');
            customerId = $(this).attr("data-customer-id");
            value = $("select[name='demandingCustomer'][data-id='" + orderId + "']").children("option:selected").val();
            sendOrderDemandingCustomerPutRequest(orderId, customerId, value);
            refresh();
            $("#operation-successful-modal").modal("show");
         }
//        } else if (name == 'receipt') {
//            //alert('demandingCustomer');
//            value = $("select[name='receipt'][data-id='" + orderId + "']").children("option:selected").val();
//            sendOrderChangePutRequest(orderId, name, value);
//        } else if (name == 'invoice') {
//            //alert('demandingCustomer');
//            value = $("select[name='invoice'][data-id='" + orderId + "']").children("option:selected").val();
//            sendOrderChangePutRequest(orderId, name, value);
//         }
    });
});

function sendOrderChangePutRequest(orderId, what, value) {
    $.ajax({
        url: "/api/orders/" + orderId + "/change/"+ what +"/value/" + value,
        method: "PUT",
        contentType: "application/json"
    })
    .done(function (data) {
        refresh();
        $("#operation-successful-modal").modal("show");
    })
    .fail(function(jqxhr, textStatus, errorThrown){
         displayErrorInformation(jqxhr.responseText);
    });
}

function openOrderStopModal() {
    var $modal = $("#stop-order-modal");
    $modal.find("#stop_order_date")
    .datepicker({
        dateFormat: "yy-mm-dd"
    });
    $modal.modal("show");
}

function openOrderStartModal() {
    var $modal = $("#start-order-modal");
    $modal.find("#start_order_date")
    .datepicker({
            dateFormat: "yy-mm-dd"
        });
    $modal.modal("show");
}

function startOrder(date) {
    //alert(date);
    sendStartDietPutRequest(orderId, date)
    .done(function(response) {
        closeOrderStartModal();
        refresh();
        $("#operation-successful-modal").modal("show");
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
    .done(function(response) {
        closeOrderStopModal();
        refresh();
        $("#operation-successful-modal").modal("show");
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

function sendOrderDemandingCustomerPutRequest(orderId, customerId, state) {
    $.ajax({
        url: "/api/orders/" + orderId + "/demanding_customer/"+ customerId +"/value/" + state,
        method: "PUT",
        contentType: "application/json"
    })
    .fail(function(jqxhr, textStatus, errorThrown){
         displayErrorInformation(jqxhr.responseText);
    });
}

function refresh() {
        $("form").submit();
}