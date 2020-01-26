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
	<%pageContext.setAttribute("now", new java.util.Date()); %>   
	<c:set var="countRoutes" value="0" />		
	<div id="printJob">	
		
			 <div class="BCSHeaderText">Routes</div>
	
	<div class="alert alert-danger" id="body_error_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    <div class="alert alert-success" id="body_success_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	
			  <br />
	  		  <form>
				<div id="BCS-Search">				
				<table id="reportdata" class="table table-striped table-bordered dt-responsive nowrap BCSTable">
			  		<thead>
			  			<tr style="background-color:#b3d9ff;">
			  				<th width="15%" class="listdata">Name</th>
			  				<th width="15%" class="listdata">Operator</th>
			  				<th width="10%" class="listdata">Run</th>
			  				<th width="10%" class="listdata">Time</th>
			  				<th width="40%" class="listdata">Schools</th>
			  				<th width="10%" class="listdata">Options</th>
			  			</tr>
			  		</thead>
			  		<tbody>
					<c:choose>
		      			<c:when test="${fn:length(routes) > 0}">
			      			<c:forEach items="${routes}" var="rule">
			      			<c:set var="countRoutes" value="${countRoutes + 1}" />
			      				<tr style="border-bottom:1px solid silver;">
			      					<td class="field_content">${rule.routeName}</td>
			      					<td class="field_content">${rule.companyName}</td>
			      					<td class="field_content">${rule.routeRun}</td>
			      					<td class="field_content">${rule.routeTime}</td>
			      					<td class="field_content">${rule.routeSchools}</td>
			      					<td align="right" class="field_content">
			      						<button type="button" class="btn btn-xs btn-primary" onclick="closeMenu();loadMainDivPage('adminViewRoute.html?vid=${rule.id}');">View</button>
		      					    	<button type="button" class="btn btn-xs btn-danger" onclick="opendeletedialogroute('${rule.routeName}','${rule.id}');">Del</button>
			      					</td>
			      				</tr>
			      				
			      			</c:forEach>
     						</c:when>
     						<c:otherwise>
     							<tr><td colspan='5' style="color:Red;">No routes found</td></tr>
     						</c:otherwise>
     						</c:choose>
			  		</tbody>
			  	</table>
			  </div>
			  <c:choose>
      	<c:when test="${countRoutes >0 }">
      		<script>$('#body_success_message_top').html("There are <b>${countRoutes}</b> <span style='text-transform:lowercase;'>routes</span> found.").css("display","block").delay(4000).fadeOut();</script>
      	</c:when>
      	<c:otherwise>
      		<script>$('#BCS-Search').css("display","none");$('#body_error_message_top').html("Sorry, there are no <span style='text-transform:lowercase;'>routes</span> at this time.").css("display","block");</script>
      	</c:otherwise>
      	</c:choose>
			 </form>
		
			<div class="alert alert-danger" id="body_error_message_bottom" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
			<div class="alert alert-success" id="body_success_message_bottom" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
			
	</div>
   	<div id="myModal2" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitled">Delete Route</h4>
                </div>
                <div class="modal-body">
                    <p class="text-warning" id="title1d"><span id="spantitle1" name="spantitle1"></span></p>
                    <p class="text-warning" id="title2d"><span id="spantitle2" name="spantitle2"></span></p>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-success"  id="buttonleftd"></button>
                    <button type="button" class="btn btn-xs btn-primary" data-dismiss="modal" id="buttonrightd"></button>
                </div>
            </div>
   		</div>
   	</div>
	<script src="includes/js/jQuery.print.js"></script>	
