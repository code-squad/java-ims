$(".mdl-js-menu li").on("click", setIssueAttribute);

function setIssueAttribute(e) {
    e.preventDefault();

    var url = $(this).find('a').attr('href');

    $.ajax({
            type : 'get',
            url : url,
            error: onError,
            success : onSuccess
        });
}

function onError(jqXhr, textStatus, errorThrown) {
    var errorMessage = jqXhr.responseJSON.message;
    alert(errorMessage);
}
function onSuccess(data, textStatus, jqXhr) {
    alert("지정에 성공하였습니다!");
}
