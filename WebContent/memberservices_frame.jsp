<%@ page language="java"
         isThreadSafe="false"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck />

<!DOCTYPE html>
<html>
  <head>
  	<title>NLESD STAFFROOM</title>
  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <meta charset="utf-8">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <meta name="description" content="MemberServices Navigation Menu Page">
    <meta http-equiv="refresh" content="5; URL=navigate.jsp">
	<script  type="text/javascript" src="https://accounts.google.com/gsi/client?onload=appStart" async defer></script>

	<script>
	window.onload = function() {
		  window.location.href = "navigate.jsp"; //Go to Navigate page. If javascript dont work, meta refresh above will redirect in 5 seconds.
		};	
	</script>
  </head>
    
  <body>
  
  </body>
 
</html>