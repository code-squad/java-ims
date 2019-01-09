console.log("main.js start");

$("#login-submit").on("click", login);
$("#issue_update").on("click", issue_update);
$("#issues-menu-lower-right").on("click", issue_delete);
$("#milestone-menu").on("click", milestone_menu);
function login(e) {
    e.preventDefault();

    var queryString = $('#loginForm').serialize();
    var url = $('#loginForm').attr('action');

    console.log("url : " + url);

    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
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
                console.log("에러");
            },
            success : function(data) {
                console.log("성공");
                console.log(data);
                $(data).each(function(index, value) {
                    console.log(value);
                    console.log(value.id);
                    console.log(value.subject);
                    li_list += '<li class="mdl-menu__item mdl-button--accent">' +
                        '<a href="' + url + '/' + value.id + '">' + value.subject + '</a></li>';
                });

                console.log(li_list);
                $("#milestone_menu").html(li_list);
            }
        })
}


