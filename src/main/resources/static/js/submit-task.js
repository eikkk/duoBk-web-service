$(document).ready(function(){
    var taskId = findGetParameter("id");
    checkPermission(taskId);
    requestResult(taskId);

    $("#submitTask").on('click', function(){
            var url = "/tasks/process/submit?id="+taskId;
            var value = document.getElementById("result").value;
            var message = document.getElementById("message").value;
            value += "!message! " + message;
            $.ajax({
                      type: "POST",
                      url: url,
                      contentType: "text/plain",
                      data: value,
                      success: function(data, textStatus, jqXHR) {
                        window.location.href= "/tasks";
                      },
                      error: function(jqXHR, textStatus, errorThrown) {
                        alert("error, check console for details");
                        console.log("ERROR : ", jqXHR.responseText);
                      }
                   });
    });

    $('#saveChanges').on('click', function(){
        updateResult(taskId);
    });

    $('#result').highlightWithinTextarea({
       highlight:
       [
          {
              highlight: /(<dp|<\/dp>)/g,
              className: 'red'
          },
          {
              highlight: /(<ds>|<\/ds>)/g,
              className: 'green'
          },
          {
              highlight: /(?<=<s1.+?>)(.+?)(?=<)/g,
              className: 'blue'
          },
          {
              highlight: /(?<=<s2.+?>)(.+?)(?=<)/g,
              className: 'yellow'
          }
       ]
    });
    $('#checkNavItem').on('click', function(){
        $('#result').highlightWithinTextarea('update');
    });
});


function findGetParameter(parameterName) {
    var result = null,
        tmp = [];
    var items = location.search.substr(1).split("&");
    for (var index = 0; index < items.length; index++) {
        tmp = items[index].split("=");
        if (tmp[0] === parameterName) result = decodeURIComponent(tmp[1]);
    }
    return result;
}
function requestResult(taskId){
    var url = "/tasks/getResult?id="+taskId;
    $.ajax({
              type: "GET",
              url: url,
              success: function(data, textStatus, jqXHR) {
                document.getElementById("result").value = data;
              },
              error: function(jqXHR, textStatus, errorThrown) {
                alert("error, check console for details");
                console.log("ERROR : ", jqXHR.responseText);
              }
           });
}

function updateResult(taskId){
    var url = "/tasks/updateResult?id="+taskId;
    var value = document.getElementById("result").value;
    $.ajax({
        type: "POST",
        url: url,
        contentType: "text/plain",
        data: value,
        success: function(data, textStatus, jqXHR) {
          window.location.href="/tasks/submit?id="+taskId;
        },
        error: function(jqXHR, textStatus, errorThrown) {
          alert("error, check console for details");
          console.log("ERROR : ", jqXHR.responseText);
        }
    });
}

function checkPermission(taskId){
$.ajax({
       type: "GET",
       url: "/tasks/checkPermission?id="+taskId,
       error: function(jqXHR, textStatus, errorThrown) {
       console.log(jqXHR.status);
           if (jqXHR.status == 401){
                alert("Permission denied");
                window.location.href = "/tasks";
           }
           else{
               alert("error, check console for details");
               console.log("ERROR : ", jqXHR.responseText);
               console.log(textStatus);
               console.log(jqXHR);

           }
       }
   });
}