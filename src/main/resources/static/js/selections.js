$(document).ready(function() {
    trace('page.show', 'selections');
    findCourses();

    document.addEventListener('keyup', (e) => {
        if (e.code === "Enter") {
            findCourses();
        }
    })

});

function refreshCourses() {
    trace("button.search",$("#search").val());
    findCourses();
}

function findCourses() {
    $.ajax({
        url: "/api/crs/courses" + prepareUrl(),
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (courses) {
        $("#results").empty();
        fillResults(courses);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillResults(courses) {
    courses.forEach(function(course){
        appendCourse(course);
    });
}

function appendCourse(course) {

    var courseCode = "'" + course.code + "'";

    $('#results').append(
        '<div class="col-lg-4 my-3">' +
            '<div class="text-center p-1 rounded bg-light shadow-sm product">' +
                '<div class="my-3" style="height:50px"><a class="product-title" href="/selection/' + course.code + '">' + course.title + '</a></div>' +
                '<div style="width:200px;height:200px;" class="m-auto" onclick="goToDetailsPage(' + courseCode + ')">' + prepareProductImage(course.imageName) + '</div>' +
                //'<div class="my-1"><b> ' + course.price + ' PLN </b></div>' +
                '<div class="my-1"><b> ' + 'Bezpłatna!' + ' </b></div>' +
                '<div class="my-2">' +
                    '<div>' +
                        prepareCourseInteractionButton(course) +
                    '</div>' +
                '</div>' +
            '</div>' +
        '</div>'
    );

}

function prepareCourseInteractionButton(course) {
    if(course.saleStatus === 'o') {
        return '<button type="button" class="btn btn-outline-success" onclick="addToBasket(' + course.id + ')"><i class="fas fa-cart-plus"></i> ' + lang.home_addToBasket + '</button>';
    } else {
        return '<a type="button" class="btn btn-outline-success" href="/selection/' + course.code + '"></i>' + lang.home_findOutMore + '</a>';
    }
}

function addToBasket(id) {
    $.ajax({
        url: "/api/crs/add-course-to-basket/" + id,
        type: "post",
        contentType: "application/json"
    })
    .done(function () {
        updateProductsInBasketQuantity();
        $("#successfully-added-to-basket-modal").modal('show');
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function goToDetailsPage(code) {
    window.location.href = '/selection/' + code;
}

function prepareUrl() {

    var url = "?";

    var title = $("#search").val();
    if(title != "") {
        url+= "&title=" + title;
    }

    url+= "&type=s";

    return url;
}