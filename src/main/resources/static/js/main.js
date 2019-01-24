console.log("main.js start");

/* 템플릿 추가 */
String.prototype.format = function() {
  var args = arguments;
       return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
         ? args[number]
             : match
        ;
  });
};

$("#login-submit").on("click", login);
$("#issue_update").on("click", issue_update);
$("#issues-menu-lower-right").on("click", issue_delete);
$("#milestone-menu").on("click", milestone_menu);
$("#label_menu").on("click", label_menu);
$(document).on("click", '.assignee', assignee);
$(document).on("click", '.register a', register);
$(".answer-write button[type=submit]").click(addAnswer);
$(".comments").on("click", ".delete-answer-form button[type=submit]", deleteAnswer);
$(".comments").on("click", ".link-modify-article", updateAnswerForm);
$(".file-upload").on("click", "button[type=submit]", fileUpload);

function login(e) {
    e.preventDefault();

    var queryString = {
        userId : $('#userId').val(),
        password : $('#password').val()
    };

    var strObject = JSON.stringify(queryString);
    console.log("strObject : " + strObject);


    var url = $('#loginForm').attr('action');

    console.log("url : " + url);

    $.ajax({
        type : 'post',
        url : url,
        data : strObject,
        contentType : 'application/json',
        dataType : 'json',
        error : onError,
        success : onSuccess
    })
}

function onError(xhr, status) {
    console.log('error');
    console.log(xhr);
    alert("아이디 또는 비밀번호가 다릅니다.");
}

function onSuccess(data, status, xhr) {
    console.log(data);
    console.log(xhr);
    window.location.href=xhr.getResponseHeader('Location');
}

function issue_update(e) {
    var url = $('#issue_update').attr('href');

    console.log("url : " + url);

    $.ajax({
        type : 'get',
        url : url,
        dataType : 'text',
        error : issue_update_onError
    })
}

function issue_delete(e) {
    e.preventDefault();
    var url = $("#issues-menu-lower-right").attr('href');

    console.log("delete url : " + url);

    $.ajax({
        type : 'delete',
        url : url,
        dataType : 'json',
        error : issue_update_onError,
        success : issue_delete_onSuccess
    })
}

function issue_update_onError(xhr, status) {
    console.log('error');
    console.log(xhr);
    alert("이슈 작성자와 로그인 아이디가 다릅니다.");
}

function issue_delete_onSuccess(data) {
    if(data.valid){
        console.log(data);
        window.location.href= "/";
    }
    else {
        alert(data.errorMessage);
    }
}

function milestone_menu(e) {
    e.preventDefault();
    var url = $("#milestone-menu").attr('value');
    var li_list = "";

    $.ajax({
        type : 'get',
        url : url,
        dataType : 'json',
        error : function(data) {
            console.log("milestone_에러");
        },
        success : function(data) {
            console.log("milestone_조회성공");
            console.log(data);
            $(data).each(function(index, value) {
                li_list += '<li class="mdl-menu__item mdl-button--accent register">' +
                    '<a href="' + url + '/' + value.id + '">' + value.subject + '</a></li>';
            });

            console.log(li_list);
            $("#milestone_menu_ul").html(li_list);
            $("#milestone-menu").unbind();
        }
    })
}

function label_menu(e) {
    e.preventDefault();
    var url = $("#label_menu").attr('value');
    var li_list = "";

    console.log("실행됨 라벨");
    $.ajax({
        type : 'get',
        url : url,
        dataType : 'json',
        error : function(data) {
            console.log("label_에러");
        },
        success : function(data) {
            console.log("label_조회성공");
            console.log(data);

            li_list += '<li class="mdl-menu__item">' +
                                '<a href="/labels">' + "라벨 추가" + '</a></li>';

            $(data).each(function(index, value) {
                console.log(value);
                li_list += '<li class="mdl-menu__item register">' +
                    '<a href="' + url + '/' + value.id + '">' + value.label + '</a></li>';
            });

            console.log(li_list);
            $("#label_menu_ul").html(li_list);
            $("#label_menu").unbind();
        }
    })
}

// 이슈에 마일스톤, 라벨, 담당자 적용
function register(e) {
    e.preventDefault();

    var url = $(this).attr('href');
    console.log("register");
    console.log(url);

    $.ajax({
        type : 'post',
        url : url,
        contentType : 'application/json',
        error : function(data) {
            console.log("register_에러");
            console.log(data);
            alert("에러! 이슈 작성자와 아이디가 일치하지 않습니다.");
        },
        success : function(data) {
            console.log("register_조회성공");
            console.log(data);
            alert("적용 되었습니다!");
        }
    })
}

function assignee(e) {
    e.preventDefault();

    var url = $(this).attr('href');
    console.log(url);

    $.ajax({
        type : 'post',
        url : url,
        contentType : 'application/json',
        error : function() {
            alert("에러! 이슈 작성자와 아이디가 일치하지 않습니다.");
        },
        success : function() {
            alert("적용 되었습니다!");
        }
    })
}

function addAnswer(e) {
    console.log("댓글 추가");
    e.preventDefault();

    var queryString = $(".answer-write").serialize();
    console.log("querystring : " + queryString);

    var url = $(".answer-write").attr("action");
    console.log("url : " + url);

    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error : function(xhr) {
            console.log("error");
            console.log(xhr);
        },
        success : function(data, status) {
            console.log(data);
            var answerTemplate = $("#answerTemplate").html();
            var template = answerTemplate.format(data.writer.userId, data.contents, data.formattedCreateDate, data.issue.id, data.id);

            console.log("data.formattedDate : " + data.formattedDate);

            $("#comments").append(template);
            $("textarea[name=comment]").val("");
        }
    });
}

function deleteAnswer(e){
    console.log("댓글 삭제");

    e.preventDefault();

    var deleteBtn = $(this);
    var url = $(this).parent('form').attr("action");
    console.log("url : " + url);

    $.ajax({
        type : 'delete',
        url : url,
        dataType : 'json',
        error : function(xhr) {
            console.log("answer Delete error");
            console.log(xhr);
        },
        success : function(data) {
            console.log(data);
            if (data.valid) {
                deleteBtn.closest(".comment").remove();
                console.log("댓글 삭제됨");
            } else {
                alert(data.errorMessage);
            }
        }
    });
}

function updateAnswerForm(e) {
    console.log("댓글 수정 Form");

    e.preventDefault();

    var url = $(this).attr("href");
    console.log("url : " + url);

    var targetComment = $(this).closest(".comment");

    $.ajax({
        type : 'get',
        url : url,
        dataType : 'json',
        error : function(xhr) {
            console.log("answer modifyForm error");
            console.log(xhr);
            alert("수정 권한이 없습니다.");
        },
        success : function(data) {
            console.log(data);
            var answerUpdateForm = $("#answerUpdate").html();
            var template = answerUpdateForm.format(data.issue.id, data.id, data.contents);
            targetComment.html(template);
            $("#updateAnswer").on("click", updateAnswer);
        }
    })
}

function updateAnswer(e) {
    console.log("댓글 업데이트");

    e.preventDefault();

    var url = $(this).parent('form').attr("action");
    console.log("url : " + url);

    var queryString = $(this).parent('form').serialize();
    var targetComment = $(this).closest(".comment");

    $.ajax({
        type : 'put',
        url : url,
        data : queryString,
        dataType : 'json',
        error : function(xhr) {
            console.log("update error!");
            console.log(xhr);
        },
        success : function(data) {
            console.log("업데이트 성공");
            var answerTemplate = $("#answerTemplate").html();
            var template = answerTemplate.format(data.writer.userId, data.contents, data.formattedCreateDate, data.issue.id, data.id);
            targetComment.replaceWith(template);
        }
    })
}

function fileUpload(e) {
    console.log("파일 업로드");
    e.preventDefault();

    var url = $(this).closest('form').attr('action');
    console.log("url : " + url);

    var form = $(this).closest('form')[0];
    console.log(form);

    var formData = new FormData(form);
    console.log("form : " + formData);

    $.ajax({
        type : 'post',
        url : url,
        data : formData,
        enctype : 'multipart/form-data',
        processData: false,
        contentType: false,
        error : function(xhr) {
            console.log("파일 업로드 에러");
            console.log(xhr);
        },
        success : function(data) {
            console.log("파일 업로드 성공");

            var answerTemplate = $("#answerTemplate").html();
            var template = answerTemplate.format(data.writer.userId, "<a href='/api/issues/" + data.issue.id + "/attachments/" + data.attachment.id + "'>" + data.attachment.originalFileName + "</a>", data.formattedCreateDate, data.issue.id, data.id);

            console.log("data.formattedDate : " + data.formattedDate);

            $("#comments").append(template);
            $("textarea[name=comment]").val("");
        }
    })
}

