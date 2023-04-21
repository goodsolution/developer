var dictCities;
var dictDiets;
var dictProducts;
var dictExtras;
var dictWeekendOption;
var dictYesNo;
var dictDrivers;
var dictDeliveryMethods;
var dictPaymentMethods;
var dictOrderStatuses;

$(document).ready(function () {
    getDictionaryDrivers();
    getDictionaryDiets();
    getDictionaryProducts();
    getDictionaryExtras();
    getDictionaryWeekendOptions();
    getDictionaryYesNo();
    getDictionaryDeliveryMethods();
    getDictionaryPaymentMethods();
    getDictionaryOrderStatuses();
});

function cloneNewOrderProductForm() {
    $(".product").first().clone(true, true).prependTo("#product-container");
}

function addAnotherProduct() {

    dietsHtml =                                                 '<label class=\"col-md-6 text-center pt-1\">' +
                                                                    'Wybierz dietę:' +
                                                                '</label>' +
                                                                '<select name=\"newOrderDiet\"' +
                                                                 '       class=\"mb-1 col-md-6 newOrderDiet\" onchange=\"reloadProducts(document.getElementById(\'newOrderType_' + i + '\'), this)\">';
    dietsHtml += '<option value="">' + '---' + '</option>';
    dictDiets.forEach(function(diet){
        dietsHtml += '<option value="'+ diet.id +'">' + diet.value + '</option>';
    });
    dietsHtml +=                                                           ' </select>';

    productsHtml = '      <label class=\"col-md-6 text-center pt-1\" >' +
                                                             '          Wybierz rodzaj:' +
                                                             '      </label>' +
                                                             '          <select id=\"newOrderType_' + i + '\" name=\"newOrderType\"' +
                                                            '               class=\"mb-1 col-md-6 newOrderType\" >' ;
    productsHtml += '<option value="">' + '---' + '</option>';

    productsHtml +=                                     '       </select>';

    testHtml =                                     '            <label class=\"col-md-6 text-center pt-1\">' +
                                                   '                Jeden dzień testowy:' +
                                                   '            </label>' +
                                                  '             <select name=\"newOrderTestDay\"' +
                                                  '                     class=\"mb-1 col-md-6 newOrderTestDay\" >';
    dictYesNo.forEach(function(diet){
            testOption = '<option value="'+ diet.code +'">' + diet.value + '</option>';
            if (diet.code == 'false') {
                testOption = '<option value="'+ diet.code +'" selected>' + diet.value + '</option>';
            }
            testHtml += testOption;
    });
    testHtml +=                                             '              </select>' ;
    weekendsHtml =                                           '      <label class=\"col-md-6 text-center pt-1\">' +
                                                              '         Weekendy:' +
                                                               '    </label>' +
                                                                '   <select name=\"newOrderWeekendOptionId\"' +
                                                                '           class=\"mb-1 col-md-6 newOrderWeekendOptionId\" >' ;
    dictWeekendOption.forEach(function(option){
                  weekendsHtml += '<option value="'+ option.id +'">' + option.value + '</option>';

    });
    weekendsHtml +=                                                            '   </select>' ;

    extrasHtml =                                              '   <label class=\"col-md-6 text-center pt-1\">' +
                                                               '      Dodatki:' +
                                                               '  </label>' +
                                                               '  <p class=\"col-md-6 align-middle\">';
    a = 0;
    dictExtras.forEach(function(extra){
        extrasHtml +=                                             '     <input class=\"extrasOne \" type=\"checkbox\"' +
                                                                 '           id="ex_'+ a + '" name="ex_'+ a + '" value="'+ extra.id +'">' +
                                                                  '   <span>' + extra.value + ' (' + extra.extraPrice + ' PLN/dzień)</span><br>';
        a++;
    });
    extrasHtml +=                                                          '   </p>' ;

    html = '<div class=\"product border\">' +
                                        '<div class=\"p-1\">' +
                                            '<div class=\"form-row pb-1\">' +
                                                dietsHtml +
                                          '  </div>' +
                                          '  <div class=\"form-row pb-1\">' +
                                                productsHtml +
                                         '   </div>' +
                                         '   <div class=\"form-row pb-1\">' +
                                         '       <label class=\"col-md-6 text-center pt-1\">' +
                                        '            Wpisz liczbę dni:' +
                                       '         </label>' +
                                      '          <input class=\"col-md-6 newOrderDays\" type=\"number\" required' +
                                      '                 name=\"newOrderDays\" value=\"1\">' +
                                     '       </div>' +
                                    '        <div class=\"form-row pb-1\">' +
                                                testHtml +
                                   '         </div>' +
                                    '        <div class=\"form-row pb-1\">' +
                                     '           <label class=\"col-md-6 text-center pt-1 \">' +
                                      '              Data rozpoczęcia diety:' +
                                       '         </label>' +
                                        '        <input class=\"col-md-6 dateNewOrderStart\" type=\"text\" required' +
                                        '               name=\"dateNewOrderStart\" id=\"dateNewOrderStart_' + i + '\">' +
                                        '    </div>' +
                                         '   <div class=\"form-row pb-1\">' +
                                                weekendsHtml +
                                            '</div>' +
                                            '<div class=\"form-row pb-1\">' +
                                                extrasHtml +
                                           ' </div>' +
                                           ' <div class=\"form-row pb-1\">' +
                                            '    <label class=\"col-md-6 text-center pt-1\">' +
                                             '       Liczba zestawów:' +
                                              '  </label>' +
                                               ' <input class=\"col-md-6 quantity\" type=\"number\" required' +
                                                '       name=\"quantity\" id=\"quantity\" value=\"1\">' +
                                            '</div>' +
                                        '</div>' +
                                    '</div>';
    $("#product-container").append(html);
        $("input[id='dateNewOrderStart_" + i + "']").datepicker({
                    dateFormat: "yy-mm-dd"
        });
    i++;
}


function postNewOrderAndReturn() {
    postNewOrder()
    .done(function(response){
                $("#add-order-modal").modal("hide");
                $("#operation-successful-modal").modal("show");
    });
}

function postNewOrderAndEdit() {
    postNewOrder()
    .done(function(response){
        window.location.assign(
            "/order?id=" + response.newOrderId + ""
        );
    });
}

function postNewOrder() {
    var request = $.ajax({
        url: "/api/orders",
        method: "POST",
        data: JSON.stringify({
            customerId: $("#new_order_customer").val(),
            purchaseDateTime: $("#new_order_purchase_datetime").val(),
            purchaseTime: $("#new_order_purchase_time").val(),
            deliveryMethodId: $("#new_order_delivery").children("option:selected").val(),
            paymentMethodId: $("#new_order_payment").children("option:selected").val(),
            driverId: $("#new_order_driver").children("option:selected").val(),
            products: {
                dietId: $(".newOrderDiet").children("option:selected").map(function(){
                    return $(this).val();
                }).toArray(),
                dietTypeId: $(".newOrderType").children("option:selected").map(function(){
                    return $(this).val()
                }).toArray(),
                days: $(".newOrderDays").map(function(){
                    return $(this).val()
                }).toArray(),
                testDay: $(".newOrderTestDay").children("option:selected").map(function(){
                    return $(this).val()
                }).toArray(),
                startDate: $(".dateNewOrderStart").map(function(){
                    return $(this).val()
                }).toArray(),
                weekendOptionId: $(".newOrderWeekendOptionId").children("option:selected").map(function(){
                    return $(this).val()
                }).toArray(),
                extrasOne: $(".extrasOne").map(function(){
                    return $(this).is(":checked")
                }).toArray(),
                extras: $(".extrasOne").map(function(){
                                    return $(this).attr('id') + ";" + $(this).val() + ";" + $(this).prop('checked')
                                }).toArray(),
                quantity: $(".quantity").map(function(){
                    return $(this).val()
                }).toArray(),
            },
            discountCode: $("#new_order_discount_code").val(),
            comments: $("#new_order_comments").val(),
            demandingClient: $("#new_order_demanding_customer").children("option:selected").val(),
            sendEmail: $("#sendMail").is(":checked"),
            driverExclude: $("#newOrderDriverExclude").is(":checked"),
            newOrderGroup: $("#newOrderGroup").is(":checked"),
            status : $("#new_order_status").children("option:selected").val()
        }),
        contentType: "application/json",
        dataType: "json",
    })
    .fail(function(jqxhr, textStatus, errorThrown){
         displayErrorInformation(jqxhr.responseText);
    });
    return request;
}

function currentDate() {
    var d = new Date(),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [year, month, day].join('-');
}

function currentTime() {
    var d = new Date();
    hours = formatTwoDigits(d.getHours());
    minutes = formatTwoDigits(d.getMinutes());
    seconds = formatTwoDigits(d.getSeconds());
    return hours + ":" + minutes + ":" + "00";
}

function formatTwoDigits(n) {
    return n < 10 ? '0' + n : n;
}

function openAddOrderModal(customerId) {
    i = 1;
    var $modal = $("#add-order-modal");
    //$modal.find(".confirmButton").attr({"data-paymentId": paymentId});
    $modal.find("#new_order_search_customer").val('');
    $modal.find("#new_order_customer").prop('disabled', false);
    if (customerId != null) {
        $modal.find("#new_order_search_line").hide();
        chooseCustomer(customerId);
    }
    $modal.find("#new_order_customer").empty();


    var datePicker = $modal.find("#new_order_purchase_datetime");
    datePicker.datepicker({
            dateFormat: "yy-mm-dd",
            minDate: 0
    });

    datePicker.val(currentDate());

    var timePicker = $modal.find("#new_order_purchase_time");
    timePicker.timepicker({
                 timeFormat: 'HH:mm',
                 interval: 60,
                 minTime: '00:00',
                 maxTime: '23:00',
                 startTime: '00:00',
                 dynamic: false,
                 dropdown: true,
                 scrollbar: true
             });

    timePicker.val(currentTime());

    $("#product-container").empty();
    addAnotherProduct();

    var delivery = $modal.find("#new_order_delivery");
    delivery.empty();
    dictDeliveryMethods.forEach(function(dic){
         opt = '<option value="'+ dic.id +'">' + dic.value + '</option>';
         delivery.append(opt);
    });

    var payment = $modal.find("#new_order_payment");
    payment.empty();
    dictPaymentMethods.forEach(function(dic){
         opt = '<option value="'+ dic.id +'">' + dic.value + '</option>';
         payment.append(opt);
    });

    var test = $modal.find("#new_order_driver");
    test.empty();
    opt = '<option value="">' + "---" + '</option>';
    test.append(opt);
    dictDrivers.forEach(function(driver){
         opt = '<option value="'+ driver.id +'">' + driver.value + '</option>';
         test.append(opt);
    });

    var status = $modal.find("#new_order_status");
    status.empty();
    dictOrderStatuses.forEach(function(dict){
         opt = '<option value="'+ dict.id +'">' + dict.value + '</option>';
         status.append(opt);
    });

    var demanding = $modal.find("#new_order_demanding_customer");
    demanding.empty();
    dictYesNo.forEach(function(option){
        opt = '<option value="'+ option.code +'">' + option.value + '</option>';
        if (option.code == 'false') {
             opt = '<option value="'+ option.code +'" selected>' + option.value + '</option>';
        }
        demanding.append(opt);
    });
    $modal.modal("show");
}

function getDictionaryDrivers() {
    $.ajax({
            url: "/api/dictionary/" + "DRIVERS",
            type: "GET",
            dataType: "json"
        })
    .done(function(response) {
            dictDrivers = response;
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation("Cannot get dict: " + "DRIVERS" + " due to: " + jqxhr.responseText);
    });
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

function getDictionaryDeliveryMethods() {
    var result;
    $.ajax({
            url: "/api/dictionary/" + "SHIPMENT_TYPES",
            type: "GET",
            dataType: "json"
        })
    .done(function(response) {
            dictDeliveryMethods = response;
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation("Cannot get dict: " + name + " due to: " + jqxhr.responseText);
    });
}

function getDictionaryPaymentMethods() {
    var result;
    $.ajax({
            url: "/api/dictionary/" + "ORDER_PAYMENT_METHODS",
            type: "GET",
            dataType: "json"
        })
    .done(function(response) {
            dictPaymentMethods = response;
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation("Cannot get dict: " + name + " due to: " + jqxhr.responseText);
    });
}

function getDictionaryOrderStatuses() {
    var result;
    $.ajax({
            url: "/api/dictionary/" + "ORDER_STATUSES",
            type: "GET",
            dataType: "json"
        })
    .done(function(response) {
            dictOrderStatuses = response;
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation("Cannot get dict: " + name + " due to: " + jqxhr.responseText);
    });
}

function reloadCustomers(substring) {
    if (substring.length > 2) {
        getCustomers(substring)
        .done(function(response) {
            var $modal = $("#add-order-modal");
            var customer = $modal.find("#new_order_customer");
            customer.empty();
            response.forEach(function(dic){
                 opt = '<option value="'+ dic.id +'">' + dic.firstAndLastName + ' (' + dic.email + ')</option>';
                 customer.append(opt);
            });
            setDefaultDriver();
        })
        .fail(function(jqxhr, textStatus, errorThrown) {
            displayErrorInformation("Nie mogę pobrać klientów" + jqxhr.responseText);
        });
    }
}

function chooseCustomer(customerId) {
            var $modal = $("#add-order-modal");
            var customer = $modal.find("#new_order_customer");
            customer.empty();
            getCustomer(customerId)
            .done(function(response) {
                 opt = '<option value="'+ response.id +'">' + response.firstAndLastName + ' (' + response.email + ')</option>';
                 customer.append(opt);
                 setDefaultDriver();
            })
            .fail(function(jqxhr, textStatus, errorThrown) {
                 displayErrorInformation("Nie mogę pobrać klienta" + jqxhr.responseText);
            });
            customer.prop('disabled', true);
}

function setDefaultDriver() {
    var customerId = $("#new_order_customer").val();
    if (customerId != null) {
    getCustomer(customerId)
        .done(function(response) {
             var $modal = $("#add-order-modal");
             var customer = $modal.find("#new_order_driver");
             customer.val(response.defaultDriverId);
             //alert('response.defaultDriverId ' + response.defaultDriverId)
             var demandingCustomer = $modal.find("#new_order_demanding_customer");
             //alert('response.demanding ' + response.demanding)
             if (response.demanding) {
                demandingCustomer.val('true');
             } else {
                demandingCustomer.val('false');
             }
        })
        .fail(function(jqxhr, textStatus, errorThrown) {
            displayErrorInformation("Nie mogę pobrać klienta" + jqxhr.responseText);
        });
     }
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

function getCustomer(customerId) {
    return $.ajax({
            url: "/api/customer/" + customerId,
            type: "GET",
            dataType: "json",
            contentType: "application/json"
        });
}

function getCustomers(substring) {
    return $.ajax({
            url: "/api/customer/substring/" + substring,
            type: "GET",
            dataType: "json"
        });

}



