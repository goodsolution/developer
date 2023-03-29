
var objToDeleteId;

$(document).ready(function () {

    $("#filter input, #filter select, [form='filter']").on("change", function () {
        findCourseComments();
    });

    findCourseComments();
});

function findCourseComments() {
    $.ajax({
        url: "/admin/api/crs/course-comments?" + preparePaginationUrl(),
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (response) {
        $("#records").empty();
        fillResults(response.courseComments);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillResults(courseComments) {
    courseComments.forEach(function(courseComment){
        fillRow(courseComment);
    });
}

function fillRow(courseComment) {
    $('#records').append(
        "<tr>" +
            "<th class='align-middle'>" + courseComment.id + "</td>" +
            "<td class='align-middle'>" + courseComment.course.title + "</td>" +
            "<td class='align-middle'>" + courseComment.customer.login + "</td>" +
            "<td style='width: 25%' class='align-middle'>" + courseComment.text + "</td>" +
            "<td class='align-middle'>" + prepareDateTime(courseComment.createDatetime) + "</td>" +
            "<td class='align-middle'>" + prepareVisibilityStatusSelect(courseComment.status, courseComment.id) + "</td>" +
            "<td class='align-middle'>" + prepareDeleteButton(courseComment.id) + "</td>" +
        "</tr>"
    );
}

function prepareVisibilityStatusSelect(visibilityStatus, courseCommentId) {
    var select = '';
    select += '<select id="visibility-status-select-' + courseCommentId + '" class="form-control" onchange="changeVisibilityStatus(' + courseCommentId + ')">';
    select += prepareVisibilityStatusOptions(visibilityStatus);
    select += '</select>';
    return select;
}

function prepareVisibilityStatusOptions(visibilityStatus) {
    var options;

    courseCommentVisibilityStatusesDict.forEach(function(visibilityStatusFromDict){

        var select = '';

        if(visibilityStatusFromDict.code == visibilityStatus) {
            select = 'selected';
        }

        options += '<option ' + select + ' value="' + visibilityStatusFromDict.code + '">' + visibilityStatusFromDict.value + '</option>';

    });

    return options;
}


function prepareDeleteButton(id) {
    return '<button type="button" class="btn btn-danger" onclick="setObjectToDeleteIdAndShowModal(' + id + ')">' + lang.Delete + '</button>';
}

function setObjectToDeleteIdAndShowModal(id) {
    objToDeleteId = id;
    $('#delete-object-modal').modal('show');
}

function sendDeleteRequest(){
    $.ajax({
        url: "/admin/api/crs/course-comment/" + objToDeleteId,
        type: "DELETE"
    })
        .done(function(response) {
            $('#delete-object-modal').modal('hide');
            $('#operation-successful-modal').modal('show');
            findCourseComments();
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

function changeVisibilityStatus(courseCommentId) {
    $.ajax({
        url: "/admin/api/crs/course-comment/" + courseCommentId,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify({
            visibilityStatus : $("#visibility-status-select-" + courseCommentId).find(":selected").val()
        })
    })
        .done(function () {
            $("#operation-successful-modal").modal('show');
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}
