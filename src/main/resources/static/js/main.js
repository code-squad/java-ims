
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
$(".new-comment button[type=submit]").on("click", addComment);
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

            $(".link-delete-comment").on("click", deleteAnswer);
        }
    });
}

//이슈 코멘트 삭제
$(".link-delete-comment").on("click", deleteAnswer);
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