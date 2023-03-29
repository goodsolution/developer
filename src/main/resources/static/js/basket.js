$(document).ready(function() {
    trace('page.show', 'basket');
    findCourses();
    fillInvoiceData();
});

function findCourses() {
    $.ajax({
        url: "/api/crs/courses-in-basket",
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (basket) {
        fillResults(basket);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillResults(basket) {
    fillTotalPrice(basket.totalPrice);
    fillProductsInBasket(basket.courses);
    showBuyButton(basket.totalPrice);
    showSummary(basket.courses);
    showNotLoggedInfo(basket.courses);

    if(basket.totalPrice == 0) {
        $("#form-data").hide();
        $("#payment-methods").hide();
    }
}

function showBuyButton(totalPrice){
    if(totalPrice == 0) {
        $('#buy-free-button').show();
    } else {
        $('#buy-button').show();
    }
}

function fillTotalPrice(totalPrice) {
    $('#total-price').text(totalPrice);
}

function fillProductsInBasket(courses) {
    if(courses.length > 0) {
        fillCourses(courses);
    } else {
        fillInfoBasketEmpty();
    }
}

function fillInfoBasketEmpty() {
    $('#results').append(
    '<div class="text-center">'+
        '<h6>' + lang.basket_noProducts + '</h6>'+
    '</div>'
    );
}

function fillCourses(courses) {
    courses.forEach(function(course){
        appendCourse(course);
    });
}

function appendCourse(course) {
    $('#results').append(
    '<div class="rounded bg-light shadow-sm row my-1 p-2">'+
        '<div class="col-sm-6 text-center row m-0 p-0">' +
            '<div class="col-sm-6 d-flex align-items-center justify-content-center py-1">' +
                prepareImage(course.imageName) +
            '</div>'+
            '<div class="col-sm-6 d-flex align-items-center justify-content-center py-1"><b>' +
                course.title +
            '</b></div>' +
        '</div>'+
        '<div class="col-sm-6 row text-center m-0 p-0">'+
            '<div class="col-sm-6 text-center d-flex align-items-center justify-content-center py-1">' + course.price + ' PLN</div>'+
            '<div class="col-sm-6 text-center d-flex align-items-center justify-content-center py-1">'+
                '<button class="btn btn-outline-danger btn-sm" onclick="deleteFromBasket(' + course.id + ')"><i class="fas fa-trash-alt"></i> ' + lang.basket_delete + '</button>'+
            '</div>'+
        '</div>'+
    '</div>'
    );
}

function prepareImage(imageName) {
    if(imageName === "") {
        return "";
    } else {
        return '<img src="/get-image/' + imageName + '/" class="img-thumbnail" width="75px" height="75px">';
    }
}

function deleteFromBasket(id) {
    $.ajax({
        url: "/api/crs/delete-course-from-basket/" + id,
        type: "delete",
        contentType: "application/json"
    })
    .done(function () {
        location.reload();
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function showSummary(courses) {
    if(courses.length > 0 && loggedCustomer != null) {
        $("#summary").show();
    }
}

function showNotLoggedInfo(courses) {
    if(courses.length > 0 && loggedCustomer == null) {
        $("#not-logged-info").show();
    }
}

function orderCoursesInBasket() {
    $.ajax({
        url: "/api/crs/order-courses-in-basket",
        method: "post",
        contentType: "application/json",
        data: JSON.stringify({
            invoiceType: prepareInvoiceType(),
            invoiceFirstAndLastName: $("#first-and-last-name").val(),
            invoiceCompanyName: $("#company-name").val(),
            invoiceStreet: $("#street").val(),
            invoicePostalCode: $("#postal-code").val(),
            invoiceCity: $("#city").val(),
            invoiceNip: $("#nip").val(),
            invoiceCountry: $('#country').find(":selected").val()
        })
    })
    .done(function (paymentUrl) {
        window.location.href = paymentUrl;
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function displayCompanyInvoiceForm() {

    $("#first-and-last-name-field").hide();
    $("#company-name-field").show();
    $("#nip-field").show();

    $("#first-and-last-name").val("");

    fillCompanyName();
    fillNip();

}

function displayPrivatePersonInvoiceForm() {

    $("#company-name-field").hide();
    $("#nip-field").hide();
    $("#first-and-last-name-field").show();

    $("#company-name").val("");
    $("#nip").val("");

    fillFirstAndLastName();

}

function prepareInvoiceType() {
    if($("#private-person").prop('checked')) {
        return 'p';
    } else if ($("#company").prop('checked')) {
        return 'c';
    } else {
        return "";
    }
}

function fillInvoiceData() {
    if(loggedCustomer != null) {
        if(loggedCustomer.invoiceType == 'p') {
            displayPrivatePersonInvoiceForm();
            $('#private-person').prop('checked', true);
            fillFirstAndLastName();
        } else if (loggedCustomer.invoiceType == 'c') {
            displayCompanyInvoiceForm();
            $('#company').prop('checked', true);
            fillCompanyName();
            fillNip();
        }
        fillStreet();
        fillPostalCode();
        fillCity();
        fillCountry();
    }
}

function fillFirstAndLastName() {
    if(loggedCustomer.invoiceFirstAndLastName != null && loggedCustomer.invoiceFirstAndLastName != 'null') {
        $("#first-and-last-name").val(loggedCustomer.invoiceFirstAndLastName);
    }
}

function fillCompanyName() {
    if(loggedCustomer.invoiceCompanyName != null && loggedCustomer.invoiceCompanyName != 'null') {
        $("#company-name").val(loggedCustomer.invoiceCompanyName);
    }
}

function fillStreet() {
    if(loggedCustomer.invoiceStreet != null && loggedCustomer.invoiceStreet != 'null') {
        $("#street").val(loggedCustomer.invoiceStreet);
    }
}

function fillPostalCode() {
    if(loggedCustomer.invoicePostalCode != null && loggedCustomer.invoicePostalCode != 'null') {
        $("#postal-code").val(loggedCustomer.invoicePostalCode);
    }
}

function fillCity() {
    if(loggedCustomer.invoiceCity != null && loggedCustomer.invoiceCity != 'null') {
        $("#city").val(loggedCustomer.invoiceCity);
    }
}

function fillCountry() {
    if(loggedCustomer.invoiceCountry != null && loggedCustomer.invoiceCountry != 'null') {
        $("#country").val(loggedCustomer.invoiceCountry);
    }
}

function fillNip() {
    if(loggedCustomer.invoiceNip != null && loggedCustomer.invoiceNip != 'null') {
        $("#nip").val(loggedCustomer.invoiceNip);
    }
}