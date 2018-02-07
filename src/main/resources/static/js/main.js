console.log('here');


$('#issues-menu-lower-right').click(removeIssue);

function removeIssue(e) {
	e.preventDefault();

	var url = $(this).attr("href");
	console.log(url);

	$.ajax({
		type : 'delete',
		url : url,
		dataType : 'json',
		error : function (error) {
			console.log(error);
		},
		success : function (data) {
			console.log(data);
			window.location.redirect("/issues");
		}
	});
}