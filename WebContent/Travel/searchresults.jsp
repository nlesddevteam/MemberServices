<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 com.awsd.travel.*,
                 com.awsd.common.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"%>

<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<esd:SecurityCheck permissions="TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW,TRAVEL-CLAIM-SUPERVISOR-VIEW,TRAVEL-CLAIM-SEARCH" />

<%
  User usr = null;  
  Personnel emp = null;
  TravelClaims claims = null;
  TravelClaim claim = null;
  Vector y_claims = null;
  Iterator iter = null;
  Iterator y_iter = null;
  Map.Entry item = null;
  DecimalFormat df = null;
  SimpleDateFormat sdf_title = null;
  SimpleDateFormat pdsdf_title = null;
  DecimalFormat dollar_f =  null;
  usr = (User) session.getAttribute("usr");
  int resultsCount = 0;
  iter = ((TreeMap) request.getAttribute("SEARCH_RESULTS")).entrySet().iterator();
  df = new DecimalFormat("#,##0");
  dollar_f = new DecimalFormat("$#,##0.00");
  sdf_title = new SimpleDateFormat("EEE, MMM dd, yyyy");
  pdsdf_title = new SimpleDateFormat("yyyy-MM-dd");
%>
<c:set var="cntr" value="0"/>
    <script>
			$(document).ready(function(){    
        		//clear spinner on load        		
        		
    			$('#loadingSpinner').css("display","none");
        		});
			
			$(function() {
 $( "#claimentSearchResults" ).accordion({
    	collapsible: true,
        autoHeight: false,
	 	active: false,
	 	heightStyle: 'content'
    });
   
  });

  </script>	
  

    <script>   
 $('document').ready(function(){	 
		  $(".searchResults").DataTable({
			  "responsive": true,
		  "order": [[ 0, "desc" ]],
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
	                    columns: [ 0, 1, 2, 3,4,5,6 ]
	                }
	            },
	        	{
	                extend: 'print',
	                //orientation: 'landscape',
	                footer:true,
	                messageTop: 'Travel/PD Claims',
	                messageBottom: null,
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3,4,5,6]
	                }
	            }
	        ],		  
		  "lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]]
	  
	  
	  });	
	 
 });
    </script>
  
  <img class="pageHeaderGraphic" src="/MemberServices/Travel/includes/img/search.png" style="max-width:200px;" border=0/> 
 
 <div class="pageHeader">
		<span id="searchResultsNumber"></span> Search Results Found
</div>
<div class="pageBodyText">   
<%if(request.getAttribute("msgERROR")!=null){%>
					<div class="alert alert-danger no-print"  style="margin-top:10px;margin-bottom:10px;padding:5px;"><%=request.getAttribute("msgERROR")%></div> 
<%} %>
<%if(request.getAttribute("msgOK")!=null){%>	
					<div class="alert alert-success no-print"  style="margin-top:10px;margin-bottom:10px;padding:5px;"><%=request.getAttribute("msgOK")%></div> 
<%} %>
     Below are your <b style="text-transform: lowercase;">${param.type}</b> search results for <b>${param.txt}</b> sorted by date. Click on the name tab to open to see a list of all claims and status for the search result.
    <br/><br/> 
    <div style="clear:both;"></div>
     <div id="claimentSearchResults">  
 <%
                    	while(iter.hasNext())
                           {
                             item = (Map.Entry) iter.next();
                             emp = PersonnelDB.getPersonnel(((Integer)item.getKey()).intValue());
                             resultsCount++;
                      
                             
                    %>
                    
                   
                    
                   
                       <h3  class="accordionHeader1 siteSubHeaders" id="acd<%=resultsCount%>"><b><%=emp.getFullName()%></b><div style='float:right;'><%=resultsCount%></div><%=(emp.getSDSInfo() != null)?" (Vendor #: "+emp.getSDSInfo().getVendorNumber():"(<span style='color:red;'><i class='fas fa-exclamation-triangle'></i> ERROR: Vendor number not on file. <i class='fas fa-exclamation-triangle'></i></span>"%>)</h3>
                          <div id="acdBody<%=resultsCount%>"><div class="pageBody">
                           
                          
                          
                            <table class="searchResults table table-condensed table-striped table-bordered" style="font-size:11px;background-color:white;" width="100%">						
								<thead>
								<tr style="text-transform:uppercase;">  
										<th width="10%">CREATED</th>										
										<th width="10%">TYPE/MONTH</th>
										<th width="45%">TITLE</th>
										<th width="10%">AMOUNT</th>
										<th width="10%">SUPERVISOR</th>
										<th width="10%">STATUS</th>
										<th width="5%">FUNCTION</th>
									</tr>
								</thead>
								<tbody>

                    <%      y_iter = ((Vector)item.getValue()).iterator();
                            while(y_iter.hasNext())
                            {
                              claim = (TravelClaim) y_iter.next();
                              if(true){
                    %>        <tr valign="middle"  style="vertical-align:middle;">
                    
                    			<%if(claim instanceof PDTravelClaim){%>      
                    			<td style="vertical-align:middle;">
                    			<%=pdsdf_title.format(((PDTravelClaim)claim).getPD().getStartDate())%>                    			
                    			</td>                     			
		             			 <%}else if(claim instanceof TravelClaim){%>
		              			<td style="vertical-align:middle;"><%=claim.getCreatedDate() %> </td> 		              				             
		             			 <%} else {%>
		             			 <td style="vertical-align:middle;color:silver;">N/A</td> 		             			 
		             			 <%} %>                  			
                    			<%if(claim instanceof PDTravelClaim){%> 
                    			<td style="vertical-align:middle;background-color:#ff8400;text-align:center;font-size:11px;color:white;font-weight:bold;">&nbsp;PD CLAIM&nbsp;</td>                     			
                    			<%} else {%>
                    			<td style="vertical-align:middle;background-color:#1c90ec;text-align:center;font-size:11px;color:white;font-weight:bold;">&nbsp;MONTHLY&nbsp;</td>                    			
                    			<%}%>
                    		 <%if(claim instanceof PDTravelClaim){%> 
                    		 <td style="vertical-align:middle;"><b><%= ((PDTravelClaim)claim).getPD().getTitle()  %></b><br/><%=((PDTravelClaim)claim).getPD().getDescription() %></td>
                    		<%} else {%>
                    		 <td style="vertical-align:middle;">
                    		 <c:set var="travelClaimMonth" value="<%=claim.getFiscalMonth() %>"/>		              			
				              			<b>
				              			<c:choose>
				              			<c:when test="${travelClaimMonth eq 0 }">
				              			JANUARY
				              			</c:when>
				              			<c:when test="${travelClaimMonth eq 1 }">
				              			FEBRUARY
				              			</c:when>
				              			<c:when test="${travelClaimMonth eq 2 }">
				              			MARCH
				              			</c:when>
				              			<c:when test="${travelClaimMonth eq 3 }">
				              			APRIL
				              			</c:when>
				              			<c:when test="${travelClaimMonth eq 4 }">
				              			MAY
				              			</c:when>
				              			<c:when test="${travelClaimMonth eq 5 }">
				              			JUNE
				              			</c:when>
				              			<c:when test="${travelClaimMonth eq 6 }">
				              			JULY
				              			</c:when>
				              			<c:when test="${travelClaimMonth eq 7 }">
				              			AUGUST
				              			</c:when>
				              			<c:when test="${travelClaimMonth eq 8 }">
				              			SEPTEMBER
				              			</c:when>
				              			<c:when test="${travelClaimMonth eq 9 }">
				              			OCTOBER
				              			</c:when>
				              			<c:when test="${travelClaimMonth eq 10 }">
				              			NOVEMBER
				              			</c:when>
				              			<c:when test="${travelClaimMonth eq 11 }">
				              			DECEMBER
				              			</c:when>
				              			<c:otherwise><span style="color:silver;">N/A</span></c:otherwise>
				              			</c:choose>
				              			CLAIM</b><br/>
				              			for fiscal year <%=claim.getFiscalYear() %>
                    		  </td>
                    			<%} %>
                    		<td style="vertical-align:middle;"><%=dollar_f.format(claim.getSummaryTotals().getSummaryTotal()) %></td>
                    		 <td style="vertical-align:middle;"><%=(claim.getSupervisor()!=null)?claim.getSupervisor().getFullName():"N/A" %></td>
                    			<td style="vertical-align:middle;">
                    			<c:set var="claimStatus" value="<%=claim.getCurrentStatus().getID()%>" />                                
									                                <c:choose>
									                                	<c:when test="${claimStatus eq 1 }">									                                	
									                                			<span style="color:DarkOrange;"><i class="far fa-file-alt"></i> PRE-SUBMISSION</span>				                                		
									                                    </c:when>
									                                	<c:when test="${claimStatus eq 2 }">
									                                			<span style="color:DarkViolet;"><i class="fas fa-sign-in-alt"></i> SUBMITTED</span>										                               	                                	
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 3 }">
									                                			<span style="color:CornflowerBlue;"><i class="fas fa-book-reader"></i> REVIEWED</span>									                                	                                	
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 4 }">
									                                			<span style="color:Green;"><i class="far fa-check-square"></i>  APPROVED</span>										                                					                                	
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 5 }">
									                                			<span style="color:Red;"><i class="far fa-times-circle"></i> REJECTED</span>									                                		
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 6 }">
									                                			<span style="color:Blue;"><i class="far fa-question-circle"></i> PENDING INFO</span>
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
                        													</c:when>
                        													<c:when test="${((claimPaidDateStamp ne '0') and (claimExportDateStamp ne '0')) and (todayDateStamp le claimCheckDateStamp)}">
                        															<span style="color:Blue;"><i class="fas fa-clipboard-check"></i> PROCESSED</span>
                        													</c:when>
                        													<c:when test="${claimPaidDateStamp ne '0' and claimExportDateStamp eq '0'}">
                        															<span style="color:Navy;"><i class="fas fa-cogs"></i> PROCESSING</span>
                        													</c:when>
                        												<c:otherwise>
                        															<span style="color:Red;"><i class="fas fa-exclamation-triangle"></i> ERROR!</span>									                                	 
                        												</c:otherwise>
                        												</c:choose>
									                                	</c:when>
									                                	<c:otherwise>
									                                	 	<span style="color:Red;"><i class="fas fa-exclamation-triangle"></i> ERROR!</span>
									                                	</c:otherwise>                             
									                                </c:choose>                    			
                    			
                    			
                    			</td>
                    			<td style="vertical-align:middle;"><a href="#" style="color:white;" class="btn btn-xs btn-primary" onclick="loadMainDivPage('viewTravelClaimDetails.html?id=<%=claim.getClaimID()%>');"><i class="far fa-eye"></i> VIEW</a></td>
                    			
                    		</tr>	
                    <%   }
                            }
                            %>    </tbody></table></div></div>
                    <%  }%>                  
                   
              
        </div> 
         <br/><br/> 
        Your search returned <span style="color:Red;"><%=resultsCount%></span> results.
        
        
        
  </div>                  

<script>
$("#searchResultsNumber").text(<%=resultsCount%>);
$(document).ready(function(){    
	var totalResults = <%=resultsCount%>+1;
	for(colorCycle=0;colorCycle<totalResults;colorCycle++) {
	var R = Math.floor(Math.random() * 256);
	var G = Math.floor(Math.random() * 256);
	var B = Math.floor(Math.random() * 256);
	bgColor = "rgb(" + R + "," + G + "," + B +",0.4)";
	bgColorL = "rgb(" + R + "," + G + "," + B +",0.1)";
	$("#acd"+colorCycle+"").css("background",bgColor).css("color","black");
	$("#acdBody"+colorCycle+"").css("background",bgColorL);	
	}
});
</script>


