<%@ page language="java" isThreadSafe="false"%>

<%@ taglib prefix="sreg" uri="/WEB-INF/school_registration.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
                 
<esd:SecurityCheck permissions="ICF-REGISTRATION-ADMIN-EXPORT" />

<html>
  
  <head>
   
    <TITLE>Export ICF Registrants</title>
    
    <script>
$('document').ready(function(){		

		
		mTable = $(".registrationTable").dataTable({
			"order" : [[1,"asc"],[0,"asc"]],		
			"bPaginate":false,			
			responsive: true,
			dom: 'Bfrtip',
	        buttons: [			        	
	        	//'colvis',
	        	{
	        	
                extend: 'print',
                title: '<div align="center"><img src="/MemberServices/schools/registration/icfreg/admin/includes/img/nlesd-colorlogo.png" style="max-width:600px;"/></div>',
                messageTop: '<div align="center" style="font-size:18pt;">${sy} Intensive Core French (ICF) Registrants</div>',
                messageBottom: '<div class="alert alert-warning"><b>Confidentiality Warning:</b> This document and any attachments are intended for the sole use of the intended recipient(s), and contain privileged and/or confidential information. If you are not an intended recipient, any review, retransmission, printing, copying, circulation or other use 	of this message and any attachments is strictly prohibited.</div>',
                	 
            },
            { 
       		 extend: 'excel',	
       		
       },
       { 
     		 extend: 'csv',	
     		 
     },
         
	        ],				
			 
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

  </head>

  <body>
 
		<div align='center' style='font-size:14pt;font-weight:bold;color:#004178;padding-bottom:15px;'>
	  	${regbean.icfRegPerSchoolYear} ICF Registrants<br/> for Registration Period ${regbean.getStartDateFormatted()} to ${regbean.getEndDateFormatted()}<br/>
	  	Total: <span style="color:red;font-weight:normal;" id="spancount">${regbean.icfRegCount}</span>
		</div>			
  		
  		<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;"><img src="/MemberServices/schools/registration/icfreg/admin/includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading Registrant Data, please wait...</div>		
		
		<div style="display:none;" class="loadPage">  
		
			<div align='center' class="no-print">
			<a href='#'
			class="no-print noJump btn btn-sm btn-warning"
			title='Print this page (pre-formatted)'
			onclick="jQuery('#printJob').print({prepend : '<div align=center><img width=400 src=/MemberServices/schools/registration/icfreg/admin/includes/img/nlesd-colorlogo.png></div><br/>'});"><i
			class="fas fa-print"></i> Print this Page</a>
			
			
				<a onclick="window.history.go(-1);" class='btn btn-sm btn-danger'>Back to Registration Period List</a>
			</div>
			<br/>
			
			
			<table class="registrationTable table table-sm table-bordered responsive" width="100%" style="font-size:11px;background-color:White;" id="registrationTable">
			<thead class="thead-dark">
					<tr>					
					<th width="15%">SUBMITTED</th>
					<th width="15%">SCHOOL (DEPT ID)</th>
					<th width="15%">APPLICANT NAME</th>				
					<th width="15%">PARENT/GUARDIAN</th>
					<th width="15%">EMAIL</th>
					<th width="10%">TEL#</th>
					<th width="10%">OTHER TEL#</th>
					<th width="5%">STATUS</th>
				</tr>
				</thead>
				<tbody>
				<c:choose>
					<c:when test="${fn:length(reglist) gt 0 }">
						<c:forEach items="${reglist}" var="r">
							<tr class='period-data-row'>
								<!-- <td width="5%">${regbean.icfRegPerSchoolYear}</td>-->								
								<td width="15%">
								<fmt:formatDate value="${r.icfAppDateSubmitted}" pattern="yyyy-MM-dd @ HH:mm aa" />
								<!-- ${r.getIcfAppDateSubmittedFormatted()}--></td>
								<td width="15%">${r.icfAppSchoolName} (${r.icfAppSchool})</td>
								<td width="15%">${r.icfAppFullName}</td>							
								<td width="15%">${r.icfAppGuaFullName}</td>
								<td width="15%">${r.icfAppEmail}</td>
								<td width="10%">${r.icfAppContact1}</td>
								<td width="10%">${r.icfAppContact2 eq null?'N/A':icfAppContact2}</td>
								<td width="5%">${r.getApplicantStatusString()}</td>
								
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
						</tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
	
		
			</div>
		
			
			
			
			
	</body>
	
</html>