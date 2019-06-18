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
<c:set var="countContracts" value="0" />
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
    		$('.menuBCS').click(function () {
    		$("#loadingSpinner").css("display","inline");
    		});  
   			});
		</script>  		    
	<div id="printJob">	
		
				<div class="BCSHeaderText">My Contracts</div>
	
				<div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
   				<div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
			
			<div id="BCS-Search">
			
			<table id="BCS-table" width="100%" class="BCSTable">
     		 <thead>
	     		<tr style="border-bottom:1px solid grey;" class="listHeader">
	      		<th width="20%" class="listdata">Name</th>
	      		<th width="15%" class="listdata">Type</th>
	      		<th width="15%" class="listdata">Start Date</th>
	      		<th width="20%" class="listdata">Expiry Date</th>
	      		<th width="20%" class="listdata">Status</th>
	      		<th width="10%" class="listdata">Options</th>
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
	      					<td class="field_content">${rule.contractStartDateFormatted}</td>
	      					<td class="field_content">${rule.contractExpiryDateFormatted}</td>
	      					<td class="field_content">	      					
	      					<c:choose>
		         				<c:when test = "${rule.contractHistory.statusString eq 'Cancelled'}">
		         				<span style="background-color:Red;color:White;padding:3px;">&nbsp;${rule.contractHistory.statusString}&nbsp;</span>
		            			</c:when>
		         				<c:when test = "${rule.contractHistory.statusString eq 'Approved'}">
		         				<span style="background-color:Green;color:white;padding:3px;">&nbsp;${rule.contractHistory.statusString}&nbsp;</span>
		         				</c:when>
		                  		<c:when test = "${rule.contractHistory.statusString eq 'Suspended'}">
		                  		<span style="background-color:Black;color:white;padding:3px;">&nbsp;${rule.contractHistory.statusString}&nbsp;</span>
		         				</c:when>
		         				<c:otherwise>
		            				<span style="background-color:Yellow;color:black;padding:1px;">&nbsp;${rule.contractHistory.statusString}&nbsp;</span>
		         				</c:otherwise>
		      				</c:choose>
	      					
	      					</td>
	      					<td align="right" class="field_content">
	      					 <button type="button" class="btn btn-xs btn-primary menuBCS" onclick="closeMenu();loadMainDivPage('viewContractInformation.html?cid=${rule.id}');">View</button>
	      					</td>
	      				</tr>
	        		</c:forEach>
	        	</c:when>
	        	<c:otherwise>
	        		<tr><td colspan='6' style="color:Red;">No contracts found.</td></tr>
	        	</c:otherwise>
	        </c:choose>
	        </tbody>
	      </table>
		
	</div></div>
	
 <c:choose>
      	<c:when test="${countContracts >0 }">
      		<script>$('#details_success_message').html("There are <b>${countContracts}</b> <span style='text-transform:lowercase;'>contracts</span>.").css("display","block").delay(4000).fadeOut();</script>
      	</c:when>
      	<c:otherwise>
      		<script>$('#BCS-Search').css("display","none");$('#details_error_message').html("Sorry, there are no <span style='text-transform:lowercase;'>contracts</span> at this time.").css("display","block");</script>
      	</c:otherwise>
      	</c:choose>
  <script src="includes/js/jQuery.print.js"></script>	