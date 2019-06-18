<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 com.esdnl.mrs.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"
        isThreadSafe="false"%>

<%!
  User usr = null;
%>
<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("MAINTENANCE-SCHOOL-VIEW")))
    {
%>    <jsp:forward page="/MemberServices/memberServices.html"/>
<%  }
  }
  else
  {
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%}
 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Eastern School District Maintenance/Repair - Member Services</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/maint.css";</style>
    <script language="JavaScript" src="js/common.js"></script>
	</head>
	<body style="margin:0px;" onload="if(top != self){resizeIFrame('maincontent_frame', 317);}">
    <form id="add_request_form" name="add_request_form" action="addRequest.html" method="post">
      <input type="hidden" id="op" name="op" value="CONFIRM">
      <table width="100%" height="100%" cellpadding="0" cellspacing="0" align="center" valign="top">
        <tr><td align="center" style="padding-bottom:5px;"><img src="images/school_reports_title.gif"><br></td></tr>
        <tr>
          <td id="maincontent" align="center" width="100%">
            Coming Soon! <br><br> Please send along your suggestions for reports you feel would be useful.
          </td>
        </tr>
      </table> 
    </form>
	</body>
</html>