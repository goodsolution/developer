
var objToDeleteId;

$(document).ready(function () {

    $('#create-modal').on('hide.bs.modal', function (e) {
        clearCreateModal();
    });

    $("#filter input, #filter select, [form='filter']").on("change", function () {
        findCourses();
    });

    findCourses();
});

function findCourses() {
    $.ajax({
        url: "/admin/api/crs/courses?&type=c" + prepareUrl(),
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (courses) {
        $("#records").empty();
        fillResults(courses);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function prepareUrl(){
     var url = "";
     url += preparePaginationUrl();

     var visibilityStatus = $("#visibility-status").find(":selected").val();
     var authorId = $("#author_filter").children(":selected").val();

     if (visibilityStatus != "") {
        url += "&visibility_status=" + visibilityStatus;
     }
     if (authorId != "") {
        url += "&author_id=" + authorId;
     }

     return url;
}

function fillResults(courses) {
    courses.forEach(function(course){
        fillRow(course);
    });
}

function fillRow(course) {
    $('#records').append(
        "<tr>" +
            "<td class='align-middle'>" + course.id + "</td>" +
            "<td class='align-middle'>" + course.title + "</td>" +
            "<td class='align-middle'>" + course.price + " PLN" + "</td>" +
            "<td class='align-middle'>" + course.language + "</td>" +
            "<td class='align-middle'>" + course.author.firstName + " " + course.author.lastName + "</td>" +
            "<td class='align-middle'>" + prepareVisibilityStatusFromDict(course.visibilityStatus) + "</td>" +
            "<td class='align-middle'>" + prepareDetailsButton(course.id) + "</td>" +
            "<td class='align-middle'>" + prepareDeleteButton(course.id) + "</td>" +
        "</tr>"
    );
}

function prepareVisibilityStatusFromDict(visibilityStatus) {
    for(i=0; i<courseVisibilityStatusesDict.length; i++) {
        if(courseVisibilityStatusesDict[i].code === visibilityStatus) {
            return courseVisibilityStatusesDict[i].value;
        }
    }
}

function prepareDetailsButton(id) {
    return '<button type="button" class="btn btn-primary" onclick="goToDetailsPage(' + id + ')">' + lang.Details + '</button>';
}

function prepareDeleteButton(id) {
    return '<button type="button" class="btn btn-danger" onclick="setObjectToDeleteIdAndShowModal(' + id + ')">' + lang.Delete + '</button>';
}

function goToDetailsPage(id) {
    window.location.href = "/admin/course/" + id;
}

function setObjectToDeleteIdAndShowModal(id) {
    objToDeleteId = id;
    $('#delete-object-modal').modal('show');
}

function sendDeleteRequest(){
    $.ajax({
        url: "/admin/api/crs/course/" + objToDeleteId,
        type: "DELETE"
    })
        .done(function(response) {
            $('#delete-object-modal').modal('hide');
            $('#operation-successful-modal').modal('show');
            findCourses();
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

function sendCreateRequest() {
    $.ajax({
        url: "/admin/api/crs/course",
        method: "post",
        contentType: "application/json",
        data: JSON.stringify({
            title: $("#create-title").val(),
            authorId: $('#author').find(":selected").val(),
            price: $("#create-price").val(),
            language: $('#create-language').find(":selected").val(),
            description: $("#create-description").val(),
            code: $("#create-code").val(),
            type: 'c'
        })
    })
        .done(function () {
            $("#create-modal").modal('hide');
            $("#operation-successful-modal").modal('show');
            findCourses();
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}


function clearCreateModal() {
    $("#create-title").val('');
    $("#create-price").val('');
    $("#create-code").val('');
    $("#create-description").val('');
}
