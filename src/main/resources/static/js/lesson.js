
var lessonAttachmentToDeleteId;

$(document).ready(function () {

    if(lesson.course.type == 'c') {
        $('#type').text(lang.Course);
        findLessonAttachments();
    } else if(lesson.course.type == 'b') {
        $('#type').text(lang.Code);
    } else if(lesson.course.type == 's') {
        $('#type').text(lang.Selection);
    } else {
        $('#type').text(lang.Product);
    }

    $("#upload-lesson-attachment").submit(function(event) {
            $.ajax({
                type: 'POST',
                enctype: 'multipart/form-data',
                url: '/admin/api/crs/lesson/attachment' + prepareUploadLessonAttachmentUrl(),
                data: new FormData(this),
                contentType: false,
                cache: false,
                processData:false,
                success: function(response){
                    $("#create-lesson-attachment-modal").modal('hide');
                    $('#operation-successful-modal').modal('show');
                    findLessonAttachments();
                },
                error: function (jqxhr, textStatus, errorThrown) {
                    displayErrorInformation(jqxhr.responseText);
                }
            });
            event.preventDefault();
        });

    $('#create-lesson-attachment-modal').on('hide.bs.modal', function (e) {
        $("#create-lesson-attachment-name").val('');
        $("#create-lesson-attachment-file").val('');
    });

});

function prepareUploadLessonAttachmentUrl() {
    var url = "?";

    url+= "&name=" + $("#create-lesson-attachment-name").val();
    url+= "&lessonId=" + lesson.id;

    return url;
}

function findLessonAttachments() {
    $.ajax({
        url: "/admin/api/crs/lesson/" + lesson.id + "/attachments",
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (response) {
        $("#lesson-attachments-records").empty();
        fillLessonAttachments(response.lessonAttachments);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillLessonAttachments(lessonAttachments) {

    lessonAttachments.forEach(function(lessonAttachment){
        $('#lesson-attachments-records').append(
            "<tr>" +
                "<td class='align-middle'>" + lessonAttachment.id + "</td>" +
                "<td class='align-middle'>" + lessonAttachment.name + "</td>" +
                "<td class='align-middle'>" + prepareLessonAttachmentDownloadButton(lessonAttachment) + "</td>" +
                "<td class='align-middle'>" + prepareLessonAttachmentDeleteButton(lessonAttachment.id) + "</td>" +
            "</tr>"
        );
    });
}

function prepareLessonAttachmentDeleteButton(id) {
    return '<button type="button" class="btn btn-danger" onclick="setLessonAttachmentToDeleteIdAndShowModal(' + id + ')">' + lang.Delete + '</button>';
}

function prepareLessonAttachmentDownloadButton(lessonAttachment) {
    return '<a type="button" class="btn btn-primary btn-sm" href="/download/lesson/attachment/' + lessonAttachment.id + '/" download="' + lessonAttachment.file.originalName + '">' + lang.Download + '</a>';
}

function setLessonAttachmentToDeleteIdAndShowModal(id) {
    lessonAttachmentToDeleteId = id;
    $('#delete-lesson-attachment-modal').modal('show');
}

function sendLessonAttachmentDeleteRequest() {
    $.ajax({
        url: "/admin/api/crs/lesson/attachment/" + lessonAttachmentToDeleteId,
        type: "DELETE"
    })
        .done(function(response) {
            $('#delete-lesson-attachment-modal').modal('hide');
            $("#operation-successful-modal").modal('show');
            findLessonAttachments();
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

function sendDeleteRequest(){
    $.ajax({
        url: "/admin/api/crs/lesson/" + lesson.id,
        type: "DELETE"
    })
        .done(function(response) {
            $('#delete-object-modal').modal('hide');
            window.location.href = '/admin/course/' + lesson.course.id;
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

function showDeleteModal() {
    $('#delete-object-modal').modal('show');
}

function sendUpdateRequest() {
    $.ajax({
        url: "/admin/api/crs/lesson/" + lesson.id,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify({
            title: $("#title").val(),
            movieLink: $("#movie-link").val(),
            description: $("#description").val(),
            visibilityStatus: $("#visibility-status").find(":selected").val(),
            moduleId: $("#module").val(),
            type: $("#lesson-type").find(":selected").val(),
            taskSolutionDescription: $("#task-solution-description").val(),
            taskSolutionMovieLink: $("#task-solution-movie-link").val(),
            movieLinkType: $("#movie-link-type").val(),
            taskSolutionMovieLinkType: $("#task-solution-movie-link-type").val()
        })
    })
        .done(function () {
            $("#operation-successful-modal").modal('show');
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}
