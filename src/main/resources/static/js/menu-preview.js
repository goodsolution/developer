var dictProducts;

$(document).ready(function () {
    getDictionaryProducts();
});


function getDictionaryProducts() {
    var result;
    $.ajax({
            url: "/api/dictionary/" + "PRODUCTS",
            type: "GET",
            dataType: "json"
        })
    .done(function(response) {
            dictProducts = response;
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation("Cannot get dict: " + name + " due to: " + jqxhr.responseText);
    });
}


function changeDateArrayToISODate(dateArray) {
    var month = dateArray[1];
    if (month.toString().length == 1) {
        month = "0" + dateArray[1];
    }

    var day = dateArray[2];
    if (day.toString().length == 1) {
            day = "0" + dateArray[2];
    }

    return dateArray[0] + "-" + month + "-" + day;
}

function openExcelSheet() {
  var dateFrom = $("#dateFrom").val();
  var dateTo = $("#dateTo").val();
  var dietId = $("input[name='dietId']:checked").val();
  var kindId = $("#kind_id").val();

  if (dietId == null || dietId == "") {
    alert("Wybierz dietę");
    return;
  }
  if (dietId == null || dietId == "") {
    alert("Wybierz rodzaj");
    return;
  }
  if (dateFrom == null || dateFrom == "") {
    alert("Ustaw datę od");
    return;
  }
  if (dateTo == null || dateTo == "") {
    alert("Ustaw datę do");
    return;
  }
  window.open("/excel-menu-preview?from=" + dateFrom + "&to=" + dateTo + "&diet_id=" + dietId + "&kind_id=" + kindId, "_blank");
}

function reloadProducts(selectProducts, dietId) {
    //alert("reloadProducts select: " + selectProducts.value + " dietId: " + selectDiets.value);
    reloadProductsFromDict(selectProducts, dietId);
}

function reloadProductsFromDict(selectProducts, dietId) {
    var products = $(selectProducts);
    $(selectProducts).empty()
    if (dietId == null || dietId == '') {
        products.append('<option value="">' + '---' + '</option>');
    } else {
        dictProducts.forEach(function(product){
              if (product.extraId == dietId) {
                products.append('<option value="'+ product.id +'">' + product.value + '</option>');
              }
        });
    }
}
