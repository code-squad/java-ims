console.log("main.js start");
$("#login-submit").on("click", login);

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
    console.log(xhr)
    alert("아이디 또는 비밀번호가 다릅니다.");
}

function onSuccess(data, status, xhr) {
    console.log(data);
    console.log(xhr);
    window.location.href=xhr.getResponseHeader('Location');
}