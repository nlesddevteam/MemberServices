<%@ page language="java" 
         session="true"
         import="com.awsd.security.*"%>

<%!
  User usr = null;
%>

<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("MEMBERADMIN-VIEW")))
    {
%>    <jsp:forward page="/MemberServices/memberServices.html"/>
<%  }
  }
  else
  {
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%}%>

<html>
  <head>
  <title>Members Administration - Members Services/Eastern School District</title>
  </head>
  <frameset framespacing="0" border="0" cols="325,*" frameborder="no">
    <frameset framespacing="0" border="0" rows="86,*" frameborder="no">
      <frame name="title" target="main" src="title.jsp" scrolling="no" marginwidth="0" marginheight="0" noresize="noresize">
      <frame name="menu" target="main" src="menu.jsp" scrolling="yes" marginwidth="0" marginheight="0" noresize="noresize">
    </frameset>
  	<frame name="main" src="Apps/Personnel/personnel_admin_view.jsp">
    <noframes>
      <body>
        <p>This page uses frames, but your browser doesn't support them.</p>
      </body>
    </noframes>
  </frameset>
</html>