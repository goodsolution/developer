var isFirstLessonChanged = false;
var sidebar;
var lessonsMenuDivider;

$(document).ready(function () {
    $("#menu-toggle").click(function(e) {
      e.preventDefault();
      $("#wrapper").toggleClass("toggled");
    });

    $("#wrapper").on("classChanged", function () {
        alert("Button Style Change event fired");
    });

    sidebar = document.getElementById('sidebar-list-tab');
    lessonsMenuDivider = document.getElementById('lessons-menu-divider');

    fillBackToProductsButton();
    fillLessonsContent();
    fillLessonsMenu();
    findCourseAttachments();

    $('.lessons-menu-element').on('shown.bs.tab', function (e) {
        var element = e.relatedTarget;
        var lessonId = element.getAttribute('lesson-id');
        stopVideo(lessonId);
        stopTaskSolutionVideo(lessonId);
    })

    $('.lesson-menu-element').on('shown.bs.tab', function (e) {
        var element = e.relatedTarget;
        var lessonId = element.getAttribute('lesson-id');
        stopTaskSolutionVideo(lessonId);
    })

});

function fillLessonsMenu() {

    fillLessonsInSidebar(lessonsWithoutModules);
    fillModulesInSidebar(modules);
}

function fillLessonsInSidebar(lessons) {
    lessons.forEach(function(lesson){
        var element = document.createElement('a');

        if(isFirstLessonChanged === false) {
            isFirstLessonChanged = true;
            element.setAttribute('class', 'list-group-item list-group-item-action text-center lessons-menu-element active');
            $('#list-lesson-' + lesson.id).addClass('active show');
            findCommentsAndAttachments(lesson.id);
        } else {
            element.setAttribute('class', 'list-group-item list-group-item-action text-center lessons-menu-element');
        }

        element.setAttribute('id', 'lessons-menu-element-lesson-' +  lesson.id);
        element.setAttribute('lesson-type', lesson.type);
        element.setAttribute('lesson-id', lesson.id);
        element.setAttribute('data-toggle', 'list');
        element.setAttribute('role', 'tab');
        element.setAttribute('href', '#list-lesson-' + lesson.id);
        element.setAttribute('onclick', 'findCommentsAndAttachments(' + lesson.id + ');');
        element.innerHTML = lesson.title;
        sidebar.insertBefore(element, lessonsMenuDivider);

        if(lesson.watched === true) {
            markLessonWatched(lesson.id);
        } else {
            markLessonUnwatched(lesson.id);
        }
    });
}

function fillModulesInSidebar(modules) {
    modules.forEach(function(module) {
        var element = document.createElement('div');
        element.setAttribute('class', 'font-weight-bold text-center mt-3');
        element.innerHTML = module.name;
        sidebar.insertBefore(element, lessonsMenuDivider);
        fillLessonsInSidebar(module.lessons);
    });
}



function fillLessonsContent() {
    var content = '';
    content += '<div class="tab-content" id="nav-tabContent">';
    content += prepareLessonsTabContent(lessonsWithoutModules);

    modules.forEach(function(module) {
        content += prepareLessonsTabContent(module.lessons);
    });

    content += '</div>';

    $("#content-container").append(content);

}

function prepareLessonsTabContent(lessons) {
    var code = '';

    lessons.forEach(function(lesson) {
        code += '<div class="tab-pane fade" id="list-lesson-' + lesson.id + '">';
        code +=     '<div class="p-3 text-center">';
        code +=         '<h3>' + lesson.title + '</h3>';
        code +=     '</div>';
        code += prepareMovieDiv(lesson);
        code +=     '<div class="row mt-3">';
        code +=         '<div class="col-lg-2"></div>';
        code +=         '<div class="col-lg-8">';
        code +=             '<div class="d-flex justify-content-end">';
        code +=                 '<button id="watched-status-button-lesson-' + lesson.id + '"></button>';
        code +=             '</div>';
        code +=         '</div>';
        code +=         '<div class="col-lg-2"></div>';
        code +=     '</div>';
        code += prepareLessonTabList(lesson);
        code += '</div>';
    });

    return code;
}

function stopVideo(lessonId) {

    var video = $('#youtube-video-lesson-' + lessonId)[0];

    if(video !== undefined) {
        video.contentWindow.postMessage('{"event":"command","func":"' + 'stopVideo' + '","args":""}', '*');
    }

    var vimeoVideo = $("#vimeo-video-lesson-" + lessonId)[0];

    if(vimeoVideo !== undefined) {
        vimeoVideo.contentWindow.postMessage('{"method":"unload"}','*');
    }
}

function stopTaskSolutionVideo(lessonId) {

    var video = $('#task-solution-youtube-video-lesson-' + lessonId)[0];

    if(video !== undefined) {
        video.contentWindow.postMessage('{"event":"command","func":"' + 'stopVideo' + '","args":""}', '*');
    }
    var vimeoVideo = $("#task-solution-vimeo-video-lesson-" + lessonId)[0];

    if(vimeoVideo !== undefined) {
        vimeoVideo.contentWindow.postMessage('{"method":"unload"}','*');
    }

}

function prepareMovieDiv(lesson) {
    var code = '';

    if(lesson.movieLink !== '' && lesson.movieLinkType !== 'n') {
        code += '<div class="row">';
        code +=     '<div class="col-lg-2"></div>';
        code +=     '<div class="col-lg-8">';
        code +=         '<div class="embed-responsive embed-responsive-16by9">';
        if(lesson.movieLinkType === 'y') {
            code += '<iframe id="youtube-video-lesson-' + lesson.id + '" class="embed-responsive-item" src="' + lesson.movieLink + '?rel=0&enablejsapi=1&version=3&playerapiid=ytplayer" allowfullscreen></iframe>';

        } else if(lesson.movieLinkType === 'v') {
            code += '<iframe id="vimeo-video-lesson-' + lesson.id + '" class="embed-responsive-item" src="' + lesson.movieLink + '" allowfullscreen></iframe>';
        }
        code +=         '</div>';
        code +=     '</div>';
        code +=     '<div class="col-lg-2"></div>';
        code += '</div>';
    }

    return code;
}

function prepareLessonTabList(lesson) {
    var code = '';
    code += '<ul class="nav nav-tabs mt-5" id="tab-lesson-' + lesson.id + '" role="tablist">';
    code +=     '<li class="nav-item">';
    code +=         '<a lesson-id="' + lesson.id +'" class="lesson-menu-element nav-link active" id="description-tab-lesson-' + lesson.id + '" data-toggle="tab" href="#description-lesson-' + lesson.id + '" role="tab">' + lang.watch_description + '</a>';
    code +=     '</li>';
    code +=     '<li class="nav-item">';
    code +=         '<a lesson-id="' + lesson.id +'" class="lesson-menu-element nav-link" id="comments-tab-lesson-' + lesson.id + '" data-toggle="tab" href="#comments-lesson-' + lesson.id + '" role="tab">' + lang.watch_comments + '</a>';
    code +=     '</li>';
    code +=     '<li class="nav-item">';
    code +=         '<a lesson-id="' + lesson.id +'" class="lesson-menu-element nav-link" style="display:none;" id="attachments-tab-lesson-' + lesson.id + '" data-toggle="tab" href="#attachments-lesson-' + lesson.id + '" role="tab">' + lang.watch_lessonAttachments + '</a>';
    code +=     '</li>';

    if(lesson.type === 't') {
        code +=     '<li class="nav-item">';
        code +=         '<a lesson-id="' + lesson.id +'" class="lesson-menu-element nav-link" id="task-solution-tab-lesson-' + lesson.id + '" data-toggle="tab" href="#task-solution-lesson-' + lesson.id + '" role="tab">' + lang.watch_taskSolution + '</a>';
        code +=     '</li>';
    }

    code += '</ul>';
    code += '<div class="tab-content mb-5">';
    code +=     '<div class="tab-pane fade show active mt-3 mb-5 px-3" id="description-lesson-' + lesson.id +'" role="tabpanel">' + prepareLessonDescriptionSection(lesson.description) + '</div>';
    code +=     '<div class="tab-pane fade text-center mt-3 mb-5" id="comments-lesson-' + lesson.id +'" role="tabpanel">' + prepareCommentsSection(lesson.id) + '</div>';
    code +=     '<div class="tab-pane fade text-center mt-3 mb-5" id="attachments-lesson-' + lesson.id +'" role="tabpanel">' + prepareAttachmentsSection(lesson.id) + '</div>';

    if(lesson.type === 't') {
        code +=     '<div class="tab-pane fade mt-3 mb-5" id="task-solution-lesson-' + lesson.id +'" role="tabpanel">' + prepareSolutionSection(lesson) + '</div>';
    }
    code += '</div>';

    return code;
}

function fillBackToProductsButton() {

    var backButton = $("#back-to-products-button");

    if(course.type === 'c') {
        backButton.html('<i class="fas fa-undo-alt"></i> ' + lang.watch_backToCourses);
        backButton.attr('href', '/my-courses');
    } else if (course.type === 's') {
        backButton.html('<i class="fas fa-undo-alt"></i> ' + lang.watch_backToSelections);
        backButton.attr('href', '/my-selections');
    } else if (course.type === 'b') {
        backButton.html('<i class="fas fa-undo-alt"></i> ' + lang.watch_backToCodes);
        backButton.attr('href', '/my-codes');
    }
}

function prepareLessonDescriptionSection(lessonDescription) {
    if(lessonDescription === '') {
        return "<div class=text-center><b>" + lang.watch_noDescription + "</b></div>";
    } else {
        return lessonDescription;
    }

}

function prepareCommentsSection(lessonId) {
    var code = '';

    code +='<div class="mx-5 mb-5">';
    code +=    '<form class="text-center">';
    code +=        '<label for="comment-lesson-' + lessonId + '">' + lang.watch_addCommentToLesson + '</label>';
    code +=        '<textarea id="comment-lesson-' + lessonId + '" type="text" class="form-control" rows="3"></textarea>';
    code +=        '<button type="button" class="btn btn-outline-success mt-3" onclick="sendLessonComment(' + lessonId + ')"><i class="far fa-comment"></i> ' + lang.watch_addLessonComment + '</button>';
    code +=    '</form>';
    code +=    '<div id="comment-success-added-alert-lesson-' + lessonId + '" class="alert alert-success mt-3" role="alert" style="display:none;">';
    code +=        '<span>' + lang.watch_lessonCommentAddedSuccess + '</span>';
    code +=        '<button type="button" class="close" onclick="hideLessonCommentAddedSuccessAlert(' + lessonId + ')">';
    code +=            '<span>&times;</span>';
    code +=        '</button>';
    code +=    '</div>';
    code +='</div>';
    code +='<h4 class="text-center mt-5">' + lang.watch_lessonComments + '</h4>';
    code +='<div id="loaded-comments-lesson-' + lessonId + '" class="text-center mb-5"></div>';


    return code;
}

function prepareAttachmentsSection(lessonId) {
    var code = '';

    code =  '<div class="mt-3 mb-5 px-5 pb-5">'+
                '<div class="table-responsive mb-0 pb-0">'+
                    '<table class="table bg-light table-bordered table-hover table-sm mb-0 pb-0">'+
                        '<thead class="thead-light">'+
                        '<tr>'+
                            '<th class="align-middle">' + lang.watch_name + '</th>'+
                            '<th class="align-middle">' + lang.watch_download + '</th>'+
                        '</tr>'+
                        '</thead>'+
                        '<tbody id="records-attachments-lesson-' + lessonId + '">'+

                        '</tbody>'+
                    '</table>'+
                '</div>'+
            '</div>';

    return code;
}

function prepareSolutionSection(lesson) {
    var code = '';

    code += '<div class="mt-3">';
    code +=     '<div id="solution-lesson-' + lesson.id + '">';

    if(lesson.taskSolutionMovieLink !== '' && lesson.taskSolutionMovieLink !== undefined && lesson.taskSolutionMovieLinkType !== 'n') {
        code +=         '<div class="row mt-3">';
        code +=             '<div class="col-lg-2"></div>';
        code +=             '<div class="col-lg-8">';
        code +=                 '<div class="embed-responsive embed-responsive-16by9">';
        if(lesson.taskSolutionMovieLinkType === 'y') {
            code += '<iframe id="task-solution-youtube-video-lesson-' + lesson.id + '" class="embed-responsive-item" src="' + lesson.taskSolutionMovieLink + '?rel=0&enablejsapi=1&version=3&playerapiid=ytplayer" allowfullscreen></iframe>';
        } else if (lesson.taskSolutionMovieLinkType === 'v') {
            code += '<iframe id="task-solution-vimeo-video-lesson-' + lesson.id + '" class="embed-responsive-item" src="' + lesson.taskSolutionMovieLink + '" allowfullscreen></iframe>';
        }
        code +=                 '</div>';
        code +=             '</div>';
        code +=             '<div class="col-lg-2"></div>';
        code +=         '</div>';
    }
    code +=         '<div class="mt-3">';
    code +=             lesson.taskSolutionDescription;
    code +=         '</div>';
    code +=     '</div>';
    code += '</div>';

    return code;
}

function sendLessonComment(lessonId) {
    $.ajax({
        url: "/api/crs/lesson-comment",
        method: "post",
        contentType: "application/json",
        data: JSON.stringify({
            text: $("#comment-lesson-" + lessonId).val(),
            lessonId: lessonId,
        })
    })
    .done(function () {
        $("#comment-lesson-" + lessonId).val('');
        $("#comment-success-added-alert-lesson-" + lessonId).show();
        findLessonComments(lessonId);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    })
}

function hideLessonCommentAddedSuccessAlert(lessonId) {
    $("#comment-success-added-alert-lesson-" + lessonId).hide();
}

function findLessonComments(lessonId) {
    $.ajax({
        url: "/api/crs/lesson-comments/" + lessonId,
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (response) {
        $("#loaded-comments-lesson-" + lessonId).empty();
        fillLessonComments(response.lessonComments, lessonId);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillLessonComments(lessonComments, lessonId) {

    if(lessonComments.length === 0) {
        $('#loaded-comments-lesson-'+ lessonId).append('<div class="text-center mb-5">' + lang.watch_lesson_comments_lack + '</div>');
    } else {
        lessonComments.forEach(function(lessonComment){
            fillLessonComment(lessonComment, lessonId);
        });
    }
}

function fillLessonComment(lessonComment, lessonId) {
    $('#loaded-comments-lesson-' + lessonId).append(
        '<div class="mt-3">' +
            '<div class="row mb-5">' +
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

function sendCourseComment() {
    $.ajax({
        url: "/api/crs/course-comment",
        method: "post",
        contentType: "application/json",
        data: JSON.stringify({
            text: $("#course-comment").val(),
            courseId: course.id,
        })
    })
    .done(function () {
        $('#product-opinion-modal').modal('hide');
        $('#product-opinion-added-success-modal').modal('show');
        $("#course-comment").val('');
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    })
}

function findCommentsAndAttachments(lessonId) {
    findLessonComments(lessonId);
    findLessonAttachments(lessonId);
}

function findLessonAttachments(lessonId) {
    $.ajax({
        url: "/api/crs/lesson/" + lessonId + "/attachments",
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (response) {
        fillLessonAttachments(lessonId, response.lessonAttachments);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillLessonAttachments(lessonId, lessonAttachments) {

    $('#records-attachments-lesson-' + lessonId).empty();

    lessonAttachments.forEach(function(lessonAttachment){
        $('#records-attachments-lesson-' + lessonId).append(
            "<tr>" +
                "<td class='align-middle'>" + lessonAttachment.name + "</td>" +
                "<td class='align-middle'>" + prepareLessonAttachmentDownloadButton(lessonAttachment) + "</td>" +
            "</tr>"
        );
    });

    if(lessonAttachments.length > 0) {
        $("#attachments-tab-lesson-" + lessonId).show();
    }

}

function prepareLessonAttachmentDownloadButton(lessonAttachment) {
    return '<a type="button" onclick="trace(' + "'file.downloaded', 'lesson-attachment&id=" + lessonAttachment.id + "'" + ')" class="btn btn-outline-primary btn-sm" href="/download/lesson/attachment/' + lessonAttachment.id + '/" download="' + lessonAttachment.originalFileName + '"><i class="fas fa-download"></i> ' + lang.watch_download + '</a>';
}

/* COURSE ATTACHMENTS */

function findCourseAttachments() {
    $.ajax({
        url: "/api/crs/course/" + course.id + "/attachments",
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
    return '<a type="button" onclick="trace(' + "'file.downloaded', 'course-attachment&id=" + courseAttachment.id + "'" + ')" class="btn btn-outline-primary btn-sm" href="/download/course/attachment/' + courseAttachment.id + '/" download="' + courseAttachment.originalFileName + '"><i class="fas fa-download"></i> ' + lang.watch_download + '</a>';
}

/* LESSON WATCHED STATUS */

function markLessonWatched(lessonId) {
    var menuElement = $("#lessons-menu-element-lesson-" + lessonId);
    var watchedStatusButton = $("#watched-status-button-lesson-" + lessonId);

    menuElement.css('text-decoration', 'line-through');

    watchedStatusButton.attr('class', 'btn btn-sm btn-secondary');
    watchedStatusButton.attr('onclick', 'changeLessonWatchedStatus(' + lessonId + ', false)');

    var lessonType = menuElement.attr('lesson-type');

    if(lessonType === 't') {
        watchedStatusButton.html(lang.watch_markAsUnresolved);
    } else {
        watchedStatusButton.html(lang.watch_markAsUnwatched);
    }
}

function markLessonUnwatched(lessonId) {
    var menuElement = $("#lessons-menu-element-lesson-" + lessonId);
    var watchedStatusButton = $("#watched-status-button-lesson-" + lessonId);

    menuElement.css('text-decoration', '');

    watchedStatusButton.attr('class', 'btn btn-sm btn-outline-success');
    watchedStatusButton.attr('onclick', 'changeLessonWatchedStatus(' + lessonId + ', true)');

     var lessonType = menuElement.attr('lesson-type');

     if(lessonType === 't') {
         watchedStatusButton.html(lang.watch_markAsSolved);
     } else {
         watchedStatusButton.html(lang.watch_markAsWatched);
     }
}

function changeLessonWatchedStatus(lessonId, watched) {
        $.ajax({
            url: "/api/crs/lesson/" + lessonId + "/change-watched-status",
            type: "PUT",
            contentType: "application/json"
        })
        .done(function () {
            if(watched === true) {
                markLessonWatched(lessonId);
            } else {
                markLessonUnwatched(lessonId);
            }
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}