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
//댓글 생성
function onSuccess(data, status) {
    console.log(data);
    console.log(status);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.userId, data.comment, data.formattedCreateDate, data.issue.id, data.id);
    $("#comment_list").append(template);

    $(".updateFrom_answer").unbind("click").on("click", updateFormAnswer);
    $(".submit-write-delete button[type=submit]").unbind("click").on("click", deleteAnswer);
    $("textarea[name=comment]").val("");

}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// 업데이트 폼생성 에이젝스
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
                console.log('error');
            },
            success : function(response) {
                var answerTemplate = $("#answerUpdateTemplate").html();
                var template = answerTemplate.format(url,response.comment, response.id)
                updateBtn.closest(".comment__author").append(template);
                $(".submit-write-update button[type=submit]").unbind("click").on("click", updateAnswer);
            }
        });


}

// 업데이트 성공
$(".submit-write-update button[type=submit]").on("click", updateAnswer);
function updateAnswer(e) {
    console.log("업데이트")
    e.preventDefault();
    var updateFormBtn = $(this);
    var queryString = $(".submit-write-update").serialize();
    var url = $(".submit-write-update").attr("action");
    console.log(url);
    console.log(queryString);

    $.ajax({
            type : 'put',
            url : url,
            data : queryString,
            dataType : 'json',
            error: function(request) {
                console.log('error');
                console.log(request);
            },
            success : function(data) {
                console.log(data)
                var answerTemplate = $("#answerTemplate").html();
                var template = answerTemplate.format(data.userId, data.comment, data.formattedCreateDate, data.issue.id, data.id);
                updateFormBtn.closest('#answer_' + data.id).html(template);
            }
        });


}

// 삭제
$(".submit-write-delete button[type=submit]").on("click", deleteAnswer);
function deleteAnswer(e) {
    console.log('딜리트')
    e.preventDefault()
    var deleteBtn = $(this)
    var url =$('.submit-write-delete').attr('action')
    console.log(url)
    $.ajax({
                type : 'delete',
                url : url,
                dataType : 'json',
                error: function(request) {
                    console.log('error');
                    console.log(request);
                },
                success : function(data) {
                    console.log(data)
                    deleteBtn.closest('.answer_form').remove();
                }
            });
}



$('#milestone-menu').on('mouseenter',getMilestone)
function getMilestone(e) {
    e.preventDefault()
    console.log('milestone')
    console.log(window.location.href)
    var milestoneMenuBtn = $(this)
    var id =$('#select-milestone');
    console.log('id1: {}',id)
    $.ajax({
                    type : 'get',
                    url : '/api/issue/1//milestones',
                    dataType : 'json',
                    error: function(request) {
                        console.log('error');
                        console.log(request);
                    },
                    success : function(data) {
                        console.log(data)

                        var template = '';
                        var menuTemplate = $("#menuTemplate").html();
                        Object.keys(data).forEach(function(k){
                            template += menuTemplate.format(1, data[k].id, data[k].subject)
                        });
                        console.log(template);
                        console.log(id);
                        id.html(template);
                        $('#milestone-menu').unbind();
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