$(".milestone-Choice").on("click", setMilestone);
$(".label-Choice").on("click", setLabel);
$(".assignee-Choice").on("click", setAssignee);
$(".add-comment").on("click", addComment);
$(".delete-comment").on("click", addComment);

function setMilestone(e){
e.preventDefault();
console.log('this : ' + this.action);
console.log(this.childNodes);

var url = $(this).attr("action");
console.log("url  " + url);
$.ajax({
type : 'put',
url : url,
error : function (xhr, status){
alert("error");
},
success : function(data, status){
console.log("success");
}
});
};

function setAssignee(e){
e.preventDefault();
var url = $(this).attr("action");
console.log("url  " + url);
$.ajax({
type : 'put',
url : url,
error : function (xhr, status){
alert("error");
},
success : function(data, status){
console.log("success");
}
});
};

function setLabel(e){
e.preventDefault();
var url = $(this).attr("action");
console.log("url  " + url);
$.ajax({
type : 'put',
url : url,
error : function (xhr, status){
alert("error");
},
success : function(data, status){
console.log("success");
}
});
};

function addComment(e){
e.preventDefault();
console.log('target : ' + e.target);
if(e.target == '[object HTMLSpanElement]'){
var url = $(this).attr("action");
var comment = $(".mdl-textfield__input").val();
console.log("url :" + url);
console.log("commentData :" + comment);

$.ajax({
type : 'post',
url : url,
contentType : "application/json",
//data : JSON.stringify({
//comment : comment
//}),
data : JSON.stringify(comment),
dataType : 'json',
error : function(){
alert("fail");
},
success : function(data, status){
console.log(data);
var commentTemplate = $("#commentTemplate").html();
var template = commentTemplate.format(data.writer.userId, data.comment);
console.log("template : " + template);
$("#add-comment").append(template);
$(".add-comment textarea").val("");
}
})
}
}

function deleteComment(e){
e.preventDefault();
var url = $(this).attr("action");
console.log("url :" + url);
var deleteBtn = $("comment mdl-color-text--grey-700");

$.ajax({
type : 'delete',
url : url,
error : function(){
alert("fail");
},
success : function(data, status){
console.log(data);
$(deleteBtn).closest("div").remove;
}
})
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