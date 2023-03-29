$(document).ready(function () {
    $("#delete-modal").html(prepareDeleteModal());
    findProductsTypes();

});

function findProductsTypes() {
    $.ajax({
        url: "/api/products-types",
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


function fillResults(data) {
    $("#products-types-results").empty();
    fillTable(data);
}


function fillTable(product) {
    var buttonCell;
    buttonCell =
        '<button onclick="createProductType()" type="button" class="wide">Dodaj</button>';


    $("#products-types-results")
        .append("<tr><td class='align-middle'>" + "0" + "</td>" +
            "<td class='align-middle font-weight-bold'>" +

            " <input name=\"product_type_name\"  id=\"product_type_name\" type=\"text\"  value=\"\" > " + "</td>" +
            "<td class='align-middle font-weight-bold'>" + buttonCell + "</td>" +
            "<td class='align-middle'>" + "</td>" +
            "</tr>");
    product.forEach(function (record) {

        var buttonDeleteCell;
        buttonDeleteCell =
            '<button onClick="showDeleteModal(' + record.id + ')"  type="button">Usuń</button>';
        var buttonSaveCell;
        buttonSaveCell =
            '<button onClick="modificationProductType(' + record.id + ')"  type="button">Zapisz</button>';


        $("#products-types-results")
            .append("<tr><td class='align-middle'>" + record.no + "</td>" +
                "<td class='align-middle font-weight-bold'>" +
                " <input name=\"product_type_name\"  id=\"product_type_name_"+record.id+"\" type=\"text\"  value=\"" + record.type + "\" > "
                + "</td>" +
                "<td class='align-middle font-weight-bold'>" + buttonSaveCell + "</td>" +
                "<td class='align-middle'>" + buttonDeleteCell + "</td>" +
                "</tr>");
    });


}

function modificationProductType(id) {
        $.ajax({
            url: "/api/products-types/" + id,
            method: "PUT",
            data: JSON.stringify({
                type: $("#product_type_name_"+id).val()

            }),
            contentType: "application/json"
        })
        .done(function (data) {

            $("#operation-successful-modal").modal("show");
            findProductsTypes();
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        });

}

function showDeleteModal(id) {
    var $modal = $("#delete-product-type-modal");
    $modal.find("#product_type_id").val(id);
    $modal.modal("show");

}

function prepareDeleteModal() {
    var deleteProductTypeModal =
        "<div class=\"modal\" id=\"delete-product-type-modal" + "\">" +
        "<div class=\"modal-dialog modal-dialog-centered\">" +
        "<div class=\"modal-content bg-red p-2\">" +
        '<div class="modal-body w-100">' +
        '<input type="hidden" id="product_type_id" name="product_type_id">' +
        '<button type="button" class="close" data-dismiss="modal">&times;</button>' +
        '<p> </p>' +
        '<p class="font-weight-bold text-center">Czy na pewno chcesz usunąć ten produkt?</p>' +
        '<p class="text-center">' +
        '<button type="button" id="productTypeDeleteConfirmButton"' +
        'onclick="deleteProductType(document.getElementById(\'product_type_id\').value );">' +
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
    return deleteProductTypeModal;
}

function closeDeleteModal() {
    $("#delete-product-type-modal").modal("hide");
}

function deleteProductType(id) {
    var $modal = $("#delete-product-type-modal-" + id);
    $.ajax({
        url: "/api/products-types/" + id + "",
        method: "DELETE"
    })
        .done(function (response) {
            findProductsTypes();
            closeDeleteModal();
            $("#operation-successful-modal").modal("show");
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        });
    $modal.modal("hide");
}

function createProductType() {
    var request = $.ajax({
        url: "api/products-types",
        method: "POST",
        data: JSON.stringify({
            type: $("#product_type_name").val()
        }),
        contentType: "application/json"
    })
        .done(function (response) {
            findProductsTypes();
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
