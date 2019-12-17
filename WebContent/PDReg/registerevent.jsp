<%@ page language="java"
          session = "true"
          import="com.awsd.pdreg.*,
          		  com.awsd.security.*, 
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
  String pageTitle;
  String bgcolor = "";
  String txtcolor = "";
  String regionName = "";
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
      pageTitle = "Past Event";     
    }
    else
    {
      status = 4;
      pageTitle = "Event Details"; 
    }
  }
  else
  {   
    if((Utils.compareCurrentDate(evt.getEventDate())==0)||
      (((evt.getEventEndDate()!= null)&&(Utils.compareCurrentDate(evt.getEventDate())==-1)&&(Utils.compareCurrentDate(evt.getEventEndDate())>=0)) ))
    {
      status = 1;
      pageTitle = "Active Event"; 
    }
    else if(Utils.compareCurrentDate(evt.getEventDate()) < 0)
    {
      status = 2;
      pageTitle = "Past Event"; 
    }
    else if((usr.getPersonnel().getPersonnelID()==evt.getSchedulerID())
      || (usr.isAdmin())
      || (evt.isDistrictCalendarCloseOutEntry() || evt.isDistrictCalendarEntry()
      || evt.isHolidayCalendarEntry() || evt.isReminderCalendarEntry()
      || evt.isSchoolPDEntry()))
    {
      status = 4;
      pageTitle = "Event Details"; 
    }
    else
    {
      status = 3;
      pageTitle = "Register"; 
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

            //Check Regions of the events.
				if(evt.getEventSchoolZoneID() ==1) {
					 bgcolor ="rgba(191, 0, 0, 0.1)";
					 txtcolor ="rgba(191, 0, 0, 1);";
					 regionName ="AVALON REGION";
				 } else if (evt.getEventSchoolZoneID() == 2) {
					 bgcolor ="rgba(0, 191, 0, 0.1)";
					 txtcolor ="rgba(0, 191, 0, 1);";
					 regionName ="CENTRAL REGION";
				 } else if (evt.getEventSchoolZoneID() ==3) {
					 bgcolor ="rgba(255, 132, 0, 0.1)";
					 txtcolor ="rgba(255, 132, 0, 1);";
					 regionName ="WESTERN REGION";
				 } else if (evt.getEventSchoolZoneID() ==4) {
					 bgcolor ="rgba(127, 130, 255, 0.1)";
					 txtcolor ="rgba(127, 130, 255, 1);";
					 regionName ="LABRADOR REGION";
				 } else if (evt.getEventSchoolZoneID() ==5) {
					 bgcolor ="rgba(128, 0, 128, 0.1)";
					 txtcolor ="rgba(128, 0, 128, 1);";
					 regionName ="PROVINCIAL";
				 } else {
					 bgcolor ="#FFFFFF";
					 txtcolor ="#000000;";
					 regionName ="";
				 }              
             
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



</style>
    
    <script type="text/javascript">
    $("#loadingSpinner").css("display","none");      
    clicked = false;
    </script>
    
   
    
  </head>

  <body>
   
   <form action="registerEvent.html" name="register" method="post">
   <div class="container-fluid no-print topGreenTitleArea" data-spy="affix" data-offset-top="0" style="position:fixed;width:100%;background-color:#008B8B;color:White;text-align:center;font-weight:bold;padding:5px;">                      
     <span class="pageTitle"><%=evt.getEventType().getEventTypeName()%> <%=evt.getEventName()%></span>
</div>

<div class="registerEventDisplay" style="padding-top:50px;font-size:11px;">
 <div style="margin-left:5px;margin-right:5px;">
   <div align="center" class="no-print"><a href="viewDistrictCalendar.html"><img class="topLogoImg" src="includes/img/pdcalheader.png" border=0 style="padding-bottom:10px;"/></a></div>
    				
    <table class="table table-condensed" style="font-size:11px;">	


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
					          	${cat.categoryName}<c:if test="${status.last eq false}">, </c:if>
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
		              	(<%=evt.getRegistrationCount()%>)	              
	              		<% } else { %>
	                   (N/A)           
	              		<%} %>
	                 </td>	              
	             </tr>
	              
              <% } %>
              
  
      
      <% if((evt.isScheduler(usr) || usr.isAdmin()) && evt.hasEventRequirements()) { %>
      <tr>
        <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1>Requirements:</td>
         <td class="tableResult" colspan=3>
        	<c:forEach items="${evt.eventRequirements}" var="req" varStatus="status">
          		<i>${req.requirement}</i><c:if test="${not empty req.extrainfo }">&nbsp;[${ req.extrainfo }]</c:if><c:if test="${status.last eq false}"><br /></c:if>
          </c:forEach>
        </td>
      </tr>
      <% } %>
      
            <% if((evt.isScheduler(usr) || usr.isAdmin()) && (evt.getEventSchoolZone().getZoneId() == 4)) { %>
      	<tr>
	        <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>" colspan=1> Nunatsiavut Government Funded:</td>
	        <td class="tableResult" colspan=3 ">
	          <%= (evt.isGovernmentFunded() ? "YES" : "NO") %>
	        </td>
	      </tr>
      <% } %>      
      
     		 <tr>
	                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%>border-bottom:1px solid <%=txtcolor%>" colspan=1>STATUS:</td>
	                <td class="tableResult" colspan=3 style="border-bottom:1px solid <%=txtcolor%>"> 
              		<% if((Utils.compareCurrentDate(evt.getEventDate())==0)||((evt.getEventEndDate()!= null)&&(Utils.compareCurrentDate(evt.getEventDate())==-1)&&(Utils.compareCurrentDate(evt.getEventEndDate())>=0))){%>
                              <span><font color="#008000"> <b><span class="glyphicon glyphicon-star"></span> Active Event</b></font></span>
              		<%}else if(Utils.compareCurrentDate(evt.getEventDate())==-1){%>
                              <span><font color="#FF0000"> <b><span class="glyphicon glyphicon-remove"></span> Past Event</b></font></span>
               		<% } else {%>
               		          <span><font color="#4169E1"> <b><span class="glyphicon glyphicon-ok"></span> Upcoming Event</b></font></span>
               		<%} %>
                    </td>
			 </tr>     

    </table>
      
     <% if(request.getAttribute("msgERR") != null) { %>
      
          <div class="alert alert-danger" align="center"><%= request.getAttribute("msgERR") %></div>
       
      <% } else if(request.getAttribute("msg") != null) {  %>
         <div class="alert alert-danger" align="center"><%= request.getAttribute("msg") %></div>
        
      <% } else if(request.getAttribute("msgOK") != null) {  %>
     
          <div class="alert alert-success" align="center"><%= request.getAttribute("msgOK") %></div>
       
      <% } else if(status == 33) { %>
             
            <div class="alert alert-info" align="center">NOTICE: You have already registered for this event.</div>         
              
      <% } else if(status == 333) { %>
             
            <div class="alert alert-danger" align="center">SORRY! Registration has ended. Event is full.</div>         
           
      <% } %>
                  
    
  

   
  <div align="center" class="no-print navBottom">
   <% if(evt.isSchoolPDEntry() || f_agenda != null) { %>
				            <a class="no-print btn btn-xs btn-info" href="/MemberServices/PDReg/agendas/<%=f_agenda.getName()%>" target="_blank" title="View Event Agenda" ><span class="glyphicon glyphicon-search"></span> Agenda</a>
	<% } %> 
      <% if((request.getAttribute("msgERR") == null) && (request.getAttribute("msgOK") == null) && (status == 3)) { %>
                    <input type="hidden" name="id" value="<%=evt.getEventID()%>">
                    <input type="hidden" name="confirmed" value="true">
                   
                    <%if((dt_reg_start.compareTo(dt_now) <= 0) || (evt.getEventType().getEventTypeID() == 4)){ %>	                    
	                    <a href="#" class="btn btn-success btn-xs" onclick="onClick(document.register);" title="Confirm Registration for this Event."><span class="glyphicon glyphicon-pencil"></span> Register?</a>
                    	<a onclick='loadingData()'class="btn btn-xs btn-danger" title="Cancel" href="javascript:history.go(-1);"><span class="glyphicon glyphicon-remove"></span> Cancel</a>	                    
	                <%}else{%>
                    	<div class="alert alert-danger">Registration currently not available.</div>
                    <%}%>
                    <% if((permissions.containsKey("CALENDAR-VIEW-PARTICIPANTS"))) { %>
                       <a onclick='loadingData()' href="viewEventParticipants.html?id=<%=evt.getEventID()%>" title="Event Participants" class="btn btn-primary btn-xs"><span class="glyphicon glyphicon-user"></span> Participants</a>
                    <% } %>
                  <% } else { %>
                    <% if(status != 3) { %>
                    	<% if((evt.isScheduler(usr) || usr.isAdmin()) && evt.hasParticipants() && (evt.isActive() || evt.isPast())) { %>
                         <a onclick='loadingData()' href="viewEventAttendance.html?id=<%=evt.getEventID()%>" title="Event Attendance" class="btn btn-xs btn-primary"><span class="glyphicon glyphicon-user"></span> Attendance</a>   
                      	<%} else if((permissions.containsKey("CALENDAR-VIEW-PARTICIPANTS") || evt.isScheduler(usr)) && evt.hasParticipants()) { %>
                        <a onclick='loadingData()' href="viewEventParticipants.html?id=<%=evt.getEventID()%>" title="Event Participants" class="btn btn-primary btn-xs"><span class="glyphicon glyphicon-user"></span> Participants</a>
                      	<% } %>
                      	<%if (!evt.isPast() || usr.isAdmin()) {%>
                      	<% if((evt.isScheduler(usr) && evt.hasParticipants())) { %>
                         <a onclick='loadingData()' href="modifyEvent.html?id=<%=evt.getEventID()%>" title="Modify this Event" class="btn btn-warning btn-xs"><span class="glyphicon glyphicon-cog"></span> Modify</a> 
                      	<% } %>
                      	<% if(permissions.containsKey("CALENDAR-DELETE-ALL") || evt.isScheduler(usr)) { %>
                          <a onclick='loadingData()' href="deleteEvent.html?id=<%=evt.getEventID()%>" title="Delete this Event" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove"></span> Delete</a>   
                        <% } %>
                        <% } %>
                    <% } %>   
                   
                  <% } %>
                  
                  <%if(status == 33) { %>
                   <a href="deregisterEvent.html?id=<%=evt.getEventID()%>" title="De-Register from this Event?" class="btn btn-danger btn-xs">De-Register?</a>  
      				<%} %>
  
  <a href='#' title='Print this page (pre-formatted)' class="btn btn-primary btn-xs"  onclick="jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:10px;><img width=400 src=includes/img/nlesd-colorlogo.png><br/><br/><b>Professional Development Calendar</b></div><br/>'});"><span class="glyphicon glyphicon-print"></span> Print</a>	                     
  
  <% if(request.getAttribute("details") == null) {%>
  <a onclick='loadingData()' class="no-print btn btn-xs btn-danger" href="viewDistrictCalendar.html"><span class="glyphicon glyphicon-calendar"></span> Back to Calendar</a>
  <% } else { %>
  <a onclick='loadingData()'class="btn btn-xs btn-danger" title="BACK" href="javascript:history.go(-1);"><span class="glyphicon glyphicon-triangle-left"></span> Back</a>
   <%} %>
  </div>
   
     </div></div>
   </form>
     
  </body>
</html>