
function findCourseAttachments() {
    $.ajax({
        url: "/api/crs/course/" + courseId + "/attachments",
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (response) {
        $("#course-attachments-records").empty();
        fillCourseAttachmentsResults(response.courseAttachments);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillCourseAttachmentsResults(courseAttachments) {

    if(courseAttachments.length == 0) {
        $("#attachments").hide();
    } else {
        $("#attachments").show();
    }

    courseAttachments.forEach(function(courseAttachment){
        $('#course-attachments-records').append(
            "<tr>" +
                "<td class='align-middle'>" + courseAttachment.name + "</td>" +
                "<td class='align-middle'>" + prepareCourseAttachmentDownloadButton(courseAttachment) + "</td>" +
            "</tr>"
        );
    });
}

function prepareCourseAttachmentDownloadButton(courseAttachment) {
    return '<a type="button" onclick="trace(' + "'file.downloaded', 'course-attachment&id=" + courseAttachment.id + "'" + ')" class="btn btn-success btn-sm" href="/download/course/attachment/' + courseAttachment.id + '/" download="' + courseAttachment.originalFileName + '">' + lang.watch_download + '</a>';
}