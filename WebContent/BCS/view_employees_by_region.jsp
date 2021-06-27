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
<c:set var="countEmp" value="0" />
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
    			      paging:         true,
    				  buttons: [
    				    //'copyHtml5', 'excelHtml5', 'pdfHtml5', 'csvHtml5','print'
    				    'copyHtml5', 'excelHtml5',  'csvHtml5','print'
    				  ]
    				} );
    			
});
		</script>
		<script>
			$(document).ready(function(){    
        		//clear spinner on load
        		
    			$('#loadingSpinner').css("display","none");
    		    $("#reportdata tr:even").not(':first').css("background-color", "#FFFFFF");
    		    $("#reportdata tr:odd").css("background-color", "#f2f2f2");
        	});</script>
		
		
	<%pageContext.setAttribute("now", new java.util.Date()); %>   		
	<div id="printJob">	
		 <div class="BCSHeaderText">View ${companyname} Employees</div>			 
			  <br />
			   <div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    		<div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
			 <div id="BCS-Search">
			 Below is a table of the <b>${companyname} Employee(s)</b> data as requested. Depending on the data returned, you may need to scroll to see other data column and/or row fields. 
			 If you require to sort data in a column, you can click on the arrows located to the right in each column title header. 
			 To export this data to Excel, CSV or to print/copy, you can select either option below to download a copy to open or print.
			
			 
			  	<div id="divtable" style="max-width:100%;padding-top:10px;">	
			  	<table id="reportdata" class="table table-striped table-bordered dt-responsive nowrap BCSTable">
				<thead>
				<tr style="background-color:#b3d9ff;">
				<th class="listdata">Contractor</th>
				<th class="listdata">Last Name</th>
				<th class="listdata">First Name</th>
				<th class="listdata">Middle Name</th>
				<th class="listdata">Date of Birth</th>
				<th class="listdata">Position</th>
				<th class="listdata">Start Date</th>
				<th class="listdata">Continuous Service</th>
				<th class="listdata">Email</th>
				<th class="listdata">Address 1</th>
				<th class="listdata">Address 2</th>
				<th class="listdata">City</th>
				<th class="listdata">Province</th>
				<th class="listdata">Postal Code</th>
				<th class="listdata">Home Phone</th>
				<th class="listdata">Cell Phone</th>
				<th class="listdata">DL Number</th>
				<th class="listdata">DL Class</th>
				<th class="listdata">DL Expiry</th>
				<th class="listdata">DA Run Date</th>
				<th class="listdata">Demerit Points</th>
				<th class="listdata">DA Convictions</th>
				<th class="listdata">Dangerous Driving 5 YRS</th>
				<th class="listdata">DA Suspensions</th>
				<th class="listdata">Suspensions 36 MNTHS</th>
				<th class="listdata">At Fault Accidents</th>
				<th class="listdata">FA Expiry Date</th>
				<th class="listdata">FA Level C</th>
				<th class="listdata">PCC Date</th>
				<th class="listdata">SCA Date</th>
				<th class="listdata">PRCVSQ Date</th>
				<th class="listdata">Findings of Guilt</th>
				<th class="listdata">DL Front</th>
				<th class="listdata">DL Back</th>
				<th class="listdata">DA Document</th>
				<th class="listdata">FA Document</th>
				<th class="listdata">PRCVSQ Document</th>
				<th class="listdata">PCC Document</th>
				<th class="listdata">SCA Document</th>
				<th class="listdata">CODDocument</th>
				<th class="listdata">COD Date Signed</th>
				<th class="listdata">Status</th>
				<th class="listdata">Status By</th>
				<th class="listdata">Status Date</th>
				<th class="listdata">Region</th>
				<th class="listdata">Depot</th>
				</thead>
				<tbody>
				<c:choose>
	      		<c:when test="${fn:length(employees) > 0}">
		      		<c:forEach items="${employees}" var="rule">
		      		<c:set var="countEmp" value="${countEmp + 1}" />
	 					<tr>
	 						<td class="field_content">${rule.bcBean.contractorName}</td>
	      					<td class="field_content">${rule.lastName}</td>
	      					<td class="field_content">${rule.firstName}</td>
	      					<td class="field_content">${rule.middleName}</td>
	      					<td class="field_content">${rule.birthDateFormatted}</td>
	      					<td class="field_content">${rule.employeePositionText}</td>
	      					<td class="field_content">${rule.startDate}</td>
	      					<td class="field_content">${rule.continuousService}</td>
	      					<td class="field_content">${rule.email}</td>
	      					<td class="field_content">${rule.address1}</td>
	      					<td class="field_content">${rule.address2}</td>
	      					<td class="field_content">${rule.city}</td>
	      					<td class="field_content">${rule.province}</td>
	      					<td class="field_content">${rule.postalCode}</td>
	      					<td class="field_content">${rule.homePhone}</td>
	      					<td class="field_content">${rule.cellPhone}</td>
	      					<td class="field_content">${rule.dlNumber}</td>
	      					<td class="field_content">${rule.dlClassText}</td>
	      					<td class="field_content">${rule.dlExpiryDateFormatted}</td>
	      					<td class="field_content">${rule.daRunDateFormatted}</td>
	      					<td class="field_content">${rule.demeritPoints}</td>
	      					<td class="field_content">${rule.daConvictions}</td>
	      					<td class="field_content">${rule.dangerousDriving}</td>
	      					<td class="field_content">${rule.daSuspensions}</td>
	      					<td class="field_content">${rule.suspensions}</td>
	      					<td class="field_content">${rule.daAccidents}</td>
	      					<td class="field_content">${rule.faExpiryDateFormatted}</td>
	      					<td class="field_content">${rule.faLevelC}</td>
	      					<td class="field_content">${rule.pccDateFormatted}</td>
	      					<td class="field_content">${rule.scaDateFormatted}</td>
	      					<td class="field_content">${rule.prcvsqDateFormatted}</td>
	      					<td class="field_content">${rule.findingsOfGuilt}</td>
	      					<td class="field_content">${rule.dlFront}</td>
	      					<td class="field_content">${rule.dlBack}</td>
	      					<td class="field_content">${rule.daDocument}</td>
	      					<td class="field_content">${rule.faDocument}</td>
	      					<td class="field_content">${rule.prcvsqDocument}</td>
	      					<td class="field_content">${rule.pccDocument}</td>
	      					<td class="field_content">${rule.scaDocument}</td>
	      					<td class="field_content">${rule.codDocument}</td>
	      					<td class="field_content">${rule.codExpiryDateFormatted}</td>
	      					<td class="field_content">${rule.statusText}</td>
	      					<td class="field_content">${rule.approvedBy}</td>
	      					<td class="field_content">${rule.dateApprovedFormatted}</td>
	      					<td class="field_content">${rule.regionBean ne null ? rule.regionBean.regionName : ''}</td>
	      					<td class="field_content">${rule.regionBean ne null ? rule.regionBean.depotName : ''}</td>
	      				</tr>
	        		</c:forEach>
	        	</c:when>
	        	<c:otherwise>
	        		<tr><td colspan='5' style="color:Red;">No employees found.</td></tr>
	        	</c:otherwise>
	        </c:choose>
				</tbody>
				</table>		  	

				</div>
				</div>

		</div>
	<c:choose>
      	<c:when test="${countEmp >0 }">
      		<script>$('#details_success_message').html("There are <b>${countEmp}</b> employee entries found for this contractor.").css("display","block").delay(6000).fadeOut();      		
      		</script>	
      	</c:when>
      	<c:otherwise>
      		<script>$('#BCS-Search').css("display","none");$('#details_error_message').html("Sorry, there are no employee entries for the contractor <b>${companyname}</b> at this time.").css("display","block");</script>
      	</c:otherwise>
      	</c:choose>