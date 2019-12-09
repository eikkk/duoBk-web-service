$(document).ready(function(){
    requestAuthors();


    $('#allAuthorsTable th.idCell').on('click', function(){
        var table = document.getElementById("allBooksTable");
        sortTable(table, 1);
    });
    $('#allAuthorsTable th.nameCell').on('click', function(){
        var table = document.getElementById("allBooksTable");
        sortTable(table, 2);
    });
    $('#saveXML').on('click', function(){
        var xml = returnTableAsXML();
        download("text.xml",xml);
    });
    $('#loadXML').on('click', function(){
        console.log("click");
        $("#my_file").val('');
        document.getElementById('my_file').click();
    });
     $('#my_file').on('change', function() {
        console.log("herere");
    	// list of selected files
    	var all_files = this.files;
    	if(all_files.length == 0) {
    		alert('Error : No file selected');
    		return;
    	}

    	// first file selected by user
    	var file = all_files[0];

    	// Max 2 MB allowed
    	var max_size_allowed = 2*1024*1024
    	if(file.size > max_size_allowed) {
    		alert('Error : Exceeded size 2MB');
    		return;
    	}

    	// file validation is successful
    	// we will now read the file
    	var reader = new FileReader();

        // file reading started
        reader.addEventListener('loadstart', function() {
            console.log('File reading started');
        });

        // file reading finished successfully
        reader.addEventListener('load', function(e) {
            var text = e.target.result;

            // contents of the file
            populateTableWithXML(text);
        });

        // file reading failed
        reader.addEventListener('error', function() {
            alert('Error : Failed to read file');
        });

        // file read progress
        reader.addEventListener('progress', function(e) {
            if(e.lengthComputable == true) {
            	var percent_read = Math.floor((e.loaded/e.total)*100);
            	console.log(percent_read + '% read');
            }
        });

        // read as text file
        reader.readAsText(file);
    });
});
function populateTableWithXML(xml){
    var xmlDoc = jQuery.parseXML(xml);
    console.log(xmlDoc.getElementsByTagName("author"));
    var authors = xmlDoc.getElementsByTagName("author");
    var table = document.getElementById("allAuthorsTable");
    var tbody = table.getElementsByTagName('tbody')[0];
    tbody.innerHTML = "";
    for(var i =0; i < authors.length; i++){
         var name = authors[i].getAttribute("name");
         var id = authors[i].getAttribute("id");
         var newRow = tbody.insertRow(table.length);
         var cell = newRow.insertCell(0);
         cell.innerHTML = "<a class=\"btn btn-default\" href=\"/admin/authors/edit?id="+id+"\">" +
         "<i class=\"fa fa-wrench\" ></i></a>";
         cell.setAttribute("class", "actionCell");
         cell = newRow.insertCell(1);
         cell.innerHTML = id;
         cell.setAttribute("class", "idCell");
         cell = newRow.insertCell(2);
         cell.innerHTML = name;
         cell.setAttribute("class", "nameCell");
    }
}
function returnTableAsXML(){
    var xml = "<items>\n";
        $("#allAuthorsTable tbody tr").each(function() {
            var cells = $("td", this);
            if (cells.length > 0) {
                xml += "<author name='" + cells.eq(2).text() + "'"+ " id='" + cells.eq(1).text() +"'>\n";
                xml += "</author>\n";
            }
        });
        xml+= "</items>"
        return xml;
}
function download(filename, text) {
  var element = document.createElement('a');
  element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
  element.setAttribute('download', filename);

  element.style.display = 'none';
  document.body.appendChild(element);

  element.click();

  document.body.removeChild(element);
}
function requestAuthors(){
 $.ajax({
        type: "GET",
        url: "/constructor/authors/getAllForMenu",
        success: function(data, textStatus, jqXHR) {
            var table = document.getElementById("allAuthorsTable");
            populateAuthorTable(table,data);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert("error, check console for details");
            console.log("ERROR : ", jqXHR.responseText);
        }
    });
}
function populateAuthorTable(table, arrayData){
        var tbody = table.getElementsByTagName('tbody')[0];
        tbody.innerHTML = "";
        for(var i =0; i < arrayData.length; i++){
            var authorId = arrayData[i][0];
            var newRow = tbody.insertRow(table.length);
            var cell = newRow.insertCell(0);
            cell.innerHTML = "<a class=\"btn btn-default\" href=\"/admin/authors/edit?id="+authorId+"\">" +
            "<i class=\"fa fa-wrench\" ></i></a>";
            cell.setAttribute("class", "actionCell");
            cell = newRow.insertCell(1);
            cell.innerHTML = authorId;
            cell.setAttribute("class", "idCell");
            cell = newRow.insertCell(2);
            cell.innerHTML = arrayData[i][1];
            cell.setAttribute("class", "nameCell");
        }
}

function sortTable(table, n) {
  var rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
  switching = true;
  // Set the sorting direction to ascending:
  dir = "asc";
  /* Make a loop that will continue until
  no switching has been done: */
  while (switching) {
    // Start by saying: no switching is done:
    switching = false;
    rows = table.rows;
    /* Loop through all table rows (except the
    first, which contains table headers): */
    for (i = 1; i < (rows.length - 1); i++) {
      // Start by saying there should be no switching:
      shouldSwitch = false;
      /* Get the two elements you want to compare,
      one from current row and one from the next: */
      x = rows[i].getElementsByTagName("td")[n];
      y = rows[i + 1].getElementsByTagName("td")[n];
      /* Check if the two rows should switch place,
      based on the direction, asc or desc: */
      if (dir == "asc") {
        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
          // If so, mark as a switch and break the loop:
          shouldSwitch = true;
          break;
        }
      } else if (dir == "desc") {
         if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
           // If so, mark as a switch and break the loop:
           shouldSwitch = true;
           break;
         }
      }
    }
    if (shouldSwitch) {
      /* If a switch has been marked, make the switch
      and mark that a switch has been done: */
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true;
      // Each time a switch is done, increase this count by 1:
      switchcount ++;
    } else {
      /* If no switching has been done AND the direction is "asc",
      set the direction to "desc" and run the while loop again. */
      if (switchcount == 0 && dir == "asc") {
        dir = "desc";
        switching = true;
      }
    }
  }
}

function searchTable() {
  // Declare variables
  var input, filter, table, tr, td, i, txtValue;
  input = document.getElementById("myInput");
  filter = input.value.toUpperCase();
  table = document.getElementById("allAuthorsTable");
  tr = table.getElementsByTagName("tr");

  // Loop through all table rows, and hide those who don't match the search query
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[2];
    if (td) {
      txtValue = td.textContent || td.innerText;
      if (txtValue.toUpperCase().indexOf(filter) > -1) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    }
  }
}