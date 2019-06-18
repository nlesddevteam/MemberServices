<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
         		 com.awsd.personnel.*,
                 com.awsd.travel.*,
                 com.awsd.travel.bean.*,
                 com.awsd.common.*,
                 com.esdnl.util.*,
                 org.apache.commons.lang.StringUtils,
                 org.apache.commons.lang.StringUtils.*,                  
                 java.util.*,
                 java.io.*,                 
                 java.text.*"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="TRAVEL-EXPENSE-VIEW" />

<%
User usr = null;
TravelClaims claims = null;
TreeMap year_map = null;
TravelClaim claim = null;
Iterator iter = null;
Iterator y_iter = null;
Map.Entry item = null;
DecimalFormat df = null;
DecimalFormat dollar_f =  null;
Iterator p_iter = null;
SimpleDateFormat sdf_title = null;
DecimalFormat curr_df = null;
DecimalFormat chart_df = null;
DecimalFormat percent_fm = null;
int year = 0;
int yearLimit = 0;
int preClaims = 0;
int claimCounter = 0;
int counter1 = 0;
int counter2 = 0;
int counter3 = 0;
int counter4 = 0;
int counter5 = 0;
int counter6 = 0;
int counter7 = 0;
int counter8 = 0;
String graphTCItem = null;
String graphPDItem = null;

double overall_km_total_amount = 0;
double overall_meals_total = 0;
double overall_lodging_total = 0;
double overall_other_total = 0;
double overall_total = 0;

String overall_km_pc = null;
String overall_meals_pc = null;
String overall_lodging_pc = null;
String overall_other_pc = null;


List<String> claimName = new ArrayList<String>();
List<Integer> claimAmount = new ArrayList<Integer>();

List<String> claimNamesToGraph = new ArrayList<String>();
List<Integer> claimAmountsToGraph = new ArrayList<Integer>();

  int c_cnt = 0;
  
  usr = (User) session.getAttribute("usr");
  
  claims = usr.getPersonnel().getTravelClaims();
  year = Calendar.getInstance().get(Calendar.YEAR);
 	yearLimit = year-5;
  df = new DecimalFormat("#,##0.00");
  dollar_f = new DecimalFormat("$#,##0.00");
  percent_fm = new DecimalFormat("##0.00");
  curr_df = new DecimalFormat("$#,##0.00");
  sdf_title = new SimpleDateFormat("EEE, MMM dd, yyyy");
%>





   <script>
   $(document).ready(function () {
	   $('#loadingSpinner').css("display","none");
});

   
   function showit(target){
		document.getElementById(target).style.display = 'block';
		
		}
		function hideit(target){
		document.getElementById(target).style.display = 'none';
		
		}
   
   
   </script>
   <script src="includes/js/Chart.min.js"></script>
   


    <div id="loadMes" style="display:none;color:white;background-color:Red;padding:3px;text-align:center;"> &nbsp; <b>*** PLEASE WAIT ***</b><br/>Retrieving your claim data... &nbsp; </div>
	
     
     
     <%  if((claims != null) && (claims.size() > 0)){
			
    	    	 
		iter = claims.entrySet().iterator();
			   					
   		int counter=0;
   		%>
   		<div class="claimHeaderText">All Your Travel/PD Claims and Status</div>
   		
   		<table id="claims-table" class="claimsTable" width="100%">
  		<thead>
  			<tr class="listHeader">
  				<th width="15%" class="listdata" style="padding:2px;">Claim Date</th>
  				<th width="10%" class="listdata" style="padding:2px;">Type</th>
  				<th width="45%" class="listdata" style="padding:2px;">Title</th>
  				<th width="10%" class="listdata" style="padding:2px;">Amount</th>
  				<th width="15%" class="listdata" style="padding:2px;">Status</th>
  				<th width="5%" class="listdata" style="padding:2px;">View</th>
  			</tr>
  		</thead>
  		<tbody>	
   		
   		<%
   		
   	  int cnt_month = -1;
        int cnt_pd =0;
        graphTCItem = "false";
        graphPDItem = "false";
        while(iter.hasNext())
        {
        	boolean ismonthlyused=false;
        	boolean ispdused=false;
          item = (Map.Entry) iter.next();
          
          year_map = ((TreeMap)item.getValue());
          
          
        
          
          %>  	
    	  
  	 
     		
  		<% 
          if(year_map.get("MONTHLY-CLAIMS") != null)
          {
        	  
        	  
        	  ismonthlyused=true;
        	  y_iter = ((Vector)year_map.get("MONTHLY-CLAIMS")).iterator();
        	
        	  %>
        	 
        	  <% 
        	  
        	  
        	  
              	while(y_iter.hasNext())
              	{
                	claim = (TravelClaim) y_iter.next();
                	
                	 cnt_month++;
                	
        			
        			//int theYear = Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear());
        			//int curStats = claim.getCurrentStatus().getID();        			
        			
        			
                  	
        			
        			
                  	
                  	
        		
                	
                	
                	
                	%>
                	
                	<tr id="claimsrow" valign="top">
                    
                    			
                    			<td><%=Utils.getMonthString(claim.getFiscalMonth()) + " " +  Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) %>
		              
		              
		              		              
		             </td>
                    			<td  style="padding:2px;">                    			
                    			<div style='text-align:center;background-color:#1c90ec;font-size:11px;color:white;font-weight:bold;'>&nbsp;MONTHLY&nbsp;</div>
                    			</td>
                    			
                    			
                    			<td>Standard Travel Claim</td>
                    			
                    			<td><%=curr_df.format(claim.getSummaryTotals().getSummaryTotal()) %></td>
                    			
                    	
                    	
                    					
                    			
                    			
                    			<td>
                    			
                    			<c:set var="claimStatus" value="<%=claim.getCurrentStatus().getID()%>" />        
                    		
                    		
                    		<c:if test="${claimStatus eq 7 }">
                    	<%   
                    	graphTCItem = "true";
                    	
                    	if( Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) >= (year-7)) {  			
                    	//if(claimCounter > 0) {
		               claimName.add("&quot;" + Utils.getMonthString(claim.getFiscalMonth()) + " " +  Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) + "&quot;");
                     claimAmount.add((int)claim.getSummaryTotals().getSummaryTotal());				
		             
                     claimCounter++;
                    	}
                    	
                    	
                    	overall_km_total_amount += claim.getSummaryTotals().getKMSTotal();
                        overall_meals_total += claim.getSummaryTotals().getMealSummary();
                        overall_lodging_total += claim.getSummaryTotals().getLodgingSummary();
                        overall_other_total += claim.getSummaryTotals().getOtherSummary();
                    	
                    	
		              %>
                    		</c:if>	
                    			                        
									                                <c:choose>
									                                	<c:when test="${claimStatus eq 1 }">									                                	
									                                	
									                                		<span style="color:DarkOrange;">PRE-SUBMISSION</span>									                                		
									                                	
									                                		<%counter1++;%>
									                                		
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 2 }">
									                                		<span style="color:DarkViolet;">SUBMITTED</span>	
									                                		<%counter2++;%>				                                	
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 3 }">
									                                		<span style="color:CornflowerBlue;">REVIEWED</span>	
									                                		<%counter3++;%>					                                	
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 4 }">
									                                		<span style="color:Green;">APPROVED</span>	
									                                		<%counter4++;%>					                                	
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 5 }">
									                                		<span style="color:Red;">REJECTED</span>
									                                		<%counter5++;%>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 6 }">
									                                		<span style="color:Blue;">PAYMENT PENDING</span>
									                                		<%counter6++;%>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 7 }">
									                                		<span style="color:DarkGreen;">PAID</span>
									                                		<%counter7++;%>
									                                	</c:when>
									                                	<c:otherwise>
									                                	 	<span style="color:Red;">ERROR!</span>
									                                	 	<%counter8++;%>
									                                	</c:otherwise>                             
									                                </c:choose>
                    			
                    			
                    			
                    			</td>
                    			<td><div align="center"><a href="#" onclick="loadMainDivPage('viewTravelClaimDetails.html?id=<%=claim.getClaimID()%>');showit('loadMes');">View</a></div></td>
                    			
                    		</tr>	
              	<%
              	
              	
              	
              	
              	
              	
              	
              	}
        	  
        	  
        	  
      		%>
      		
      		
      	<%
          	 
          }
          //next we write out the pd claims for the year
		  
          
          
          if(year_map.get("PD-CLAIMS") != null)
          {
        	  
				ispdused=true;
        	  	
        	  	       	  	
        	  	String title = null;
    		  	y_iter = ((Vector)year_map.get("PD-CLAIMS")).iterator();
              	
    		  	
    		  	while(y_iter.hasNext())
              	{
                    claim = (TravelClaim) y_iter.next();
                    title = ((PDTravelClaim)claim).getPD().getTitle();
                    
                    %>
                    
                    <tr id="claimsrow" valign="top">
                    
                    			
                    			<td>            
		              <%=sdf_title.format(((PDTravelClaim)claim).getPD().getStartDate())%>
		              </td>
                    			<td  style="padding:2px;">                    			
                    			<div style='text-align:center;background-color:#ff8400;font-size:11px;color:white;font-weight:bold;'>&nbsp;PD CLAIM&nbsp;</div>
                    			</td>
                    			
                    			
                    			<td> 
                    			<b><%= ((PDTravelClaim)claim).getPD().getTitle().replace("\"","").replace("'","")  %></b><br/><%=((PDTravelClaim)claim).getPD().getDescription().replace("\"","").replace("'","") %>                   			
                    			
                    			</td>
                    			<td><%=curr_df.format(claim.getSummaryTotals().getSummaryTotal()) %></td>
                    			
                    			                  			
                    			
                    			
                    			
                    			
                    			<td>
                    			
                    			<c:set var="claimStatus" value="<%=claim.getCurrentStatus().getID()%>" /> 
                    			
                    			                   			      
                    		
                    		
                    		<c:if test="${claimStatus eq 7 }">
                    	<%                    					
                    	graphPDItem = "true";
                    	    
                    	if( Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) >= (year-7)) {		
                    		//if(claimCounter > 0) {	
                    			
                    			
		               	claimName.add("&quot;PD " + sdf_title.format(((PDTravelClaim)claim).getPD().getStartDate()) + "&quot;");
                     	claimAmount.add((int)claim.getSummaryTotals().getSummaryTotal());	
                     	claimCounter++;
		         
                    	}
                    	
                    	overall_km_total_amount += claim.getSummaryTotals().getKMSTotal();
                        overall_meals_total += claim.getSummaryTotals().getMealSummary();
                        overall_lodging_total += claim.getSummaryTotals().getLodgingSummary();
                        overall_other_total += claim.getSummaryTotals().getOtherSummary();
                    	
		              %>
                    		</c:if>	
                    			
                    			
                    			
                    			
                    			                               
									                                 <c:choose>
									                                	<c:when test="${claimStatus eq 1 }">
									                                	<span style="color:DarkOrange;">PRE-SUBMISSION</span>
									                                	<%counter1++;%>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 2 }">
									                                		<span style="color:DarkViolet;">SUBMITTED</span>	
									                                		<%counter2++;%>				                                	
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 3 }">
									                                		<span style="color:CornflowerBlue;">REVIEWED</span>	
									                                		<%counter3++;%>					                                	
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 4 }">
									                                		<span style="color:Green;">APPROVED</span>	
									                                		<%counter4++;%>						                                	
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 5 }">
									                                		<span style="color:Red;">REJECTED</span>
									                                		<%counter5++;%>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 6 }">
									                                		<span style="color:Blue;">PAYMENT PENDING</span>
									                                		<%counter6++;%>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 7 }">
									                                		<span style="color:DarkGreen;">PAID</span>
									                                		<%counter7++;%>
									                                	</c:when>
									                                	<c:otherwise>
									                                	 	<span style="color:Red;">ERROR!</span>
									                                	 	<%counter8++;%>
									                                	</c:otherwise>                             
									                                </c:choose>
                    			
                    			
                    			
                    			</td>
                    			<td><div align="center"><a href="#" onclick="loadMainDivPage('viewTravelClaimDetails.html?id=<%=claim.getClaimID()%>');showit('loadMes');">View</a></div></td>
                    			
                    		</tr>	
                    
                    <%
                    
                    
                	
              	}
          	 
              
          } 
          
          counter++;
         
          
        }
        %>
   		</tbody></table>  
       <%
       
     }else{
    		out.println("No Claims found.");
   		}
  		
   		%>
	
	<br/>
	
	<% if(counter1 > 0) { %>
	<div class="alert alert-danger"><b>NOTICE:</b> Any claims that are in Pre-Submission Status (prior to July 2016) and have NOT been submitted for processing, are now EXPIRED. Please contact Travel Claim Support listed below on this page.</div>
   		
	<%} %>
	
	<div class="claimHeaderText">Number of Claims on File</div>  
	<br/> 
	<table border=0 style="padding:3px;max-width:400px;min-width:300px;border:1px solid silver;">
	<tr><td><span style="color:DarkGreen;">PAID claims</span></td><td><%=counter7 %></td></tr>	
	<tr><td><span style="color:Blue;">PAYMENT PENDING claims</span></td><td><%=counter6 %></td></tr>
	<tr><td><span style="color:Green;">APPROVED claims</span></td><td><%=counter4 %></td></tr>
	<tr><td><span style="color:CornflowerBlue;">REVIEWED claims</span></td><td><%=counter3 %></td></tr>
	<tr><td><span style="color:DarkViolet;">SUBMITTED claims</span></td><td><%=counter2 %></td></tr>
	<tr><td><span style="color:DarkOrange;">PRE-SUBMISSION claims:</span></td><td><%=counter1 %></td></tr>
	<tr><td><span style="color:Red;">REJECTED claims</span></td><td><%=counter5 %></td></tr>
	<tr><td><span style="color:Red;">ERRORED claims</span></td><td><%=counter8 %></td></tr>
	<tr style="border-top:1px solid silver;"><td><span style="color:black;font-weight:bold;">TOTAL Claims on file:</span></td><td><b><%=counter1 + counter2 + counter3 + counter4 + counter5 + counter6 + counter7 + counter8%></b></td></tr>
	
	
	</table>

	<script>
        $(document).ready(function()
        		{
        		  $("#claims-table tbody tr:even").css("background-color", "#E3F1E6");
        		});
        
        
       
        </script>
	
	<% if (graphPDItem =="true" || graphTCItem =="true") { %>
	<br/> 
          <%if(claimCounter >= 20) {
				claimCounter=20;
          };%>

     <div class="no-print">
      <div class="claimHeaderText">Your last <%=claimCounter %> Paid Travel Claims.</div>  
          
	  <br/><canvas id="myChartofClaims" height="300"></canvas><br>	 
	
	
	<div class="claimHeaderText">Your All-Time Expenditure Breakdown (Regular and PD Claims)</div> <br/>
	
	  <canvas id="myBreakdownChart" height="330"></canvas>
	
	</div>
		
 
    
<% 




//max 25 to graph

Collections.reverse(claimName);
Collections.reverse(claimAmount);

if(claimCounter >= 20) {
claimCounter=20;
claimNamesToGraph = claimName.subList(0,20);
claimAmountsToGraph =claimAmount.subList(0,20);
//out.print(claimNamesToGraph);
//out.print(claimAmountsToGraph);
} else {
	
claimNamesToGraph = claimName.subList(0,claimCounter);
claimAmountsToGraph =claimAmount.subList(0,claimCounter);
	//out.print(claimNamesToGraph);
	//out.print(claimAmountsToGraph);
}

String claimNames = StringUtils.join(claimNamesToGraph, ","); 
claimNames=claimNames.replace("&quot;","\'");


//String claimNames = StringUtils.join(claimName, ","); 
//claimNames=claimNames.replace("&quot;","\'");

overall_total = overall_km_total_amount + overall_meals_total + overall_lodging_total + overall_other_total;


overall_km_pc = percent_fm.format(overall_km_total_amount/overall_total * 100);
overall_meals_pc = percent_fm.format(overall_meals_total/overall_total * 100);
overall_lodging_pc = percent_fm.format(overall_lodging_total/overall_total * 100);
overall_other_pc = percent_fm.format(overall_other_total/overall_total * 100);




%>  

  <!-- NOTES 
  
  1. Make random color generator for graphing - DONE -
  2. Limit graph last 7 years? - done
  3. Add graph of individial claim data (pie) - done (overall)
  4. Implement PD claim data in chart and edit lables to say PD
  5. Only show PAID claims - Done
  
   -->   
   <script>

var ctx = document.getElementById("myChartofClaims");
ctx.height = 100;

var randomColorPlugin = {

		beforeUpdate: function(chart) {
	        var backgroundColor = [];
	        var borderColor = [];	       
	        for (var i = 0; i < chart.config.data.datasets[0].data.length; i++) {	          
	            var color = "rgba(" + Math.floor(Math.random() * 255) + "," + Math.floor(Math.random() * 255) + "," + Math.floor(Math.random() * 255) + ",";				
	            backgroundColor.push(color + "0.2)");
	            borderColor.push(color + "1)");
	        }
			chart.config.data.datasets[0].backgroundColor = backgroundColor;
	        chart.config.data.datasets[0].borderColor = borderColor;
	    }
	}; 
	
var ColorPluginPie = {

		beforeUpdate: function(chart) {
	        var backgroundColor = ['#F08080',' #1E90FF','#FFD700','#90EE90'];
	        var borderColor = ['#F08080',' #1E90FF','#FFD700','#90EE90'];	       
	        
			chart.config.data.datasets[0].backgroundColor = backgroundColor;
	        chart.config.data.datasets[0].borderColor = borderColor;
	    }
	}; 

Chart.pluginService.register(randomColorPlugin);

var myChartofClaims = new Chart(ctx, {
  type: 'bar',  
  options: { 
	  scales: {
	        xAxes: [{
	            ticks: {
	                fontSize: 9
	            }
	        }],
	        yAxes: [{
	            ticks: {
	                fontSize: 9
	            }
	        }]
	        
	    },

	  legend: {
          display: false,
          labels: {
              fontColor: 'rgb(255, 99, 132)',
             
          }
      },  
      title: {
          display: false,
          text: 'Your Last Paid Claims'
      }
  },
  showDatapoints: true,
  data: {
	  labels: [<%=claimNames%>],
	  
    	datasets: [{    	
    		backgroundColor: 'rgba(255, 99, 132, 0.2)',
            borderColor: 'rgba(255,99,132,1)',
            borderWidth: 1,            
      data:  <%=claimAmountsToGraph%> 
    }],
   
    
  }});


 
var ctx1 = document.getElementById("myBreakdownChart").getContext('2d');

Chart.pluginService.register(ColorPluginPie);
var myBreakdownChart = new Chart(ctx1, {
  type: 'pie',
  data: {
    labels: ["Kilometers: <%=overall_km_pc%>% (<%=curr_df.format(overall_km_total_amount)%>)", 
             "Meals: <%=overall_meals_pc%>% (<%=curr_df.format(overall_meals_total)%>)", 
             "Lodging: <%=overall_lodging_pc%>% (<%=curr_df.format(overall_lodging_total)%>)", 
             "Other: <%=overall_other_pc%>% (<%=curr_df.format(overall_other_total)%>)",],
    datasets: [{
    	backgroundColor: 'rgba(255, 99, 132, 0.2)',
        borderColor: 'rgba(255,99,132,1)',
        borderWidth: 1,  
      data: [ 	<%=overall_km_total_amount%>,<%=overall_meals_total%>,<%=overall_lodging_total%>,<%=overall_other_total%>]
    }]
  },
  
  options: {
	  	  
      title: {
         display: false,
         fontSize: 20,
         text: 'Your Expenditure Breakdown'
     },
     legend: {
         display: true,
         position: 'bottom',

     },
     responsive: false
 }

  
  
});


</script>

<% }  

	else {
	
		%>
		
	<br/><br/>
	 <div class="alert alert-warning" style="text-align:center;margin-top:10px;margin-bottom:10px;padding:5px;"><b>NO GRAPHING CHART DATA AVAILABLE</b>.<br/> You do not have any paid claims history to graph. Once you have paid claims, you will see a breakdown of your travel expenditures and claims history charted below.	</div>
	
	
	
		
	<%}


%>


