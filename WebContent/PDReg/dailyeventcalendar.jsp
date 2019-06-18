<%@ page  language="java" 
          session="true" 
          import="java.util.*, 
                  java.text.*,
                  java.io.*,
                  com.awsd.pdreg.*,
                  com.awsd.security.*,
                  com.awsd.common.Utils,
                  org.apache.commons.lang.*" 
          isThreadSafe="false"%>
 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
 
<esd:SecurityCheck permissions="CALENDAR-VIEW" />
 
<%!
	private class AgendaFilenameFilter implements FilenameFilter {
	
		private Event evt;
	
		public AgendaFilenameFilter(Event evt) {
	
			this.evt = evt;
		}
	
		public boolean accept(File dir, String name) {
	
			boolean check = false;
	
			if (name.indexOf(evt.getEventID() + ".") >= 0)
				check = true;
	
			return check;
		}
	}
%>
<%
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

  File agenda_dir = new File(session.getServletContext().getRealPath("/") + "/PDReg/agendas/");
  
  String prevM;
  String nextM;
  String prevD;
  String nextD;
  int curM;
  int curY;
  int curD;
  int dayofweek;

  Calendar cur = null;
  SimpleDateFormat sdf = null;

  DailyCalendar daily = null;
  Event evt = null;
  Iterator iter = null;
  //Date now = null;
  User usr = (User) session.getAttribute("usr");
  UserPermissions permissions = null;
  int id;
  RegisteredEvents revts = null;
  int rowcnt;
  CalendarLegend legend = null;
  
  File f_agenda = null;

  String color;
  
  revts = new RegisteredEvents(usr.getPersonnel());
  id = usr.getPersonnel().getPersonnelID();
  permissions = usr.getUserPermissions();
  
  
  daily = (DailyCalendar) request.getAttribute("DailyEvents");
  iter = daily.iterator();
  
  prevM = (String) request.getAttribute("PrevMonth");
  prevD = (String) request.getAttribute("PrevDay");
  nextM = (String) request.getAttribute("NextMonth");
  nextD = (String) request.getAttribute("NextDay");
  
  curD = ((Integer) request.getAttribute("CurrentDay")).intValue();
  curM = ((Integer) request.getAttribute("CurrentMonth")).intValue();
  curY = ((Integer) request.getAttribute("CurrentYear")).intValue();

  cur =  Calendar.getInstance();
  cur.set(curY, curM, curD);
  dayofweek = cur.get(Calendar.DAY_OF_WEEK);

  sdf = new SimpleDateFormat("yyyyMMdd");

  //now = (Calendar.getInstance()).getTime();
  
  rowcnt = 0;

  legend = new CalendarLegend();
  
  Calendar reg_start = Calendar.getInstance();
  Date dt_reg_start = null;
  Calendar reg_end = Calendar.getInstance();
  Date dt_reg_end = null;
  Calendar now = Calendar.getInstance();
  Date dt_now = now.getTime();
  
  
%>

<html>

<head>
<meta content="text/html;charset=utf-8" http-equiv="Content-Type">
<meta content="utf-8" http-equiv="encoding">
<title>Newfoundland &amp; Labrador English School District - Daily Calendar</title>
<style>
	td {font-family: Tahoma, Verdana, sans-serif; font-size: 12px;}
</style>

<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript">
  function toggle(target, sw)
  {
    obj=(document.all) ? document.all[target] : document.getElementById(target);
    obj.style.display=sw;
  }
  
  function setMessage(rid, cid, msg)
  {
    var cell  = document.getElementById(cid);

    cell.innerHTML = "<FONT style='font-weight:bold' COLOR='FF0000'>"+ msg +"</FONT>";

    toggle(rid, 'inline');
  }
</script>
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
                  <a href="viewDailyCalendar.html?dt=<%=prevM%><%= daily.getZone() != null ? "&region-id=" + daily.getZone().getZoneId() : "" %>"><img src="<%=STR_ICONPATH%>prev_year.gif" width="16" height="16" border="0" alt="previous month"></a>&nbsp;<a href="viewDailyCalendar.html?dt=<%=prevD%><%= daily.getZone() != null ? "&region-id=" + daily.getZone().getZoneId() : "" %>"><img src="<%=STR_ICONPATH%>prev.gif" width="16" height="16" border="0" alt="previous day"></a>
                </td>
                <td align="center" width="100%">
                  <font size="3" color="#ffffff"><b><%=ARR_WEEKDAYS[dayofweek-1]%>,&nbsp;<%=ARR_MONTHS[curM]%>&nbsp;<%=curD%>&nbsp;<%=curY%></b></font>
                </td>
                <td>
                  <a href="viewDailyCalendar.html?dt=<%=nextD%><%= daily.getZone() != null ? "&region-id=" + daily.getZone().getZoneId() : "" %>"><img src="<%=STR_ICONPATH%>next.gif" width="16" height="16" border="0" alt="next day"></a>&nbsp;<a href="viewDailyCalendar.html?dt=<%=nextM%><%= daily.getZone() != null ? "&region-id=" + daily.getZone().getZoneId() : "" %>"><img src="<%=STR_ICONPATH%>next_year.gif" width="16" height="16" border="0" alt="next month"></a>
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
            
            if(evt.isCloseOutDaySession() || evt.isSchoolPDRequest())
            {
              continue;
            }
            
      			File[] agendas = agenda_dir.listFiles(new AgendaFilenameFilter(evt));

      			if (agendas.length > 0) {
            	f_agenda = agendas[0];
      			}
      			else {
      				f_agenda = null;
      			}
            
            if(evt.getEventDate() != null)
            {
	            reg_start.clear();
	            reg_start.setTime(evt.getEventDate());
	            reg_start.add(Calendar.WEEK_OF_YEAR, -3);
	            dt_reg_start = reg_start.getTime();
	            
	            reg_end.clear();
	            reg_end.setTime(reg_start.getTime());
	            reg_end.add(Calendar.WEEK_OF_YEAR, 2);
	            dt_reg_end = reg_end.getTime();
            }
            rowcnt++;
            
            if(!evt.isPrivateCalendarEntry() 
              || (evt.isPrivateCalendarEntry() && (evt.getSchedulerID() == id))) { %>
              <tr>
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
                <td bgcolor="#ffffff" align="left">Location</td>
                <td bgcolor="#ffffff" align="left"><%=evt.getEventLocation() + ((evt.getEventSchoolZone() != null) ? " - " + StringUtils.capitalize(evt.getEventSchoolZone().getZoneName()) + " Region" : "") %></td>
              </tr>
              <% if(evt.isCloseOutDaySession() || evt.isPDOpportunity()) { %>
              	<tr>
                  <td bgcolor="<%=(legend.containsKey(new Integer(evt.getSchedulerID()))?"#" + ((String)legend.get(new Integer(evt.getSchedulerID()))):"#FFFFFF")%>" align="left">Event Host</td>
                  <td bgcolor="#FFFFFF" align="left"><font style="font-weight:bold" color="<%=legend.containsKey(new Integer(evt.getSchedulerID()))?"#" + (String)legend.get(new Integer(evt.getSchedulerID())):"#000000"%>"><%=evt.getScheduler().getFullName()%></font></td>  
              	</tr>
              <% } %>
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
              
              <% if(evt.hasEventCategories()) { %>
					      <tr>
					        <td width="100" valign="top" bgcolor="#FFFFFF">
					          Categories:
					        </td>
					        <td width="300" valign="top" bgcolor="#FFFFFF">
					          <c:forEach items="${evt.eventCategories}" var="cat" varStatus="status">
					          	${cat.name}<c:if test="${status.last eq false}"><br /></c:if>
					          </c:forEach>
					        </td>
					      </tr>
				      <% } %>
              
              <% if(evt.isSchoolPDEntry() || f_agenda != null) { %>
				      <tr>
				        <td width="100" valign="top" bgcolor="#FFFFFF">
				          Agenda:
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
	              <% if(!usr.checkRole("TEACHER")) { %>
		              <tr>
		                <td width="100" valign="top" bgcolor="#FFFFFF">
		                  # Registered:
		                </td>
		                <td width="300" valign="top" bgcolor="#FFFFFF">
		                  <%=evt.getRegistrationCount()%>
		                </td>
		              </tr>
	              <% } %>
              <% } %>
              <tr id="msgrow_<%=rowcnt%>" style="display:none">
                  <td id="msg_<%=rowcnt%>"  colspan="2" bgcolor="#ffffff" align="center"></td>
              </tr>
              <tr>
                <% if(Utils.compareCurrentDate(evt.getEventDate())>0) { %>
                <td bgcolor="#F4F4F4" align="right" colspan="2">
                  <table>
                    <tr>
                      <% if(!evt.isScheduler(usr)) { %>
                        <% if(evt.isPDOpportunity()) { %>
                          <td>
                            <% if(revts.get(new Integer(evt.getEventID())) != null){ %>
                              <script>setMessage('msgrow_<%=rowcnt%>', 'msg_<%=rowcnt%>', 'You are already registered for this event.');</script>
                            <% } else if(evt.isFull()) { %>
                              <script>setMessage('msgrow_<%=rowcnt%>', 'msg_<%=rowcnt%>', 'Registration has ended. Event is full.');</script>
                            <% } else { %>
                            

                            	<%if((dt_reg_start.compareTo(dt_now) <= 0) || (evt.getEventType().getEventTypeID() == 4) //PD SESSION
                            			){ %>
	                              <img src="images/register_01.jpg" 
	                               onmouseover="src='images/register_02.jpg';"
	                               onmouseout="src='images/register_01.jpg';"
	                               onmousedown="src='images/register_03.jpg';"
	                               onmouseup="src='images/register_02.jpg';"
	                               onclick="openWindow('registration', 'registerEvent.html?id=<%=evt.getEventID()%>', 420, 465, 1);">
                               <%}else{%>
                               		&nbsp;
                               		<script>setMessage('msgrow_<%=rowcnt%>', 'msg_<%=rowcnt%>', 'Registration not currently available.');</script>
                               <%}%>
                            <% } %>
                          </td>
                          <% if(permissions.containsKey("CALENDAR-VIEW-PARTICIPANTS") && evt.isPDOpportunity()) { %>
                            <td>
                              <img src="images/view_participants_01.jpg" 
                                onmouseover="src='images/view_participants_02.jpg';"
                                onmouseout="src='images/view_participants_01.jpg';"
                                onmousedown="src='images/view_participants_03.jpg';"
                                onmouseup="src='images/view_participants_02.jpg';"
                                onclick="openWindow('Participants', 'eventparticipants_frame.jsp?id=<%=evt.getEventID()%>', 450, 480, 1);">
                            </td>
                          <% } %>
                        <% } else if(evt.isDistrictCalendarCloseOutEntry()){%>
                              <td>
                                <img src="images/sessions_01.jpg" 
                                  onmouseover="src='images/sessions_02.jpg';"
                                  onmouseout="src='images/sessions_01.jpg';"
                                  onmousedown="src='images/sessions_03.jpg';"
                                  onmouseup="src='images/sessions_02.jpg';"
                                  onclick="openWindow('closeout', 'districtCloseout.html?id=<%=evt.getEventID()%>', 450, 465, 1);">                            
                              </td>
                        <%}%>
                        <% if(permissions.containsKey("CALENDAR-DELETE-ALL")) { %>
                          <td>
                            <img src="images/delete_01.jpg" 
                              onmouseover="src='images/delete_02.jpg';"
                              onmouseout="src='images/delete_01.jpg';"
                              onmousedown="src='images/delete_03.jpg';"
                              onmouseup="src='images/delete_02.jpg';"
                              onclick="openWindow('deleteevent', 'deleteEvent.html?id=<%=evt.getEventID()%>', 400, 400, 0);">
                          </td>
                        <% } %>
                      <% } else { %>
                        <% if(evt.hasParticipants()) { %>
                          <td>
                            <img src="images/view_participants_01.jpg" 
                                onmouseover="src='images/view_participants_02.jpg';"
                                onmouseout="src='images/view_participants_01.jpg';"
                                onmousedown="src='images/view_participants_03.jpg';"
                                onmouseup="src='images/view_participants_02.jpg';"
                                onclick="openWindow('Participants', 'eventparticipants_frame.jsp?id=<%=evt.getEventID()%>', 450, 480, 1);">
                          </td>
                        <% } else if(evt.isDistrictCalendarCloseOutEntry()){%>
                              <td>
                                <img src="images/sessions_01.jpg" 
                                 onmouseover="src='images/sessions_02.jpg';"
                                 onmouseout="src='images/sessions_01.jpg';"
                                 onmousedown="src='images/sessions_03.jpg';"
                                 onmouseup="src='images/sessions_02.jpg';"
                                 onclick="openWindow('closeout', 'districtCloseout.html?id=<%=evt.getEventID()%>', 450, 465, 1);">                            
                              </td>
                        <%}%>
                        <% if((evt.isCloseOutDaySession() || evt.isPDOpportunity()) || usr.isAdmin()){ %>
                        <td>
                          <img src="images/modify_01.jpg" 
                             onmouseover="src='images/modify_02.jpg';"
                             onmouseout="src='images/modify_01.jpg';"
                             onmousedown="src='images/modify_03.jpg';"
                             onmouseup="src='images/modify_02.jpg';"
                             onclick="openWindow('modifyevent', 'modifyEvent.html?id=<%=evt.getEventID()%>', 400, 465, 1);">                           
                        </td>
                        <% } %>
                        <td>
                          <img src="images/delete_01.jpg" 
                             onmouseover="src='images/delete_02.jpg';"
                             onmouseout="src='images/delete_01.jpg';"
                             onmousedown="src='images/delete_03.jpg';"
                             onmouseup="src='images/delete_02.jpg';"
                             onclick="openWindow('deleteevent', 'deleteEvent.html?id=<%=evt.getEventID()%>', 400, 400, 0);">
                        </td>
                      <% } %>
                    </tr>
                  </table>
                </td>
                <%} else { %>
                  <td bgcolor="#F4F4F4" align="left" width="100%" colspan="2" valign="middle">
                    <table width="100%">
                      <tr>
                        <td bgcolor="#F4F4F4" width="50%" align="left">
                          <% if((Utils.compareCurrentDate(evt.getEventDate())==0)||((evt.getEventEndDate()!= null)&&(Utils.compareCurrentDate(evt.getEventDate())==-1)&&(Utils.compareCurrentDate(evt.getEventEndDate())>=0))){%>
                              <span><font color="#FF0000"><b>Active Event</b></font></span>
                          <%}else if(Utils.compareCurrentDate(evt.getEventDate())==-1){%>
                              <span><font color="#FF0000"><b>Past Event</b></font></span>
                          <% } %>
                        </td>
                        <% if((evt.isScheduler(usr)|| usr.isAdmin()) && evt.hasParticipants() && (evt.isActive() || evt.isPast())) { %>
                          <td bgcolor="#F4F4F4" align="right" valign="middle">
                            <img src="images/attendance_01.jpg" 
                              onmouseover="src='images/attendance_02.jpg';"
                              onmouseout="src='images/attendance_01.jpg';"
                              onmousedown="src='images/attendance_03.jpg';"
                              onmouseup="src='images/attendance_02.jpg';"
                              onclick="openWindow('Attendance', 'viewEventAttendance.html?id=<%=evt.getEventID()%>', 450, 480, 1);">
                          </td>
                        <% } else if(evt.hasParticipants() && permissions.containsKey("CALENDAR-VIEW-PARTICIPANTS")) { %>
                          <td bgcolor="#F4F4F4" align="right" valign="middle">
                            <img src="images/view_participants_01.jpg" 
                              onmouseover="src='images/view_participants_02.jpg';"
                              onmouseout="src='images/view_participants_01.jpg';"
                              onmousedown="src='images/view_participants_03.jpg';"
                              onmouseup="src='images/view_participants_02.jpg';"
                              onclick="openWindow('Participants', 'eventparticipants_frame.jsp?id=<%=evt.getEventID()%>', 450, 480, 1);">
                          </td>
                        <% } else if(evt.isDistrictCalendarCloseOutEntry()){%>
                            <td bgcolor="#F4F4F4" align="right" valign="middle">
                              <img src="images/sessions_01.jpg" 
                               onmouseover="src='images/sessions_02.jpg';"
                               onmouseout="src='images/sessions_01.jpg';"
                               onmousedown="src='images/sessions_03.jpg';"
                               onmouseup="src='images/sessions_02.jpg';"
                               onclick="openWindow('closeout', 'districtCloseout.html?id=<%=evt.getEventID()%>', 450, 465, 1);">
                            </td>   
                        <%}%>
                      </tr>
                    </table>
                  </td>
                <%}%>
              </tr>
        <%  }
          }
        } %>
      <tr>
        <td colspan="2" align="center">
          <img src="images/closeblue_01.jpg" 
               onmouseover="src='images/closeblue_02.jpg';" 
               onmouseout="src='images/closeblue_01.jpg';"
               onmousedown="src='images/closeblue_03.jpg';"
               onmouseup="src='images/closeblue_02.jpg';"
               onclick="window.opener.location.reload(); self.close();">
        </td>
      </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>