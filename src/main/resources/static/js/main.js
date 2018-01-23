// set milestone
$("#form-wrap").on('submit', '.milestone', function(e) {
//	alert(1);
	e.preventDefault();
	
	var url = $(this).attr("action");
	console.log("url :" + url);
	
	$.ajax({
		type: 'put',
		url : url,
		error : function(){
			console.log("failure");
		},
		success: function(){
			console.log("success");
		}
	});
});
// set label
$("#label_group")
	.on('submit', '.label', setLabel);

function setLabel(e) {
	e.preventDefault();
	
	var url = $(this).attr("action");
	console.log("url :" + url);
	
	$.ajax({
		type: 'put',
		url : url,
		error : function(){
			console.log("failure");
		},
		success : function(){
			console.log("success");
		}
	});	
}

// set assignUser
$("#assignUser_group")
	.on('submit', '.assignUser', setAssignUser);

function setAssignUser(e) {
e.preventDefault();
	
	var url = $(this).attr("action");
	console.log("url :" + url);
	
	$.ajax({
		type: 'put',
		url : url,
		error : function(){
			console.log("failure");
		},
		success : function(){
			console.log("success");
		}
	});	
}

