$("#addCommentSubmit").on("click", addComment);
$(document).on("click", ".buttons .update_comment", updateFormSetting);

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
    console.log("addComment is called");

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

            // template 불러오기
            var commentTemplate = $("#commentTemplate").html();

            // ajax를 통해 응답으로 넘어온 json에서 필요한 데이터를 template으로 넘김
            var template = commentTemplate.format(data.writer.userId, data.contents, data.id);

            $("#comment-bottom").before(template);
            $("#contents").val("");
        }
    });
}

function updateFormSetting(e) {
    e.preventDefault();
    // commentAddForm 제거
    $("#contents-form").remove();

    // commentUpdateForm 추가
    $("#comment-form-parent").append($("#commentUpdateForm").html());

    // 이전에 추가된 update_contents 클래스 제거
    $(".update_contents").removeClass("update_contents");

    // 수정 버튼을 누른 댓글에 update_contents 클래스 추가
    $(e.target).closest(".comment_area").find(".contents").addClass("update_contents");

    var comment_contents = $(e.target).closest(".comment_area").find(".contents").text();
    console.log("contents : " + comment_contents);

    var id = $(e.target).closest(".comment_area").find(".id").attr("value");
    console.log("commentId : " + id);

    // bug : toggle로만 동작하면 수정버튼을 연속 2번 누를 경우 updateCommentSubmit이 아닌 addCommentSubmit으로 다시 바뀐다
    $("#addCommentSubmit").toggle();
    $("#updateCommentSubmit").toggle();

    var path = window.location.pathname; // path : /issue/1
    var index = path.lastIndexOf('/');
    var issueId = path.substring(index+1); // issueId : 1
    console.log("issueId : " + issueId);

    var updateRequestUrl = "/api/issues/"+ issueId +"/comments/"+id;
    console.log("update request url : " + updateRequestUrl);

    $("#updateCommentSubmit").attr("data-message", updateRequestUrl);
    $("#contents-form").attr("action", updateRequestUrl);

    // 동적으로 코멘트를 추가하고 수정 버튼을 눌렀을 떄, textarea에 수정할 코멘트 내용을 세팅하기 위해 .text를 사용하면 세팅이 안 된다. val을 사용했을 때에는 된다.
    // $("#contents").text(comment_contents);
    $("#contents").val(comment_contents);
}

function updateComment(e) {
    e.preventDefault();
    console.log("updateComment is called");

    var url = $("#updateCommentSubmit").data("message");
    console.log("update url : " + url);

    var queryString = $("#contents-form").serialize();
    console.log("queryString : " + queryString);

    $.ajax({
        type: 'PUT',
        data: queryString,
        url: url,
        dataType: 'json',
        error: onError,
        success: function (data, status) {
            console.log(data);
            console.log(status);
            $(".update_contents").text(data.contents);
            console.log(data.contents);
            $("#contents").val("");
        }
    });
}

function onError() {
    console.log("error detected");
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