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
		
		<div class="BCSHeaderText">View Archive Records For ${vehname} [ ${conname} ]</div>			 
			  <br />
			   <div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    			<div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
			 <div id="BCS-Search">
			 Below is a table of the archive records for <b>${vehname} [ ${conname} ]</b> as requested. The first record is the current information for that vehicle.
			 Depending on the data returned, you may need to scroll to see other data column and/or row fields. 
			 If you require to sort data in a column, you can click on the arrows located to the right in each column title header. 
			 To export this data to Excel, CSV or to print/copy, you can select either option below to download a copy to open or print.
		
					 
			  	<div id="divtable" style="max-width:100%;padding-top:10px;">
				<table id="reportdata" class="table table-striped table-bordered dt-responsive nowrap BCSTable">
				<thead>
				<tr style="background-color:#b3d9ff;">
				<th class="listdata">Id</th>
				<th class="listdata">Contractor</th>
				<th class="listdata">Make</th>
				<th class="listdata">Make Other</th>
				<th class="listdata">Model</th>
				<th class="listdata">Year</th>
				<th class="listdata">Serial Number</th>
				<th class="listdata">Plate Number</th>
				<th class="listdata">Type</th>
				<th class="listdata">Size</th>
				<th class="listdata">Owner</th>
				<th class="listdata">Registration Expiry Date</th>
				<th class="listdata">Insurance Expiry Date</th>
				<th class="listdata">Insurance Provider</th>
				<th class="listdata">Insurance Policy Number</th>
				<th class="listdata">Unit Number</th>
				<th class="listdata">Fall Inspection Date</th>
				<th class="listdata">Fall CMVI Certificate #</th>
				<th class="listdata">Fall Offical Inspection Station</th>
				<th class="listdata">Winter Inspection Date</th>
				<th class="listdata">Winter CMVI Certificate #</th>
				<th class="listdata">Winter Offical Inspection Station</th>
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
	 						<td class="field_content">${countVeh}</td>
	 						<td class="field_content">${rule.value.bcBean.contractorName}</td>
	      					<td class="field_content">${rule.value.makeText}</td>
	      					<td class="field_content">${rule.value.vMakeOther}</td>
	      					<td class="field_content">${rule.value.vModel2}</td>
	      					<td class="field_content">${rule.value.vYear}</td>
	      					<td class="field_content">${rule.value.vSerialNumber}</td>
	      					<td class="field_content">${rule.value.vPlateNumber}</td>
	      					<td class="field_content">${rule.value.typeText}</td>
	      					<td class="field_content">${rule.value.sizeText}</td>
	      					<td class="field_content">${rule.value.vOwner}</td>
	      					<td class="field_content">${rule.value.regExpiryDateFormatted}</td>
	      					<td class="field_content">${rule.value.insExpiryDateFormatted}</td>
	      					<td class="field_content">${rule.value.insuranceProvider}</td>
	      					<td class="field_content">${rule.value.insurancePolicyNumber}</td>
	      					<td class="field_content">${rule.value.unitNumber}</td>
	      					<td class="field_content">${rule.value.fallInsDateFormatted}</td>
	      					<td class="field_content">${rule.value.fallCMVI}</td>
	      					<td class="field_content">${rule.value.fallInsStation}</td>
	      					<td class="field_content">${rule.value.winterInsDateFormatted}</td>
	      					<td class="field_content">${rule.value.winterCMVI}</td>
	      					<td class="field_content">${rule.value.winterInsStation}</td>
	      					<td class="field_content">${rule.value.fallHeInsDateFormatted}</td>
	      					<td class="field_content">${rule.value.miscHeInsDate1Formatted}</td>
	      					<td class="field_content">${rule.value.miscHeInsDate1Formatted}</td>
	      					<td class="field_content">${rule.value.statusText}</td>
	      					<td class="field_content">${rule.value.approvedBy}</td>
	      					<td class="field_content">${rule.value.dateApprovedFormatted}</td>
	      				</tr>
	        		</c:forEach>
	        	</c:when>
	        	<c:otherwise>
	        		<tr><td colspan='5' style="color:Red;">No archive records found.</td></tr>
	        	</c:otherwise>
	        </c:choose>
				</tbody>
				</table>
				</div>

		
	</div></div>
