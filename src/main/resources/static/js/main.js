
//템플릿 추가
String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

// 로그인
$(".submit-login div button[type=submit]").on("click", login);

function login(e) {
    console.log("로그인 버튼 작동");
    e.preventDefault();

    var queryString = $('.submit-login').serialize();       // 사용자가 입력한 id, password
    console.log("query : " + queryString);

    var url = $('.submit-login').attr('action');            // '/api/users/login'
    console.log("url : " + url);

    $.ajax({
        type : 'post',
        data : queryString,
        url : url,
        error : onError,
        success : onSuccess
    });
}

function onError(request,status,error){
        console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
}

function onSuccess (output, status, xhr) {
    location.href = xhr.getResponseHeader('Location');
}
//--------------------------------------------------------------------------------

//마일스톤 리스트
$("#milestone-menu").on('mouseenter', milestoneList);

function milestoneList(e) {
    e.preventDefault();
    console.log("버튼 작동");

    var url = $(this).attr('value');
    console.log(url);
    var li_list = "";

    $.ajax({
        type : 'get',
        url : url,
        dataType : 'json',
        error : function() {
                    alert('milestone이 존재 하지 않습니다.');
                },
        success : function(output) {
                      $.each(output,function(index, value) {
                            li_list +=  '<li class="mdl-menu__item mdl-button--accent register"><a href="'
                                        + url + '/' + value.id + '">' + value.milestoneDto.subject + '</a></li>';


                            console.log(li_list);
                      });

                      $("#milestone_menu_ul").html(li_list);
                      $("#milestone-menu").unbind();
                  }
    });
}

//------------------------------------------------------------------------
//마일스톤 지정
$(document).on("click",'.register',setAttribute);

function setAttribute(e) {
        e.preventDefault();

     var milestoneViewer="";

    var url = $(this).children('a').attr('href');
    console.log(url);

    $.ajax({
        type : 'get',
        url : url,
        error : function() {
            alert("로그인 하시기 바랍니다.");
        },
        success : function(output) {
        console.log(output);
          milestoneViewer = '<div class="section-spacer">' + output.milestone.milestoneDto.subject + '</div>';
          $(".section-spacer").html(milestoneViewer);
//          alert("적용되었습니다.")
            var milestoneComment = $("#issueProperty").html();
            var template = milestoneComment.format(output.user, output.milestone.milestoneDto.subject, output.milestone.formattedCreateDate);
            $("#comments").append(template);
        }
    });
}

//------------------------------------------------------
//댓글 달기
$(".submit-answer button[type=submit]").click(createAnswer);

function createAnswer(e) {
    console.log("댓글달기 버튼 작동");
    e.preventDefault();

    var queryString = $(".submit-answer").serialize();
    console.log("query : " + queryString);

    var url = $(".submit-answer").attr('action');
    console.log(url);

    $.ajax({
        type:'post',
        data : queryString,
        url : url,
        dataType : 'json',
        error : function(data) {
                      console.log(data);
                       $("textarea[name=comment").val("");
                      alert("error");
                  },
        success : function(output) {
            var answerCreateTemplate = $("#answerCreateTemplate").html();
            var template = answerCreateTemplate.format(output.answer.userDto.userId,
                                        output.answer.comment, output.formattedCreateDate, output.issue.id,output.id);
            $("#comments").append(template);
            $("textarea[name=comment").val("");
        }
    });
}

//---------------------------------------------
//댓글 수정
$("#comments").on("click",".edit-answer" ,editAnswer);

function editAnswer(e) {
    console.log("댓글 수정버튼 동작");
    e.preventDefault();

    var url = $(this).attr('href');
    console.log(url);
    var editTemplate = $(this).closest(".comment");

    $.ajax({
        type : 'get',
        url : url,
        dataType : 'json',
        error : function() {
            console.log('error');
        },
        success : function(data) {
            var answerEditTemplate = $("#commentEditTemplate").html();
            var answerEditForm = answerEditTemplate.format(url,data.answer.comment);
            editTemplate.html(answerEditForm);
            $(".comment-edit-ims").click(updateAnswer);
        }
    });

}

function updateAnswer(e) {
    console.log("댓글 수정버튼 등록!!");
    e.preventDefault();

    var url = $(this).parent('form').attr('action');
    console.log(url);

    var queryString = $("#updateAnswer").serialize();
    console.log(queryString);

    var targetComment = $(this).closest('.new-comment');

    $.ajax({
        type : 'put',
        url : url,
        data : queryString,
        dataType : 'json',
        error : function() {
            console.log('error');
        },
        success : function(output) {
            console.log('업데이트 성공');
            var answerCreateTemplate = $("#answerCreateTemplate").html();
            var template = answerCreateTemplate.format(output.answer.userDto.userId,
                                        output.answer.comment,output.formattedCreateDate, output.issue.id,output.id);
            targetComment.replaceWith(template);
        }
    });
}

$("#comments").on("click",".delete-btn" ,deleteComment);

function deleteComment(e) {
    console.log("답변 삭제버튼 실행")
    e.preventDefault();

    var deleteTmp = $(this);

    var url = $(this).parent('form').attr('action');
    console.log(url);

    $.ajax({
        type : 'delete',
        url : url,
//        dataType : 'json',
        error : function() {
            console.log('error');
        },
        success : function() {
            deleteTmp.closest('.comment').remove();
        }

    });

}





