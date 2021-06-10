<%@ page language="java" isErrorPage="true" import="java.lang.*"%>

<%!
  Exception e = null;
%>
<%
  e = (Exception) request.getAttribute("err");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>
Eastern School District - Event Calendar Error Notification
</title>
</head>
<body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
    <table width="400" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <td width="400" valign="top" bgcolor="#646464">
          <img src="images/spacer.gif" width="1" height="10"><br>
        </td>
      </tr>
      <tr>
        <td width="400" height="1" bgcolor="#FFFFFF">
          <img src="images/spacer.gif" width="1" height="1"><br>
        </td>
      </tr>
      <tr>
        <td width="400" valign="top">
          <img src="images/calendarerror_pt1.gif"><img src="images/title_pt2.jpg"><br>
        </td>
      </tr>
    </table>

    <table width="400" cellpadding="1" cellspacing="2" border="0">
      <tr>
        <td width="100" valign="top" bgcolor="#f4f4f4">
          &nbsp;&nbsp;Error:
        </td>
        <td width="300" valign="top" bgcolor="#FFFFFF">
          <font color="#FF0000"><b><%=e.getMessage()%><BR>Please refresh your calendar view.</b></font>
        </td>
      </tr>
    </table>
    <table width="400" cellpadding="1" cellspacing="2" border="0">
      <tr>
        <td width="400" valign="middle" bgcolor="#f4f4f4">
          <table width="400" cellpadding="0" cellspacing="0" border="0">
            <tr>
              <td align="center" valign="middle">  
                    <img src="images/close_01.jpg" 
                        onmouseover="src='images/close_02.jpg';" 
                        onmouseout="src='images/close_01.jpg';" 
                        onmousedown="src='images/close_03.jpg';"
                        onmouseup="src='images/close_02.jpg';"
                        onclick="self.opener.location.reload(); self.close();">
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
</html>
