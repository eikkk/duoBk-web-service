$(document).ready(function(){
    var taskId = findGetParameter("id");
    checkPermission(taskId);
    requestResult(taskId);

    $("#submitTask").on('click', function(){
        event.preventDefault();
        submitFormData(taskId);
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
    var url = "/constructor/tasks/getResult?id="+taskId + "&callback=?";
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
    var url = "/constructor/tasks/updateResult?id="+taskId + "&callback=?";
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
       url: "/tasks/checkPermission?taskId="+taskId,
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
function submitFormData(taskId){
    var result = document.getElementById("result").value;
    var message = document.getElementById("message").value;
    var objectData =
             {
                 result: result,
                 message: message
             };

    var objectDataString = JSON.stringify(objectData);
    console.log(objectDataString)
    var url = "/tasks/process/submit?id="+taskId;
    // Display the key/value pairs
    $.ajax({
        type: "POST",
        url: url,
        contentType: "application/json",
        data: objectDataString,
        success: function(textStatus, jqXHR) {
              window.location.href= "/tasks";
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert("error, check console for details");
            console.log("ERROR : ", jqXHR.responseText);
        }
    });
}