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
   
    <TITLE>Administration</title>
    
    <script>
$('document').ready(function(){		

		
		mTable = $(".registrationTable").dataTable({
			"order" : [[0,"asc"]],		
			"bPaginate":false,			
			responsive: true,
			dom: 'Bfrtip',
	        buttons: [			        	
	        	//'colvis',
	        	{
	        	
                extend: 'print',
                title: '<div align="center"><img src="/MemberServices/schools/registration/kindergarten/includes/img/nlesd-colorlogo.png" style="max-width:600px;"/></div>',
                messageTop: '<div align="center" style="font-size:18pt;">${sy} ${krp.schoolYear} Kinderstart/Kindergarten Registrants</div>',
                messageBottom: '<div class="alert alert-danger"><b>Confidentiality Warning:</b> This document and any attachments are intended for the sole use of the intended recipient(s), and contain privileged and/or confidential information. If you are not an intended recipient, any review, retransmission, printing, copying, circulation or other use 	of this message and any attachments is strictly prohibited.</div>',
                	 exportOptions: {
                		 format: {
          	                body: function ( data, row, column, node ) {
          	                    // Only show last 4 digitis on print
          	                    return column === 2 ?
          	                        data.replace(/\d(?=\d{4})/g, "*") :
          	                        data;
          	                }
          	            },
                		 
                         columns: [ 1,2,3,4,5,6,7,8],
                       
                     }
            },
            { 
       		 extend: 'excel',	
       		 exportOptions: {
           		 format: {
      	                body: function ( data, row, column, node ) {
      	                    // Only show last 4 digitis excel, and strip HTML from other columns.
      	                    return column === 2 ? data.replace(/\d(?=\d{4})/g, '*'): data .replace(/(&nbsp;|<([^>]+)>)/ig, "");   	                    
      	                 }
      	            },            		 

                    columns: [ 1,2,3,4,5,6,7,8],
                 },
       },
       { 
     		 extend: 'csv',	
     		 exportOptions: {
         		 format: {
    	                body: function ( data, row, column, node ) {
    	                    // Only show last 4 digitis excel, and strip HTML from other columns.
    	                    return column === 2 ? data.replace(/\d(?=\d{4})/g, '*'): data .replace(/(&nbsp;|<([^>]+)>)/ig, "");   	                    
    	                 }
    	            },            		 

                    columns: [ 1,2,3,4,5,6,7,8],
               },
     },
         
	        ],				
			 "columnDefs": [
				 {
		                "targets": [9],			               
		                "searchable": false,
		                "orderable": false
		            },
		            {
		                "targets": [0],	
		                "visible":false,
		                "searchable": false,
		                "orderable": false
		            },
		            
		            
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
		
		
		
});
    
    
    
    	jQuery(function(){
    		
    		$('#btnSendPhysicalAddressProofReminder').click(function(){
    			if(confirm('Send Reminder?')) {
    				
    				$('#btnSendPhysicalAddressProofReminder').attr('disabled', 'disabled');
    				$.post("/MemberServices/schools/registration/kindergarten/admin/district/ajax/sendProofPhysicalAddressReminderEmail.html", 
    						{	
    							id: ${krp ne null ? krp.registrationId : 0},
    							ajax : true 
    						}, 
    						function(data){
    							if($(data).find('PHYSICAL-ADDRESS-PROOF-REMINDER-RESPONSE').length > 0) {
    								$('.divResponseMsg').css("display","block").html("<br/>" + $(data).find('PHYSICAL-ADDRESS-PROOF-REMINDER-RESPONSE').first().attr('msg') + "<br/>");
    							}
    							else{
    								$('#btnSendPhysicalAddressProofReminder').removeAttr('disabled');
    							}
    						}, 
    						"xml");
    			}
    		});
    		
    	
    		
    		$('.opbutton-delete').click(function(){
    			return confirm("Are you sure you want to delete the registrant?");
    		});
    		
    	
    		
    	});
    	
    </script>
    <style> 
    .table td {vertical-align:middle;}
    @media print {		
	.pageBreak{page-break-after: always;}		
	td {white-space: nowrap;}
	
  body {
  	font-size:10pt !important;   
    color: #000;
    background-color: #fff;    
     border: 0;
    padding: 0;
    margin: 0;
        
  }
}
    
    </style>
    <c:set var="frenchCount" value="0"/>
    <c:set var="englishCount" value="0"/>
  </head>

  <body>
 
		<div align='center' style='font-size:14pt;font-weight:bold;color:#004178;;padding-bottom:15px;'>
	  	Kinderstart/Kindergarten Registrants<br/>  
  	 <div style="font-size:14px;">
  		<c:choose>
					<c:when test="${krp ne null}">
						${krp.schoolYear} Registration Period<br/><fmt:formatDate type="both" dateStyle="medium" value="${krp.startDate}" /> - <fmt:formatDate type="both" dateStyle="medium" value="${krp.endDate}" /><br/>Total: <span style="color:red;font-weight:normal;">${ fn:length(registrants)}</span>
					</c:when>
					<c:when test="${sy ne null}">
						${sy} ${sch ne null ? sch.schoolName : ""} ${ss ne null ? ss.text : ""} <br/> Total: <span style="color:red;font-weight:normal;">${ fn:length(registrants)}</span>
					</c:when>
				</c:choose> 
				<br/>
				English: <span style="color:red;font-weight:normal;" class="englishCountVal">-</span> &middot; French: <span style="color:red;font-weight:normal;" class="frenchCountVal">-</span>
	</div>			
  		</div>
  	<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;"><img src="../../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading Registrant Data, please wait...</div>		
						<br/>
		<div class='divResponseMsg alert alert-danger' align='center' style="display:none;"></div>
		
		<div style="display:none;" class="loadPage">  
		
			<div align='center' class="no-print">
		
			<c:if test="${krp ne null}">
				<input id='btnSendPhysicalAddressProofReminder' type='button' class='btn btn-sm btn-warning'  value='Send Proof of Physical Address Reminder' />
			</c:if>
			<c:if test="${sy ne null and sch ne null}">
				<a class='btn btn-sm btn-primary' href="<c:url value='/schools/registration/kindergarten/admin/district/processRegistrations.html?sy=${sy}&sid=${sch.schoolID}&ss=${ss.value}' />">Process Registrations</a>
			</c:if>			
			
			
			<a onclick="loadingData();" class='btn btn-sm btn-danger' href="<c:url value='/schools/registration/kindergarten/admin/district/index.html' />">Back to Registration Period List</a>
		</div>
		<br/>
			<div align="center" class="no-print" style="margin-bottom:10px;">	  				
						<a href="#" class="resetSort btn btn-sm btn-primary"  title="Resorts the table to default sorting.">
	  					<i class="fas fa-sync"></i> RESET SORT</a> 
	  					<a href="#" class="sortSchoolName btn btn-sm btn-warning" title="Sort Table by School and Student Name.">
	  					<i class="fas fa-sync"></i> SORT BY SCHOOL & STUDENT</a> 
	  	</div>
					<div style="clear:both;"></div>
			
			<table class="registrationTable table table-sm table-bordered responsive" width="100%" style="font-size:11px;background-color:White;">
			<thead class="thead-dark">
					<tr>
					<th width="*">SORT</th>
					<th width="7%">DATE</th>
					<th width="8%">TIME</th>
					<th width="8%">MCP</th>
					<th width="18%">NAME</th>
					<th width="18%">SCHOOL</th>
					<th width="7%">STREAM</th>
					<th width="5%">APPROVED</th>
					<th width="8%">STATUS</th>
					<th width="15%">OPTIONS</th>
				</tr>
				</thead>
				<tbody>
				<c:choose>
					<c:when test="${fn:length(registrants) gt 0 }">
						<c:forEach items="${registrants}" var="r">
							<tr class='period-data-row'>								
								<td width="*"><fmt:formatDate value="${r.registrationDate}" pattern="yyyyMMddHHmmss" /></td>
								<td width="7%"><fmt:formatDate value="${r.registrationDate}" pattern="MM/dd/yyyy" /></td>
								<td width="8%"><fmt:formatDate value="${r.registrationDate}" pattern="hh:mm:ssa" /></td>
								<td width="8%">${r.mcpNumber}</td>
								<td width="18%">${r.studentFullName}</td>
								<td width="18%">${r.school.schoolName}</td>
								
								<c:choose>
								<c:when test="${r.schoolStream.text eq 'ENGLISH'}">
								<c:set var="englishCount" value="${englishCount+1}"/>
								<td width="7%" style="background-color:#FFF8DC;text-align:center;">${r.schoolStream.text}</td>
								</c:when>
								<c:otherwise>
								<c:set var="frenchCount" value="${frenchCount+1}"/>
								<td width="7%" style="background-color:#6495ED;text-align:center;color:White;">${r.schoolStream.text}</td>
								</c:otherwise>
								</c:choose>
								
								<td width="5%" align='center'>${r.physicalAddressApproved ? "<span style='font-weight:bold;color:green;'><i class='fas fa-check'></i> Yes</span>" : "<span style='font-weight:bold;color:red;'><i class='fas fa-times'></i> No</span>"}</td>
								<td width="8%">${r.status.accepted eq true ? "<span style='font-weight:bold;color:green;'><i class='fas fa-check'></i> Accepted</span>" : r.status.waitlisted eq true ? "<span style='font-weight:bold;color:orange;'><i class='far fa-hourglass'></i> Waitlisted</span>" : "<span style='font-weight:bold;color:#9E7BFF;'><i class='fas fa-cog'></i> Processing</span>"}</td>
								<td width="15%" align="center">
									<a onclick="loadingData();" class='btn btn-xs btn-primary' href="<c:url value='/schools/registration/kindergarten/admin/district/viewRegistrant.html?kr=${r.registrantId}' />"><i class="far fa-eye"></i> VIEW</a> <a onclick="loadingData();" class='btn btn-xs btn-warning' href="<c:url value='/schools/registration/kindergarten/admin/district/editRegistrant.html?kr=${r.registrantId}' />"><i class="far fa-edit"></i> EDIT</a> <a onclick="loadingData();" class='btn btn-xs btn-danger opbutton-delete' href="<c:url value='/schools/registration/kindergarten/admin/district/deleteRegistrant.html?kr=${r.registrantId}' />"><i class="fas fa-times"></i> DEL</a>
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
							<td>N/A</td>
						</tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
	
		<br />
		<div align='center' class="no-print">
			<c:if test="${krp ne null}">
				<input id='btnSendPhysicalAddressProofReminder' type='button' class='btn btn-sm btn-warning'  value='Send Proof of Physical Address Reminder' />
			</c:if>
			<c:if test="${sy ne null and sch ne null}">
				<a class='btn btn-sm btn-primary' href="<c:url value='/schools/registration/kindergarten/admin/district/processRegistrations.html?sy=${sy}&sid=${sch.schoolID}&ss=${ss.value}' />">Process Registrations</a>
			</c:if>
			<a onclick="loadingData();" class='btn btn-sm btn-danger' href="<c:url value='/schools/registration/kindergarten/admin/district/index.html' />">Back to Registration Period List</a>
			
		</div>
		<div class='divResponseMsg alert alert-danger' style='display:none;' align='center'></div>
		<br />
			</div>
			
			<script>
	//RESORT THE TABLE
    		
    		$('.resetSort').click( function() {
    			
    			mTable.fnSort([0,"asc"]);    		
    			
    			$(".loadingTable").css("display","block").removeClass("alert-danger").addClass("alert-success").text("Table Data has been sorted by the default settings.").delay(5000).fadeOut();
		    });
    		$('.sortSchoolName').click( function() {
    			mTable.fnSort([[5,"asc"],[4,"asc"]]);    		
    			
    			$(".loadingTable").css("display","block").removeClass("alert-danger").addClass("alert-success").text("Table Data has been sorted by SCHOOL and STUDENT NAME.").delay(5000).fadeOut();
		    });
    		
    		$('document').ready(function(){
    		
    		$(".englishCountVal").html("${englishCount}");
    		$(".frenchCountVal").html("${frenchCount}");
    		
    		
    	//	str = str.replace(/\d(?=\d{4})/g, "*");
    		
    		
    		});
			</script>
			
			
			
	</body>
	
</html>