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
    			
        	});
			
			 function showit(target){
					document.getElementById(target).style.display = 'block';
					
					}
			 function hideit(target){
					document.getElementById(target).style.display = 'none';
					
					}		
			
			</script>
   


    <div id="loadMes" style="display:none;color:white;background-color:Red;padding:3px;text-align:center;margin-bottom:10px;"> &nbsp; <b>*** PLEASE WAIT ***</b><br/>Retrieving the selected claim... &nbsp; </div>
	
     
     
  
   		   		
   		<table id="claims-table" class="claimsTable" width="100%">
  		
  		
  		
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
				        	
				        	counter++;
				        	Personnel p = new Personnel(((Integer)item.getKey()).intValue());
				        	TheName = p.getFullNameReverse().replace("'", "&#8217;");
				        	
				        			        	
				        
				        	
				        	out.println("<tr class='listHeader'><th width='100%' colspan=3 class='listdata' style='padding:2px;'>&nbsp;For " + TheName + "</th></tr>" );
				        	
				        	p_iter = ((Vector)item.getValue()).iterator();
				        	
				        	%>
				        	
				        	<tr class='listSubHeader' style='border-top:1px solid white;color:White;'>
				        		
				        		 				
  				<th width="75%" style="border-right:1px solid white;color:Grey;">&nbsp;CLAIM TYPE and TITLE</th>
  				<th width="15%" style="border-right:1px solid white;color:Grey;">&nbsp;AMOUNT</th>  				
  				<th width="10%" style="border-right:1px solid white;color:Grey;">&nbsp;VIEW</a></th>
				        		
				        	
				        	<%
				        	
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
				        		
				        		 				
  				<td width="75%" class="listdata" style="padding:2px;border:1px solid silver;"><%=titleFix %>
  				<% if(claim instanceof PDTravelClaim){%>
  				<br/><span style="color:Grey;">
  				<%=pdDescription %>
  				<%} %>
  				</span>
  				</td>
  				<td width="15%" class="listdata" style="padding:2px;border:1px solid silver;">&nbsp;$<%=claim.getSummaryTotals().getSummaryTotal() %></td>  				
  				<td width="10%" class="listdata" style="padding:2px;border:1px solid silver;">&nbsp;<a href='#' class='mclaims' onclick="loadMainDivPage('viewTravelClaimDetails.html?id=<%=claim.getClaimID()%>');showit('loadMes');">View</a></td>
				        		
				        		
				        		<%
				        		
				        		
				            }
				        	
				        %>
				        	</tr>
				        	<tr><td colspan=3>&nbsp;</td></tr>
				       <% }
				        
				        
						
					} else {
						
						out.println("Nothing to Approve.");
					} 
					}
			   		%>  
		           
		           
		       
		   
		     
   
   	</table>  
       
	
	<br/>
	
	


