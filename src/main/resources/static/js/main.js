var loginUrl = "/users/login";

$(".mileStone-add-btn").on("click", function(e) {
	e.preventDefault();
	console.log("milestone add");

	var url = $(this).attr("href");
	console.log("url :" + url);

	$.ajax({
		type : 'get',
		url : url,
		error : function() {
			location.href = loginUrl;
		},
		success : function() {
			alert("마일스톤이 등록되었습니다.");
		}
	});
});
$(".label-add-btn").on("click", function(e) {
	e.preventDefault();
	console.log("label add");

	var url = $(this).attr("href");
	console.log("url :" + url);

	$.ajax({
		type : 'get',
		url : url,
		error : function() {
			location.href = loginUrl;
		},
		success : function() {
			alert("라벨이 등록되었습니다.");
		}
	});
});
$(".assignee-add-btn").on("click", function(e) {
	e.preventDefault();
	console.log("assignee add");

	var url = $(this).attr("href");
	console.log("url :" + url);

	$.ajax({
		type : 'get',
		url : url,
		error : function() {
			location.href = loginUrl;
		},
		success : function() {
			alert("관리자가 등록되었습니다.");
		}
	});
});

// 글쓰기
$(".answer-write button[type=submit]").on(
		"click",
		function(e) {
			e.preventDefault();
			console.log("answer write");

			var queryString = $(".answer-write").serialize();
			var url = $(".answer-write").attr("action");
			console.log("queryString :" + queryString);
			console.log("url :" + url);

			$.ajax({
				type : 'post',
				url : url,
				data : queryString,
				dataType : 'json',
				error : function() {
					location.href = loginUrl;
				},
				success : function(data) {
					console.log(data);
					var answerTemplate = $("#answerTemplate").html();
					var template = answerTemplate.format(data.writer.name,
							data.comment, data.formattedCreateDate,
							data.issue.id, data.id);
					$(".comment-article").prepend(template);
				}
			});
		});

// 수정폼 & 수정
$(document).on(
		"click",
		".update-comment",
		function(e) {
			e.preventDefault();
			var updateBtn = $(this);
			var url = updateBtn.attr("href");
			var textarea = updateBtn.parents().eq(3).find(
					"textarea.add-comment-textarea");
			var check = textarea.prop("readonly");
			console.log("check status : " + check);

			if (check) {
				console.log("수정폼 url:" + url);

				$.ajax({
					type : 'get',
					url : url,
					dataType : 'json',
					error : function() {
						location.href = loginUrl;
					},
					success : function(data) {
						console.log(data);
						if (data.valid) {
							textarea.prop("readonly", false);
							textarea.focus();
						} else {
							alert("자신의 글만 수정가능합니다.");
						}
					}
				});

			} else {
				var queryString = textarea.val();
				console.log("수정폼" + queryString);
				console.log("url" + url);
				$.ajax({
					type : 'put',
					url : url,
					contentType : "application/json",
					data : queryString,
					dataType : 'json',
					error : function() {
						console.log("error");
					},
					success : function(data) {
						if (data.valid) {
							alert("성공수정");
						} else {
							alert("자신의 글만 수정가능합니다.");
						}
						textarea.prop("readonly", true);
					}
				});
			}

		});

// 삭제
$(document).on("click", ".delete-comment", function(e) {
	e.preventDefault();

	var deleteBtn = $(this);
	var url = deleteBtn.attr("href");
	console.log("삭제url:" + url);

	$.ajax({
		type : 'delete',
		url : url,
		dataType : 'json',
		error : function() {
			location.href = loginUrl;
		},
		success : function(data) {
			console.log(data);
			if (data.valid) {
				alert("성공적으로 삭제되었습니다.");
				deleteBtn.closest(".comment").remove();
			} else {
				alert("자신의 글만 삭제가능합니다.");
			}
		}
	});
});

String.prototype.format = function() {
	var args = arguments;
	return this.replace(/{(\d+)}/g, function(match, number) {
		return typeof args[number] != 'undefined' ? args[number] : match;
	});
};
