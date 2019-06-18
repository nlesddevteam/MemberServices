<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*"%>

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
    <form>
      <table width="100%" height="100%" cellpadding="0" cellspacing="0" align="center" valign="top">
        <tr><td align="center" style="padding-bottom:5px;"><img src="images/admin_utility_menu_title.gif"><br></td></tr>
        <tr>
          <td id="maincontent" align="center">
            <table width="200" height="100%" cellpadding="5" cellspacing="0" align="center" valign="top">
              <tr>
                <td width="26" valign="middle"><img src="images/arrow_bullet_orange.gif"><br></td>
                <td width="*" align="left"><a href="" class="admin_menu_item">Add Request Type</a></td>
              </tr>
              <tr>
                <td width="26" valign="middle"><img src="images/arrow_bullet_orange.gif"><br></td>
                <td width="*" align="left"><a href="" class="admin_menu_item">Add Category</a></td>
              </tr>
            </table>
          </td>
        </tr>
      </table> 
    </form>
	</body>
</html>