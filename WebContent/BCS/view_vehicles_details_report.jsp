<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,                 
                 com.awsd.common.*,com.esdnl.util.*,
                 org.apache.commons.lang.StringUtils,
                 org.apache.commons.lang.StringUtils.*, 
                 java.util.*,
                 java.io.*,
                 java.text.*,
                 java.sql.*"
        isThreadSafe="false"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<c:set var="countVeh" value="0" />
<script src="includes/js/jquery.min.js"></script>
<script src="includes/js/bootstrap.min.js"></script>
<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<script src="includes/js/bootstrapvalidator.min.js"></script>
<link href="includes/css/bootstrap-datepicker3.css" rel="stylesheet" type="text/css">
<script src="includes/js/bootstrap-datepicker.min.js"></script>
<script src="includes/js/jquery.dataTables.min.js"></script>
<script src="includes/js/dataTables.bootstrap.min.js"></script>
<script src="includes/js/buttons.print.min.js"></script>
<script src="includes/js/buttons.html5.min.js"></script>
<script src="includes/js/dataTables.buttons.min.js"></script>
<script src="includes/js/jszip.min.js"></script>
<script src="includes/js/pdfmake.min.js"></script>
<script src="includes/js/vfs_fonts.js"></script>
<script src="includes/js/bcs.js"></script>
<link rel="stylesheet" type="text/css" href="includes/css/dataTables.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="includes/css/buttons.dataTables.min.css">
<link href="includes/css/bcs.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
$(document).ready(function() {
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");
    			//$("#reportdata").dataTable();
    			$('#reportdata').DataTable({
    				  dom: 'Bfrtip',
    				  scrollY:        300,
    				  scrollX:        true,
    			      scrollCollapse: true,
    			      paging:         false,
    				  buttons: [
    				    'copyHtml5', 'excelHtml5', 'csvHtml5','print'
    				  ]
    				} );
    			
});
		</script>
		<script>
			$(document).ready(function(){    
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");
    		   
        	});
		</script>
		
		
		
	<%pageContext.setAttribute("now", new java.util.Date()); %>   		
	<div id="printJob">	
		
		<div class="BCSHeaderText">View ${companyname} Vehicles</div>			 
			  <br />
			   <div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    			<div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
			 <div id="BCS-Search">
			 Below is a table of the <b>${companyname} Vehicle(s)</b> data as requested. Depending on the data returned, you may need to scroll to see other data column and/or row fields. 
			 If you require to sort data in a column, you can click on the arrows located to the right in each column title header. 
			 To export this data to Excel, CSV or to print/copy, you can select either option below to download a copy to open or print.
		
					 
			  	<div id="divtable" style="max-width:100%;padding-top:10px;">
				<table id="reportdata" class="table table-striped table-bordered dt-responsive nowrap BCSTable">
				<thead>
				<tr style="background-color:#b3d9ff;">
				<th class="listdata">Contractor</th>
				<th class="listdata">Make</th>
				<th class="listdata">Make Other</th>
				<th class="listdata">Model</th>
				<th class="listdata">Year</th>
				<th class="listdata">Serial Number</th>
				<th class="listdata">Plate Number</th>
				<th class="listdata">Type</th>
				<th class="listdata">Size</th>
				<th class="listdata">Wheelchair Accessible</th>
				<th class="listdata">Owner</th>
				<th class="listdata">Registration Expiry Date</th>
				<th class="listdata">Insurance Expiry Date</th>
				<th class="listdata">Insurance Provider</th>
				<th class="listdata">Insurance Policy Number</th>
				<th class="listdata">Unit Number</th>
				<th class="listdata">Primary CMVI Date</th>
				<th class="listdata">Primary CMVI Certificate #</th>
				<th class="listdata">Primary CMVI Official Inspection Station</th>
				<th class="listdata">Secondary CMVI Date</th>
				<th class="listdata">Secondary CMVI Certificate #</th>
				<th class="listdata">Secondary CMVI Inspection Station</th>
				<th class="listdata">Fall HE Inspection Date</th>
				<th class="listdata">Misc HE Inspection Date 1</th>
				<th class="listdata">Misc HE Inspection Date 2</th>
				<th class="listdata">Status</th>
				<th class="listdata">Status By</th>
				<th class="listdata">Status Date</th>
				</thead>
				<tbody>
				<c:choose>
	      		<c:when test="${fn:length(vehicles) > 0}">
		      		<c:forEach items="${vehicles}" var="rule">
	 					<c:set var="countVeh" value="${countVeh + 1}" />
	 					<tr>
	 						<td class="field_content">${rule.bcBean.contractorName}</td>
	      					<td class="field_content">${rule.makeText}</td>
	      					<td class="field_content">${rule.vMakeOther}</td>
	      					<td class="field_content">${rule.vModel2}</td>
	      					<td class="field_content">${rule.vYear}</td>
	      					<td class="field_content">${rule.vSerialNumber}</td>
	      					<td class="field_content">${rule.vPlateNumber}</td>
	      					<td class="field_content">${rule.typeText}</td>
	      					<td class="field_content">${rule.sizeText}</td>
	      					<td class="field_content">${rule.vModel eq 1 ? 'Yes' : 'No'}</td>
	      					<td class="field_content">${rule.vOwner}</td>
	      					<td class="field_content">${rule.regExpiryDateFormatted}</td>
	      					<td class="field_content">${rule.insExpiryDateFormatted}</td>
	      					<td class="field_content">${rule.insuranceProvider}</td>
	      					<td class="field_content">${rule.insurancePolicyNumber}</td>
	      					<td class="field_content">${rule.unitNumber}</td>
	      					<td class="field_content">${rule.fallInsDateFormatted}</td>
	      					<td class="field_content">${rule.fallCMVI}</td>
	      					<td class="field_content">${rule.fallInsStation}</td>
	      					<td class="field_content">${rule.winterInsDateFormatted}</td>
	      					<td class="field_content">${rule.winterCMVI}</td>
	      					<td class="field_content">${rule.winterInsStation}</td>
	      					<td class="field_content">${rule.fallHeInsDateFormatted}</td>
	      					<td class="field_content">${rule.miscHeInsDate1Formatted}</td>
	      					<td class="field_content">${rule.miscHeInsDate1Formatted}</td>
	      					<td class="field_content">${rule.statusText}</td>
	      					<td class="field_content">${rule.approvedBy}</td>
	      					<td class="field_content">${rule.dateApprovedFormatted}</td>
	      				</tr>
	        		</c:forEach>
	        	</c:when>
	        	<c:otherwise>
	        		<tr><td colspan='5' style="color:Red;">No vehicles found.</td></tr>
	        	</c:otherwise>
	        </c:choose>
				</tbody>
				</table>
				</div>

		
	</div></div>
	<c:choose>
      	<c:when test="${countVeh >0 }">
      		<script>$('#details_success_message').html("There are <b>${countVeh}</b> vehicle entries found for this contractor.").css("display","block").delay(6000).fadeOut();
      		
      		</script>	
      	</c:when>
      	<c:otherwise>
      		<script>$('#BCS-Search').css("display","none");$('#details_error_message').html("Sorry, there are no vehicle entries for the contractor <b>${companyname}</b> at this time.").css("display","block");</script>
      	</c:otherwise>
      	</c:choose>