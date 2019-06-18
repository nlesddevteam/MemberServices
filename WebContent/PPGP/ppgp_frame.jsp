<%@ page language="java"
         isThreadSafe="false"%>

<%
  if(session.getAttribute("usr") == null)
  {
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%
  }
%>

<html>
  <head>
  <title>Professional Learning Plan</title>
  </head>
  <frameset name="ppgpframe" framespacing="0" border="0" cols="177,*" frameborder="NO">
    <frame name="ppgpmenu" target="ppgpmain" src="ppgp_menu.jsp" scrolling="no" marginwidth="0" marginheight="0" noresize>
  	<frame name="ppgpmain" src="ppgp_index.jsp">
    <noframes>
      <body>
        <p>This page uses frames, but your browser doesn't support them.</p>
      </body>
    </noframes>
  </frameset>
</html>