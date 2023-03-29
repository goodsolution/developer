var currentPage = 1;

$(document).ready(function () {
    trace('page.show', 'job-offers');
    find();
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

    if (currentPage == 1) {
        $("#next-page-button").addClass("disabled");
    } else {
        $("#next-page-button").removeClass("disabled");
    }

    $("#records").empty();

    find();
}

function find() {
    $.ajax({
        url: "/api/crs/job-offers/" + currentPage,
        type: "GET",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (data) {
        fillResults(data.jobOffers);
    })
    .fail(function (jqxhr, textStatus, errorThrown) {
        displayErrorInformation(jqxhr.responseText);
    });
}

function fillResults(elements) {
    elements.forEach(function (element) {
        render(element);
    });
}

function render(element) {
    $('#records').append(
        '<div class="container w-75 text-center shadow p-3 mb-5 bg-white rounded">' +
            '<h4 class="text-center mt-3 p-md-4">' + element.title + '</h4>' +
            '<div class="mt-3">' + element.content + '</div>' +
            '<div class="mt-3">' + prepareDateTime(element.createDateTime) + '</div>' +
        '</div>'
    );
}