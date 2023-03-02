<%@ page language="java" isThreadSafe="false"%>

<%@ taglib prefix="sreg" uri="/WEB-INF/school_registration.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
                 
<esd:SecurityCheck permissions="ICF-REGISTRATION-ADMIN-VIEW,ICF-REGISTRATION-SCHOOL-VIEW" />

<html>
  
  <head>
   
    <TITLE>View ICF Registrants by School</title>
 
    <script>
$('document').ready(function(){		

		
		mTable = $(".registrationTable").dataTable({
			"order" : [[0,"asc"],[1,"asc"]],		
			"bPaginate":false,			
			responsive: true,
			dom: 'Bfrtip',
	        buttons: [			        	
	        	//'colvis',
	        	{
	        	
                extend: 'print',
                title: '<div align="center"><img src="/MemberServices/schools/registration/icfreg/admin/includes/img/nlesd-colorlogo.png" style="max-width:600px;"/></div>',
                messageTop: "<div align=\"center\" style=\"font-weight:bold;font-size:16pt;\"><br/>${regbean.icfRegPerSchoolYear} ${sname} Intensive Core French (ICF) Registrants</div>",
                messageBottom: '<div class="alert alert-danger"><b>Confidentiality Warning:</b> This document and any attachments are intended for the sole use of the intended recipient(s), and contain privileged and/or confidential information. If you are not an intended recipient, any review, retransmission, printing, copying, circulation or other use 	of this message and any attachments is strictly prohibited.</div>',
                	 exportOptions: {
                		 columns: [ 0,1,2,3,4,5],
                     }
            },
            { 
       		 extend: 'excel',	
       		 exportOptions: {         		           		 
					columns: [0,1,2,3,4,5],
                 },
       },
       { 
     		 extend: 'csv',	
     		 exportOptions: {        		             		 
					 columns: [ 0,1,2,3,4,5],
               },
     },
         
	        ],				
			 "columnDefs": [
				 {
		                "targets": [6],			               
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
 
		<div align='center' style='font-size:14pt;font-weight:bold;color:#004178;;padding-bottom:15px;'>
	  	ICF Registrants - ${sname}<br/> <input type="hidden" id="hidsid" value="${sid}"> 
  	 	<div style="font-size:14px;">
  			${regbean.icfRegPerSchoolYear} Registration Period<br/>${regbean.getStartDateFormatted()} - ${regbean.getStartDateFormatted()}<br/>Total: <span style="color:red;font-weight:normal;" id="spancount">${fn:length(reglist)}</span>
		</div>			
  		</div>
  		<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;"><img src="../../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading Registrant Data, please wait...</div>		
		
		
		<div style="display:none;" class="loadPage">  
		
			<div align='center' class="no-print">
				<a onclick="loadingData();" class='btn btn-sm btn-success' href="/MemberServices/schools/registration/icfreg/admin/adminAddNewApplicant.html?irp=${regbean.icfRegPerId}">Add New Registrant</a>
				<a onclick="window.history.go(-1);" class='btn btn-sm btn-danger' >Back to Registration List</a>
			</div>
			<br/>
			
			
			<table class="registrationTable table table-sm table-bordered responsive" width="100%" style="font-size:11px;background-color:White;" id="registrationTable">
			<thead class="thead-dark">
					<tr>
					<th width="10%">DATE</th>
					<th width="10%">TIME</th>
					<th width="25%">APPLICANT</th>
					<th width="10%">TEL</th>
					<th width="20%">EMAIL</th>
					<th width="10%">STATUS</th>
					<th width="15%">OPTIONS</th>
				</tr>
				</thead>
				<tbody>
				<c:choose>
					<c:when test="${fn:length(reglist) gt 0 }">
						<c:forEach items="${reglist}" var="r">
							<tr class='period-data-row'>								
								<td width="10%"><fmt:formatDate value="${r.icfAppDateSubmitted}" pattern="MM/dd/yyyy" /></td>
								<td width="10%"><fmt:formatDate value="${r.icfAppDateSubmitted}" pattern="HH:mm:ss a" /></td>
								<td width="25%">${r.icfAppFullName}</td>
								<td width="10%">${r.icfAppContact1}</td>
								<td width="20%">${r.icfAppEmail}</td>
								<td width="10%">${r.getApplicantStatusString()}</td>
								<td width="15%" align="center">
									<a onclick="loadingData();" class='btn btn-xs btn-primary' href="<c:url value='/schools/registration/icfreg/admin/viewRegistrant.html?irp=${r.icfAppId}&vtype=V' />">VIEW</a> 
									<a onclick="loadingData();" class='btn btn-xs btn-warning' href="<c:url value='/schools/registration/icfreg/admin/viewRegistrant.html?irp=${r.icfAppId}&vtype=A' />">EDIT</a> 
									<esd:SecurityAccessRequired permissions="ICF-REGISTRATION-ADMIN-DELETE">
									<a class='btn btn-xs btn-danger opbutton-delete' onclick='openDeleteConfirm("${r.icfAppId}","${r.icfAppFullName}","S");'>DEL</a>
									</esd:SecurityAccessRequired>
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
						</tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
	
		
			</div>
			<div class="modal fade bg-danger" id="deleteRegistrantModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="apsTitle"></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      <div class="container">
  		<div class="row" >
    		<div class="col-md" id="studentname">
    		
      		</div>
      		<br/>
  		</div>
  		<div class="row" >
    		<div id="divmessage" class="col-md">
      			<span id="spanmessage"></span>
    		</div>
  		</div>
  	</div>
      
      </div>
       
      
      <div class="modal-footer">
      <button type="button" class="btn btn-primary" id="btndeleteregistrant">Delete</button>
      	<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>	
			
			
			
			
	</body>
	
</html>