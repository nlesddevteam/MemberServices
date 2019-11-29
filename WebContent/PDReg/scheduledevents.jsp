<%@ page  language="java" 
          session="true" 
          import="java.util.*, 
                  java.text.*, 
                  com.awsd.pdreg.*,com.awsd.security.*,
                  com.awsd.common.Utils" 
          isThreadSafe="false"%>

<%!
  // path to the directory where calendar images are stored. trailing slash req.
  final String STR_ICONPATH = "includes/img/";
%>

<%
  int dayofweek;
  Event evt = null;
  Iterator iter = null;
  //Date now = null;
  User usr = null;
  UserPermissions permissions = null;
  int id, category;
  RegisteredEvents revts = null;
  ScheduledEvents evts = null;
  CalendarLegend legend = null;
  String color;
  
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    permissions = usr.getUserPermissions();
    if(!(permissions.containsKey("CALENDAR-VIEW")))
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
  
  revts = new RegisteredEvents(usr.getPersonnel());
  id = usr.getPersonnel().getPersonnelID();
  
  evts = (ScheduledEvents) request.getAttribute("ScheduledEvents");
  iter = evts.iterator();
  
  //cur =  Calendar.getInstance();

  //dayofweek = cur.get(Calendar.DAY_OF_WEEK);

  //now = (Calendar.getInstance()).getTime();
  legend = new CalendarLegend();
  if(evts.getPersonnel() != null)
  {
    color = (String) legend.get(new Integer(evts.getPersonnel().getPersonnelID()));
  }
  else
  {
    category = evts.getDistrictEventCategory();
    switch(category)
    {
      case -1:
        color="CCCCCC";
        break;
      case -2:
        color="9999FF";
      default:
        color="000000";
    }
  }
  
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
    <td bgcolor="#<%=color%>">
      <table cellspacing="1" cellpadding="3" border="0" width="100%">
        <tr>
          <td colspan="2">
            <table cellspacing="0" cellpadding="0" border="0" width="100%">
              <tr>
                <td align="center" width="100%">
                  <font size="3" color="#ffffff"><b><%=(evts.getPersonnel()!=null)?evts.getPersonnel().getFullNameReverse():"District Calendar Events"%></b></font>
                </td>
              </tr>
            </table>
          </td>
        </tr>
<%      if(!iter.hasNext())
        {
%>        <tr>
              <td bgcolor="#ffffff" align="center">No Events Scheduled.</td>
          </tr>
<%      }
        else
        {
          while (iter.hasNext())
          {
            evt = (Event) iter.next();
            if(!evt.isPrivateCalendarEntry() 
              || (evt.isPrivateCalendarEntry() && (evt.getSchedulerID() == id))) { %>
              <tr>
                <td bgcolor="#ffffff" align="left" width="30%">Type:</td>
                <td bgcolor="#ffffff" align="left"><%=evt.getEventType().getEventTypeName()%></td>
              </tr>
              <tr>
                <td bgcolor="#ffffff" align="left" width="30%">Title:</td>
                <td bgcolor="#ffffff" align="left"><%=evt.getEventName()%></td>
              </tr>
              <tr>
                <td bgcolor="#ffffff" align="left">Description:</td>
                <td bgcolor="#ffffff" align="left"><%=evt.getEventDescription()%></td>
              </tr>
              <tr>
                <td bgcolor="#ffffff" align="left">Start Date:</td>
                <td bgcolor="#ffffff" align="left"><%=evt.getEventDate()%></td>
              </tr>
              <% if(evt.getEventEndDate() != null) { %>
              <tr>
                <td bgcolor="#ffffff" align="left">End Date:</td>
                <td bgcolor="#ffffff" align="left"><%=evt.getEventEndDate()%></td>
              </tr>
              <% } %>
              <tr>
                <td bgcolor="#ffffff" align="left">Event Location:</td>
                <td bgcolor="#ffffff" align="left"><%=evt.getEventLocation()%></td>
              </tr>
              <% if(!evt.getEventStartTime().equals("UNKNOWN")) { %>
              <tr>
                <td width="100" valign="top" bgcolor="#FFFFFF">
                  Start Time:
                </td>
                <td width="300" valign="top" bgcolor="#FFFFFF">
                  <%=evt.getEventStartTime()%>
                </td>
              </tr>
              <tr>
                <td width="100" valign="top" bgcolor="#FFFFFF">
                  Finish Time:
                </td>
                <td width="300" valign="top" bgcolor="#FFFFFF">
                  <%=evt.getEventFinishTime()%>
                </td>
              </tr>
              <% } %>
              <% if(evt.isCloseOutDaySession() || evt.isPDOpportunity()) { %>
              <tr>
                <td width="100" valign="top" bgcolor="#FFFFFF">
                  Max Participants:
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
                <td width="100" valign="top" bgcolor="#FFFFFF">
                  # Registered:
                </td>
                <td width="300" valign="top" bgcolor="#FFFFFF">
                  <%=evt.getRegistrationCount()%>
                </td>
              </tr>
              <% } %>
              <tr>
                <% if(Utils.compareCurrentDate(evt.getEventDate()) > 0) { %>
                <td bgcolor="#F4F4F4" align="right" colspan="2">
                  <table>
                    <tr>
                      <% if (evt.getSchedulerID() != id) { %>
                        <% if(evt.isPDOpportunity()) { %>
                          <td>
                            <% if(revts.get(new Integer(evt.getEventID())) != null){ %>
                             <span align="left"><font color="#FF0000"><b>You are already registered for this event.</b></font></span>
                            <% } else if(evt.isFull()) { %>
                              <span align="left"><font color="#FF0000"><b>Registration has ended. Event is full.</b></font></span>
                            <% } else { %>                       
                              <img src="images/register_01.jpg" 
                               onmouseover="src='images/register_02.jpg';"
                               onmouseout="src='images/register_01.jpg';"
                               onmousedown="src='images/register_03.jpg';"
                               onmouseup="src='images/register_02.jpg';"
                               onclick="openWindow('registration', 'registerEvent.html?id=<%=evt.getEventID()%>', 400, 300, 0);">
                           <% } %> 
                          </td>
                        <% } else if(evt.isCloseOutDaySession()) { %>
                            <td>
                              <img src="images/sessions_01.jpg" 
                               onmouseover="src='images/sessions_02.jpg';"
                               onmouseout="src='images/sessions_01.jpg';"
                               onmousedown="src='images/sessions_03.jpg';"
                               onmouseup="src='images/sessions_02.jpg';"
                               onclick="openWindow('closeout', 'districtCloseout.html?id=<%=EventDB.getCloseOutEvent(evt).getEventID()%>', 450, 465, 1);">                            
                               
                            </td>
                        <% } else if(evt.isDistrictCalendarCloseOutEntry()) { %>
                            <td>
                              <img src="images/sessions_01.jpg" 
                               onmouseover="src='images/sessions_02.jpg';"
                               onmouseout="src='images/sessions_01.jpg';"
                               onmousedown="src='images/sessions_03.jpg';"
                               onmouseup="src='images/sessions_02.jpg';"
                               onclick="openWindow('closeout', 'districtCloseout.html?id=<%=evt.getEventID()%>', 450, 465, 1);">                            
                            </td>
                        <% } %>
                        <% if((evt.isPDOpportunity()||evt.isCloseOutDaySession()) && permissions.containsKey("CALENDAR-VIEW-PARTICIPANTS")) { %>
                            <td>
                              <img src="images/view_participants_01.jpg" 
                                onmouseover="src='images/view_participants_02.jpg';"
                                onmouseout="src='images/view_participants_01.jpg';"
                                onmousedown="src='images/view_participants_03.jpg';"
                                onmouseup="src='images/view_participants_02.jpg';"
                                onclick="openWindow('Participants', 'eventparticipants_frame.jsp?id=<%=evt.getEventID()%>', 450, 480, 1);">
                            </td>
                        <% } %>
                        <% if(permissions.containsKey("CALENDAR-DELETE-ALL")) { %>
                          <td>
                            <img src="images/delete_01.jpg" 
                              onmouseover="src='images/delete_02.jpg';"
                              onmouseout="src='images/delete_01.jpg';"
                              onmousedown="src='images/delete_03.jpg';"
                              onmouseup="src='images/delete_02.jpg';"
                              onclick="openWindow('deleteevent', 'deleteEvent.html?id=<%=evt.getEventID()%>', 400, 275, 0);">
                          </td>
                        <% } %>
                      <% } else { %>
                        <td>
                          <img src="images/modify_01.jpg" 
                             onmouseover="src='images/modify_02.jpg';"
                             onmouseout="src='images/modify_01.jpg';"
                             onmousedown="src='images/modify_03.jpg';"
                             onmouseup="src='images/modify_02.jpg';"
                             onclick="openWindow('modifyevent', 'modifyEvent.html?id=<%=evt.getEventID()%>', 400, 465, 0);">                           
                        </td>
                        <td>
                          <img src="images/delete_01.jpg" 
                             onmouseover="src='images/delete_02.jpg';"
                             onmouseout="src='images/delete_01.jpg';"
                             onmousedown="src='images/delete_03.jpg';"
                             onmouseup="src='images/delete_02.jpg';"
                             onclick="openWindow('deleteevent', 'deleteEvent.html?id=<%=evt.getEventID()%>', 400, 275, 0);">
                        </td>
                        <% if(evt.isCloseOutDaySession() || evt.isPDOpportunity()) { %>
                        <td>
                          <img src="images/view_participants_01.jpg" 
                              onmouseover="src='images/view_participants_02.jpg';"
                              onmouseout="src='images/view_participants_01.jpg';"
                              onmousedown="src='images/view_participants_03.jpg';"
                              onmouseup="src='images/view_participants_02.jpg';"
                              onclick="openWindow('Participants', 'eventparticipants_frame.jsp?id=<%=evt.getEventID()%>', 450, 480, 1);">
                        </td>
                        <% } else if(evt.isDistrictCalendarCloseOutEntry()) { %>
                            <td>
                              <img src="images/sessions_01.jpg" 
                               onmouseover="src='images/sessions_02.jpg';"
                               onmouseout="src='images/sessions_01.jpg';"
                               onmousedown="src='images/sessions_03.jpg';"
                               onmouseup="src='images/sessions_02.jpg';"
                               onclick="openWindow('closeout', 'districtCloseout.html?id=<%=evt.getEventID()%>', 450, 465, 1);">                            
                            </td>
                        <% } %>
                      <% } %>
                    </tr>
                  </table>
                </td>
                <%} else { %>
                  <td bgcolor="#F4F4F4"  colspan="2" valign="middle">
                    <table width="100%">
                      <tr>
                        <td width="50%" ALIGN="left">
                          <% if((Utils.compareCurrentDate(evt.getEventDate())==0)||((evt.getEventEndDate()!= null)&&(Utils.compareCurrentDate(evt.getEventDate())==-1)&&(Utils.compareCurrentDate(evt.getEventEndDate())>=0))){%>
                              <span><font color="#FF0000"><b>Active Event</b></font></span>
                          <%}else if(Utils.compareCurrentDate(evt.getEventDate())==-1){%>
                              <span><font color="#FF0000"><b>Past Event</b></font></span>
                          <% } %>
                        </td>
                        <% if((evt.isCloseOutDaySession() || evt.isPDOpportunity())&&((permissions.containsKey("CALENDAR-VIEW-PARTICIPANTS"))||(id ==evt.getSchedulerID()))) { %>
                        <td width="50%" ALIGN="RIGHT">
                          <img src="images/view_participants_01.jpg" 
                            onmouseover="src='images/view_participants_02.jpg';"
                            onmouseout="src='images/view_participants_01.jpg';"
                            onmousedown="src='images/view_participants_03.jpg';"
                            onmouseup="src='images/view_participants_02.jpg';"
                            onclick="openWindow('Participants', 'eventparticipants_frame.jsp?id=<%=evt.getEventID()%>', 450, 480, 1);">
                        </td>
                        <% } else if(evt.isDistrictCalendarCloseOutEntry()) { %>
                            <td>
                              <img src="images/sessions_01.jpg" 
                               onmouseover="src='images/sessions_02.jpg';"
                               onmouseout="src='images/sessions_01.jpg';"
                               onmousedown="src='images/sessions_03.jpg';"
                               onmouseup="src='images/sessions_02.jpg';"
                               onclick="openWindow('closeout', 'districtCloseout.html?id=<%=evt.getEventID()%>', 450, 465, 1);">                            
                            </td>
                        <% } %>
                      </tr>
                    </table>                  
                  </td>
                <%}%>
              </tr>
<%          }
            response.flushBuffer();
          }
        }
%>    <tr>
        <td colspan="2" align="center">
          <img src="images/closeblue_01.jpg" 
               onmouseover="src='images/closeblue_02.jpg';" 
               onmouseout="src='images/closeblue_01.jpg';"
               onmousedown="src='images/closeblue_03.jpg';"
               onmouseup="src='images/closeblue_02.jpg';"
               onclick="window.close();">
        </td>
      </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>