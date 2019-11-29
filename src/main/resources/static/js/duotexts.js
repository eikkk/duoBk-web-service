$(document).ready(function () {

    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
    });
    $("#create-btn").on('click',function(){

    });

    $("#create-task-form-btn").click(function(event){
        event.preventDefault();
        var fileInput = $("#image")[0];
        if(fileInput.value != ""){
             var imageFile = fileInput.files[0];
             var ext = imageFile.name.split('.').pop().toLowerCase();
             if(ext != "jpg" && ext != "jpeg"){
                alert("wrong image format");
                return;
             }
             if(imageFile.size > 100000){
                 alert("file is too large. Should be less than 100KB");
                 return;
             }
        }
        // Get form
        var form = $('#create-form')[0];
        var data = new FormData(form);
        data.append("userId", value);
        $("#submitBook").prop("disabled", true);
        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/constructor/authors/create",
            data: data,
            // prevent jQuery from automatically transforming the data into a query string
            processData: false,
            contentType: false,
            cache: false,
            timeout: 1000000,
            success: function(textStatus, jqXHR) {
                window.location.href="/admin/authors";
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert("error, check console for details");
                console.log("ERROR : ", jqXHR.responseText);
            }
        });
    });
});