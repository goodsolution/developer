$(document).ready(function () {
    $("#delete-modal").html(prepareDeleteModal());
    findAllergens();

});

function findAllergens() {
    $.ajax({
        url: "/api/allergens",
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
        .done(function (data) {
            fillResults(data);
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        });
}

// function getAllergens() {
//     var allergen_id = $("#allergen_id").val();
//     if (allergen_id != "") {
//         allergen_id = "&allergen_id=" + allergen_id;
//     }
//     var allergen_name = $("#allergen_name").val();
//     if (allergen_name != "") {
//         allergen_name = "&name=" + allergen_name;
//     }
//         $.ajax({
//         url: "api/allergens?" +
//             allergen_id +
//             allergen_name,
//         type: "get",
//         dataType: "json",
//         contentType: "application/json"
//     })
//         .done(function (data) {
//             // fillResults(data);
//         })
//         .fail(function (jqxhr, textStatus, errorThrown) {
//             displayErrorInformation(jqxhr.responseText);
//         });
// }


function fillResults(data) {
    $("#allergens-results").empty();
    fillTable(data)
}


function fillTable(allergens) {
    var buttonCell;
    buttonCell =
        '<button onclick="postNewAllergen()" type="button" class="wide">Dodaj</button>';


    $("#allergens-results")
        .append("<tr><td class='align-middle'>" + "0" + "</td>" +
            "<td class='align-middle font-weight-bold'>" +

            " <input name=\"allergen_name\"  id=\"allergen_name\" type=\"text\"  value=\"\" > " + "</td>" +
            "<td class='align-middle font-weight-bold'>" + buttonCell + "</td>" +
            "<td class='align-middle'>" + "</td>" +
            "</tr>");
    allergens.forEach(function (record) {

        var buttonDeleteCell;
        buttonDeleteCell =
            '<button onClick="showDeleteModal(' + record.id + ')"  type="button">Usuń</button>';
        var buttonSaveCell;
        buttonSaveCell =
            '<button onClick="save(' + record.id + ')"  type="button">Zapisz</button>';


        $("#allergens-results")
            .append("<tr><td class='align-middle'>" + record.no + "</td>" +
                "<td class='align-middle font-weight-bold'>" +
                " <input name=\"allergen_name\"  id=\"allergen_name_"+record.id+"\" type=\"text\"  value=\"" + record.name + "\" > "
                + "</td>" +
                "<td class='align-middle font-weight-bold'>" + buttonSaveCell + "</td>" +
                "<td class='align-middle'>" + buttonDeleteCell + "</td>" +
                "</tr>");
    });


}

function save(id) {
        $.ajax({
            url: "/api/allergens/" + id,
            method: "PUT",
            data: JSON.stringify({
                name: $("#allergen_name_"+id).val()

            }),
            contentType: "application/json"
        })
        .done(function (data) {

            $("#operation-successful-modal").modal("show");
            findAllergens();
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        });

}

function showDeleteModal(id) {
    var $modal = $("#delete-allergen-modal");
    $modal.find("#allergen_id").val(id);
    $modal.modal("show");

}

function prepareDeleteModal() {
    var deleteAllergenModal =
        "<div class=\"modal\" id=\"delete-allergen-modal" + "\">" +
        "<div class=\"modal-dialog modal-dialog-centered\">" +
        "<div class=\"modal-content bg-red p-2\">" +
        '<div class="modal-body w-100">' +
        '<input type="hidden" id="allergen_id" name="allergen_id">' +
        '<button type="button" class="close" data-dismiss="modal">&times;</button>' +
        '<p> </p>' +
        '<p class="font-weight-bold text-center">Czy na pewno chcesz usunąć ten alergen?</p>' +
        '<p class="text-center">' +
        '<button type="button" id="allergenDeleteConfirmButton"' +
        'onclick="deleteAllergen(document.getElementById(\'allergen_id\').value );">' +
        "Tak" +
        "</button>" +
        '<button type="button" data-dismiss="modal">' +
        'Anuluj' +
        '</button>' +
        '</p>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</div>';
    return deleteAllergenModal;
}

function closeDeleteModal() {
    $("#delete-allergen-modal").modal("hide");
}

function deleteAllergen(id) {
    var $modal = $("#delete-allergen-modal-" + id);
    $.ajax({
        url: "/api/allergens/" + id + "",
        type: "DELETE"
    })
        .done(function (response) {
            findAllergens();
            closeDeleteModal();
            $("#operation-successful-modal").modal("show");
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        });
    $modal.modal("hide");
}

function postNewAllergen() {
    var request = $.ajax({
        url: "api/allergens",
        method: "POST",
        data: JSON.stringify({
            name: $("#allergen_name").val()
        }),
        contentType: "application/json"
    })
        .done(function (response) {
            findAllergens();
            $("#operation-successful-modal").modal("show");
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        });
    return request;
}

function refresh() {
    $("form").submit();
}
