$(document).ready(function () {

    findCourses();
    $("#filter input, #filter select, [form='filter']").on("change", function () {
        findCourses();
    });

});


function findCourses() {
    $.ajax({
        url: "/admin/api/crs/statistic/courses/completion?" + preparePaginationUrl(),
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (courses) {
        $("#records").empty();
        fillResults(courses.courseCompletion);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillResults(courses) {
    courses.forEach(function(course){
        fillRow(course);
    });
}

function fillRow(course) {
    $('#records').append(
        "<tr>" +
            "<td class='align-middle'>" + course.login + "</td>" +
            "<td class='align-middle'>" + course.courseTitle + " </td>" +
            "<td class='align-middle'>" + course.percentOfCompletion + "%" + "</td>" +
        "</tr>"
    );
}



