$(".delete-comments").click(deleteAnswer);


function deleteAnswer(e){
	e.preventDefault();
	console.log(e);
	if (!confirm("삭제하시겠습니까?")) {
		return;
	}
	console.log("click delete");
	
	var deleteBtn = $(".delete-comments");
	
	var deleteUrl = $(this).closest(".delete-comment-parent").attr("id");
	console.log("deleteUrl : " + deleteUrl);
	
	$.ajax({
		type : 'delete',
		url : deleteUrl,
		dataType : 'json',
		error : function (xhr, status){
		},
		success : function (data, status){
			$("#"+data.id.toString()).closest("div").remove();
		}
	})
}

$("#comment-submit").click(addAnswer);
function addAnswer(e) {
	e.preventDefault();
	console.log("click me");

	var queryString = $("#comment-form").serialize();
	console.log("query : " + queryString);

	var url = $("#comment-form").attr("action"); 
	console.log("url : " + url);

	$.ajax({
		type : 'post',
		url : url,
		data : queryString,
		dataType : 'json',
		error : onError,
		success : onSuccess
	});
}


function onError() {
	console.log("fail!!!");
}

function onSuccess(data, status) {
	console.log("status : " + status);
	console.log(data);
	var answerTemplate = $("#answerTemplate").html();
	
	var template = answerTemplate.format(data.writer.name, data.contents, data.id);
	$(template).appendTo("#before-comment").hide().fadeIn(500);
	console.log(template);
	$("#comment-form textarea[name=contents]").val("");
}

String.prototype.format = function() {
	var args = arguments;
	return this.replace(/{(\d+)}/g, function(match, number) {
		return typeof args[number] != 'undefined' ? args[number] : match;
	});
};
