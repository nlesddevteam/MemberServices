<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
                 java.util.*,
                 java.io.*,
                 java.text.*,com.awsd.personnel.*"%>

<%!
  User usr = null;
  SecretQuestion sq = null;
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
    
  sq = (SecretQuestion) request.getAttribute("SECRETQUESTION");
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
    <form id="set_question_form" method="post" action="setSecretQuestion.html">
      <input type="hidden" name="op" value="CONFIRMED">
      <table width="620" cellpadding="0" cellspacing="0" align="left" valign="top">
        <tr>
          <td width="100%" id="maincontent" align="center" valign="top">
            <table width="100%" cellpadding="0" cellspacing="0" align="center" valign="top">
              <tr>
                <td width="100%" align="left" valign="top">
                 <img src="images/secret_question_header.gif"><br>
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
                                  <td width="75" class="label">Question:</td>
                                  <td width="*" ><input type="text" name="question" id="question" class="requiredinput" value="<%=(sq != null)?sq.getQuestion():""%>" style="width:300px;"></td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                          <tr>
                            <td>
                              <table width="100%" cellpadding="0" cellspacing="0">
                                <tr>
                                  <td width="75" class="label">Answer: </td>
                                  <td width="*" ><input type="text" name="answer" id="answer" class="requiredinput" value="<%=(sq != null)?sq.getAnswer():""%>" style="width:300px;"></td>
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
                    <%}else{%>
                      <tr>
                        <td width="100%" align="center" valign="bottom" class="info">
                          <br>NOTE: It is very important that your secret question be unique. Someone
                          who knows the answer to your secret question can reset your password.
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