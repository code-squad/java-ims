String.prototype.format = function() {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function(match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};

//답변기능

$("#checkButton").on('click', addAnswer);

function addAnswer(e) {
    console.log("add answer button click");

    // 페이지 넘어가는 이벤트 방지
    e.preventDefault();

    var queryString = $("#comment").serialize();
    console.log("query : " + queryString);

    var url = $("#answerForm").attr("action");
    console.log("url : " + url);

    $.ajax({
        type: 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error : onError,
        succuess : onSuccess});
}

function onError() {
    console.log("error");
}

function onSuccess(data, status) {
    // data는 answer데이터
    console.log("success");
    console.log(data);
    var answerTemplate = $("#commentTemplate").html();
    var template = commentTemplate.format(data.writer.userId, data.formattedCreateDate, data.contents);
    $(".comment.mdl-color-text--grey-700").prepend(template);
    //comment mdl-color-text--grey-700
    $("textarea[name=comment]").val("");
}