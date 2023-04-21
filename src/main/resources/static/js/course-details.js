
$(document).ready(function () {
    trace('page.show', 'course-details&id=' + course.id);
    findCourseComments();
});

function addToBasket(id) {
    $.ajax({
        url: "/api/crs/add-course-to-basket/" + course.id,
        type: "post",
        contentType: "application/json"
    })
    .done(function () {
        updateProductsInBasketQuantity();
        $("#successfully-added-to-basket-modal").modal('show');
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}


function findCourseComments() {
    $.ajax({
        url: "/api/crs/course-comments/" + course.id,
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (response) {
        showCommentsHeader(response.courseComments);
        fillCourseComments(response.courseComments);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function showCommentsHeader(courseComments) {
    if(courseComments.length > 0) {
        $("#students-comments-header").show();
    }
}

function fillCourseComments(courseComments) {
    courseComments.forEach(function(courseComment){
        fillCourseComment(courseComment);
    });
}

function fillCourseComment(courseComment) {
    $('#comments').append(
            '<div class="row mb-3">' +
                '<div class="col-lg-2"></div>' +
                '<div class="rounded bg-light shadow-sm col-lg-8">' +
                    '<div class="p-3"></div>' +
                    '<div class="my-3 text-center">' + courseComment.text + '</div>' +
                    '<div class="d-flex justify-content-end p-3"><small class="form-text text-muted">' + prepareDateTime(courseComment.createDatetime) + '</small></div>' +
                '</div>' +
                '<div class="col-lg-2"></div>' +
            '</div>'
    );
}