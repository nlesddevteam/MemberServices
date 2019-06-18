<%@ page language="java" 
         session="true"
         import="com.awsd.security.*"
         isThreadSafe="false"%>

<%!
  User usr = null;
  UserPermissions permissions = null;
%>

<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    permissions = usr.getUserPermissions();
    if(!(permissions.containsKey("CALENDAR-VIEW") 
        && (permissions.containsKey("CALENDAR-SCHEDULE")
            || permissions.containsKey("CALENDAR-VIEW-PARTICIPANTS"))))
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
  <title>District Calendar - Members Services/Newfoundland &amp; Labrador English School District</title>
  </head>
  <frameset framespacing="0" border="0" rows="100%,*" frameborder="0">
      <frame name="participants_main" src='viewEventParticipants.html?id=<%=request.getParameter("id")%>' scrolling="vertical" marginwidth="0" marginheight="0" noresize>
      <frame name="participants_hidden" src=""  marginwidth="0" marginheight="0" noresize>
  </frameset>
  <noframes>
      <body>
        <p>This page uses frames, but your browser doesn't support them.</p>
      </body>
    </noframes>
</html>