
$(document).ready(function () {
    findLessons();
    findCourseAttachments();
    findLessonComments();
    getLesson();
});

function getLesson(id) {
    return $.ajax({
        url: "/api/crs/lesson/" + lessonId,
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (response) {
        fillLessonContent(response.lesson);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillLessonContent(lesson) {
    fillTitle(lesson.title);
    fillVideo(lesson.movieLink);
    fillDescription(lesson.description);
    createWatchedButton(lesson.watched);
}

function fillTitle(title) {
    $("#title").text(title);
}

function fillVideo(movieLink) {

    var finalMovieLink = movieLink;

    if(finalMovieLink == '') {
        $("#video-place").hide();
    } else {
        $("#video-place").show();
        finalMovieLink += '?rel=0';
    }

    $("#video").attr("src", finalMovieLink);

}

function fillDescription(description) {
    $("#description").html(description);
}

function hideLessonCommentSuccessAddedAlert() {
    $("#lesson-comment-added-success-alert").hide();
}

function sendLessonComment() {
    $.ajax({
        url: "/api/crs/lesson-comment",
        method: "post",
        contentType: "application/json",
        data: JSON.stringify({
            text: $("#lesson-comment").val(),
            lessonId: lessonId,
        })
    })
    .done(function () {
        $("#lesson-comment").val('');
        $("#lesson-comment-added-success-alert").show();
        findLessonComments();
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    })
}

function findLessonComments() {
    $.ajax({
        url: "/api/crs/lesson-comments/" + lessonId,
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (response) {
        $("#lesson-comments").empty();
        fillLessonComments(response.lessonComments);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillLessonComments(lessonComments) {
    lessonComments.forEach(function(lessonComment){
        fillLessonComment(lessonComment);
    });

    if(lessonComments.length == 0) {
        $('#lesson-comments').append('<div class="text-center">' + lang.watch_lesson_comments_lack + '</div>');
    }
}

function fillLessonComment(lessonComment) {
    $('#lesson-comments').append(
        '<div class="mt-3">' +
            '<div class="row mb-3">' +
                '<div class="col-lg-2"></div>' +
                '<div class="border col-lg-8">' +
                    '<div class="p-3"></div>' +
                    '<div class="my-3 text-center">' + lessonComment.text + '</div>' +
                    '<div class="d-flex justify-content-end p-3"><small class="form-text text-muted">' + prepareDateTime(lessonComment.createDatetime) + '</small></div>' +
                '</div>' +
                '<div class="col-lg-2"></div>' +
            '</div>' +
        '</div>'
    );
}

function changeLessonWatchedStatus(watched) {
    $.ajax({
        url: "/api/crs/lesson/" + lessonId + "/change-watched-status",
        type: "PUT",
        contentType: "application/json"
    })
    .done(function () {
        createWatchedButton(watched);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function createWatchedButton(watched) {

    $("#watched-button-field").empty();

    if(watched == true) {
        $("#watched-button-field").append('<button class="btn btn-sm btn-secondary" onclick="changeLessonWatchedStatus(false)">' + lang.watch_markAsUnwatched + '</button>');
    } else {
        $("#watched-button-field").append('<button class="btn btn-sm btn-success" onclick="changeLessonWatchedStatus(true)">' + lang.watch_markAsWatched + '</button>');
    }

}

function showCourseOpinionForm() {
    $("#course-opinion-form").show();
    $("#show-course-opinion-form-button").hide();
}

function sendCourseComment() {
    $.ajax({
        url: "/api/crs/course-comment",
        method: "post",
        contentType: "application/json",
        data: JSON.stringify({
            text: $("#course-comment").val(),
            courseId: courseId,
        })
    })
    .done(function () {
        $("#course-opinion-form").hide();
        $("#course-comment").val('');
        $("#course-comment-added-success-alert").show();
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    })
}
