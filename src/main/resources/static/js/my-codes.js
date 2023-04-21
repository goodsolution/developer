$(document).ready(function() {
    trace('page.show', 'my-codes');
    findCourses();
});

function findCourses() {
    $.ajax({
        url: "/api/crs/courses-bought-by-logged-customer?&courseType=b",
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
    if (courses.length == 0) {
        showInfoNoCourses();
    } else {
        printResults(courses);
    }
}

function showInfoNoCourses() {
    $('#container').append(
    '<div class="text-center">'+
        "<h6>" + lang.myCodes_noCodes + "</h6>"+
    '</div>'
    );
}

function printResults(courses) {
    courses.forEach(function(course){
        appendCourse(course);
    });
}

function appendCourse(course) {
    $('#results').append(
        '<div class="col-lg-4 my-3">' +
            '<div class="text-center p-1 rounded bg-light shadow-sm product">' +
                '<div class="my-3" style="height:50px">' + course.title + '</div>' +
                '<div style="width:200px;height:200px;" class="m-auto" onclick="goToWatchPage(' + course.id + ')">' + prepareProductImage(course.imageName) + '</div>' +
                '<div class="my-2">' +
                    '<div class="p-3">' +
                        '<button class="btn btn-outline-primary btn-lg btn-block" onclick="goToWatchPage(' + course.id + ')">' + lang.myCodes_watch + '</button>' +
                    '</div>' +
                '</div>' +
            '</div>' +
        '</div>'
    );
}

function goToWatchPage(id) {
    window.location.href = '/watch/course/' + id;
}