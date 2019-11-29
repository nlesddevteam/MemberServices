<%@ page  language="java" 
          session="true" 
          import="java.util.*, 
            java.text.*, 
            com.awsd.pdreg.*,
            com.awsd.security.*,
            com.awsd.personnel.*,
            com.awsd.school.*,
            com.awsd.common.Utils,
            com.nlesd.school.bean.*,
            com.nlesd.school.service.*"
          isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions='CALENDAR-SCHEDULE' />

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

  Calendar cur = null;
  User usr = null;

  Date now = null;
  Date evtdt = null;
  Date evtenddt = null;
  Event evt = null;
  School s = null;
  boolean other;
  String status = "";
  SimpleDateFormat sdf = null;
  int numEmpAttended = 0;
  int numEmpRegistered = 0;
  int evtMaxAttend = 0;
  int evtMaxPart = 0;
  int sid = -1;
  EventAttendeeCollection attendees = null;
  attendees = (EventAttendeeCollection) request.getAttribute("EventAttendees");
  
  cur =  Calendar.getInstance();
  now = cur.getTime();
  
  dayofweek = cur.get(Calendar.DAY_OF_WEEK);

  evt = attendees.getEvent();
  evtdt = evt.getEventDate();
  evtenddt = evt.getEventEndDate();

  other = false;

  if(evt.isActive())
    status = "ACTIVE";
  else if(evt.isPast())
    status = "PAST";
  else
    status = "";
  
  sdf = new SimpleDateFormat("MM/dd/yyyy");
  
	String bgcolor = "";
	String txtcolor = "";
	String regionName = "";
  
  
%>

<html>

<head>
<title>PD Calendar</title>
<script>
	$("#loadingSpinner").css("display","none");
</script>


<script type="text/javascript">
	$(function(){
		
		
		$('#lst-school-id').change(function(){
			$('#lst-attendee-id').children('option').remove();
			$('#lst-attendee-id').append($('<option>').val('').text('--- SELECT ATTENDEE ---'));
			
			if($(this).val() != "") {
				var params = {};
				params.id = $(this).val();
				
				$.post('/MemberServices/PDReg/ajax/getSchoolPersonnel.html', params, function(xml){
					
					$(xml).find('PERSONNEL').each(function(){
						$('#lst-attendee-id').append($('<option>').val($(this).find('ID').text()).text($(this).find('DISPLAY').text()));	
					});
					
				});
			}
		});
		
		$('#lst-school-id, #lst-attendee-id').change(function(){
			$('#add-attendee-msg').html('&nbsp;');
		});
		
		$('#btn-add-attend-submit').click(function(){
			if($('#lst-attendee-id').val() == '') {
				$('#add-attendee-msg').html('Select attendee to add.').css("display","block").delay(5000).fadeOut();				
			}
			else{
				$('#add-attendee-msg').html("Success! Attendee Added.").css("display","block").delay(5000).fadeOut();				
				$('#frm-add-attendee').submit();
			}
		});
		
		$('#btn-update-attendance').click(function(){
			$("#btn-update-attendance").css("display","none");		
			$(".updateMsg").css("display","block").delay(8000).fadeOut();
			$('#frm-update-attendance').submit();
			
		});
		
		
		
	});
	
  
</script>
<style>
		.tableTitle {font-weight:bold;width:20%;}
		.tableResult {font-weight:normal;width:50%;}
		.tableResultImg {font-weight:normal;text-align:right;}
		.tableQuestionNum {font-weight:bold;width:5%;}
		.tableQuestion {width:95%;}
		.ratingQuestionNum {font-weight:bold;width:5%;}
		.ratingQuestion {width:75%;}
		.ratingAnswer {width:20%;}
		input[type="radio"] {margin-top: -1px;margin-left:6px;margin-right:2px;vertical-align: middle;}
</style>
<script src="/MemberServices/PDReg/includes/js/Chart.min.js"></script>
</head>

<body>

<%
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
					 regionName ="OTHER AREA";
				 }
              
              %>              



<div class="container-fluid no-print topGreenTitleArea" data-spy="affix" data-offset-top="0" style="position:fixed;width:100%;background-color:#008B8B;color:White;text-align:center;font-weight:bold;padding:5px;">                      
   Attendance for:<br/><span style="color:Yellow;"><%=evt.getEventName()%></span> 
</div>
<div class="registerEventDisplay" style="padding-top:50px;font-size:11px;">
<div style="margin-left:5px;margin-right:5px;">
 <div align="center" class="no-print"><a href="viewDistrictCalendar.html"><img class="topLogoImg" src="includes/img/pdcalheader.png" border=0 style="padding-bottom:10px;"/></a></div>

<div class="alert alert-danger no-print topAlert" align="center" style="font-size:11px;display:none;"><b>*** EVENT ATTENDENCE NOTICE ***</b><br/>It is manditory that you confirm attendence to any event you hold. Please confirm below anyone registered who has attended the event. If someone attended who was not registered or listed below, please add using the 'Missing a Participant' link below and confirm.</div>         


	<div style="font-size:14px;font-weight:bold;width:100%;background-color:<%=bgcolor%>;color:<%=txtcolor%>">&nbsp;<%=regionName%> EVENT</div>
					
    <table class="table table-striped table-condensed" style="font-size:12px;padding-top:3px;">							   
										    
					<tbody>					
						<tr>
						<td class="tableTitle">EVENT:</td>
						<td class="tableResult"><%=evt.getEventName()%></td>
						<td class="tableResultImg no-print" rowspan=6 width="*"><img src="includes/img/sit2.gif" title="Attendance" class="attendImg"></td>
						</tr>
						<tr>
						<td class="tableTitle">TYPE:</td>
						<td class="tableResult"><%=evt.getEventType().getEventTypeName()%></td>
						</tr>
						<tr>
						<td class="tableTitle">DESCRIPTION:</td>
						<td class="tableResult"><%=evt.getEventDescription()%></td>
						</tr>
						<tr>
						<td class="tableTitle">LOCATION (HOST):</td>
						<td class="tableResult"> <%=evt.getEventLocation()%> 						
						<% if(evt.isCloseOutDaySession() || evt.isPDOpportunity()) { %>
              	 			<br/>(Hosted by: <span style="text-transform:Capitalize;"><%=evt.getScheduler().getFullNameReverse()%></span>) 
                		<% } else {%>
               				<br/>(N/A)
              			<%} %>
						</td>
						</tr>
						<tr>
						<td class="tableTitle">START/END DATE:</td>
						<td class="tableResult"><%=sdf.format(evtdt)%> 
						<% if(evtenddt != null) { %>
						 to <%=sdf.format(evtenddt)%>
						 <%} %>
						
						</td>
						</tr>
						<% if(!evt.getEventStartTime().equals("UNKNOWN")) { %>
						<tr>
						<td class="tableTitle">START/END TIME:</td>
						<td class="tableResult">						
                       <%=evt.getEventStartTime()%> to <%=evt.getEventFinishTime()%>                		
						</td>
						</tr>
						<% } %>
						<% if(evt.isCloseOutDaySession() || evt.isPDOpportunity()) { %>						
						<tr>
						<td class="tableTitle">MAX PARTICIPANTS (#REGISTERED):</td>
						<td class="tableResult">
						<% if(evt.getEventMaximumParticipants() > 0) { %>
                      	<%=evt.getEventMaximumParticipants()%>
                    	<% } else { %>
                      	UNLIMITED
                    	<% } %>
						 ( <%=evt.getRegistrationCount()%> )
						</td>
						</tr>
                        <% } %>
                         </tbody>
 </table> 
 <hr>  
<div class="alert alert-success updateMsg" align="center" style="display:none;">Updating Attendance, please wait...</div>
<br/>     


<% if(evt.isCloseOutDaySession() || evt.isPDOpportunity()) { //Get registered/max
	evtMaxPart = evt.getRegistrationCount();
			
} else { //Get attendence

	evtMaxPart = attendees.size();

}%>
 

          
<%      if(attendees == null || (attendees.size() <= 0))
        {
%>       

 <div class="alert alert-danger" align="center">Sorry, no personnel currently registered for this event.</div>

<%      }
        else
        {
%>        

<div  style="height:150px;">
<canvas id="registeredAttendeesChart" style="width:100%;height:100%;"></canvas>
</div>

<div class="partMessage alert alert-info" style="font-size:11px;text-align:center;"></div>

              	<form id='frm-update-attendance' action='updateEventAttendance.html' method='POST'>
              	<input type='hidden' name='hdn-event-id' value='<%= evt.getEventID() %>' />
                
       <table class="table table-condensed" id="calEventsTable" style="font-size:11px;">
       				<thead>
                	<tr style="background-color:#008B8B;color:#FFFFFF;">    
                	<th width="85%">SCHOOL/PARTIPANT(S)</th>					           	
                     <th width="15%" style="text-align:center;">ATTENDED?</th>
                  </tr>
                  </thead>
                  <tbody>                                  
                  <% for (EventAttendee ea : attendees) {
                    s = ea.getPersonnel().getSchool();
                    
                    if((s!=null)&&(s.getSchoolID() != sid))
                    {
                      sid = s.getSchoolID();
                      %>                       
                      <tr><td colspan=2 style="text-transform:uppercase;background-color:#FFFFF0;color:#008B8B;border-top:1px solid #008B8B;"><b><%=s.getSchoolName()%></b></td></tr>
					<% }  else if((s == null)&&(!other)) { 
						other = true;%> 					                  
                      <tr><td colspan="2" style="background-color:#FFFFF0;color:#008B8B;border-top:1px solid #008B8B;"><b>OTHER</b></td></tr>
					<%  }%>
					<tr>
					<%numEmpRegistered++; %>
					<td>&nbsp;&nbsp;&nbsp;&middot;&nbsp;<%= ea.getPersonnel().getFullName()%> </td>					
					<td style="text-align:center;">
                      <% if(evt.isActive() || evt.isPast()) { %>
                          <input type="checkbox" name='attendees[]' value='<%= ea.getPersonnel().getPersonnelID() %>' <%= ea.isAttended() ? " CHECKED" : "" %> />
                          
                          <%
                          if (ea.isAttended()) {
                        	  numEmpAttended++; 
                          }
                          
                          %>
                          
                      <% } %>
                    </td>
                    </tr>
					<% }%>
					</tbody>
		</table>
		<br/>
		<div class="alert alert-success updateMsg" align="center" style="display:none;">Updating Attendance, please wait...</div>
					  <div align="center" class="no-print navBottom">					
					<a href="#" id='btn-update-attendance' class="btn btn-xs btn-success" title="Update/Confirm Attendence"><span class="glyphicon glyphicon-refresh"></span> Update/Confirm</a>
					<button id='lnk-add-attendee' type="button" class="btn btn-danger btn-xs" data-toggle="modal" data-target="#add-attendee-panel" title="Add a Missing Attendee"><span class="glyphicon glyphicon-user"></span> Add Participant?</button>
					<p class="mobileView"></p>
					<a href='#' title='Print this page (pre-formatted)' class="btn btn-primary btn-xs"  onclick="jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:10px;><img width=400 src=includes/img/nlesd-colorlogo.png><br/><br/><b>Professional Development Calendar</b></div><br/>'});"><span class="glyphicon glyphicon-print"></span> Print</a>	                     
 					<a class="btn btn-xs btn-danger" href="javascript:history.go(-1);" title="Back"><span class="	glyphicon glyphicon-step-backward"></span> Back</a>
					</div>
						
			</form>
      

      
<script>
$(document).ready(function(){
	
	var warnText="";
	var warnColor="#FFFFFF";
	if (<%=evtMaxPart%><1) {
		evtMaxPart=1;
	}
	var percentAttended = Math.round(<%=numEmpAttended %>/<%=evtMaxPart%>*100);
	//Write to calendar legend bottom

	if (percentAttended <=0) {
		$(".topAlert").css("display","block");
	}
	
if (<%=evt.getEventMaximumParticipants()%> >0) {

	
	
	if(percentAttended<=25) {		
		warnText = "<b style='font-size:14px;'>*** NOTICE "+percentAttended+"% Attendence ***</b><br/> Your event had only <b> <%=numEmpAttended %></b> participants attending out of <b><%=numEmpRegistered%></b>  registered. (<b>"+percentAttended+"% Attendence</b>).<br/>This is a very low turnout for an event. Please report this event attendence to your supervisor and/or confirm attendence. Confirmination of every event attendence is REQUIRED and attendence is logged. Max # spaces was "+<%=evt.getEventMaximumParticipants()%>+".";	
		$('.partMessage').removeClass("alert-info").addClass("alert-danger").html(warnText);
	} else if(percentAttended<=50 && percentAttended >25) {
		warnText = "<b style='font-size:14px;'>*** NOTICE "+percentAttended+"% Attendence ***</b><br/> Your event had <b><%=numEmpAttended %></b> participants attending out of <b><%=numEmpRegistered%> </b> registered. ("+percentAttended+"% Attendence).<br/>This is a low turnout for an event. Please report this event attendence to your supervisor and/or confirm attendence. Confirmination of every event attendence is REQUIRED and attendence is logged. Max # spaces was "+<%=evt.getEventMaximumParticipants()%>+".";	
		$('.partMessage').removeClass("alert-info").addClass("alert-danger").html(warnText);
	} else if(percentAttended<=80 && percentAttended >50) {
		warnText = "<b style='font-size:14px;'>*** NOTICE "+percentAttended+"% Attendence ***</b><br/> Your event had <b><%=numEmpAttended %></b> participants attending out of <b><%=numEmpRegistered%></b> registered.  ("+percentAttended+"% Attendence).<br/>This is a satisfactory turnout for an event. Please confirm attendence as confirmination of event attendence is REQUIRED and attendence is logged. Max # spaces was "+<%=evt.getEventMaximumParticipants()%>+".";	
		$('.partMessage').removeClass("alert-info").addClass("alert-warning").html(warnText);
	} else if(percentAttended<=95 && percentAttended >80) {
		warnText = "<b style='font-size:14px;'>*** GREAT! "+percentAttended+"% Attendence ***</b><br/> Your event had <b><%=numEmpAttended %></b> participants attending out of <b><%=numEmpRegistered%></b> registered. ("+percentAttended+"% Attendence).<br/>This is a great turnout for an event. Please confirm attendence as confirmination of event attendence is REQUIRED and attendence is logged. Max # spaces was "+<%=evt.getEventMaximumParticipants()%>+".";	
		$('.partMessage').removeClass("alert-info").addClass("alert-success").html(warnText);
	} else {	
		warnText = "<b style='font-size:14px;'>*** GOOD JOB! "+percentAttended+"% Attendence ***</b><br/> Your event had <b><%=numEmpAttended %></b> participants attending out of <b><%=numEmpRegistered%></b> registered. ("+percentAttended+"% Attendence).<br/>Fantastic turnout.  Max # spaces was "+<%=evt.getEventMaximumParticipants()%>+".";	
		$('.partMessage').removeClass("alert-info").addClass("alert-success").html(warnText);
	}
	

} else {	
	
	if(percentAttended<=25) {		
		warnText = "<b style='font-size:14px;'>*** NOTICE "+percentAttended+"% Attendence ***</b><br/> Your event had only <b><%=numEmpAttended %></b> participants attending out of <b><%=numEmpRegistered%></b> registered.  (<b>"+percentAttended+"% Attendence</b>).<br/>This is a very low turnout for an event. Please report this event attendence to your supervisor and/or confirm attendence. Confirmination of every event attendence is REQUIRED and attendence is logged. Max # spaces was unlimited.";	
		$('.partMessage').removeClass("alert-info").addClass("alert-danger").html(warnText);
	} else if(percentAttended<=50 && percentAttended >25) {
		warnText = "<b style='font-size:14px;'>*** NOTICE "+percentAttended+"% Attendence ***</b><br/> Your event had <b> <%=numEmpAttended %> </b>  participants attending out of <b><%=numEmpRegistered%></b> registered. ("+percentAttended+"% Attendence).<br/>This is a low turnout for an event. Please report this event attendence to your supervisor and/or confirm attendence. Confirmination of every event attendence is REQUIRED and attendence is logged. Max # spaces was unlimited.";	
		$('.partMessage').removeClass("alert-info").addClass("alert-danger").html(warnText);
	} else if(percentAttended<=80 && percentAttended >50) {
		warnText = "<b style='font-size:14px;'>*** NOTICE "+percentAttended+"% Attendence ***</b><br/> Your event had <b> <%=numEmpAttended %></b> participants attending out of <b><%=numEmpRegistered%></b> registered.. ("+percentAttended+"% Attendence).<br/>This is a satisfactory turnout for an event. Please confirm attendence as confirmination of event attendence is REQUIRED and attendence is logged. Max # spaces was unlimited.";	
		$('.partMessage').removeClass("alert-info").addClass("alert-warning").html(warnText);
	} else if(percentAttended<=95 && percentAttended >80) {
		warnText = "<b style='font-size:14px;'>*** GREAT! "+percentAttended+"% Attendence ***</b><br/> Your event had <b><%=numEmpAttended %> </b>  participants attending out of <b><%=numEmpRegistered%></b> registered.. ("+percentAttended+"% Attendence).<br/>This is a great turnout for an event. Please confirm attendence as confirmination of event attendence is REQUIRED and attendence is logged. Max # spaces was unlimited.";	
		$('.partMessage').removeClass("alert-info").addClass("alert-success").html(warnText);
	} else {	
		warnText = "<b style='font-size:14px;'>*** GOOD JOB! "+percentAttended+"% Attendence ***</b><br/> Your event had <b><%=numEmpAttended %> </b> participants attending out of <b><%=numEmpRegistered%></b> registered. ("+percentAttended+"% Attendence).<br/>Fantastic turnout. Max # spaces was unlimited.";	
		$('.partMessage').removeClass("alert-info").addClass("alert-success").html(warnText);
	}
}	
	
	
	
//Chart

var ctx = document.getElementById('registeredAttendeesChart').getContext('2d');
var registeredAttendeesChart = new Chart(ctx, {
	type: 'horizontalBar',
	  data: {
		  labels: [''],
	    datasets: [{
	    	label: "# Registered",
	        backgroundColor: ["#1E90FF"],
	        data: [<%=numEmpRegistered %>]
	    },{
            label: "# Attended",
            backgroundColor: ["#00FF00"],
            data: [<%=numEmpAttended %>]
        }]
	  },	  
	  options: {
		  scales: {
	            xAxes: [{
	                ticks: {	                	
	                	 beginAtZero: true,
                        // steps: 5,
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
		  
		  legend: { display: true },
	      title: {
	        display: true,	      
	        text: 'Number of Registered Participants vs Attendees'
	      }
	 }
 
	});   

$(window).on('resize', function(){
	registeredAttendeesChart.resize();
});


});

</script>      
        
<%  }%>    
        

 </div> </div>
 
 
<!-- Modal -->
<div id="add-attendee-panel" class="modal fade" role="dialog" style="z-index:9999;">
 
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Add Participant to this Event</h4>
      </div>
      <div class="modal-body">       
       <form id='frm-add-attendee' action='addEventAttendee.html' method='POST'>
       
      
	          		<input type='hidden' name='hdn-event-id' value='<%= evt.getEventID() %>' />
		          	<div class="formTitle">SCHOOL:</div>
					<div class="formBody">
						<select id="lst-school-id" name="lst-school-id" class="form-control">
			              	<option value="">--- SELECT SCHOOL/LOCATION ---</option>
			              	<% for(SchoolZoneBean sz : SchoolZoneService.getSchoolZoneBeans()){ %>
			              		<optgroup label="<%= sz.getZoneName().toUpperCase() %>">
				              	<% for(School sch : SchoolDB.getSchools(sz)){ %>
				              			<option value="<%= sch.getSchoolID() %>"><%= sch.getSchoolName() %></option>
				              	<% } %>
				              	</optgroup>
			              	<% } %>
			              </select>
					</div>					
		          	<div class="formTitle">ATTENDEE:</div>
		          	<div class="formBody">
		          	<select id="lst-attendee-id" class="form-control" name="lst-attendee-id">
			              	<option value="">--- SELECT ATTENDEE ---</option>
			        </select></td>
		          	</div>
			        <div id='add-attendee-msg' class="alert alert-success" style="display:none;"></div>	           
               
    	</form>
      </div>
      <div class="modal-footer">
        <button id='btn-add-attend-submit' class="btn btn-success btn-xs" type='button'>Add Attendee</button> <button type="button" class="btn btn-danger btn-xs" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
   
</div> 
<script>
//Prevent resubmit
if ( window.history.replaceState ) {
  window.history.replaceState( null, null, window.location.href );}
</script>

</body>
</html>