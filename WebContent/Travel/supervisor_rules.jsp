<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 com.awsd.travel.*,
                 com.awsd.common.*,com.esdnl.util.*,
                 java.util.*,
                 java.io.*,
                 java.text.*,
                 java.sql.*"
        isThreadSafe="false"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<esd:SecurityCheck permissions="TRAVEL-CLAIM-ADMIN" />


		<script>
			
			
			$(document).ready(function(){    
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");
        		
    			 $('.mclaims').click(function () {
 			    	$("#loadingSpinner").css("display","inline");
 			    });
        		
        		
			});
			
			
			   
			   </script>
		
		
      
	<div class="claimHeaderText">Travel Claim Supervisor Rules</div>
	<br/>Below is a list of default Supervisor Rules for travel claims that set the default supervisor for an employee automatically when they start a claim. Claiments can still select and/or change their supervisor if requred before they submit their claim. To add a new rule, click Add below.<br/>
	<a href="#" onclick="loadMainDivPage('addSupervisorRule.html');"><img src="includes/img/add-off.png" title="Add a new Rule" border=0 class="img-swap" align="right" style="padding-right:5px;"/></a>
	<br/>	
	<br/><div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
           <div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	
	<br/>
	<div id="printJob">       
    <form name="add_claim_item_form">
      <table id="claims-table" width="100%" class="claimsTable">
     		<tr style="border-bottom:1px solid grey;" class="listHeader">
      		<td width="30%" class="listdata">Employee</td>
      		<td width="30%" class="listdata">Supervisor</td>
      		<td width="30%" class="listdata">Division</td>
      		<td align="right" width="10%" class="listdata">Options</td>
     		</tr>
      	<c:choose>
      		<c:when test="${fn:length(SUPERVISOR_RULES) > 0}">
      			<c:forEach items="${SUPERVISOR_RULES}" var="rule">
      				<tr style="border-bottom:1px dashed silver;">
      					<td class="field_content">${rule.employee}</td>
      					<td class="field_content">${rule.supervisor}</td>
      					<td class="field_content">${rule.division}</td>
      					<td align="right" class="field_content">
      						<a href='#' class="mclaims" onclick="loadMainDivPage('addSupervisorRule.html?rule_id=${rule.ruleId}');")><img src="includes/img/viewsm-off.png" class="img-swap" title="View Rule" border=0 style="padding-top:3px;padding-bottom:3px;"></a> &nbsp; 
							<a class='edit' href="#" onclick="deleteSupervisorRule('${rule.ruleId}');"><img src="includes/img/deletesm-off.png" class="img-swap" border=0 title="Delete Rule" style="padding-top:3px;padding-bottom:3px;"></a>
      					</td>
      				</tr>
        		</c:forEach>
      		</c:when>
        	<c:otherwise>
        		<tr><td colspan='4' style="color:Red;">No rules found.</td></tr>
        	</c:otherwise>
        </c:choose>
       
      </table>
    </form>
    </div>
    
    
    <script>
    $('document').ready(function(){
    $("#claims-table tr:even").not(':first').css("background-color", "#FFFFFF");
    $("#claims-table tr:odd").css("background-color", "#E3F1E6");
    })
    
    
    
    </script>

