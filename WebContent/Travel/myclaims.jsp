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
SimpleDateFormat sdf_date = null;
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
int counter9 = 0;
int counter10 = 0;
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
  sdf_date= new SimpleDateFormat("yyyy-MM-dd");
%>
<style>
input { border:1px solid silver;}
</style>
 <script>
 $('document').ready(function(){
	 
		
	 //Initialize the dataTable
	  $("#claimsTableList").DataTable({
				  "order": [[ 0, "desc" ]],			
				  "lengthMenu": [[10, 20, 50, 100, -1], [10, 20, 50, 100, "All"]],		
				  "responsive": true,
				   dom: 'Blfrtip',
			        buttons: [			        	
			        	//'colvis',
			        	//'copy', 
			        	//'csv', 
			        	'excel', 
			        	{
			                extend: 'pdfHtml5',
			                footer:true,
			                //orientation: 'landscape',
			                messageTop: '<%=usr.getPersonnel().getFirstName().toLowerCase()%> <%=usr.getPersonnel().getLastName().toLowerCase().replace("'","") %>&apos;s Travel/PD Claims ',
			                messageBottom: null,
			                exportOptions: {
			                    columns: [ 0, 1, 2, 3,4,5 ]
			                }
			            },
			        	{
			                extend: 'print',
			                //orientation: 'landscape',
			                footer:true,
			                messageTop: '<%=usr.getPersonnel().getFirstName().toLowerCase()%> <%=usr.getPersonnel().getLastName().toLowerCase().replace("'","")  %>&apos;s Travel/PD Claims',
			                messageBottom: null,
			                exportOptions: {
			                    columns: [ 0, 1, 2, 3,4,5 ]
			                }
			            }
			        ],
				 			  
				  "footerCallback": function ( row, data, start, end, display ) {
			            var api = this.api(), data;			 
			            // Remove the formatting to get integer data for summation
			            var intVal = function ( i ) {
			                return typeof i === 'string' ?
			                    i.replace(/[\$,]/g, '')*1 :
			                    typeof i === 'number' ?
			                        i : 0;
			            };
			 
			            // Total over all pages
			            totalAmount = api.column(4).data().reduce( function (a, b) { return (intVal(a) + intVal(b)).toFixed(2); }, 0 );
			           
			            
			            // Total over this page NOT NEEDED YET ONE PAGE FOR ITEMS
			            pageTotalAmount = api.column( 4, { page: 'current'} ).data().reduce( function (a, b) {return (intVal(a) + intVal(b)).toFixed(2) ;}, 0 );
			 			
			            
			            // Update footer with values
			            $( api.column( 4 ).footer() ).html('$'+pageTotalAmount+' ($'+totalAmount +')');			            
				  
				  
				  }
				  
				  
			  });	
});
 
 
 
 
 
    </script>
   <script>
   $(document).ready(function () {
	   $('#loadingSpinner').css("display","none");
	 $.cookie('backurl', 'myclaims.jsp', {expires: 1 });
});
  </script>
  
<script src="includes/js/Chart.min.js"></script>  
<img class="pageHeaderGraphic" src="/MemberServices/Travel/includes/img/folders2.png" style="max-width:100px;" border=0/>
<div class="pageHeader">
		<%=usr.getPersonnel().getFirstName().toLowerCase()%> <%=usr.getPersonnel().getLastName().toLowerCase() %>'s Travel/PD Claims
</div>
<div class="pageBodyText"> 		
  	Below is a complete listing of your previous Travel and PD claims and their current status, as well as a graph of the amounts you have claimed in the last 4 years.
 	 <%  if((claims != null) && (claims.size() > 0)){    	    	 
		iter = claims.entrySet().iterator();			   					
   		int counter=0;
   		%>   	
   		<br/><br/>
   		<table id="claimsTableList" class="table table-condensed table-striped" style="font-size:11px;" width="100%">
  		<thead>
  			<tr style="text-transform:uppercase;font-weight:bold;">  			
  				<th width="10%" >Claim Date</th>
  				<th width="10%" >Type</th>
  				<th width="30%">Title</th>
  				<th width="10%" >Supervisor</th>
  				<th width="10%" >Amount</th>
  				<th width="15%">Status</th>
  				<th width="5%" >Options</th>
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
          
          if(year_map.get("MONTHLY-CLAIMS") != null)
          {
        	   ismonthlyused=true;
        	  y_iter = ((Vector)year_map.get("MONTHLY-CLAIMS")).iterator();
             	  
        	  
        	  
              	while(y_iter.hasNext())
              	{
                	claim = (TravelClaim) y_iter.next();                	
                	 cnt_month++;                	
        		
        			//int theYear = Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear());
        			//int curStats = claim.getCurrentStatus().getID();        			
        		
    	%>
                	
                	<tr style="vertical-align:middle;">                    
                   			 <%    String monthClaim= String.format("%02d", claim.getFiscalMonth()+1);  %>                    	
                    		<td><%=Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear())+"-"+ monthClaim +"-01" %> </td>
                    		<td>MONTHLY</td>
                    		<td>Standard Travel Claim</td>
                    		<td><span style="text-transform:Capitalize;"><%=(claim.getSupervisor().getFullNameReverse()!=null)?claim.getSupervisor().getFullNameReverse():"N/A" %></span></td>
                    		<td><%=curr_df.format(claim.getSummaryTotals().getSummaryTotal()) %></td>
                    		<td>
                    		<c:set var="claimStatus" value="<%=claim.getCurrentStatus().getID()%>" />        
                    		<c:choose>
									                                	<c:when test="${claimStatus eq 1 }">							                                	
									                                	<span style="color:DarkOrange;"><i class="far fa-file-alt"></i> PRE-SUBMISSION</span>									                                		
									                                	<%counter1++;%>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 2 }">
									                                		<span style="color:DarkViolet;"><i class="fas fa-sign-in-alt"></i> SUBMITTED</span>	
									                                		<%counter2++;%>				                                	
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 3 }">
									                                		<span style="color:CornflowerBlue;"><i class="fas fa-book-reader"></i> REVIEWED</span>	
									                                		<%counter3++;%>					                                	
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 4 }">
									                                		<span style="color:Green;"><i class="far fa-check-square"></i>  APPROVED</span>	
									                                		<%counter4++;%>					                                	
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 5 }">
									                                		<span style="color:Red;"><i class="far fa-times-circle"></i> REJECTED</span>
									                                		<%counter5++;%>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 6 }">
									                                		<span style="color:Blue;"><i class="far fa-question-circle"></i> PENDING INFO</span>
									                                		<%counter6++;%>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 7 }">							                                	
									                                		<% Date now = new java.util.Date();	%>
									                                		<c:set var="todayDate" value="<%=new java.util.Date() %>" />									                                										                                	
									                                		<c:set var="todayDateStamp" value="<%=now.getTime()%>" />									                                		
									                                		<c:set var="claimExportDate" value='<%=(claim.getExportDate() != null) ? claim.getExportDate() :"0" %>'/>																            															               	
																            <c:set var="claimExportDateStamp" value='<%=(claim.getExportDate() != null) ? claim.getExportDate().getTime() : "0" %>'/>
																            <c:set var="claimPaidDate" value='<%=(claim.getPaidDate() != null) ? claim.getPaidDate() :"0" %>'/>	
																            <c:set var="claimPaidDateStamp" value='<%=(claim.getPaidDate() != null) ? claim.getPaidDate().getTime() : "0" %>'/>																         
																          <!-- After 30 days, set as paid for cosmetic reasons.-->
																            <c:set var="claimCheckDate" value='<%=(claim.getExportDate() != null) ? claim.getExportDate().getTime() : "0"%>'/>															               							                
																            <c:set var="claimCheckDateStamp" value="${(60*60*24*30*1000) + claimCheckDate}" /> 
																        <c:choose>
                        												<c:when test="${((claimPaidDateStamp ne '0') and (claimExportDateStamp ne '0')) and (todayDateStamp gt claimCheckDateStamp)}">
                        												<span style="color:DarkGreen;"><i class="fas fa-hand-holding-usd"></i> PAID</span>
                        												<%counter7++;%>                        												
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
                        												</c:when>
                        												<c:when test="${((claimPaidDateStamp ne '0') and (claimExportDateStamp ne '0')) and (todayDateStamp le claimCheckDateStamp)}">
                        												<span style="color:Blue;"><i class="fas fa-clipboard-check"></i> PROCESSED</span>
                        												<%counter10++;%>
                        												</c:when>
                        												<c:when test="${claimPaidDateStamp ne '0' and claimExportDateStamp eq '0'}">
                        												<span style="color:Navy;"><i class="fas fa-cogs"></i> PROCESSING</span>
                        												<%counter9++;%>
                        												</c:when>
                        												<c:otherwise>
                        												<span style="color:Red;"><i class="fas fa-exclamation-triangle"></i> ERROR!</span>
									                                	 	<%counter8++;%>                        												
                        												</c:otherwise>
                        												</c:choose>			                                	
									                                	</c:when>
									                                	<c:otherwise>
									                                	 	<span style="color:Red;"><i class="fas fa-exclamation-triangle"></i> ERROR!</span>
									                                	 	<%counter8++;%>
									                                	</c:otherwise>                             
									                                </c:choose>
                    			
                    			
                    			
                    			</td>
                    			<td><div align="center"><a href="#" class="btm btn-xs btn-primary" onclick="loadingData();loadMainDivPage('viewTravelClaimDetails.html?id=<%=claim.getClaimID()%>');">VIEW</a></div></td>
                    			
                    		</tr>	
              	<%	}  %>
      		
      		
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
                    
                    <tr valign="top">                  
                    			<td><%=sdf_date.format(((PDTravelClaim)claim).getPD().getStartDate())%></td>                    			
                    			<td>PD CLAIM</td>               			
                    		   <td><b><%= ((PDTravelClaim)claim).getPD().getTitle().replace("\"","").replace("'","")  %></b><br/><%=((PDTravelClaim)claim).getPD().getDescription().replace("\"","").replace("'","") %> </td>
                    			<td><span style="text-transform:Capitalize;"><%=(((PDTravelClaim)claim).getSupervisor()!=null)?((PDTravelClaim)claim).getSupervisor().getFullNameReverse():"N/A" %></span></td>
                    			<td><%=curr_df.format(claim.getSummaryTotals().getSummaryTotal()) %></td>
                    			<td><c:set var="claimStatus" value="<%=claim.getCurrentStatus().getID()%>" />         			
                    			                   	 <c:choose>
									                                	<c:when test="${claimStatus eq 1 }">
									                                	<span style="color:DarkOrange;"><i class="far fa-file-alt"></i> PRE-SUBMISSION</span>
									                                	<%counter1++;%>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 2 }">
									                                		<span style="color:DarkViolet;"><i class="fas fa-sign-in-alt"></i> SUBMITTED</span>	
									                                		<%counter2++;%>				                                	
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 3 }">
									                                		<span style="color:CornflowerBlue;"><i class="fas fa-book-reader"></i> REVIEWED</span>	
									                                		<%counter3++;%>					                                	
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 4 }">
									                                		<span style="color:Green;"><i class="far fa-check-square"></i> APPROVED</span>	
									                                		<%counter4++;%>						                                	
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 5 }">
									                                		<span style="color:Red;"><i class="far fa-times-circle"></i> REJECTED</span>
									                                		<%counter5++;%>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 6 }">
									                                		<span style="color:Blue;"><i class="far fa-question-circle"></i> PENDING INFO</span>
									                                		<%counter6++;%>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 7 }">
									                                		
									                                	<% Date now = new java.util.Date();	%>
									                                		<c:set var="todayDate" value="<%=new java.util.Date() %>" />									                                										                                	
									                                		<c:set var="todayDateStamp" value="<%=now.getTime()%>" />
									                                		
									                                		<c:set var="claimExportDate" value='<%=(claim.getExportDate() != null) ? claim.getExportDate() :"0" %>'/>																            															               	
																            <c:set var="claimExportDateStamp" value='<%=(claim.getExportDate() != null) ? claim.getExportDate().getTime() : "0" %>'/>
																            <c:set var="claimPaidDate" value='<%=(claim.getPaidDate() != null) ? claim.getPaidDate() :"0" %>'/>	
																            <c:set var="claimPaidDateStamp" value='<%=(claim.getPaidDate() != null) ? claim.getPaidDate().getTime() : "0" %>'/>
																         
																          <!-- After 30 days, set as paid for cosmetic reasons.-->
																            <c:set var="claimCheckDate" value='<%=(claim.getExportDate() != null) ? claim.getExportDate().getTime() : "0"%>'/>															               							                
																            <c:set var="claimCheckDateStamp" value="${(60*60*24*30*1000) + claimCheckDate}" /> 
												<c:choose>
                        												<c:when test="${((claimPaidDateStamp ne '0') and (claimExportDateStamp ne '0')) and (todayDateStamp gt claimCheckDateStamp)}">
                        												                        												
                        												<span style="color:DarkGreen;"><i class="fas fa-hand-holding-usd"></i> PAID</span>
                        												<%counter7++;%>
                        												
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
                        												
                        												</c:when>
                        												<c:when test="${((claimPaidDateStamp ne '0') and (claimExportDateStamp ne '0')) and (todayDateStamp le claimCheckDateStamp)}">
                        												
                        												 <span style="color:Blue;"><i class="fas fa-clipboard-check"></i> PROCESSED</span>
                        												<%counter10++;%>
                        												</c:when>
                        												
                        												
                        												<c:when test="${claimPaidDateStamp ne '0' and claimExportDateStamp eq '0'}">
                        												<span style="color:Navy;"><i class="fas fa-cogs"></i> PROCESSING</span>
                        												<%counter9++;%>
                        												</c:when>
                        												<c:otherwise>
                        												<span style="color:Red;"><i class="fas fa-exclamation-triangle"></i> ERROR!</span>
									                                	 	<%counter8++;%>
                        												</c:otherwise>
                        												</c:choose>
									                                	
									                                	</c:when>
									                                	<c:otherwise>
									                                	 	<span style="color:Red;"><i class="fas fa-exclamation-triangle"></i> ERROR!</span>
									                                	 	<%counter8++;%>
									                                	</c:otherwise>                             
									                                </c:choose>
                    			
                    			
                    			
                    			</td>
                    			<td><div align="center"><a href="#" class="btm btn-xs btn-primary" onclick="loadingData();loadMainDivPage('viewTravelClaimDetails.html?id=<%=claim.getClaimID()%>');">VIEW</a></div></td>
                    			
                    		</tr>	
                    
                    <%	}  } 
          					 counter++;
         					 } %>
   							</tbody>
   							<tfoot>
			            <tr style="font-weight:bold;font-size:10px;">
			            <td></td>
			               <td></td>
			               <td></td>
			                <td style="text-align:right">TOTAL:</th>
			                <td colspan=2></td>			               
			               <td></td>			              
			            </tr>
        		</tfoot>
   							
   							
   							</table>  
      						 <%
       
    					 }else{ %>
    						 <br/><br/>
    						 <div class="alert alert-danger" style="text-align:center;">Sorry, you have no claims currently on file through this account. If you believe this to be in error, please contact your supervisor or Travel Claim Support.</div>
    						 
    					 <%} %>
	
	<br/>
	
	<% if(counter1 > 0) { %>
	<div class="alert alert-danger"><b>NOTICE:</b> Any claims that are in Pre-Submission Status (prior to July 2016) and have NOT been submitted for processing, are now EXPIRED. Please contact Travel Claim Support listed below on this page.</div>
   	<%} %>	

	<table align="center" border=0 style="padding:3px;max-width:600px;min-width:300px;border:1px solid silver;font-size:11px;">
	<thead>
	 <tr style="border-bottom:1px solid silver;">
	<th colspan=5><b>NUMBER OF CLAIMS YOU HAVE ON FILE: <span style="color:Red;"><%=counter1 + counter2 + counter3 + counter4 + counter5 + counter6 + counter7 + counter8 + counter9 + counter10%></span></b></th>
	</tr>
	</thead>
	<tbody>
	<tr><td><span style="color:DarkGreen;">PAID claims</span></td><td><%=counter7 %></td><td style="border-left:1px solid silver;">&nbsp;</td><td><span style="color:Blue;">PROCESSED claims</span></td><td><%=counter10 %></td></tr>	
	<tr><td><span style="color:Navy;">PROCESSING claims</span></td><td><%=counter9 %></td><td style="border-left:1px solid silver;">&nbsp;</td><td><span style="color:Black;">PENDING claims</span></td><td><%=counter6 %></td></tr>
	<tr><td><span style="color:Green;">APPROVED claims</span></td><td><%=counter4 %></td><td style="border-left:1px solid silver;">&nbsp;</td><td><span style="color:CornflowerBlue;">REVIEWED claims</span></td><td><%=counter3 %></td></tr>
	<tr><td><span style="color:DarkViolet;">SUBMITTED claims</span></td><td><%=counter2 %></td><td style="border-left:1px solid silver;">&nbsp;</td><td><span style="color:DarkOrange;">PRE-SUBMISSION claims:</span></td><td><%=counter1 %></td></tr>
	<tr><td><span style="color:Red;">REJECTED claims</span></td><td><%=counter5 %></td><td style="border-left:1px solid silver;">&nbsp;</td><td><span style="color:Red;">ERRORED claims</span></td><td><%=counter8 %></td></tr>
	</tbody>	
	</table>
<br/>
 	
 	         <% if (graphPDItem =="true" || graphTCItem =="true") { %>
	<br/> 
          <%if(claimCounter >= 20) {
				claimCounter=20;
          };%>

     <div class="no-print" style="text-align:center;">
      <div class="pageSubSubHeader">Your last <%=claimCounter %> Paid Travel Claims. (up to max last 7 years)</div>  
          
	  <br/><canvas id="myChartofClaims" height="300"></canvas><br>	 	
	
	<div class="pageSubSubHeader">Your All-Time Expenditure Breakdown (Regular and PD Claims)</div> <br/>	
	  <canvas id="myBreakdownChart" height="330"></canvas>	
	</div>
		
 
    
<% 
//max 10 to graph

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
<% }  else {	%>		
	<br/><br/>
	 <div class="alert alert-warning" style="text-align:center;margin-top:10px;margin-bottom:10px;padding:5px;"><b>NO GRAPHING CHART DATA AVAILABLE</b>.<br/> You do not have any paid claims history to graph. Once you have paid claims, you will see a breakdown of your travel expenditures and claims history charted below.	</div>
<%} %> 


   
     </div>
 
     
         