console.log("HIHI");

$(".milestone-menu").click(registerMilestone);

function registerMilestone(e) {
	console.log("click me");
	e.preventDefault();

	var url = $(e.target).attr("href");
	console.log("url is : " + url);

	$.ajax({
		type: 'get',
		url: url,
		error: onError,
		success: onSuccess});
}

function onError() {

}

function onSuccess(data, status) {
	console.log(data);
	
	//change color. (to notice success.)
	issue_success_effect();
	
	//add comment to notice.
	var timestamp = new Date();
	var answerCommentTemplate = $('[data-template="issue-comment"]').html();
	var template = answerCommentTemplate.format(data.writer.userId, data.milestone.subject, timestamp);
	$("#milestone-menu").text(data.milestone.subject);
	$(".issue-comments").append(template);
}

function issue_success_effect() {
	$(".issue-title-line").attr("style", "background-color:#39C435");
	$(".issue-title-line").animate({width: "70%",}, 1000);
	$(".issue-title-line").animate({width: "100%",}, 1000);
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