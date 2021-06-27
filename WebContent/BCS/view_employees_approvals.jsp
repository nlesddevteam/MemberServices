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
    <form>
    <div id="BCS-Search">
			  	<div id="divtable" style="max-width:100%;padding-top:10px;">	
			  	
			  	<table id="reportdata" class="table table-striped table-bordered dt-responsive nowrap BCSTable">
				<thead>
				<tr style="background-color:#b3d9ff;">
				<th class="listdata">Contractor</th>
				<th class="listdata">Last Name</th>
				<th class="listdata">First Name</th>
				<th class="listdata">Position</th>
				<th class="listdata">DL Number</th>
				<th class="listdata">DL Class</th>
				<th class="listdata">DA Run Date</th>
				<th class="listdata">Demerit Points</th>
				<th class="listdata">DA Convictions</th>
				<th class="listdata">Dangerous Driving 5 YRS</th>
				<th class="listdata">Suspensions 36 MNTHS</th>
				<th class="listdata">PRCVSQ Date</th>
				<th class="listdata">Findings of Guilt</th>
				<th class="listdata">SCA Document</th>
				<th class="listdata">FA Expiry Date</th>
				<th class="listdata">FA Level C</th>
				<th class="listdata">PCC Date</th>
				<th class="listdata">Status</th>
				<th class="listdata">Region</th>
				<th class="listdata">Depot</th>
				<th class="listdata">Options</th>
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
	      					<td class="field_content">${rule.employeePositionText}</td>
	      					<td class="field_content">${rule.dlNumber}</td>
	      					<td class="field_content">${rule.dlClassText}</td>	      										
	      					<td class="field_content">${rule.daRunDate}</td>
	      					<td class="field_content">${rule.demeritPoints}</td>
	      					<td class="field_content">${rule.daConvictions}</td>
	      					<td class="field_content">${rule.dangerousDriving}</td>
							<td class="field_content">${rule.suspensions}</td>
							<td class="field_content">${rule.prcvsqDateFormatted}</td>
							<td class="field_content">${rule.findingsOfGuilt}</td>
							<td class="field_content">${rule.scaDateFormatted}</td>
	      					<td class="field_content">${rule.faExpiryDateFormatted}</td>
	      					<td class="field_content">${rule.faLevelC}</td>
	      					<td class="field_content">${rule.pccDateFormatted}</td>
	      					<td class="field_content">${rule.statusText}</td>
	      					<td class="field_content">${rule.regionBean ne null ? rule.regionBean.regionName : ''}</td>
	      					<td class="field_content">${rule.regionBean ne null ? rule.regionBean.depotName : ''}</td>
	      					<td class="field_content" align="center">
      							<button type="button" class="btn btn-xs btn-primary" onclick="closeMenu();loadMainDivPage('adminViewEmployee.html?vid=${rule.id}');">View</button>
      							<c:choose>
      							<c:when test="${reporttitle ne 'Employees Removed' }">
      							<button type="button" class="btn btn-xs btn-danger" onclick="opendeletedialogemp('${rule.lastName},${rule.firstName}','${rule.id}','A');">Del</button>
      							</c:when>
      							</c:choose>
      							
	      					</td>
	      				</tr>
	        		</c:forEach>
	        	</c:when>
	        	<c:otherwise>
	        		<tr><td colspan='16' style="color:Red;">No employees found.</td></tr>
	        	</c:otherwise>
	        </c:choose>
				</tbody>
				</table>
    	</div>
    </div>  
       <c:choose>
      	<c:when test="${countEmp >0 }">
      		<script>$('#details_success_message').html("There are <b>${countEmp}</b> <span style='text-transform:lowercase;'>${reporttitle}</span>.").css("display","block").delay(4000).fadeOut();</script>
      	</c:when>
      	<c:otherwise>
      		<script>$('#BCS-Search').css("display","none");$('#details_error_message').html("Sorry, there are no <span style='text-transform:lowercase;'>${reporttitle}</span> at this time.").css("display","block");</script>
      	</c:otherwise>
      	</c:choose>
      
    </form>
</div>
	    <div id="myModal" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitle"></h4>
                </div>
                <div class="modal-body">
                    <p class="text-warning" id="title1"></p>
                    <p class="text-warning" id="title2"></p>
 		    		<p class="text-warning" id="title3"></p>
		</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-default" data-dismiss="modal" id="buttonleft"></button>
                    <button type="button" class="btn btn-xs btn-primary" data-dismiss="modal" id="buttonright"></button>
                </div>
            </div>
        </div>
    </div>	

  
 