var currentPage = 1;

$(document).ready(function () {
    clearCreateModal()

    trace('page.show', 'itube');

    findITubes();

    $('#search-iTube').on('keypress',function(e) {
        if(e.which === 13) {
            searchITubes();
        }
    });


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

    if (currentPage === 1) {
        $("#next-page-button").addClass("disabled");
    } else {
        $("#next-page-button").removeClass("disabled");
    }
    $("#records").empty();
    findITubes();
}

function findITubes() {
    $.ajax({
        url: "/api/crs/itube/" + currentPage + prepareUrl(),
        type: "GET",
        dataType: "json",
        contentType: "application/json"
    })
        .done(function (data) {
            $("#records").empty();
            fillResults(data.iTubesGetResponse);
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        });
}

function searchITubes() {
    $.ajax({
        url: "/api/crs/itube/1" + prepareUrl(),
        type: "GET",
        dataType: "json",
        contentType: "application/json"
    })
        .done(function (data) {
            $("#records").empty();
            fillResults(data.iTubesGetResponse);
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        });
}

function prepareUrl() {
    let url = "?";
    const phrase = $("#search-iTube").val();
    if (phrase !== "") {
        url += "&phrase=" + phrase;
    }
    return url;
}

function fillResults(elements) {
    elements.forEach(function (element) {
        render(element);
    });
}

function render(element) {
    $('#records').append(
        '<div class="row-cols-sm-3 px-sm-3">' +
            '<iframe src="' + element.link + '" frameBorder="0" allowFullScreen></iframe>' +
            '<div class="text-justify">' +
                '<p class="d-inline-block text-truncate" style="max-width: 300px;">' + element.title + '</p>' +
            '</div>' +
        '<div/>'
    );
}

function refreshITubes() {
    trace("button.search", $("#search").val());
    searchITubes();
}

function clearCreateModal() {
    $("#filter").val("");
}