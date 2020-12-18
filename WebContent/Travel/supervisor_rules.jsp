<%@ page language="java"
         session="true"
         import="com.awsd.security.*,
         		 com.awsd.personnel.*,
                 com.awsd.travel.*,
                 com.awsd.common.*,
                 com.esdnl.util.*,
                 java.util.*,
                 java.io.*,
                 java.text.*,
                 java.sql.*"
        isThreadSafe="false"%>
        <%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>   
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>


<esd:SecurityCheck permissions="TRAVEL-CLAIM-ADMIN" />


		<script>
			
			
			$(document).ready(function(){    
       
    			$('#loadingSpinner').css("display","none");           		      		
    			 $.cookie('backurl', 'listSupervisorRules.html', {expires: 1 });
			   	 
		   		  $("#claims-table").DataTable({
		   			"responsive": true,
		   		  "order": [[ 0, "asc" ]],
		   		  dom: 'Blfrtip',
		   	        buttons: [			        	
		   	        	//'colvis',
		   	        	'copy', 
		   	        	'csv', 
		   	        	'excel', 
		   	        	{
		   	                extend: 'pdfHtml5',
		   	                footer:true,
		   	                //orientation: 'landscape',
		   	                messageTop: 'Travel/PD Claims ',
		   	                messageBottom: null,
		   	                exportOptions: {
		   	                    columns: [ 0, 1, 2 ]
		   	                }
		   	            },
		   	        	{
		   	                extend: 'print',
		   	                //orientation: 'landscape',
		   	                footer:true,
		   	                messageTop: 'Travel/PD Claims',
		   	                messageBottom: null,
		   	                exportOptions: {
		   	                    columns: [ 0, 1, 2]
		   	                }
		   	            }
		   	        ],		  
		   		  "lengthMenu": [[25, 50, 100, -1], [25, 50, 100, "All"]]
		   	  
		   	  
		   	  });	
		   	 
		    });
		       </script>
		
	<style>
input { border:1px solid silver;}
</style>  		
		
        <img class="pageHeaderGraphic" src="/MemberServices/Travel/includes/img/supervisorrules.png" style="max-width:200px;" border=0/> 
	<div class="siteHeaderBlue">Travel Claim Supervisor Rules</div>
	<br/>Below is a list of default Supervisor Rules for travel claims that set the default supervisor for an employee automatically when they start a claim. 
	Claiments can still select and/or change their supervisor if requred before they submit their claim. To add a new rule, click Add below.<br/><br/>
	
	<div align="center"><a href="#" class="btn btn-success btn-sm" onclick="loadingData();loadMainDivPage('addSupervisorRule.html');return false;"><i class="fas fa-user-tag"></i> Add a New Rule</a> <a href="index.jsp" class="btn btn-danger btn-sm mr-2" onclick="loadingData();"><i class="far fa-window-close"></i> Cancel</a></div>
	<br/>	
	<br/>
	<div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
     <div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	
	  <form name="add_claim_item_form">
      <table id="claims-table" class="table table-condensed table-striped table-bordered claimsTable" style="font-size:11px;background-color:White;" width="100%">	
				<thead>
				<tr style="text-transform:uppercase;font-weight:bold;">  	
      		<td width="30%">Employee</td>
      		<td width="30%">Supervisor</td>
      		<td width="25%">Division</td>
      		<td width="15%">Options</td>
     		</tr>
     		</thead>
     		<tbody>
      	<c:choose>
      		<c:when test="${fn:length(SUPERVISOR_RULES) > 0}">
      			<c:forEach items="${SUPERVISOR_RULES}" var="rule">
      				<tr valign="middle">
      					<td style="vertical-align:middle;">${rule.employee}</td>
      					<td style="vertical-align:middle;">${rule.supervisor}</td>
      					<td style="vertical-align:middle;">${rule.division.name}</td>
      					<td style="vertical-align:middle;">
      						<a href='#' class="btn btn-xs btn-primary" onclick="loadingData();loadMainDivPage('addSupervisorRule.html?rule_id=${rule.ruleId}');")><i class="fas fa-eye"></i> VIEW</a>
							<a class='edit btn btn-xs btn-danger' href="#" onclick="deleteSupervisorRule('${rule.ruleId}');"><i class="fas fa-trash-alt"></i> DEL</a>
      					</td>
      				</tr>
        		</c:forEach>
      		</c:when>        	
        </c:choose>
       </tbody>
      </table>
    </form>


