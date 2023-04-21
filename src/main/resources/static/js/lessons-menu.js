function findLessons() {
    $.ajax({
        url: "/api/crs/lessons/" + courseId,
        method: "GET",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (response) {
        fillLessons(response);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    })
}

function fillLessons(response) {
    fillLessonsWithoutModule(response.lessonsWithoutModule);
    fillModules(response.modules);
}

function fillLessonsWithoutModule(lessonsWithoutModule) {
    lessonsWithoutModule.forEach(function(lesson){
        $('#lessons-menu').append(prepareLesson(lesson));
    });
}

function prepareLesson(lesson) {

    var selectedLesson;
    var buttonStyle;

    if (lesson.id == lessonId) {
        selectedLesson = true;
    } else {
        selectedLesson = false;
    }

    if(lesson.watched == true) {

        if(selectedLesson == true) {
            buttonStyle = "btn-primary";
        } else {
            buttonStyle = "btn-secondary";
        }

    } else {

        if(selectedLesson == true) {
            buttonStyle = "btn-primary";
        } else {
            buttonStyle = "btn-outline-success";
        }

    }

    return '<a href="/watch/course/' + courseId + '/lesson/' + lesson.id + '" type="button" class="btn ' + buttonStyle + ' btn-block">' + lesson.title + '</a>';
}

function fillModules(modules) {
    modules.forEach(function(module) {
        fillModule(module);
    });
}

function fillModule(module) {
    $('#lessons-menu').append(prepareModule(module));
}

function prepareModule(module) {

    var lessons = module.lessons;
    var html = "";

    html += '<div class="border mt-2 p-1">';
    html +=     '<a class="text-center mt-1" data-toggle="collapse" href="#module-' + module.id + '">' + module.name + '</a>';
    html +=     '<div class="collapse show mt-1" id="module-' + module.id + '">';

    lessons.forEach(function(lesson) {
        html+= prepareLesson(lesson);
    });

    html +=     '</div>';
    html += '</div>';

    return html;
}
