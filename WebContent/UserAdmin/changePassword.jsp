<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
                 java.util.*,
                 java.io.*,
                 java.text.*,com.awsd.personnel.*"%>

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
	<body style="margin:0px;background-color:#E7E7DD;" onload="if(top != self){resizeIFrame('user_opt_frame', 0);}">
    <form id="set_question_form" method="post" action="changePassword.html">
      <input type="hidden" name="op" value="CONFIRMED">
      <table width="620" cellpadding="0" cellspacing="0" align="left" valign="top">
        <tr>
          <td width="100%" id="maincontent" align="center" valign="top">
            <table width="100%" cellpadding="0" cellspacing="0" align="center" valign="top">
              <tr>
                <td width="100%" align="left" valign="top">
                 <img src="images/change_password_header.gif"><br>
                </td>
              </tr>
              <tr>
                <td width="100%" align="left" valign="top" style="padding-left:9px;">
                   <table width="600" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="100%" align="left" style="background-color:#F7F7EF; border-left: solid 1px #233154; border-right:solid 1px #233154;">
                        <table width="100%" cellpadding="0" cellspacing="5">
                          <tr>
                            <td>
                              <table width="100%" cellpadding="0" cellspacing="0">
                                <tr>
                                  <td width="115" class="label">New Password:</td>
                                  <td width="*" ><input type="password" name="password" id="password" class="requiredinput" value="" style="width:300px;"></td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                          <tr>
                            <td>
                              <table width="100%" cellpadding="0" cellspacing="0">
                                <tr>
                                  <td width="115" class="label">Confirm: </td>
                                  <td width="*" ><input type="password" name="confirm" id="confirm" class="requiredinput" value="" style="width:300px;"></td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td width="100%" align="center" valign="bottom" style="background-color:#F7F7EF; border-left: solid 1px #233154; border-right:solid 1px #233154;">
                        <br><img src="images/submit_button.gif" style="cursor:hand;" onclick="document.forms[0].submit();">
                      </td>
                    </tr>
                    <%if(request.getAttribute("msg") != null){%>
                      <tr>
                        <td width="100%" align="center" valign="bottom" class="info">
                          <br><%=request.getAttribute("msg").toString()%>
                        </td>
                      </tr>
                    <%}%>
                   </table>
                </td>
              </tr>
              <tr>
                <td width="100%" align="left" valign="top">
                 <img src="images/option_footer.gif"><br>
                </td>
              </tr>
            </table> 
          </td>
        </tr>
      </table> 
    </form>
	</body>
</html>