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
	     		<tr style="border-bottom:1px solid grey;" class="listHeader">
	      		<th width="30%" class="listdata">Serial Number</th>
	      		<th width="15%" class="listdata">Plate Number</th>
	      		<th width="15%" class="listdata">Year</th>
	      		<th width="30%" class="listdata">Status</th>
	      		<th width="10%" class="listdata">OPTIONS</th>
	      		</tr>
	      		</thead>
	      		<tbody>
	      	<c:choose>
	      		<c:when test="${fn:length(vehicles) > 0}">
		      		<c:forEach items="${vehicles}" var="rule">
		      		<c:set var="countVehicles" value="${countVehicles + 1}" />
	 					<tr style="border-bottom:1px solid silver;">
	      					<td class="field_content">${rule.vSerialNumber}</td>
	      					<td class="field_content">${rule.vPlateNumber}</td>
	      					<td class="field_content">${rule.vYear}</td>
	      					<td class="field_content">	      					
	      					<c:choose>
		         				<c:when test = "${rule.vStatus eq 1}">
		         				<span style="background-color:Yellow;color:black;padding:1px;">&nbsp;${ rule.statusText}&nbsp;</span>
		         				</c:when>
		         				<c:when test = "${rule.vStatus eq 2}">
		         				<span style="background-color:Green;color:white;padding:1px;">&nbsp;${ rule.statusText}&nbsp;</span>
		         				</c:when>
		                  		<c:when test = "${rule.vStatus eq 3}">
		                  		<div class="col-sm-8">
		            				<span style="background-color:Red;color:white;padding:1px;">&nbsp;${rule.statusText}&nbsp;</span>
		         				</div>
		         				</c:when>
		         				<c:otherwise>
		            				<span style="background-color:Black;color:white;padding:1px;">&nbsp;${rule.statusText}&nbsp;</span>
		         				</c:otherwise>
		      				</c:choose>
		      				</td>
	      					<td align="right" class="field_content">
	      						<button type="button" class="btn btn-xs btn-primary menuBCSC" onclick="closeMenu();loadMainDivPage('addNewVehicle.html?vid=${rule.id}');">View</button>
		      					<button type="button" class="btn btn-xs btn-danger" onclick="opendeletedialog('${rule.vPlateNumber}','${rule.id}','C');">Del</button>
		      				</td>
	      				</tr>
	        		</c:forEach>
	        	</c:when>
	        	<c:otherwise>
	        		<tr><td colspan='5' class="field_content" style="color:Red;">No vehicles found.</td></tr>
	        	</c:otherwise>
	        </c:choose>
	        </tbody>
				</table>		  	

				</div>
				</div>

		</div>
	<c:choose>
      	<c:when test="${countVehicles >0 }">
      		<script>$('#details_success_message').html("There are <b>${countVehicles}</b> vehicle entries found for this contractor.").css("display","block").delay(6000).fadeOut();      		
      		</script>	
      	</c:when>
      	<c:otherwise>
      		<script>$('#BCS-Search').css("display","none");$('#details_error_message').html("Sorry, there are no vehicle entries for the contractor <b>${companyname}</b> at this time.").css("display","block");</script>
      	</c:otherwise>
      	</c:choose>