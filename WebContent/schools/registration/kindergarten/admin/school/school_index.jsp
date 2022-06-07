<%@ page language="java"
         import="com.esdnl.school.registration.kindergarten.bean.*" 
         isThreadSafe="false"%>

<%@ taglib prefix="sreg" uri="/WEB-INF/school_registration.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
                 
<esd:SecurityCheck permissions="KINDERGARTEN-REGISTRATION-SCHOOL-VIEW" />

<html>
  
  <head>   
    <TITLE>Administration</title>
    
    
    <script>
    $('document').ready(function(){
    	
    	$("#loadingSpinner").css("display","none");
    	$("#viewReg").prop('disabled', true);
    	
    	$(function(){
    		$('#ddl_SchoolYear').change(function(){ 
    			$('#ddl_Stream').val("1") ;
    			$("#viewReg").prop('disabled', false);
    		});
    	});
    	
    	$(function(){
    		$('#ddl_Stream').change(function(){     			
    			if($(this).children("option:selected").val() != '') {
    			$("#viewReg").prop('disabled', false);
    			} else {
    			$("#viewReg").prop('disabled', true);    				
    			};
    		});
    	});
    	
    	mTable = $(".registrationPeriodsTable").dataTable({
			"order" : [[1,"asc"]],		
			"bPaginate": false,
			responsive: true,
			dom: 'Bfrtip',
	        buttons: [		
	        	
	        	{ 
	        		 extend: 'excel',	
	        		 exportOptions: {
                		 format: {
           	                body: function ( data, row, column, node ) {
           	                    // Only show last 4 digitis excel, and strip HTML from other columns.
           	                    return column === 0 ? data.replace(/\d(?=\d{4})/g, '*'): data .replace(/(&nbsp;|<([^>]+)>)/ig, "");   	                    
           	                 }
           	            },            		 
                		  columns: [ 0, 1, 2,3,4 ],
                      },
            },
            { 
       		 extend: 'csv',	
       		 exportOptions: {
           		 format: {
      	                body: function ( data, row, column, node ) {
      	                    // Only show last 4 digitis excel, and strip HTML from other columns.
      	                    return column === 0 ? data.replace(/\d(?=\d{4})/g, '*'): data .replace(/(&nbsp;|<([^>]+)>)/ig, "");   	                    
      	                 }
      	            },            		 
           		  columns: [ 0, 1, 2,3,4 ],
                 },
       },
	        	
	        	{
                extend: 'print',
                title: '<div align="center"><img src="/MemberServices/schools/registration/kindergarten/includes/img/nlesd-colorlogo.png" style="max-width:600px;"/></div>',
                messageTop: '<div align="center" style="font-size:18pt;">${sy} Kinderstart/Kindergarten Registrants for ${fn:replace(sch.schoolName, "'", "&apos;")}</div>',           
               messageBottom: '<br/><div class="alert alert-danger"><b>Confidentiality Warning:</b> This document and any attachments are intended for the sole use of the intended recipient(s), and contain privileged and/or confidential information. If you are not an intended recipient, any review, retransmission, printing, copying, circulation or other use 	of this message and any attachments is strictly prohibited.</div>',
                exportOptions: {
                		 format: {
           	                body: function ( data, row, column, node ) {
           	                    // Only show last 4 digitis on print
           	                    return column === 0 ?
           	                        data.replace(/\d(?=\d{4})/g, "*") :           	                        	
           	                        data;
           	                }
           	            },            		 
                		  columns: [ 0, 1, 2,3,4 ],
                      },
            },
	        ],				
			 "columnDefs": [
				 {
		                "targets": [5],			               
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
    		
if(${sch ne null}) {
			$('#ddl_Stream').children().remove();
			if(parseInt($.inArray(parseInt(${sch.schoolID}), efi)) > -1){
				$('#ddl_Stream').append($('<option>').attr({'value':'', 'SELECTED':'SELECTED'}).text('--- Select One ---'));
    		$('#ddl_Stream').append($('<option>').attr('value', '1').text('ENGLISH'));
				$('#ddl_Stream').append($('<option>').attr('value', '2').text('FRENCH'));
			}
			else {
				$('#ddl_Stream').append($('<option>').attr({'value':'1', 'SELECTED':'SELECTED'}).text('ENGLISH'));
			}
	
} else {
	$(".noaccessMsg").css("display","block");
	$(".disablePage").css("display","none");
}	
    	
    });
    
    
    
    
    </script>
    
 
  </head>

  <body>
  
  
  <div class="alert alert-danger noaccessMsg" style="display:none;text-align:center;"><b>USER ERROR:</b> You are not assigned to any school. Kindergarten Administration is disabled until you are properly assigned in MemberServices for your school.</div>
  <div class="disablePage">
  
		<div align='center' style='font-size:14pt;font-weight:bold;color:#004178;;padding-bottom:15px;'>
	  	${sch ne null ? sch.schoolName : ""} Kinderstart/Kindergarten Registrants
  	</div>
  	
       <c:if test="${krp ne null}">
<div class="card">
							  <div class="card-header"><b>ADD REGISTRANT:</b></div>
							  <div class="card-body">		
									
		You can add a new registrant to the most recent registration period by clicking the link below. Registrations for years past cannot be added as the data may have already been merged with PowerSchool.<br/><br/>
		<div align="center">
		<a onclick="loadingData();" href="/MemberServices/schools/registration/kindergarten/admin/school/addKindergartenRegistrant.html?id=${ krp.registrationId }" class='btn btn-sm btn-primary'>Add New Registrant</a>
		</div>
		
		
						</div>
		</div>       
       <br/><br/>
       </c:if>
       
		
		<div class="card">
							  <div class="card-header"><b>VIEW REGISTRANTS BY:</b></div>
							  <div class="card-body">		
									<form method='post' action="/MemberServices/schools/registration/kindergarten/admin/school/index.html">
									<div class="row container-fluid">
									<div class="col-lg-6 col-12">																	
									<b>School Year:</b>
									<sreg:RegistrationSchoolYearsDDL cls="form-control" id='ddl_SchoolYear' offset='1' listAll='true' value='${sy}' />
									</div>
									<div class="col-lg-6 col-12">
									<b>Stream:</b>			
									<sreg:SchoolStreamDDL cls='required form-control' id='ddl_Stream' value="${ss ne null ? ss.value : 0}" />
									</div>
									</div>
									<br/><br/>
									<div align="center">
									
									<input id="viewReg" onclick="loadingData();" type='submit' value='View Registrants' class='btn btn-danger btn-sm' />
									</div>		
									</form>
		
						</div>
		</div>
		
		
		
		
		
		<c:choose>
		<c:when test="${sy ne null}">
					<div class="siteHeaderBlue">${sy} ${ss ne null ? ss.text : ""}  <span style='color:red;'>(${ fn:length(registrants)})</span></div><br/>
					Below is the list of registrants for ${sy} sorted by Student Name. You can sort by clicking on any column header, or search using the search tool at right. You can also <b>Print</b> the list, or export the list to Excel or CSV data. To print, use the Print option below - <b>DO NOT USE</b> the browser file, print feature.<br/>
		</c:when>
		<c:otherwise>
					<div class="siteHeaderBlue">No Registrants Listed</div>
					<div class="alert alert-warning" style="text-align:center;"><b>NOTICE:</b> Please select the School year and/or Stream above to select a list to display.</div>
		</c:otherwise>
		</c:choose>
		
		<br/>
								

		<table class="registrationPeriodsTable table table-sm table-bordered responsive" width="100%" style="font-size:12px;background-color:White;">
					<thead class="thead-dark">
					<tr style="color:Black;font-size:12px;">
					<th>MCP NUMBER</th>
					<th>STUDENT NAME</th>
					<th>STREAM</th>
					<th>APPROVED?</th>
					<th>STATUS</th>
					<th>OPTIONS</th>
				</tr>
				</thead>
				<tbody>
				<c:choose>
					<c:when test="${fn:length(registrants) gt 0 }">
						<c:forEach items="${registrants}" var="r">
							<tr class='period-data-row'>
								<td width="15%">${r.mcpNumber}</td>
								<td width="35%">${r.studentFullName}</td>
								<td width="10%">${r.schoolStream.text}</td>
								<td width="15%" align='center'>${r.physicalAddressApproved ? "<span style='font-weight:bold;color:green;'><i class='fas fa-check'></i> Yes</span>" : "<span style='font-weight:bold;color:red;'><i class='fas fa-times'></i> No</span>"}</td>
								<td width="10%">${r.status.accepted eq true ? "<span style='font-weight:bold;color:green;'><i class='fas fa-check'></i> Accepted</span>" : r.status.waitlisted eq true ? "<span style='font-weight:bold;color:orange;'><i class='far fa-hourglass'></i> Waitlisted</span>" : "<span style='font-weight:bold;color:#9E7BFF;'><i class='fas fa-cog'></i> Processing</span>"}</td>
								<td width="15%">
									<a onclick="loadingData();" class='btn btn-xs btn-primary' href="<c:url value='/schools/registration/kindergarten/admin/school/viewRegistrant.html?kr=${r.registrantId}' />"><i class="far fa-eye"></i> VIEW</a>
									<a onclick="loadingData();" class='btn btn-xs btn-warning' href="<c:url value='/schools/registration/kindergarten/admin/school/editKindergartenRegistrant.html?kr=${r.registrantId}' />"><i class="far fa-edit"></i> EDIT</a>
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
						</tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
			
			<br/><br/>
			
		</div>
	
	</body>
	
</html>