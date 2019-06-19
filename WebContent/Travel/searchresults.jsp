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
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>

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
  DecimalFormat dollar_f =  null;
  usr = (User) session.getAttribute("usr");
  int resultsCount = 0;
  iter = ((TreeMap) request.getAttribute("SEARCH_RESULTS")).entrySet().iterator();
  df = new DecimalFormat("#,##0");
  dollar_f = new DecimalFormat("$#,##0.00");
  sdf_title = new SimpleDateFormat("EEE, MMM dd, yyyy");
%>

    
    <script>
			
			
			$(document).ready(function(){    
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");
        		
    			});
			
			
	 </script>
    <script>

  $(function() {

    $( "#claimentSearchResults" ).accordion({
    	collapsible: true,
        autoHeight: false,
	 	active: false,
	 	heightStyle: 'content'
    });
   
  });

  </script>	
     

     Below are your <b style="text-transform: lowercase;">${param.type}</b> search results for <b>${param.txt}</b>. Click on the name tab to open to see a list of all claims and status for the search result.
    <br/><br/> 
     <div id="claimentSearchResults"> 	
 
 
  						 
            
                  
                    <%
                    	while(iter.hasNext())
                           {
                             item = (Map.Entry) iter.next();
                             emp = PersonnelDB.getPersonnel(((Integer)item.getKey()).intValue());
                             resultsCount++;
                    %>   <h3 class="accordionHeader1 siteSubHeaders"><%=emp.getFullName()%>(Vendor #: <%=(emp.getSDSInfo() != null)?emp.getSDSInfo().getVendorNumber():"vendor number not on file."%>)</h3>
                          <div style="background:White;"><div class="pageBody">
							<table id="claims-table" class="claimsTable" width="100%">
								<thead>
									<tr class="listHeader">
										<th width="15%" class="listdata" style="padding:2px;">Claim Date</th>
										<th width="10%" class="listdata" style="padding:2px;">Type</th>
										<th width="40%" class="listdata" style="padding:2px;">Title</th>
										<th width="10%" class="listdata" style="padding:2px;">Amount</th>
										<th width="20%" class="listdata" style="padding:2px;">Status</th>
										<th width="5%" class="listdata" style="padding:2px;">Function</th>
									</tr>
								</thead>
								<tbody>


                    <%      y_iter = ((Vector)item.getValue()).iterator();
                            while(y_iter.hasNext())
                            {
                              claim = (TravelClaim) y_iter.next();
                              if(true){
                    %>        <tr id="claimsrow" valign="top">
                    
                    			
                    			<td><%if(claim instanceof PDTravelClaim){%>              
		              <%=sdf_title.format(((PDTravelClaim)claim).getPD().getStartDate())%>
		              <%}else if(claim instanceof TravelClaim){%>
		              <%=Utils.getMonthString(claim.getFiscalMonth()) + " " +  Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) %>
		              <%}%></td>
                    			<td>
                    			<%if(claim instanceof PDTravelClaim){%> 
                    			<div style='text-align:center;font-size:10px;'>&nbsp;PD CLAIM&nbsp;</div>
                    			
                    			<%} else {%>
                    			<div style='text-align:center;font-size:10px;'>&nbsp;MONTHLY&nbsp;</div>
                    			<%}%>
                    			</td>
                    			
                    			
                    			<td><%if(claim instanceof PDTravelClaim){%> 
                    			<%= ((PDTravelClaim)claim).getPD().getTitle()  %> - <%=((PDTravelClaim)claim).getPD().getDescription() %>
                    			
                    			<%} else {%>
                    			Standard Travel Claim
                    			<%} %>
                    			</td>
                    			<td><%=dollar_f.format(claim.getSummaryTotals().getSummaryTotal()) %></td>
                    			<td>
                    			
                    			<c:set var="claimStatus" value="<%=claim.getCurrentStatus().getID()%>" />                                
									                                <c:choose>
									                                	<c:when test="${claimStatus eq 1 }">
									                                		<span style="color:DarkOrange;">PRE-SUBMISSION</span>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 2 }">
									                                		<span style="color:DarkViolet;">SUBMITTED</span>						                                	
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 3 }">
									                                		<span style="color:CornflowerBlue;">REVIEWED</span>							                                	
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 4 }">
									                                		<span style="color:Green;">APPROVED</span>							                                	
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 5 }">
									                                		<span style="color:Red;">REJECTED</span>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 6 }">
									                                		<span style="color:Blue;">PENDING INFO</span>
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
                        												
                        												<span style="color:DarkGreen;">PAID</span>
                        												
                        											
                        												</c:when>
                        												
                        												
                        												<c:when test="${((claimPaidDateStamp ne '0') and (claimExportDateStamp ne '0')) and (todayDateStamp le claimCheckDateStamp)}">
                        												
                        												<span style="color:Blue;">PROCESSED</span>
                        												
                        												</c:when>
                        												
                        												
                        												<c:when test="${claimPaidDateStamp ne '0' and claimExportDateStamp eq '0'}">
                        												<span style="color:Navy;">PROCESSING</span>
                        												
                        												</c:when>
                        												
                        												<c:otherwise>
                        												
                        												<span style="color:Red;">ERROR!</span>
									                                	 
                        												
                        												</c:otherwise>
                        												</c:choose>
									                                		
									                                		
									                                		
									                                		
									                                		
									                                		
									                                		
									                                		
									                                		
									                                		
									                                		
									                                		
									                                		
									                                	</c:when>
									                                	<c:otherwise>
									                                	 	<span style="color:Red;">ERROR!</span>
									                                	</c:otherwise>                             
									                                </c:choose>
                    			
                    			
                    			
                    			</td>
                    			<td><div align="center"><a href="#" onclick="loadMainDivPage('viewTravelClaimDetails.html?id=<%=claim.getClaimID()%>');">View</a></div></td>
                    			
                    		</tr>	
                    <%        }
                            }
                    %>    </tbody></table></div></div>
                    <%  }%>
                   
                   
              
        </div> 
         <br/><br/> 
        Your search returned <span style="color:Red;"><%=resultsCount%></span> results.
        <script>
        $(document).ready(function()
        		{
        		  $("tbody tr:even").css("background-color", "#E3F1E6");
        		});
        
        
       
        </script>
          



