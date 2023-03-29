/*NAVBAR*/

$(".dropdown-menu a.dropdown-toggle").on("click", function (e) {
  if (!$(this).next().hasClass("show")) {
    $(this).parents(".dropdown-menu").first().find(".show").removeClass("show");
  }
  var $subMenu = $(this).next(".dropdown-menu");
  $subMenu.toggleClass("show");

  $(this).parents("li.nav-item.dropdown.show").on("hidden.bs.dropdown", function (e) {
    $(".dropdown-submenu .show").removeClass("show");
  });

  return false;
});

$(document).ready(function () {
  markActiveMenuItem();
  updateProductsInBasketQuantity();
});

function markActiveMenuItem() {
  var path = window.location.pathname;
  if (path != "/building" && !path.startsWith("/watch")) {
    $(".nav-link").each(function () {
      var href = $(this).attr("href");
      if (path.substring(0, href.length) === href) {
        $(this).addClass("active");
      }
    });
    $(".dropdown-item").each(function () {
      var href = $(this).attr("href");
      if (path.substring(0, href.length) === href) {
        $(this).closest(".nav-item").children("a").addClass("active");
      }
    });
  }
}

function updateProductsInBasketQuantity() {
    $.ajax({
        url: "/api/crs/basket/courses/quantity",
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (response) {
        var quantity = response.quantity;

        $("#products-in-basket-quantity").html(quantity);

        if(quantity >= 1) {
            $("#products-in-basket-counter").show();
        } else {
            $("#products-in-basket-counter").hide();
        }

    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });

}

/* ERROR and SUCCESS */
function displayErrorInformation(jqxhrResponseText) {
    alert(prepareErrorMessage(jqxhrResponseText));
    //$("#operation-failed-modal").modal("show");
    //$("#operation_error_message").text(response.message);
}

function prepareErrorMessage(jqxhrResponseText) {
    var response = jQuery.parseJSON(jqxhrResponseText);
    return response.message;
}

function htmlEncode (html) {
    html = html.split(' ').join('&#32;');
    html = html.split('/').join('&#47;');
    html = html.split('<').join('&#60;');
    html = html.split('>').join('&#62;');
    html = html.split('≤').join('&le;');
    html = html.split('≥').join('&ge;');
    html = html.split("'").join('&#39;');
    html = html.split('"').join('&quot;');
    return html;
}

function changeLanguage(lang) {
    window.location.href = window.location.pathname + "?lang=" + lang;
}

//$(document).ready(function () {
//    $.ajax({
//        url: "/api/adviser/languages",
//        method: "get",
//        contentType: "application/json",
//        dataType: "json"
//    })
//        .done(function (languages) {
//            languages.forEach(function (language) {
//                $("#language-change-menu").append(
//                    '<a class="dropdown-item" href="#" onclick="changeLanguage(\'' + language.code + '\')">' + language.value + '</a>'
//                )
//            })
//        })
//        .fail(function(jqxhr, textStatus, errorThrown){
//            displayErrorInformation(jqxhr.responseText);
//        });
//})

function prepareText(text) {
    if(text === "null") {
        return "";
    } else {
        return htmlEncode(text);
    }
}

function prepareDateTime(dateTime) {
    return dateTime.replace('T', ' ');
}

function preparePaginationUrl() {

    let url = "";

    const pageSize = $("#page_size").children(":selected").val();
    if (pageSize != "" && pageSize > 0) {
        url += "&page_size=" + pageSize;
    }

    const page = $("#page").val();
    if (page != "" && !isNaN(page) && page > 0) {
        url += "&page=" + page;
    }

    return url;
}

/* PASSWORD */
function hash(password) {
  var hashObj = new jsSHA("SHA-256", "TEXT", {numRounds: 1});
  hashObj.update(password);
  return hashObj.getHash("HEX");
}

/* PASSWORD VALIDATION */

function arePasswordsValid(password, repeatedPassword) {
    if(password.length < 8) {
        showError("Password must have at least 8 characters");
        return false;
    } else if(password !== repeatedPassword) {
        showError("Passwords are not the same");
        return false;
    } else if(!hasLowerCase(password)) {
        showError("Password must have at least one lowercase letter");
        return false;
    } else if(!hasUpperCase(password)){
        showError("Password must have at least one uppercase letter");
        return false;
    } else if(!hasNumber(password)) {
        showError("Password must have at least one number");
        return false;
    } else if(!hasSpecialCharacter(password)) {
        showError("Password must have at least one special character");
        return false;
    } else if(hasSpace(password)) {
        showError("Password can't have spaces");
        return false;
    }else {
        return true;
    }
}
function hasLowerCase(str) {
    return (/[a-z]/.test(str));
}

function hasUpperCase(str) {
    return (/[A-Z]/.test(str));
}

function hasNumber(str) {
    return (/[0-9]/.test(str));
}

function hasSpecialCharacter(str) {
    var regex = /^[A-Za-z0-9 ]+$/
    return !regex.test(str);
}

function hasSpace(str) {
    return (/\s/.test(str));
}

/* IMAGES */

function prepareImage(imageName) {
    if(imageName === "") {
        return "";
    } else {
        return '<img src="/get-image/' + imageName + '/" class="img-fluid img-thumbnail">';
    }
}

function prepareProductImage(imageName) {
    if(imageName === "") {
        return "";
    } else {
        return '<img src="/get-image/' + imageName + '/" class="img-fluid img-thumbnail product-image">';
    }
}

$(document).ready(function() {
    if(localStorage.getItem('popState') != 'shown'){
        $('#cookie-modal').modal('show');
        localStorage.setItem('popState','shown')
    }
});

// what:
// button.<button-name>
// page.show
// file.downloaded
// link.clicked
function trace(what, value) {
    $.ajax({
        url: "/api/crs/trace",
        method: "post",
        contentType: "application/json",
        data: JSON.stringify({
            what: what,
            value: value,
            who: null,
            browser: getBrowser(),
            operatingSystem: navigator.platform
        })
    })
        .done(function () {

        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            displayErrorInformation(jqxhr.responseText);
        })
}

function getBrowser() {
   if(navigator.userAgent.match('OPR')) {
        return 'opera';
   } else if (navigator.appVersion.indexOf("Edg") > -1) {
        return 'edge';
   } else if (/Chrome/.test(navigator.userAgent) && /Google Inc/.test(navigator.vendor)) {
        return 'chrome';
   } else if (navigator.userAgent.match('MSIE|Trident')) {
        return 'internet explorer';
   } else if (navigator.userAgent.toLowerCase().indexOf('firefox') > -1) {
        return 'mozilla';
   } else {
        return 'unknown'
   }
}

/* DICTIONARIES */

function getDictionaryValue(code, dictionary) {
    for(i=0; i<dictionary.length; i++) {
        if(dictionary[i].code === code) {
            return dictionary[i].value;
        }
    }
}