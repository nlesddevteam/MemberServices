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
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel.tld" prefix="per" %>

<esd:SecurityCheck permissions="TRAVEL-CLAIM-ADMIN,TRAVEL-CLAIM-SUPERVISOR-VIEW" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	
	<head>
		<title>Travel Claim details</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">  
		    <meta charset="utf-8">
		    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">    		
    		<link href="includes/css/jquery-ui.css" rel="stylesheet" type="text/css"> 
    		<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">  	
   			<link href="includes/css/travel.css" rel="stylesheet" type="text/css">
   			<!-- For mini-icons in menu -->
   			<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">			
    		<script src="includes/js/jquery.min.js"></script>		
    		<script src="includes/js/jquery-ui.js"></script>
    		<script src="includes/js/bootstrap.min.js"></script>
    		<script src="includes/js/travel.js"></script>
			<script src="includes/js/jquery.maskedinput.min.js"></script>
			
    <script type="text/javascript">
    	$('document').ready(function() {
    	
    		$('#loadingSpinner').css("display","none");
    		
        $('#tab_content tr:not(tr.division_title):odd').css({
            'background-color': '#f0f0f0'
        });

        $('#tab_content tr.alert').css({
            'background-color': '#F75D59',
            'font-weight': 'bold' 
        });

        $('a.delete').click(function() {
            return confirm('Are you sure you want to delete the budget?');
        });
        
        $('#school-year').change(function(){
        	        	
        	$('#add_claim_item_form').submit();
        });
        
        $('.auto-adjust').click(function(){
        	if(confirm('Are you sure you want to auto adjust these budgets?'))
        		return true;
        	else
        		return false;
        });
        
        $('.surrender').click(function(){
        	if(confirm('Are you sure you want to surrender these budgets?'))
        		return true;
        	else
        		return false;
        });
        
        $('.rollover').click(function(){
        	if(confirm('Are you sure you want to roll these budgets forward to the next school year?'))
        		return true;
        	else
        		return false;
        });
        
    	
        
    	});
    </script>
    
    <c:set var="curdiv" value="0" />
    <c:set var="curdivbudget" value="0" />  
    <c:set var='colspan' value="5" />
    <c:set var='subtotal_budget' value="0"/>
	  <c:set var='subtotal_ytd' value="0"/>
	  <c:set var='subtotal_deficit' value="0"/>
	</head>
	
	<body style="margin:0px;">
		<h1 class='title'>Travel Budgets ${sy}</h1>
    <form id='add_claim_item_form' name="add_claim_item_form" action='listTravelBudgets.html' method='post'>
    	<table cellspacing="2" cellpadding="2" class='options'>
    		<tr>
    			<td>School Year</td>
    			<td>
    				<select id='school-year' name='school_year'>
    					<c:forEach items='${FISCALYEARS}' var='year'>
    						<option value='${year}' ${(sy eq year) ? 'SELECTED' :''}>${year}</option>
    					</c:forEach>
    				</select>
    			</td>
    		</tr>
    	</table>
      <table id="tab_content" width="100%" cellspacing="2" cellpadding="2">
     		<tr>
      		<td class="label">Employee</td>
      		<td class="label">Fiscal Year</td>
      		<td class="label">Budgeted<br/>Amount</td>
      		<td class="label">Fiscal YTD</td>
      		<td class="label">Available<br/>Funds</td>
      		<esd:SecurityAccessRequired permissions="TRAVEL-EXPENSE-EDIT-BUDGET,TRAVEL-EXPENSE-DELETE-BUDGET">
      			<c:set var='colspan' value="6" />
      			<td class="label options" align='center'>Options</td>
      		</esd:SecurityAccessRequired>
     		</tr>
      	<c:choose>
      		<c:when test="${fn:length(BUDGETS) > 0}">
      			<c:forEach items="${BUDGETS}" var="b">
      				<c:choose>
	      				<c:when test="${b.division.id ne curdiv}">
	      					<c:set var='curdiv' value="${b.division.id}"/>
	      					<c:if test="${subtotal_budget gt 0}">
		      					<tr class='totals'>
				        			<td colspan='2'>Division Subtotals</td>
				        			<td><fmt:formatNumber value='${subtotal_budget}' type='currency' /></td>
				        			<td><fmt:formatNumber value='${subtotal_ytd}' type='currency' /></td>
				        			<td><fmt:formatNumber value='${subtotal_deficit}' type='currency' /></td>
				        			<esd:SecurityAccessRequired permissions="TRAVEL-EXPENSE-EDIT-BUDGET,TRAVEL-EXPENSE-DELETE-BUDGET">
				        				<td class='options'>&nbsp;</td>
				        			</esd:SecurityAccessRequired>
				        		</tr>
				        		<esd:SecurityAccessRequired roles="ADMINISTRATOR,ASSISTANT DIRECTORS">
					        		<tr class='totals'>
					        			<td colspan='2'>Division Budget</td>
					        			<td><fmt:formatNumber value='${curdivbudget}' type='currency' /></td>
					        			<td>&nbsp;</td>
					        			<td>&nbsp;</td>
					        			<esd:SecurityAccessRequired permissions="TRAVEL-EXPENSE-EDIT-BUDGET,TRAVEL-EXPENSE-DELETE-BUDGET">
					        				<td class='options'>&nbsp;</td>
					        			</esd:SecurityAccessRequired>
					        		</tr>
					        		<tr class='totals'>
					        			<td colspan='2'>Division Available Funds</td>
					        			<td><fmt:formatNumber value='${curdivbudget - subtotal_ytd}' type='currency' /></td>
					        			<td>&nbsp;</td>
					        			<td>&nbsp;</td>
					        			<esd:SecurityAccessRequired permissions="TRAVEL-EXPENSE-EDIT-BUDGET,TRAVEL-EXPENSE-DELETE-BUDGET">
					        				<td class='options'>&nbsp;</td>
					        			</esd:SecurityAccessRequired>
					        		</tr>
				        		</esd:SecurityAccessRequired>
			        		</c:if>
	      					<tr class='division_title'>
	      						<td colspan='${colspan}' align='center'>${b.division}
	      							<esd:SecurityAccessRequired permissions="TRAVEL-CLAIM-ADMIN">
				        				<span class='options'>[<a class='auto-adjust' href='autoAdjustTravelBudgets.html?id=${b.division.id}&sy=${sy}'>auto adjust</a>|<a class='surrender' href='surrenderTravelBudgets.html?id=${b.division.id}&sy=${sy}'>surrender</a>|<a class='rollover' 
	      							href='rolloverTravelBudgets.html?id=${b.division.id}&sy=${sy}'>rollover</a>]</span>
				        			</esd:SecurityAccessRequired>
	      						</td>
	      					</tr>
	      					<c:set var='curdivbudget' value="${b.division.budget.budget}"/>
	      					<c:set var='subtotal_budget' value="${b.amount}"/>
	      					<c:set var='subtotal_ytd' value="${b.amountClaimed}"/>
	      					<c:set var='subtotal_deficit' value="${b.deficit}"/>
	      				</c:when>
      					<c:otherwise>
      						<c:set var='subtotal_budget' value="${subtotal_budget + b.amount}" />
	      					<c:set var='subtotal_ytd' value="${subtotal_ytd + b.amountClaimed}"/>
	      					<c:set var='subtotal_deficit' value="${subtotal_deficit + b.deficit}"/>
      					</c:otherwise>
      				</c:choose>
      				<tr class='${(b.deficit/b.amount*100) < 10.0 ? "alert":"noalert" }'>
      					<td style='border-top:solid 1px #f0f0f0;border-bottom:solid 1px #f0f0f0;'>${b.personnel.fullName}</td>
      					<td style='border-top:solid 1px #f0f0f0;border-bottom:solid 1px #f0f0f0;'>${b.fiscalYear}</td>
      					<td style='border-top:solid 1px #f0f0f0;border-bottom:solid 1px #f0f0f0;'><fmt:formatNumber value='${b.amount}' type='currency' /></td>
      					<td style='border-top:solid 1px #f0f0f0;border-bottom:solid 1px #f0f0f0;'><fmt:formatNumber value='${b.amountClaimed}' type='currency' /></td>
      					<td style='border-top:solid 1px #f0f0f0;border-bottom:solid 1px #f0f0f0;'><fmt:formatNumber value='${b.deficit}' type='currency' /></td>
      					<esd:SecurityAccessRequired permissions="TRAVEL-EXPENSE-EDIT-BUDGET,TRAVEL-EXPENSE-DELETE-BUDGET">
	      					<td align='center' class='options'  style='border-top:solid 1px #f0f0f0;border-bottom:solid 1px #f0f0f0;'>
	      						<a class='edit' href='#' onclick="openWindow('EDIT_TRAVEL_BUDGET', 'addTravelBudget.html?budget_id=${b.budgetId}',357, 350, 0); return false;">edit</a>&nbsp;
	      						<a class='ADJUST' href='#' onclick="openWindow('ADJUST_TRAVEL_BUDGET', 'adjustTravelBudget.html?budget_id=${b.budgetId}',357, 225, 0); return false;">adj</a>
	      						<esd:SecurityAccessRequired permissions="TRAVEL-EXPENSE-DELETE-BUDGET">
	      							 &nbsp;<a class='edit delete' href="deleteTravelBudget.html?budget_id=${b.budgetId}">del</a>
	      						</esd:SecurityAccessRequired>
	      					</td>
      					</esd:SecurityAccessRequired>
      				</tr>
        		</c:forEach>
        		<c:if test="${subtotal_budget gt 0}">
     					<tr class='totals'>
	        			<td colspan='2'>Division Subtotals</td>
	        			<td><fmt:formatNumber value='${subtotal_budget}' type='currency' /></td>
	        			<td><fmt:formatNumber value='${subtotal_ytd}' type='currency' /></td>
	        			<td><fmt:formatNumber value='${subtotal_deficit}' type='currency' /></td>
	        			<esd:SecurityAccessRequired permissions="TRAVEL-EXPENSE-EDIT-BUDGET,TRAVEL-EXPENSE-DELETE-BUDGET">
	        				<td class='options'>&nbsp;</td>
	        			</esd:SecurityAccessRequired>
	        		</tr>
	        		<esd:SecurityAccessRequired roles="ADMINISTRATOR,ASSISTANT DIRECTORS">
		        		<tr class='totals'>
					      	<td colspan='2'>Division Budget</td>
		        			<td><fmt:formatNumber value='${curdivbudget}' type='currency' /></td>
		        			<td>&nbsp;</td>
		        			<td>&nbsp;</td>
		        			<esd:SecurityAccessRequired permissions="TRAVEL-EXPENSE-EDIT-BUDGET,TRAVEL-EXPENSE-DELETE-BUDGET">
		        				<td class='options'>&nbsp;</td>
		        			</esd:SecurityAccessRequired>
		        		</tr>
		        		<tr class='totals'>
		        			<td colspan='2'>Division Available Funds</td>
		        			<td><fmt:formatNumber value='${curdivbudget - subtotal_ytd}' type='currency' /></td>
		        			<td>&nbsp;</td>
		        			<td>&nbsp;</td>
		        			<esd:SecurityAccessRequired permissions="TRAVEL-EXPENSE-EDIT-BUDGET,TRAVEL-EXPENSE-DELETE-BUDGET">
		        				<td class='options'>&nbsp;</td>
		        			</esd:SecurityAccessRequired>
		        		</tr>
	        		</esd:SecurityAccessRequired>
        		</c:if>
        		<tr class='totals'>
        			<td colspan='2'>Totals</td>
        			<td><fmt:formatNumber value='${SUMMARY.budget}' type='currency' /></td>
        			<td><fmt:formatNumber value='${SUMMARY.claimed}' type='currency' /></td>
        			<td><fmt:formatNumber value='${SUMMARY.deficit}' type='currency' /></td>
        			<esd:SecurityAccessRequired permissions="TRAVEL-EXPENSE-EDIT-BUDGET,TRAVEL-EXPENSE-DELETE-BUDGET">
        				<td class='options'>&nbsp;</td>
        			</esd:SecurityAccessRequired>
        		</tr>
      		</c:when>
        	<c:otherwise>
        		<tr><td colspan='${colspan}'>No budgets found.</td></tr>
        	</c:otherwise>
        </c:choose>
      </table>
    </form>
	</body>
	
</html>
