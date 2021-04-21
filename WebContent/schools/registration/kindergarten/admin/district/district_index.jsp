<%@ page language="java"
         import="com.esdnl.school.registration.kindergarten.bean.*" 
         isThreadSafe="false"%>

<%@ taglib prefix="sreg" uri="/WEB-INF/school_registration.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
                 
<esd:SecurityCheck permissions="KINDERGARTEN-REGISTRATION-ADMIN-VIEW" />


<html>
  
  <head>
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <TITLE>Administration</title>
    
    <script type="text/javascript">
    //var efi = new Array(211, 215, 219, 287, 244, 209, 247, 229, 232, 495, 289, 387, 192, 239, 207, 241, 196, 242, 162, 464, 414, 352, 403, 330, 341, 416, 595);
    	var efi = new Array(330, 211, 215, 352, 219, 287, 595, 464, 244, 209, 247, 229, 232, 495, 289, 341, 162, 192, 239, 207, 241, 403, 196, 242, 414);
    	jQuery(function(){
    		
    		$('.opbutton').button();
    		
    		$('#txt_StartDate, #txt_EndDate, #txt_ConfirmationDeadlineDate').datetimepicker({
					dateFormat: "dd/mm/yy",
					timeFormat: "hh:mm tt",
					changeYear: true,
					yearRange: "c-0:c+1"
				});
    		
    		$('#btn_cancelAddRegPeriod').click(function(){
    			$('#add-reg-period-form form')[0].reset();
    		});
    		
    		$('tr.period-data-row:odd').css({'background-color':'#f0f0f0'})
    		
    		$('#ddl_School').change(function(){
    			$('#ddl_Stream').children().remove();

    			if(parseInt($.inArray(parseInt($('#ddl_School').val()), efi)) > -1){
    				$('#ddl_Stream').append($('<option>').attr({'value':'', 'SELECTED':'SELECTED'}).text('--- Select One ---'));
        		$('#ddl_Stream').append($('<option>').attr('value', '1').text('ENGLISH'));
    				$('#ddl_Stream').append($('<option>').attr('value', '2').text('FRENCH'));
    			}
    			else {
    				$('#ddl_Stream').append($('<option>').attr({'value':'1', 'SELECTED':'SELECTED'}).text('ENGLISH'));
    			}
    		});
    		
    		$('input[type=text], select').css({'border' : 'solid 1px #7B9EBD', 'background-color' : '#FFFFCC'});
    		
    	});
    
    	var totalList = new Array();
		var englishAvalonList = new Array();
		var frenchAvalonList = new Array();
		var englishCWLList = new Array();
		var frenchCWLList = new Array();
		var dateList = new Array();  
    	
    </script>
    <script src="/MemberServices/schools/registration/kindergarten/admin/district/Chart.min.js"></script>
  </head>






  <body bgcolor="#BF6200">
  
  <div class="alert alert-danger" style="text-align:center;">NOTICE: System is undergoing maintenance. Please do not use at this time. Sorry for any inconvenience.</div>
  	
  <div style="display:none;">	
  
		<div align='center' style='font-size:14pt;font-weight:bold;color:#33cc33;padding-bottom:15px;'>
	  	Kindergarten Registration Periods<br/>
	  	<div style="float:left; width:33%;"><canvas id="provincialChart" height="200"></canvas></div>
	  	<div style="float:left; width:33%;"><canvas id="avalonChart" height="200"></canvas></div>
	  	<div style="float:left; width:33%;"><canvas id="cwlChart" height="200"></canvas></div>  	
	  	<br/><br/>
	  	<canvas id="dataChart" height="100"></canvas>
	  	
  	</div>
  
  
  
  	
		<div>
			<table width="100%" border="0" cellspacing="0" cellpadding="5" align="center" style='border-bottom: solid 2px grey;'>
				<tr>
					<th>School Year</th>
					<th>Zones</th>
					<th>Start Date</th>
					<th>End Date</th>
					<th>Is Past?</th>
					<th>Registrants</th>
					<th>English</th>
					<th>French</th>
					<th>Actions</th>
				</tr>
				<c:choose>
					<c:when test="${fn:length(periods) gt 0 }">
						<c:forEach items="${periods}" var="p">
						
				
						
							<tr class='period-data-row'>
								<td class="dateData">${p.schoolYear}</td>	
								<script>								       
							        dateList.push('${p.schoolYear}'); 							        
								</script>
								
								<td>							
									<c:forEach items="${p.zones}" var='z'>
										<c:choose>
											<c:when test="${z.zoneId eq 1 }">												
											A&nbsp;
											</c:when>
											<c:when test="${z.zoneId eq 2}">
											C&nbsp;
											</c:when>
											<c:when test="${z.zoneId eq 3 }">
											W&nbsp;
											</c:when>
											<c:when test="${z.zoneId eq 4 }">
											L&nbsp;											
											</c:when>
											<c:when test="${z.zoneId eq 5 }">
											P&nbsp;											
											</c:when>
										</c:choose>
										<c:if test="${z.zoneId eq '1'}">
										        <script>					        
										        englishAvalonList.push('${p.englishCount}');
								      			frenchAvalonList.push('${p.frenchCount}');										        
												</script>
										</c:if>
										<c:if test="${z.zoneId eq 2}">
										        <script>										       
										        englishCWLList.push('${p.englishCount}');
								      			frenchCWLList.push('${p.frenchCount}');										        
												</script>
										</c:if>
									</c:forEach>
								</td>
								<td><fmt:formatDate type="both" dateStyle="medium" value="${p.startDate}" /></td>
								<td><fmt:formatDate type="both" dateStyle="medium" value="${p.endDate}" /></td>
								<td align='center'>${p.past ? "Yes": "No"}</td>
								<td align='center' class="totalData">${p.registrantCount}</td>
								<td align='center' class="englishData">${p.englishCount}</td>
								<td align='center' class="frenchData">${p.frenchCount}</td>
									
								<td><a class='opbutton small' href="<c:url value='/schools/registration/kindergarten/admin/district/viewPeriodRegistrants.html?krp=${p.registrationId}' />">View Registrations</a></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan='6'>No kindergarten registrations periods found.</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
		<br />
		<div>
			<table align='center'>
				<tr>
					<td valign='top'>
						<div id='list-registrants-by-form' class='form-panel' style='width:100%; display:inline;'>
							<form method='post' action="<c:url value='/schools/registration/kindergarten/admin/district/listKindergartenRegistrantsBy.html'/>">
								<table align='center' cellspacing='2' cellpadding='2'>
									<caption>View Registrants By...</caption>
									<tr>
										<td class='label required'>School Year:</td>
										<td><sreg:RegistrationSchoolYearsDDL id='ddl_SchoolYear' offset='1' listAll='true' /></td>
									</tr>
									<tr>
										<td class='label required'>School:</td>
										<td><sreg:SchoolsDDL cls='required' id='ddl_School' dummy='true'/></td>
									</tr>
									<tr>
										<td class='label required'>Stream:</td>
										<td><sreg:SchoolStreamDDL cls='required' id='ddl_Stream' /></td>
									</tr>
									<tr>
										<td colspan='2' align='center'>- OR -</td>
									</tr>
									<tr>
										<td class='label required'>MCP #:</td>
										<td><input type='text' id='txt_MCPNumber' name='txt_MCPNumber' style='width:150px;' /></td>
									</tr>
									<tr>
										<td class='label required'>Student Name:</td>
										<td><input type='text' id='txt_StudentName' name='txt_StudentName' style='width:150px;' /></td>
									</tr>
									<tr>
										<td colspan='2' align='right'>
											<input type='submit' value='GO' class='opbutton' />
										</td>
									</tr>
								</table>
							</form>
						</div>
					</td>
					<td valign='top'>
						<div id='caps-reports-form' class='form-panel' style='width:100%; display:inline;'>
							<form method='post' action="<c:url value='/schools/registration/kindergarten/admin/district/listSchoolRegistrationCaps.html'/>">
								<table align='center' cellspacing='2' cellpadding='2'>
									<caption>School Registration Caps...</caption>
									<tr>
										<td class='label required'>School Year:</td>
										<td><sreg:RegistrationSchoolYearsDDL id='ddl_SchoolYear' offset='1' listAll='true' /></td>
									</tr>
									<tr>
										<td colspan='2' align='right'>
											<input type='submit' value='GO' class='opbutton' />
										</td>
									</tr>
								</table>
							</form>
						</div>
						<div style='height:5px;'>&nbsp;</div>
						<div id='summary-reports-form' class='form-panel' style='width:100%; display:inline;'>
							<form method='post' action="<c:url value='/schools/registration/kindergarten/admin/district/listSchoolRegistrationSummaries.html'/>">
								<table align='center' cellspacing='2' cellpadding='2'>
									<caption>School Registration Summary...</caption>
									<tr>
										<td class='label required'>School Year:</td>
										<td><sreg:RegistrationSchoolYearsDDL id='ddl_SchoolYear' offset='1' listAll='true' /></td>
									</tr>
									<tr>
										<td colspan='2' align='right'>
											<input type='submit' value='GO' class='opbutton' />
										</td>
									</tr>
								</table>
							</form>
						</div>
					</td>
					<td valign='top'>
						<div id='add-reg-period-form' class='form-panel' style='width:100%; display:inline;'>
							<form method='post' action="<c:url value='/schools/registration/kindergarten/admin/district/addKindergartenRegistrationPeriod.html'/>">
								<table align='center' cellspacing='2' cellpadding='2'>
									<caption>Add Registration Period...</caption>
									<tr>
										<td class='label required'>School Year:</td>
										<td><sreg:RegistrationSchoolYearsDDL id='ddl_SchoolYear' offset='2' /></td>
									</tr>
									<tr>
										<td class='label required'>Start Date:</td>
										<td><input type='text' id='txt_StartDate' name='txt_StartDate' /></td>
									</tr>
									<tr>
										<td  class='label required'>End Date:</td>
										<td><input type='text' id='txt_EndDate' name='txt_EndDate' /></td>
									</tr>
									<tr>
										<td  class='label required'>Confirmation<br/>Deadline Date:</td>
										<td><input type='text' id='txt_ConfirmationDeadlineDate' name='txt_ConfirmationDeadlineDate' /></td>
									</tr>
									<tr>
										<td class='label required' valign='top'>Associated Zones:</td>
										<td>
											<select id='txt_AssociatedZones' name='txt_AssociatedZones' multiple="multiple">
												<c:forEach items='${zones}' var='zone'>
													<option value='${zone.zoneId}' style='text-transform: capitalize'>${zone.zoneName} Zone</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<td colspan='2' align='right'>
											<input type='submit' value='add' class='opbutton' /> <input id='btn_cancelAddRegPeriod' type='button' value='cancel' class='opbutton' />
										</td>
									</tr>
								</table>
							</form>
						</div>
					</td>
				</tr>
			</table>
		</div>



</div>



<script>
     //Get last 4 years
     
     var uniqueDate = [];
	$.each(dateList, function(i, el){
    if($.inArray(el, uniqueDate) === -1) uniqueDate.push(el);
		});
     
    var dateLimit = uniqueDate.slice(0,4);
    
     var ctx = document.getElementById('dataChart').getContext('2d');
     //ctx.canvas.height = 100;
     var dataChart = new Chart(ctx, {
    	 
       type: 'bar',       
       data: {
         labels: ["1","2","3","4"],
         datasets: [{
           label: 'CWL English Numbers',
           data: englishCWLList,
           backgroundColor: "rgba(255, 153, 0, 0.3)"
         }, {
           label: 'CWL French Numbers',
           data: frenchCWLList,          
           backgroundColor: "rgb(51, 153, 51, 0.3)"
         }, 
         {
            label: 'Avalon English Numbers',
            data: englishAvalonList,          
          	backgroundColor: "rgba(220, 20, 60, 0.3)"
           }, 
           {
            label: 'Avalon French Numbers',
           data: frenchAvalonList,          
           backgroundColor: "rgba(0, 102, 255, 0.3)"
             }
         
         
         ]
       },options: {

    	   scales: {
               xAxes: [{
                       display: true
                       
                   }],
               yAxes: [{
                       display: true,                       
                       ticks: {
                           beginAtZero: false,
                           steps: 100,
                           stepValue: 100,
                           min: 0,
                           max: 2500
                       }
                   }]
           },
       },
    	   
     });
     
     </script>
<script>
var totalEnglishY1 = parseInt(englishAvalonList.slice(0,1))+ parseInt(englishCWLList.slice(0,1));
var totalFrenchY1 = parseInt(frenchAvalonList.slice(0,1))+ parseInt(frenchCWLList.slice(0,1));
var englishPercentage = Math.round(totalEnglishY1/(totalEnglishY1 + totalFrenchY1)* 100); 
var frenchPercentage = Math.round(totalFrenchY1/(totalEnglishY1 + totalFrenchY1)* 100);
var ctx = document.getElementById('provincialChart').getContext('2d');
var provincialChart = new Chart(ctx, {
	type: 'pie',
	  data: {
	    labels: [ "English Registrations: " + totalEnglishY1 + " ("+englishPercentage+"%)","French Registrations: " + totalFrenchY1 + " ("+frenchPercentage+"%)",],
	    datasets: [{
	      backgroundColor: ["#FF0000","#0066ff"],
	      data: [ 	totalEnglishY1,
	    	  		totalFrenchY1]
	    }]
	  },
	  
	  options: {
		  	  
	      title: {
	         display: true,
	         fontSize: 14,
	         text: 'Latest Total Registrations (Province)'
	     },
	     legend: {
	         display: true,
	         fontSize: 14,
	         position: 'top',

	     },
	     responsive: false
	 }

	  
	  
	});

var totalEnglishAvalon = parseInt(englishAvalonList.slice(0,1));
var totalFrenchAvalon = parseInt(frenchAvalonList.slice(0,1));
var englishPercentage = Math.round(totalEnglishAvalon/(totalEnglishAvalon + totalFrenchAvalon)* 100); 
var frenchPercentage = Math.round(totalFrenchAvalon/(totalEnglishAvalon + totalFrenchAvalon)* 100);
var ctx = document.getElementById('avalonChart').getContext('2d');
var avalonChart = new Chart(ctx, {
	type: 'pie',
	  data: {
	    labels: [ "English Registrations: " + totalEnglishAvalon + " ("+englishPercentage+"%)","French Registrations: " + totalFrenchAvalon + " ("+frenchPercentage+"%)",],
	    datasets: [{
	      backgroundColor: ["rgba(220, 20, 60, 0.3)","rgba(0, 102, 255, 0.3)"],
	      data: [ 	totalEnglishAvalon,
	    	  		totalFrenchAvalon]
	    }]
	  },
	  
	  options: {
		  	  
	      title: {
	         display: true,
	         fontSize: 14,
	         text: 'Latest Avalon Region Total Registrations'
	     },
	     legend: {
	         display: true,
	         fontSize: 14,
	         position: 'top',

	     },
	     responsive: false
	 }

	  
	  
	});

var totalEnglishCWL = parseInt(englishCWLList.slice(0,1));
var totalFrenchCWL = parseInt(frenchCWLList.slice(0,1));
var englishPercentage = Math.round(totalEnglishCWL/(totalEnglishCWL + totalFrenchCWL)* 100); 
var frenchPercentage = Math.round(totalFrenchCWL/(totalEnglishCWL + totalFrenchCWL)* 100);
var ctx = document.getElementById('cwlChart').getContext('2d');
var cwlChart = new Chart(ctx, {
	type: 'pie',
	  data: {
	    labels: [ "English Registrations: " + totalEnglishCWL + " ("+englishPercentage+"%)","French Registrations: " + totalFrenchCWL + " ("+frenchPercentage+"%)",],
	    datasets: [{
	      backgroundColor: ["rgba(255, 153, 0, 0.3)","rgb(51, 153, 51, 0.3)"],
	      data: [ 	totalEnglishCWL,
	    	  		totalFrenchCWL]
	    }]
	  },
	  
	  options: {
		  	  
	      title: {
	         display: true,
	         fontSize: 14,
	         text: 'Latest Central, Western and Labrador Total Registrations'
	     },
	     legend: {
	         display: true,
	         fontSize: 14,
	         position: 'top',

	     },
	     responsive: false
	 }

	  
	  
	});
</script>		
	</body>
	
</html>