
//이슈 열림/닫힘 설정
$("#close-button").on("click", changeStatus);
function changeStatus(e) {
    e.preventDefault();

    var url = $("#close-button").attr("href");
    console.log("url : " + url);

    $.ajax({
            type : 'get',
            url : url,
            dataType : 'json',
            error: onError,
            success : function(data) {
                console.log(data);
                if(data.closed) {
                    $("#close-button").html('OPEN ISSUE');
                } else {
                    $("#close-button").html('CLOSE ISSUE');
                }
            }
        });
}

//이슈 속성 지정(마일스톤/라벨/담당자)
$(".property-list").on("click", relateProperty);
function relateProperty(e) {
    e.preventDefault();

    var url = $(this).attr("href");
    console.log("url : " + url);

    $.ajax({
            type : 'get',
            url : url,
            dataType : 'json',
            error: onError,
            success : function(data) {
                console.log('success');
            }
        });
}

//이슈 코멘트 등록
$(document).on("click", ".new-comment button[type=submit]", addComment);
function addComment(e) {
    e.preventDefault();

    var url = $(this).closest("form").attr("action");
    console.log("url : " + url);

    var queryString = $("#comment").serialize();
    console.log(queryString);

    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        error: onError,
        success : function (data, status) {
            console.log(data);
            var commentTemplate = $("#commentTemplate").html();
            var template = commentTemplate.format(data.writer.userId, data.comment, data.issue.id, data.id);
            $(".comments").prepend(template);
            $("textarea[name=comment]").val("");
        }
    });
}

//이슈 코멘트 삭제
$(document).on("click", ".link-delete-comment", deleteAnswer);
function deleteAnswer(e) {
    e.preventDefault();

    var deleteBtn = $(this);
    var url = $(this).attr("href");
    console.log("url : " + url);

    $.ajax({
        type : 'delete',
        url : url,
        error : onError,
        success : function() {
            deleteBtn.closest("comment").remove();
        }
    });
}

//이슈 코멘트 업데이트 폼 열기
$(document).on("click", ".link-modify-comment", updateCommentForm);
function updateCommentForm(e) {
    e.preventDefault();

    var target = $(this);

    var url = $(this).attr("href");
    console.log("url : " + url);

    $.ajax({
        type : 'get',
        url : url,
        error: onError,
        success : function (data) {
            console.log(data);
            var commentUpdateTemplate = $("#commentUpdateTemplate").html();
            var template = commentUpdateTemplate.format(url, data.comment);
            target.closest("comment").append(template);

            $(document).on("click", ".modify-comment button[type=submit]", updateComment);
        }
    });
}

//이슈 코멘트 업데이트
$(document).on("click", ".modify-comment button[type=submit]", updateComment);
function updateComment(e) {
    console.log("click updateAnswer");
    e.preventDefault();

    var submitBnt = $(this);

    var url = $(this).closest("form").attr("action");
    console.log("url : " + url);

    var data = $("textarea[name=modified-comment]").val();
    var allData = { "comment": data};

    $.ajax({
        type : 'put',
        url : url,
        data : allData,
        error: onError,
        success : function (data, status) {
            console.log(data);

            var commentTemplate = $("#commentTemplate").html();
            var template = commentTemplate.format(data.writer.userId, data.comment, data.issue.id, data.id);
            submitBnt.closest("comment").html(template);
        }
    });
}

function onError() {
    console.log('error');
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