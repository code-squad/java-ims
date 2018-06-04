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
		success : function(data,status){
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
		success : function(data,status){
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
		success : function(data,status){
			alert("관리자가 등록되었습니다.");
		}
	});
});



