<%@ page  language="java" 
          session="true" 
          import="java.util.*, 
          				java.text.*, 
          				com.awsd.pdreg.*,          				
          				com.awsd.security.*,
          				com.nlesd.school.bean.*,
          				com.nlesd.school.service.*,
          				org.apache.commons.lang.*"
          isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="awsd" %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<esd:SecurityCheck permissions="CALENDAR-VIEW" />

<%
  // months as they appear in the calendar's title
  final String ARR_MONTHS[] = {"January","February","March","April","May","June","July","August","September","October","November","December"};
  // week day titles as they appear on the calendar
  final String ARR_WEEKDAYS[] = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
  final String ARR_WEEKDAYSM[] = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
  // path to the directory where calendar images are stored. trailing slash req.
  final String STR_ICONPATH = "includes/img/";
  String prevM = (String) request.getAttribute("PrevMonth");
  String nextM = (String) request.getAttribute("NextMonth");
  String prevY = (String) request.getAttribute("PrevYear");
  String nextY = (String) request.getAttribute("NextYear");
  int curM = ((Integer) request.getAttribute("CurrentMonth")).intValue();
  int curY = ((Integer) request.getAttribute("CurrentYear")).intValue();
  //int curD;
  int firstday;
  int day;
  int dayofweek;
  boolean firstweek;  
  int numEventsThisMonth=0;  
  Calendar cur = null;
  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
  User usr = (User) session.getAttribute("usr");
  UserPermissions permissions = null;
  cur =  Calendar.getInstance();
  //curD = cur.get(Calendar.DATE);
  cur.set(curY, curM, 1);
  firstday = cur.get(Calendar.DAY_OF_WEEK);
  firstweek = true;  
  MonthlyCalendar month = (MonthlyCalendar) request.getAttribute("MonthlyEvents");
  MonthlyCalendar monthAvalon = (MonthlyCalendar) request.getAttribute("avalonMonthlyEvents");
  MonthlyCalendar monthCentral = (MonthlyCalendar) request.getAttribute("centralMonthlyEvents");
  MonthlyCalendar monthWestern = (MonthlyCalendar) request.getAttribute("westernMonthlyEvents");
  MonthlyCalendar monthLabrador = (MonthlyCalendar) request.getAttribute("labradorMonthlyEvents");
  MonthlyCalendar monthProvincial = (MonthlyCalendar) request.getAttribute("provincialMonthlyEvents");
  int total = month.totalEvents();
  int totalAvalon = monthAvalon.totalEvents();
  int totalCentral = monthCentral.totalEvents();
  int totalWestern = monthWestern.totalEvents();
  int totalLabrador = monthLabrador.totalEvents();
  int totalProvincial = monthProvincial.totalEvents();  
  int totalAll = totalAvalon+totalCentral+totalWestern+totalLabrador+totalProvincial;     
%>

<html>

<head>
  <title>PD Event Calendar</title>
  <script type="text/javascript">    
  $("#loadingSpinner").css("display","none");
    $(function(){
    	$('.btnRegionView').click(function(){
    		self.location.href = '/MemberServices/PDReg/viewMonthlyCalendar.html?dt=<%= month.getMonth() %>&region-id=' + $(this).attr('data-region-id');	
    	});    	
    	$('#regionized-view').buttonset();
    	var region_id = <%= month.getZone() != null ? month.getZone().getZoneId() : "0" %>;
   		$('.btnRegionView').each(function(){
   			if($(this).attr('data-region-id') == region_id){
   				$(this).addClass("topnavSelected").addClass("borderline");   				
   			} 
   		});    	
    });
  </script>
<script src="/MemberServices/PDReg/includes/js/Chart.min.js"></script>


<style>
 .affix { top: 0px; width: 100%;z-index: 999 !important;}
.affix + .container-fluid { padding-top: 110px;}

@media only screen and (max-width: 850px) {		
		.affix + .container-fluid {padding-top: 105px; }
	
}
</style>
</head>

<body>
       				
<form name="month_selector" action="viewMonthlyCalendar.html" method="POST">
			<% if(month.getZone() != null) { %>
				<input type='hidden' name='region-id' value='<%= month.getZone().getZoneId() %>' />
			<% } %>  	       	
 
  	<%
  	//Change calendar header color based on seasons similar to foundation calendar
  	if(ARR_MONTHS[curM] == "January" || ARR_MONTHS[curM] == "February") { %>
  		<c:set var="season" value="winter"/> 
  	    <c:set var="seasonIconLeft" value="winter.png"/>
   		<c:set var="seasonIconRight" value="winter.png"/>
   		<c:set var="seasonImage" value="winter.jpg"/>
   	<%} %>
    <%if(ARR_MONTHS[curM] == "March") { %>   
   		<c:set var="season" value="springGradient"/>
   		<c:set var="seasonIconLeft" value="winter.png"/>
   		<c:set var="seasonIconRight" value="spring.png"/>
   		<c:set var="seasonImage" value="winter.jpg"/>
   		  
    <%} %>    
    <%if(ARR_MONTHS[curM] == "April" || ARR_MONTHS[curM] == "May") { %>
  		<c:set var="season" value="spring"/> 
  		<c:set var="seasonIconLeft" value="spring.png"/>
   		<c:set var="seasonIconRight" value="spring.png"/>
   		<c:set var="seasonImage" value="spring.jpg"/>
   	<%} %>   
   <%if(ARR_MONTHS[curM] == "June") { %>   
   		<c:set var="season" value="summerGradient"/>
   		<c:set var="seasonIconLeft" value="spring.png"/>
   		<c:set var="seasonIconRight" value="summer.png"/>
   		<c:set var="seasonImage" value="default.jpg"/>
   		 
    <%} %> 
    <%if(ARR_MONTHS[curM] == "July" || ARR_MONTHS[curM] == "August") { %>
  		<c:set var="season" value="summer"/>
  		<c:set var="seasonIconLeft" value="summer.png"/>
   		<c:set var="seasonIconRight" value="summer.png"/>
   		<c:set var="seasonImage" value="summer.jpg"/>  
  		
   	<%} %>
    <%if(ARR_MONTHS[curM] == "September") { %>   
   		<c:set var="season" value="fallGradient"/>
   		<c:set var="seasonIconLeft" value="summer.png"/>
   		<c:set var="seasonIconRight" value="fall.png"/>
   		<c:set var="seasonImage" value="september.jpg"/>
   		  
    <%} %>
    <%if(ARR_MONTHS[curM] == "October" || ARR_MONTHS[curM] == "November") { %>
  		<c:set var="season" value="fall"/> 
  		<c:set var="seasonIconLeft" value="fall.png"/>
   		<c:set var="seasonIconRight" value="fall.png"/> 
   		<c:set var="seasonImage" value="fall.jpg"/>
  		 
   	<%} %>    
    <%if(ARR_MONTHS[curM] == "December") { %>   
   		<c:set var="season" value="winterGradient"/> 
   		<c:set var="seasonIconLeft" value="fall.png"/>
   		<c:set var="seasonIconRight" value="winter.png"/>
   		<c:set var="seasonImage" value="fall.jpg"/>
   		
    <%} %>
 
 
<div id="affixedTop" class="container-fluid" data-spy="affix" data-offset-top="0">
		
 		<table id="affixedTable" class="table table-bordered table-condensed ${season}" align="center" style="font-size:11px;width:100%;min-width:350px;">              
              <tr>
                <td colspan=7>
                <div style="float:left;width:20%;">
                  <a class="btn btn-default btn-sm yearNav" title="Step a Year Back" href="viewMonthlyCalendar.html?dt=<%=prevY%><%= month.getZone() != null ? "&region-id=" + month.getZone().getZoneId() : "" %>"><span class="glyphicon glyphicon-fast-backward"></span></a>
                  <a class="btn btn-default btn-sm" title="Step a Month Back" href="viewMonthlyCalendar.html?dt=<%=prevM%><%= month.getZone() != null ? "&region-id=" + month.getZone().getZoneId() : "" %>"><span class="glyphicon glyphicon-backward"></span></a>
                </div>
                <div class="topTitle" style="float:left;width:60%;text-align:center;"><img src="includes/img/icon/${seasonIconLeft}" class="topTitleImgLeft"  border="0"><span style="font-weight:bold;text-transform:uppercase;"><%=ARR_MONTHS[curM]%>&nbsp;<%=curY%></span> <img src="includes/img/icon/${seasonIconRight}" class="topTitleImgRight" border="0">
               </div>
                <div style="float:right;width:20%;text-align:right;"> 
                  <a class="btn btn-default btn-sm" title="Step a Month Ahead" href="viewMonthlyCalendar.html?dt=<%=nextM%><%= month.getZone() != null ? "&region-id=" + month.getZone().getZoneId() : "" %>"><span class="glyphicon glyphicon-forward"></span></a>
                  <a class="btn btn-default btn-sm yearNav" title="Step a Year Ahead" href="viewMonthlyCalendar.html?dt=<%=nextY%><%= month.getZone() != null ? "&region-id=" + month.getZone().getZoneId() : "" %>"><span class="glyphicon glyphicon-fast-forward"></span></a>
                </div>
                </td>
              </tr>             
              <%if (total >0) { %>
              <tr>
                <td colspan=7><div style="text-align:center;"><%=total %> EVENTS</div>
                </td>
              </tr> 
 				<%}%>
 				 
 				
 				
        <tr>
		<% for (int n=0; n<7; n++)  { %>          
          <td align="center" width="14%">              
                <span class="mainView" style="text-transform:uppercase;font-weight:bold;"><%=ARR_WEEKDAYS[n]%></span>
                 <span class="mobileView" style="text-transform:uppercase;font-weight:bold;"><%=ARR_WEEKDAYSM[n]%></span>
            </td>
		<% } %>      
		</tr>
		</table>
</div>

<div class="container-fluid">           
 <table id="bodyTable" class="table table-bordered table-condensed ${season}" align="center" style="margin-top:-20px;font-size:11px;width:100%;min-width:350px;"> 
             
	<%  
	while (cur.get(Calendar.MONTH) == curM)
        { %>     
     
     <tr>
     
		
	<%  for (int n_current_wday=0; (n_current_wday<7); n_current_wday++) 
          {
            day = cur.get(Calendar.DATE);
            dayofweek = cur.get(Calendar.DAY_OF_WEEK);

            if((n_current_wday + 1) == firstday)
            {
            	firstweek = false;
            }%>    			
			<%  if (!firstweek && (cur.get(Calendar.MONTH) == curM))
              { %>
	        
			
			
			<% if(dayofweek==1 || dayofweek ==7) { %>
		<td bgcolor="#F8F8FF" align="right" valign="top" style="width:14%;min-width:45px;">
		<%}else { %>
		<td bgcolor="#FFFFFF" align="right" valign="top" style="width:14%;min-width:45px;">
		<%} %>		
	
		<awsd:DailyCalendar 
		date="<%=sdf.format(cur.getTime())%>" 
		printable="false" 
		uid="<%=usr.getPersonnel().getPersonnelID()%>"		
		zone="<%= month.getZone() %>" />
		
		</td>		
		
		<% } else { %>          	
				<td bgcolor="#F8F8FF" align="right" valign="top" style="width:14%;min-width:45px;">&nbsp;</td>
		<% }%>         
<%          if(!firstweek)
            {
              cur.add(Calendar.DATE, 1);
            }
			} %>
</tr>
<%}%>



           
<tr class="${season}" style="color:Black;">         
          <td colspan=7 align="center">
          <select name="dt" size="1" style="width:100%;max-width:350px;line-height:10px;" class="form-control input-sm" onchange="if(document.month_selector.dt.value != -1) document.month_selector.submit();">
                <option value='-1'>SELECT A MONTH TO DISPLAY</option>
			<% sdf = new SimpleDateFormat("yyyyMM");
                cur.set(curY, curM, 1);
				%>              
				<option value='<%=sdf.format(cur.getTime())%>'>CURRENT MONTH</option>
			<% cur.add(Calendar.MONTH, -13);
                for(int i=0; i < 25; i++)
                {
                	cur.add(Calendar.MONTH, 1);%>                
                <option value='<%=sdf.format(cur.getTime())%>'><%=ARR_MONTHS[cur.get(Calendar.MONTH)] + " " + cur.get(Calendar.YEAR)%></option>
			<% }%>            
</select>
          </td>
        </tr>  
        
        
      </table>
 </div>  
 
 
 
 <div align="center" class="no-print navBottom">
  <div style="display:inline-block;margin-top:5px;"><a href="#" class='btnRegionView btn btn-xs btn-primary' title="List All Regional Events" onclick="loadingData()" data-region-id='0'>All Events</a></div>
   			<% for(SchoolZoneBean sz : SchoolZoneService.getSchoolZoneBeans()){ %> 
   			    <div style="display:inline-block;margin-top:5px;"><a href="#" class='btnRegionView btn btn-xs btn-primary' onclick="loadingData()" data-region-id="<%= sz.getZoneId() %>" title="Show <%= StringUtils.capitalize(sz.getZoneName())%> Regional Events"> <%= StringUtils.capitalize(sz.getZoneName())%></a></div>
   			<%}%> 
   			
   			<% if(usr.checkPermission("CALENDAR-SCHEDULE")) { %>
           		<div style="display:inline-block;margin-top:5px;"><a href="scheduleEvent.html?passthrough=true" class="btn btn-xs btn-success" title="Schedule Event" onclick="loadingData();"><span class="glyphicon glyphicon-plus"></span> Add Event</a></div>
            	<div style="display:inline-block;margin-top:5px;"><a href="viewUpcomingEvents.html?pid=<%=usr.getPersonnel().getPersonnelID()%>" title="List events that I have scheduled for others to attend." class="btn btn-xs btn-danger" onclick="loadingData();"><span class="glyphicon glyphicon-calendar"></span> My Scheduled Events</a></div>
   			<% } %>   			
 			<div style="display:inline-block;margin-top:5px;"><a href="viewMemberEvents.html" title="List events that I am currently registered for." class="btn btn-xs btn-danger" onclick="loadingData();"><span class="glyphicon glyphicon-tasks"></span> My Registered Events</a></div>
			 <% if(usr.checkPermission("CALENDAR-VIEW-SCHOOL-ENROLLMENT")) { %>			                    
              	<div style="display:inline-block;margin-top:5px;"><a href="viewSchoolRegistrations.html?id=<%=usr.getPersonnel().getSchool().getSchoolID()%>"  title="View School Enrollment" class="btn btn-xs btn-info" onclick="loadingData();"><span class="glyphicon glyphicon-user"></span> School Enrollment</a></div>
            <% } %>
            
            <div style="display:inline-block;margin-top:5px;"><a href='#' title='Print this page (pre-formatted)'  class="btn btn-xs btn-success"  onclick="jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:10px;><img width=400 src=includes/img/nlesd-colorlogo.png><br/><b>Professional Development Calendar</b></div><br/>'});"><span class="glyphicon glyphicon-print"></span> Print</a></div>
            <div style="display:inline-block;margin-top:5px;"><a href="https://forms.gle/UrYhxVAXRpAWhJGf7" class="btn btn-xs btn-warning" title="Provide Feedback - Will open in a new tab." target="_blank"><span class="glyphicon glyphicon-bullhorn"></span> Feedback</a></div>
  		 	<div style="display:inline-block;margin-top:5px;"><a href="/MemberServices/navigate.jsp" class="btn btn-xs btn-danger" title="Back to MS" onclick="loadingData();"><span class="glyphicon glyphicon-eject"></span> Exit</a></div>
 </div>
 
  	 </form>   
  <div class="mainView" style="font-size:11px;color:Black;text-align:center;padding-top:5px;"><b>CALENDAR  / CHART LEGEND (#Events) Percentage of Total</b><br/>
	  <div style="display:inline-block;white-space: nowrap;"><span class="region1a">&nbsp; AVALON (<%=totalAvalon%>) <span id="pA"></span>&nbsp;</span></div>
	   <div style="display:inline-block;white-space: nowrap;"><span class="region2a">&nbsp; CENTRAL (<%=totalCentral%>) <span id="pC"></span>&nbsp;</span></div>
	   <div style="display:inline-block;white-space: nowrap;"><span class="region3a">&nbsp; WESTERN (<%=totalWestern%>) <span id="pW"></span>&nbsp;</span></div>
	   <div style="display:inline-block;white-space: nowrap;"><span class="region4a">&nbsp; LABRADOR (<%=totalLabrador%>) <span id="pL"></span>&nbsp;</span></div>
	   <div style="display:inline-block;white-space: nowrap;"><span class="region5a">&nbsp; PROVINCIAL (<%=totalProvincial%>) <span id="pP"></span>&nbsp;</span></div>
	   <div style="display:inline-block;white-space: nowrap;"><span class="pastEvt">&nbsp; PAST EVENT</span></div>	 	
  </div>


<!--  ONLY SHOW CHART IN NORMAL VIEW -->

<div align='center' class="mainView" style='font-size:14pt;font-weight:bold;color:#33cc33;padding-bottom:15px;padding-top:10px;'>
	  	Regional Event Comparisons<br/>	
	    <canvas id="eventsByRegionChart" height="300"></canvas>
</div>
<script>
$(document).ready(function(){
  $('[data-toggle="popover"]').popover();  
  
  //$('html').css({'background': 'url(includes/img/season/${seasonImage}) no-repeat center center fixed','-webkit-background-size':'cover','-moz-background-size':'cover','-o-background-size':'cover','background-size':'cover'});
  
});
</script>


<script>
 //Get percentages (rounded)
var percentAvalon = Math.round(<%=totalAvalon%>/<%=totalAll%>*100);
var percentCentral = Math.round(<%=totalCentral%>/<%=totalAll%>*100);
var percentWestern = Math.round(<%=totalWestern%>/<%=totalAll%>*100);
var percentLabrador = Math.round(<%=totalLabrador%>/<%=totalAll%>*100);
var percentProvincial = Math.round(<%=totalProvincial%>/<%=totalAll%>*100);
//Write to calendar legend bottom
$('#pA').text(percentAvalon+"%");
$('#pC').text(percentCentral+"%");
$('#pW').text(percentWestern+"%");
$('#pL').text(percentLabrador+"%");
$('#pP').text(percentProvincial+"%");     
  
 //Pie Chart
var ctx = document.getElementById('eventsByRegionChart').getContext('2d');
var eventsByRegionChart = new Chart(ctx, {
	type: 'pie',
	  data: {
		  labels: ['Avalon Events','Central Events','Western Events','Labrador Events','Provincial Events'],
	    datasets: [{
	    	backgroundColor: ['rgba(220, 20, 60, 0.3)','rgb(51, 153, 51, 0.3)','rgba(255, 153, 0, 0.3)','rgba(0, 102, 255, 0.3)','rgba(128, 0, 128, 0.3)'],
	    	data: [<%=totalAvalon%>, <%=totalCentral%>, <%=totalWestern%>, <%=totalLabrador%>,<%=totalProvincial%> ]
	    }]
	  },	  
	  options: {		  	  
	      title: {
	         display: true,
	         fontSize: 14,
	         text: 'Breakdown for this Month'
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


<div id="welcomeMessage" class="modal fade mainView" style="z-index:99999;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header" style="text-align:center;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title"><img src="includes/img/pdcalheader-md.png" border=0 style="max-width:80%;"/></h4>
            </div>
            <div class="modal-body" style="font-size:11px;">
				Welcome to the new Professional Development Calendar where District Educational Staff can schedule PD events and register for various PD Events held throughout the District. 
				<ul>
				<li>Events are listed colour coded by region on the calendar. By default All Events province wide are displayed. 
				<li>To limit the view to a particular region, click on one of the regional links. 
				<li>Hover the mouse over an event to have a quick view of the event details. 
				<li>To open an event to register, simply click on the event. 
				</ul>
				You can also add an event to your NLESD GoogleCalendar.
               <div style="text-align:center;"><img class="topLogoImg" src="includes/img/welcomepd.png" border=0 style="padding-top:5px;"/></div>
            </div>
             <div class="modal-footer">
        <button type="button" class="btn btn-sm btn-success" data-dismiss="modal">Continue</button>       
      </div>
        </div>
    </div>
</div>

<script>
	$(document).ready(function(){
		//Check for apple device and format affix accordingly
		 if (navigator.userAgent.match(/(iPod|iPhone|iPad)/)) {	    	
		    	$("#bodyTable").css("margin-top","-15px");
			} else {
		              // Buy Android         
		        }
		
		//Give NL cookie monster a cookie so modal welocme only displays ONCE.	Seems like google is demanding the SameSite and secure options now too...
		var displayWelcomeModalCookie = $.cookie("displayWelcomeModalCookie");
		$.cookie("displayWelcomeModalCookie", "hideWelcome", { 
			expires: 365, 
			path: "/MemberServices/PDReg/",
			SameSite: "Lax", 
			secure  : true 	
		});
		
	if (displayWelcomeModalCookie=="hideWelcome") {
		$("#welcomeMessage").modal('hide').css("display","none");
		//$("#welcomeMessage").modal('show'); //For testing
	}	else {		
		$("#welcomeMessage").modal('show');
	}	
	
	 
	
	});
</script>



</body>
</html>