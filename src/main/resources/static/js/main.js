$(".assignee-items").click(addAssignee)
function addAssignee(e) {
    e.preventDefault();

    var addBtn = $(this);
    var addUserUrl = addBtn.closest(".mdl-menu__item").attr("id");

    $.ajax({
        type: 'post',
        url: addUserUrl,
        dataType: 'json',
        error: function (xhr, status) {

        },
        success: function (data, status) {
            alert("assignee : " + data.userId);

            var assigneeTemplate = $("#assigneeTemplate").html();
            var template = assigneeTemplate.format(data.userId);
            $("#assignee-menu").remove();
            $(template).insertAfter($("#label-menu"));
        }
    })
}


$(".label-items").click(addLabel);
function addLabel(e) {
    e.preventDefault();

    var addBtn = $(this);
    var addLabelUrl = addBtn.closest(".mdl-menu__item").attr("id");

    $.ajax({
        type: 'post',
        url: addLabelUrl,
        dataType: 'json',
        error: function (xhr, status) {

        },
        success: function (data, status) {
            alert("labels : " + data.title + " color : " + data.color);
        }
    })
}


$(".milestone-items").click(addMilestone);
function addMilestone(e) {
    e.preventDefault();

    var addBtn = $(this);
    var addMilestoneUrl = addBtn.closest(".mdl-menu__item").attr("id");

    $.ajax({
        type: 'post',
        url: addMilestoneUrl,
        dataType: 'json',
        error: function (xhr, status) {

        },
        success: function (data, status) {
            var milestoneTemplate = $("#milestoneTemplate").html();
            var template = milestoneTemplate.format(data.title);
            $("#milestone-menu").remove();
            $(template).insertAfter(".section-spacer").show(1000);
        }
    })
}


$(".delete-comments").click(deleteAnswer);
function deleteAnswer(e) {
    e.preventDefault();
    if (!confirm("삭제하시겠습니까?")) {
        return;
    }
    var deleteBtn = $(".delete-comments");
    var deleteUrl = $(this).closest(".delete-comment-parent").attr("id");

    $.ajax({
        type: 'delete',
        url: deleteUrl,
        dataType: 'json',
        error: function (xhr, status) {
        },
        success: function (data, status) {
            $("#" + data.id.toString()).closest("div").remove();
        }
    })
}

$("#comment-submit").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();

    var queryString = $("#comment-form").serialize();
    var url = $("#comment-form").attr("action");

    $.ajax({
        type: 'post',
        url: url,
        data: queryString,
        dataType: 'json',
        error: onError,
        success: onSuccess
    });
}


function onError() {
}

function onSuccess(data, status) {
    var answerTemplate = $("#answerTemplate").html();

    var template = answerTemplate.format(data.writer.name, data.contents, data.id);
    if (data.id < 1) {
        $("#before-comment").after(template);
    }
    else
        $(template).insertAfter("#" + (data.id - 1).toString()).hide().fadeIn(500);

    $("#comment-form textarea[name=contents]").val("");
}

String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined' ? args[number] : match;
    });
};
