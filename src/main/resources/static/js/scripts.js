// 댓글쓰기 ajax
$(".submit-write button[type=submit]").on("click", addAnswer);
function addAnswer(e) {
    e.preventDefault();

    var queryString = $(".submit-write").serialize(); //form data들을 자동으로 묶어준다.
    console.log("query : "+ queryString);

    var url = $(".submit-write").attr("action");
    console.log("url : " + url);

    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error: onError,
        success : onSuccess
    });
}

function onError(request) {
    console.log(request);
    console.log('error');

}
function onSuccess(data, status) {
    console.log(data);
    console.log(status);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.userId, data.comment, data.formattedCreateDate, data.issue.id, data.id);
    $("#comment_list").append(template);
    $(".updateFrom_answer").unbind("click").on("click", function(e){
        e.preventDefault();
        var updateBtn = $(this);
        var answerTemplate = $("#answerUpdateTemplate").html();
        console.log(answerTemplate)
        console.log(updateBtn.closest(".comment mdl-color-text--grey-700"))
        updateBtn.closest(".comment__author").append(answerTemplate);
    });
    $("textarea[name=comment]").val("");

}

$(".updateFrom_answer").on("click", updateFormAnswer);
function updateFormAnswer(e) {
    console.log("업데이트폼 생성")
    e.preventDefault();

    var updateBtn = $(this);
    var url = updateBtn.attr('href');
    console.log(url);
    console.log(updateBtn);

    $.ajax({
            type : 'get',
            url : url,
            dataType : 'json',
            error: function() {
                console.log(request);
                console.log('error');
            },
            success : function() {
                var answerTemplate = $("#answerUpdateTemplate").html();
                console.log(answerTemplate)
                console.log(updateBtn.closest(".comment mdl-color-text--grey-700"))
                updateBtn.closest(".comment__author").append(answerTemplate);
            }
        });


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