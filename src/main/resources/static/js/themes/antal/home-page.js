var currentPage = 1;

$(document).ready(function () {
    clearCreateModal()
    // trace('page.show', 'home-page');

    findAvailablePremisesByCity()

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

function findAvailablePremisesByCity() {
    $.ajax({
        url: "api/dev/developers/1/investments/buildings/premises/count",
        type: "GET",
        dataType: "json",
        contentType: "application/json"
    })
        .done(function (data) {
            $("#records").empty();
            fillResults(data.availablePremisesByCity);
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
    $('#cities').append(
        '<div class="card">' +
        '<div class="card-body">' +
        '<h5 class="card-title">miasto: ' + element.city + '</h5>' +
        '<p class="card-text">dostepne mieszkania: ' + element.availablePremisesNumber + '</p>' +
        '</div>' +
        '</div>'
    );
}

function clearCreateModal() {
    $("#filter").val("");
}