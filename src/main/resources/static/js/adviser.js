var go = true;

$(document).ready(function () {
  setInterval(function(){
    if (go) {
        getAdvise();
    }
  }, 5000);
});

var adviserPanelTagId = "#adviser-panel";

function getAdvise(){
  //console.log('tic');

      context = {
                              domain: 'main-stream',
                              id: 2,
                              appId: 'life-adviser',
                              secret: '2b2221ed-43d3-4834-8bd9-a0454f17037f-1592039601774-718d6284-2250-4af7-b700-2cd067e0cc83',
                              lang: 'pl'
                          };
      $.ajax({
          url: "/api/adviser/trigger",
          method: "POST",
          data: JSON.stringify({
              context: context
          }),
          contentType: "application/json",
          dataType: "json",
      })
      .done(function (data) {
            retrieveAdvise(data.data);
      })
      .fail(function(jqxhr, textStatus, errorThrown){
            $(adviserPanelTagId).addClass("hide");
            go = true;
      });
}

function retrieveAdvise(data) {
//console.log(data);
       if(data != null) {
           $(adviserPanelTagId).html(prepareAdviserPanelHtml(data));
           $(adviserPanelTagId).removeClass("hide");
           go = false;
       } else {
            $(adviserPanelTagId).addClass("hide");
            go = true;
       }
//    $.ajax({
//              url: "/api/adviser/pull?domain=user&id=1",
//              method: "GET",
//              contentType: "application/json",
//              dataType: "json",
//          })
//          .done(function (data) {
//                $(adviserPanelTagId).html(prepareAdviserPanelHtml(data));
//                $(adviserPanelTagId).removeClass("hide");
//                go = false;
//          })
//          .fail(function(jqxhr, textStatus, errorThrown){
//               $(adviserPanelTagId).addClass("hide");
//               go = true;
//          });
}

function prepareAdviserPanelHtml(data) {
//console.log(data);
    html =  '<div style="display:inline;">' + data.content + '</div>';
    if (data.type == 'Q') {
        if (data.dataType == 'STRING') {
            if (data.component == 'INPUT_TEXT') {
                html +=  '<div style="display:inline;"><input type="text" id="answer" name="answer"></div>';
            } else if (data.component == 'SELECT') {
                html +=  '<div style="display:inline;"><select name="answer" id="answer">';
                data.answerOptions.forEach(function (option) {
                    html +=  '   <option value="' + option.value + '">' + option.name + '</option>';
                });
                html +=  '</select></div>';
            }
        }
    }
    html += '<div style="display:inline;"><button type="button" onclick="respond(' + data.adviceId + ',' + data.id + ', 1)">&#x2714;</button></div>';
    html += '<div style="display:inline;"><button type="button" onclick="respond(' + data.adviceId + ',' + data.id + ', -1)">X</button></div>';

    return html;
}

function respond(adviceId, triggeredAdviceId,  score) {
      answer = document.getElementById('answer');
      answer_value = '';
      if (answer != null) {
              answer_value = document.getElementById('answer').value;
      }

      context = {
                              domain: 'main-stream',
                              id: 1,
                              appId: 'life-adviser',
                              secret: '2b2221ed-43d3-4834-8bd9-a0454f17037f-1592039601774-718d6284-2250-4af7-b700-2cd067e0cc83',
                              lang: 'pl'
                          };
      $.ajax({
          url: "/api/adviser/respond",
          method: "POST",
          data: JSON.stringify({
              context: context,
              adviceId: adviceId,
              triggeredAdviceId, triggeredAdviceId,
              answer: answer_value,
              score: score
          }),
          contentType: "application/json",
          dataType: "json",
      })
      .done(function (data) {
         $(adviserPanelTagId).addClass("hide");
         go = true;
      })
      .fail(function(jqxhr, textStatus, errorThrown){
            $(adviserPanelTagId).addClass("hide");
            go = true;
      });
}

