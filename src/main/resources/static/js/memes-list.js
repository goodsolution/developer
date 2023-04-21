
var currentPage = 1;

$(document).ready(function () {
    trace('page.show', 'memes');
    findMemes();
});

function goToNextPage() {
    currentPage++;
    performPageChange();
}

function goToPreviousPage() {
    currentPage--;
    performPageChange();
}


function performPageChange() {
    $("#current-page").text(currentPage);

    if(currentPage == 1) {
        $("#next-page-button").addClass("disabled");
    } else {
        $("#next-page-button").removeClass("disabled");
    }

    $("#records").empty();

    findMemes();
}

function findMemes() {
    $.ajax({
        url: "/api/crs/memes/" + currentPage,
        type: "GET",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (data) {
        fillResults(data.memes);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillResults(memes) {
    memes.forEach(function(meme){
        addMeme(meme);
    });
}

function addMeme(meme) {
    $('#records').append(
        '<div class="rounded bg-light shadow-sm my-5 p-3 text-center">' +
            '<a style="color:#000000; text-decoration:none;" href="/meme/' + meme.code + '">' +
                '<h4 class="text-center mt-3">' + meme.title + '</h4>' +
            '</a>' +
            '<a href="/meme/' + meme.code + '">' +
                '<img src="/meme/file/' + meme.fileName + '/" class="img-fluid mt-5">' +
            '</a>' +
            '<div class="mt-3">' + meme.description + '</div>' +
            '<div class="row mt-5">' +
                '<div class="col-lg-6"></div>' +
                '<div class="col-lg-6"><small class="form-text text-muted">' + prepareDateTime(meme.creationDatetime) + '</small></div>' +
            '</div>' +
        '</div>'
    );
}