<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Confirmation of Pledge</title>
<script language="Javascript" src="../js/jquery-1.6.2.min.js"></script>

<script language="Javascript">
$( document ).ready(function() {
	var prid = window.location.search.substring(5);
	$.ajax(
 			{
 				type: "POST",  
 				url: "cancelAntiBullyingPledge.html",
 				data: {
 					rid: prid
 				}, 
 				success: function(xml){
 					$(xml).find('INFO').each(function(){
 							var response = $(this).find("MESSAGE").text();
 							if(response == "PLEDGE DELETED")
 								{
 							    document.getElementById("error").style.display = 'none';
 							    document.getElementById("success").style.display = 'block';
	                   				
 								}else{
 								    document.getElementById("success").style.display = 'none';
 								    document.getElementById("error").style.display = 'block';
 									
 								}
					});
				},
 				  error: function(xhr, textStatus, error){
 				      alert(xhr.statusText);
 				      alert(textStatus);
 				      alert(error);
 				  },
 				dataType: "text",
 				async: false
 			}
 		);
});
</script>
</head>
<body>
					<div id="success" style="display:none;">
					<h1>You NLESD Bullying Pledge has been removed.</h1>
					</div>
					<div id="error" style="display:none;">
					<h1>There was an error trying to remove your pledge, please try again later.</h1>
					</div>
</body>
</html>