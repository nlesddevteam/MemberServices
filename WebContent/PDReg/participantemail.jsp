<%@ page language="java"
          session="true"
          import="com.awsd.security.*,
                  com.awsd.pdreg.*,com.awsd.personnel.*"
          isThreadSafe="false"%>

<%!
  User usr = null;
  UserPermissions permissions = null;
  String msg;
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
<%}
    msg = (String) request.getAttribute("msg");
%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>
      Newfoundland &amp; Labrador English School District - Event Participants Email
    </title>

    <script language="JavaScript" src="../js/common.js"></script>
    <script language="JavaScript">
      function processing()
      {
        var cell = document.getElementById('processing');
        cell.style.display=(cell.style.display=='none') ? 'inline' : 'none';
        self.resizeTo(400, 221);
        document.email.submit();
      }
    </script>
  </head>
  <body bgcolor="#FFFFFF" marginheight="0" marginwidth="0" topmargin="0" leftmargin="0" rightmargin="0">
    <% if(msg != null) { %>
      <script language="JavaScript">
        openWindow('ConfirmMail', 'email_reply.jsp?msg=<%=msg%>', 405, 200, 0);
      </script>
    <%}%>
    <form name="email" action="sendEventParticipantsEmail.html" method="post">
      <table id='processing' style="display:none;" width="400" cellpadding="0" cellspacing="0" border="0">
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
                        <td width="51" height="51" align="left" valign="middle">
                          <IMG src="images/sending_email.gif"><BR>
                        </td>
                        <td width="249" height="51" align="left" valign="middle">
                          <span class=""><font color="#003399"><b>Sending Email...</b></font></span><BR>
                        </td>
                      </tr>
                      <tr>
                        <td width="300" align="center" valign="middle" colspan="2">
                          <img src="images/spacer.gif" width="1" height="15"><BR><BR>
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

      <table  align="center" cellpadding="1" cellspacing="0" border="0" width="255">
      <tr>
      <td bgcolor="#4682B4">
      <table  width="255" cellpadding="0" cellspacing="0" border="0">
        <tr>
          <td bgcolor="#f4f4f4" width="55">
            From:
          </td>
          <td bgcolor="#FFFFFF">
            <input type="text" name="from" size="70" value="<%=usr.getPersonnel().getEmailAddress()%>" readonly>
          </td>
        </tr>
        <tr>
          <td bgcolor="#f4f4f4" width="55">
            To:
          </td>
          <td bgcolor="#FFFFFF">
            <textarea name="to" cols="53" rows="5"><%=request.getAttribute("to")%></textarea>
          </td>
        </tr>
        <tr>
          <td  bgcolor="#f4f4f4" width="55">
            Cc:
          </td>
          <td  bgcolor="#FFFFFF">
            <textarea name="cc" cols="53" rows="1"></textarea>
          </td>
        </tr>
        <tr>
          <td  bgcolor="#f4f4f4" width="55">
            Bcc:
          </td>
          <td bgcolor="#FFFFFF">
            <textarea name="bcc" cols="53" rows="1"></textarea>
          </td>
        </tr>
        <tr>
          <td  bgcolor="#f4f4f4" width="55">
            Subject:
          </td>
          <td bgcolor="#FFFFFF">
            <input type="text" name="subject" size="71" value='<%=request.getAttribute("subject")%>'>
          </td>
        </tr>
        <tr>
          <td  bgcolor="#f4f4f4" colspan="2">
            Message:
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <textarea name="message" cols="60" rows="15"></textarea>
          </td>
        </tr>
        <tr>
          <td valign="middle" colspan="2" align="right">
            <img name="login" src="images/send_01.jpg" 
                onMouseover="src='images/send_02.jpg'" 
                onMouseout="src='images/send_01.jpg'" 
                border="0" 
                style="cursor:hand;"
                onclick="processing();">
          </td>
        </tr>
      </table>
      </td>
      </tr>
      </table>
    </form>
  </body>
</html>
