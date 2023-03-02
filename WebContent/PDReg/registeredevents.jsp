<%@ page  language="java" 
          session="true" 
          import="java.util.*, 
          java.text.*, 
           java.io.*,
          com.awsd.common.Utils,
          com.awsd.pdreg.*,
          com.awsd.security.*,
           org.apache.commons.lang.*" 
          isThreadSafe="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>          
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

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
  int dayofweek;
  	String startTimeHour = "";
  	String finishTimeHour = "";
  	String bgcolor = "";
	String txtcolor = "";
	String regionName = "";
	String color;
	//Google Calendar Vars to Add to user Google Calendar
	
	String gcalEvtTitle;
    String gcalEvtDescription;
    String gcalEvtLocation;
    String gcalEvtStartTime;
    String gcalEvtEndTime;
    int gcalEvtStartTimeH;
    int gcalEvtStartTimeM;
    int gcalEvtFinishTimeH;
    int gcalEvtFinishTimeM;
    String gcalEvtStartTimeAMPM;
    String gcalEvtFinishTimeAMPM;
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
  Calendar cur = null;
  SimpleDateFormat sdf = null;
  File f_agenda = null;
  RegisteredEvents events = null;
  Event evt = null;
  Iterator iter = null;
  Date now = null;
  User usr = null;
  int eventRCount = 0;
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
    </script>
  </head>

<body>

<div class="container-fluid no-print topGreenTitleArea" data-spy="affix" data-offset-top="0" style="position:fixed;width:100%;background-color:#008B8B;color:White;text-align:center;font-weight:bold;padding:5px;">                      
 <span class="evntPCount"></span> REGISTERED EVENTS
</div>
<div class="registerEventDisplay" style="padding-top:50px;font-size:11px;">
 <div style="margin-left:5px;margin-right:5px;">
     <div align="center" class="no-print"><a href="viewDistrictCalendar.html"><img class="topLogoImg" src="includes/img/pdcalheader.png" border=0 style="padding-bottom:10px;"/></a></div>
 

     You are registered for the following <span class="evntPCount"></span> events. You may de-register from any listed event by selecting DE-REGISTER option. You can also ADD this event to your Google Calendar.
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
<%      if(!iter.hasNext())
        {
%>       <tr>
              <td align="center" colspan="4"><div class="alert alert-danger">Sorry, you have no registered events scheduled.</div></td>
          </tr>
<%      }
        else
        {
        	
          while (iter.hasNext())
          {
            evt = (Event) (((Map.Entry)iter.next()).getValue());
            eventRCount++;
         
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

%>         


                        
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
              
              
            
				      
			<tr class="no-print">
                  <td class="eventOptions" style="background-color:<%=bgcolor%>;"  colspan="4" valign="middle"> 				
  					
  					<% if(evt.isSchoolPDEntry() || f_agenda != null) { %>
				          <a class="no-print btn btn-xs btn-info" href="/MemberServices/PDReg/agendas/<%=f_agenda.getName()%>" target="_blank" title="View Event Agenda" ><span class="glyphicon glyphicon-search"></span> Agenda</a>
	<% } %> 
  					
  					
                 <!-- Add to my Google calendar if user is logged in --> 
                 <% if(usr != null) {              
                 //Get rid of stuff Google hates
                 gcalEvtTitle = evt.getEventName().replaceAll("\\<.*?\\>", "");
                 gcalEvtDescription = evt.getEventDescription().replaceAll("\\<.*?\\>", "");
                 gcalEvtLocation = evt.getEventLocation().replaceAll("\\<.*?\\>", "");
                //Default to 8:30AM if no start time set.                
	                if(evt.getEventStartTime().equals("UNKNOWN")) { 
	                	 gcalEvtStartTimeH = 8;	                	 
	                	 gcalEvtStartTimeM = 30;	                	
	                } else {
	                	gcalEvtStartTimeH = Integer.parseInt(evt.getEventStartTimeHour());
	                	gcalEvtStartTimeM = Integer.parseInt(evt.getEventStartTimeMinute());	                
	                }          
                //Default to 4:30pm if no end time set
	                if(evt.getEventFinishTime().equals("UNKNOWN")) { 	                	 
	                	 gcalEvtFinishTimeH = 4;
	                	 gcalEvtFinishTimeM = 30;
	                	 
	                
	                	 
	                } else {	                	
	                	gcalEvtFinishTimeH = Integer.parseInt(evt.getEventFinishTimeHour());
	                	gcalEvtFinishTimeM = Integer.parseInt(evt.getEventFinishTimeMinute());
	                } 
                 gcalEvtStartTimeAMPM = (evt.getEventStartTimeAMPM()!=null?evt.getEventStartTimeAMPM():"AM");
                 gcalEvtFinishTimeAMPM = (evt.getEventFinishTimeAMPM()!=null?evt.getEventFinishTimeAMPM():"PM");
                //Fix for events for 24hr format assuming events only from 8am
                 if (gcalEvtStartTimeAMPM.equalsIgnoreCase("PM") && gcalEvtStartTimeH < 12 ) {
                	 gcalEvtStartTimeH= gcalEvtStartTimeH + 12;
                 }
                 if (gcalEvtFinishTimeAMPM.equalsIgnoreCase("PM") && gcalEvtFinishTimeH < 12 ) {
                	 gcalEvtFinishTimeH = gcalEvtFinishTimeH + 12;
                 }
                 
                 if( gcalEvtStartTimeH <10 ) {
                	startTimeHour =  "0" + Integer.toString(gcalEvtStartTimeH);
                 } else {
                	 startTimeHour =  Integer.toString(gcalEvtStartTimeH);
                 }
                 
                 if( gcalEvtFinishTimeH <10 ) {
                 	finishTimeHour =  "0" + Integer.toString(gcalEvtFinishTimeH);
                  } else {
                 	 finishTimeHour =  Integer.toString(gcalEvtFinishTimeH);
                  }
                 
               	%>               	
               	
                 <fmt:formatDate type = "date" pattern = "yyyyMMdd" var="eventStartDate" value = "<%=evt.getEventDate()%>" />
                 <fmt:formatDate type = "date" pattern = "yyyyMMdd" var="eventEndDate" value = "<%=evt.getEventEndDate() %>" />                
               
                 	<a href="http://www.google.com/calendar/render?
					action=TEMPLATE
					&text=<%=gcalEvtTitle%>
					&dates=${eventStartDate}T<%=startTimeHour%><%=gcalEvtStartTimeM%>00/${eventEndDate}T<%=finishTimeHour%><%=gcalEvtFinishTimeM%>00
					&details=<%=gcalEvtDescription%>
					&location=<%=gcalEvtLocation%>
					&ctz=America/St_Johns
					&trp=false
					&sprop=
					&sprop=name:"
					target="_blank" 
					rel="nofollow"
					class="btn btn-xs btn-primary"  
					title="Add to your Google Calendar"><img src="includes/img/gg.png" border=0 height=15> +Google Calendar</a>
				<%}%>	
				
				 <a href="deregisterEvent.html?id=<%=evt.getEventID()%>" title="De-Register from this Event?" class="btn btn-danger btn-xs">De-Register?</a>  




 </td>
                          
			</tr>	      
				      
            
<%        }
        }
%>    
      </table>
   





<div align="center" class="no-print navBottom">
  <a href='#' title='Print this page (pre-formatted)' class="btn btn-primary btn-xs"  onclick="jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:10px;><img width=400 src=includes/img/nlesd-colorlogo.png><br/><br/><b>Professional Development Calendar</b></div><br/>'});"><span class="glyphicon glyphicon-print"></span> Print</a>	                     
  			
 <a onclick="loadingData()" class="no-print btn btn-xs btn-danger" href="viewDistrictCalendar.html">Back to Calendar</a>
 
 </div>

</div></div>

<script>
$(document).ready(function(){
	$(".evntPCount").text(<%=eventRCount%>);	
});

</script>
</body>
</html>