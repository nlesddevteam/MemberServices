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
     <meta name="viewport" content="width=device-width, initial-scale=1.0">	
    <TITLE>Student Registration</title>
    <script>
    //var efi = new Array(211, 215, 219, 287, 244, 209, 247, 229, 232, 495, 289, 387, 192, 239, 207, 241, 196, 242, 162, 464, 414, 352, 403, 330, 341, 416, 595);
    	var efi = new Array(330, 211, 215, 352, 219, 287, 595, 464, 244, 209, 247, 229, 232, 495, 289, 341, 162, 192, 239, 207, 241, 403, 196, 242, 414);
    	jQuery(function(){
    		
    	    		
    		
    		$('#btn_cancelAddRegPeriod').click(function(){
    			$('#add-reg-period-form form')[0].reset();
    		});
    		
    		    		
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
       
    	<script>
	$('document').ready(function(){
		
		
		mTable = $(".registrationPeriodsTable").dataTable({
			"order" : [[0,"desc"]],		
			"bPaginate": false,
			responsive: true,
			dom: 'Bfrtip',
	        buttons: [			        	
	        	//'colvis',
	        	{
                extend: 'print',
                title: '<div align="center"><img src="/MemberServices/schools/registration/kindergarten/includes/img/nlesd-colorlogo.png" style="max-width:600px;"/></div>',
                messageTop: '<div align="center" style="font-size:18pt;">${sy} Kinderstart/Kindergarten Registration Periods</div>',           
                	 exportOptions: {
                         columns: [ 0,1,2,3,4,5,6,7 ],
                     }
            },
            {
                extend: 'csv',
                exportOptions: {
                    columns: [ 0,1,2,3,4,5,6,7 ],
                }
            },
            {
                extend: 'excel',
                exportOptions: {
                    columns: [ 0,1,2,3,4,5,6,7 ],
                }
            },    	
	        ],				
			 "columnDefs": [
				 {
		                "targets": [8],			               
		                "searchable": false,
		                "orderable": false
		            }
		        ]
		});		
		
				
		$("tr").not(':first').hover(
		  function () {
		    $(this).css("background","yellow");
		  }, 
		  function () {
		    $(this).css("background","");
		  }
		);				
		
			$(".loadPage").show();
			$(".loadingTable").css("display","none");
			$("#loadingSpinner").css("display","none");		    		  
	    		 
	    		    $('.mcpNum').mask('000000000000');
	    	    			

		});
	</script>

    
  </head>


  <body>
  
  	<div align='center' style='font-size:14pt;font-weight:bold;color:#004178;;padding-bottom:15px;'>
	  	Kinderstart/Kindergarten Registration Periods<br/>
	  	<div style="float:left; width:33%;"><canvas id="provincialChart" height="200"></canvas></div>
	  	<div style="float:left; width:33%;"><canvas id="avalonChart" height="200"></canvas></div>
	  	<div style="float:left; width:33%;"><canvas id="cwlChart" height="200"></canvas></div>  	
	  	<br/><br/>
	  	<canvas id="dataChart" height="100"></canvas>
	  	
  	</div>
  
  
  
     <div class="siteHeaderBlue">REGISTRATION PERIODS</div><br/>
     Below is the list of registration periods currently in the system. 
     <ul>
     <li>You can view the registration periods, or EXPORT complete data to CSV/Excel using the option below right for import into <b>PowerSchool</b>. 
     <li>You can also use the Print, Excel, or CSV on any table (below left atop any table) to export the current view. 
    <li>Use the Search on any table (below right atop any table) to quickly find any data as you type.
	</ul>
	
		<table class="registrationPeriodsTable table table-sm table-bordered responsive" width="100%" style="font-size:12px;background-color:White;">
					<thead class="thead-dark">
					<tr >							
					<th>YEAR</th>
					<th>ZONE(s)</th>
					<th>START (y/m/d)</th>
					<th>END (y/m/d)</th>
					<th>STATUS</th>
					<th>TOTAL</th>
					<th>ENGLISH</th>
					<th>FRENCH</th>
					<th>OPTIONS</th>
				</tr>
				</thead>
				<tbody>
				<c:choose>
					<c:when test="${fn:length(periods) gt 0 }">
					<c:set var="periodCnt" value="0"/>
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
								<td><fmt:formatDate type="both" pattern="yyyy/MM/dd @ h:mm a" value="${p.startDate}" /></td>
								<td><fmt:formatDate type="both" pattern="yyyy/MM/dd @ h:mm a" value="${p.endDate}" /></td>
								<td align='center'>${p.past ? "<span style='color:red;'>CLOSED</span>": "<span style='color:Green;'>OPEN</span>"}</td>
								<td align='center' class="totalData">${p.registrantCount}</td>
								<td align='center' class="englishData">${p.englishCount}</td>
								<td align='center' class="frenchData">${p.frenchCount}</td>									
								<td><a onclick="loadingData();" class='btn btn-xs btn-primary' href="/MemberServices/schools/registration/kindergarten/admin/district/viewPeriodRegistrants.html?krp=${p.registrationId}"><i class="far fa-eye"></i> VIEW </a>
								<a onclick="loadingData();" class='btn btn-xs btn-warning' href="/MemberServices/schools/registration/kindergarten/admin/district/viewPeriodRegistrantsExport.html?krp=${p.registrationId}"><i class="fas fa-file-export"></i> EXPORT </a>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>
						</tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>

		<br />
		
		
		<div class="row container-fluid" style="padding-top:5px;">
      	<div class="col-lg-4 col-12">
		
		<div class="card">
							  <div class="card-header"><b>VIEW REGISTRANTS BY:</b></div>
							  <div class="card-body">
										<form method='post' action="/MemberServices/schools/registration/kindergarten/admin/district/listKindergartenRegistrantsBy.html">
																							
												<b>School Year:</b>
												<sreg:RegistrationSchoolYearsDDL id='ddl_SchoolYear' offset='1' listAll='true' cls="form-control"/>
												
												<b>School:</b>
												<sreg:SchoolsDDL cls='required form-control' id='ddl_School' dummy='true'/>
												
												<b>Stream:</b>
												<sreg:SchoolStreamDDL cls='required form-control' id='ddl_Stream' />
												
												<br/><div align="center" style="padding-top:10px;padding-bottom:10px;"><b>- OR -</b><br/></div>
												
												<b>MCP #:</b>
												<input type='text' id='txt_MCPNumber' name='txt_MCPNumber' class="form-control mcpNum"/>
												
												<b>Student Name:</b>
												<input type='text' id='txt_StudentName' name='txt_StudentName' class="form-control" />
												<div align="center" style="padding-top:10px;">	
												<input onclick="loadingData();" type='submit' value='Search' class='btn btn-sm btn-primary' />
												</div>		
										</form>
							  </div>
		</div>
		
		</div>
		
		<div class="col-lg-4 col-12">
		
		<div class="card">
							  <div class="card-header"><b>SCHOOL REGISTRATION CAPS</b></div>
							  <div class="card-body">
							  <form method='post' action="/MemberServices/schools/registration/kindergarten/admin/district/listSchoolRegistrationCaps.html">			
								<b>School Year:</b>
								<sreg:RegistrationSchoolYearsDDL id='ddl_SchoolYear' offset='1' listAll='true' cls="form-control"/>
								<div align="center" style="padding-top:10px;">	
								<input onclick="loadingData();"  type='submit' value='Search' class='btn btn-sm btn-primary' />
								</div>						
								</form>
							  </div>
		</div>
		<br/>
		<div class="card">
							  <div class="card-header"><b>SCHOOL REGISTRATION SUMMARY</b></div>
							  <div class="card-body">
							  <form method='post' action="/MemberServices/schools/registration/kindergarten/admin/district/listSchoolRegistrationSummaries.html">							
							<b>School Year:</b>
							<sreg:RegistrationSchoolYearsDDL id='ddl_SchoolYear' offset='1' listAll='true' cls="form-control"/>
							<div align="center" style="padding-top:10px;">	
							<input onclick="loadingData();" type='submit' value='Search' class='btn btn-sm btn-primary'/>
							</div>
							</form>
							  </div>
		</div>
		
		</div>
		
		<div class="col-lg-4 col-12">
		
		<div class="card">
							  <div class="card-header"><b>ADD REGISTRATION PERIOD:</b></div>
							  <div class="card-body">
							 <form method='post' action="<c:url value='/schools/registration/kindergarten/admin/district/addKindergartenRegistrationPeriod.html'/>">
														
							<b>School Year:</b>
							<sreg:RegistrationSchoolYearsDDL id='ddl_SchoolYear' offset='2' cls="form-control"/>
							
							<b>Start Date:</b>
							
							<input type="text" class="form-control datetimepicker-input" id='txt_StartDate' name='txt_StartDate'  data-toggle="datetimepicker" data-target="#txt_StartDate"/>
							
							
							<b>End Date:</b>							
							<input type="text" class="form-control datetimepicker-input" id='txt_EndDate' name='txt_EndDate'  data-toggle="datetimepicker" data-target="#txt_EndDate"/>
							
							<b>Confirmation Deadline Date:</b>							
							<input type="text" class="form-control datetimepicker-input" id='txt_ConfirmationDeadlineDate' name='txt_ConfirmationDeadlineDate'  data-toggle="datetimepicker" data-target="#txt_ConfirmationDeadlineDate"/>
									
							<b>Associated Zones:</b>
									
											<select id='txt_AssociatedZones' name='txt_AssociatedZones' multiple="multiple" class='form-control' >
												<c:forEach items='${zones}' var='zone'>
													<option value='${zone.zoneId}' style='text-transform: capitalize'>${zone.zoneName} Zone</option>
												</c:forEach>
											</select>
									<div align="center" style="padding-top:10px;">			
											<input type='submit' value='Add' class='btn btn-sm btn-primary' /> &nbsp; <input id='btn_cancelAddRegPeriod' type='button' value='Cancel' class='btn btn-sm btn-danger' />
									</div>			
							</form>
							  </div>
		</div>
		
		</div>
		
		
		</div>
		

<script>

$('document').ready(function(){
	
	 
         $('#txt_StartDate,#txt_EndDate').datetimepicker({
        	 format: 'DD/MM/YYYY hh:mm A'
      });
         
         $('#txt_ConfirmationDeadlineDate').datetimepicker({
        	 format: 'DD/MM/YYYY hh:mm A'  
        
     });
         
        $('#ddl_School').prop('required',false);
        $('#ddl_Stream').removeAttr('required');
	
});
</script>


<script>
     //Get last 4 years
     
     var uniqueDate = [];
	$.each(dateList, function(i, el){
    if($.inArray(el, uniqueDate) === -1) uniqueDate.push(el);
		});
     
    var dateLimit = uniqueDate.slice(0,4);
    
  //  var chartYear = parseInt(dateList[1].substring(0,4));
  
 
    
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