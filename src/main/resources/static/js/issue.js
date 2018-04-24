console.log("HIHI");

$(".milestone-menu").click(registerMilestone);
$(".label-menu").click(updateLabel);
$(".assignee-menu").click(addManager);
$(".new-comment [type=submit]").click(addComments);
//$(".upload [type=submit]").click(test);

function test(e) {
	e.preventDefault();
	console.log("test in");
	
	var file = $(".upload-file-submit").serialize();
	console.log("file name : " + file.value);
	
	var url = $(".upload").attr("action");
	console.log("url is : " + url);
}

function addComments(e) {
	e.preventDefault();
	
	var queryString = $("#comment").serialize();
	console.log("query : " + queryString);
	
	var url = $(".addcomment").attr("action");
	console.log("url is : " + url);
	
	$.ajax({
		type: 'post',
		url: url,
		data: queryString,
		dataType: 'json',
		error: makeAnswerFail,
		success: makeAnswerSuccess});
}

function makeAnswerFail(data, status) {
	console.log(data);
	
	$("#comment").fadeOut();
	$(".alert-danger").fadeIn();
	$(".alert-danger").text("[ERROR] 로그인을 해주세요.");
	$(".issue-title-line").attr("style", "background-color:red");
	$(".issue-title-line").animate({width: "70%",}, 1000);
	$(".issue-title-line").animate({width: "100%",}, 1000);
	$(".alert-danger").animate({width: "100%",}, 800);
	$(".alert-danger").fadeOut();
	$("#comment").fadeIn();
}

function makeAnswerSuccess(data, status) {
	console.log(data);
	
	//change color. (to notice success.)
	issue_success_effect();
	
	$("#comment").fadeOut();
	var answerCommentTemplate = $('[data-template="issue-comment"]').html();
	var template = answerCommentTemplate.format(data.writer.userId, data.comment);
	$(".issue-comments").append(template);
	$("#comment").fadeIn();
}

function registerMilestone(e) {
	console.log("click me");
	e.preventDefault();

	var url = $(e.target).attr("href");
	console.log("url is : " + url);

	$.ajax({
		type: 'get',
		url: url,
		error: onError,
		success: onSuccessMilestone});
}

function updateLabel(e) {
	console.log("click me");
	e.preventDefault();
	
	var url = $(e.target).attr("href");
	console.log("url is : " + url);
	
	$.ajax({
		type: 'get',
		url: url,
		error: onError,
		success: onSuccessLabel});
}

function addManager(e) {
	console.log("add manager service.");
	e.preventDefault();
	
	var url = $(e.target).attr("href");
	console.log("url is : " + url);
	
	$.ajax({
		type: 'get',
		url: url,
		error: onError,
		success: onSuccessAssignee});
	
}

function onError() {
	console.log("ERROR");
	
	//change color. (to notice fail.)
	issue_fail_effect();
}

function onSuccessMilestone(data, status) {
	console.log(data);
	
	//change color. (to notice success.)
	issue_success_effect();
	
	//add comment to notice.
	var timestamp = new Date();
	var answerCommentTemplate = $('[data-template="issue-comment-milestone"]').html();
	var template = answerCommentTemplate.format(data.writer.userId, data.milestone.subject, timestamp);
	$("#milestone-menu").text(data.milestone.subject);
	$(".issue-comments").append(template);
}

function onSuccessLabel(data, status) {
	console.log(data);
	
	//change color. (to notice success.)
	assignee_success_effect();
	
	//add comment to notice.
	var timestamp = new Date();
	var answerCommentTemplate = $('[data-template="issue-comment-label"]').html();
}

function onSuccessAssignee(data, status) {
	console.log(data);
	
	//change color. (to notice success.)
	assignee_success_effect();
}

function issue_success_effect() {
	$(".issue-title-line").attr("style", "background-color:#39C435");
	$(".issue-title-line").animate({width: "70%",}, 1000);
	$(".issue-title-line").animate({width: "100%",}, 1000);
}

function assignee_success_effect() {
	$(".alert-danger").fadeIn();
	$(".alert-danger").text("SAVE SUCCESS!!");
	$(".issue-title-line").attr("style", "background-color:#39C435");
	$(".alert-danger").attr("style", "background-color:#39C435");
	$(".issue-title-line").animate({width: "70%",}, 1000);
	$(".issue-title-line").animate({width: "100%",}, 1000);
	$(".alert-danger").animate({width: "100%",}, 800);
	$(".alert-danger").fadeOut();
}

function issue_fail_effect() {
	$(".alert-danger").fadeIn();
	$(".alert-danger").text("[ERROR] 수정 권한이 없습니다.");
	$(".issue-title-line").attr("style", "background-color:red");
	$(".issue-title-line").animate({width: "70%",}, 1000);
	$(".issue-title-line").animate({width: "100%",}, 1000);
	$(".alert-danger").animate({width: "100%",}, 800);
	$(".alert-danger").fadeOut();
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