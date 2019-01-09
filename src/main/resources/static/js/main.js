$("#login-submit").on("click", login);

function login(e) {
    e.preventDefault();

    var queryString = $("#login").serialize();
    console.log("query : " + queryString);

    var url = '/api/users/login';

    $.ajax ({
    type : 'post' ,
    url : url,
    data : queryString,
    dataType : 'json',
    error : function(xhr, status) {
        console.log(xhr);
        alert('아이디 또는 비밀번호가 다릅니다.');
    },
    success : function(data, status, xhr) {
        console.log(xhr);
        console.log(data);
        location.href = xhr.getResponseHeader('Location');

    }
    });
}

