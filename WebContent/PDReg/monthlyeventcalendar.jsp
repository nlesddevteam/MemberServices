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
    
    //For Chart
    	 var eA = 0;
    	 var eC = 0;
    	 var eW = 0;
    	 var eL = 0;
    	 var eP = 0;
    	 var eF1 = 0;
    	 var eF2 = 0;
    	 var eF3 = 0;
    	 var eF4 = 0;
    	 var eF5 = 0;
    	 var eF6 = 0;
    	 var eF7 = 0;
    	 var eF8 = 0;
    	 var eF9 = 0;
    	 var eF10 = 0;
    	 var eF11 = 0;
    	 var eF12 = 0;
    	 var eF13 = 0;
    	 var unk = 0;
        
  </script>
<script src="/MemberServices/PDReg/includes/js/Chart.min.js"></script>


<style>
 .affix { top: 0px; width: 100%;z-index: 999 !important;}
.affix + .container-fluid { padding-top: 110px;}
canvas{

  width:100% !important;
  height:400px !important;

}
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
  <div style="display:inline-block;margin-top:5px;"><a href="#" class='btnRegionView btn btn-xs btn-primary' title="List All Regional Events" onclick="loadingData()" data-region-id='0'><span class="glyphicon glyphicon-home"></span> Home</a></div>
   			
    		
   			<% if(usr.checkPermission("CALENDAR-SCHEDULE")) { %>
           		<div style="display:inline-block;margin-top:5px;"><a href="scheduleEvent.html?passthrough=true" class="btn btn-xs btn-success" title="Schedule Event" onclick="loadingData();"><span class="glyphicon glyphicon-plus"></span> Add Event</a></div>
            	<div style="display:inline-block;margin-top:5px;"><a href="viewUpcomingEvents.html?pid=<%=usr.getPersonnel().getPersonnelID()%>" title="List events that I have scheduled for others to attend." class="btn btn-xs btn-danger" onclick="loadingData();"><span class="glyphicon glyphicon-calendar"></span> My Scheduled Events</a></div>
   			<% } %>   			
 			<div style="display:inline-block;margin-top:5px;"><a href="viewMemberEvents.html" title="List events that I am currently registered for." class="btn btn-xs btn-danger" onclick="loadingData();"><span class="glyphicon glyphicon-tasks"></span> My Registered Events</a></div>
			 <% if(usr.checkPermission("CALENDAR-VIEW-SCHOOL-ENROLLMENT")) { %>			                    
              	<div style="display:inline-block;margin-top:5px;"><a href="viewSchoolRegistrations.html?id=<%=usr.getPersonnel().getSchool().getSchoolID()%>"  title="View School Enrollment" class="btn btn-xs btn-info" onclick="loadingData();"><span class="glyphicon glyphicon-user"></span> School Enrollment</a></div>
            <% } %>
            
            <div style="display:inline-block;margin-top:5px;"><a href='#' title='Print this page (pre-formatted)'  class="btn btn-xs btn-success"  onclick="jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:10px;><img width=400 src=includes/img/nlesd-colorlogo.png><br/><b>Professional Development Calendar</b></div><br/>'});"><span class="glyphicon glyphicon-print"></span> Print</a></div>
            <!-- <div style="display:inline-block;margin-top:5px;"><a href="https://forms.gle/UrYhxVAXRpAWhJGf7" class="btn btn-xs btn-warning" title="Provide Feedback - Will open in a new tab." target="_blank"><span class="glyphicon glyphicon-bullhorn"></span> Feedback</a></div>-->
  		 	<div style="display:inline-block;margin-top:5px;"><a href="/MemberServices/navigate.jsp" class="btn btn-xs btn-danger" title="Back to MS" onclick="loadingData();"><span class="glyphicon glyphicon-eject"></span> Exit</a></div>
 </div>
 
  	 </form>   
  <div class="mainView" style="font-size:11px;color:Black;text-align:center;padding-top:5px;"><b>CALENDAR  / CHART LEGEND (#Events) Percentage of Total</b><br/>
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" id="numFOSb1">&nbsp;FOS 01 (<span id="numFOS1"></span>) <span id="p1"></span>&nbsp;</div>
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" id="numFOSb2">&nbsp;FOS 02 (<span id="numFOS2"></span>) <span id="p2"></span>&nbsp;</div>
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" id="numFOSb3">&nbsp;FOS 03 (<span id="numFOS3"></span>) <span id="p3"></span>&nbsp;</div>
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" id="numFOSb4">&nbsp;FOS 04 (<span id="numFOS4"></span>) <span id="p4"></span>&nbsp;</div>
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" id="numFOSb5">&nbsp;FOS 05 (<span id="numFOS5"></span>) <span id="p5"></span>&nbsp;</div>
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" id="numFOSb6">&nbsp;FOS 06 (<span id="numFOS6"></span>) <span id="p6"></span>&nbsp;</div>
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" id="numFOSb7">&nbsp;FOS 07 (<span id="numFOS7"></span>) <span id="p7"></span>&nbsp;</div><br/>
  
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" id="numFOSb8">&nbsp;FOS 08 (<span id="numFOS8"></span>) <span id="p8"></span>&nbsp;</div>
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" id="numFOSb9">&nbsp;FOS 09 (<span id="numFOS9"></span>) <span id="p9"></span>&nbsp;</div>
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" id="numFOSb10">&nbsp;FOS 10 (<span id="numFOS10"></span>) <span id="p10"></span>&nbsp;</div>
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" id="numFOSb11">&nbsp;FOS 11 (<span id="numFOS11"></span>) <span id="p11"></span>&nbsp;</div>
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" id="numFOSb12">&nbsp;FOS 12 (<span id="numFOS12"></span>) <span id="p12"></span>&nbsp;</div>
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" id="numFOSb13">&nbsp;FOS 13 (<span id="numFOS13"></span>) <span id="p13"></span>&nbsp;</div>
  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;" id="numUnkb">&nbsp;OTHER (<span id="numUnk"></span>) <span id="pU"></span>&nbsp;</div><br/>
 <%if (curY <= 2022) { %>  
	  <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;"><span class="region1">&nbsp; AVALON (<%=totalAvalon%>) <span id="pA"></span>&nbsp;</span></div>
	   <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;"><span class="region2">&nbsp; CENTRAL (<%=totalCentral%>) <span id="pC"></span>&nbsp;</span></div>
	   <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;"><span class="region3">&nbsp; WESTERN (<%=totalWestern%>) <span id="pW"></span>&nbsp;</span></div>
	   <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;"><span class="region4">&nbsp; LABRADOR (<%=totalLabrador%>) <span id="pL"></span>&nbsp;</span></div>
<%} %>	   
	   <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;"><span class="region5">&nbsp; PROVINCIAL (<%=totalProvincial%>) <span id="pP"></span>&nbsp;</span></div>
	   <div style="display:inline-block;white-space: nowrap;padding:1px;margin:3px;"><span style="color:Grey;background-color:#f2f2f2;">&nbsp; PAST EVENT &nbsp;</span></div>	 	
  </div>


<!--  ONLY SHOW CHART IN NORMAL VIEW -->

<div align='center' class="mainView" style='font-size:14pt;font-weight:bold;color:#33cc33;padding-bottom:15px;padding-top:10px;'>

Family of Schools/Regional Event Comparisons<br/>	
	  <canvas id="eventsByFamilyChart"></canvas>
	
	   
</div>
<div style="clear:both;"></div>
<br/>&nbsp;<br/>
<div style="padding-bottom:50px;">&nbsp;</div>
<script>
$(document).ready(function(){
  $('[data-toggle="popover"]').popover();  
  
  //$('html').css({'background': 'url(includes/img/season/${seasonImage}) no-repeat center center fixed','-webkit-background-size':'cover','-moz-background-size':'cover','-o-background-size':'cover','background-size':'cover'});
  
});

$("#numFOS1").text(eF1);
$("#numFOSb1").css("color","rgba(2, 134, 209,1)").css("background-color","rgba(2, 134, 209,0.2)");
$("#numFOS2").text(eF2);
$("#numFOSb2").css("color","rgba(148, 39, 97,1)").css("background-color","rgba(148, 39, 97,0.2)");
$("#numFOS3").text(eF3);
$("#numFOSb3").css("color","rgba(169, 205, 130,1)").css("background-color","rgba(169, 205, 130,0.2)");
$("#numFOS4").text(eF4);
$("#numFOSb4").css("color","rgba(15, 157, 87,1)").css("background-color","rgba(15, 157, 87,0.2)");
$("#numFOS5").text(eF5);
$("#numFOSb5").css("color","rgba(1, 87, 155,1)").css("background-color","rgba(1, 87, 155,0.2)");
$("#numFOS6").text(eF6);
$("#numFOSb6").css("color","rgba(57, 73, 171,1)").css("background-color","rgba(57, 73, 171,0.2)");
$("#numFOS7").text(eF7);
$("#numFOSb7").css("color","rgba(32, 126, 75,1)").css("background-color","rgba(32, 126, 75,0.2)");
$("#numFOS8").text(eF8);
$("#numFOSb8").css("color","rgba(133, 123, 29,1)").css("background-color","rgba(133, 123, 29,0.2)");
$("#numFOS9").text(eF9);
$("#numFOSb9").css("color","rgba(165, 39, 20,1)").css("background-color","rgba(165, 39, 20,0.2)");
$("#numFOS10").text(eF10);
$("#numFOSb10").css("color","rgba(103, 58, 183,1)").css("background-color","rgba(103, 58, 183,0.2)");
$("#numFOS11").text(eF11);
$("#numFOSb11").css("color","rgba(249, 171, 45,1)").css("background-color","rgba(249, 171, 45,0.2)");
$("#numFOS12").text(eF12);
$("#numFOSb12").css("color","rgba(105, 83, 78,1)").css("background-color","rgba(105, 83, 78,0.2)");
$("#numFOS13").text(eF13);
$("#numFOSb13").css("color","rgba(175, 180, 43,1)").css("background-color","rgba(175, 180, 43,0.2)");
$("#numUnk").text(unk);
$("#numUnkb").css("color","Black").css("background-color","Silver");
var eFTotal = eF1+eF2+eF3+eF4+eF5+eF6+eF7+eF8+eF9+eF10+eF11+eF12+eF13+unk+<%=totalAll%>;
 //Get percentages (rounded)
var percentFOS1 = Math.round(eF1/eFTotal*100);
var percentFOS2 = Math.round(eF2/eFTotal*100);
var percentFOS3 = Math.round(eF3/eFTotal*100);
var percentFOS4 = Math.round(eF4/eFTotal*100);
var percentFOS5 = Math.round(eF5/eFTotal*100);
var percentFOS6 = Math.round(eF6/eFTotal*100);
var percentFOS7 = Math.round(eF7/eFTotal*100);
var percentFOS8 = Math.round(eF8/eFTotal*100);
var percentFOS9 = Math.round(eF9/eFTotal*100);
var percentFOS10 = Math.round(eF10/eFTotal*100);
var percentFOS11 = Math.round(eF11/eFTotal*100);
var percentFOS12 = Math.round(eF12/eFTotal*100);
var percentFOS13 = Math.round(eF13/eFTotal*100);
var percentUnk = Math.round(unk/eFTotal*100);
var percentAvalon = Math.round(<%=totalAvalon%>/eFTotal*100);
var percentCentral = Math.round(<%=totalCentral%>/eFTotal*100);
var percentWestern = Math.round(<%=totalWestern%>/eFTotal*100);
var percentLabrador = Math.round(<%=totalLabrador%>/eFTotal*100);
var percentProvincial = Math.round(<%=totalProvincial%>/eFTotal*100);
//Write to calendar legend bottom
$('#p1').text(percentFOS1+"%");
$('#p2').text(percentFOS2+"%");
$('#p3').text(percentFOS3+"%");
$('#p4').text(percentFOS4+"%");
$('#p5').text(percentFOS5+"%");
$('#p6').text(percentFOS6+"%");
$('#p7').text(percentFOS7+"%");
$('#p8').text(percentFOS8+"%");
$('#p9').text(percentFOS9+"%");
$('#p10').text(percentFOS10+"%");
$('#p11').text(percentFOS11+"%");
$('#p12').text(percentFOS12+"%");
$('#p13').text(percentFOS13+"%");
$('#pU').text(percentUnk+"%");
$('#pA').text(percentAvalon+"%");
$('#pC').text(percentCentral+"%");
$('#pW').text(percentWestern+"%");
$('#pL').text(percentLabrador+"%");
$('#pP').text(percentProvincial+"%");    

  
   
//Chart Families
var ctx = document.getElementById('eventsByFamilyChart').getContext('2d');

if (<%=curY%> <=2022 ) {

var eventsByRegionChart = new Chart(ctx, {
	type: 'bar',
	  data: {
		  labels: ['FOS 01','FOS 02','FOS 03','FOS 04','FOS 05','FOS 06','FOS 07','FOS 08','FOS 09','FOS 10','FOS 11','FOS 12','FOS 13','AVALON','CENTRAL','WESTERN','LABRADOR','PROVINCIAL','OTHER'],
	    datasets: [{
	    	backgroundColor: ['rgba(2, 134, 209,0.2)',
	    		'rgba(148, 39, 97,0.2)',
	    		'rgba(169, 205, 130,0.2)',
	    		'rgba(15, 157, 87,0.2)',
	    		'rgba(1, 87, 155,0.2)',
	    		'rgba(57, 73, 171,0.2)',
	    		'rgba(32, 126, 75,0.2)',
	    		'rgba(133, 123, 29,0.2)',
	    		'rgba(165, 39, 20,0.2)',
	    		'rgba(103, 58, 183,0.2)',
	    		'rgba(249, 171, 45,0.2)',
	    		'rgba(105, 83, 78,0.2)',
	    		'rgba(175, 180, 43,0.2)',	    		
	    		'rgba(220, 20, 60, 0.3)',
	    		'rgba(51, 153, 51, 0.3)',
	    		'rgba(255, 153, 0, 0.3)',
	    		'rgba(0, 102, 255, 0.3)',
	    		'rgba(128, 0, 128, 0.3)',
	    		'#C0C0C0',],
	    	data: [eF1,eF2,eF3,eF4,eF5,eF6,eF7,eF8,eF9,eF10,eF11,eF12,eF13,<%=totalAvalon%>, <%=totalCentral%>, <%=totalWestern%>, <%=totalLabrador%>,<%=totalProvincial%>,unk]
	    }]
	  },	  
	  options: {		  	  
	      title: {
	         display: true,
	         fontSize: 12,
	         text: 'Breakdown for this Month'
	     },
	     legend: {
	         display: false,
	         fontSize: 12,
	         position: 'top',

	     },
	     responsive: true,
	     maintainAspectRatio: false,
	 }
 
	  
	});   
} else {
	
	var eventsByRegionChart = new Chart(ctx, {
		type: 'bar',
		  data: {
			  labels: ['FOS 01','FOS 02','FOS 03','FOS 04','FOS 05','FOS 06','FOS 07','FOS 08','FOS 09','FOS 10','FOS 11','FOS 12','FOS 13','PROVINCIAL','OTHER'],
		    datasets: [{
		    	backgroundColor: ['rgba(2, 134, 209,0.2)',
		    		'rgba(148, 39, 97,0.2)',
		    		'rgba(169, 205, 130,0.2)',
		    		'rgba(15, 157, 87,0.2)',
		    		'rgba(1, 87, 155,0.2)',
		    		'rgba(57, 73, 171,0.2)',
		    		'rgba(32, 126, 75,0.2)',
		    		'rgba(133, 123, 29,0.2)',
		    		'rgba(165, 39, 20,0.2)',
		    		'rgba(103, 58, 183,0.2)',
		    		'rgba(249, 171, 45,0.2)',
		    		'rgba(105, 83, 78,0.2)',
		    		'rgba(175, 180, 43,0.2)',		    			    		
		    		'rgba(128, 0, 128, 0.3)',
		    		'#C0C0C0'],
		    	data: [eF1,eF2,eF3,eF4,eF5,eF6,eF7,eF8,eF9,eF10,eF11,eF12,eF13,<%=totalProvincial%>,unk]
		    }]
		  },	  
		  options: {		  	  
		      title: {
		         display: true,
		         fontSize: 12,
		         text: 'Breakdown for this Month'
		     },
		     legend: {
		         display: false,
		         fontSize: 12,
		         position: 'top',

		     },
		     responsive: true,
		     maintainAspectRatio: false,
		 }
	 
		  
		}); 
	
	
	
}
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