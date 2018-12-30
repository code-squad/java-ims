/* 뒤로가기 */
$('.back-btn').on('click',function(){history.back();})

/* 이슈등록 */
$('button[type=submit]').click(createIssue);

function createIssue(e) {
    e.preventDefault();
    console.log("Call createIssue Method()");

    var queryString = $('#issue').serialize();
    console.log(queryString);
    var url = '/api/issues';

    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error : function (data, xhr) {
            console.log("이슈등록 실패");
            alert(data.responseJSON.message);
        },
        success : function (data, status, xhr) {
            console.log("이슈등록 성공!");
            location.href = xhr.getResponseHeader('Location');

        }
    });
}