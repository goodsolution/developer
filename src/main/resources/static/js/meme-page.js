$(document).ready(function () {
    trace('page.show', 'meme-page&id=' + meme.id);
    $("#create-date").text(prepareDateTime(meme.creationDatetime));
});
