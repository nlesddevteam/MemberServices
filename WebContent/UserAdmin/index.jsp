<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"%>

<%!
  User usr = null;
%>
<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("USER-ADMIN-VIEW")))
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
		<title>Eastern School District User Admin - Member Services</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/user_admin.css";</style>
    <script language="JavaScript" src="js/common.js"></script>
    
	</head>
	<body style="margin-top:-30px;">
    <form>
      <table width="800" cellpadding="0" cellspacing="0" align="center" valign="top">
        <tr>
          <td width="100%" height="100%" id="maincontent" align="left" valign="top">
            <img src="../images/spacer.gif" width="1" height="125"><br>
            <table width="100%" height="100%" cellpadding="0" cellspacing="0" align="center" valign="top"  style="background-color:#E7E7DD;">
              <tr>
                <td width="100%" align="left" valign="top">
                 <img src="images/header.gif"><br>
                </td>
              </tr>
              <tr>
                <td width="100%" align="left" valign="top">
                 <table with="100%" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="246" align="left">
                      <img src="images/account_options_start.gif"><br>
                    </td>
                    <td width="431" align="left" style="background-color:#38526B;">
                      <table with="100%" cellpadding="0" cellspacing="0">
                        <tr>
                          <td align="center" style="border-right:solid 1px #cccccc;padding-right:5px;padding-left:5px;"><a href="" onclick="alert('Coming soon!!'); return false;" class="option">User<br>Profile</a></td>
                          <td align="center" style="border-right:solid 1px #cccccc;padding-right:5px;padding-left:5px;"><a href="changePassword.html" target="user_opt_frame" class="option">Change<br>Password</a></td>
                          <td align="center" style="padding-left:5px;"><a href="setSecretQuestion.html" target="user_opt_frame" class="option">Secret<br>Question</a></td>
                        </tr>
                      </table>
                    </td>
                    <td width="123" align="left">
                      <img src="images/account_options_end.gif"><br>
                    </td>
                  </tr>
                  <tr>
                    <td colspan="3" height="100%" style="padding:15px;">
                      <iframe name="user_opt_frame" width="100%" frameborder="0" height="250" scrolling="no" src="setSecretQuestion.html" style="background-color:#E7E7DD;"></iframe>
                    </td>
                  </tr>
                 </table>
                </td>
              </tr>
              <tr>
                <td width="100%" align="left" valign="top">
                 <img src="images/footer.gif"><br>
                </td>
              </tr>
            </table> 
          </td>
        </tr>
      </table> 
    </form>
	</body>
</html>