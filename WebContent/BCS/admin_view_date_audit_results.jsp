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
		 <div class="BCSHeaderText">View Date Audit Entries</div>			 
			  <br />
			   <div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    		<div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
			 <div id="BCS-Search">
			 Below is a table of the data as requested. Depending on the data returned, you may need to scroll to see other data column and/or row fields. 
			 If you require to sort data in a column, you can click on the arrows located to the right in each column title header. 
			 To export this data to Excel, CSV or to print/copy, you can select either option below to download a copy to open or print.
			
			 
			  	<div id="divtable" style="max-width:100%;padding-top:10px;">	
			  	<table id="reportdata" class="table table-striped table-bordered dt-responsive nowrap BCSTable">
				<thead>
				<tr style="background-color:#b3d9ff;">
				<th class="listdata">Contractor</th>
				<c:choose>
					<c:when test="${datetype eq 1 }">
						<th class="listdata">Employee Name</th>
					</c:when>
					<c:otherwise>
						<th class="listdata">Plate Number(Serial Number)</th>
					</c:otherwise>
				</c:choose>
				<th class="listdata">Date Type</th>
				<th class="listdata">Old Value</th>
				<th class="listdata">New Value</th>
				<th class="listdata">Changed On</th>
				<th class="listdata">Changed By</th>
				<th class="listdata"></th>
				
				</thead>
				<tbody>
				<c:choose>
	      		<c:when test="${fn:length(auditentries) > 0}">
		      		<c:forEach items="${auditentries}" var="rule">
								      		
	 					<tr>
	 						<c:choose>
								<c:when test="${datetype eq 1 }">
									<td class="field_content">${rule.bceBean.bcBean.contractorName}</td>
									<td class="field_content">${rule.bceBean.lastName},${rule.bceBean.firstName}</td>
								</c:when>
								<c:otherwise>
									<td class="field_content">${rule.bcvBean.bcBean.contractorName}</td>
									<td class="field_content">${rule.bcvBean.vPlateNumber}(${rule.bcvBean.vSerialNumber})</td>
								</c:otherwise>
							</c:choose>	 						
	 						<td class="field_content">${rule.dateTypeString}</td>
	      					<td class="field_content">${rule.oldValueFormatted}</td>
							<td class="field_content">${rule.newValueFormatted}</td>
	      					<td class="field_content">${rule.dateChangedFormatted}</td>
	      					<td class="field_content">${rule.changedBy}</td>
	      					<td class="field_content"></td>
	      				</tr>
	        		</c:forEach>
	        	</c:when>
	        	<c:otherwise>
	        		<tr><td colspan='5' style="color:Red;">No entries found.</td></tr>
	        	</c:otherwise>
	        </c:choose>
				</tbody>
				</table>		  	

				</div>
				</div>

		</div>
