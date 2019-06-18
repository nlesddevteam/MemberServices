<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 com.awsd.travel.*,
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

<esd:SecurityCheck permissions="TRAVEL-CLAIM-ADMIN" />




		<script>
			
			
			$(document).ready(function(){    
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");
        		
    			 $('.mclaims').click(function () {
 			    	$("#loadingSpinner").css("display","inline");
 			    });
        		
        		
			});
			
			var baseRatesList = new Array();
			var approvedRatesList = new Array();
			var dateStart = new Array();  
			   </script>
	<script src="includes/js/Chart.min.js"></script>	
	<div id="printJob">	
      
	<div class="claimHeaderText">Travel Claim KM Rates</div>
	<div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
           <div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	
	<br/>
	Below are the current and past rates for base and approved travel.<p>
	
	<br/><canvas id="rateChart" height="300"></canvas><br>	       
    <form name="add_claim_item_form">
      <table id="claims-table" width="100%" class="claimsTable">
     		<tr style="border-bottom:1px solid grey;" class="listHeader">
      		<td width="20%" class="listdata">Effective Start Date</td>
      		<td width="20%" class="listdata">Effective End Date</td>
      		<td width="20%" class="listdata">Base Rate</td>
      		<td align="right" width="15%" class="listdata">Approved Rate</td>
      		<td></td>
      		</tr>
      	<c:choose>
      		<c:when test="${fn:length(RATES) > 0}">
      		
      		
      		<c:forEach items="${RATES}" var="rule">
      			
      			
      			<script>
      			
   				baseRatesList.push(<c:out value="${rule.baseRate}" />);
      			approvedRatesList.push(<c:out value="${rule.approvedRate}" />);
				</script> 
      			
      			
      			
      				<tr style="border-bottom:1px dashed silver;">
      				      				
      					<td class="field_content dateTest"><fmt:formatDate pattern="MMMM d, yyyy" value="${rule.effectiveStartDate}"/></td>      					
      					<td class="field_content"><fmt:formatDate pattern="MMMM d, yyyy" value="${rule.effectiveEndDate}"/></td>
      					<td class="field_content"><span class="baseRateData">${rule.baseRate}</span></td>      					
      					<td class="field_content approvedRateDate">${rule.approvedRate}</td>
      					<td align="right" class="field_content">
      						<a href='#' class="mclaims" onclick="loadMainDivPage('addKmRate.html?sdate=${rule.effectiveStartDateFormatted}&edate=${rule.effectiveEndDateFormatted}');")><img src="includes/img/viewsm-off.png" class="img-swap" title="View Rate" border=0 style="padding-top:3px;padding-bottom:3px;"></a> &nbsp; 
							<a class='edit' href="#" onclick="opendeletedialog('${rule.effectiveStartDateFormatted}','${rule.effectiveEndDateFormatted}','${rule.baseRate}','${rule.approvedRate}');")><img src="includes/img/deletesm-off.png" class="img-swap" border=0 title="Delete Rate" style="padding-top:3px;padding-bottom:3px;"></a>
      					</td>
      				</tr>
        		</c:forEach>
        		
        		
        	
      		</c:when>
        	<c:otherwise>
        		<tr><td colspan='5' style="color:Red;">No rates found.</td></tr>
        	</c:otherwise>
        </c:choose>
       
      </table>
      
      
      
    </form>
    
  
      
    
    
    </div>
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
                    <button type="button" class="btn btn-default" data-dismiss="modal" id="buttonleft"></button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" id="buttonright"></button>
                </div>
            </div>
        </div>
    </div>
    
    <script>
    $('document').ready(function(){
    $("#claims-table tr:even").not(':first').css("background-color", "#FFFFFF");
    $("#claims-table tr:odd").css("background-color", "#E3F1E6");
     
    $('td.dateTest').each(function() { 
        var dateFormat = $(this).text();           
        dateStart.push(dateFormat);     			
		 
    });
    
    });  
   
    </script>
	
  
    
  <script>
     
     
     var ctx = document.getElementById('rateChart').getContext('2d');
     ctx.canvas.height = 100;
     var rateChart = new Chart(ctx, {
    	 
       type: 'line',       
       data: {
         labels: dateStart,
         datasets: [{
           label: 'Base Rate',
           data: baseRatesList,
           backgroundColor: "rgba(255,153,0,0.5)"
         }, {
           label: 'Approved Rate',
           data: approvedRatesList,
           
        	   backgroundColor: "rgba(153,255,51,0.2)"
         }]
       },options: {

    	   scales: {
               xAxes: [{
                       display: true
                       
                   }],
               yAxes: [{
                       display: true,                       
                       ticks: {
                           beginAtZero: false,
                           steps: 10,
                           stepValue: 0.05,
                           min: 0.25,
                           max: 0.65
                       }
                   }]
           },
       },
    	   
     });
     
     </script>
  
 
  