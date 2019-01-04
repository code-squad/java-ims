$(".mdl-card__supporting-text button[type=submit]").click(login);

function login(e) {
    console.log("click login");
    e.preventDefault();

    var queryString = $("#login").serialize();
    console.log(queryString);

    var url = $('#login').attr("action");
    console.log(url);

    var json = new Object();
    json.userId = $('#userId').val();
    json.password = $('#password').val();

    console.log(JSON.stringify(json));

    $.ajax({
        type : 'post',
        url : url,
        data : JSON.stringify(json),
        contentType: 'application/json',
        dataType : 'json',
        error : function() {
            console.log("error");
            alert("아이디 또는 비밀번호가 다릅니다.")
        },
        success : function() {
            console.log("success");
            location.href = "/"
        }
    });
}