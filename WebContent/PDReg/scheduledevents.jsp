<%@ page  language="java" 
          session="true" 
           import="java.util.*, 
         			 java.text.*, 
          			 java.io.*,
          			com.awsd.common.Utils,
          			com.awsd.pdreg.*,
          			com.awsd.security.*,   
          			com.awsd.personnel.*,
		            com.awsd.school.*,           
		            com.nlesd.school.bean.*,
		            com.nlesd.school.service.*,     			
		          	org.apache.commons.lang.*" 
          isThreadSafe="false"%>
          
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>          
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
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

File agenda_dir = new File(session.getServletContext().getRealPath("/") + "/PDReg/agendas/");
int dayofweek;
	String startTimeHour = "";
	String finishTimeHour = "";
	String bgcolor = "";
	String txtcolor = "";
	String regionName = "";
	String color;
	School s = null;
  Event evt = null;
  Event evta = null;
 Iterator iter = null;
  Date now = null;
  User usr = null;
  UserPermissions permissions = null;
  int id, category;
  RegisteredEvents revts = null;
  ScheduledEvents evts = null;
  int eventRCount = 0;
  Calendar cur = null;
  SimpleDateFormat sdf = null;
  File f_agenda = null;  
  usr = (User) session.getAttribute("usr");

  if(usr != null)
  {
    permissions = usr.getUserPermissions();
    
    if(!(permissions.containsKey("CALENDAR-VIEW")))
    {
%>    
<jsp:forward page="/MemberServices/memberServices.html"/>
<%  }
  }
  else
  {
%>  
		<jsp:forward page="/MemberServices/login.html">
      	<jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    	</jsp:forward>
<%}
  boolean registered = false;
 revts = new RegisteredEvents(usr.getPersonnel());
  id = usr.getPersonnel().getPersonnelID();  
  evts = (ScheduledEvents) request.getAttribute("ScheduledEvents");
  EventAttendeeCollection attendees = null;
  attendees = (EventAttendeeCollection) request.getAttribute("EventAttendees");
  
iter = evts.iterator();

int numEmpAttended=0;
int numEmpRegistered = 0;
int numberOfRegistrants = 0;
int numberOfAttenddees = 0;
int yearOfEvent = 0;
int yearArchive=0;
boolean isThereEmptyAttendence = false;
boolean isThereEmptyAttendenceNoRegistrants = false;
boolean eventIsPast = false; 
  //now = (Calendar.getInstance()).getTime();
  
  cur =  Calendar.getInstance();
  dayofweek = cur.get(Calendar.DAY_OF_WEEK);

  
  int eA = 0;
  int eC = 0;
  int eW = 0;
  int eL = 0;
  int eP = 0;
  int eF1 = 0;
  int eF2 = 0;
  int eF3 = 0;
  int eF4 = 0;
  int eF5 = 0;
  int eF6 = 0;
  int eF7 = 0;
  int eF8 = 0;
  int eF9 = 0;
  int eF10 = 0;
  int eF11 = 0;
  int eF12 = 0;
  int eF13 = 0;
  
%>

<html>

<head>
	<title>PD Calendar</title>

<style>
.tableTitle {font-weight:bold;width:20%;color:White;text-transform:uppercase;}
.tableResult {font-weight:normal;width:80%;background-color:#ffffff;}
.tableTitleL {font-weight:bold;width:20%;background-color:#006400;color:White;text-transform:uppercase;}
.tableResultL {font-weight:normal;width:30%;background-color:#ffffff;}
.tableTitleR {font-weight:bold;width:20%;background-color:#006400;color:White;text-transform:uppercase;}
.tableResultR {font-weight:normal;width:30%;background-color:#ffffff;}
input {border:1px solid silver;}
@keyframes blinker {50% {  opacity: 0; }}

</style>
    
    <script type="text/javascript">
    $("#loadingSpinner").css("display","none");    
    </script>
  </head>

<body><a name="top"></a>
<div class="container-fluid no-print topGreenTitleArea" data-spy="affix" data-offset-top="0" style="position:fixed;width:100%;background-color:#DC143C;color:White;text-align:center;font-weight:bold;padding:5px;">                      
 <span class="evntPCount"></span> EVENTS WERE SCHEDULED BY <span style="text-transform:uppercase;"><%=(evts.getPersonnel()!=null)?evts.getPersonnel().getFullNameReverse():"You "%></span></div>
<div class="registerEventDisplay" style="padding-top:50px;font-size:11px;">
 <div style="margin-left:5px;margin-right:5px;">
     <div align="center" class="no-print"><a href="viewDistrictCalendar.html"><img class="topLogoImg" src="includes/img/pdcalheader.png" border=0 style="padding-bottom:10px;"/></a></div>
 
    The following <span class="evntPCount"></span> events (sorted by latest first) for the last 3 years were scheduled by <span style="text-transform:capitalize;"><%=(evts.getPersonnel()!=null)?evts.getPersonnel().getFullNameReverse():"You "%></span>. 
   <br/><br/>
   <div class="alert alert-warning" style="text-align:center;"><b>NOTICE:</b> Make sure you complete the attendance of your past events (starting with 2020 events) as this is REQUIRED.</div>
    
     
     <div style="font-size:10px;color:Black;text-align:center;padding-top:10px;padding-bottom:10px;">   
	 
	 <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" class="numFOSb1">&nbsp; FOS 01 &nbsp;</div>
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" class="numFOSb2">&nbsp; FOS 02 &nbsp;</div>
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" class="numFOSb3">&nbsp; FOS 03 &nbsp;</div>
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" class="numFOSb4">&nbsp; FOS 04 &nbsp;</div>
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" class="numFOSb5">&nbsp; FOS 05 &nbsp;</div>
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" class="numFOSb6">&nbsp; FOS 06 &nbsp;</div>
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" class="numFOSb7">&nbsp; FOS 07 &nbsp;</div><br/>
  
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" class="numFOSb8">&nbsp; FOS 08 &nbsp;</div>
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" class="numFOSb9">&nbsp; FOS 09 &nbsp;</div>
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" class="numFOSb10">&nbsp; FOS 10 &nbsp;</div>
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" class="numFOSb11">&nbsp; FOS 11 &nbsp;</div>
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" class="numFOSb12">&nbsp; FOS 12 &nbsp;</div>
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" class="numFOSb13">&nbsp; FOS 13 &nbsp;</div>
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" class="numUnkb">&nbsp; OTHER &nbsp;</div><br/>
	 
	 
	 
	  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" class="region1">&nbsp; AVALON REGION &nbsp;</div>
	  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" class="region2">&nbsp; CENTRAL REGION &nbsp;</div>
	  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" class="region3">&nbsp; WESTERN REGION &nbsp;</div> 
	  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" class="region4">&nbsp; LABRADOR REGION &nbsp;</div>
	  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" class="region5">&nbsp; PROVINCIAL &nbsp;</div>
  </div>
      
        <table class="table table-condensed" style="font-size:11px;">

<% if (evts.size()<1) { %>
	<tr>
<td align="center" colspan="4"><div class="alert alert-danger">Sorry, you have no scheduled events created for staff.</div></td>
</tr>


<%} else {
	
			
	for (int i = evts.size() - 1; i > 0 ; i--) {	

				evt=evts.get(i);
				
				int evtDate=evt.getEventDate().getYear()+1900;
				//set # years to show back.
				yearArchive=cur.get(Calendar.YEAR)-2;				
			if (evtDate>=yearArchive) {		
					
					
					
              eventRCount++;
              numEmpAttended=0;
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
						//FOS 01
				 } else if (evt.getEventSchoolZoneID() ==267) {
					 bgcolor ="rgba(2, 134, 209,0.1)";
					 txtcolor ="rgba(2, 134, 209, 1);";
					 regionName ="FOS 01";
					 eF1++;
					//FOS 02
				 } else if (evt.getEventSchoolZoneID() ==607) {
					 bgcolor ="rgba(148, 39, 97,0.1)";
					 txtcolor ="rgba(148, 39, 97, 1);";
					 regionName ="FOS 02";
					 eF2++;
					//FOS 03
				 } else if (evt.getEventSchoolZoneID() ==608) {
					 bgcolor ="rgba(169, 205, 130,0.1)";
					 txtcolor ="rgba(169, 205, 130, 1);";
					 regionName ="FOS 03";
					 eF3++;
					//FOS 04
				 } else if (evt.getEventSchoolZoneID() ==609) {
					 bgcolor ="rgba(15, 157, 87,0.1)";
					 txtcolor ="rgba(15, 157, 87, 1);";
					 regionName ="FOS 04";
					 eF4++;
					//FOS 05
				 } else if (evt.getEventSchoolZoneID() ==610) {
					 bgcolor ="rgba(1, 87, 155,0.1)";
					 txtcolor ="rgba(1, 87, 155, 1);";
					 regionName ="FOS 05";
					 eF5++;
					//FOS 06
				 } else if (evt.getEventSchoolZoneID() ==611) {
					 bgcolor ="rgba(57, 73, 171,0.1)";
					 txtcolor ="rgba(57, 73, 171, 1);";
					 regionName ="FOS 06";
					 eF6++;
					//FOS 07
				 } else if (evt.getEventSchoolZoneID() ==612) {
					 bgcolor ="rgba(32, 126, 75,0.1)";
					 txtcolor ="rgba(32, 126, 75, 1);";
					 regionName ="FOS 07";
					 eF7++;
					//FOS 08
				 } else if (evt.getEventSchoolZoneID() ==613) {
					 bgcolor ="rgba(133, 123, 29,0.1)";
					 txtcolor ="rgba(133, 123, 29, 1);";
					 regionName ="FOS 08";
					 eF8++;
					//FOS 09
				 } else if (evt.getEventSchoolZoneID() ==614) {
					 bgcolor ="rgba(165, 39, 20,0.1)";
					 txtcolor ="rgba(165, 39, 20, 1);";
					 regionName ="FOS 09";
					 eF9++;
					//FOS 10
				 } else if (evt.getEventSchoolZoneID() ==615) {
					 bgcolor ="rgba(103, 58, 183,0.1)";
					 txtcolor ="rgba(103, 58, 183, 1);";
					 regionName ="FOS 10";
					 eF10++;
					//FOS 11
				 } else if (evt.getEventSchoolZoneID() ==567) {
					 bgcolor ="rgba(249, 171, 45,0.1)";
					 txtcolor ="rgba(249, 171, 45, 1);";
					 regionName ="FOS 11 (DSS)";
					 eF11++;
					//FOS 12
				 } else if (evt.getEventSchoolZoneID() ==627) {
					 bgcolor ="rgba(105, 83, 78,0.1)";
					 txtcolor ="rgba(105, 83, 78, 1);";
					 regionName ="FOS 12 (DSS)";
					 eF12++;
					//FOS 13
				 } else if (evt.getEventSchoolZoneID() ==628) {
					 bgcolor ="rgba(175, 180, 43,0.1)";
					 txtcolor ="rgba(175, 180, 43, 1);";
					 regionName ="FOS 13 (DSS)";
					 eF13++;
					 
				 } else {
					 bgcolor ="#FFFFFF";
					 txtcolor ="#000000;";
					 regionName ="";
				 }
                    
            
            
            if(!evt.isPrivateCalendarEntry() || (evt.isPrivateCalendarEntry() && (evt.getSchedulerID() == id))) { %>
            
             <tr>
                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%> border-top:1px solid <%=txtcolor%>;" colspan=1>Family/Region:</td>
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
                <td class="tableResult" colspan=3><%=evt.getEventLocation() + ((evt.getEventSchoolZone() != null) ? " - " + StringUtils.capitalize(evt.getEventSchoolZone().getZoneName()) + " Region" : "&nbsp;"+regionName) %>
                <% if(evt.isCloseOutDaySession() || evt.isPDOpportunity()) { %>
              	 	<br/>(Hosted by: <span style="text-transform:Capitalize;"><%=evt.getScheduler().getFullNameReverse()%></span>)  
                <% } else {%>
               	(N/A)
              <%} %>
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
	                    <%=evt.getEventMaximumParticipants()%>
	                  	<% } else { %>
	                    UNLIMITED
	                  	<% } %>	                             
		              	(<%=evt.getRegistrationCount() %>)	           
	              	
	              		
	                 </td>	              
	             </tr>
	              
            
              
               <% if(evt.isPast()) { %>           
               <tr>
	                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1>#Attended:</td>
	                <td class="tableResult" colspan=3>
	                  <span id="numberAttended<%=i%>"></span>
	                  <div id="numberAttendedWarning<%=i%>" style="display:none;float:right;color:Red;padding:2px;border:1px solid red;background-color:Yellow;animation: blinker 1s linear infinite;">	                   
	                    <span class="glyphicon glyphicon-remove"></span> ATTENDENCE NOT COMPLETE! <span class="glyphicon glyphicon-remove"></span>
	                </div>
	                <div id="numberAttendedInfo<%=i%>" style="display:none;float:right;color:DimGrey;">	                   
	                   Note: Not all registered attended.
	                   <span class="glyphicon glyphicon-info-sign"></span>
	                </div>
	                <div id="numberAttendedSuccess<%=i%>" style="display:none;float:right;color:Green;">	                   
	                   All registered attended.
	                   <span class="glyphicon glyphicon-ok"></span>
	                </div>
	                 <div id="numberAttendedNone<%=i%>" style="display:none;float:right;color:Red;border:1px solid red;">	                   
	                   <span class="glyphicon glyphicon-info-sign"></span> NO REGISTRANTS? <span class="glyphicon glyphicon-info-sign"></span>
	                </div>
	                  
	                 </td>	              
	             </tr>
              <%} %>
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
              
              
            
				      
			<tr class="no-print">
                  <td class="eventOptions" style="background-color:<%=bgcolor%>;"  colspan="4" valign="middle"> 				
  					
  					<%
  					File[] agendas = agenda_dir.listFiles(new AgendaFilenameFilter(evt));

  	      			if (agendas != null && agendas.length > 0) {      			
  	            	f_agenda = agendas[0];
  	      			}
  	      			else {
  	      				f_agenda = null;
  	      			}
  					
  					%>
  					
  					
  					
  					<% if(f_agenda != null) { %>
				            <a class="no-print btn btn-xs btn-info" href="/MemberServices/PDReg/agendas/<%=f_agenda.getName()%>" target="_blank" title="View Event Agenda" ><span class="glyphicon glyphicon-search"></span> Agenda</a>
					<% } %> 
	<% if(Utils.compareCurrentDate(evt.getEventDate()) > 0) { %>
               					
               					
               					<% if (evt.getSchedulerID() != id) { %>                        
                        
							                        <% if(evt.isPDOpportunity()) { %>                        
							                            					<% if(revts.get(new Integer(evt.getEventID())) != null){ %>
							                            								  <div class="alert alert-success" align="center">NOTICE: You have already registered for this event.</div>  
							                            					<% } else if(evt.isFull()) { %>
							                              									 <div class="alert alert-danger" align="center">SORRY! Registration has ended. Event is full.</div>  
							                            					<% } else { %>              
							                            									<a href="registerEvent.html?id=<%=evt.getEventID()%>" class="btn btn-xs btn-success">Register</a>                                
							                              					<% } %>                          
							                        <% } else if(evt.isCloseOutDaySession()) { %>
							                           			<a href="districtCloseout.html?id=<%=EventDB.getCloseOutEvent(evt).getEventID()%>" class="btn btn-xs btn-primary">Sessions</a> 
							                        <% } else if(evt.isDistrictCalendarCloseOutEntry()) { %>
							                           			 <a href="districtCloseout.html?id=<%=evt.getEventID()%>" class="btn btn-xs btn-primary">Sessions</a>
							                        <% } %>
                       								
                        							<% if((evt.isPDOpportunity()||evt.isCloseOutDaySession()) && permissions.containsKey("CALENDAR-VIEW-PARTICIPANTS")) { %>
                             									<a href="viewEventParticipants.html?id=<%=evt.getEventID()%>&ref=sce&vusr=<%=evts.getPersonnel().getPersonnelID() %>" class="btn btn-xs btn-primary">Participants</a>
                        							<% } %>
                        							<% if(permissions.containsKey("CALENDAR-DELETE-ALL")) { %>
                          										 <a href="deleteEvent.html?id=<%=evt.getEventID()%>" class="btn btn-xs btn-danger">Delete</a>
                       								 <% } %>
                     
                      			<% } else { %>                        
                         										<a href="modifyEvent.html?id=<%=evt.getEventID()%>" class="btn btn-xs btn-warning">Modify</a>
                         										<a href="deleteEvent.html?id=<%=evt.getEventID()%>" class="btn btn-xs btn-danger">Delete</a>
                        							<% if(evt.isCloseOutDaySession() || evt.isPDOpportunity()) { %>                       
                       											<a href="viewEventParticipants.html?id=<%=evt.getEventID()%>&ref=sce&vusr=<%=evts.getPersonnel().getPersonnelID() %>" class="btn btn-xs btn-primary">Participants</a>
                        							<% } else if(evt.isDistrictCalendarCloseOutEntry()) { %>
                            									<a href="districtCloseout.html?id=<%=evt.getEventID()%>" class="btn btn-xs btn-primary">Sessions</a>
                        							<% } %>
                      			<% } %>
              
<%} else { %>
                  
                         
                  
                   	<% if((evt.isScheduler(usr) || usr.isAdmin()) && evt.hasParticipants() && (evt.isActive() || evt.isPast())) { %>
                         <a onclick='loadingData()' href="viewEventAttendance.html?id=<%=evt.getEventID()%>&ref=sce&vusr=<%=evts.getPersonnel().getPersonnelID() %>" title="Event Attendance" class="btn btn-xs btn-primary"><span class="glyphicon glyphicon-user"></span> Attendance</a>   
                      	<%} else if((permissions.containsKey("CALENDAR-VIEW-PARTICIPANTS") || evt.isScheduler(usr)) && evt.hasParticipants()) { %>
                        <a onclick='loadingData()' href="viewEventParticipants.html?id=<%=evt.getEventID()%>&ref=sce&vusr=<%=evts.getPersonnel().getPersonnelID() %>" title="Event Participants" class="btn btn-primary btn-xs"><span class="glyphicon glyphicon-user"></span> Participants</a>
                     <% } else if(evt.isDistrictCalendarCloseOutEntry()) { %>
                            		<a onclick='loadingData()' href="districtCloseout.html?id=<%=evt.getEventID()%>" class="btn btn-primary btn-xs">Sessions</a>
                        <% } %>
                  
                        
<%} %>


<% 
if((evt.isCloseOutDaySession() || evt.isPDOpportunity()) && evt.isPast()) { 
	
numberOfRegistrants = evt.getRegistrationCount();
numberOfAttenddees = evt.getAttendees();
eventIsPast = evt.isPast();
yearOfEvent = evt.getEventDate().getYear()+1900;
//If number attendees is greator than 0....
if (numberOfAttenddees > 0) { 
								
								//isThereEmptyAttendence = false;					
								if  (numberOfAttenddees == numberOfRegistrants) { %>		
									<script>									
									$("#numberAttendedSuccess<%=i%>").css("display","block");	// All registered attended.
									</script>			
									<% } else { %>
									<script>
									$("#numberAttendedInfo<%=i%>").css("display","block");  //Note: Not all registered attended.
									</script>	
								<%	}
} else {
	//There were no attendees, so
	//check to see if 0 for both, if so not attendence no registrants no empty attendence notice,				
			
				if  (numberOfRegistrants == 0) {	%>
								<script>	
								$("#numberAttendedNone<%=i%>").css("display","block"); //No registered participants to attend.
							 	 </script>
				
				<%} else {						
					if (yearOfEvent >2019) {
					isThereEmptyAttendence = true;	
					}
				%>				
									<script>	
								
										$("#numberAttendedWarning<%=i%>").css("display","block"); //ATTENDENCE NOT COMPLETE!
									</script>	
				<%}%>
	
<%}%>
		<script>$("#numberAttended<%=i%>").text("<%=evt.getAttendees() %>");</script>
	<%} %>	
	</td>
			</tr>	      
			<tr>
			<td colspan=4>&nbsp;</td>
			</tr>	      
            
<% }%>

<%}%>
				
  <%}%>    
	
                

         <%} %>   

</table>

<div align="center" class="no-print navBottom">
  <a href='#' title='Print this page (pre-formatted)' class="btn btn-primary btn-xs"  onclick="jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:10px;><img width=400 src=includes/img/nlesd-colorlogo.png><br/><br/><b>Professional Development Calendar</b></div><br/>'});"><span class="glyphicon glyphicon-print"></span> Print</a>	                     
  			
 <a onclick="loadingData()" class="no-print btn btn-xs btn-danger" href="viewDistrictCalendar.html">Back to Calendar</a>
 <a href="#top" class="btn btn-info btn-xs"><span class="glyphicon glyphicon-arrow-up"></span> Back to Top</a>
 
 </div>
</div></div>


<div style="clear:both;"></div>
<br/>&nbsp;<br/>

<script>
$(document).ready(function(){
	$(".evntPCount").text(<%=eventRCount%>);	
	attCheck = <%=isThereEmptyAttendence %>;
	yearCheck = <%=cur.get(Calendar.YEAR)%>;
	isEventPast = <%=eventIsPast%>;	
	//Event without attendence? Year 2020? Event is past? Then do attendence warning.
	if (attCheck == true && isEventPast == true && yearCheck > 2019 ) {
		$("#yearTxt").text(yearCheck);	
		$('#noAttendance').modal('show');
	}	
});

$(".numFOSb1").css("color","rgba(2, 134, 209,1)").css("background-color","rgba(2, 134, 209,0.2)");

$(".numFOSb2").css("color","rgba(148, 39, 97,1)").css("background-color","rgba(148, 39, 97,0.2)");

$(".numFOSb3").css("color","rgba(169, 205, 130,1)").css("background-color","rgba(169, 205, 130,0.2)");

$(".numFOSb4").css("color","rgba(15, 157, 87,1)").css("background-color","rgba(15, 157, 87,0.2)");

$(".numFOSb5").css("color","rgba(1, 87, 155,1)").css("background-color","rgba(1, 87, 155,0.2)");

$(".numFOSb6").css("color","rgba(57, 73, 171,1)").css("background-color","rgba(57, 73, 171,0.2)");

$(".numFOSb7").css("color","rgba(32, 126, 75,1)").css("background-color","rgba(32, 126, 75,0.2)");

$(".numFOSb8").css("color","rgba(133, 123, 29,1)").css("background-color","rgba(133, 123, 29,0.2)");

$(".numFOSb9").css("color","rgba(165, 39, 20,1)").css("background-color","rgba(165, 39, 20,0.2)");

$(".numFOSb10").css("color","rgba(103, 58, 183,1)").css("background-color","rgba(103, 58, 183,0.2)");

$(".numFOSb11").css("color","rgba(249, 171, 45,1)").css("background-color","rgba(249, 171, 45,0.2)");

$(".numFOSb12").css("color","rgba(105, 83, 78,1)").css("background-color","rgba(105, 83, 78,0.2)");

$(".numFOSb13").css("color","rgba(175, 180, 43,1)").css("background-color","rgba(175, 180, 43,0.2)");

$(".numUnkb").css("color","Black").css("background-color","Silver");


</script>



<!-- Event Attendence Warning -->
<div id="noAttendance" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" style="color:Red;"><span class="glyphicon glyphicon-info-sign"></span> NOTICE: <span id="yearTxt"></span> Event Attendance</h4>
      </div>
      <div class="modal-body"><img src="includes/img/x_mark.png" border=0 style="max-width:150px;float:right;">
        You have events listed on this page that require your input to complete required attendance. 
        <p><b>This is required for events scheduled in 2020 forward.</b>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-sm btn-success" data-dismiss="modal">OK</button>
      </div>
    </div>

  </div>
</div>
</body>
</html>