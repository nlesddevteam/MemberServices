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
    <TITLE>District Kindergarten Administration</title>

    
    <script>
    	$(function(){
    		
    		$('#frm-reg-caps').submit(function(){
    			$('#btnSaveCaps').attr("disabled", "disabled");	
    			$('#btnSaveCaps').val("Saving...");	
    			
    			return true;
    		});
    		
    		
    		//$('input[type=text], select').css({'border' : 'solid 1px #7B9EBD', 'background-color' : '#FFFFCC'});
    	});
    	
    	
    	
    	$('document').ready(function(){
    		
    		
    		mTable = $(".registrationCapsTable").dataTable({
    			"order" : [[0,"asc"]],		
    			"bPaginate": false,
    			responsive: true,
    			dom: 'Bfrtip',
    	        buttons: [			        	
    	        	//'colvis',
    	        	{
                    extend: 'print',                   
                    title: '<div align="center"><img src="/MemberServices/schools/registration/kindergarten/includes/img/nlesd-colorlogo.png" style="max-width:600px;"/></div>',
                    messageTop: '<div align="center" style="font-size:18pt;">${sy} Kinderstart/Kindergarten Registration Caps</div>', 
                    messageBottom: '<br/><b>TOTAL REGISTERED:</b> ${totalreg}<br/><b>ENGLISH:</b>  ${totaleng}<br/><b>ENGLISH WAITLISTED:</b> ${englishw}<br/><b>FRENCH:</b> ${totalfr}<br/><b>FRENCH WAITLISTED:</b> ${frenchw} <br/><br/><div class="alert alert-danger"><b>Confidentiality Warning:</b> This document and any attachments are intended for the sole use of the intended recipient(s), and contain privileged and/or confidential information. If you are not an intended recipient, any review, retransmission, printing, copying, circulation or other use of this message and any attachments is strictly prohibited.</div>',
                    	 exportOptions: {
                             columns: [ 0,1,2,4,5,6,8,9 ],
                         }
                },
                {
                    extend: 'csv',
                    exportOptions: {
                        columns: [ 0,1,2,4,5,6,8,9 ],
                    }
                },
                {
                    extend: 'excel',
                    exportOptions: {
                        columns: [ 0,1,2,4,5,6,8,9 ],
                    }
                },    	
    	        ],				
    			 "columnDefs": [
    				 {
    		                "targets": [3,7],			               
    		                "searchable": false,
    		                "orderable": false
    		            },
    		            {
    		                "targets": [4,8],			               
    		                "searchable": false,
    		                "orderable": false,
    		                "visible":false,
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
  </head>

  <body>
  
  <div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;"><img src="../../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading Registrant Data, please wait...</div>		
	
  	<div style="display:none;" class="loadPage">  
	<div align='center' style='font-size:14pt;font-weight:bold;color:#004178;;padding-bottom:15px;'>
	  	${sy} School Kindergarten Registrant Cap Summary
  	</div>
		<div>
			<form id='frm-reg-caps' method='post' action="<c:url value='/schools/registration/kindergarten/admin/district/updateSchoolRegistrationCaps.html'/>">
				<input type='hidden' name='school_year' value='${sy}' />
				<div align='center' class='no-print'>
					<input id='btnSaveCaps' class='btn btn-primary btn-sm' type="submit" value='Save Registrant Caps' />
					<a onclick="loadingData();" class='btn btn-sm btn-danger' href="<c:url value='/schools/registration/kindergarten/admin/district/index.html' />">Back to Registration Period List</a>
				</div>
				<br/>
				<table id='data-table' class="registrationCapsTable table table-sm table-bordered table-striped responsive" width="100%" style="font-size:12px;background-color:White;">
					<thead class="thead-dark">									
					<tr>
					<!-- See duplicates below? No worries. One prints for output, other for screen only. Set by Datatables -->
						<th>SCHOOL</th>
						<th>TOTAL</th>
						<th>ENGLISH REGISTERED</th>
						<th>ENGLISH ACCEPTED</th>
						<th>ENGLISH ACCEPTED</th>
						<th>ENGLISH WAITLISTED</th>
						<th>FRENCH REGISTERED</th>
						<th>FRENCH ACCEPTED</th>
						<th>FRENCH ACCEPTED</th>
						<th>FRENCH WAITLISTED</th>
					</tr>
					</thead>
					<tbody>
					<c:choose>
						<c:when test="${fn:length(caps) gt 0 }">
							<c:forEach items="${caps}" var="c">
								<tr class='period-data-row'>
									<td>
										<input type="hidden" name="school_id" value='${c.school.schoolID}' />
										${c.school.schoolName}
									</td>
									<td>${c.summary.totalRegistrants}</td>
									<td>${c.summary.englishRegistrants}</td>
									<td>
										<input type="text" name="english_cap_${c.school.schoolID}" value="${c.englishCap}" style='width:50px;' class='required' />
									</td>
									<td>${c.englishCap}</td>
									<td>${((c.summary.englishRegistrants - c.englishCap) < 0) ? 0 : (c.summary.englishRegistrants - c.englishCap) }</td>
									<td>${c.summary.frenchRegistrants}</td>
									<td>
										<input type="text" name="french_cap_${c.school.schoolID}" value="${c.frenchCap}" style='width:50px;' />
									</td>
									<td>${c.frenchCap}</td>
									<td>${((c.summary.frenchRegistrants - c.frenchCap) < 0) ? 0 : (c.summary.frenchRegistrants - c.frenchCap)}</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
						<!-- Datatables require all columns. NO COLSPAN allowed. -->
							<tr>
								<td>No cap data found.</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</c:otherwise>
					</c:choose>
					</tbody>
					<tfoot style="background-color:#343a40;color:White;font-size:16px;">
					<tr style=";font-size:12px;">
						<td>SCHOOL</th>
						<td>TOTAL</th>
						<td>ENGLISH REGISTERED</td>
						<td>ENGLISH ACCEPTED</td>
						<td>ENGLISH ACCEPTED</td>
						<td>ENGLISH WAITLISTED</td>
						<td>FRENCH REGISTERED</td>
						<td>FRENCH ACCEPTED</td>
						<td>FRENCH ACCEPTED</td>
						<td>FRENCH WAITLISTED</td>
					</tr>
					<tr>
						<td style='font-weight: bold; text-align:right;'>TOTALS: </td>
						<td style='font-weight: bold;'>${totalreg}</td>
						<td style='font-weight: bold;'>${totaleng}</td>
						<td style='font-weight: bold;'>&nbsp;</td>
						<td style='font-weight: bold;'>&nbsp;</td>
						<td style='font-weight: bold;'>${englishw}</td>
						<td style='font-weight: bold;'>${totalfr}</td>
						<td style='font-weight: bold;'>&nbsp;</td>
						<td style='font-weight: bold;'>&nbsp;</td>
						<td style='font-weight: bold;'>${frenchw}</td>
					</tr>
					
					</tfoot>
				</table>
				<br />
				<div align='center' class='no-print'>
					<input id='btnSaveCaps' class='btn btn-primary btn-sm' type="submit" value='Save Registrant Caps' />
					<a onclick="loadingData();" class='btn btn-sm btn-danger' href="<c:url value='/schools/registration/kindergarten/admin/district/index.html' />">Back to Registration Period List</a>
				</div>
				
			</form>
		</div>
</div>
	</body>
	
</html>