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
    <title>Student Registration Summaries</title>
    
    <script>
	$('document').ready(function(){
		
		
		mTable = $(".registrationSummariesTable").dataTable({
			"order" : [[0,"asc"]],		
			"bPaginate": false,
			responsive: true,
			dom: 'Bfrtip',
	        buttons: [			        	
	        	//'colvis',
	        	{
                extend: 'print',             
                title: '<div align="center"><img src="/MemberServices/schools/registration/kindergarten/includes/img/nlesd-colorlogo.png" style="max-width:600px;"/></div>',
                messageTop: '<div align="center" style="font-size:18pt;">${sy} Kinderstart/Kindergarten Registration Summaries</div>',           
                messageBottom: '<br/><b>TOTAL REGISTERED:</b> ${totalreg}<br/><b>ENGLISH:</b>  ${totaleng}<br/><b>FRENCH:</b> ${totalfr}<br/><br/><div class="alert alert-danger"><b>Confidentiality Warning:</b> This document and any attachments are intended for the sole use of the intended recipient(s), and contain privileged and/or confidential information. If you are not an intended recipient, any review, retransmission, printing, copying, circulation or other use of this message and any attachments is strictly prohibited.</div>',
             
                
                	 exportOptions: {
                         columns: [ 0,1,2,3],
                     }
            },
            {
                extend: 'csv',
                exportOptions: {
                    columns: [ 0,1,2,3 ],
                }
            },
            {
                extend: 'excel',
                exportOptions: {
                    columns: [ 0,1,2,3 ],                    
                }
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
    
    
  </head>

  <body>
			<div align='center' style='font-size:14pt;font-weight:bold;color:#004178;;padding-bottom:15px;'>
	  	${sy} Kindergarten Registrant Summary
  	</div>
	  <div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;"><img src="../../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading Registrant Data, please wait...</div>		
	
  	<div style="display:none;" class="loadPage">  	
				<div align='center' class='no-print'>
			<a onclick="loadingData();" class='btn btn-danger btn-sm' href="<c:url value='/schools/registration/kindergarten/admin/district/index.html' />">Back to Registration Period List</a>
		</div>
		<br/>	
			<table class="registrationSummariesTable table table-sm table-bordered responsive" width="100%"  id='data-table' style="font-size:12px;background-color:White;">
					<thead class="thead-dark">
					<tr>							
				<tr>
					<th>SCHOOL</th>
					<th>TOTAL</th>
					<th>ENGLISH</th>
					<th>FRENCH</th>
				</tr>
				</thead>
				<tbody>
				<c:choose>
					<c:when test="${fn:length(summaries) gt 0 }">
						<c:forEach items="${summaries}" var="s">
							<tr class='period-data-row'>
								<td>${s.school.schoolName}</td>
								<td>${s.totalRegistrants}</td>
								<td>${s.englishRegistrants}</td>
								<td>${s.frenchRegistrants}</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td>No summary data found.</td>
						</tr>
					</c:otherwise>
				</c:choose>
				</tbody>
				<tfoot style="background-color:#343a40;color:White;font-size:16px;">
				<tr style="font-size:12px;">
					<td>SCHOOL</td>
					<td>TOTAL</td>
					<td>ENGLISH</td>
					<td>FRENCH</td>
				</tr>
				<tr>
					<td style='font-weight: bold;text-align:right;'>TOTALS: </td>
					<td style='font-weight: bold;'>${totalreg}</td>
					<td style='font-weight: bold;'>${totaleng}</td>
					<td style='font-weight: bold;'>${totalfr}</td>
				</tr>				
				</tfoot>
			</table>
			
		</div>
		<br />
		<div align='center' class='no-print'>
			<a onclick="loadingData();"  class='btn btn-danger btn-sm' href="<c:url value='/schools/registration/kindergarten/admin/district/index.html' />">Back to Registration Period List</a>
		</div>
		<br />
		
	</body>
	
</html>