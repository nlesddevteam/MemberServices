<%@ page language="java"
          import="com.awsd.pdreg.*,com.awsd.security.*, java.text.*, java.util.*"
          isThreadSafe="false"%>

<%! 
    User usr = null;
    Event evt = null;
    String img;
    int width;
    SimpleDateFormat sdf = null;
%>

<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("CALENDAR-VIEW")))
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
  
  img = "images/deregister_pt1.gif";
 
  sdf = new SimpleDateFormat("MM/dd/yyyy");
  
  if(request.getAttribute("msg") != null)
  {
    width = 287;
  }
  else
  {
    width = 185;
  }  
%>
    
<html>
  <head>
    <title>Newfoundland &amp; Labrador English School District - Events Calendar: Register for an Event</title>

    <style>
      td {font-family: Arial, Helvetica, sans-serif; font-size: 11px; line-height: 16px; color: #000000; font-weight:bold;}
    </style>
    <script langauge="JavaScript" src="../js/common.js"></script>
    <script langauge="JavaScript">
      clicked = false;
    </script>
  </head>

  <body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
   <form action="deregisterEvent.html" name="deregister" method="post">
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
          <img src="<%=img%>"><img src="images/title_pt2.jpg"><br>
        </td>
      </tr>
    </table>

    <table width="400" cellpadding="0" cellspacing="5" border="0">
      <tr>
        <td width="100" valign="top" bgcolor="#f4f4f4">
          &nbsp;&nbsp;Type:
        </td>
        <td width="300" valign="top" bgcolor="#FFFFFF">
          <%=evt.getEventType().getEventTypeName()%>
        </td>
      </tr>
      <tr>
        <td width="100" valign="top" bgcolor="#f4f4f4">
          &nbsp;&nbsp;Title:
        </td>
        <td width="300" valign="top" bgcolor="#FFFFFF">
          <%=evt.getEventName()%>
        </td>
      </tr>
      <tr>
        <td width="100" valign="top" bgcolor="#f4f4f4">
          &nbsp;&nbsp;Description:
        </td>
        <td width="300" valign="top" bgcolor="#FFFFFF">
          <%=evt.getEventDescription()%>
        </td>
      </tr>
      <tr>
        <td width="100" valign="top" bgcolor="#f4f4f4">
          &nbsp;&nbsp;Location:
        </td>
        <td width="300" valign="top" bgcolor="#FFFFFF">
          <%=evt.getEventLocation()%>
        </td>
      </tr>
      <tr>
        <td width="100" valign="top" bgcolor="#f4f4f4">
          &nbsp;&nbsp;Host:
        </td>
        <td width="300" valign="top" bgcolor="#FFFFFF">
          <%=evt.getScheduler().getFullName()%>
        </td>
      </tr>
      <tr>
        <td width="100" valign="top" bgcolor="#f4f4f4">
          &nbsp;&nbsp;Start Date:
        </td>
        <td width="300" valign="top" bgcolor="#FFFFFF">
          <%=sdf.format(evt.getEventDate())%>
        </td>
      </tr>
      <% if(evt.getEventEndDate() != null) { %>
        <tr>
          <td width="100" valign="top" bgcolor="#f4f4f4">
            &nbsp;&nbsp;End Date:
          </td>
          <td width="300" valign="top" bgcolor="#FFFFFF">
            <%=sdf.format(evt.getEventEndDate())%>
          </td>
        </tr>
      <% } %>
      <% if(!evt.getEventStartTime().equals("UNKNOWN")) { %>
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
      <% if(evt.isCloseOutDaySession() || evt.isPDOpportunity()) { %>
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
      <% } %>
    </table>
    
    <table width="400" cellpadding="1" cellspacing="5" border="0">
      <tr>
        <td width="400" valign="middle" bgcolor="#f4f4f4">
          <table width="400" cellpadding="0" cellspacing="0" border="0">
            <tr>
              <td align="left" valign="middle" width="<%=width%>">
                <img name="processing" src="images/spacer.gif">
                <font color="#FF0000">
                  <% if(request.getAttribute("msg") != null) { %>
                    <%= request.getAttribute("msg") %>
                  <% } %>
                </font>
              </td>
              <td valign="middle">
                  <% if(request.getAttribute("msg") == null) { %>
                    <input type="hidden" name="id" value="<%=evt.getEventID()%>">
                    <input type="hidden" name="confirmed" value="true">
                    <img name="confirm" src="images/confirm_01.jpg" 
                        onmouseover="src='images/confirm_02.jpg';" 
                        onmouseout="src='images/confirm_01.jpg';"
                        onmousedown="src='images/confirm_03.jpg';"
                        onmouseup="src='images/confirm_02.jpg';"
                        onclick="onClick(document.deregister);">
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
                        onclick="self.opener.location.reload(); self.close();">
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