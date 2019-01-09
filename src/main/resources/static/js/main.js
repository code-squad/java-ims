$(".mdl-js-menu li").on("click", setIssueAttribute);
$(".issue-comment-ims[type=submit]").on("click", addComment);
$('.comments').on('click', '.comment-delete button[type=submit]', deleteComment);
$('.comments').on('click', '.comment-edit button[type=submit]', editRequest);
$('.comments').on('click', '.comment-edit-ims[type=submit]', editComment);

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
                          var template = commentTemplate.format(data.writer.name, data.contents, data.issue.id, data.id);
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

function editRequest(e) {
    e.preventDefault();
    var url = $(this).parent().attr('action');
    var commentBody = $(this).closest('.comment-body');
    $.ajax({
            type : 'get',
            url : url,
            error : onError,
            success : function onSuccess(data, textStatus, jqXhr) {
                var commentEditTemplate = $('#commentEditTemplate').html().format(url, data.contents);
                commentBody.html(commentEditTemplate);
            }
    });
}

function editComment(e) {
    e.preventDefault();
    var url = $(this).parent().attr('action');
    var comment = new Object();
    comment.contents = $('#edit-contents').val();
    var targetComment = $(this).closest('div.comment');
    $.ajax({
            type : 'put',
            url : url,
            data : JSON.stringify(comment),
            contentType : 'application/json',
            error: onError,
            success : function onSuccess(data, textStatus, jqXhr) {
                          console.log(data);
                          var commentTemplate = $('#commentTemplate').html();
                          var template = commentTemplate.format(data.writer.name, data.contents, data.issue.id, data.id);
                          targetComment.replaceWith(template);
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


