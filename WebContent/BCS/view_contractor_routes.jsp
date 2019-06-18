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
<c:set var="countRoutes" value="0" />
<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="includes/css/bcs.css" rel="stylesheet" type="text/css">
<script src="includes/js/jquery.min.js"></script>
<script src="includes/js/bootstrap.min.js"></script>
<script src="includes/js/bootstrapvalidator.min.js"></script>
<script src="includes/js/bcs.js"></script>
<script type="text/javascript">
$(document).ready(function() {
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");
    			$("#BCS-table tr:even").not(':first').css("background-color", "#FFFFFF");
    		    $("#BCS-table tr:odd").css("background-color", "#E3F1E6");

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
		
		
		<div class="BCSHeaderText">My Routes</div>
			
			<div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
   			<div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
			
			
			
			<div id="BCS-Search">
			
			<table id="BCS-table" width="100%" class="BCSTable">
			<thead>
	     		<tr style="border-bottom:1px solid grey;" class="listHeader">
	      		<th width="25%" class="listdata">Name</th>
	      		<th width="30%" class="listdata">School</th>
	      		<th width="25%" class="listdata">Driver</th>
	      		<th width="10%" class="listdata">Vehicle</th>
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
	      					<td class="field_content">${rule.schoolName}</td>
	      					<td class="field_content">${rule.lastName},${rule.firstName}</td>
	      					<td class="field_content">${rule.vPlateNumber}</td>
	      					<td align="right" class="field_content">
	      					<button type="button" class="btn btn-xs btn-primary menuBCSC" onclick="closeMenu();loadMainDivPage('viewRouteInformation.html?rid=${rule.id}&cid=${rule.contractId}');";">View</button>
	      					</td>
	      				</tr>
	        		</c:forEach>
	        	</c:when>
	        	<c:otherwise>
	        		<tr><td colspan='5' style="color:Red;">No routes found.</td></tr>
	        	</c:otherwise>
	        </c:choose>
	        </tbody>
	      </table>
		</div>
	</div>
	
 <c:choose>
      	<c:when test="${countRoutes >0 }">
      		<script>$('#details_success_message').html("There are <b>${countRoutes}</b> <span style='text-transform:lowercase;'>routes</span>.").css("display","block").delay(4000).fadeOut();</script>
      	</c:when>
      	<c:otherwise>
      		<script>$('#BCS-Search').css("display","none");$('#details_error_message').html("Sorry, there are no <span style='text-transform:lowercase;'>routes</span> at this time.").css("display","block");</script>
      	</c:otherwise>
      	</c:choose>
  