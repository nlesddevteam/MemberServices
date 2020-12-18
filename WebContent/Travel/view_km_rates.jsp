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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>
<esd:SecurityCheck permissions="TRAVEL-CLAIM-ADMIN" />

<script>
		var baseRatesList = new Array();
		var approvedRatesList = new Array();
		var dateStart = new Array();  

		$(document).ready(function(){    
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");        		
    			$(".kmRatesTable").DataTable({
    				  "order": [[ 0, "desc" ]],
    				  "responsive": true,
    				  columnDefs: [{
    				        "targets": [0,1],
    				        "type": 'date',
    				     }],
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
    			                    columns: [ 0, 1, 2, 3]
    			                }
    			            },
    			        	{
    			                extend: 'print',
    			                //orientation: 'landscape',
    			                footer:true,
    			                messageTop: 'Travel/PD Claims',
    			                messageBottom: null,
    			                exportOptions: {
    			                    columns: [ 0, 1, 2, 3]
    			                }
    			            }
    			        ],		  
    				  "lengthMenu": [[25, 50, 100, -1], [25, 50, 100, "All"]]    			  
    			  
    			  });	
    			 
        		
        		
        		
        	});			
			
			
			
			
			
 </script>
 
	<script src="includes/js/Chart.min.js"></script>	
	
	  <img class="pageHeaderGraphic" src="/MemberServices/Travel/includes/img/rates.png" style="max-width:200px;" border=0/> 
 
 <div class="pageHeader">Travel Claim KM Rates</div>
<div class="pageBodyText">  
	
	<div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
      <div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	
	<br/>
	Below are the current and past rates for base and approved travel.	
	<br/><br/>
   <a class="btn btn-sm btn-success" href="#" onclick="loadingData();loadMainDivPage('addKmRate.html');return false;"><i class="fa fa-fw fa-user-plus"></i> Set New Rate</a>
	<a class="btn btn-sm btn-dark" href="https://www.gov.nl.ca/exec/hrs/working-with-us/auto-reimbursement/" target="_blank"><i class="fa fa-fw fa-file-text-o"></i> Check Gov NL Rates</a>

	<br/><canvas id="rateChart" height="200"></canvas><br>	       
    <form name="add_claim_item_form">
     
     <table class="kmRatesTable table table-condensed table-striped table-bordered" style="font-size:11px;background-color:white;" width="100%">						
								<thead>
								<tr style="text-transform:uppercase;">    
					      		<th width="25%">Effective Start Date</th>
					      		<th width="25%">Effective End Date </th>
					      		<th width="20%">Base Rate</th>
					      		<th width="20%">Approved Rate</th>
					      		<th width="10%">OPTIONS</th>
					      		</tr>
					      		</thead>
      		<tbody>
      	<c:choose>
      		<c:when test="${fn:length(RATES) > 0}">     		
      		
      		<c:forEach items="${RATES}" var="rule">      			
      			
      			<script>      			
   				baseRatesList.push(<c:out value="${rule.baseRate}" />);
      			approvedRatesList.push(<c:out value="${rule.approvedRate}" />);
				</script> 
      			
      			<tr valign="middle">
      				      	
      					<td class="dateTest"><fmt:formatDate pattern="MMMM d, yyyy" value="${rule.effectiveStartDate}"/></td>      					
      					<td><fmt:formatDate pattern="MMMM d, yyyy" value="${rule.effectiveEndDate}"/></td>
      					<td><span class="baseRateData">${rule.baseRate}</span></td>      					
      					<td class="approvedRateDate">${rule.approvedRate}</td>
      					<td>
      						<a href='#' class="btn btn-xs btn-primary" onclick="loadingData();loadMainDivPage('addKmRate.html?sdate=${rule.effectiveStartDateFormatted}&edate=${rule.effectiveEndDateFormatted}');")>VIEW</a>
							<a class='btn btn-xs btn-danger' href="#" onclick="opendeletedialog('${rule.effectiveStartDateFormatted}','${rule.effectiveEndDateFormatted}','${rule.baseRate}','${rule.approvedRate}');")>DEL</a>
      					</td>
      				</tr>
        		</c:forEach>
        		
        		
        	
      		</c:when>
        	
        </c:choose>
       </tbody>
      </table>
   </form>
   <div align="center">
   <a class="btn btn-sm btn-success" href="#" onclick="loadingData();loadMainDivPage('addKmRate.html');return false;"><i class="fa fa-fw fa-user-plus"></i> Set New Rate</a>
	<a class="btn btn-sm btn-dark" href="https://www.gov.nl.ca/exec/hrs/working-with-us/auto-reimbursement/" target="_blank"><i class="fa fa-fw fa-file-text-o"></i> Check Gov NL Rates</a>
   </div>
    </div>
  
    <div id="travelModal" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">                    
                    <h4 class="modal-title" id="maintitle"></h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <p id="title1"></p>
                    <p id="title2"></p>
 		    		<p id="title3"></p>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-sm btn-danger" data-dismiss="modal" id="buttonleft"></button>
                    <button type="button" class="btn btn-sm btn-primary" data-dismiss="modal" id="buttonright"></button>
                </div>
            </div>
        </div>
    </div>
    
      
  <script>
  $('document').ready(function(){    	
     
	  $('td.dateTest').each(function() { 
	        var dateFormat = $(this).text();           
	        dateStart.push(dateFormat);    		
			 
	    });    	  
	  
	  
	  
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
  });
     </script>
  
 
  