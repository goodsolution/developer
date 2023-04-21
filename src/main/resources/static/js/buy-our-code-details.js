
var lessonToDeleteId;
var courseAttachmentToDeleteId;

$(document).ready(function () {

    $('#create-lesson-modal').on('hide.bs.modal', function (e) {
        clearCreateModal();
    });

    $('#create-course-attachment-modal').on('hide.bs.modal', function (e) {
        $("#create-course-attachment-name").val('');
        $("#create-course-attachment-file").val('');
    });

    findLessons();
    findCourseAttachments();

    $("#upload-course-attachment").submit(function(event) {
        $.ajax({
            type: 'POST',
            enctype: 'multipart/form-data',
            url: '/admin/api/crs/course/attachment' + prepareUploadCourseAttachmentUrl(),
            data: new FormData(this),
            contentType: false,
            cache: false,
            processData:false,
            success: function(response){
                $("#create-course-attachment-modal").modal('hide');
                $('#operation-successful-modal').modal('show');
                findCourseAttachments();
            },
            error: function (jqxhr, textStatus, errorThrown) {
                displayErrorInformation(jqxhr.responseText);
            }
        });
        event.preventDefault();
    });

});

function prepareUploadCourseAttachmentUrl() {
    var url = "?";

    url+= "&name=" + $("#create-course-attachment-name").val();
    url+= "&courseId=" + course.id;

    return url;
}

function showDeleteModal() {
    $('#delete-object-modal').modal('show');
}

function sendDeleteRequest(){
    $.ajax({
        url: "/admin/api/crs/course/" + course.id,
        type: "DELETE"
    })
        .done(function(response) {
            $('#delete-object-modal').modal('hide');
            window.location.href = '/admin/courses';
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

function sendUpdateRequest() {
    $.ajax({
        url: "/admin/api/crs/course/" + course.id,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify({
            title: $("#title").val(),
            price: $("#price").val(),
            language: $('#language').find(":selected").val(),
            description: $("#description").val(),
            authorId: $('#author').find(":selected").val(),
            visibilityStatus : $("#visibility-status").find(":selected").val(),
            code: $("#code").val(),
            saleStatus: $("#sale-status").find(":selected").val()
        })
    })
        .done(function () {
            $("#operation-successful-modal").modal('show');
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}


function findCourseAttachments() {
    $.ajax({
        url: "/admin/api/crs/course/" + course.id + "/attachments",
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (response) {
        $("#course-attachments-records").empty();
        fillCourseAttachmentsTable(response.courseAttachments);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillCourseAttachmentsTable(courseAttachments) {
    courseAttachments.forEach(function(courseAttachment){
        $('#course-attachments-records').append(
            "<tr>" +
                "<td class='align-middle'>" + courseAttachment.id + "</td>" +
                "<td class='align-middle'>" + courseAttachment.name + "</td>" +
                "<td class='align-middle'>" + prepareCourseAttachmentDownloadButton(courseAttachment) + "</td>" +
                "<td class='align-middle'>" + prepareCourseAttachmentDeleteButton(courseAttachment.id) + "</td>" +
            "</tr>"
        );
    });
}

function prepareCourseAttachmentDeleteButton(id) {
    return '<button type="button" class="btn btn-danger" onclick="setCourseAttachmentToDeleteIdAndShowModal(' + id + ')">Delete</button>';
}

function prepareCourseAttachmentDownloadButton(courseAttachment) {
    return '<a type="button" class="btn btn-primary btn-sm" href="/download/course/attachment/' + courseAttachment.id + '/" download="' + courseAttachment.originalFileName + '">Download</a>';
}

function findLessons() {
    $.ajax({
        url: "/admin/api/crs/lessons/" + course.id,
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (response) {
        fillLessons(response);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillLessons(response) {
    $("#records").empty();

    var lessonsWithoutModule = response.lessonsWithoutModule;

    lessonsWithoutModule.forEach(function(lessonWithoutModule){
        fillLesson(lessonWithoutModule);
    });
}


function fillLesson(lesson) {
    $('#records').append(
        "<tr>" +
            "<td class='align-middle'>" +
                "<div class='d-flex justify-content-around'>" +
                    "<img src='/img/downArrow.png' width='20px' height='20px' onclick='moveLessonDown(" + lesson.id + ")'>" +
                    "<img src='/img/upArrow.png' width='20px' height='20px' onclick='moveLessonUp(" + lesson.id + ")'>" +
                "</div>" +
            "</td>" +
            "<td class='align-middle'>" + lesson.title + "</td>" +
            "<td class='align-middle'>" + getDictionaryValue(lesson.visibilityStatus, lessonVisibilityStatusesDict) + "</td>" +
            "<td class='align-middle'>" + prepareDetailsButton(lesson.id) + "</td>" +
            "<td class='align-middle'>" + prepareDeleteButton(lesson.id) + "</td>" +
        "</tr>"
    );
}

function prepareDetailsButton(id) {
    return '<button type="button" class="btn btn-primary" onclick="goToDetailsPage(' + id + ')">Details</button>';
}

function goToDetailsPage(id) {
    window.location.href = "/admin/lesson/" + id;
}

function prepareDeleteButton(id) {
    return '<button type="button" class="btn btn-danger" onclick="setLessonToDeleteIdAndShowModal(' + id + ')">Delete</button>';
}

function setLessonToDeleteIdAndShowModal(id) {
    lessonToDeleteId = id;
    $('#delete-lesson-modal').modal('show');
}

function setCourseAttachmentToDeleteIdAndShowModal(id) {
    courseAttachmentToDeleteId = id;
    $('#delete-course-attachment-modal').modal('show');
}

function sendLessonDeleteRequest() {
        $.ajax({
            url: "/admin/api/crs/lesson/" + lessonToDeleteId,
            type: "DELETE"
        })
            .done(function(response) {
                $('#delete-lesson-modal').modal('hide');
                $("#operation-successful-modal").modal('show');
                findLessons();
            })
            .fail(function(jqxhr, textStatus, errorThrown){
                displayErrorInformation(jqxhr.responseText);
            });
}

function sendCourseAttachmentDeleteRequest() {
        $.ajax({
            url: "/admin/api/crs/course/attachment/" + courseAttachmentToDeleteId,
            type: "DELETE"
        })
            .done(function(response) {
                $('#delete-course-attachment-modal').modal('hide');
                $("#operation-successful-modal").modal('show');
                findCourseAttachments();
            })
            .fail(function(jqxhr, textStatus, errorThrown){
                displayErrorInformation(jqxhr.responseText);
            });
}

function clearCreateModal() {
    $("#create-title").val('');
    $("#create-movie-link").val('');
    $("#create-description").val('');
}

function sendCreateLessonRequest() {
    $.ajax({
        url: "/admin/api/crs/lesson",
        method: "post",
        contentType: "application/json",
        data: JSON.stringify({
            courseId: course.id,
            title: $("#create-title").val(),
            movieLink: $("#create-movie-link").val(),
            description: $("#create-description").val(),
            movieLinkType: $("#create-movie-link-type").val()
        })
    })
        .done(function () {
            $("#create-lesson-modal").modal('hide');
            $("#operation-successful-modal").modal('show');
            findLessons();
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}

function moveLessonUp(id) {
    sendLessonOrderChangeRequest(id, 'UP');
}

function moveLessonDown(id) {
    sendLessonOrderChangeRequest(id, 'DOWN');
}

function sendLessonOrderChangeRequest(id, direction) {
    $.ajax({
        url: "/admin/api/crs/lesson/change-order/" + id,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify({
            direction: direction
        })
    })
        .done(function () {
            findLessons();
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}


