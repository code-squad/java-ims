$(document).ready(function () {

  if ($("#milestone-list") != null) {
    showMilestoneList();
  }

  if ($("#assignee-list") != null) {
    showAssigneeList();
  }

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

          alert("마일스톤 추가 됨");
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

          alert("담당자 추가 됨");
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

  String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
      return typeof args[number] != 'undefined'
          ? args[number]
          : match
          ;
    });
  };

});