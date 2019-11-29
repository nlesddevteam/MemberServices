<%@ page  language="java" 
          session="true" 
          import="java.util.*, 
                  java.text.*,
                  java.io.*,
                  com.awsd.pdreg.*,
                  com.awsd.security.*,
                  com.awsd.personnel.*,
            com.awsd.school.*,
            com.awsd.common.Utils,
            com.nlesd.school.bean.*,
            com.nlesd.school.service.*,
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
  final String STR_ICONPATH = "includes/img/";

  File agenda_dir = new File(session.getServletContext().getRealPath("/") + "/PDReg/agendas/");
  
  String prevM;
  String nextM;
  String prevD;
  String nextD;
  int curM;
  int curY;
  int curD;
  int dayofweek;
  
	String bgcolor = "";
	String txtcolor = "";
	String regionName = "";
	
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
  
  evt = (Event) request.getAttribute("evt");
  
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
  
  int eA = 0;
  int eC = 0;
  int eW = 0;
  int eL = 0;
  int eP = 0;
  
  
%>

<html>

<head>
<title>PD Calendar</title>


<script language="JavaScript">
  function toggle(target, sw)
  {
    obj=(document.all) ? document.all[target] : document.getElementById(target);
    obj.style.display=sw;
  }
  
 
$("#loadingSpinner").css("display","none");
</script>
<style>
.tableTitle {font-weight:bold;width:20%;color:White;text-transform:uppercase;}
.tableResult {font-weight:normal;width:80%;background-color:#ffffff;}
.tableTitleL {font-weight:bold;width:20%;background-color:#006400;color:White;text-transform:uppercase;}
.tableResultL {font-weight:normal;width:30%;background-color:#ffffff;}
.tableTitleR {font-weight:bold;width:20%;background-color:#006400;color:White;text-transform:uppercase;}
.tableResultR {font-weight:normal;width:30%;background-color:#ffffff;}
input {border:1px solid silver;}

.affix { top: 0px; width: 100%;z-index: 999 !important;}
.affix + .container-fluid { padding-top: 80px;}

@media only screen and (max-width: 850px) {		
		.affix + .container-fluid {padding-top: 70px; }
	
}

</style>
<script src="/MemberServices/PDReg/includes/js/Chart.min.js"></script>
</head>

<body>     <a name="top"></a>
<%if(ARR_MONTHS[curM] == "January" || ARR_MONTHS[curM] == "February") { %>
  		<c:set var="season" value="winter"/> 
  	    <c:set var="seasonIconLeft" value="winter.png"/>
   		<c:set var="seasonIconRight" value="winter.png"/>
   	<%} %>
    <%if(ARR_MONTHS[curM] == "March") { %>   
   		<c:set var="season" value="springGradient"/>
   		<c:set var="seasonIconLeft" value="winter.png"/>
   		<c:set var="seasonIconRight" value="spring.png"/>
   		  
    <%} %>    
    <%if(ARR_MONTHS[curM] == "April" || ARR_MONTHS[curM] == "May") { %>
  		<c:set var="season" value="spring"/> 
  		<c:set var="seasonIconLeft" value="spring.png"/>
   		<c:set var="seasonIconRight" value="spring.png"/>
   	<%} %>   
   <%if(ARR_MONTHS[curM] == "June") { %>   
   		<c:set var="season" value="summerGradient"/>
   		<c:set var="seasonIconLeft" value="spring.png"/>
   		<c:set var="seasonIconRight" value="summer.png"/>
   		 
    <%} %> 
    <%if(ARR_MONTHS[curM] == "July" || ARR_MONTHS[curM] == "August") { %>
  		<c:set var="season" value="summer"/>
  		<c:set var="seasonIconLeft" value="summer.png"/>
   		<c:set var="seasonIconRight" value="summer.png"/>  
  		
   	<%} %>
    <%if(ARR_MONTHS[curM] == "September") { %>   
   		<c:set var="season" value="fallGradient"/>
   		<c:set var="seasonIconLeft" value="summer.png"/>
   		<c:set var="seasonIconRight" value="fall.png"/>
   		  
    <%} %>
    <%if(ARR_MONTHS[curM] == "October" || ARR_MONTHS[curM] == "November") { %>
  		<c:set var="season" value="fall"/> 
  		<c:set var="seasonIconLeft" value="fall.png"/>
   		<c:set var="seasonIconRight" value="fall.png"/> 
  		 
   	<%} %>    
    <%if(ARR_MONTHS[curM] == "December") { %>   
   		<c:set var="season" value="winterGradient"/> 
   		<c:set var="seasonIconLeft" value="fall.png"/>
   		<c:set var="seasonIconRight" value="winter.png"/>
   		
    <%} %>
    
  <div id="affixedTop" class="container-fluid" data-spy="affix" data-offset-top="0">
		
 		<table id="affixedTable" class="table table-condensed ${season}" align="center" style="font-size:11px;width:100%;min-width:350px;">     
    			<tr>
                <td>
                <div style="float:left;width:15%;">
                  <a onclick='loadingData()' class="monthNav btn btn-default btn-sm" title="Step a Month Back" href="viewDailyCalendar.html?dt=<%=prevM%><%= daily.getZone() != null ? "&region-id=" + daily.getZone().getZoneId() : "" %>"><span class="glyphicon glyphicon-fast-backward"></span></a>
                  <a onclick='loadingData()' class="btn btn-default btn-sm" title="Step a Day Back" href="viewDailyCalendar.html?dt=<%=prevD%><%= daily.getZone() != null ? "&region-id=" + daily.getZone().getZoneId() : "" %>"><span class="glyphicon glyphicon-backward"></span></a>
                </div>
                <div class="topTitle" style="float:left;width:70%;text-align:center;">
                   <img src="includes/img/icon/${seasonIconLeft}" class="topTitleImgLeft" border="0"><span style="font-weight:bold;color:white;text-transform:uppercase;"><span class="showWeekDay"><%=ARR_WEEKDAYS[dayofweek-1]%>,&nbsp;</span><%=ARR_MONTHS[curM]%>&nbsp;<%=curD%>,&nbsp;<%=curY%></span><img src="includes/img/icon/${seasonIconRight}" class="topTitleImgRight" border="0">
               	</div>
                <div style="float:right;width:15%;text-align:right;">
                  <a onclick='loadingData()' class="btn btn-default btn-sm" title="Step a Day Ahead" href="viewDailyCalendar.html?dt=<%=nextD%><%= daily.getZone() != null ? "&region-id=" + daily.getZone().getZoneId() : "" %>"><span class="glyphicon glyphicon-forward"></span></a>
                  <a onclick='loadingData()' class="monthNav btn btn-default btn-sm" title="Step a Month Ahead" href="viewDailyCalendar.html?dt=<%=nextM%><%= daily.getZone() != null ? "&region-id=" + daily.getZone().getZoneId() : "" %>"><span class="glyphicon glyphicon-fast-forward"></span></a>
               </div>
                </td>
              </tr>
            </table>
   </div>

    <div class="container-fluid" >           
      
   
     <div align="center" class="no-print"><a href="viewDistrictCalendar.html"><img class="topLogoImg" src="includes/img/pdcalheader-md.png" border=0 style="padding-bottom:10px;"/></a></div>
     
     <div align="center">
   <b><span class="numT"></span> Events for <span class="showWeekDay"><%=ARR_WEEKDAYS[dayofweek-1]%>,&nbsp;</span><%=ARR_MONTHS[curM]%>&nbsp;<%=curD%>,&nbsp;<%=curY%></b><br/>
  
    
     All <span class="numT"></span> Events for today are listed below and are color coded by region.
     <div class="mainView" style="font-size:11px;color:Black;text-align:center;padding-top:5px;padding-bottom:5px;">   
	   <div style="display:inline-block;white-space: nowrap;"><span class="region1a">&nbsp; AVALON (<span class="numA"></span>)&nbsp;</span></div>
	   <div style="display:inline-block;white-space: nowrap;"><span class="region2a">&nbsp; CENTRAL (<span class="numC"></span>)&nbsp;</span> </div>
	   <div style="display:inline-block;white-space: nowrap;"><span class="region3a">&nbsp; WESTERN (<span class="numW"></span>)&nbsp;</span></div>
	  <div style="display:inline-block;white-space: nowrap;"><span class="region4a">&nbsp; LABRADOR (<span class="numL"></span>)&nbsp;</span></div>
	  <div style="display:inline-block;white-space: nowrap;"><span class="region5a">&nbsp; PROVINCIAL (<span class="numP"></span>)&nbsp;</span></div>
  </div>  
  
</div>
  
     <table id="bodyTable" class="dailyEventList table table-condensed" align="center" style="font-size:11px;width:100%;min-width:350px;">  
<%      if(!iter.hasNext())
        {
%>        <tr>
              <td align="center" colspan="4"><div class="alert alert-danger">Sorry, no events currently scheduled for today.</div></td>
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

      			if (agendas != null && agendas.length > 0) {      			
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
              
              
              <%
            //Check Regions of the events.
				if(evt.getEventSchoolZoneID() ==1) {
					 bgcolor ="rgba(191, 0, 0, 0.1)";
					 txtcolor ="rgba(191, 0, 0, 1);";
					 regionName ="AVALON REGION";
					 eA++;
				 } else if (evt.getEventSchoolZoneID() == 2) {
					 bgcolor ="rgba(0, 191, 0, 0.1)";
					 txtcolor ="rgba(0, 191, 0, 1);";
					 regionName ="CENTRAL REGION";
					 eC++;
				 } else if (evt.getEventSchoolZoneID() ==3) {
					 bgcolor ="rgba(255, 132, 0, 0.1)";
					 txtcolor ="rgba(255, 132, 0, 1);";
					 regionName ="WESTERN REGION";
					 eW++;
				 } else if (evt.getEventSchoolZoneID() ==4) {
					 bgcolor ="rgba(127, 130, 255, 0.1)";
					 txtcolor ="rgba(127, 130, 255, 1);";
					 regionName ="LABRADOR REGION";
					 eL++;
				 } else if (evt.getEventSchoolZoneID() ==5) {
					 bgcolor ="rgba(128, 0, 128, 0.1)";
					 txtcolor ="rgba(128, 0, 128, 1);";
					 regionName ="PROVINCIAL";
					 eP++;
				 } else {
					 bgcolor ="#FFFFFF";
					 txtcolor ="#000000;";
					 regionName ="";
				 }
              
              %>              
                                       
              <tr>
                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%> border-top:1px solid <%=txtcolor%>;" colspan=1>Region:</td>
                <td class="tableResult" colspan=3 style="color:<%=txtcolor%>;border-top:1px solid <%=txtcolor%>;"><%=regionName%></td>
              </tr>
              
              <tr>
                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1>Event Type:</td>
                <td class="tableResult" colspan=3><%=evt.getEventType().getEventTypeName()%></td>
              </tr>
              
              <tr>
                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1>Title:</td>
                <td class="tableResult" colspan=3><%=evt.getEventName()%></td>
              </tr>
              <tr>
                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1>Description:</td>
                <td class="tableResult" colspan=3><%=evt.getEventDescription()%></td>
              </tr>
              
              <tr>
                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1>Location (Host):</td>
                <td class="tableResult" colspan=3><%=evt.getEventLocation() + ((evt.getEventSchoolZone() != null) ? " - " + StringUtils.capitalize(evt.getEventSchoolZone().getZoneName()) + " Region" : "") %>
                <% if(evt.isCloseOutDaySession() || evt.isPDOpportunity()) { %>
              	 	<br/><i>Hosted by: <span style="text-transform:capitalize;"><%=evt.getScheduler().getFullNameReverse() %></span></i>
                <% } %>
                </td>
              </tr>
              <tr>
                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1>Start/End Date:</td>
                <td class="tableResult" colspan=3>
                <%=evt.getEventDate()%> to 
                <% if(evt.getEventEndDate() != null) { %>
			              <%=evt.getEventEndDate()%>
			          <% } else {%>              		
			              N/A
		              <%} %>
                 </td>
                
              </tr>
              <% if(!evt.getEventStartTime().equals("UNKNOWN")) { %>
	              <tr>
	                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1>Start/Finish Time:</td>
	                <td class="tableResult" colspan=3><%=evt.getEventStartTime()%> to <%=evt.getEventFinishTime()%></td>	                
	              </tr>
              <% } %>
              
              <% if(evt.hasEventCategories()) { %>
					      <tr>
					        <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1>Categories:</td>
					        <td class="tableResult" colspan=3>
					          <c:forEach items="${evt.eventCategories}" var="cat" varStatus="status">
					          	${cat.name}<c:if test="${status.last eq false}">, </c:if>
					          </c:forEach>
					        </td>
					      </tr>
				      <% } %>
              
              
				      
              <% if(evt.isCloseOutDaySession() || evt.isPDOpportunity()) { %>
	              <tr>
	                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1>Max Spaces (#Registered):</td>
	                <td class="tableResult" colspan=3>
	                  	<% if(evt.getEventMaximumParticipants() > 0) { %>	                  
	                  			<%if (evt.getEventMaximumParticipants()<10) { %>
								10
								<%} else {%>	
	                  			<%=evt.getEventMaximumParticipants()%>
	                    		<%}%>
	                  	<% } else { %>
	                    UNLIMITED
	                  	<% } %>
	                   <% if(!usr.checkRole("TEACHER")) { %>		              
		              	(<%=evt.getRegistrationCount() %>)	              
	              		<% } else { %>
	                   (N/A)           
	              		<%} %>
	                 </td>	              
	             </tr>
	              
              <% } %>
              
              <tr>
	                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1>STATUS:</td>
	                <td class="tableResult" colspan=3>
              		<% if((Utils.compareCurrentDate(evt.getEventDate())==0)||((evt.getEventEndDate()!= null)&&(Utils.compareCurrentDate(evt.getEventDate())==-1)&&(Utils.compareCurrentDate(evt.getEventEndDate())>=0))){%>
                              <span><font color="#008000"> <b><span class="glyphicon glyphicon-star"></span> Active Event</b></font></span>
              		<%}else if(Utils.compareCurrentDate(evt.getEventDate())==-1){%>
                              <span><font color="#FF0000"> <b><span class="glyphicon glyphicon-remove"></span> Past Event</b></font></span>
               		<% } else {%>
               		          <span><font color="#4169E1"> <b><span class="glyphicon glyphicon-ok"></span> Upcoming Event</b></font></span>
               		<%} %>
                    </td>
			 </tr>
              
                <% if(Utils.compareCurrentDate(evt.getEventDate())>0) { %>
                
                <tr>
                <td class="no-print eventOptions" colspan="4" style="background-color:<%=bgcolor%>">
                  
                  <div id="msgrow_<%=rowcnt%>" style="display:none;padding-bottom:5px;"><span id="msg_<%=rowcnt%>"></span></div>
                  
                  <% if(evt.isSchoolPDEntry() || f_agenda != null) { %>
				            <a class="no-print btn btn-xs btn-info" href="/MemberServices/PDReg/agendas/<%=f_agenda.getName()%>" target="_blank" title="View Event Agenda" ><span class="glyphicon glyphicon-search"></span> Agenda</a>
				   <% } %> 
                  
                      <% if(!evt.isScheduler(usr)) { %>
                        <% if(evt.isPDOpportunity()) { %>
                         
                            <% if(revts.get(new Integer(evt.getEventID())) != null){ %>
                              <script>
                              $('#msgrow_<%=rowcnt%>').css('display','inline');
                              $('#msg_<%=rowcnt%>').text('NOTICE: You are already registered for this event.').css('color','green').css('font-weight','bold');                             
                             </script>
                            <% } else if(evt.isFull()) { %>
                              <script>
                              $('#msgrow_<%=rowcnt%>').css('display','block');
                              $('#msg_<%=rowcnt%>').text('NOTICE: Sorry, registration has ended. Event is full.').css('color','red').css('font-weight','bold');   
                              </script>
                            <% } else { %>
                            

                            	<%if((dt_reg_start.compareTo(dt_now) <= 0) || (evt.getEventType().getEventTypeID() == 4) //PD SESSION
                            			){ %>
                            			<a onclick="loadingData()" href="registerEvent.html?id=<%=evt.getEventID()%>" title="Register for this Event" class="btn btn-primary btn-xs"><span class="glyphicon glyphicon-pencil"></span> Register</a>	                             
                               <%}else{%>
                               		&nbsp;
                               		<script>
                               	 		$('#msgrow_<%=rowcnt%>').css('display','block');
                                 		$('#msg_<%=rowcnt%>').text('Sorry, registration not currenty available.').css('color','red').css('font-weight','bold');     
                               		</script>
                               <%}%>
                            <% } %>
                          
                                                   
                          
                          <% if(permissions.containsKey("CALENDAR-VIEW-PARTICIPANTS") && evt.isPDOpportunity()) { %>                           
                           		<a onclick="loadingData()" href="viewEventParticipants.html?id=<%=evt.getEventID()%>" title="Event Participants" class="btn btn-primary btn-xs"><span class="glyphicon glyphicon-user"></span> Participants</a>                             
                          <% } %>
                        <% } else if(evt.isDistrictCalendarCloseOutEntry()){%>
                                <a onclick="loadingData()" href="districtCloseout.html?id=<%=evt.getEventID()%>" title="Sessions" class="btn btn-primary btn-xs">Sessions</a>                                                         
                        <%}%>
                        <% if(permissions.containsKey("CALENDAR-DELETE-ALL")) { %>
                          		<a onclick="loadingData()" href="deleteEvent.html?id=<%=evt.getEventID()%>" title="Delete this Event" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove"></span> Delete</a>                          
                        <% } %>
                      <% } else { %>
                        <% if(evt.hasParticipants()) { %>
                         <a onclick="loadingData()" href="viewEventParticipants.html?id=<%=evt.getEventID()%>" title="Event Participants" class="btn btn-primary btn-xs"><span class="glyphicon glyphicon-user"></span> Participants</a>  
                        <% } else if(evt.isDistrictCalendarCloseOutEntry()){%>
                         <a onclick="loadingData()" href="districtCloseout.html?id=<%=evt.getEventID()%>" title="Sessions" class="btn btn-primary btn-xs">Sessions</a>
                        <%}%>
                        <% if((evt.isCloseOutDaySession() || evt.isPDOpportunity()) || usr.isAdmin()){ %>
                          <a onclick="loadingData()" href="modifyEvent.html?id=<%=evt.getEventID()%>" title="Modify this Event" class="btn btn-warning btn-xs"><span class="glyphicon glyphicon-cog"></span> Modify</a>                                                   
                        <% } %>
                        
                         <a onclick="loadingData()" href="deleteEvent.html?id=<%=evt.getEventID()%>" title="Delete this Event" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove"></span> Delete</a>   
                        
                      <% } %>
                    
                </td>
                </tr>
                
                
                <tr><td colspan=4>&nbsp;</td></tr>
                <%} else { %>
                <tr>
                <td class="no-print eventOptions" colspan="4" style="background-color:<%=bgcolor%>">
                 <% if((evt.isScheduler(usr)|| usr.isAdmin()) && evt.hasParticipants() && (evt.isActive() || evt.isPast())) { %>
                  		<a onclick="loadingData()" href="viewEventAttendance.html?id=<%=evt.getEventID()%>" title="Event Attendance" class="btn btn-xs btn-primary"><span class="glyphicon glyphicon-user"></span> Attendance</a>                           
                <% } else if(evt.hasParticipants() && permissions.containsKey("CALENDAR-VIEW-PARTICIPANTS")) { %>
                         <a onclick="loadingData()" href="viewEventParticipants.html?id=<%=evt.getEventID()%>" title="Event Participants" class="btn btn-xs btn-primary"><span class="glyphicon glyphicon-user"></span> Participants</a>                          
                <% } else if(evt.isDistrictCalendarCloseOutEntry()){%>
                       <a onclick="loadingData()" href="districtCloseout.html?id=<%=evt.getEventID()%>" title="Sessions" class="btn btn-xs btn-primary">Sessions</a>                           
                        
                 <%} else {%>
                        
                        <%} %>
               </td>
                  </tr>
                  <tr><td colspan=4>&nbsp;</td></tr>
                <%}%>
              
        <%  }
          }
        } %>      
      </table>
      
       <div  style="font-size:11px;color:Black;text-align:center;padding-top:5px;">   
	   <div style="display:inline-block;white-space: nowrap;"><span class="region1a">&nbsp; AVALON (<span class="numA"></span>)&nbsp;</span></div>
	   <div style="display:inline-block;white-space: nowrap;"><span class="region2a">&nbsp; CENTRAL (<span class="numC"></span>)&nbsp;</span> </div>
	   <div style="display:inline-block;white-space: nowrap;"><span class="region3a">&nbsp; WESTERN (<span class="numW"></span>)&nbsp;</span></div>
	  <div style="display:inline-block;white-space: nowrap;"><span class="region4a">&nbsp; LABRADOR (<span class="numL"></span>)&nbsp;</span></div>
	  <div style="display:inline-block;white-space: nowrap;"><span class="region5a">&nbsp; PROVINCIAL (<span class="numP"></span>)&nbsp;</span></div>
  </div>  

 	  <br/>
 	  <div align="center"><canvas id="eventsByRegionChart" height="300"></canvas></div>  
 
 <br/>
 
 <div align="center" class="no-print navBottom">
  <a href='#' title='Print this page (pre-formatted)' class="btn btn-primary btn-xs"  onclick="jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:10px;><img width=400 src=includes/img/nlesd-colorlogo.png><br/><br/><b>Professional Development Calendar</b></div><br/>'});"><span class="glyphicon glyphicon-print"></span> Print</a>	                     
 <a onclick="loadingData()" class="no-print btn btn-xs btn-danger" href="viewDistrictCalendar.html"><span class="glyphicon glyphicon-calendar"></span> Back to Calendar</a>
 <a href="#top" class="btn btn-info btn-xs"><span class="glyphicon glyphicon-arrow-up"></span> Back to Top</a>
 </div>

</div>




 <script>$(document).ready(function(){ 
 //Populate numbers of evenst for this page.
 eT=0;
 $(".numA").text(<%=eA%>);
 $(".numC").text(<%=eC%>);
 $(".numW").text(<%=eW%>);
 $(".numL").text(<%=eL%>);
 $(".numP").text(<%=eP%>);
 eT = <%=eA + eC +eW + eL + eP%>;
 $(".numT").text(eT);
 });
 </script>
 
 <script>
  
  
 //Pie Chart
var ctx = document.getElementById('eventsByRegionChart').getContext('2d');
var eventsByRegionChart = new Chart(ctx, {
	type: 'pie',
	  data: {
		  labels: ['Avalon Events','Central Events','Western Events','Labrador Events','Provincial Events'],
	    datasets: [{
	    	backgroundColor: ['rgba(220, 20, 60, 0.3)','rgb(51, 153, 51, 0.3)','rgba(255, 153, 0, 0.3)','rgba(0, 102, 255, 0.3)','rgba(128, 0, 128, 0.3)'],
	    	data: [<%=eA%>, <%=eC%>, <%=eW%>, <%=eL%>,<%=eP%> ]
	    }]
	  },	  
	  options: {		  	  
	      title: {
	         display: true,
	         fontSize: 14,
	         text: 'Breakdown for this Day'
	     },
	     legend: {
	         display: false,
	         fontSize: 14,
	         position: 'top',

	     },
	     responsive: false
	 }
 
	  
	});   
     
     </script>
 
</body>
</html>