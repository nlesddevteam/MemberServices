//save add new category
function addNewCategory(){
	//reset error message
	$("#msgerradd").hide();
	//check the name field to see if it is populated
	if($("#txtname").val() == ""){
		$("#msgerradd").html("Please enter name").show();
	}else{
		addNewCategoryAjax();
	}
}
//call add category ajaxx
function addNewCategoryAjax()
{
	var catnameval = $("#txtname").val();
	var catdescval = $("#txtdescription").val();

	$.ajax(
 			{
 				type: "POST",  
 				url: "addNewPCategory.html",
 				data: {
 					catname: catnameval, catdesc: catdescval
 				}, 
 				success: function(xml){
 					$(xml).find('CATSTATUS').each(function(){
 							
 							
 							if($(this).find("MESSAGE").text() == "SUCCESS")
 								{
									window.location="viewCategory.html?cid=" + $(this).find("CID").text();
 									//alert($(this).find("CID").text());
 								}else{
 									alert($(this).find("MESSAGE").text());
 									
 								}
					});

 					
 				},
 				  error: function(xhr, textStatus, error){
 				      alert("Status:" + xhr.statusText + "  " + "Text:" +textStatus + "  " + "Error:" + error );

 				  },
 				dataType: "text",
 				async: false
 			}
 		);
}
// open remove role modal
function openRemoveRole(roleid,rowid) {
	$("#spanroleid").html(roleid);
	$("#hidrowid").val(rowid);
	$("#hidroleid").val(roleid);
	$('#removeCatRoleModal').modal('show');
}
// call ajax remove role
function removeCatRole(){
	var rowid = $("#hidrowid").val();
	var vroleid = $("#hidroleid").val();
	var vcatid = $("#hidcatid").val();
	$.ajax(
 			{
 				type: "POST",  
 				url: "removeRoleFromCategory.html",
 				data: {
 					catid: vcatid, rolename: vroleid
 				}, 
 				success: function(xml){
 					$(xml).find('CATSTATUS').each(function(){
 							
 							
 							if($(this).find("MESSAGE").text() == "SUCCESS")
 								{
									//window.location="viewCategory.html?cid=" + $(this).find("CID").text();
 									//alert($(this).find("CID").text());
 								$("#tr" + rowid).remove();
 								}else{
 									alert($(this).find("MESSAGE").text());
 									
 								}
					});

 					
 				},
 				  error: function(xhr, textStatus, error){
 				      alert("Status:" + xhr.statusText + "  " + "Text:" +textStatus + "  " + "Error:" + error );

 				  },
 				dataType: "text",
 				async: false
 			}
 		);
	
$('#removeCatRoleModal').modal('hide');
	
	
}
//open remove role modal
function openAddModal() {
	
	$('#addCatRoleModal').modal('show');
}
//call add role to category ajax
function addRoleToCategory(){
	var vroleid = $("#newroles").val();
	var vcatid = $("#hidcatid").val();
	$.ajax(
 			{
 				type: "POST",  
 				url: "addRoleToCategory.html",
 				data: {
 					catid: vcatid, rolename: vroleid
 				}, 
 				success: function(xml){
 					$(xml).find('CATSTATUS').each(function(){
 							
 							
 							if($(this).find("MESSAGE").text() == "SUCCESS")
 								{
 									
									//clear table and populate again
 									//clear dropdown and populate again
 									//$('#tabroles tbody').empty();
 									$('#tabroles').find("tr:gt(0)").remove();
 									$("#newroles option").remove();
 									
 									$('#newroles').append(new Option("Please select", " "));
 									$(xml).find('ROLEDD').each(function(){
 										
 										$('#newroles').append(new Option($(this).text(), $(this).text()));
 									});
 									$(xml).find('ROLELIST').each(function(){
 										var newrow = "<tr id='tr" + $(this).find("ROLECOUNT").text() + "'>";
 										newrow += "<td width='80%'>" + $(this).find("ROLENAME").text() + "</td>";
 										newrow += "<td width='20%'><a onclick='openRemoveRole(\"" + $(this).find("ROLENAME").text() + "\",\"" + 
 										$(this).find("ROLECOUNT").text() + "\")' class='btn btn-xs btn-danger'>REMOVE</a></td></tr>";
 										$('#tabroles tr:last').after(newrow);
 									});
 	 								
 								
 								}else{
 									alert($(this).find("MESSAGE").text());
 									
 								}
					});

 					
 				},
 				  error: function(xhr, textStatus, error){
 				      alert("Status:" + xhr.statusText + "  " + "Text:" +textStatus + "  " + "Error:" + error );

 				  },
 				dataType: "text",
 				async: false
 			}
 		);
	
$('#addCatRoleModal').modal('hide');
}
