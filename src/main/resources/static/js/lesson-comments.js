
var objToDeleteId;

$(document).ready(function () {

    $("#filter input, #filter select, [form='filter']").on("change", function () {
        findLessonComments();
    });

    findLessonComments();
});

function findLessonComments() {
    $.ajax({
        url: "/admin/api/crs/lesson-comments?" + preparePaginationUrl(),
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (response) {
        $("#records").empty();
        fillResults(response.lessonComments);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillResults(lessonComments) {
    lessonComments.forEach(function(lessonComment){
        fillRow(lessonComment);
    });
}

function fillRow(lessonComment) {
    $('#records').append(
        "<tr>" +
            "<th class='align-middle'>" + lessonComment.id + "</td>" +
            "<td class='align-middle'>" + lessonComment.lesson.title + "</td>" +
            "<td class='align-middle'>" + lessonComment.lesson.course.title + "</td>" +
            "<td class='align-middle'>" + lessonComment.customer.login + "</td>" +
            "<td style='width: 25%' class='align-middle'>" + lessonComment.text + "</td>" +
            "<td class='align-middle'>" + prepareDateTime(lessonComment.createDatetime) + "</td>" +
            "<td class='align-middle'>" + prepareStatusSelect(lessonComment.status, lessonComment.id) + "</td>" +
            "<td class='align-middle'>" + prepareDeleteButton(lessonComment.id) + "</td>" +
        "</tr>"
    );
}

function prepareStatusSelect(status, lessonCommentId) {
    var select = '';
    select += '<select id="status-select-' + lessonCommentId + '" class="form-control" onchange="changeStatus(' + lessonCommentId + ')">';
    select += prepareStatusOptions(status);
    select += '</select>';
    return select;
}

function prepareStatusOptions(status) {
    var options;

    lessonCommentStatusesDict.forEach(function(statusFromDict){

        var select = '';

        if(statusFromDict.code == status) {
            select = 'selected';
        }

        options += '<option ' + select + ' value="' + statusFromDict.code + '">' + statusFromDict.value + '</option>';

    });

    return options;
}

function changeStatus(lessonCommentId) {
    $.ajax({
        url: "/admin/api/crs/lesson-comment/" + lessonCommentId,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify({
            status : $("#status-select-" + lessonCommentId).find(":selected").val()
        })
    })
        .done(function () {
            $("#operation-successful-modal").modal('show');
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
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
        url: "/admin/api/crs/lesson-comment/" + objToDeleteId,
        type: "DELETE"
    })
        .done(function(response) {
            $('#delete-object-modal').modal('hide');
            $('#operation-successful-modal').modal('show');
            findLessonComments();
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}
