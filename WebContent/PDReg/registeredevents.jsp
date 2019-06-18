<%@ page  language="java" 
          session="true" 
          import="java.util.*, java.text.*, com.awsd.pdreg.*,com.awsd.security.*" 
          isThreadSafe="false"%>

<%!
  // months as they appear in the calendar's title
  final String ARR_MONTHS[] = {"January", 
                                "February", 
                                "March", 
                                "April", 
                                "May", 
                                "June", 
                                "July", 
                                "August", 
                                "September", 
                                "October", 
                                "November", 
                                "December"};

  // week day titles as they appear on the calendar
  final String ARR_WEEKDAYS[] = {"Sunday", 
                                  "Monday", 
                                  "Tuesday", 
                                  "Wednesday", 
                                  "Thursday", 
                                  "Friday", 
                                  "Saturday"};
  
  // path to the directory where calendar images are stored. trailing slash req.
  final String STR_ICONPATH = "images/";

  int dayofweek;

  Calendar cur = null;
  SimpleDateFormat sdf = null;

  RegisteredEvents events = null;
  Event evt = null;
  Iterator iter = null;
  Date now = null;
  User usr = null;
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
  
  events = (RegisteredEvents) request.getAttribute("RegisteredEvents");
  iter = (events.entrySet()).iterator();
  
  cur =  Calendar.getInstance();
  dayofweek = cur.get(Calendar.DAY_OF_WEEK);
%>

<html>

<head>
<title>Newfoundland &amp; Labrador English School District - Daily Calendar</title>
<style>
	td {font-family: Tahoma, Verdana, sans-serif; font-size: 12px;}
</style>

<script language="JavaScript" src="../js/common.js"></script>
</head>

<body bgcolor="#FFFFFF" marginheight="5" marginwidth="5" topmargin="5" leftmargin="5" rightmargin="5">
<table class="clsOTable" cellspacing="0" border="0" width="100%">
  <tr>
    <td bgcolor="#4682B4">
      <table cellspacing="1" cellpadding="3" border="0" width="100%">
        <tr>
          <td colspan="2">
            <table cellspacing="0" cellpadding="0" border="0" width="100%">
              <tr>
                <td>
                  &nbsp;
                </td>
                <td align="center" width="100%">
                  <font size="3" color="#ffffff"><b><%=ARR_WEEKDAYS[dayofweek-1]%>,&nbsp;<%=ARR_MONTHS[cur.get(Calendar.MONTH)]%>&nbsp;<%=cur.get(Calendar.DATE)%>&nbsp;<%=cur.get(Calendar.YEAR)%></b></font>
                </td>
                <td>
                  &nbsp;
                </td>
              </tr>
            </table>
          </td>
        </tr>
<%      if(!iter.hasNext())
        {
%>        <tr>
              <td bgcolor="#ffffff" align="center">No Registered Events.</td>
          </tr>
<%      }
        else
        {
          while (iter.hasNext())
          {
            evt = (Event) (((Map.Entry)iter.next()).getValue());
%>          <tr>
              <td bgcolor="#ffffff" align="left" width="25%">Type</td>
              <td bgcolor="#ffffff" align="left"><%=evt.getEventType().getEventTypeName()%></td>
            </tr>
            <tr>
              <td bgcolor="#ffffff" align="left" width="25%">Title</td>
              <td bgcolor="#ffffff" align="left"><%=evt.getEventName()%></td>
            </tr>
            <tr>
              <td bgcolor="#ffffff" align="left">Description</td>
              <td bgcolor="#ffffff" align="left"><%=evt.getEventDescription()%></td>
            </tr>
            <tr>
              <td bgcolor="#ffffff" align="left">Start Date</td>
              <td bgcolor="#ffffff" align="left"><%=evt.getEventDate()%></td>
            </tr>
            <% if(evt.getEventEndDate() != null) { %>
            <tr>
              <td bgcolor="#ffffff" align="left">End Date</td>
              <td bgcolor="#ffffff" align="left"><%=evt.getEventEndDate()%></td>
            </tr>
            <% } %>
            <tr>
              <td bgcolor="#ffffff" align="left">Event Location</td>
              <td bgcolor="#ffffff" align="left"><%=evt.getEventLocation()%></td>
            </tr>
            <tr>
              <td bgcolor="#ffffff" align="left">Event host</td>
              <td bgcolor="#ffffff" align="left"><%=evt.getScheduler().getFullName()%></td>
            </tr>
            <% if(!evt.getEventStartTime().equals("UNKNOWN")) { %>
            <tr>
              <td width="100" valign="top" bgcolor="#FFFFFF">
                &nbsp;&nbsp;Start Time:
              </td>
              <td width="300" valign="top" bgcolor="#FFFFFF">
                <%=evt.getEventStartTime()%>
              </td>
            </tr>
            <tr>
              <td width="100" valign="top" bgcolor="#FFFFFF">
                &nbsp;&nbsp;Finish Time:
              </td>
              <td width="300" valign="top" bgcolor="#FFFFFF">
                <%=evt.getEventFinishTime()%>
              </td>
            </tr>
            <% } %>
            <% if(evt.isCloseOutDaySession() || evt.isPDOpportunity()) { %>
            <tr>
              <td width="100" valign="top" bgcolor="#FFFFFF">
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
            <tr>
              <td bgcolor="#F4F4F4" align="right" colspan="2">                 
                <img src="images/deregister_01.jpg" 
                     onmouseover="src='images/deregister_02.jpg';"
                     onmouseout="src='images/deregister_01.jpg';"
                     onmousedown="src='images/deregister_03.jpg';"
                     onmouseup="src='images/deregister_02.jpg';"
                     onclick="openWindow('deregistration', 'deregisterEvent.html?id=<%=evt.getEventID()%>', 430, 350, 1);">
              </td>
            </tr>
<%        }
        }
%>    <tr>
        <td colspan="2" align="center">
          <img src="images/closeblue_01.jpg" 
               onmouseover="src='images/closeblue_02.jpg';" 
               onmouseout="src='images/closeblue_01.jpg';"
               onmousedown="src='images/closeblue_03.jpg';"
               onmouseup="src='images/closeblue_02.jpg';"
               onclick="self.close();">
        </td>
      </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>