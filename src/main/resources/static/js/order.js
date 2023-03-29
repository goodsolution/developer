var orderId;
var orderStatusIdOld;

var dictCities;
var dictDiets;
var dictProducts;
var dictExtras;
var dictWeekendOption;
var dictYesNo;

$(document).ready(function() {
    orderId = $("#currentOrderId").attr("data-orderId");

    getDictionaryCities();
    getDictionaryDiets();
    getDictionaryProducts();
    getDictionaryExtras();
    getDictionaryWeekendOptions();
    getDictionaryYesNo();

    //hideBoxes();
    reactToTogglesOnSections();

    if (!readOnly) {
        refillPaymentsTable();
        refillChangesTable();
        refillDeliveryChangesTable();
        refillEmailsTable();
    }
    refillProductTable();
    refillOrderDaysTable();
    refillOrderDaysHistoryTable();
    refillDeliveryTable();
    refillDeliveryReleasedTable();

    if (readOnly) {
        setReadOnlyElements();
    }
});

function hideBoxes() {
    $("tbody.results:not(:has(tr))").each(function(){
        $(this).closest("div.box").hide();
    });
}


function reactToTogglesOnSections() {
    $(".toggle-disabled .switch").on("change", function(){
        let $allFieldsExceptToggleSwitch = $(this).closest(".toggle-disabled").find("input:not(.switch), select");
        if ( $(this).is(":checked") ) {
            $allFieldsExceptToggleSwitch.prop("disabled", false);
        } else {
            $allFieldsExceptToggleSwitch.prop("disabled", true);
        }
    })

    $("form").on("change", "#invoiceWanted", function(){
        if ($(this).is(":checked")) {
            $("#invoiceIssuedStatus").parent("p").removeClass("hide");
        } else {
            $("#invoiceIssuedStatus").parent("p").addClass("hide");
        }
    });
}

function openOrderCancelReasonModal() {
    var $modal = $("#cancel-order-reason-modal");
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

function openOrderStartModal() {
    var $modal = $("#start-order-modal");
    $modal.find("#start_order_date")
    .datepicker({
            dateFormat: "yy-mm-dd"
        });
    //$modal.find(".confirmButton").attr({"data-paymentId": paymentId});
    $modal.modal("show");
}

function cancelOrderWithReason(reason) {
    //alert("orderId: " + orderId + " reason: " + reason);
    updateOrder(null, null, reason);
    closeOrderCancelReasonModal();
}
function closeOrderCancelReasonModal() {
    $("#cancel-order-reason-modal").modal("hide");
}

function modifyOrder() {
    var orderStatusIdNew = $("#orderStatusId").children("option:selected").val();
    //alert("orderStatusIdNew: " + orderStatusIdNew + " orderStatusIdOld: " + orderStatusIdOld);
    /*
    1	Nowe
    2	Przyjęte do realizacji
    3	Gotowe do wysyłki
    4	Zrealizowane - wysłane
    5	Zaakceptowane
    6	Anulowane
    7	Wstrzymane
    */
	if( orderStatusIdOld != 7 && orderStatusIdNew == 7   ) {
		openOrderStopModal();
	} else if(orderStatusIdOld == 7  && orderStatusIdNew == 5) {
		openOrderStartModal();
	} else if (orderStatusIdOld != 6 && orderStatusIdNew == 6 ) {
		openOrderCancelReasonModal();
	} else {
	    updateOrder(null, null, null);
	}
}

function stopOrder(date) {
    //alert(date);
    closeOrderStopModal();
    updateOrder(null, date, null);
}

function closeOrderStopModal() {
    $("#stop-order-modal").modal("hide");
}

function startOrder(date) {
    //alert(date);
    closeOrderStartModal();
    updateOrder(date, null, null);
}

function closeOrderStartModal() {
    $("#start-order-modal").modal("hide");
}

/* UPDATE ORDER */
function updateOrder(startDate, stopDate, cancelReason) {
    $.ajax({
        url: "/api/orders/" + $("#orderStatusId").attr("data-id") + "",
        method: "PUT",
        data: JSON.stringify({
            orderStatusId: $("#orderStatusId").children("option:selected").val(),
            email: $("#email").val(),
            invoiceIssuedStatus: $("#invoiceIssuedStatus").is(":checked"),
            receipt: $("#receipt").is(":checked"),
            demandingCustomer: $("#demandingCustomer").children("option:selected").val(),
            paymentMethodId: $("#paymentMethodId").children("option:selected").val(),
            paymentByCard: $("#paymentByCard").is(":checked"),
            paymentStatusId: $("#paymentStatusId").children("option:selected").val(),
            deliveryMethodId: $("#deliveryMethodId").children("option:selected").val(),
            groupOrders: $("#groupOrders").is(":checked"),

            customerFirstName: $("#customerFirstName").val(),
            customerLastName: $("#customerLastName").val(),
            customerPhone: $("#customerPhone").val(),
            customerStreet: $("#customerStreet").val(),
            customerBuildingNumber: $("#customerBuildingNumber").val(),
            customerApartmentNumber: $("#customerApartmentNumber").val(),
            customerPostalCode: $("#customerPostalCode").val(),
            customerCityId: $("#customerCityId").children("option:selected").val(),

            hoursTo: $("#hoursTo").val(),
            weekendHours: $("#weekendHours").is(":checked"),
            weekendHoursTo: $("#weekendHoursTo").val(),

            weekendAddress: $("#weekendAddress").is(":checked"),
            weekendStreet: $("#weekendStreet").val(),
            weekendBuildingNumber: $("#weekendBuildingNumber").val(),
            weekendApartmentNumber: $("#weekendApartmentNumber").val(),
            weekendPostalCode: $("#weekendPostalCode").val(),
            weekendCityId: $("#weekendCityId").children("option:selected").val(),

            invoiceWanted: $("#invoiceWanted").is(":checked"),
            invoiceCompanyName: $("#invoiceCompanyName").val(),
            invoiceNip: $("#invoiceNip").val(),
            invoiceStreet: $("#invoiceStreet").val(),
            invoiceBuildingNumber: $("#invoiceBuildingNumber").val(),
            invoiceApartmentNumber: $("#invoiceApartmentNumber").val(),
            invoicePostalCode: $("#invoicePostalCode").val(),
            invoiceCityName: $("#invoiceCityName").val(),

            comments: $("#comments").val(),
            adminComments: $("#adminComments").val(),
            driverExclude: $("#driverExclude").children("option:selected").val(),
            startDate : startDate,
            stopDate : stopDate,
            cancelReason : cancelReason
        }),
        contentType: "application/json"
    })
    .done(function(data){
        refillChangesTable();
        refillDeliveryTable();
        refillEmailsTable();
        refillDeliveryReleasedTable();
        refillDeliveryChangesTable();
        orderStatusIdOld = $("#orderStatusId").children("option:selected").val();
        $("#operation-successful-modal").modal("show");
        	    window.location.assign(
                            "/orders"
                );
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation(jqxhr.responseText);
    })
    .always( function() {
      refreshOrderDetails();
    });

}

function refreshOrderDetails() {
    getOrderDetails()
    .done(function(data){
        fillOrderDetails(data);
    });
}

function getOrderDetails() {
    return $.ajax({
        url: "api/orders/" + orderId,
        method: "GET"
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillOrderDetails(data) {
    $("#email").val(data.email);
    $("#email-section span").text(data.email);
}


/* EMAIL */
$(document).ready(function() {
    $("#email-section input").hide();
    $("#email-section button:last").hide();
})

function editEmail() {
    toggleEmailSection();
}

function saveEmail() {
    updateOrder(null, null, null);
    toggleEmailSection();
}

function toggleEmailSection() {
    $("#email-section *").toggle();
}


/* SECTION: Płatności */

function refillPaymentsTable() {
    getAllOrderPayments().done(function(response) {
        refillPaymentsTableBody(response);
        refillPaymentsTableFooter(response);
    });
}

function getAllOrderPayments() {
    return $.ajax({
        url: "/api/orders/" + orderId + "/payments",
        type: "GET",
        dataType: "json"
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation(jqxhr.responseText);
    });
}

function refillPaymentsTableBody(response) {
    var $tbody = $(".payments-table").children("tbody");
    $tbody.empty();
    response.payments.forEach(function(payment) {
        refillPaymentRow(payment);
    });
}

function refillPaymentRow(payment) {
    var $tbody = $(".payments-table").children("tbody");
    $tbody.append(
            "<tr data-paymentId='" + payment.paymentId + "'>" +
                "<td class='align-middle'>" + payment.paymentNo + "</td>" +
                "<td class='align-middle paymentDate'>" + payment.dateTime + "</td>" +
                "<td class='align-middle paymentValue'>" + payment.value.toFixed(2) + " PLN</td>" +
                "<td class='align-middle paymentMethod' >" + payment.paymentMethodName + "</td>" +
                "<td class='align-middle'>" +
                    '<button type="button" ' +
                        'onclick="openDeletePaymentModal(' + payment.paymentId + ');">' +
                        "Usuń" +
                    "</button>" +
                "</td>" +
            "</tr>"
        );
}

function refillPaymentsTableFooter(response) {
    var $tfoot = $(".payments-table").children("tfoot");

    var refundRow = "";
    if (response.refund > 0) {
        refundRow =
            '<tr>' +
                '<td></td>' +
                '<th>Zwrot: </th>' +
                '<td class="align-middle" >' +
                    response.refund.toFixed(2) + " PLN" +
                '</td>' +
                '<td colspan="2"></td>' +
            "</tr>"
    }

    $tfoot.empty();
    $tfoot.html(
        "<tr>" +
            "<td></td>" +
                "<th>Suma: </th>" +
                    '<td class="align-middle" >' +
                        response.sum.toFixed(2) + " PLN" +
                    '</td>' +
                    '<td colspan="2"></td>' +
                '</tr>' +
                '<tr>' +
                    '<td></td>' +
                    '<th>Do zapłaty pozostało: </th>' +
                     '<td class="align-middle" >' +
                        response.toPayLeft.toFixed(2) + " PLN" +
                     '</td>' +
                    '<td colspan="2"></td>' +
                '</tr>' +
                refundRow
        );
}


// "Dodaj płatność" button
function closeAddPaymentModal() {
    $("#add-payment-modal").modal("hide");
};

function resetAddPaymentForm() {
    $("#add-payment-modal form").each(function() {
        this.reset();
    });
}

function postNewPaymentAndClose() {
    postNewPayment();
    closeAddPaymentModal();
}

function postNewPayment() {
    $.ajax({
        url: "/api/orders/" + orderId + "/payments",
        method: "POST",
        data: JSON.stringify({
            amount: $("#newPaymentValue").val(),
            paymentMethodId: $("#newPaymentMethod").children("option:selected").val()
        }),
        contentType: "application/json"
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation(jqxhr.responseText);
    })
    .always(function() {
        refillPaymentsTable();
        resetAddPaymentForm();
    });
}


//"Usuń" button (payments)
function openDeletePaymentModal(paymentId) {
    var $modal = $("#delete-payment-modal");
    $modal.find(".confirmButton").attr({"data-paymentId": paymentId});
    $modal.modal("show");
}

function deletePayment(paymentId) {
    var $modal = $("#delete-payment-modal");
    $.ajax({
        url: "/api/orders/" + orderId + "/payments/" + paymentId + "",
        type: "DELETE"
    })
    .done(function(){
        $("#operation-successful-modal").modal("show");
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation(jqxhr.responseText);
    })
    .always(function(){
        refillPaymentsTable();
        $modal.modal("hide");
    });
}


//"Zapisz" - użyty kod rabatowy
function saveDiscount() {
    var discountCode = $("#discountCode").val();
    if (discountCode == null || discountCode == '') {
        discountCode = "adr34r4fdscccc4rfff5w54gwwygwgbfdbst544tgstgst4";
    }

    $.ajax({
        url: "/api/orders/" + orderId + "/discountCode/" + discountCode + "",
        method: "PUT"
    })
    .done(function (data) {
        $("#operation-successful-modal").modal("show");
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation(jqxhr.responseText);
    })
   .always(function(){
        getOrderDetails()
        .done(function(data){
            $("#discountValue").text("(" + data.discountValue + "%)");
            $("#discountCode").val(data.discountCode);
            refillPaymentsTable();
            refillProductTable();
        });
    })
    ;
}


/* SECTION: Produkty w zamówieniu */

function getOrderProduct(orderId, productId) {
    return $.ajax({
        url: "/api/orders/" + orderId + "/products/" + productId + "",
        type: "GET",
        dataType: "json"
    })
}

function getAllOrderProducts(orderId) {
    return $.ajax({
        url: "/api/orders/" + orderId + "/products",
        type: "GET",
        dataType: "json"
    })
}

function getAllOrderDays(orderId) {
    return $.ajax({
        url: "/api/orders/" + orderId + "/order_days",
        type: "GET",
        dataType: "json"
    })
}

function getAllOrderDaysHistory(orderId) {
    return $.ajax({
        url: "/api/orders/" + orderId + "/order_days_history",
        type: "GET",
        dataType: "json"
    })
}

function sendOrderProductPutRequest(data, orderId, productId) {
    $.ajax({
        url: "/api/orders/" + orderId + "/products/" + productId + "",
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(data)
    })
    .done(function(response) {
        refillProductTable();
        refillChangesTable();
        refillDeliveryTable();
        refillDeliveryChangesTable();
        refillDeliveryReleasedTable();
        refillOrderDaysTable();
        refillOrderDaysHistoryTable();
        $("#operation-successful-modal").modal("show");
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation(jqxhr.responseText);
    });
}

function refillProductTable() {
    getAllOrderProducts(orderId).done(function(response) {
        refillProductTableBody(orderId, response);
        refillProductTableFooter(response);
    });
}

function refillProductTableBody(orderId, response) {
    var $tbody = $(".products-table").children("tbody");
    $tbody.empty();
    response.products.forEach(function(product) {
        refillProductRow(orderId, product);
    });
}

function refillProductRow(orderId, product) {
    var $tbody = $(".products-table").children("tbody");
    row =
            "<tr data-product='" + product.id + "'>" +
                "<td class='align-middle'>" + product.productId + "</td>" +
                "<td class='align-middle font-weight-bold productName'>" + product.typeName + ' (' + product.dietName + ")</td>" +
                "<td class='align-middle productQuantity'>" + product.quantity + "</td>" +
                "<td class='align-middle' >" + product.dateFrom + "</td>" +
                "<td class='align-middle productDays' >" + product.days + " dni</td>" +
                "<td class='align-middle productExtras' >" + product.extras + "</td>" +
                "<td class='align-middle productNetPrice' >" + product.netPrice.toFixed(2) + " PLN</td>" +
                "<td class='align-middle' >" + product.vatPercent + "%</td>" +
                "<td class='align-middle productGrossPrice' >" + product.grossPrice.toFixed(2) + " PLN</td>" +
                "<td class='align-middle' >" + product.promotion + "</td>" +
                "<td class='align-middle productGrossValue' >" + product.grossValue.toFixed(2) + " PLN</td>" +
                "<td class='align-middle productWeekendIndicator' >" + product.weekendsIndicator + "</td>" +
                "<td class='align-middle productTestIndicator' >" + product.testIndicatorName + "</td>" +
                "<td class='align-middle'>";
                if (product.productStopped == false) {
                row += '<button type="button" onclick="openSuspendProductModal(' + orderId + ', ' + product.id + ');">Wstrzymaj</button>';
                } else {
                 row += '<button type="button" onclick="openStartProductModal(' + orderId + ', ' + product.id + ');">Wznów</button>';
                }
                row += "</td>" +
                "<td class='align-middle'>";
                if (readOnly == false) {
                row +=         '<button type="button" data-orderId="" data-productId="" ' +
                                'onclick="openEditProductModal(' + orderId + ', ' + product.id + ');">' +
                            "Edytuj" +
                        "</button>";
                }
                row +=     "</td>" +
                    '<td class="align-middle">';
                if (readOnly == false) {
                row +=         '<button type="button" data-orderId="" data-productId="" ' +
                                'onclick="openDeleteProductModal(' + orderId + ', ' + product.id + ');">' +
                            "Usuń" +
                        "</button>";
                }
                row +=     "</td>" +
                "</tr>"
        $tbody.append(row);
}

function refillProductTableFooter(response) {
    var $tfoot = $(".products-table").children("tfoot");

    $tfoot.empty();
    $tfoot.html(
        "<tr>" +
            "<td colspan='12'></td>" +
            "<th>Suma przed rabatem</th>" +
            '<td class="align-middle" >' +
                response.priceBeforeDiscount.toFixed(2) + " PLN" +
            "<th>Suma po rabacie</th>" +
            '<td class="align-middle" >' +
                 response.priceAfterDiscount.toFixed(2) + " PLN" +
        "</tr>"
        );
}

function refillOrderDaysTable() {
    getAllOrderDays(orderId).done(function(response) {
        refillOrderDaysTableBody(orderId, response);
    });
}

function refillOrderDaysTableBody(orderId, response) {
    var $tbody = $(".order-days-table").children("tbody");
    $tbody.empty();
    response.forEach(function(product) {
        refillOrderDaysRow(orderId, product);
    });
}

function refillOrderDaysRow(orderId, day) {
    var $tbody = $(".order-days-table").children("tbody");
    row =
            "<tr data-product='" + day.id + "'>" +
                "<td class='align-middle'>" + day.id + "</td>" +
                 "<td class='align-middle productQuantity'" + (orderDayToBeGrey(day.status) ? " style=\"color: #c1bebe\"" : "") + ">" + day.date + "</td>" +
                "<td class='align-middle font-weight-bold productName'" + (orderDayToBeGrey(day.status) ? " style=\"color: #c1bebe\"" : "") + ">" + day.product + "</td>" +
                "<td class='align-middle font-weight-bold productName'" + (orderDayToBeGrey(day.status) ? " style=\"color: #c1bebe\"" : "") + ">" + day.quantity + "</td>" +
            "</tr>"
        $tbody.append(row);
}



function refillOrderDaysHistoryTable() {
    getAllOrderDaysHistory(orderId).done(function(response) {
        refillOrderDaysHistoryTableBody(orderId, response);
    });
}

function refillOrderDaysHistoryTableBody(orderId, response) {
    var $tbody = $(".order-days-table-history").children("tbody");
    $tbody.empty();
    response.forEach(function(product) {
        refillOrderDaysHistoryRow(orderId, product);
    });
}

function refillOrderDaysHistoryRow(orderId, day) {
    var $tbody = $(".order-days-table-history").children("tbody");
    row =
            "<tr data-product='" + day.id + "'>" +
                "<td class='align-middle'>" + day.id + "</td>" +
                 "<td class='align-middle productQuantity'" + (orderDayToBeGrey(day.status) ? " style=\"color: #c1bebe\"" : "") + ">" + day.date + "</td>" +
                "<td class='align-middle font-weight-bold productName'" + (orderDayToBeGrey(day.status) ? " style=\"color: #c1bebe\"" : "") + ">" + day.product + "</td>" +
                //"<td class='align-middle productQuantity'" + (orderDayToBeGrey(day.status) ? " style=\"color: #c1bebe\"" : "") + ">" + day.quantity + "</td>" +
                "<td class='align-middle productQuantity'" + (orderDayToBeGrey(day.status) ? " style=\"color: #c1bebe\"" : "") + ">" + day.statusName + "</td>" +
                "<td class='align-middle productQuantity'" + (orderDayToBeGrey(day.status) ? " style=\"color: #c1bebe\"" : "") + ">" + day.deliveryInfo + "</td>" +
            "</tr>"
        $tbody.append(row);
}



function orderDayToBeGrey(status) {
    return (status == 'S' || status == 'L' ||  status == 'R');
}

// "Nowy produkt" button

$(document).ready(function(){
    //setting the default value for the modal's date field
    if ($("#dateNewProductStart").attr("value") == null) {
        $("#dateNewProductStart").attr("value", (new Date()).toISOString().split("T")[0]);
    }
});

function closeAddProductModal() {
    $("#add-product-modal form").modal("hide");
};

function resetAddProductForm() {
    $("#add-product-modal form").each(function() {
        this.reset();
    });
}

function postNewProductAndClose() {
    postNewProduct();
    closeAddProductModal();
}

function postNewProduct() {
    var extrasIds = [];
    var $modal = $("#add-product-modal");
    $modal.find("#add_extras :input").each(function() {
        id = $( this ).attr('id');
        check = $modal.find("#" + id);
        if (check.prop("checked") == true) {
            extrasIds.push(check.val());
        }
    });

    $.ajax({
        url: "/api/orders/" + orderId + "/products",
        method: "POST",
        data: JSON.stringify({
            dietId: $("#newProductDiet").children("option:selected").val(),
            dietTypeId: $("#newProductType").children("option:selected").val(),
            days: $("#newProductDays").val(),
            testDay: $("#newProductTestDay").children("option:selected").val(),
            dateFrom: $("#new_product_start_date").val(),
            weekendOptionId: $("#newProductWeekendOption").children("option:selected").val(),
            extrasOne: $("#extrasOne").is(":checked"),
            quantity: $("#newProductQuantity").val(),
            extras: extrasIds
        }),
        contentType: "application/json",
    })
    .done(function(response) {
        refillProductTable();
        refillChangesTable();
        refillDeliveryTable();
        refillDeliveryChangesTable();
        refillOrderDaysTable();
        refillOrderDaysHistoryTable();
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation(jqxhr.responseText);
    });
    resetAddProductForm();
}

// "Wstrzymaj" button
function openSuspendProductModal(orderId, productId) {
    var $modal = $("#suspend-product-modal");
    $modal.find(".confirmButton").attr({
        "data-orderId": orderId,
        "data-productId": productId
    });
    var datePicker = $modal.find("#suspendProductDate");
    datePicker.datepicker({
                dateFormat: "yy-mm-dd"
     });
    $modal.modal("show");
}


function suspendProduct(orderId,  productId) {
    var $modal = $("#suspend-product-modal");
    let data = {
        suspensionDate: $modal.find("#suspendProductDate").val()
    }
    sendOrderProductPutRequest(data, orderId, productId)
    //refillProductTable();
    $modal.modal("hide");
}

// "Wznów" button
function openStartProductModal(orderId, productId) {
    var $modal = $("#start-product-modal");
    $modal.find(".confirmButton").attr({
        "data-orderId": orderId,
        "data-productId": productId
    });
    $modal.modal("show");
}

function startProduct(orderId,  productId) {
    var $modal = $("#start-product-modal");
    let data = {
        startDate: $modal.find("#dateStartProduct").val()
    }
    sendOrderProductPutRequest(data, orderId, productId)
    //refillProductTable();
    $modal.modal("hide");
}

// "Edytuj" button
function openEditProductModal (orderId, productId) {
    var $modal = $("#edit-product-modal");
    fillEditProductModal(orderId, productId);
    $modal.modal("show");
}

function fillEditProductModal(orderId, productId) {
    getOrderProduct(orderId, productId)
    .done(function(product) {
        var $modal = $("#edit-product-modal");
        $modal.find(".modal-footer button").attr({
            "data-orderId": orderId,
            "data-productId": productId
        });

        var diets = $modal.find("#edit_product_diet");
         diets.empty()
         diets.append('<option value="">' + '---' + '</option>');
         dictDiets.forEach(function(diet){
            html = '<option value="'+ diet.id +'">' + diet.value + '</option>';
            if (productId == product.id) {
                html = '<option value="'+ diet.id +'" selected>' + diet.value + '</option>';
            }
            diets.append(html);
         });
         $modal.find("#edit_product_diet").val(product.dietId);
         reloadProducts(document.getElementById('edit_product_type'), document.getElementById('edit_product_diet'));

         $modal.find("#edit_product_type").val(product.productId);

         $modal.find("#edit_product_quantity").val(product.quantity);
         $modal.find("#edit_product_days").val(product.days);
         var weekendOption = $modal.find("#edit_product_weekend_option");
         weekendOption.empty();
         dictWeekendOption.forEach(function(option){
              weekend = '<option value="'+ option.id +'">' + option.value + '</option>';
              if (option.id == product.weekendOptionId) {
                weekend = '<option value="'+ option.id +'" selected>' + option.value + '</option>';
              }
              weekendOption.append(weekend);
         });
         var test = $modal.find("#edit_product_test_day");
         test.empty();
         dictYesNo.forEach(function(option){
              opt = '<option value="'+ option.code +'">' + option.value + '</option>';
              if (String(option.code) === String(product.testIndicatorCode)) {
                opt = '<option value="'+ option.code +'" selected>' + option.value + '</option>';
              }
              test.append(opt);
         });

         var orderExtras = product.extrasList;


         var extras = $modal.find("#edit_extras");
         extras.empty();
         i = 0;
         dictExtras.forEach(function(extra){
                  if (i==0) {
                     text = 'Dodatki:';
                  } else {
                     text = '';
                  }
                  html = '<span class="col-sm-6 text-center">' + text + '</span>'+
                  '<p class="col-sm-6 align-middle"><input type="checkbox" name="edit_ext_'+ i + '" id="edit_ext_'+ i + '" value="' + extra.id + '">'+
                  '<label class=""> ' + extra.value + ' (' + extra.extraPrice + ' PLN/dzień)' + '</label></p>';

                  itWas = false;
                  orderExtras.forEach(function(orderExtra){
                    if (orderExtra.id == extra.id) {
                        itWas = true;
                    }
                  });
                  if (itWas == true) {
                                    html = '<span class="col-sm-6 text-center">' + text + '</span>'+
                                    '<p class="col-sm-6 align-middle"><input type="checkbox" name="edit_ext_'+ i + '" id="edit_ext_'+ i + '" value="' + extra.id + '" checked>'+
                                    '<label class=""> ' + extra.value + ' (' + extra.extraPrice + ' PLN/dzień)' + '</label></p>';
                  }

                  extras.append(html);
                  i++;
         });
         var changePriceValue = $modal.find("#edit_change_price_value").val(product.grossValue);
         changePriceValue.hide();
         $modal.find("#edit_currency").hide();
         $modal.find("#edit_change_price").prop('checked', false);
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation(jqxhr.responseText);
    });
}

function togglePrice() {
    var $modal = $("#edit-product-modal");
    var changePriceValue = $modal.find("#edit_change_price_value");
         changePriceValue.toggle();
         $modal.find("#edit_currency").toggle();
}

function editProduct(orderId, productId) {
    var $modal = $("#edit-product-modal");
    var extrasIds = [];
    $modal.find("#edit_extras :input").each(function() {
        id = $( this ).attr('id');
        check = $modal.find("#" + id);
        if (check.prop("checked") == true) {
            extrasIds.push(check.val());
        }
    });
    let data = {
        dietId: $("#edit_product_diet").children("option:selected").val(),
        typeId: $("#edit_product_type").children("option:selected").val(),
        quantity: $("#edit_product_quantity").val(),
        days: $("#edit_product_days").val(),
        weekendOptionId: $("#edit_product_weekend_option").children("option:selected").val(),
        testDay: $("#edit_product_test_day").children("option:selected").val(),
        extrasOne: $("#edit_product_extras_one").is(":checked"),
        changePrice :  $("#edit_change_price").is(":checked"),
        changePriceValue : $("#edit_change_price_value").val(),
        extras : extrasIds
    }

    sendOrderProductPutRequest(data, orderId, productId);
    $modal.modal("hide");
}

// "Usuń" button
function openDeleteProductModal (orderId, productId) {
    var $modal = $("#delete-product-modal");
    $modal.find(".confirmButton").attr({
        "data-orderId": orderId,
        "data-productId": productId
    });
    $modal.modal("show");
}

function deleteProduct(orderId,  productId) {
    var $modal = $("#delete-product-modal");
    $.ajax({
        url: "/api/orders/" + orderId + "/products/" + productId + "",
        type: "DELETE"
    })
    .done(function(response) {
        refillProductTable();
        refillChangesTable();
        refillDeliveryTable();
        refillDeliveryChangesTable();
        refillDeliveryReleasedTable();
        refillOrderDaysTable();
        refillOrderDaysHistoryTable();
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation(jqxhr.responseText);
    });
    $modal.modal("hide");
}


/* SECTION: Informacje na temat dostawy */
function getOrderDeliveries(orderId) {
    return $.ajax({
        url: "/api/orders/" + orderId + "/deliveries",
        type: "GET",
        dataType: "json"
    })
}

function refillDeliveryTable() {
    getOrderDeliveries(orderId)
    .done(function(response) {
        refillDeliveryTableBody(orderId, response);
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation(jqxhr.responseText);
    })
    ;
}

function refillDeliveryTableBody(orderId, response) {
    var $tbody = $(".deliveries-table").children("tbody");
    $tbody.empty();
    response.deliveries.forEach(function(delivery) {
        refillDeliveryRow(orderId, delivery);
    });
}

function refillDeliveryRow(orderId, delivery) {
    var $tbody = $(".deliveries-table").children("tbody");
    var deliveryRowStyle = "";
    if (delivery.weekend) {
        deliveryRowStyle = "style='background: #bdbdbd'";
    }
    var row =
            "<tr " + deliveryRowStyle +  " >" +
                "<td class='align-middle'>" + delivery.no + "</td>" +
                "<td class='align-middle'>" + delivery.productName + "</td>" +
                "<td class='align-middle'>" + delivery.driver + "</td>" +
                "<td class='align-middle' >" + delivery.date + "</td>" +
                "<td class='align-middle' >" + delivery.deliveryDate + "</td>" +
                "<td class='align-middle' >" + delivery.hourFrom + ' - ' + delivery.hourTo + "</td>" +
                "<td class='align-middle' >" + delivery.address + "</td>" +
                "<td class='align-middle' >";
                    if (delivery.status == 0) {
                    row +=
                     '<button type="button" onclick="stopDelivery('+ delivery.id +', ' + orderId + ')">Zwolnij</button>';
                        if (readOnly == false) {
                     row += '<button type="button" data-toggle="modal" onclick="openDeliveryChangeAddressModal('+ delivery.id +', ' + orderId + ', \'' + delivery.date + '\')">Zmień adres</button>' +
                     '<button type="button" data-toggle="modal" onclick="openDeliveryChangeHoursModal('+ delivery.id +', ' + orderId + ')">Zmień godziny</button>';
                        }
                     } else {
                     row += '---';
                     }
                 row += "</td>" +
                "<td class='align-middle' >";
                     if (readOnly == false) {
                         if (delivery.status == 0) {
                         row = row + '<button class="font-weight-bold" data-toggle="modal" onclick="openDeliverySetAsDeliveredModal('+ delivery.id +', ' + orderId + ')">Oznacz jako dostarczone</button>';
                         } else {
                         row = row + '<span>Dostarczono: '+ delivery.deliveredDateTime + '</span>';
                         }
                     } else {
                        row += '---';
                     }
                 row = row + "</td>" +
                "<td class='align-middle' >" + delivery.numberOfPhotos + "</td>" +
            "</tr>"
        $tbody.append(row);
}

function sendOrderDeliveryPutRequest(data, deliveryId) {
    return $.ajax({
        url: "/api/orders/" + orderId + "/deliveries/" + deliveryId + "",
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(data)
    });
}

function getOrderDeliveryData(deliveryId) {
    return $.ajax({
        url: "/api/orders/" + orderId + "/deliveries/" + deliveryId + "",
        type: "GET",
        dataType: "json"
    })
}

function refillDeliveryRecord(deliveryId, response) {
    $("td.deliveryAddress[data-deliveryId=" + deliveryId + "]")
        .text(response.address);

    $("td.deliveryHours[data-deliveryId=" + deliveryId + "]")
        .text(response.hourFrom + " - " + response.hourTo);

    if (response.completionDateTime) {
        $("td.deliveryConfirmation[data-deliveryId=" + deliveryId + "]")
            .text("Dostarczono: " + response.completionDateTime);
    }
}

//Zmień address
function changeDeliveryAddress(deliveryId) {
    var $modal = $("#change-delivery-address-modal");
    var data  = {
            cityId: $modal.find("#deliveryAddressCity").children("option:selected").val(),
            street: $modal.find("#deliveryAddressStreet").val(),
            buildingNumber: $modal.find("#deliveryAddressBuilding").val(),
            apartmentNumber: $modal.find("#deliveryAddressApartment").val(),
            postalCode: $modal.find("#deliveryAddressPostalCode").val()
    }
    sendOrderDeliveryPutRequest(data, deliveryId)
    .done(function(data){
            refillDeliveryReleasedTable();
            refillDeliveryTable();
            refillDeliveryChangesTable();
            refillOrderDaysTable();
            refillOrderDaysHistoryTable();
            $modal.modal("hide");
            $("#operation-successful-modal").modal("show");
            //hideBoxes();
         })
                 .fail(function(jqxhr, textStatus, errorThrown) {
                     displayErrorInformation(jqxhr.responseText);
                 });

}


//"Zmień godziny" button
function changeDeliveryHours(deliveryId) {
    var $modal = $("#change-delivery-hours-modal");
    var data = {
        "hourFrom": $modal.find("#changeDeliveryHoursFrom").val(),
        "hourTo": $modal.find("#changeDeliveryHoursTo").val()
    };
    sendOrderDeliveryPutRequest(data, deliveryId)
        .done(function(data){
                refillDeliveryReleasedTable();
                refillDeliveryTable();
                refillDeliveryChangesTable();
                refillOrderDaysTable();
                refillOrderDaysHistoryTable();
                $modal.modal("hide");
                $("#operation-successful-modal").modal("show");
                //hideBoxes();
             })
                     .fail(function(jqxhr, textStatus, errorThrown) {
                         displayErrorInformation(jqxhr.responseText);
                     });
}


//"Oznacz jako dostarczone" button
function confirmDelivery(deliveryId) {
    var $modal = $("#delivery-confirmation-modal");


    var data  = {
        complete: true
    }
    sendOrderDeliveryPutRequest(data, deliveryId)
            .done(function(data){
                    refillDeliveryReleasedTable();
                    refillDeliveryTable();
                    refillDeliveryChangesTable();
                    refillOrderDaysTable();
                    refillOrderDaysHistoryTable();
                    $modal.modal("hide");
                    $("#operation-successful-modal").modal("show");
                    //hideBoxes();
                 })
                         .fail(function(jqxhr, textStatus, errorThrown) {
                             displayErrorInformation(jqxhr.responseText);
                         });
    $modal.modal("hide");
}

/* SECTION: Historia zmian - terminarz dostaw */
function refillDeliveryChangesTable() {
    var $tbody = $(".delivery-changes-table").children("tbody");
    $tbody.empty();

    getDeliveryChanges()
    .done(function(data){
        data.forEach(function(deliveryChange){
            refillDeliveryChangeRow(deliveryChange);
        })
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation(jqxhr.responseText);
    });
}

function getDeliveryChanges() {
    return $.ajax({
        url: "/api/orders/" + orderId + "/delivery_changes" + "",
        type: "GET",
        dataType: "json"
    });
}

function getDeliveryChange(deliveryChangeId) {
    return $.ajax({
        url: "/api/orders/" + orderId + "/delivery_changes/" + deliveryChangeId + "",
        type: "GET",
        dataType: "json"
    });
}

function refillDeliveryChangeRow(deliveryChange) {
    var $tbody = $(".delivery-changes-table").children("tbody");
    $tbody.append(
            "<tr>" +
                "<td class='align-middle'>" + deliveryChange.no + "</td>" +
                "<td class='align-middle'>" + deliveryChange.type + "</td>" +
                "<td class='align-middle'>" + deliveryChange.dateTime + "</td>" +
                "<td class='align-middle'>" + deliveryChange.userName + "</td>" +
                "<td class='align-middle'>" +
                    '<button type="button" data-toggle="modal" onclick="openDeliveryChangeModal('+  orderId + ', ' + deliveryChange.id + ')">' +
                        "Szczegóły" +
                    "</button>" +
                "</td>" +
            "</tr>"
        );
}

function openDeliveryChangeModal(orderId, deliveryChangeId) {
    var $modal = $("#show-delivery-change-modal");
    getDeliveryChange(deliveryChangeId)
    .done(function(change) {
        $modal.find("#change_type").text(change.type);
        $modal.find("#change_date_from").text(change.dateFrom);
        $modal.find("#change_date_to").text(change.dateTo);
        $modal.find("#change_product_name").text(change.productName);
        $modal.find("#change_user_name").text(change.userName);
        $modal.find("#change_date_time").text(change.dateTime);

        $modal.modal("show");
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation(jqxhr.responseText);
     });
}

function openDeliveryChangeAddressModal(deliveryId, orderId, date) {
    var $modal = $("#change-delivery-address-modal");
    $modal.find("#deliveryAddressCity").empty();
    var cities = $modal.find("#deliveryAddressCity");
    cities.empty()
    dictCities.forEach(function(city){
                 cities.append('<option value="'+ city.id +'">' + city.value + '</option>');
    });
    $modal.find("#change_addr_delivery_id").val(deliveryId);
    $modal.find("#change_delivery_address_on_day").text(date);
    $modal.modal("show");
}

function openDeliveryChangeHoursModal(deliveryId, orderId) {
    var $modal = $("#change-delivery-hours-modal");
    $modal.find("#change_hours_delivery_id").val(deliveryId);
    //getOrderEmail(orderId, emailId).done(function(email) {
             //$modal.find("#email_body").html(email.message);
             $modal.modal("show");
//    })
//    .fail(function(jqxhr, textStatus, errorThrown) {
//                displayErrorInformation(jqxhr.responseText);
//    })

}

function openDeliverySetAsDeliveredModal(deliveryId, orderId) {
    var $modal = $("#delivery-confirmation-modal");
    $modal.find("#delivery_id").val(deliveryId);
    $modal.modal("show");
}

function stopDelivery(deliveryId, orderId) {
    dateTime = getCurrentDateTime();

    var data  = {
        suspensionDate: dateTime
    }
    var request = sendOrderDeliveryPutRequest(data, deliveryId);
    request
    .done(function(data){
        refillDeliveryReleasedTable();
        refillDeliveryTable();
        refillDeliveryChangesTable();
        refillOrderDaysTable();
        refillOrderDaysHistoryTable();
        $("#operation-successful-modal").modal("show");
        //hideBoxes();
     })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation(jqxhr.responseText);
    })
}

function getCurrentDateTime() {
    now = new Date();
    year = "" + now.getFullYear();
    month = "" + (now.getMonth() + 1); if (month.length == 1) { month = "0" + month; }
    day = "" + now.getDate(); if (day.length == 1) { day = "0" + day; }
    hour = "" + now.getHours(); if (hour.length == 1) { hour = "0" + hour; }
    minute = "" + now.getMinutes(); if (minute.length == 1) { minute = "0" + minute; }
    second = "" + now.getSeconds(); if (second.length == 1) { second = "0" + second; }
    return year + "-" + month + "-" + day + "T" + hour + ":" + minute + ":" + second;
}

/* SECTION: Zwolnione dostawy */
function getAllOrderReleasedDeliveries(orderId) {
    return $.ajax({
        url: "/api/orders/" + orderId + "/deliveries_released",
        type: "GET",
        dataType: "json"
    })
}

function refillDeliveryReleasedTable() {
    getAllOrderReleasedDeliveries(orderId).done(function(response) {
        refillDeliveryReleasedTableBody(orderId, response);
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
    })
    ;
}

function refillDeliveryReleasedTableBody(orderId, response) {
    var $tbody = $(".delivery-released-table").children("tbody");
    $tbody.empty();
    response.deliveries.forEach(function(delivery) {
        refillDeliveryReleasedRow(orderId, delivery);
    });
}

function refillDeliveryReleasedRow(orderId, delivery) {
    var $tbody = $(".delivery-released-table").children("tbody");
    $tbody.append(
            "<tr data-deliveryId='" + delivery.id + "'>" +
                "<td class='align-middle'>" + delivery.no + "</td>" +
                "<td class='align-middle'>" + delivery.productName + "</td>" +
                "<td class='align-middle'>" + delivery.driver + "</td>" +
                "<td class='align-middle' >" + '<input name="date_released_delivery_'+ delivery.id +'" id="date_released_delivery_'+ delivery.id +'" type="text" autocomplete="off"/>' + "</td>" +
                "<td class='align-middle' >" + delivery.hourFrom + ' - ' + delivery.hourTo + "</td>" +
                "<td class='align-middle' >" + '<button type="button" onclick="startDelivery(' + delivery.id + ', ' + orderId + ')">Uzupełnij</button>' + "</td>" +
                "</tr>"
        );

        $("input[id='date_released_delivery_" + delivery.id + "']").datepicker({
            dateFormat: "yy-mm-dd"
        });
}

function startDelivery(deliveryId, orderId) {
    date = $('#date_released_delivery_' + deliveryId).val();
    var data  = {
        startDate: date
    }
    var request = sendOrderDeliveryPutRequest(data, deliveryId);
    request
    .done(function(data){
        refillDeliveryReleasedTable();
        refillDeliveryTable();
        refillDeliveryChangesTable();
        refillOrderDaysTable();
        refillOrderDaysHistoryTable();
        $("#operation-successful-modal").modal("show");
        //hideBoxes();
     })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation(jqxhr.responseText);
    });
}


/* SECTION: Historia zmian */
function getOrderChanges(orderId) {
    return $.ajax({
        url: "/api/orders/" + orderId + "/changes",
        type: "GET",
        dataType: "json"
    })
}

function getOrderChange(orderId, changeId) {
    return $.ajax({
        url: "/api/orders/" + orderId + "/changes/" + changeId,
        type: "GET",
        dataType: "json"
    })
}

function refillChangesTable() {
    getOrderChanges(orderId).done(function(response) {
        refillChangesTableBody(orderId, response);
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
    })
    ;
}

function refillChangesTableBody(orderId, response) {
    var $tbody = $(".changes-table").children("tbody");
    $tbody.empty();
    response.changes.forEach(function(change) {
        refillChangesRow(orderId, change);
    });
}

function refillChangesRow(orderId, change) {
    var $tbody = $(".changes-table").children("tbody");
    $tbody.append(
            "<tr data-changeId='" + change.id + "'>" +
                "<td class='align-middle'>" + change.no + "</td>" +
                "<td class='align-middle'>" + change.dateTime + "</td>" +
                "<td class='align-middle'>" + change.name + "</td>" +
                "<td class='align-middle'>" +
                 '<button type="button" data-toggle="modal" onclick="openChangeModal('+  orderId + ', ' + change.id + ')">Podgląd</button>' +
                 "</td>" +
                "</tr>"
        );
}

function openChangeModal(orderId, changeId) {
    var $modal = $("#show-change-modal");

    getOrderChange(orderId, changeId)
    .done(function(change) {
        $modal.find("#chg_demanding_customer_indicator").text(change.demandingCustomerIndicator);

        $modal.find("#chg_payment_method_name").text(change.paymentMethodName);
        $modal.find("#chg_card_payment_indicator").text(change.cardPaymentIndicator);
        $modal.find("#chg_payment_status").text(change.paymentStatus);
        $modal.find("#chg_delivery_method_name").text(change.deliveryMethodName);
        $modal.find("#chg_first_name").text(change.firstName);
        $modal.find("#chg_last_name").text(change.lastName);
        $modal.find("#chg_phone_number").text(change.phoneNumber);
        $modal.find("#chg_street").text(change.street);
        $modal.find("#chg_building_number").text(change.buildingNumber);
        $modal.find("#chg_apartment_number").text(change.apartmentNumber);
        $modal.find("#chg_postal_code").text(change.postalCode);
        $modal.find("#chg_city_mame").text(change.cityName);
        $modal.find("#chg_hours").text(change.hourFrom + ' - ' + change.hourTo);

        var $tbody = $modal.find(".chg-prd-table").children("tbody");
        $tbody.empty();
        change.products.forEach(function(product) {
            $tbody.append(
                    '<tr>'+
                                                     '<td>' + product.productName + '</td>'+
                                                     '<td>' + product.quantity + '</td>'+
                                                     '<td>' + product.price.toFixed(2) + ' PLN</td>'+
                                                     '<td>' + product.dateFrom + '</td>'+
                                                     '<td>' + product.daysLeft + '</td>'+

                     '</tr>'
            );
         });
        $modal.modal("show");
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation(jqxhr.responseText);
     });
}

/* SECTION: Historia wysłanych wiadomośc */
function getOrderEmails(orderId) {
    return $.ajax({
        url: "/api/orders/" + orderId + "/emails",
        type: "GET",
        dataType: "json"
    })
}

function refillEmailsTable() {
    getOrderEmails(orderId).done(function(response) {
        refillEmailsTableBody(orderId, response);
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
    })
    ;
}

function refillEmailsTableBody(orderId, response) {
    var $tbody = $(".emails-table").children("tbody");
    $tbody.empty();
    response.emails.forEach(function(email) {
        refillEmailsRow(orderId, email);
    });
}

function refillEmailsRow(orderId, email) {
    var $tbody = $(".emails-table").children("tbody");
    $tbody.append(
            "<tr data-emailId='" + email.id + "'>" +
                "<td class='align-middle'>" + email.no + "</td>" +
                "<td class='align-middle'>" + email.title + "</td>" +
                "<td class='align-middle'>" + email.sentBy + "</td>" +
                "<td class='align-middle'>" + email.dateTime + "</td>" +
                "<td class='align-middle'>" +
                '<button type="button" data-toggle="modal" onclick="openEmailModal('+  orderId + ', ' + email.id + ')">Podgląd</button>' +
                "</td>" +
             "</tr>"
        );
}

function getOrderEmail(orderId, emailId) {
    return $.ajax({
        url: "/api/orders/" + orderId + "/emails/" + emailId,
        type: "GET",
        dataType: "json"
    })
}

function openEmailModal(orderId, emailId) {
    var $modal = $("#show-email-modal");
    getOrderEmail(orderId, emailId).done(function(email) {
             $modal.find("#email_body").html(email.message);
             $modal.modal("show");
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
                displayErrorInformation(jqxhr.responseText);
    })

}

function getDictionaryCities() {
    var result;
    $.ajax({
            url: "/api/dictionary/" + "CITIES",
            type: "GET",
            dataType: "json"
        })
    .done(function(response) {
            dictCities = response;
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation("Cannot get dict: " + name + " due to: " + jqxhr.responseText);
    });
}

function getDictionaryDiets() {
    var result;
    $.ajax({
            url: "/api/dictionary/" + "DIETS",
            type: "GET",
            dataType: "json"
        })
    .done(function(response) {
            dictDiets = response;
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation("Cannot get dict: " + name + " due to: " + jqxhr.responseText);
    });
}

function getDictionaryProducts() {
    var result;
    $.ajax({
            url: "/api/dictionary/" + "PRODUCTS",
            type: "GET",
            dataType: "json"
        })
    .done(function(response) {
            dictProducts = response;
            dictProducts = response;
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation("Cannot get dict: " + name + " due to: " + jqxhr.responseText);
    });
}

function getDictionaryExtras() {
    var result;
    $.ajax({
            url: "/api/dictionary/" + "EXTRAS",
            type: "GET",
            dataType: "json"
        })
    .done(function(response) {
            dictExtras = response;
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation("Cannot get dict: " + name + " due to: " + jqxhr.responseText);
    });
}

function getDictionaryWeekendOptions() {
    var result;
    $.ajax({
            url: "/api/dictionary/" + "WEEKEND_OPTIONS",
            type: "GET",
            dataType: "json"
        })
    .done(function(response) {
            dictWeekendOption = response;
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation("Cannot get dict: " + name + " due to: " + jqxhr.responseText);
    });
}

function getDictionaryYesNo() {
    var result;
    $.ajax({
            url: "/api/dictionary/" + "YES_NO",
            type: "GET",
            dataType: "json"
        })
    .done(function(response) {
            dictYesNo = response;
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation("Cannot get dict: " + name + " due to: " + jqxhr.responseText);
    });
}

function openAddProductModal() {
    var $modal = $("#add-product-modal");
        $modal.find("#new_product_start_date").datepicker({
                                                               dateFormat: "yy-mm-dd"
                                                           });

    $modal.find("#newProductQuantity").val(1);
    $modal.find("#newProductDays").val(1);

    var diets = $modal.find("#newProductDiet");
    diets.empty()
    diets.append('<option value="">' + '---' + '</option>');
    dictDiets.forEach(function(diet){
                     diets.append('<option value="'+ diet.id +'">' + diet.value + '</option>');
    });
    var products = $modal.find("#newProductType");
    products.empty();
    products.append('<option value="">' + '---' + '</option>');

    var weekendOption = $modal.find("#newProductWeekendOption");
    weekendOption.empty();
    dictWeekendOption.forEach(function(option){
         weekendOption.append('<option value="'+ option.id +'">' + option.value + '</option>');
    });
    var test = $modal.find("#newProductTestDay");
    test.empty();
    dictYesNo.forEach(function(option){
         opt = '<option value="'+ option.code +'">' + option.value + '</option>';
         if (option.code == 'false') {
            opt = '<option value="'+ option.code +'" selected>' + option.value + '</option>';
         }
         test.append(opt);
    });


    var extras = $modal.find("#add_extras");
    extras.empty();
    i = 0;
    dictExtras.forEach(function(extra){
         html =
         '<p><input type="checkbox" name="ext_'+ i + '" id="ext_'+ i + '" value="' + extra.id + '">'+
         '<label class=""> ' + extra.value + ' (' + extra.extraPrice + ' PLN/dzień)' + '</label></p>';
         extras.append(html);
         i++;
    });
    $modal.modal("show");
}

function reloadProducts(selectProducts, selectDiets) {
    //alert("reloadProducts select: " + selectProducts.value + " dietId: " + selectDiets.value);
    reloadProductsFromDict(selectProducts, selectDiets.value);
}

function reloadProductsFromDict(selectProducts, dietId) {
    var products = $(selectProducts);
    $(selectProducts).empty()
    if (dietId == null || dietId == '') {
        products.append('<option value="">' + '---' + '</option>');
    } else {
        dictProducts.forEach(function(product){
              if (product.extraId == dietId) {
                products.append('<option value="'+ product.id +'">' + product.value + '</option>');
              }
        });
    }
}

function sendOrderSummaryEmail(orderId) {
    $.ajax({
            url: "/api/orders/" + orderId + "/send_order_summary_email",
            method: "PUT"
        })
        .done(function (data) {
            $("#operation-successful-modal").modal("show");
            refillEmailsTable();
        })
        .fail(function(jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        });
}

function setReadOnlyElements() {
    $("#payment-button").hide();
    $("#payments").children().hide();
    $("#payments").hide();

    $("#changes").children().hide();
    $("#changes").hide();

    $("#delivery-changes").children().hide();
    $("#delivery-changes").hide();

    $("#emails").children().hide();
    $("#emails").hide();

    $("#discounts").children().hide();
    $("#discounts").hide();
}

function openStopDietModal(orderId) {
    var $modal = $("#stop-diet-modal");
    $modal.find(".confirmButton").attr({
        "data-orderId": orderId
    });
    $modal.modal("show");
}

// "Wznów" button
function openStartDietModal(orderId) {
    var $modal = $("#start-diet-modal");
    $modal.find(".confirmButton").attr({
        "data-orderId": orderId
    });
    $modal.modal("show");
}

function stopDiet(orderId) {
    var $modal = $("#stop-diet-modal");
    sendStopDietPutRequest(orderId, $modal.find("#dateStopDiet").val());
}

function startDiet(orderId) {
    var $modal = $("#start-diet-modal");
    sendStartDietPutRequest(orderId, $modal.find("#dateStartDiet").val());
}

function sendStopDietPutRequest(orderId, date) {
    return $.ajax({
        url: "/api/orders/" + orderId + "/stop_diet/"+ date,
        method: "PUT",
        contentType: "application/json"
    })
        .done(function(response) {
            $("#operation-successful-modal").modal("show");
            location.reload();
        })
        .fail(function(jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        });
}

function sendStartDietPutRequest(orderId, date) {
    return $.ajax({
        url: "/api/orders/" + orderId + "/start_diet/"+ date,
        method: "PUT",
        contentType: "application/json"
    })
            .done(function(response) {
                $("#operation-successful-modal").modal("show");
                location.reload();
            })
            .fail(function(jqxhr, textStatus, errorThrown) {
                displayErrorInformation(jqxhr.responseText);
            });
}