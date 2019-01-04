/* 뒤로가기 */
$('.back-btn').on('click',function(){history.back();})

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
        error : function (data, xhr) {
            console.log("이슈등록 실패");
            alert(data.responseJSON.message);
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

    var queryString = $('#login').serialize();
    console.log(queryString);
    var url = '/api/user/login';

    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error : function (data, xhr) {
            console.log("로그인 실패");
            alert(data.responseJSON.message);
        },
        success : function (data, status, xhr) {
            console.log("로그인 성공!");
            location.href = xhr.getResponseHeader('Location');
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
        error : function (data, xhr) {
            console.log("개인정보수정 실패");
            alert(data.responseJSON.message);
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
        error : function () {
            console.log("이슈삭제 실패");
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
        error : function (data, xhr, status) {
            console.log("이슈 수정화면이동 실패");
            alert(data.responseText.message);
        },
        success : function (data) {
            console.log("이슈수정화면이동 성공!");
            console.log(data);
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
        error : function (data, xhr) {
            console.log("이슈수정 실패");
            console.log(data);
            alert(data.responseText.message);
        },
        success : function () {
            console.log("이슈수정 성공!");
            location.href = '/';
        }
    });
}