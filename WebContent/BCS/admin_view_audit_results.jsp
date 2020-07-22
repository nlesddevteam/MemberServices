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
		 <div class="BCSHeaderText">View Audit Entries</div>
		 <div class="BCSHeaderText">${rtype} : ${bcname} Between ${sdate} And ${edate}</div>			 
			  <br />
			   <div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    		<div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
			 <div id="BCS-Search">
				Depending on the data returned, you may need to scroll to see other data column and/or row fields. 
			 If you require to sort data in a column, you can click on the arrows located to the right in each column title header. 
			 To export this data to Excel, CSV or to print/copy, you can select either option below to download a copy to open or print.
			
			 
			  	<div id="divtable" style="max-width:100%;padding-top:10px;">	
			  	<table id="reportdata" class="table table-striped table-bordered dt-responsive nowrap BCSTable">
				<thead>
				<tr style="background-color:#b3d9ff;">
				<th class="listdata">Contractor</th>
				<th class="listdata">Date</th>
				<th class="listdata">Notes</th>
				
				</thead>
				<tbody>
				<c:choose>
	      		<c:when test="${fn:length(auditentries) > 0}">
		      		<c:forEach items="${auditentries}" var="rule">
								      		
	 					<tr>
	 						<td class="field_content">${rule.bcBean.contractorName}</td>
	      					<td class="field_content">${rule.entryDateFormatted}</td>
	      					<td class="field_content">${rule.entryNotes}</td>
				
	      					
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
