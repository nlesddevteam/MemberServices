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
  				    'copyHtml5', 'excelHtml5', 'csvHtml5','print'
  				  ]
  				} );
    			$("#reportdata tr:even").not(':first').css("background-color", "#FFFFFF");
    		    $("#reportdata tr:odd").css("background-color", "#F2F2F2");
    		    
        	});</script>
		<script>
   			$(document).ready(function () {
    		$('.menuBCS').click(function () {
    		$("#loadingSpinner").css("display","inline").delay(2000).fadeOut();
    		});
    		$("#numdays").val($("#reportdays").val());
   			});
		</script>
	<div id="printJob">	
      
	<div class="BCSHeaderText">Document Audit Report Last ${reporttitle} Day(s)</div>
	<div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
           <div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	
	<br/>       
    <form>
    	<div id="BCS-Search">
			  	<div id="divtable" style="max-width:100%;padding-top:10px;">
			  	<input type="hidden" id="reportdays" value="${reporttitle}">
			  	<table style="max-width:100%;padding-top:10px;">
			  	<tr>
			  	<td class="BCSHeaderText" style="padding:10px;">Please select number of days for the audit</td>
			  	<td class="BCSHeaderText" style="padding:10px;">
			  		<select id="numdays">
			  			<option value='1'>1 Day</option>
			  			<option value='7'>7 days</option>
			  			<option value='14'>14 Days</option>
			  			<option value='21'>21 Days</option>
			  			<option value='28'>28 Days</option>
			  		</select>
			  	</td>
			  	<td class="BCSHeaderText" style="padding:10px;"><input type="button" value="Submit" onclick="populatereport();"></td>
			  	</tr>
			  	</table>
			  	<br />
				<table id="reportdata" class="table table-striped table-bordered dt-responsive nowrap BCSTable">
				<thead>
				<tr style="background-color:#b3d9ff;">
				<th class="listdata">Contractor</th>
				<th class="listdata">Name/Plate Number</th>
				<th class="listdata">Action</th>
				<th class="listdata">Action By</th>
				<th class="listdata">Action Date</th>
				<th class="listdata">Document Type</th>
				<th class="listdata">Option</th>
				</thead>
				<tbody>
				<c:choose>
	      		<c:when test="${fn:length(vehicles) > 0}">
		      		<c:forEach items="${vehicles}" var="rule">
	 					<c:set var="countVeh" value="${countVeh + 1}" />
	 					<tr>
	 						<td class="field_content">${rule.companyName}</td>
	      					<td class="field_content">${rule.objectName}</td>
	      					<td class="field_content">${rule.fileAction}</td>
	      					<td class="field_content">${rule.actionBy}</td>
	      					<td class="field_content">${rule.actionDateFormatted}</td>
	      					<td class="field_content">${rule.documentName}</td>
							<td class="field_content" align="right">
									<c:choose>
										<c:when test="${rule.fileCategory eq 'BCS_CONTRACTOR_VEHICLE'  }">
											<button type="button" class="btn btn-xs btn-primary" onclick="closeMenu();loadMainDivPage('adminViewVehicle.html?cid=${rule.parentId}');">View</button>
										</c:when>
										<c:when test="${rule.fileCategory eq 'BCS_CONTRACTOR_EMPLOYEE'  }">
											<button type="button" class="btn btn-xs btn-primary" onclick="closeMenu();loadMainDivPage('adminViewEmployee.html?vid=${rule.parentId}');">View</button>
										</c:when>
										<c:otherwise>
										&nbsp;
										</c:otherwise>
									</c:choose>
      								
      								
		      				</td>
	      				</tr>
	        		</c:forEach>
	        	</c:when>
	        	<c:otherwise>
	        		<tr><td colspan='9' style="color:Red;">No vehicles found.</td></tr>
	        	</c:otherwise>
	        </c:choose>
				</tbody>
				</table>
				</div>

		
	</div>	
      	</div>

      
    </form>
</div>
	

  
 