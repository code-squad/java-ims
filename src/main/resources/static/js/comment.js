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
            alert("답변 생성에 성공했습니다.");
            var commentHtml = $("#commentTemplate").html();
            var template = commentHtml.format(data.writer.name, data.createDate, data.comment);
            $(".comments").append(template);
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

$(".article-util").on("click", ".delete-answer button[type ='subject']", deleteAnswer);
function deleteAnswer(e) {
    var deleteBtn = $(this).parent();

    e.preventDefault();
    console.log("답글 삭제 ajax");

    var url = $(".form-delete").attr("action");
    console.log(url);

    $.ajax({
        type : 'delete',
        url : url,
        dataType : 'json',
        error : function(xhr) {
            console.log(xhr);
            alert("답변 삭제 실패했습니다.");
        },
        success : function(data) {
            console.log(data);
            if(data.valid) {
                deleteBtn.closest('header').remove();
            }
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