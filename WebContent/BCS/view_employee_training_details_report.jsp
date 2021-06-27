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
<c:set var="countEmp" value="0" />
<esd:SecurityCheck permissions="BCS-SYSTEM-ACCESS" />
	<script>
			$(document).ready(function(){    
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");

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

    		    $("#reportdata tr:even").not(':first').css("background-color", "#FFFFFF");
    		    $("#reportdata tr:odd").css("background-color", "#f2f2f2");
    		    
        	});</script>
		
		
		<script>
   			$(document).ready(function () {
    		$('.menuBCS').click(function () {
    		$("#loadingSpinner").css("display","inline");
    		});  
   			});
		</script>
		
		
	<div id="printJob">	
      
	<div class="BCSHeaderText">${reporttitle}</div>
	<div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
           <div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	
	<br/>
	       
 
    <div id="BCS-Search">
			  	<div id="divtable" style="max-width:100%;padding-top:10px;">	
			  	
			  	<table id="reportdata" class="table table-striped table-bordered dt-responsive nowrap BCSTable">
				<thead>
				<tr style="background-color:#b3d9ff;">
				<th class="listdata">Employee Last Name</th>
				<th class="listdata">Employee First Name</th>
				<th class="listdata">Position</th>
				<th class="listdata">Continuous Service</th>
				<th class="listdata">Status</th>
				<th class="listdata">Company</th>
				<th class="listdata">Contractor Last Name</th>
				<th class="listdata">Contractor First Name</th>
				<th class="listdata">Training Type</th>
				<th class="listdata">Training Length</th>
				<th class="listdata">Training Date</th>
				<th class="listdata">Expiry Date</th>
				<th class="listdata">Provided By</th>
				<th class="listdata">Location</th>
				</thead>
				<tbody>
				<c:choose>
	      		<c:when test="${fn:length(employees) > 0}">
		      		<c:forEach items="${employees}" var="rule">
		      		<c:set var="countEmp" value="${countEmp + 1}" />
	 					<tr>
	 					<td class="field_content">${rule.employeeLastName}</td>
	      					<td class="field_content">${rule.employeeFirstName}</td>
	      					<td class="field_content">${rule.employeePosition}</td>
	      					<td class="field_content">${rule.continuousService}</td>
	      					<td class="field_content">${rule.employeeStatus.description}</td>	      										
	      					<td class="field_content">${rule.companyName}</td>
	      					<td class="field_content">${rule.contractorLastName}</td>
							<td class="field_content">${rule.contractorFirstName}</td>
							<td class="field_content">${rule.trainingType}</td>
							<td class="field_content">${rule.trainingLength}</td>
							<td class="field_content">${rule.trainingDateFormatted}</td>
							<td class="field_content">${rule.expiryDateFormatted}</td>
	      					<td class="field_content">${rule.providedBy}</td>
	      					<td class="field_content">${rule.location}</td>
	      					
	      				</tr>
	        		</c:forEach>
	        	</c:when>
	        	<c:otherwise>
	        		<tr><td colspan='12' style="color:Red;">No employees found.</td></tr>
	        	</c:otherwise>
	        </c:choose>
				</tbody>
				</table>
    	</div>
    </div>


      


  
 