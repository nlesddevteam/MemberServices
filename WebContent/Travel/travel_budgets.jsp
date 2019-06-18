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

<esd:SecurityCheck permissions="TRAVEL-CLAIM-ADMIN,TRAVEL-CLAIM-SUPERVISOR-VIEW" />


    <script type="text/javascript">
    	$('document').ready(function() {
    		
    	$('#loadingSpinner').css("display","none");
    	
    	$('.mclaims').click(function () {
		    	$("#loadingSpinner").css("display","inline");
		    });
    		
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
        	var schoolyear = $("#school-year").val();
        	loadMainDivPage("listTravelBudgets.html?school_year=" + schoolyear);
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
        
    		if(top != self) {
        	resizeIFrame('claim_details', 360);
        }
        
    	});
    </script>
    
    <c:set var="curdiv" value="0" />
    <c:set var="curdivbudget" value="0" />  
    <c:set var='colspan' value="5" />
    <c:set var='subtotal_budget' value="0"/>
	  <c:set var='subtotal_ytd' value="0"/>
	  <c:set var='subtotal_deficit' value="0"/>
	
	
	<div class="claimHeaderText">Travel Budgets ${sy}</div>
	
	
    <form id='add_claim_item_form' name="add_claim_item_form" action='listTravelBudgets.html' method='post'>
    	School Year:<br/>
    	
    	
    				<select id='school-year' name='school_year'>
    					<c:forEach items='${FISCALYEARS}' var='year'>
    						<option value='${year}' ${(sy eq year) ? 'SELECTED' :''}>${year}</option>
    					</c:forEach>
    				</select>
    		<br/>	
    		<div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
           	<div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 		
    <br/>
	<div id="printJob"> 			
    			
      <table id="claims-table" width="100%" class="claimsTable">
     		<tr class="listHeader">
      		<td width="20%" class="listdata">Employee</td>
      		<td width="15%" class="listdata">Fiscal Year</td>
      		<td width="20%" class="listdata">Budgeted</td>
      		<td width="15%" class="listdata">Fiscal YTD</td>
      		<td width="20%" class="listdata">Available</td>
      		<esd:SecurityAccessRequired permissions="TRAVEL-EXPENSE-EDIT-BUDGET,TRAVEL-EXPENSE-DELETE-BUDGET">
      			<c:set var='colspan' value="6" />
      			<td width="10%" class="listdata">Options</td>
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
      </div>
    </form>
    
    <script>
    $('document').ready(function(){
    $("#claims-table tr:even").not(':first').css("background-color", "#FFFFFF");
    $("#claims-table tr:odd").css("background-color", "#E3F1E6");
    })
    
    
    
    </script>
    
	
