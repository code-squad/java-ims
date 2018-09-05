// $(".set_milestone_submit").on("click", setMilestone);
// $(document).on("click", "set_milestone_submit", setMilestone);
$("#addCommentSubmit").on("click", addComment);
// $(".buttons .update_comment").on("click", updateComment);
$(document).on("click", ".buttons .update_comment", updateComment);

function assign(e) {
    e.preventDefault();
    console.log("event : " + e + ", url : " + e.target.dataset.message);

    $.ajax({
        type: 'get',
        url: e.target.dataset.message,
        dataType: 'text',
        error: onError,
        success: function () {
            alert("지정되었습니다");
        }
    });
}

function addComment(e) {
    e.preventDefault();
    console.log("This is addComment");
    var queryString = $("#contents-form").serialize();
    console.log("queryString : " + queryString);

    var url = $("#contents-form").attr("action");
    console.log("url : " + url);

    $.ajax({
        type: 'POST',
        data: queryString,
        url: url,
        dataType: 'json',
        error: onError,
        success: function (data, status) {
            console.log(data);
            console.log(status);
            var commentTemplate = $("#commentTemplate").html();
            console.log(commentTemplate);
            var template = commentTemplate.format(data.writer.userId, data.contents);
            $("#comment-bottom").before(template);
            $("#contents").val("");
        }
    });
}

function updateComment(e) {
    var comment_contents = $(e.target).closest(".comment_area").find(".contents").text();
    console.log("contents : " + comment_contents);
}

function onError() {
    console.log("error detected");
}

// function onSuccess(data, status) {
//     console.log(data);
//     console.log(status);
//     var commentTemplate = $("#commentTemplate").html();
//     console.log(commentTemplate);
//     var template = commentTemplate.format(data.writer.userId, data.contents);
//     $("#comment-bottom").before(template);
//     $("#contents").val("");
// }

String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};