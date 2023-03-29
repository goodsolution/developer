
var lessonToDeleteId;
var courseAttachmentToDeleteId;
var moduleToDeleteId;

var modules;

$(document).ready(function () {

    $('#create-lesson-modal').on('hide.bs.modal', function (e) {
        clearCreateModal();
    });

    $('#create-course-attachment-modal').on('hide.bs.modal', function (e) {
        $("#create-course-attachment-name").val('');
        $("#create-course-attachment-file").val('');
    });

    $('#create-module-modal').on('hide.bs.modal', function (e) {
        $("#create-module-name").val('');
    });

    findCourseAttachments();
    findModules();

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
    return '<button type="button" class="btn btn-danger" onclick="setCourseAttachmentToDeleteIdAndShowModal(' + id + ')">' + lang.Delete + '</button>';
}

function prepareCourseAttachmentDownloadButton(courseAttachment) {
    return '<a type="button" class="btn btn-primary btn-sm" href="/download/course/attachment/' + courseAttachment.id + '/" download="' + courseAttachment.originalFileName + '">' + lang.Download + '</a>';
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

    var modules = response.modules;

    modules.forEach(function(module) {
        var lessons = module.lessons;

        lessons.forEach(function(lesson) {
            fillLesson(lesson);
        });
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
            "<td class='align-middle'>" + prepareModulesSelect(lesson) + "</td>" +
            "<td class='align-middle'>" + prepareDetailsButton(lesson.id) + "</td>" +
            "<td class='align-middle'>" + prepareDeleteButton(lesson.id) + "</td>" +
        "</tr>"
    );
}

function prepareModulesSelect(lesson) {

    var html = '';

    html +=  '<select id="lesson-module-select-' + lesson.id + '" class="form-control" onchange="changeLessonModule(' + lesson.id + ')">';
    html += '<option value="">---</option>';

    modules.forEach(function(module){
        var selected = '';

        if(module.id == lesson.moduleId) {
            selected = 'selected';
        }

        html += '<option ' + selected + ' value="' + module.id + '">' + module.name + '</option>';
    });

    html +=  '</select>';

    return html;
}

function changeLessonModule(lessonId) {

    var moduleId = $("#lesson-module-select-" + lessonId).val();

    $.ajax({
        url: "/admin/api/crs/lesson/" + lessonId + "/change-module?&moduleId=" + moduleId,
        method: "PUT",
        contentType: "application/json",
    })
        .done(function () {
            $("#operation-successful-modal").modal('show');
            findLessons();
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}

function prepareDetailsButton(id) {
    return '<button type="button" class="btn btn-primary" onclick="goToDetailsPage(' + id + ')">' + lang.Details + '</button>';
}

function goToDetailsPage(id) {
    window.location.href = "/admin/lesson/" + id;
}

function prepareDeleteButton(id) {
    return '<button type="button" class="btn btn-danger" onclick="setLessonToDeleteIdAndShowModal(' + id + ')">' + lang.Delete + '</button>';
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
    $("#create-module").val('');
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
            moduleId: $("#create-module").val(),
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


function sendCreateModuleRequest() {
    $.ajax({
        url: "/admin/api/crs/module",
        method: "post",
        contentType: "application/json",
        data: JSON.stringify({
            courseId: course.id,
            name: $("#create-module-name").val()
        })
    })
        .done(function () {
            $("#create-module-modal").modal('hide');
            $("#operation-successful-modal").modal('show');
            findModules();
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}

function findModules() {
    $.ajax({
        url: "/admin/api/crs/modules?&courseId=" + course.id,
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (response) {
        $("#modules-records").empty();
        fillModules(response.modules);
        modules = response.modules;
        fillModulesInCreateLessonModal();
        findLessons();
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillModulesInCreateLessonModal() {

    $("#create-module").empty();

    var html = '';

    html += '<option value="">---</option>';

    modules.forEach(function(module){
        html += '<option value="' + module.id  + '">' + module.name + '</option>';
    });

    $("#create-module").html(html);
}

function fillModules(modules) {

    modules.forEach(function(module){
        $('#modules-records').append(
            "<tr>" +
                "<td class='align-middle'>" +
                    "<div class='d-flex justify-content-around'>" +
                        "<img src='/img/downArrow.png' width='20px' height='20px' onclick='moveModuleDown(" + module.id + ")'>" +
                        "<img src='/img/upArrow.png' width='20px' height='20px' onclick='moveModuleUp(" + module.id + ")'>" +
                    "</div>" +
                "</td>" +
                "<td class='align-middle'>" + module.name + "</td>" +
                "<td class='align-middle'>" + getDictionaryValue(module.visibilityStatus, moduleVisibilityStatusesDict) + "</td>" +
                "<td class='align-middle'>" + prepareModuleDetailsButton(module.id) + "</td>" +
                "<td class='align-middle'>" + prepareModuleDeleteButton(module.id) + "</td>" +
            "</tr>"
        );
    });
}

function moveModuleUp(id) {
    moveModule(id, "UP");
}

function moveModuleDown(id) {
    moveModule(id, "DOWN");
}

function moveModule(id, direction) {
    $.ajax({
        url: "/admin/api/crs/module/" + id + "/change-order?&direction=" + direction,
        method: "PUT",
        contentType: "application/json",
    })
        .done(function () {
            findModules();
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}

function prepareModuleDetailsButton(moduleId) {
    return '<button type="button" class="btn btn-primary" onclick="goToModuleDetails(' + moduleId + ')">' + lang.Details + '</button>';
}

function prepareModuleDeleteButton(moduleId) {
    return '<button type="button" class="btn btn-danger" onclick="setModuleToDeleteIdAndShowModal(' + moduleId + ')">' + lang.Delete + '</button>';
}

function goToModuleDetails(id) {
    window.location.href = "/admin/module/" + id;
}

function setModuleToDeleteIdAndShowModal(id) {
    moduleToDeleteId = id;
    $("#delete-module-modal").modal('show');
}

function sendModuleDeleteRequest() {
    $.ajax({
        url: "/admin/api/crs/module/" + moduleToDeleteId,
        type: "DELETE"
    })
        .done(function(response) {
            $('#delete-module-modal').modal('hide');
            $("#operation-successful-modal").modal('show');
            findModules();
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}
