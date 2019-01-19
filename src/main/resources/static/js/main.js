
$("#close-button").on("click", changeStatus);
function changeStatus(e) {
    e.preventDefault();

    var url = $("#close-button").attr("href");
    console.log("url : " + url);

    $.ajax({
            type : 'get',
            url : url,
            dataType : 'json',
            error: onError,
            success : function(data) {
                console.log(data);
                if(data.closed) {
                    $("#close-button").html('OPEN ISSUE');
                } else {
                    $("#close-button").html('CLOSE ISSUE');
                }
            }
        });
}

$(".property-list").on("click", relateProperty);
function relateProperty(e) {
    e.preventDefault();

    var url = $(this).attr("href");
    console.log("url : " + url);

    $.ajax({
            type : 'get',
            url : url,
            dataType : 'json',
            error: onError,
            success : function(data) {
                console.log('success');
            }
        });
}

function onError() {
    console.log('error');
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