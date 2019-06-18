<%@ page language="java"
         import="com.awsd.pdreg.*,com.awsd.security.*,java.text.*"
         isThreadSafe="false"%>

<%! 
    User usr = null;
    UserPermissions permissions = null;
    Event evt = null;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    int width;
%>

<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    permissions = usr.getUserPermissions();
    if(!(permissions.containsKey("CALENDAR-VIEW") 
          && permissions.containsKey("CALENDAR-SCHEDULE")))
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

  evt = (Event) request.getAttribute("evt");

  if(request.getAttribute("msg") != null)
  {
    width = 290;
  }
  else
  {
    width = 175;
  }  
%>
    
<html>
  <head>
    <title>Newfoundland &amp; Labrador English School District - Events Calendar: Delete an Event</title>
    <style>
      td {font-family: Arial, Helvetica, sans-serif; font-size: 11px; line-height: 16px; color: #000000; font-weight:bold;}
    </style>
    <script langauge="JavaScript" src="../js/common.js"></script>
    <script langauge="JavaScript">
      clicked = false;
    </script>
  </head>

  <body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
    <form action="deleteEvent.html" name="delevt" method="post">
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
          <img src="images/delete_pt1.gif"><img src="images/title_pt2.jpg"><br>
        </td>
      </tr>
    </table>

    <table width="400" cellpadding="0" cellspacing="5" border="0">
      <tr>
        <td width="100" valign="top" bgcolor="#f4f4f4">
          &nbsp;&nbsp;Type:
        </td>
        <td width="300" valign="top" bgcolor="#FFFFFF">
          <%=(evt==null)?"&nbsp;":evt.getEventType().getEventTypeName()%>
        </td>
      </tr>
      <tr>
        <td width="100" valign="top" bgcolor="#f4f4f4">
          &nbsp;&nbsp;Name:
        </td>
        <td width="300" valign="top" bgcolor="#FFFFFF">
          <%=(evt==null)?"&nbsp;":evt.getEventName()%>
        </td>
      </tr>
      <tr>
        <td width="100" valign="top" bgcolor="#f4f4f4">
          &nbsp;&nbsp;Description:
        </td>
        <td width="300" valign="top" bgcolor="#FFFFFF">
          <%=(evt==null)?"&nbsp;":evt.getEventDescription()%>
        </td>
      </tr>
      <tr>
        <td width="100" valign="top" bgcolor="#f4f4f4">
          &nbsp;&nbsp;Location:
        </td>
        <td width="300" valign="top" bgcolor="#FFFFFF">
          <%=(evt==null)?"&nbsp;":evt.getEventLocation()%>
        </td>
      </tr>
      <tr>
        <td width="100" valign="top" bgcolor="#f4f4f4">
          &nbsp;&nbsp;Start Date:
        </td>
        <td width="300" valign="top" bgcolor="#FFFFFF">
          <%=(evt==null)?"&nbsp;":sdf.format(evt.getEventDate())%>
        </td>
      </tr>
      <% if((evt != null) && (evt.getEventEndDate() != null)) { %>
        <tr>
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;End Date:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <%=(evt==null)?"&nbsp;":sdf.format(evt.getEventEndDate())%>
          </td>
        </tr>
      <% } %>
      <% if((evt != null) && !evt.getEventStartTime().equals("UNKNOWN")) { %>
        <tr>
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;Start Time:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <%=evt.getEventStartTime()%>
          </td>
        </tr>
        <tr>
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;Finish Time:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <%=evt.getEventFinishTime()%>
          </td>
        </tr>
      <% } %>
      <% if((evt != null) && (evt.isCloseOutDaySession() || evt.isPDOpportunity())) { %>
        <tr>
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;Max Participants:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <% if(evt.getEventMaximumParticipants() > 0) { %>
              <%=evt.getEventMaximumParticipants()%>
            <% } else { %>
              UNLIMITED
            <% } %>
          </td>
        </tr>
        <tr>
          <td width="100" valign="top" bgcolor="#f4f4f4">
            # Registered:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <%=evt.getRegistrationCount()%>
          </td>
        </tr>
      <% } %>
    </table>

    <table width="400" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <td width="400" valign="middle" bgcolor="#f4f4f4">
          <table>
            <tr>
              <td align="left" width="<%=width%>">
                <img name="processing" src="images/spacer.gif">
                <font color="#FF0000">
                  <% if(request.getAttribute("msg") != null) { %>
                    <%= request.getAttribute("msg") %>
                  <% } %>
                </font>
              </td>
              <td align="right">                
                  <input type="hidden" name="confirmed" value="true">
                  <% if(request.getAttribute("msg") == null) { %>
                    <input type="hidden" name="id" value="<%=evt.getEventID()%>">
                    <img name="confirm" src="images/confirm_01.jpg" 
                        onmouseover="src='images/confirm_02.jpg';" 
                        onmouseout="src='images/confirm_01.jpg';"
                        onmousedown="src='images/confirm_03.jpg';"
                        onmouseup="src='images/confirm_02.jpg';"
                        onclick="onClick(document.delevt);">
                    <img name="cancel" src="images/cancel_01.jpg" 
                        onmouseover="src='images/cancel_02.jpg';" 
                        onmouseout="src='images/cancel_01.jpg';"
                        onmousedown="src='images/cancel_03.jpg';"
                        onmouseup="src='images/cancel_02.jpg';"
                        onclick="self.close();">
                  <% } else { %>
                    <img src="images/close_01.jpg" 
                        onmouseover="src='images/close_02.jpg';" 
                        onmouseout="src='images/close_01.jpg';"
                        onmousedown="src='images/close_03.jpg';"
                        onmouseup="src='images/close_02.jpg';"
                        onclick="self.opener.opener.location.reload(); self.opener.close(); self.close();">
                  <% } %>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
    </form>
  </body>
</html>