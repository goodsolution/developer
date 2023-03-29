$(document).ready(function () {
    $("input, select").on("change", function () {
      $("form").submit();
    });
});

