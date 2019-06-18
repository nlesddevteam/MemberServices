<%@ page language="java"
         isThreadSafe="false"%>

<%
  if(session.getAttribute("usr") == null)
  {
%>  <jsp:forward page="signin.jsp">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%
  }
%>

<html>
  <head>
  <title>Members Services - Eastern School District</title>
  </head>
  <frameset name="memberservicesframe" framespacing="0" border="0" rows="40,*" frameborder="no">
    <frame name="memberservicesmenu" target="memberservicesmain" src="mservices_menu.jsp" scrolling="no" marginwidth="0" marginheight="0" noresize>
  	<frame name="memberservicesmain" src="navigate.jsp">
    <noframes>
      <body>
        <p>This page uses frames, but your browser doesn't support them.</p>
      </body>
    </noframes>
  </frameset>
</html>