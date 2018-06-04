var loginUrl = "/users/login";

$(".mileStone-add-btn").on("click",function(e){
	e.preventDefault();
	console.log("milestone add");
	
	var url = $(this).attr("href");
	console.log("url :"+url);
	
	$.ajax({
		type : 'get',
		url: url,
		error : function(){
			location.href =loginUrl;
		},
		success : function(){
			alert("마일스톤이 등록되었습니다.");
		}
	});
});
$(".label-add-btn").on("click",function(e){
	e.preventDefault();
	console.log("label add");
	
	var url = $(this).attr("href");
	console.log("url :"+url);
	
	$.ajax({
		type : 'get',
		url: url,
		error : function(){
			location.href =loginUrl;
		},
		success : function(){
			alert("라벨이 등록되었습니다.");
		}
	});
});
$(".assignee-add-btn").on("click",function(e){
	e.preventDefault();
	console.log("assignee add");
	
	var url = $(this).attr("href");
	console.log("url :"+url);
	
	$.ajax({
		type : 'get',
		url: url,
		error : function(){
			location.href =loginUrl;
		},
		success : function(){
			alert("관리자가 등록되었습니다.");
		}
	});
});


$(".answer-write button[type=submit]").on("click", function(e){
	e.preventDefault();
	console.log("answer write");
	
	var queryString = $(".answer-write").serialize();
	var url = $(".answer-write").attr("action");
	console.log("queryString :"+queryString);
	console.log("url :"+url);
	
	$.ajax({
		type : 'post',
		url : url,
		data : queryString,
		dataType : 'json',
		error : function(){
			location.href =loginUrl;
		},
		success : function(data){
			console.log(data);
			var answerTemplate = $("#answerTemplate").html();
			var template = answerTemplate.format(data.writer.name, data.comment, data.formattedCreateDate);
			$(".comment-article").prepend(template);
		}
	});
	
	
});

String.prototype.format = function() {
	var args = arguments;
	return this.replace(/{(\d+)}/g, function(match, number) {
		return typeof args[number] != 'undefined' ? args[number] : match;
	});
};
