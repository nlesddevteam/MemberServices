<%@ page language="java"
          session="true"
          isThreadSafe="false"%>
<html>
  <head>
    <title>Email Response</title>
    <link rel="stylesheet" href="css/style.css">
  </head>
  <body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
    <script language="JavaScript">
      self.resizeTo(400, 221);
    </script>
    <table width="400" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <td width="400" align="left" valign="top" colspan="3">
          <img src="images/email_response_top.gif"><BR>
        </td>
      </tr>
      <tr>
        <td width="1" align="left" valign="top" bgcolor="#000000">
          <img src="images/spacer.gif" width="1" height="1"><BR>
        </td>
        <td width="398" align="center" valign="top" bgcolor="#FFFFFF">
          <table width="300" cellpadding="0" cellspacing="0" border="0">
            <tr>
              <td width="300" height="40" align="left" valign="middle">
                <span class=""><font color="#003399"><b><%=request.getAttribute("msg")%></b></font></span><BR>
              </td>
            </tr>
            <tr>
              <td width="300" align="center" valign="middle">
                <img src="images/ok_01.gif" 
                onMouseover="src='images/ok_02.gif'" 
                onMouseOut="src='images/ok_01.gif'"
                style="cursor:hand;"
                border="0" alt="OK"
                onclick="self.close();"><BR><BR>
              </td>
            </tr>
          </table>
        </td>
        <td width="1" align="left" valign="top" bgcolor="#000000">
          <img src="images/spacer.gif" width="1" height="1"><BR>
        </td>
      </tr>
      <tr>
        <td width="400" align="left" valign="top" colspan="3">
          <img src="images/email_response_bottom.gif"><BR>
        </td>
      </tr>
    </table>