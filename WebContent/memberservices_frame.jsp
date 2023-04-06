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
   
		<script  type="text/javascript" src="https://accounts.google.com/gsi/client?onload=appStart" async defer></script>

	
  </head>
  
  <frameset rows="32,*" framespacing="0" frameborder="0" name="memberservicesframe">
    <frame src="mservices_menu.jsp" name="memberservicesmenu" id="memberservicesmenu" frameborder="0" noresize marginwidth="0" marginheight="0" scrolling="no">
  	<frame src="navigate.jsp" name="memberservicesmain" id="memberservicesmain" frameborder="0" scrolling="Auto" marginwidth="0" marginheight="0">
    <noframes>
      <body>
        <p>This page uses frames, but your browser doesn't support them.</p>
      </body>
    </noframes>
  </frameset>

</html>