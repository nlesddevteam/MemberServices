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
<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="includes/css/bootstrap-datepicker3.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="includes/css/dataTables.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="includes/css/buttons.dataTables.min.css">
<link href="includes/css/bcs.css" rel="stylesheet" type="text/css">
<script src="includes/js/jquery.min.js"></script>
<script src="includes/js/bootstrap.min.js"></script>
<script src="includes/js/bootstrapvalidator.min.js"></script>
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
<script type="text/javascript">
$(document).ready(function() {
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");
    			$('#reportdata').DataTable({
    				  dom: 'Bfrtip',
    				  paging:         true,
    				  buttons: [
    				    'copyHtml5', 'excelHtml5', 'csvHtml5','print'
    				  ]
    				} );
    			$("#reportdata tr:even").not(':first').css("background-color", "#FFFFFF");
    		    $("#reportdata tr:odd").css("background-color", "#f2f2f2");
				

});
		</script>
		
		<script>
   			$(document).ready(function () {
    		$('.menuBCS').click(function () {
    		$("#loadingSpinner").css("display","inline");
    		});  
   			});
		</script> 
		
		<c:set var="countMemos" value="0" />
	<%pageContext.setAttribute("now", new java.util.Date()); %>   		
	<div id="printJob">	
		
			 <div class="BCSHeaderText">Contracts</div>
	
	<div class="alert alert-danger" id="body_error_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    <div class="alert alert-success" id="body_success_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	
			  <br />
	  		  <form>
	  		  <div id="BCS-Search">				
				<div id="divtable" style="max-width:100%;padding-top:10px;">	
					<table id="reportdata" class="table table-striped table-bordered dt-responsive nowrap BCSTable">
			  		<thead>
			  			<tr style="border-bottom:1px solid grey;" class="listHeader">
			  				<th width="30%" class="listdata" style="padding:2px;">Name</th>
			  				<th width="15%" class="listdata" style="padding:2px;">Type</th>
			  				<th width="15%" class="listdata" style="padding:2px;">Region</th>
			  				<th width="15%" class="listdata" style="padding:2px;">Expiry Date</th>
			  				<th width="15%" class="listdata" style="padding:2px;">Status</th>
			  				<th width="10%" class="listdata" style="padding:2px;">Options</th>
			  			</tr>
			  		</thead>
			  		<tbody>
					<c:choose>
		      			<c:when test="${fn:length(contracts) > 0}">
			      			<c:forEach items="${contracts}" var="rule">
			      			<c:set var="countContracts" value="${countContracts + 1}" />
			 					<tr style="border-bottom:1px solid silver;">
			      					<td class="field_content">${rule.contractName}</td>
			      					<td class="field_content">${rule.contractTypeString}</td>
			      					<td class="field_content">${rule.contractRegionString}</td>
			      					<td class="field_content">${rule.contractExpiryDateFormatted}</td>
			      					<td class="field_content">
			      					
			      					<c:choose>
						      			<c:when test="${rule.contractHistory.statusString eq 'Awarded' }">
						      			<span style="background-color:green;color:White;padding:3px;">&nbsp;AWARDED&nbsp;</span>			      				
						      			</c:when>
						      			<c:when test="${rule.contractHistory.statusString eq 'Suspended' }">
						      			<span style="background-color:black;color:White;padding:3px;">&nbsp;SUSPENDED&nbsp;</span>			      				
						      			</c:when>
						      			<c:when test="${rule.contractHistory.statusString eq 'Cancelled' }">
						      			<span style="background-color:Red;color:White;padding:3px;">&nbsp;CANCELLED&nbsp;</span>						      				
						      			</c:when>
						      			<c:otherwise>
						      			<span style="background-color:yellow;color:black;padding:3px;">&nbsp;NOT AWARDED&nbsp;</span>			      			
						      			</c:otherwise>			      			
			      					</c:choose>
			      					
			      					
			      					
			      					</td>
			      					<td class="field_content" align="right">
			      					
			      					<button type="button" class="btn btn-xs btn-primary menuBCS" onclick="closeMenu();loadMainDivPage('adminViewContract.html?vid=${rule.id}');">View</button>
		      								<button type="button" class="btn btn-xs btn-danger" onclick="opendeletedialogcontract('${fn:replace(rule.contractName, "'", " ")}','${rule.id}');">Del</button>
		      				
			      					</td>
			      				</tr>
		        			</c:forEach>
     							</c:when>
     							<c:otherwise>
     								<tr><td colspan='6' style="color:Red;">No contracts found</td></tr>
     							</c:otherwise>
     						</c:choose>
			  		</tbody>
			  	</table>
			  	</div>
			  	</div>
			  	
			  	 <c:choose>
      	<c:when test="${countContracts >0 }">
      		<script>$('#body_success_message_top').html("There are <b>${countContracts}</b> <span style='text-transform:lowercase;'>contracts</span> found.").css("display","block").delay(4000).fadeOut();</script>
      	</c:when>
      	<c:otherwise>
      		<script>$('#BCS-Search').css("display","none");$('#body_error_message_top').html("Sorry, there are no <span style='text-transform:lowercase;'>contracts</span> at this time.").css("display","block");</script>
      	</c:otherwise>
      	</c:choose>
			  	
			 </form>
		
	</div>
   	<div id="myModal2" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitled">Delete Contract</h4>
                </div>
                <div class="modal-body">
                    <p class="text-warning" id="title1d"><span id="spantitle1" name="spantitle1"></span></p>
                    <p class="text-warning" id="title2d"><span id="spantitle2" name="spantitle2"></span></p>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-default"  id="buttonleftd"></button>
                    <button type="button" class="btn btn-xs btn-primary" data-dismiss="modal" id="buttonrightd"></button>
                </div>
            </div>
   		</div>
   	</div>
	<script src="includes/js/jQuery.print.js"></script>	
