console.log("main.js 시작");

//회원가입
$("#join-submit").click(join);

function join(e){
    e.preventDefault();

//    var queryString = $("#join").serializeObject();   //form태그의 id(join)에서 받은 데이터를 직렬화(키,밸류 형태) string타입
//    console.log("query : " + queryString);

    var json = new Object();
    json.userId = $("#userId").val();
    json.name = $("#name").val();
    json.password = $("#password").val();
    console.log("json : " + json);
    console.log("json.name :" + json.name);
    console.log("JSON.stringify(json) : " + JSON.stringify(json));

    var url = $("#join").attr("action");
    console.log("url : " + url);

    //서버로 데이터 전송
    $.ajax({
        type : "post",
        url : url,
//        data : JSON.parse(json),        //JSON.parse() --> string 객체를 json 객체로
        data : JSON.stringify(json),    //JSON.stringify() --> json 객체를 string 객체로
        dataType : "json",              //서버로부터 받은 데이터 결과, 서버의 리턴값이 ResponseEntity<Void>라면 바디에 데이터가 없으므로, 400에러 발생
        contentType : "application/json", //얘 안하면 415에러
//        error : function(jqXHR, status, errorThrown){
//            console.log("jqXHR status: " + jqXHR.status);
//            console.log("jqXHR responseTest :" + jqXHR.responseTest);
//            console.log(jqXHR.getResponseHeader);
//            console.log("status : " + status);
//            console.log("join fail!");
//            alert("User Id, Name은 3자 이상, Password는 6자 이상입니다."); //size 예외처리는 security 에서 관리 하지 않는데,, 어떻게 메세지 전달을 할 수 있을까?
//        },
        error : onError,
        success : function(data, status, xhr){
            console.log(status);
            console.log(data);
            location.href = xhr.getResponseHeader("location");        //얘 역할을 정확히 모르겠음..
        }
    });
}

//로그인
$("#login-submit").click(login);

function login(e) {
    e.preventDefault();
    var json = new Object();
    json.userId = $("#userId").val();
    json.password = $("#password").val();
    var url = $("#login").attr("action");

    $.ajax({
        type : "post",
        url : url,
        data : JSON.stringify(json),
        dataType : "json",
        contentType : "application/json",
        error : onError,
        success : function(data, status, jqXHR) {
            console.log(data);
            location.href = jqXHR.getResponseHeader("location");
        }
    })
}

//이슈 속성 추가
$("ul.mdl-menu--bottom-right li").click(setIssueAttribute);

function setIssueAttribute(e) {
    e.preventDefault();
    console.log("함수 작동");
    var url = $(this).children('a').attr("href");
    console.log("url : " + url);

    $.ajax({
        type : "get",
        url : url,
        dataType : "json",
        error : onError,
        success : function(data, status, jqXHR) {
            if(data.subject) {
                alert("해당 이슈에 '" + data.subject + "' 이(가) 지정되었습니다.");     //어떤 속성이 적용 되었는지까지 메세지가 나오면 좋을 듯
            }
            if(data.name) {
                alert("해당 이슈에 '" + data.name + "' 이(가) 지정되었습니다.");
            }
        }
    })

}


//답변 추가
$("#write-comment-btn").click(addComment);

function addComment(e){
    e.preventDefault();
    var json = new Object();
    json.contents = $("#comment").val();
    var url = $("#add-comment").attr("action");
    console.log("url : " + url);

    $.ajax({
        type : "post",
        url : url,
        data : JSON.stringify(json),
        contentType : "application/json",
        dataType : "json",
        error : onError,
        success : function(data, status, jqXHR) {
            console.log(data);
            console.log("writeOnly : " + data.formattedCreateDate);
            console.log("readOnly : " + data.formattedModifiedDate);
            var commentTemplate = $("#write-comment-template").html();
            var template = commentTemplate.format(data.writer.name, data.contents, data.issue.id, data.id, data.formattedCreateDate);
            $("#comments").append(template);
            $(".delete-comment-btn").click(deleteComment);
        }
    })
}

//답변 수정 폼
$(".edit-comment-btn").click(updateCommentForm);

function updateCommentForm(e) {
    e.preventDefault();
    console.log(this);
    var updateBtn = $(this);
    console.log("updateBtn : " + updateBtn);
    var url = updateBtn.parent().attr("action");
    console.log("url : " + url);

    $.ajax({
        type : "get",
        url : url,
        dataType : "json",
        error : onError,
        success : function(data, status, jqXHR) {
            console.log(status);
            console.log(data);
            var commentUpdateTemplate = $("#update-comment-template").html();       //script태그 안의 클래스만 유효
            var template = commentUpdateTemplate.format(data.contents, data.issue.id, data.id);
            $(".comment-body-" + data.id).html(template);  //해당 댓글만 수정폼으로 바꿔줌 --> 클래스 네임에 해당 댓글의 아이디를 줘야 가능 아닐 경우, 모든 댓글에 대한 수정 폼이 적용됨
            $("#template-update-comment-btn").click(updateComment);
        }
    })
}

//답변 수정
function updateComment(e) {
    e.preventDefault();
    var updateBtn = $(this);
    var json = new Object();
    json.contents = $("#template-comment").val();
    console.log("contents : " + json.contents);

    var url = updateBtn.parent().attr("action");
    console.log("url : " + url);

    $.ajax({
        type : "put",
        url : url,                      //url지정 안하니까 /issues/1(id) 로 이동하던데 왜 하필 저기로 이동함??
        data : JSON.stringify(json),
        contentType : "application/json",
        dataType : "json",
        error : onError,
        success : function(data, status, jqXHR) {
            console.log(status);
             var commentTemplate = $("#write-update-comment-template").html();
             var template = commentTemplate.format(data.writer.name, data.contents, data.issue.id, data.id, data.formattedModifiedDate);
             $(".comment-body-" + data.id).html(template);
             $(".delete-comment-btn").click(deleteComment);
             $(".edit-comment-btn").click(updateCommentForm);
        }
    })
}

//답변 삭제
$(".delete-comment-btn").click(deleteComment);

function deleteComment(e) {
    e.preventDefault();
    var deleteBtn = $(this);
    console.log(deleteBtn);
    var url = deleteBtn.parent().attr("action");
    console.log("url : " + url);

    $.ajax({
        type : "delete",
        url : url,
//        dataType : "json",        //리스폰스의 바디가 없기 때문에, 데이터타입 지정할 경우 parseerror발생
        error : onError,
        success : function(data, status, jqXHR) {
            deleteBtn.closest(".mdl-color-text--grey-700").remove();
        }
    })
}

//에러 메세지
function onError(jqXHR, status, errorThrown) {
    console.log(jqXHR.responseText);    //json값 다 보여줌 (키,밸류 모두다) ex) {"message":"아이디 또는 비밀번호가 다릅니다."}
    console.log(jqXHR);
    alert(jqXHR.responseJSON.message);  //예외처리에서 받은 리턴값(responseEntity<ErrorMessage>)의 json객체의 키(message)값의 value를 가져온다.
}

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