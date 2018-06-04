/*$(".mileStone-add-btn").on("click",function(e){
	e.preventDefault();
	console.log("milestone add");
	
	var url = $(this).attr("href");
	console.log("url :"+url);
	
	
	$.ajax({
		type : 'get',
		url: url,
//		dataType : 'json',
		error : onError,
		success : function(data,status){
			if(data==null){
				window.location.href = "/user/login.html";
			}
			console.log(data);
			console.log("상태"+status);
			alert("마일스톤이 등록되었습니다.");
		}
	});
	
});


function onError() {
	console.log("error");
}
*/