<%@ page language="java" session="true"         
         import="java.util.*,com.awsd.security.*,com.awsd.school.*,com.awsd.personnel.*,
                 java.text.*"%>
<%!
  User usr = null;
  String title;
  String prompt;
  String action;
%>
<%
  usr = (User) session.getAttribute("usr");
  if(usr == null)
  {
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%}

  title = request.getParameter("title");
  if(title == null)
    title = "Member Services Confirmation";
    
  prompt = request.getParameter("prompt");
  if(prompt == null)
    prompt = "Are you sure you want to do this?";

  action = request.getParameter("action");
%>

<html>
  <head>
    <title><%=title%></title>
  </head>
  <body>
    <form name="confirm_form" method="post" action="<%=action%>">
      <table align="center" valigh="middle" width="100%" cellpadding="0" cellspacing="0">
        <tr>
          <td width="60" align="center"><img src="images/stop.gif"></td>
          <td width="*" align="left"><%=prompt%></td>
        </tr>
      </table>
    </form>
  </body>
</html>