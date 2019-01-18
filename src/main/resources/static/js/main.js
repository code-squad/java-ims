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

$("#milestone-menu").on("click", milestone);

function milestone(e) {
    console.log("제발좀...")
    e.preventDefault();

    var url = $("#milestone-menu").attr("value");
    console.log(url);
    var li = "";
    $.ajax ({
        type : 'get',
        url : url,
        dataType : 'json',
        contentType : "application/json",
        error : function(xhr, status) {
            console.log(xhr);
            alert("마일스톤 에러");
        },
        success : function(data) {
//            $.each(function(item) {
//                console.log(url);
//                console.log(data);
//                console.log(item);
//                console.log(item.subject);
//                li += '<li class="mdl-menu__item">' +
//                    '<a href ="'+ url + '">' + item.subject +'</a></li>';
//            });
            $.each(data, function(index, value) {
                  li += '<li class="mdl-menu__item" id = "milestone-inject" value = "' + url + '/' + value.id +'"><a>'
                                              + value.subject +'</a></li>';
            });

            console.log("마일스톤 리스트 확인 ~~ : "+li);
            $("#milestone-list").html(li);
            $("#milestone-menu").unbind();
        }
    })
}

$("#label-menu").click(label);

function label(e) {
    console.log("label 되나요?");
    e.preventDefault();

    var url = $("#label-menu").attr('value')
    var li = "";
    console.log(url);
    $.ajax({
    type : 'get',
    url : url,
    dataType : 'json',
    contentType : 'application/json',
    error : function(xhr, status) {
        console.log(xhr);
        alert('Error of Label')
    },
    success : function(data) {
        $.each(data, function(index, value) {
            li += '<li class="mdl-menu__item" id = "label-inject" value = "'+ url + "/" + value.id +'"><a>'
                    + value.name +'</a></li>';
        });

        console.log("제발 되주세요 ㅠㅠ ~~ :" + li);
        $("#label-list").html(li);
        $("#label-menu").unbind();
        }
    })

}

$("#assignee-menu").on("click", assignee);
function assignee(e) {
    e.preventDefault();
    console.log("프리벤트 지나간다잉~");

    var url = $("#assignee-menu").attr("value");
    console.log(url);

    var li = "";

    $.ajax({
        type : 'get',
        url : url,
        dataType : 'json',
        contentType : 'application/json',
        error : function(xhr, status) {
            console.log(xhr);
            alert("problem with assignee");
        },
        success : function(data) {
            console.log("assignee 성공 했디야.");
            $.each(data, function(index, value) {
                 li += '<li class="mdl-menu__item" id = "assignee-inject" value = "'+ url + '/' + value.id +'"><a>' +
                                     value.name +'</a></li>';
            })


            console.log("성공했나요?");
            console.log(li);
            $("#assignee-list").html(li);
            $("#assignee-menu").unbind();
        }
    })
}

$(document).on("click", "#milestone-inject a", function milestoneInject(e) {
    e.preventDefault();
    console.log("마일스톤 이슈에 저장");

    var url = $('#milestone-inject').attr('value');

    $.ajax({
        type : 'post',
        url : url,
        dataType : 'json',
        contentType : 'application/json',
        error : function(xhr, status) {
            console.log(xhr);
            console.error();
            alert("제대로 처리가 되지 않았습니다.");
        },
        success : function(data, status, xhr) {
            console.log(xhr);
            console.log(data);
            alert("제대로 처리가 되었습니다.")
        }
    })

});

$(document).on("click", "#label-inject a" ,labelInject);

function labelInject(e) {
    e.preventDefault();
    console.log("라벨 이슈에 저장");

    var url = $('#label-inject').attr('value');

    $.ajax({
        type : 'post',
        url : url,
        dataType : 'json',
        contentType : 'application/json',
        error : function(xhr, status) {
            console.log(xhr);
            alert("제대로 처리가 되지 않았습니다.");
        },
        success : function(data, status, xhr) {
            console.log(data);
            console.log(status);
            alert("제대로 처리가 되었습니다.");
        }
    })
}

$(document).on("click", "#assignee-inject", assigneeInject);
function assigneeInject(e) {
    e.preventDefault();
    console.log("담당자 에이젝스");

    var url = $("#assignee-inject").attr("value");
    console.log(url);

    $.ajax({
        type : 'post',
        url : url,
        dataType : 'json',
        contentType : 'application/json',
        error : function(xhr, status) {
            console.log(xhr);
            alert("담당자 처리가 재대로 되지 않았습니다.");
        },
        success : function(data) {
            console.log(data);
            alert("처리가 완료 되었습니다.");
        }
    })
}

String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};