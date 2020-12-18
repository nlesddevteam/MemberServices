<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
         		 com.awsd.personnel.*,
         		  com.awsd.personnel.profile.*,
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
<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>

<esd:SecurityCheck permissions="TRAVEL-EXPENSE-VIEW" />

<%
User usr = null;
TravelClaims claims = null;
TreeMap year_map = null;
TreeMap pending_approval = null;
TreeMap approved = null;
LinkedHashMap payment_pending = null;
TreeMap paid_today = null; 
TravelClaim claim = null;
Iterator iter = null;
Iterator y_iter = null;
Map.Entry item = null;
DecimalFormat df = null;
DecimalFormat dollar_f =  null;
TravelBudget budget = null;
Iterator p_iter = null;

  int c_cnt = 0;
  
  usr = (User) session.getAttribute("usr");
  
  claims = usr.getPersonnel().getTravelClaims();
  budget = usr.getPersonnel().getCurrrentTravelBudget();
	//populate initial objects from database
  if(usr.getUserPermissions().containsKey("TRAVEL-CLAIM-SUPERVISOR-VIEW")){
    pending_approval = usr.getPersonnel().getTravelClaimsPendingApproval();
  }
  else
  {
    pending_approval = null;
  }
  if(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW"))
  {
	    approved = usr.getPersonnel().getTravelClaimsApproved();
	    payment_pending = usr.getPersonnel().getTravelClaimsPaymentPending();
	    paid_today = usr.getPersonnel().getTravelClaimsPaidToday();
  }
  else
  {
    approved = null;
    payment_pending = null;
    paid_today = null;
  }

  df = new DecimalFormat("#,##0");
  dollar_f = new DecimalFormat("$#,##0");
  
   
%>


  			<script>			
			
			$(document).ready(function(){    
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");   
        		
    			$.cookie('backurl', 'supervisor_approval.jsp', {expires: 1 });
    			
    				 $(".claimsToApproveTable").DataTable({
    					  "order": [[ 0, "desc" ]],
    					  "lengthChange": false,
    					  "paging":   false,
    					  "responsive": true
    				 });	
        		
        		
        	});
			
			 function showit(target){
					document.getElementById(target).style.display = 'block';
					
					}
			 function hideit(target){
					document.getElementById(target).style.display = 'none';
					
					}		
			
			</script>
   <img class="pageHeaderGraphic" src="/MemberServices/Travel/includes/img/check-pencil.png" style="max-width:100px;" border=0/>     
<div class="siteHeaderRed">Travel Claims Pending Your Approval</div>  
<div class="siteBodyTextBlack">Below are a list of claims awaiting your approval. View the claim and review in detail and either approve or decline the claim.</div>

     <table class="table table-condensed table-striped table-bordered claimsToApproveTable" style="font-size:11px;background-color:White;" width="100%">
		  					<thead>
		  						<tr style="text-transform:uppercase;font-weight:bold;"> 
		  						<th width="10%">SUBMITTED</th>
		  						<th width="20%">NAME</th>
						        <th width="40%" >TYPE /  TITLE</th>						        
						        <th width="10%">STATUS</th>				        
		  						<th width="10%" >AMOUNT</th>  				
		  						<th width="10%">OPTIONS</th>
						  		</tr>      		
							</thead>		
							<tbody>   
        	
			<%		        
		        String TheName = "";
		        String title="";
        		String titleFix="";
        		String pdDescription="";
		   	
			   		int counter=0;
					c_cnt=0;
					if(usr.getUserPermissions().containsKey("TRAVEL-CLAIM-SUPERVISOR-VIEW")){
					if((pending_approval != null)&& (pending_approval.size() > 0)) {
						
				   		
						iter = pending_approval.entrySet().iterator();
						counter=0;
					
				   		while(iter.hasNext())
				        {
				        	item = (Map.Entry) iter.next();				        	
				        	Personnel p = new Personnel(((Integer)item.getKey()).intValue());				        
				        	
				        	p_iter = ((Vector)item.getValue()).iterator();		
				        	
				        	
				        	
				        	while(p_iter.hasNext())
				            {  	
				        	claim = (TravelClaim) p_iter.next();
				        	
				        		if(!(claim instanceof PDTravelClaim)){
				        			title=Utils.getMonthString(claim.getFiscalMonth()) + " " + Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear());
				        			titleFix = "<span style='background-color:#1c90ec;'>&nbsp;&nbsp;</span> Monthly Claim: " + title;			        			
				        		}else{				        			
				        			title=("<span style=\"background-color:#ff8400;\">&nbsp;&nbsp;</span> PD Claim: " + ((PDTravelClaim)claim).getPD().getTitle().replaceAll("[^A-Za-z0-9()\\[\\] ]", ""));
				        			pdDescription =((PDTravelClaim)claim).getPD().getDescription().replaceAll("[^A-Za-z0-9()\\[\\] ]", "");
				        			titleFix=(title.replace("'", "&#8217;"));
				        		}
				        		%>				        		
				        		<tr>
				        		<td style="vertical-align:middle;"><%=claim.getSubmitDate() %></td>	
				        		<td style="vertical-align:middle;"><span style="text-transform:Capitalize;"><%=p.getFullName() %></span></td>				        					        		
				        		<td style="vertical-align:middle;"><%=titleFix %>	
				  				<% if(claim instanceof PDTravelClaim){%>
				  				<br/><span style="color:Grey;"><%=pdDescription %></span>
				  				<%} %>  				
				  				</td>				  				
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
																		<span style="color:DarkGreen;"><i class="fas fa-hand-holding-usd"></i> PAID</span>
                        												
																		</c:when>
																		<c:otherwise>
																		<span style="color:Red;"><i class="fas fa-question"></i> UNKNOWN</span>
																		</c:otherwise>
																		</c:choose>				
				  				</td>
				  				<td style="vertical-align:middle;">$<%=claim.getSummaryTotals().getSummaryTotal() %></td>  				
				  				<td style="vertical-align:middle;">
				  				<a href='#' class="btn btn-xs btn-primary" onclick="loadMainDivPage('viewTravelClaimDetails.html?id=<%=claim.getClaimID()%>');"><i class="fas fa-eye"></i> VIEW</a>
				  				</td>
								</tr>	
																	        	
				       <% }} } %>						
						<%} %>
						</tbody>
						</table>

	