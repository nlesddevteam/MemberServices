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
<c:set var="countVehicles" value="0" />
<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="includes/css/bcs.css" rel="stylesheet" type="text/css">
<script src="includes/js/bootstrapvalidator.min.js"></script>
<script src="includes/js/jquery.min.js"></script>
<script src="includes/js/bootstrap.min.js"></script>
<script src="includes/js/bcs.js"></script>
<script type="text/javascript">
$(document).ready(function() {
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");
    			$("#BCS-table tr:even").not(':first').css("background-color", "#FFFFFF");
    		    $("#BCS-table tr:odd").css("background-color", "#f2f2f2");

});
		</script>
		<script>
   			$(document).ready(function () {
    		$('.menuBCSC').click(function () {
    		$("#loadingSpinner").css("display","inline");
    		});  
   			});
		</script> 
		
		    		    
	<div id="printJob">	
	
	
	<c:set var="query" value="${ param.status }"/>
		
		<c:choose>
		<c:when test="${query eq '1'}">
				<c:set var="query" value="Vehicle(s) Pending Approval"/>				
		</c:when>
		<c:when test="${query eq '2'}">
				<c:set var="query" value="Approved Vehicle(s)"/>				
		</c:when>
		<c:when test="${query eq '3'}">
				<c:set var="query" value="Vehicle(s) Not Approved"/>				
		</c:when>
		<c:when test="${query eq '4'}">
				<c:set var="query" value="Vehicle(s) Suspended"/>				
		</c:when>
		<c:when test="${query eq '-1'}">
				<c:set var="query" value="Vehicle(s)"/>				
		</c:when>
		<c:otherwise>Vehicle(s)</c:otherwise>
		</c:choose>
	
	
	
	
	
	<div class="BCSHeaderText">${query}</div>
	
				<div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
   				<div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
			
			<div id="BCS-Search">
			
			<table id="BCS-table" width="100%" class="BCSTable">
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
	
	 <c:choose>
      	<c:when test="${countVehicles >0 }">
      		<script>$('#details_success_message').html("There are <b>${countVehicles}</b> <span style='text-transform:lowercase;'>${query}</span>.").css("display","block").delay(4000).fadeOut();</script>
      	</c:when>
      	<c:otherwise>
      		<script>$('#BCS-Search').css("display","none");$('#details_error_message').html("Sorry, there are no <span style='text-transform:lowercase;'>${query}</span> at this time.").css("display","block");</script>
      	</c:otherwise>
      	</c:choose>
	
	
	
	
	
	
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
	
<script src="includes/js/jQuery.print.js"></script>	
  