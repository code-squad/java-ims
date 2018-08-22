// $(".set_milestone_submit").on("click", setMilestone);
// $(document).on("click", "set_milestone_submit", setMilestone);

function assign(e) {
    e.preventDefault();
    console.log("event : " + e + ", url : " + e.target.dataset.message);

    $.ajax({
        type : 'get',
        url : e.target.dataset.message,
        dataType : 'text',
        error : onError,
        success : function(){
            alert("지정되었습니다");
        }
    });
}

function onError() {
    console.log("error detected");
}

function onSuccess(data, status) {
    console.log(data);
    console.log(status);
    // var answerTemplate = $("#answerTemplate").html();
    // var template = answerTemplate.format(data.writer.userId, data.formattedCreateDate, data.comment, data.question.id, data.id);
    // $(".answer-write").before(template);
    // $(".answer-write textarea").val("");
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