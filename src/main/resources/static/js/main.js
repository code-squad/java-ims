$(".mdl-js-menu li").on("click", setIssueAttribute);
$(".issue-comment-ims[type=submit]").on("click", addComment);
$('.comments').on('click', '.comment-delete button[type=submit]', deleteComment);

function setIssueAttribute(e) {
    e.preventDefault();
    var url = $(this).find('a').attr('href');
    $.ajax({
            type : 'get',
            url : url,
            error: onError,
            success : function onSuccess(data, textStatus, jqXhr) {
                          alert("지정에 성공하였습니다!");
                      }
        });
}

function addComment(e) {
    e.preventDefault();
    var url = $(this).parent().attr('action');
    var comment = new Object();
    comment.contents = $('#contents').val();
    $.ajax({
            type : 'post',
            url : url,
            data : JSON.stringify(comment),
            contentType : 'application/json',
            error: onError,
            success : function onSuccess(data, textStatus, jqXhr) {
                          var commentTemplate = $('#commentTemplate').html();
                          var template = commentTemplate.format(data.writer.name, data.contents);
                          $('.comments').append(template);
                          $("textarea[name=contents]").val("");
                      }
        });
}

function deleteComment(e) {
    e.preventDefault();
    var deleteBtn = $(this);
    var url = deleteBtn.parent().attr('action');
    $.ajax({
            type : 'delete',
            url : url,
            error : onError,
            success : function onSuccess(data, textStatus, jqXhr) {
                        deleteBtn.closest('div.comment').remove();
                    }
        });
}

function onError(jqXhr, textStatus, errorThrown) {
    var errorMessage = jqXhr.responseJSON.message;
    alert(errorMessage);
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


