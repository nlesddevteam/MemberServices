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
    if(!(usr.getUserPermissions().containsKey("EFILE-VIEW")))
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
  <title>E-File Repository - Member Services/Eastern School District</title>
  </head>
  <frameset framespacing="0" border="0" rows="100%,*" frameborder="0">
      <frame name="cart_main" src="viewQuestionCartContents.html?page=<%=request.getParameter("page")%>" scrolling="vertical" marginwidth="0" marginheight="0" noresize>
      <frame name="cart_hidden" src=""  marginwidth="0" marginheight="0" noresize>
  </frameset>
  <noframes>
      <body>
        <p>This page uses frames, but your browser doesn't support them.</p>
      </body>
    </noframes>
</html>