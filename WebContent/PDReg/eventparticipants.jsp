<%@ page  language="java" 
          session="true" 
          import="java.util.*, 
            java.text.*, 
            com.awsd.pdreg.*,com.awsd.security.*,com.awsd.personnel.*,com.awsd.school.*,
            com.awsd.common.Utils"
          isThreadSafe="false"%>

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

  int dayofweek;
  String pageTitle;
  String bgcolor = "";
  String txtcolor = "";
  String regionName = "";
  Calendar cur = null;
  User usr = null;
  UserPermissions permissions = null;
  RegisteredPersonnelCollection users = null;
  Personnel p = null;
  Iterator iter = null;
  Date now = null;
  Date evtdt = null;
  Date evtenddt = null;
  Event evt = null;
  School s = null;
  boolean other;
  String status = "";
  SimpleDateFormat sdf = null;

  int sid = -1;
  int numEmpRegistered = 0;
  int evtMaxPart = 0;
  
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    permissions = usr.getUserPermissions();
    if(!(permissions.containsKey("CALENDAR-VIEW") 
        && (permissions.containsKey("CALENDAR-SCHEDULE")
            || permissions.containsKey("CALENDAR-VIEW-PARTICIPANTS"))))
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
  
  users = (RegisteredPersonnelCollection) request.getAttribute("RegisteredPersonnel");
  iter = users.iterator();
  
  cur =  Calendar.getInstance();
  now = cur.getTime();
  
  dayofweek = cur.get(Calendar.DAY_OF_WEEK);

  evt = users.getEvent();
  evtdt = evt.getEventDate();
  evtenddt = evt.getEventEndDate();

  other = false;

  if((Utils.compareCurrentDate(evt.getEventDate())==0)||
    (((evt.getEventEndDate()!= null)&&(Utils.compareCurrentDate(evt.getEventDate())==-1)&&(Utils.compareCurrentDate(evt.getEventEndDate())>=0)) ))
    status = "ACTIVE";
  else if(Utils.compareCurrentDate(evt.getEventDate())==-1)
    status = "PAST";
  else
    status = "";
  
  sdf = new SimpleDateFormat("MM/dd/yyyy");
  
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
<script language="Javascript"> 
function confirm_send() {
		return confirm('Are you ready to send this email?');
		
	}

	$(function() {

		$("#calEventsTable").DataTable({
			"order" : [ [ 0, "asc" ] ],
			"lengthMenu" : [ [ -1 ], [ "All" ] ],
			"bPaginate": false,
		    "bLengthChange": false
		});
	});


 
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
  
  
  @media print {  
  table {font-size:10px;}
  
  }
  
  </style>
  <script src="/MemberServices/PDReg/includes/js/Chart.min.js"></script>
</head>

<body>

<div class="container-fluid no-print topGreenTitleArea" data-spy="affix" data-offset-top="0" style="position:fixed;width:100%;background-color:#008B8B;color:White;text-align:center;font-weight:bold;padding:5px;">                      
 Participants of:<br/><span style="color:Yellow;"><%=evt.getEventName()%></span>
</div>
<div class="registerEventDisplay" style="padding-top:50px;font-size:11px;">
 <div style="margin-left:5px;margin-right:5px;">
     <div align="center" class="no-print"><a href="viewDistrictCalendar.html"><img class="topLogoImg" src="includes/img/pdcalheader.png" border=0 style="padding-bottom:10px;"/></a></div>
  				
    <table class="table table-condensed" style="font-size:11px;">	


     <tr>
                <td class="tableTitle" style="background-color:<%=bgcolor%>;color:<%=txtcolor%> border-top:1px solid <%=txtcolor%>;" colspan=1>Region:</td>
                <td class="tableResult" colspan=3 style="color:<%=txtcolor%>border-top:1px solid <%=txtcolor%>"><%=regionName%></td>
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
                <td class="tableResult" colspan=3 style="text-transform:Capitalize;"><%=evt.getEventLocation() + ((evt.getEventSchoolZone() != null) ? " - " + evt.getEventSchoolZone().getZoneName() + " Region" : "") %>
                <% if(evt.isCloseOutDaySession() || evt.isPDOpportunity()) { %>
              	 	<br/>(Hosted by: <span style="text-transform:Capitalize;"><%=evt.getScheduler().getFullNameReverse()%></span>) 
                <% } else {%>
               	<br/>(N/A)
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
            

<% if(evt.getEventMaximumParticipants()>0) {
	//Max should never be less than 10 default.
	if (evt.getEventMaximumParticipants()<10) {
		evtMaxPart = 10;
	} else {
		evtMaxPart = evt.getEventMaximumParticipants();
	}
	
	
} else {
	if (evt.getRegistrationCount() >0 ){
		if (evt.getRegistrationCount() <= 10){
			evtMaxPart = 10;
		} else if (evt.getRegistrationCount() <= 25 && evt.getRegistrationCount() >10){
			evtMaxPart = 25;			
		} else if (evt.getRegistrationCount() <= 50 && evt.getRegistrationCount() >25){
			evtMaxPart = 50;
		} else if (evt.getRegistrationCount() <= 100 && evt.getRegistrationCount() >50){
			evtMaxPart = 100;
		} else if (evt.getRegistrationCount() <= 150 && evt.getRegistrationCount() >100){
			evtMaxPart = 150;
		} else if (evt.getRegistrationCount() <= 200 && evt.getRegistrationCount() >150){
			evtMaxPart = 200;
		} else {
			evtMaxPart = 250;
		}
			
		//Add color green for less than 50%, blue for less than 20%, less than 10% pink, warning about low registration
		//orange for 50-85%, and red for 85-100% (Revers this. lower red, higher green) Use bootsrap colors match warnings.
				
	} else {
		evtMaxPart = 10;
	}
		
}

%>

<% if(request.getAttribute("msgDREG") != null) { %>
<div class="alert alert-success msg-dereg" style="font-size:12px;text-align:center;display:none;"><%= request.getAttribute("msgDREG") %></div>
<script>$(".msg-dereg").css("display","block").delay(5000).fadeOut();</script>
<%}%>

<div style="font-size:14px;padding-bottom:5px;font-weight:bold;color:<%=txtcolor%>">REGISTERED PERSONNEL FOR THIS EVENT</div>
<span style="font-size:11px;">Registrants are listed sorted by name. You can click on the column header to sort by that column. You can DE-REGISTER a participant</span><br/><br/>
<% if(!iter.hasNext()) {%>       
<div class="alert alert-danger" style="font-size:11px;text-align:center;">NOTICE: Currently No Registered Personnel for this event.</div>
<%  } else { %>        

<div  style="height:100px;">
<canvas id="registeredParticipantsChart" style="width:100%;height:100%;"></canvas>
</div>
<div class="partMessage alert alert-info" style="font-size:11px;text-align:center;"></div>

<table class="table table-striped table-condensed" id="calEventsTable" style="font-size:11px;">


<thead>
<th class="nameColumn">REGISTRANT</th>
<th width="30%" class="mainView">EMAIL</th>
<th width="30%">SCHOOL</th>
<th width="15%">OPTIONS</th>
</thead>


<tbody>             
  
                  <% while (iter.hasNext())
                  {
                    p = (Personnel) iter.next();
                    s = p.getSchool(); %>
                       
<tr>
<td><%=p.getFullName()%></td>
<td class="mainView"><%=(p.getEmailAddress() !=null)?p.getEmailAddress():"N/A" %></td>

<td>
<%=(s!=null && s.getSchoolID()!=-1)?s.getSchoolName():"<span style='color:Red;'>NO SCHOOL ASSIGNED</span>"%>
<%if((s==null)&&(!other)) {
other=true;
}
%>
</td>

<td>
<% if(Utils.compareCurrentDate(evt.getEventDate()) > 0) { %>
                        <% if(permissions.containsKey("CALENDAR-SCHEDULE")){%>                
                        
                        <a title="De-Register this Participant?" class="no-print btn btn-danger btn-xs" href="#" data-href="deregisterEvent.html?pid=<%=p.getPersonnelID()%>&id=<%=evt.getEventID()%>" data-toggle="modal" data-target="#confirm-deregister">De-Register?</a>
                        
       <%}%>
 <% } %>
</td>
</tr>             
<% numEmpRegistered++;
}%> 

</tbody>
</table> 




<script>
$(document).ready(function(){
	
	var warnText="";
	var warnColor="#FFFFFF";
	var percentParticipants = Math.round(<%=numEmpRegistered %>/<%=evtMaxPart%>*100);
	//Write to calendar legend bottom

if (<%=evt.getEventMaximumParticipants()%> >0) {

	if (percentParticipants <= 10) {	
		warnText = "OH OH! Your event has only "+ <%=numEmpRegistered %>+" registered at this time out of "+<%=evtMaxPart%>+" avaliable spaces.";
		warnColor = '#DC143C';
		$('.partMessage').removeClass("alert-info").addClass("alert-danger").text(warnText);
		
	} else if (percentParticipants <= 25) {	
		warnText = "NOTICE: Your event has only "+ <%=numEmpRegistered %>+" registered at this time out of "+<%=evtMaxPart%>+" avaliable spaces.";
		warnColor = '#FF4500';
		$('.partMessage').removeClass("alert-info").addClass("alert-warning").text(warnText);	
		
	} else if (percentParticipants <= 50) {	
		warnText = "NOTICE: Your event has only "+ <%=numEmpRegistered %>+" registered at this time out of "+<%=evtMaxPart%>+" avaliable spaces.";
		warnColor = '#FFA500';
		$('.partMessage').removeClass("alert-info").addClass("alert-warning").text(warnText);
		
	} else if (percentParticipants <= 75) {		
		warnText = "Great! Your event has "+ <%=numEmpRegistered %>+" registered at this time.";
		warnColor = '#1E90FF';
		$('.partMessage').text(warnText);
		
	} else if (percentParticipants <= 90) {		
		warnText = "GOOD NEWS! Your event is almost full with "+ <%=numEmpRegistered %>+"  registered at this time.";
		warnColor = '#008000';
		$('.partMessage').removeClass("alert-info").addClass("alert-success").text(warnText);
		
	} else if (percentParticipants <= 100) {
		warnText = "GOOD NEWS! Your event has max number of spaces full with "+ <%=numEmpRegistered %>+"  registered.";
		warnColor = '#008000';
		$('.partMessage').removeClass("alert-info").addClass("alert-success").text(warnText);
		
	} else {
		warnText = "OPPS! Your event has nobody registered yet.";
		warnColor = '#FF000';
		$('.partMessage').removeClass("alert-info").addClass("alert-danger").text(warnText);
	}

} else {
	
	warnText = "NOTICE: Your event has "+ <%=numEmpRegistered %>+" registered at this time. Max # spaces is unlimited.";
	warnColor = '#1E90FF';
	$('.partMessage').text(warnText);
	
}	
	
//Chart it (Resize it on window change)
var ctx = document.getElementById('registeredParticipantsChart').getContext('2d');
registeredParticipantsChart = new Chart(ctx, {
	type: 'horizontalBar',
	  data: {
		  labels: [''],
	    datasets: [{
	    	label: "Participants",
	          backgroundColor: [""+warnColor+""],
	          data: [<%=numEmpRegistered %>]
	    }]
	  },	  
	  options: {
		  scales: {
	            xAxes: [{
	                ticks: {	                	
	                	 beginAtZero: true,
                         //steps: 5,
                         //stepValue: 5,
                         max: <%=evtMaxPart%>
	                }
	            }],
	            yAxes: [{
	                stacked: true
	            }]
	        },
	        responsive: false,
	        maintainAspectRatio: false,
	        aspectRatio: 4,	        
		  legend: { display: false },
	      title: {
	        display: true,
	        text: 'Number of Registered Participants'
	      }
	 }
 
	  
	});   
	
$(window).on('resize', function(){
	registeredParticipantsChart.resize();
});
	
});

</script>
    
<% }%>   
</div></div>
<br/>
         <div align="center" class="no-print navBottom">
         	<a href='#' class="btn btn-primary btn-xs" title='Print this page (pre-formatted)' onclick="jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:10px;font-size:16px;font-weight:bold;><img width=400 src=includes/img/nlesd-colorlogo.png><br/>Scheduled PD Event</div>'});">Print Page</a>
           	<a onclick='loadingData()' href="createEventParticipantsEmail.html?id=<%=evt.getEventID()%>" class="btn btn-primary btn-xs" title="Email Participants">Email Participants</a>
         <a onclick='loadingData()' class="btn btn-xs btn-danger" href="javascript:history.go(-1);" title="Back"><span class="	glyphicon glyphicon-step-backward"></span> Back</a>
         <a class="no-print btn btn-xs btn-danger" href="viewDistrictCalendar.html"><span class="	glyphicon glyphicon-step-backward"></span> Back to Calendar</a>
         </div> 
        

   <div class="modal fade" id="confirm-deregister" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header" style="font-size:14px;font-weight:bold;">
               Confirm De-Register
            </div>
            <div class="modal-body" style="font-size:11px;">
             Are you sure you want to remove this participant from this event?
            </div>
            <div class="modal-footer">
             <a class="btn btn-danger btn-dereg">Yes</a>
             <button type="button" class="btn btn-success" data-dismiss="modal">No</button>
             </div>
        </div>
    </div>
</div>      
       
       
 <script>
       $('#confirm-deregister').on('show.bs.modal', function(e) {
    	    $(this).find('.btn-dereg').attr('href', $(e.relatedTarget).data('href'));
    	    
    	});       
</script>
</body>
</html>