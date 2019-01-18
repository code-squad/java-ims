$(".comments-write button[type='submit']").on('click', comment);
function comment(e) {
    e.preventDefault();
    console.log("커밋 생성중");

    var queryString = $(".comments-write").serialize();
    console.log(queryString);

    var url = $(".comments-write").attr("action");
    console.log(url);



    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        contentType : 'application/json; charset=euc-kr',
        error : function(xhr) {
            console.log(xhr);
            alert("답변 생성에 실패했습니다.");
        },
        success : function(data) {
            console.log(data);
            var commentHtml = $("#commentTemplate").html();
            var template = commentHtml.format(data.writer.name, data.comment, data.createDate, data.issue.id, data.id);
            $(".comments").append(template);
//            $(".article-util").on("click", ".delete-answer-form button[type='submit']", deleteAnswer);

            $(".delete-answer-button").on('click', deleteAnswer);
            $(".update-answer-button").on('click', updateAnswerShow);
            $("textarea[name=comment]").val("");
        }
    })
}
//
//$(".form-update").on('click', updateAnswer);
//function updateAnswer(e) {
//    e.preventDefault();
//    console.log('답글 업데이트');
//
//    var queryString = $('')
//}

//$(".article-util").on("click", ".delete-answer-form button[type='submit']", deleteAnswer);
$(".delete-answer-button").click(deleteAnswer);
function deleteAnswer(e) {
    var deleteBtn = $(this);

    e.preventDefault();
    console.log("답글 삭제 ajax");

    var url = $(".delete-answer-form").attr("action");
    console.log(url);

    $.ajax({
        type : 'delete',
        url : url,
        error : function(xhr) {
            console.log(xhr);
            alert("답변 삭제 실패했습니다.");
        },
        success : function() {

            console.log("답변성공");
//                $('header').remove();
            deleteBtn.closest(".mdl-color-text--grey-700").remove();

        }
    })
}

$(".update-answer-button").click(updateAnswerShow);
function updateAnswerShow(e) {
    e.preventDefault();
    console.log("업데이트 답변");

    var updateBtn = $(this).parent();
    var url = updateBtn.attr("href");
    console.log(url);
//    var json = new Object()'
//    json.name =  'sdsds';

    $.ajax({
        type : 'get',
        url : url,
        dataType : 'json',
//        data : JSON.stringify(json),
        contentType : 'application/json',
        error : function(xhr) {
            console.log(xhr);
            alert("답변 에러");
        },
        success : function(data) {
//            var answerTemplate = $("#update-answer-text-show").html();
//            var commentTemplate = answerTemplate.format(data.comment, data.issue.id, data.id);
//            $("update-answer-text-show").html();
             var answerTemplate = $("#answerUpdateTemplate").html();
             var template = answerTemplate.format(url,data.comment, data.id, data.issue.id);
             console.log(url);
             console.log(data.comment);
             console.log($(".comment__author").html())
             $("#answer-" + data.id).append(template);
             $("#update-answer-btn").on('click', updateAnswer);
        }
    })
}

$("#update-answer-btn").on('click', updateAnswer);
function updateAnswer(e) {
    e.preventDefault();
    console.log("되고 있는중");

    var deleteBtn = $(this);

    var url = $(this).parent().attr("action");
    console.log(url);

    var queryString = $("#update_comment").serialize();

    $.ajax({
        type : 'put',
        url : url,
        data : queryString,
        dataType : 'json',
        contentType : 'application/json',
        error : function(xhr) {
            console.log(xhr);
            alert("답글 확인 에러")
        },
        success : function(data) {
            console.log(data);
            var answerTemplate = $("#commentTemplate").html();
            var template = answerTemplate.format(data.writer.name, data.comment, data.createDate, data.issue.id, data.id);
            deleteBtn.closest(".comments").html(template);
            $(".delete-answer-button").on('click', deleteAnswer);
            $(".update-answer-button").on('click', updateAnswerShow);
        }
    })
}

String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};