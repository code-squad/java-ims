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

/* 뒤로가기 */
$('.back-btn').on('click',function(){history.back();})

/* 마일스톤 select box load */
$('#milestone-menu').click(showMilestone);
function showMilestone(e) {
    e.preventDefault();
    e.stopImmediatePropagation();
    var events = $._data($('#milestone-menu'), 'events');
    console.log(events);

    console.log("call show Milestone!");
    var url = $('#select-milestone').attr('value');
    console.log(url);
    var li = '';
    $.ajax({
        type : 'get',
        url : url,
        dataType : 'json',
        error : function (request) {
            console.log("마일스톤 목록 조회 실패");
            console.log(request);
        },
        success : function (response) {
            console.log("마일스톤 목록 조회 성공!");
            $.each(response, function(index, value) {
                li += '<li class="mdl-menu__item" value="' + url + '/' + value.id + '">'
                    + value.subject + '</li>';
            });
            console.log(li);
            $("#select-milestone").html(li);
            $('#milestone-menu').click();
            $('#milestone-menu').unbind();
        }
    });
}

/* 라벨 select box load */
$('#label-menu').click(showLabel);
function showLabel(e) {
    e.preventDefault();
    console.log("call show Label!");
    var url = $('#select-label').attr('value');
    console.log(url);
    var li = '';
    $.ajax({
        type : 'get',
        url : url,
        dataType : 'json',
        error : function (request) {
            console.log("라벨 목록 조회 실패");
            console.log(request);
        },
        success : function (response) {
            console.log("라벨 목록 조회 성공!");
            $.each(response, function(index, value) {
                li += '<li class="mdl-menu__item" value="' + url + '/' + value.id + '">'
                    + value.subject + '</li>';
            });
            $("#select-label").html(li);
            $('#select-menu').unbind();

        }
    });
}

/* 담당자 select box load */
$('#assignee-menu').click(showAssignee);
function showAssignee(e) {
    e.preventDefault();
    console.log("call show Assignee!");
    var url = $('#select-assignee').attr('value');
    console.log(url);
    var li = '';
    $.ajax({
        type : 'get',
        url : url,
        dataType : 'json',
        error : function (request) {
            console.log("담당자 목록 조회 실패");
            console.log(request);
        },
        success : function (response) {
            console.log("담당자 목록 조회 성공!");
            $.each(response, function(index, value) {
                li += '<li class="mdl-menu__item" value="' + url + '/' + value.id + '">'
                    + value.userId + '</li>';
            });
            $("#select-assignee").html(li);
            $('#assignee-menu').unbind();

        }
    });
}
/* 댓글 수정 창 생성 */
$('.showUpdateAnswerBtn').click(showAnswer);
function showAnswer(e) {
    e.preventDefault();
    var parent = $(this);
    var url = parent.attr('href');

    $.ajax({
        type : 'get',
        url : url,
        dataType : 'json',
        error : function (request) {
            console.log("댓글 수정 창 실패");
            exceptionProcessor(request);
        },
        success : function (response) {
            console.log("댓글 수정 창 성공!");
            var answerTemplate = $('#update-answer-template').html();
            var template = answerTemplate.format(url, response.comment);
            parent.closest('div div').append(template);
            $('.updateAnswerBtn').click(updateAnswer);
        }
    });
}

/* 댓글 수정 */
function updateAnswer(e) {
    e.preventDefault();
    var parent = $(this);
    var url = parent.closest('form').attr('action');
    console.log(url);

    var json = new Object();
    json.comment = $('#updateComment').val();

    $.ajax({
        type : 'put',
        url : url,
        data : JSON.stringify(json),
        dataType : 'json',
        contentType: 'application/json',
        error : function (request) {
            console.log("댓글 수정 실패");
            console.log(request);
            exceptionProcessor(request);
        },
        success : function (response) {
            console.log("댓글 수정 성공!");
            parent.closest('#answerDiv').html('');
            console.log(response);
            $('.answer-' + response.id).html(response.comment);
        }
    });
}

/* 댓글 삭제 */
$('.deleteAnswerBtn').click(deleteAnswer);
function deleteAnswer(e) {
    e.preventDefault();
    parent = $(this);
    var url = parent.attr('href');

    $.ajax({
        type : 'delete',
        url : url,
        contentType: 'application/json',
        error : function (request) {
            console.log("댓글 삭제 실패");
            console.log(request);
            exceptionProcessor(request);
        },
        success : function (response) {
            console.log("댓글 삭제 성공!");
            console.log(response);
            $('.article-' + response.id).html('');
        }
    });
}

/* 댓글 등록 */
$('#writeAnswerBtn').click(writeAnswer);
function writeAnswer(e) {
    e.preventDefault();
    parent = $(this);
    var url = parent.closest('form').attr('action');
    console.log(url);

    var json = new Object();
    json.comment = $('#comment').val();

    $.ajax({
        type : 'post',
        url : url,
        data : JSON.stringify(json),
        dataType : 'json',
        contentType: 'application/json',
        error : function (request) {
            console.log("댓글 작성 실패");
            console.log(request);
            exceptionProcessor(request);
        },
        success : function (response) {
            console.log("댓글 작성 성공!");
            console.log(response);
            var answerTemplate = $('#write-answer-template').html();
            var template = answerTemplate.format(response.id, response.writer.userId, response.comment, url);
            $('#answers').append(template);
            $('.showUpdateAnswerBtn').click(showAnswer);
            $('#comment').html('');
        }
    });
}

/* 이슈등록 */
$('#issue-submit').click(createIssue);

function createIssue(e) {
    e.preventDefault();
    console.log("Call createIssue Method()");

    var url = '/api/issues';
    var json = new Object();
    json.comment = $('#comment').val();
    json.subject = $('#subject').val();
    console.log(json);

    $.ajax({
        type : 'post',
        url : url,
        data : JSON.stringify(json),
        dataType : 'json',
        contentType: 'application/json',
        error : function (request) {
            console.log("이슈등록 실패");
            exceptionProcessor(request);
        },
        success : function () {
            console.log("이슈등록 성공!");
            location.href = '/';
        }
    });
}

/* 로그인 */
$('#login-submit').click(login);

function login(e) {
    e.preventDefault();
    console.log("Call login Method()");

    var json = new Object();
    json.userId = $('#userId').val();
    json.password = $('#password').val();
    var url = '/api/user/login';

    $.ajax({
        type : 'post',
        url : url,
        data : JSON.stringify(json),
        dataType : 'json',
        contentType : 'application/json',
        error : function (request) {
            console.log("로그인 실패");
            exceptionProcessor(request);
        },
        success : function (data, status, xhr) {
            console.log("로그인 성공!");
            location.href = xhr.getResponseHeader('Location');
        }
    });
}

/* 회원가입 */
$('#join-submit').click(join);

function join(e) {
    e.preventDefault();
    console.log("Call join Method()");

    var url = '/api/user';
    var json = new Object();
    json.userId = $('#userId').val();
    json.name = $('#name').val();
    json.password = $('#password').val();

    var data = new FormData();
    data.append('user', new Blob([JSON.stringify(json)], {type : "application/json"}));
    data.append('file', $('input[name=pic]')[0].files[0])

    $.ajax({
        type : 'post',
        url : url,
        data : data,
        dataType : 'json',
        contentType: false,
        processData : false,
        error : function (request) {
            console.log("회원가입 실패");
            console.log(request);
            //exceptionProcessor(request);
        },
        success : function (data, status, xhr) {
            console.log("회원가입 성공!");
            location.href = '/';
        }
    });
}


/* 개인정보수정 */
$('#edit-privacy-submit').click(editPrivacy);

function editPrivacy(e) {
    e.preventDefault();
    console.log("Call editPrivacy Method()");

    var url = '/api' + $('#edit-privacy').attr('action');
    console.log(url);

    var json = new Object();
    json.userId = $('#userId').val();
    json.name = $('#name').val();
    json.password = $('#password').val();

    $.ajax({
        type : 'put',
        url : url,
        data : JSON.stringify(json),
        dataType : 'json',
        contentType: 'application/json',
        error : function (request) {
            exceptionProcessor(request);
        },
        success : function (data, status, xhr) {
            console.log("개인정보수정 성공!");
            location.href = xhr.getResponseHeader('Location');
        }
    });
}

/* 이슈삭제 */
$('#issues-menu-lower-right').click(deleteIssue);

function deleteIssue(e) {
    e.preventDefault();
    console.log("Call deleteIssue Method()");

    var url = $('#issues-menu-lower-right').attr('href');
    console.log(url);

    $.ajax({
        type : 'delete',
        url : url,
        contentType: 'application/json',
        error : function (request) {
            console.log("이슈삭제 실패");
            exceptionProcessor(request);
        },
        success : function () {
            console.log("이슈삭제 성공!");
            location.href = '/';
        }
    });
}

/* 이슈수정 화면 이동 */
$('#issues-menu-lower-left').click(goUpdateForm);

function goUpdateForm(e) {
    e.preventDefault();
    console.log("Call goUpdateForm Method()");

    var url = $('#issues-menu-lower-left').attr('href');
    console.log(url);

    $.ajax({
        type : 'get',
        url : url,
        dataType : 'text',
        contentType: 'application/json',
        error : function (request) {
            console.log("이슈 수정화면이동 실패");
            exceptionProcessor(request);
        },
        success : function (data) {
            console.log("이슈수정화면이동 성공!");
            alert(data);
            location.href = data;
        }
    });
}

/* 이슈수정 */
$('#issue-update-submit').click(updateIssue);

function updateIssue(e) {
    e.preventDefault();
    console.log("Call updateIssue Method()");

    var url = $('#issue').attr('action');
    console.log(url);
    var json = new Object();
    json.comment = $('#comment').val();
    json.subject = $('#subject').val();
    console.log(json);

    $.ajax({
        type : 'put',
        url : url,
        data : JSON.stringify(json),
        dataType : 'json',
        contentType: 'application/json',
        error : function (request) {
            console.log("이슈수정 실패");
            exceptionProcessor(request);
        },
        success : function () {
            console.log("이슈수정 성공!");
            location.href = '/';
        }
    });
}

/* 라벨, 담당자, 마일스톤 등록 */
$(document).on("click", '.mdl-menu__item', register);
function register(e) {
    e.preventDefault();

    var url = $(this).attr('value');
    console.log(url);

    var selected = $(this).attr('value');
    parent = $(this).closest('ul').children();

    $.ajax({
        type : 'post',
        url : url,
        contentType: 'application/json',
        error : function (request) {
            console.log("등록 실패");
            exceptionProcessor(request);
        },
        success : function () {
            console.log("등록 성공!");
            $.each(parent, function( index, value ) {
                console.log(value);
                if(value.attributes.value.nodeValue != selected) {
                    value.remove();
                }
            });
        }
    });
}

/* 예외처리 */
function exceptionProcessor(request) {
    if(request.status == '403') {
        if(request.responseText != null) {
            alert(request.responseText)
        }
        if(request.responseText == null) {
            location.href = '/user/login';
        }
    }

    if(request.status == '401') {
        alert(request.responseText);
    }

    if(request.status == '400') {
        alert(request.responseText);
    }

    if(request.status == '500') {
        alert(request.responseText);
    }
}