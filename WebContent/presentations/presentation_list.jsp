<%@ page language="java"
         session="true"
         import="com.awsd.security.*"
        isThreadSafe="false"%>

<%!
  User usr = null;
%>
<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("PRESENTATION-VIEW")))
    {
%>    <jsp:forward page="/MemberServices/memberServices.html"/>
<%  }
  }
  else
  {
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Presentation Directory</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/roerequest.css";</style>
	</head>
	<body style="margin-top:10px;">
    <form name="" method="post" action="">
      
      <table width="600" cellpadding="0" cellspacing="0"  align="center">
        <tr>
          <td id="form_header" width="100%" height="75">
            <img src="images/pppheader.gif"><br>
          </td>
        </tr>
        <tr>
          <td id="maincontent" width="100%">
            <table width="100%" cellpadding="0" cellspacing="6" border="0">
              <tr>
                <td width="100%">
                  <table width="100%" cellpadding="0" cellspacing="6" border="0">
                    <tr>
                      <td class="label" width="100%">
                      	<ul>
                      		<li><a href='files/fin_admin_may_2008.ppt'>Finance &amp; Administration Presentation May 2008</a></li>
                      	</ul>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </form>
	</body>
</html>
