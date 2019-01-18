
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

// 로그인
$(".submit-login div button[type=submit]").on("click", login);

function login(e) {
    console.log("로그인 버튼 작동");
    e.preventDefault();

    var queryString = $('.submit-login').serialize();       // 사용자가 입력한 id, password
//    console.log("query : " + queryString);

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

function onError(request,status,error){
        console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
}

function onSuccess(xhr) {
    console.log(xhr);
    window.location.href = xhr.getResponseHeader('Location');
}
//--------------------------------------------------------------------------------

//마일스톤 리스트
$("#milestone-menu").on("mouseenter", milestoneList);

function milestoneList(e) {
    console.log("버튼 작동");
    e.preventDefault();

    var url = $("#milestone-menu").attr('value');
    console.log(url);
    var list = "";

    $.ajax({
        type : 'get',
        url : url,
        dataType : 'json',
        error : function() {
                    alert("milestone이 존재 하지 않습니다.");
                },
        success : function(data) {
                      $.each(data, function(index, value) {
                          list += '<a href="' + url + '/' + value.id
                                  + '"><li class="mdl-menu__item">'
                                  + value.milestoneDto.subject
                                  + '</li></a>';
                      });

                      console.log(list);
                      $("#milestone_menu_ul").html(list);
                      $("#milestone_menu").click();
                      $("#milestone-menu").unbind();
                  }
    });
}

//------------------------------------------------------------------------
//마일스톤 지정
//$(".mdl-menu__item").on("click", milestone);
//
//function milestone(e) {
//    e.preventDefault();
//
//    var url = $(".mdl-menu__item").parent().attr("href");
//
//
//    $.ajax({
//        type : 'get',
//        url : url,
//        dataType : 'json',
//        error : function() {
//            console.log('error');
//        }
//        success : function(data) {
//            console.log(data);
//
//        }
//    });
//}


