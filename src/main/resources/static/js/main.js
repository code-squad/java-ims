
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
    console.log("로긴 버튼 작동");
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
        success : onSuccess,
    });
}

function onError() {
    alert("아이디 또는 비밀번호가 틀립니다.");
}

function onSuccess(data,status,xhr) {
    location.href = xhr.getResponseHeader('Location');
}
