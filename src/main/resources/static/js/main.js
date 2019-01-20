
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

$(".new-comment button[type=submit]").on("click", addComment);
function addComment(e) {
    e.preventDefault();

    var url = $(this).closest("form").attr("action");
    console.log("url : " + url);

    var comment = $("#comment").val();
    console.log(comment);

//    $.ajax({
//        type : 'post',
//        url : url,
//        data : queryString,
//        dataType : 'json',
//        error: function () {
//            alert("error");
//        },
//        success : function (data, status) {
//            console.log(data);
//            var answerTemplate = $("#answerTemplate").html();
//            var template = answerTemplate.format(data.writer.userId, data.formattedCreateDate, data.contents, data.question.id, data.id);
//            $(".qna-comment-slipp-articles").prepend(template);
//            $("textarea[name=contents]").val("");
//        }
//    });
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