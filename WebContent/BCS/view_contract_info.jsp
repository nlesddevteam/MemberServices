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
<%@ taglib uri="/taglib/memberservices.tld" prefix="esd" %>
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
		
		<div class="BCSHeaderText">View Contract Information</div>
	
				 
		
		    <form class="form-horizontal"  id="contact-form-up" name="contact-form-up" method="post" action="">
		     <div class="form-group">
		      <label class="control-label col-sm-2" for="email">Status:</label>
		      <div class="col-sm-10">
		      
		      				<c:choose>
		         				<c:when test = "${contract.contractHistory.statusString eq 'Cancelled'}">
		         				<span style="background-color:Red;color:White;padding:3px;text-transform:uppercase;">&nbsp;${contract.contractHistory.statusString}&nbsp;</span>
		         				</c:when>
		         				<c:when test = "${contract.contractHistory.statusString eq 'Approved'}">
		         				<span style="background-color:Green;color:white;padding:3px;text-transform:uppercase;">&nbsp;${contract.contractHistory.statusString}&nbsp;</span>
		         				</c:when>
		                  		<c:when test = "${contract.contractHistory.statusString eq 'Suspended'}">
		                  		<span style="background-color:Black;color:white;padding:3px;text-transform:uppercase;">&nbsp;${contract.contractHistory.statusString}&nbsp;</span>
		         				</c:when>
		         				<c:otherwise>
		            				<span style="background-color:Yellow;color:black;padding:1px;text-transform:uppercase;">&nbsp;${contract.contractHistory.statusString}&nbsp;</span>
		         				</c:otherwise>
		      				</c:choose>
		      
		      
		        
		      </div>
		    </div>
		    
		    <div class="form-group">
		      <label class="control-label col-sm-2" for="email">Name:</label>
		      <div class="col-sm-10">
		        <p class="form-control-static">${ contract.contractName}</p> 
		      </div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-2" for="email">Type:</label>
		      <div class="col-sm-10">
		        <p class="form-control-static">${ contract.contractTypeString}</p>
		      </div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-2" for="email">Vehicle Type:</label>
		      <div class="col-sm-10">
		        <p class="form-control-static">${ vehicletype}</p>
		      </div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-2" for="email">Vehicle Size:</label>
		      <div class="col-sm-10">
		        <p class="form-control-static">${ vehiclesize}</p>
		      </div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-2" for="email">Start Date:</label>
		      <div class="col-sm-10">
		        <p class="form-control-static">${ contract.contractStartDateFormatted}</p>
		      </div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-2" for="email">Start Date:</label>
		      <div class="col-sm-10">
		        <p class="form-control-static">${ contract.contractExpiryDateFormatted}</p>
		      </div>
		    </div>
    		<div class="form-group">
		    		<label class="control-label col-sm-2" for="email">Route(s):</label>
      				<div class="col-sm-10">
      				
		    		
		    		<div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
   				    <div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
		    		
			      		<div id="BCS-Search">
			
			<table id="BCS-table" width="100%" class="BCSTable">
     		 <thead>
	     		<tr style="border-bottom:1px solid grey;" class="listHeader">
						      <th width="50%" class="listdata">Route</th>
						      <th width="40%" class="listdata">School</th>
						      <th width="10%" class="listdata">Options</th>
						    </tr>
						  </thead>
						  <tbody>
						  
							<c:forEach var="entry" items="${croutes}">
							<c:set var="countRoutes" value="${countRoutes + 1}" />
								<tr style="border-bottom:1px solid silver;">
									<td class="field_content">${entry.routeName}</td>
									<td class="field_content">${entry.routeSchoolString}</td>
									<td class="field_content">
									<button type="button" class="btn btn-xs btn-primary menuBCS" onclick="closeMenu();loadMainDivPage('viewRouteInformation.html?rid=${entry.id}&cid=${contract.id }');">View Route</button>
		      						
									</td>
								</tr>
							</c:forEach>
							</tbody>
							
						</table>
				</div>
		    </div>	</div>		    		    		    
		   </form>
		
	</div>
	 <c:choose>
      	<c:when test="${countRoutes >0 }">
      		<script>$('#details_success_message').html("There are <b>${countRoutes}</b> route(s) for this contract.").css("display","block").delay(4000).fadeOut();</script>
      	</c:when>
      	<c:otherwise>
      		<script>$('#BCS-Search').css("display","none");$('#details_error_message').html("Sorry, there are no routes for this contract at this time.").css("display","block");</script>
      	</c:otherwise>
      	</c:choose>
<script src="includes/js/jQuery.print.js"></script>	