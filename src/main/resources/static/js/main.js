showAssigneeList();
showMilestoneList();
showLabelList();
showAnswerList();

$(document).on("click", "#milestone-list .mdl-menu__item > a",
    function (e) {
      e.preventDefault();

      var url = $(this).attr("href");

      console.log("url " + url);

      $.ajax({
        type: 'post',
        url: url,
        contentType: 'application/json',
        error: onError,
        success: onSuccess
      });

      function onError(error) {
        console.warn("error" + error.status);
      }

      function onSuccess(data, status) {
        console.log("post success");

        alert("마일스톤 지정 됨");
      }
    });

$(document).on("click", "#assignee-list .mdl-menu__item > a",
    function (e) {
      e.preventDefault();

      var url = $(this).attr("href");

      console.log("url " + url);

      $.ajax({
        type: 'post',
        url: url,
        contentType: 'application/json',
        error: onError,
        success: onSuccess
      });

      function onError(error) {
        console.warn("error" + error.status);
      }

      function onSuccess(data, status) {
        console.log("post success");

        alert("담당자 지정 됨");
      }
    });

$(document).on("click", "#label-list .mdl-menu__item > a",
    function (e) {
      e.preventDefault();

      var url = $(this).attr("href");

      console.log("url " + url);

      $.ajax({
        type: 'post',
        url: url,
        contentType: 'application/json',
        error: onError,
        success: onSuccess
      });

      function onError(error) {
        console.warn("error" + error.status);
      }

      function onSuccess(data, status) {
        console.log("post success");

        alert("라벨 지정 됨");
      }
    });

function showMilestoneList() {
  $.ajax({
    type: 'get',
    url: "/api/milestones",
    contentType: 'application/json',
    error: onError,
    success: onSuccess
  });

  function onError(error) {
    console.warn("error" + error.status);
  }

  function onSuccess(data, status) {
    console.log(data);

    var milestoneTemplate = $("#milestoneTemplate").html();
    var milestones = data.milestones;

    $("#milestone-list").empty();

    for (var i = 0; i < milestones.length; i++) {
      var template = milestoneTemplate.format(milestones[i].id, milestones[i].content);
      $("#milestone-list").append(template);
    }
  }
}

function showAssigneeList() {
  $.ajax({
    type: 'get',
    url: "/api/users",
    contentType: 'application/json',
    error: onError,
    success: onSuccess
  });

  function onError(error) {
    console.warn("error" + error.status);
  }

  function onSuccess(data, status) {
    console.log(data);

    var assigneeTemplate = $("#assigneeTemplate").html();
    var assignees = data.assignees;

    $("#assignee-list").empty();

    for (var i = 0; i < assignees.length; i++) {
      var template = assigneeTemplate.format(assignees[i].id, assignees[i].content);
      $("#assignee-list").append(template);
    }
  }
}

function showLabelList() {
  $.ajax({
    type: 'get',
    url: "/api/labels",
    contentType: 'application/json',
    error: onError,
    success: onSuccess
  });

  function onError(error) {
    console.warn("error" + error.status);
  }

  function onSuccess(data, status) {
    console.log(data);

    var labelTemplate = $("#labelTemplate").html();
    var labels = data.labels;

    $("#label-list").empty();

    for (var i = 0; i < labels.length; i++) {
      var template = labelTemplate.format(labels[i].id, labels[i].content);
      $("#label-list").append(template);
    }
  }
}

function showAnswerList() {

  var issueId = $("#issue-id").val();

  $.ajax({
    type: 'get',
    url: "/issues/" + issueId + "/answers",
    contentType: 'application/json',
    error: onError,
    success: onSuccess
  });

  function onError(error) {
    console.warn("error" + error.status);
  }

  function onSuccess(data, status) {
    console.log(data);

    var answerTemplate = $("#answerTemplate").html();
    var answers = data.answers;

    $("#answer-list").empty();

    for (var i = 0; i < answers.length; i++) {
      var template = answerTemplate.format(answers[i].name, answers[i].contents, answers[i].createDate, answers[i].id);
      $("#answer-list").append(template);
    }
  }
}

<!-- ------------------------------------------------------------------ -->

$("#answer-write button[type=submit]").on("click", addAnswer);

function addAnswer(e) {
  e.preventDefault();

  var inputVal = $(".mdl-textfield__input").val();
  var url = $("#answer-write").attr("action");

  console.log(JSON.stringify({content: inputVal}));
  console.log("url " + url);

  $.ajax({
    type: 'post',
    url: url,
    contentType: 'application/json',
    data: JSON.stringify({"content": inputVal}),
    dataType: 'json',
    error: onError,
    success: onSuccess
  });
}

function onError(error) {
  console.warn("error" + error.status);
}

function onSuccess(data, status) {
  console.log(data);
  showAnswerList();
  $("textarea[name=content]").val("");
}

$(document).on("click", ".comment__author #answer-delete", deleteAnswer);

function deleteAnswer(e) {
  e.preventDefault();

  var url = $(this).attr("href");

  $.ajax({
    type: 'delete',
    url: url,
    dataType: 'json',
    success: onSuccess,
    error: onError
  });

  function onError(error) {
    console.warn("error" + error.status);
  }

  function onSuccess(data, status) {
    console.log(data);
    showAnswerList();
  }
}

String.prototype.format = function () {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function (match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};