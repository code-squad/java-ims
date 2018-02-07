$('#logout').click(logout);

function logout(e) {
	e.preventDefault();

	var url = $(this).attr("href");

	$.ajax({
		type: 'put',
		url: url,
		error: function (error) {
			console.log(error);
		},
		success: function (data) {
			window.location.href = data;
		}
	});
}

$('#issues-menu-lower-right').click(removeIssue);

function removeIssue(e) {
	e.preventDefault();

	var url = $(this).attr("href");
	console.log(url);

	$.ajax({
		type: 'delete',
		url: url,
		error: function (error) {
			console.log(error);
			if (error.status == 403) {
				alert("작성자만 삭제할 수 있습니다.");
			}
		},
		success: function (data) {
			window.location.href = data;
		}
	});
}