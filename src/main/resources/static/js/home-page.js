var currentPage = 1;

$(document).ready(function () {
    clearCreateModal()

    // trace('page.show', 'home-page');
    findInvestments();

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

function findInvestments() {
    $.ajax({
        url: "/api/dev/investment/1",
        type: "GET",
        dataType: "json",
        contentType: "application/json"
    })
        .done(function (data) {
            $("#records").empty();
            fillResults(data.investmentsGetResponse);
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
            '<h5 class="card-title">' + element.addressCity + '</h5>' +
            '<p class="card-text">Liczba inwestycji</p>' +
            '<p class="card-text">Wolne mieszkania</p>' +
        '</div>' +
    '</div>'
    );
}

function refreshITubes() {
    trace("button.search", $("#search").val());
    searchITubes();
}

function clearCreateModal() {
    $("#filter").val("");
}