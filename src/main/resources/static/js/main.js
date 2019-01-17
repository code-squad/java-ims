
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
        dataType : 'json',
        error : onError,
        success : onSuccess
    });
}

function onError() {
    alert("아이디 또는 비밀번호가 틀립니다.");
}

function onSuccess(data,status,xhr) {
    location.href = xhr.getResponseHeader('Location');
}
//--------------------------------------------------------------------------------
$("#milestone-menu").on("click", milestoneList);

function milestoneList(e) {
    console.log("버튼 작동");
    e.preventDefault();

    var url = $("#milestone-menu").attr('value');
    var list = "";

    $.ajax({
        type : 'get',
        url : url,
        dataType : 'json',
//        error : onError,
        success : OnSuccess
    });
}

//function onError() {
//    alert("milestone이 존재 하지 않습니다.");
//}

function OnSuccess(data) {
    $(data).each(function(index, value) {
        li_list += '<a href="' + url + '/' + value.id
                + '"><li class="mdl-menu__item">'
                + value.milestoneDto.subject
                + '</li></a>';
    });

    console.log(li_list);
    $("#milestone_menu_ul").html(li_list);
    $("#milestone-menu").unbind();
}
