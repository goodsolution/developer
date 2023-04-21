
var go = true;
var step = 0;
var id = 1;

$(document).ready(function () {
  setInterval(function(){
    if (go) {
        pullAdvise();
        id++;
    }
  }, 2000);
});

function pullAdvise(){
  //console.log('tic');
  step++;
  pull();
}

function pull() {
        $.ajax({
                      url: "/api/adviser/pull?context=inner-voice&customerId=" + loggedCustomer.id + "&appId=inner-voice&lang=pl&country=Poland&secret=2322342342",
                      method: "GET",
                      contentType: "application/json",
                      dataType: "json",
        })
        .done(function (data) {
            render(data);
            go = false;
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            go = true;
        });

}

function render(data){
    //ifs
    if (data.type == 'P') {
        renderMessage(data);
    }
    else if (data.type == 'Q') {
        renderQuestionText(data);
    }
    else if (data.type == 'D') {
        renderQuestionDate(data);
        //TODO calendar
    }
    else if (data.type == 'T') {
        renderQuestionDateTime(data);
        //TODO calendar
    }
    else if (data.type == 'C') {
        renderQuestionCombo(data);
    }
}

function renderResponse(data){
    $('#advices').append(
    '        <div id="advice-response" class="adviser-message">' +
    '             ' + data.response + '<br>' +
    '         </div>');
}

function renderMessage(data){
    $('#advices').append(
    '        <div id="advice-' + data.id + '" class="adviser-message">' +
    '             <strong>' + data.title + '</strong><br>' +
    '             ' + data.content + '<br>' +
    '                          <button id="button-ok-' + data.id + '" onclick="performOk(' + data.id + ')">OK</button>'+
    '                          <button id="button-store-' + data.id + '" onclick="performStore(' + data.id + ')">Zapisz</button>'+
    '         </div>');
}

function renderQuestionDate(data){
    $('#advices').append(
    '        <div id="advice-' + data.id + '" class="adviser-question">' +
    '             <strong>' + data.title + '</strong><br>'+
    '                          ' + data.content + '<br>'+
    '                          <input type="text" id="question-answer-'+data.id+'" name="fname"><br>'+
    '                          <button id="button-no-response-' + data.id + '" onclick="performNoResponse(' + data.id + ')">Nie chcę odpowiadać</button>'+
    '                          <button id="button-later-' + data.id + '" onclick="performLater(' + data.id + ')">Zapytaj później</button>'+
    '                          <button id="button-send-' + data.id + '" onclick="performSend(' + data.id + ', \'STRING\')">Wyślij odpowiedź</button>'+
    '         </div>');
    $("input[id='question-answer-" + data.id + "']").datepicker({
        dateFormat: "yy-mm-dd"
    });
}

function renderQuestionDateTime(data){
    $('#advices').append(
    '        <div id="advice-' + data.id + '" class="adviser-question">' +
    '             <strong>' + data.title + '</strong><br>'+
    '                          ' + data.content + '<br>'+
    '                          <input type="text" id="question-answer-'+data.id+'" name="fname"><br>'+
    '                          <button id="button-no-response-' + data.id + '" onclick="performNoResponse(' + data.id + ')">Nie chcę odpowiadać</button>'+
    '                          <button id="button-later-' + data.id + '" onclick="performLater(' + data.id + ')">Zapytaj później</button>'+
    '                          <button id="button-send-' + data.id + '" onclick="performSend(' + data.id + ', \'STRING\')">Wyślij odpowiedź</button>'+
    '         </div>');

    //TODO download lib
    $("input[id='question-answer-" + data.id + "']").datetimepicker({
                                                                format: 'DD-MM-YYYY HH:mm:ss'
                                                            });
}

function renderQuestionText(data){
    $('#advices').append(
    '        <div id="advice-' + data.id + '" class="adviser-question">' +
    '             <strong>' + data.title + '</strong><br>'+
    '                          ' + data.content + '<br>'+
    '                          <input type="text" id="question-answer-'+data.id+'" name="fname"><br>'+
    '                          <button id="button-no-response-' + data.id + '" onclick="performNoResponse(' + data.id + ')">Nie chcę odpowiadać</button>'+
    '                          <button id="button-later-' + data.id + '" onclick="performLater(' + data.id + ')">Zapytaj później</button>'+
    '                          <button id="button-send-' + data.id + '" onclick="performSend(' + data.id + ', \'STRING\')">Wyślij odpowiedź</button>'+
    '         </div>');
}

function renderQuestionCombo(data){
    $('#advices').append(
    '        <div id="advice-' + data.id + '" class="adviser-question">' +
    '             <strong>' + data.title + '</strong><br>'+
    '                          ' + data.content + '<br>'+
    '                          <select name="cars" id="question-answer-'+data.id+'">'+
    prepareOptions(data.comboJson.options) +
    '                          </select><br>'+
    '                          <button id="button-no-response-' + data.id + '" onclick="performNoResponse(' + data.id + ')">Nie chcę odpowiadać</button>'+
    '                          <button id="button-later-' + data.id + '" onclick="performLater(' + data.id + ')">Zapytaj później</button>'+
    '                          <button id="button-send-' + data.id + '" onclick="performSend(' + data.id + ', \'STRING\')">Wyślij odpowiedź</button>'+
    '         </div>');
}

function prepareOptions(options) {
        result = '';
        options.forEach(function(option){
            result += '<option value="'+ option.value +'">'+ option.text +'</option>\n';
        });
        return result
}

//function renderAdvise(data){
//    $('#advices').append(
//    '        <div id="advice-' + data.id + '" class="adviser-advise">' +
//    '             <strong>Podatki</strong><br>'+
//    '                          Żeby nie płacić podatków to możesz bla blal<br>'+
//    '                          <a href="https://poradnikprzedsiebiorcy.pl/" target="blank">Tu dowiesz się wiecej</a><br>'+
//    '                          <button onclick="performOk(' + data.id + ')">Usuń i nie pokazuj nigdy</button>'+
//    '                          <button onclick="performOk(' + data.id + ')">Pokaż później</button>'+
//    '                          <button onclick="performOk(' + data.id + ')">Zapisz w moich radach</button>'+
//    '         </div>');
//}

function performOk(triggeredAdviceId){
    $('#advice-'+ triggeredAdviceId).css('background-color', '#f8f9fa');
    $('#button-ok-' + triggeredAdviceId).hide();
    $('#button-store-' + triggeredAdviceId).hide();
    respond(triggeredAdviceId, 'R', null, null);
}

function performStore(triggeredAdviceId){
    $('#advice-'+ triggeredAdviceId).css('background-color', '#f8f9fa');
    $('#button-ok-' + triggeredAdviceId).hide();
    $('#button-store-' + triggeredAdviceId).hide();
    respond(triggeredAdviceId, 'S', null, null);
}

function performNoResponse(triggeredAdviceId){
    $('#advice-'+ triggeredAdviceId).css('background-color', '#f8f9fa');
    $('#button-no-response-' + triggeredAdviceId).hide();
    $('#button-later-' + triggeredAdviceId).hide();
    $('#button-send-' + triggeredAdviceId).hide();
    respond(triggeredAdviceId, 'R', null, null);
}

function performLater(triggeredAdviceId){
    $('#advice-'+ triggeredAdviceId).css('background-color', '#f8f9fa');
    $('#button-no-response-' + triggeredAdviceId).hide();
    $('#button-later-' + triggeredAdviceId).hide();
    $('#button-send-' + triggeredAdviceId).hide();
    respond(triggeredAdviceId, 'L', null, null);
}

function performSend(triggeredAdviceId, dataType){
    $('#advice-'+ triggeredAdviceId).css('background-color', '#f8f9fa');
    $('#button-no-response-' + triggeredAdviceId).hide();
    $('#button-later-' + triggeredAdviceId).hide();
    $('#button-send-' + triggeredAdviceId).hide();
    answer = $('#question-answer-' + triggeredAdviceId).val();
    respond(triggeredAdviceId, 'S', answer, dataType);
}

function respond(triggeredAdviceId, newStatus, answer, dataType) {
       context = {
                              context: 'inner-voice',
                              customerId: 1,
                              appId: 'inner-voice',
                              secret: '2b2221ed-43d3-4834-8bd9-a0454f17037f-1592039601774-718d6284-2250-4af7-b700-2cd067e0cc83',
                              lang: 'pl'
                          };
      $.ajax({
          url: "/api/adviser/respond",
          method: "POST",
          data: JSON.stringify({
              context: context,
              triggeredAdviceId, triggeredAdviceId,
              answer: answer,
              status: newStatus,
              dataType: dataType
          }),
          contentType: "application/json",
          dataType: "json",
      })
      .done(function (data) {
        if (data.response != null) {
           renderResponse(data);
        }
        go = true;
      })
      .fail(function(jqxhr, textStatus, errorThrown){
            go = true;
      });
}