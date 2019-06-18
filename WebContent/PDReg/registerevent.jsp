<%@ page language="java"
          session = "true"
          import="com.awsd.pdreg.*,com.awsd.security.*, 
                  java.text.*, 
                  java.util.*,
                  java.io.*,
                  com.awsd.common.Utils,
                  org.apache.commons.lang.*" 
          errorPage="error.jsp" 
          isThreadSafe="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>          
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
 
<esd:SecurityCheck permissions="CALENDAR-VIEW" />
 
<%
  Event evt = null;
  
  Date now = null;
  int status;
  String img;

  int width;

  User usr = null;
  UserPermissions permissions = null;
  RegisteredEvents revts = null;
  boolean registered = false;
  File f_agenda = null;

  usr = (User) session.getAttribute("usr");
  
  permissions = usr.getUserPermissions();
  
  revts = new RegisteredEvents(usr.getPersonnel());
  
  evt = (Event) request.getAttribute("evt");
  
  if(request.getAttribute("details")!= null)
  {
    if(Utils.compareCurrentDate(evt.getEventDate()) < 0)
    {
      status = 2;
      img = "images/pastevent_pt1.gif";
    }
    else
    {
      status = 4;
      img = "images/eventdetails_pt1.gif";
    }
  }
  else
  {   
    if((Utils.compareCurrentDate(evt.getEventDate())==0)||
      (((evt.getEventEndDate()!= null)&&(Utils.compareCurrentDate(evt.getEventDate())==-1)&&(Utils.compareCurrentDate(evt.getEventEndDate())>=0)) ))
    {
      status = 1;
      img = "images/activeevent_pt1.gif";
    }
    else if(Utils.compareCurrentDate(evt.getEventDate()) < 0)
    {
      status = 2;
      img = "images/pastevent_pt1.gif";
    }
    else if((usr.getPersonnel().getPersonnelID()==evt.getSchedulerID())
    	||(usr.isAdmin())
      ||(evt.isDistrictCalendarCloseOutEntry() || evt.isDistrictCalendarEntry()
      || evt.isHolidayCalendarEntry() || evt.isReminderCalendarEntry()
      || evt.isSchoolPDEntry()))
    {
      status = 4;
      img = "images/eventdetails_pt1.gif";
    }
    else
    {
      status = 3;
      img = "images/register_pt1.gif";
      if (revts.get(new Integer(evt.getEventID())) != null)
      {
        status = 33;
      }
      else if(evt.isFull())
      {
        status = 333;
      }
    }
  }

  if(request.getAttribute("msg") != null)
  {
    if((status != 3) && (status != 33))
    {
      width = 185;
    }
    else
    {
      width = 287;
    }
  }
  else if((status == 4) && (evt.isDistrictCalendarCloseOutEntry() || evt.isDistrictCalendarEntry()))
  {
    width=287;
  }
  else
  {
    width = 185;
  } 
  
  f_agenda = (File) request.getAttribute("AGENDA_FILE");
  
  Calendar reg_start = Calendar.getInstance();
  Date dt_reg_start = null;
  Calendar reg_end = Calendar.getInstance();
  Date dt_reg_end = null;
  Calendar noww = Calendar.getInstance();
  Date dt_now = noww.getTime();
  
  reg_start.clear();
  reg_start.setTime(evt.getEventDate());
  reg_start.add(Calendar.WEEK_OF_YEAR, -3);
  dt_reg_start = reg_start.getTime();
  
  reg_end.clear();
  reg_end.setTime(reg_start.getTime());
  reg_end.add(Calendar.WEEK_OF_YEAR, 2);
  reg_end.add(Calendar.DATE, 1);
  dt_reg_end = reg_end.getTime();
%>
    
<html>
  <head>
  	<meta content="text/html;charset=utf-8" http-equiv="Content-Type">
		<meta content="utf-8" http-equiv="encoding">
    <title>Newfoundland &amp; Labrador English School District - Events Calendar: Register for an Event</title>

    <style>
      td {font-family: Arial, Helvetica, sans-serif; font-size: 11px; line-height: 16px; color: #000000; font-weight:bold;}
    </style>

    <script type="text/javascript" src="../js/common.js"></script>
    <script type="text/javascript">
      clicked = false;
    </script>
  </head>

  <body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
    <script language="JavaScript">clicked = false;</script>
   <form action="registerEvent.html" name="register" method="post">
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
          <%=evt.getEventDescription().replaceAll(Character.toString((char)191), "&#39;")%>
        </td>
      </tr>
      <tr>
        <td width="100" valign="top" bgcolor="#f4f4f4">
          &nbsp;&nbsp;Location:
        </td>
        <td width="300" valign="top" bgcolor="#FFFFFF">
          <%=evt.getEventLocation() + ((evt.getEventSchoolZone() != null) ? " - " + StringUtils.capitalize(evt.getEventSchoolZone().getZoneName()) + " Region" : "") %>
        </td>
      </tr>
      <% if(evt.isCloseOutDaySession() || evt.isPDOpportunity()) { %>
      <tr>
        <td width="100" valign="top" bgcolor="#f4f4f4">
          &nbsp;&nbsp;Host:
        </td>
        <td width="300" valign="top" bgcolor="#FFFFFF">
          <%=evt.getScheduler().getFullName()%>
        </td>
      </tr>
      <% } %>
      <tr>
        <td width="100" valign="top" bgcolor="#f4f4f4">
          &nbsp;&nbsp;Start Date:
        </td>
        <td width="300" valign="top" bgcolor="#FFFFFF">
          <%=(new SimpleDateFormat("MM/dd/yyyy")).format(evt.getEventDate())%>
        </td>
      </tr>
      <% if(evt.getEventEndDate() != null) { %>
      <tr>
        <td width="100" valign="top" bgcolor="#f4f4f4">
          &nbsp;&nbsp;End Date:
        </td>
        <td width="300" valign="top" bgcolor="#FFFFFF">
          <%=(new SimpleDateFormat("MM/dd/yyyy")).format(evt.getEventEndDate())%>
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
      
      <% if(evt.hasEventCategories()) { %>
      <tr>
        <td width="100" valign="top" bgcolor="#f4f4f4">
          &nbsp;&nbsp;Categories:
        </td>
        <td width="300" valign="top" bgcolor="#FFFFFF">
        	<c:forEach items="${evt.eventCategories}" var="cat" varStatus="status">
          		<i>${cat.categoryName}</i><c:if test="${status.last eq false}"><br /></c:if>
          </c:forEach>
        </td>
      </tr>
      <% } %>
      
      <% if((evt.isScheduler(usr) || usr.isAdmin()) && evt.hasEventRequirements()) { %>
      <tr>
        <td width="100" valign="top" bgcolor="#f4f4f4">
          &nbsp;&nbsp;Requirements:
        </td>
        <td width="300" valign="top" bgcolor="#FFFFFF">
        	<c:forEach items="${evt.eventRequirements}" var="req" varStatus="status">
          		<i>${req.requirement}</i><c:if test="${not empty req.extrainfo }">&nbsp;[${ req.extrainfo }]</c:if><c:if test="${status.last eq false}"><br /></c:if>
          </c:forEach>
        </td>
      </tr>
      <% } %>
      
      <% if(evt.isSchoolPDEntry() || f_agenda != null) { %>
      <tr>
        <td width="100" valign="top" bgcolor="#f4f4f4">
          &nbsp;&nbsp;Agenda:
        </td>
        <td width="300" valign="top" bgcolor="#FFFFFF">
          <%if(f_agenda != null){%>
            <a href="http://www.nlesd.ca/MemberServices/PDReg/agendas/<%=f_agenda.getName()%>" target="_blank" style="color:#FF0000;font-weight:bold;text-decoration:none;">DOWNLOAD</a>
          <%}else{%>
            <span style="color:#FF0000;font-weight:bold;">Agenda not available.</span>
          <%}%>
        </td>
      </tr>
      <% } %>
      
      <% if((evt.isScheduler(usr) || usr.isAdmin()) && (evt.getEventSchoolZone().getZoneId() == 4)) { %>
      	<tr>
	        <td width="100" valign="top" bgcolor="#f4f4f4">
	          &nbsp;&nbsp;Nunatsiavut Government Funded:
	        </td>
	        <td width="300" valign="top" bgcolor="#FFFFFF">
	          <%= (evt.isGovernmentFunded() ? "YES" : "NO") %>
	        </td>
	      </tr>
      <% } %>
      
      <% if(request.getAttribute("msg") != null) { %>
      <tr>
        <td align="center" valign="middle" colspan="2">
          <font color="#FF0000">
            <%= request.getAttribute("msg") %>
          </font>
        </td>
      </tr>          
      <% } else if(status == 33) { %>
      <tr>
        <td align="center" valign="middle" colspan="2">
          <font color="#FF0000">
            You have already registered for this event.
          </font>
        </td>
      </tr>             
      <% } else if(status == 333) { %>
      <tr>
        <td align="center" valign="middle" colspan="2">
          <font color="#FF0000">
            Registration has ended. Event is full.
          </font>
        </td>
      </tr>          
      <% } %>
    </table>
    
    <table width="400" cellpadding="1" cellspacing="5" border="0">
      <tr>
        <td width="400" valign="middle" bgcolor="#f4f4f4">
          <table width="100%" cellpadding="0" cellspacing="0" border="0">
            <tr>
              <td width="100%" align="right" valign="middle">
                  <% if((request.getAttribute("msg") == null) && (status == 3)) { %>
                    <input type="hidden" name="id" value="<%=evt.getEventID()%>">
                    <input type="hidden" name="confirmed" value="true">
                    <img name="processing" src='images/spacer.gif'>
                    <%if((dt_reg_start.compareTo(dt_now) <= 0) || (evt.getEventType().getEventTypeID() == 4)){ %>
	                    <img src="images/confirm_01.jpg" name="confirm"
	                        onmouseover="src='images/confirm_02.jpg';" 
	                        onmouseout="src='images/confirm_01.jpg';"
	                        onmousedown="src='images/confirm_03.jpg';"
	                        onmouseup="src='images/confirm_02.jpg';"
	                        onclick="onClick(document.register);">
	                    <img name="cancel" src="images/cancel_01.jpg" 
	                        onmouseover="src='images/cancel_02.jpg';" 
	                        onmouseout="src='images/cancel_01.jpg';"
	                        onmousedown="src='images/cancel_03.jpg';"
	                        onmouseup="src='images/cancel_02.jpg';"
	                        onclick="self.close();">
                    <%}else{%>
                    	<font style='font-weight:bold;'>Registration not currently available.</font>
                    <%}%>
                    <% if((permissions.containsKey("CALENDAR-VIEW-PARTICIPANTS"))) { %>
                      <img src="images/view_participants_01.jpg" 
                        onmouseover="src='images/view_participants_02.jpg';" 
                        onmouseout="src='images/view_participants_01.jpg';" 
                        onmousedown="src='images/view_participants_03.jpg';"
                        onmouseup="src='images/view_participants_02.jpg';"
                        onclick="openWindow('Participants', 'eventparticipants_frame.jsp?id=<%=evt.getEventID()%>', 450, 480, 1);">
                    <% } %>
                  <% } else { %>
                    <% if(status != 3) { %>
                    	<% if((evt.isScheduler(usr) || usr.isAdmin()) && evt.hasParticipants() && (evt.isActive() || evt.isPast())) { %>
                         <img src="images/attendance_01.jpg" 
                           onmouseover="src='images/attendance_02.jpg';"
                           onmouseout="src='images/attendance_01.jpg';"
                           onmousedown="src='images/attendance_03.jpg';"
                           onmouseup="src='images/attendance_02.jpg';"
                           onclick="openWindow('Attendance', 'viewEventAttendance.html?id=<%=evt.getEventID()%>', 450, 480, 1);">
                      <%} else if((permissions.containsKey("CALENDAR-VIEW-PARTICIPANTS") || evt.isScheduler(usr)) && evt.hasParticipants()) { %>
                        <img src="images/view_participants_01.jpg" 
                          onmouseover="src='images/view_participants_02.jpg';" 
                          onmouseout="src='images/view_participants_01.jpg';" 
                          onmousedown="src='images/view_participants_03.jpg';"
                          onmouseup="src='images/view_participants_02.jpg';"
                          onclick="openWindow('Participants', 'eventparticipants_frame.jsp?id=<%=evt.getEventID()%>', 450, 480, 1);">
                      <% } %>
                      <% if((evt.isScheduler(usr) && evt.hasParticipants()) || usr.isAdmin()) { %>
                        <img src="images/modify_01.jpg" 
                          onmouseover="src='images/modify_02.jpg';"
                          onmouseout="src='images/modify_01.jpg';"
                          onmousedown="src='images/modify_03.jpg';"
                          onmouseup="src='images/modify_02.jpg';"
                          onclick="openWindow('modifyevent', 'modifyEvent.html?id=<%=evt.getEventID()%>', 425, 625, 1);">
                      <% } %>
                      <% if(permissions.containsKey("CALENDAR-DELETE-ALL") || evt.isScheduler(usr)) { %>
                          <img src="images/delete_01.jpg" 
                              onmouseover="src='images/delete_02.jpg';"
                              onmouseout="src='images/delete_01.jpg';"
                              onmousedown="src='images/delete_03.jpg';"
                              onmouseup="src='images/delete_02.jpg';"
                              onclick="openWindow('deleteevent', 'deleteEvent.html?id=<%=evt.getEventID()%>', 400, 400, 0);">
                        <% } %>
                    <% } %>   
                    <img src="images/close_01.jpg" 
                        onmouseover="src='images/close_02.jpg';" 
                        onmouseout="src='images/close_01.jpg';" 
                        onmousedown="src='images/close_03.jpg';"
                        onmouseup="src='images/close_02.jpg';"
                        onclick="if(self.opener.name=='dailyCalendar')self.opener.location.reload();self.close();">
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