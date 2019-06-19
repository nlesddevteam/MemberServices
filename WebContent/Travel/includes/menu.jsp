<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
         		 com.awsd.personnel.*,
                 com.awsd.travel.*,
                 com.awsd.travel.bean.*,
                 com.awsd.common.*,
                 com.esdnl.util.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"%>

<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="TRAVEL-EXPENSE-VIEW" />

<%
User usr = null;
TravelClaims claims = null;
TreeMap year_map = null;
TreeMap pending_approval = null;
TreeMap approved = null;
LinkedHashMap payment_pending = null;
LinkedHashMap pre_submission=null;
LinkedHashMap rejected=null;																																																																																				
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
	    pre_submission = usr.getPersonnel().getTravelClaimsPreSubmission();
	   rejected = usr.getPersonnel().getTravelClaimsRejected();
  }
  else
  {
    approved = null;
    payment_pending = null;
    paid_today = null;
    pre_submission = null;
    rejected = null;
  }

  df = new DecimalFormat("#,##0");
  dollar_f = new DecimalFormat("$#,##0");
%>




<script src="includes/js/menu.js"></script>
   <script>
   $(document).ready(function () {
    $('.mclaims').click(function () {
    	$("#loadingSpinner").css("display","inline");
    });
});
   
   </script>
   
   

<div id="loadingSpinner" style="display:none;"><div id="spinner"><img src="includes/img/processing3.gif" width="100" border=0><br/>Loading data, please wait...<div id="secondMessage" style="display:none;"></div></div></div>
<div id="TravelClaimMenu" style="z-index:999;">
  <ul>  
     
     <li class="active has-sub"><a href="#"><i class="fa fa-fw fa-folder-open-o"></i> File</a>
        <ul>
           <li><a href="index.jsp"><i class="fa fa-fw fa-home"></i> Home</a></li>                       
           <li><a href="#" class="mclaims" onclick="closeMenu();loadMainDivPage('addTravelClaim.html');"><i class="fa fa-fw fa-plus"></i> New Claim</a></li> 
           <li><a href="#" class="mclaims" onclick="closeMenu();loadMainDivPage('calculatedistance.jsp');"><i class="fa fa-fw fa-car"></i> Distance Calculator</a></li>                 
           <li><a href='#' title='Print this page (pre-formatted)' onclick="closeMenu();jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:15px;><img width=400 src=includes/img/nlesd-colorlogo.png></div><br/><br/>'});"><i class="fa fa-fw fa-print"></i> Print</a></li>
          
          
           <li><a href="../navigate.jsp"><i class="fa fa-fw fa-reply"></i> Exit to MS</a></li>
        </ul>
     </li>
     
     <esd:SecurityAccessRequired permissions="TRAVEL-CLAIM-ADMIN,TRAVEL-EXPENSE-VIEW-REPORTS,TRAVEL-EXPENSE-SDS-EXPORT">
		     <li class="has-sub"><a href="#"><i class="fa fa-fw fa-cog"></i> Admin</a>
		        <ul>
		        
		        
		        <%if(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-SDS-EXPORT")){%>
		           <li class="has-sub"><a href="#"><i class="fa fa-fw fa-money"></i> Payments</a>
		              <ul>
		              
		              <%
				   		int counter=0;
						c_cnt=0;
						if(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW")){
						if((approved != null)&& (approved.size() > 0)) {
							//count approved claims
							iter = approved.entrySet().iterator();
					        while(iter.hasNext()){
					          c_cnt += ((Vector)((Map.Entry)iter.next()).getValue()).size();
					        }
					      //getTravelClaimsApprovedByRegionAjax.html
					      	out.println("<li class='has-sub'><a href='#'><i class='fa fa-fw fa-check'></i> Approved (" +  c_cnt +")</a>" );
					      	out.println("<ul>");
					      	out.println("<li><a href='#' class='mclaims' onclick='closeMenu();loadMainDivPage(&quot;claimsApprovedByDate.html&quot;);'><i class='fa fa-fw fa-calendar-check-o'></i> By Date</a></li>");
							out.println("<li><a href='#' class='mclaims' onclick='closeMenu();loadMainDivPage(&quot;claimsApprovedByRegion.html&quot;);'><i class='fa fa-fw fa-th-large'></i> By Region</a></li>");
							out.println("</ul>");
							out.println("</li>");
							
						}
						}
						%>             
		              
		                             
		                 <%
					   		counter=0;
							c_cnt=0;
							if(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW")){
							if((paid_today != null)&& (paid_today.size() > 0)) {
								//get count of paid claims today
								iter = paid_today.entrySet().iterator();
						        while(iter.hasNext()){
						          c_cnt += ((Vector)((Map.Entry)iter.next()).getValue()).size();
						        }
						       
						        out.println("<li><a href='#' class='mclaims' onclick='closeMenu();loadMainDivPage(&quot;claimsPaidTodayLetter.html&quot;);'><i class='fa fa-fw fa-money'></i> Processed Today " + "(" + c_cnt +")</a></li>");
								
								
							}
							}
							%>   		
		                 <%
					   		counter=0;
							c_cnt=0;
							if(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW")){
							if((payment_pending != null)&& (payment_pending.size() > 0)) {
								//get count of payment pending claims
								iter = payment_pending.entrySet().iterator();
						        while(iter.hasNext()){
						          c_cnt += ((Vector)((Map.Entry)iter.next()).getValue()).size();
						        }
								
								out.println("<li><a href='#' class='mclaims' onclick='closeMenu();loadMainDivPage(&quot;claimsPaymentPendingLetter.html&quot;);'><i class='fa fa-fw fa-warning'></i> Pending Info " + "(" + c_cnt +")</a></li>");
								
							}
							}
							%>
		                 		
									                 
		                </ul>
		           </li>
		        <%}%>
		         <esd:SecurityAccessRequired permissions="TRAVEL-CLAIM-ADMIN">  
		           <li class="has-sub"><a href="#"><i class="fa fa-fw fa-users"></i> Rules</a>
		              <ul>
		               	 <li><a href='#'  class="mclaims" onclick="closeMenu();loadMainDivPage('listSupervisorRules.html');"><i class="fa fa-fw fa-file-text-o"></i> List Rules</a></li>
		                 <li><a href='#'  class="mclaims" onclick="closeMenu();loadMainDivPage('addSupervisorRule.html');"><i class="fa fa-fw fa-user-plus"></i> New Rule</a></li>
		                
		              </ul>
		           </li>
		           <li class="has-sub"><a href="#"><i class="fa fa-fw fa-bar-chart"></i> Budgets</a>
		              <ul>
		              	 <li><a href='#'  class="mclaims" onclick="closeMenu();loadMainDivPage('listTravelBudgets.html');"><i class="fa fa-fw fa-file-text-o"></i> List Budgets</a></li>
		                 <li><a href='#'  class="mclaims" onclick="closeMenu();loadMainDivPage('addTravelBudget.html');"><i class="fa fa-fw fa-bus"></i> New Budget</a></li>
		                 
		              </ul>
		           </li>
		           <li class="has-sub"><a href="#"><i class="fa fa-fw fa-users"></i> Km Rates</a>
		              <ul>
		              	 <li><a href='#'  class="mclaims" onclick="closeMenu();loadMainDivPage('listKmRates.html');"><i class="fa fa-fw fa-file-text-o"></i> List Rates</a></li>
		                 <li><a href='#'  class="mclaims" onclick="closeMenu();loadMainDivPage('addKmRate.html');"><i class="fa fa-fw fa-user-plus"></i> New Rate</a></li>
		                 
		              </ul>
		           </li>
		        </esd:SecurityAccessRequired> 
		      <%if(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-VIEW-REPORTS")){%>
		           <li class="has-sub"><a href="#"><i class="fa fa-fw fa-book"></i> Reports</a>
		              <ul>
		                 <li><a href='#' class="mclaims"  onclick="closeMenu();loadMainDivPage('yearly_kms_detail.jsp');"><i class="fa fa-fw fa-car"></i> Yearly KM Details</a></li>
		                 <li><a href='#' class="mclaims" onclick="closeMenu();loadMainDivPage('yearly_claims_detail.jsp');"><i class="fa fa-fw fa-calendar-minus-o"></i> Yearly Details</a></li>
		               <li><a href='#' class="mclaims" onclick="closeMenu();loadMainDivPage('yearly_claims_detail_top.jsp');"><i class="fa fa-fw fa-calendar-minus-o"></i> Top Usage Report</a></li>
		              
		              <%
					   		int counter=0;
							c_cnt=0;
							if(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW")){
							if((pre_submission != null)&& (pre_submission.size() > 0)) {
								//get count of payment pending claims
								iter = pre_submission.entrySet().iterator();
						        while(iter.hasNext()){
						          c_cnt += ((Vector)((Map.Entry)iter.next()).getValue()).size();
						        }
								
								out.println("<li><a href='#' class='mclaims' onclick='closeMenu();loadMainDivPage(&quot;claimsPreSubmissionLetter.html&quot;);'><i class='fa fa-fw fa-warning'></i> Pre-Submission " + "(" + c_cnt +")</a></li>");
								
							}
							}
							%>
		                 <%
					   		counter=0;
							c_cnt=0;
							if(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW")){
							if((rejected != null)&& (rejected.size() > 0)) {
								//get count of payment pending claims
								iter = rejected.entrySet().iterator();
						        while(iter.hasNext()){
						          c_cnt += ((Vector)((Map.Entry)iter.next()).getValue()).size();
						        }
								
								out.println("<li><a href='#' class='mclaims' onclick='closeMenu();loadMainDivPage(&quot;claimsRejectedLetter.html&quot;);'><i class='fa fa-fw fa-warning'></i> Claims Rejected " + "(" + c_cnt +")</a></li>");
								
							}
							}
							%>						
		              
		              
		              </ul>
		           </li>
		      <%}%>
		      <%if(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-SDS-EXPORT")){%>
		           <li class="has-sub"><a href="#"><i class="fa fa-fw fa-download"></i> Export</a>
		              <ul>
		                 <li><a href='#' class="mclaims" onclick="closeMenu();loadMainDivPage('exportPaidTravelClaims.html');"><i class="fa fa-fw fa-check"></i> Claims to be Paid (SDS)</a></li>
		              </ul>
		           </li>
		      <%}%>
		        </ul>
		     </li>
	</esd:SecurityAccessRequired>	     
     
     
     <esd:SecurityAccessRequired permissions="TRAVEL-CLAIM-SUPERVISOR-VIEW">	     
		  
		     
		     <li><a href="#" class="mclaims" onclick="closeMenu();loadMainDivPage('supervisor_approval.jsp');"><i class="fa fa-fw fa-user"></i> Supervisor</a></li>
		     
		     
     </esd:SecurityAccessRequired>
     
     

		<li><a href='#'  class="mclaims" onclick="closeMenu();loadMainDivPage('myProfile.html');"><i class="fa fa-fw fa-user"></i> My Claims &amp; Profile</a></li>
     <li class="has-sub"><a href="#"><i class="fa fa-fw fa-question"></i> Help</a>
        <ul>
       	 <li><a href="#" class="mclaims" onclick="closeMenu();loadMainDivPage('calculatedistance.jsp');"><i class="fa fa-fw fa-car"></i> Distance Calculator</a></li>     	 
       	  <li><a href="/includes/files/policies/doc/1504103153912.pdf" onclick="closeMenu();" target="_blank"><i class="fa fa-fw fa-calendar-minus-o"></i> Travel Policy FIN-401</a></li>
       	  <li><a href="/includes/files/policies/doc/1504103153928.pdf" onclick="closeMenu();" target="_blank"><i class="fa fa-fw fa-calendar-minus-o"></i> Travel Policy Regs</a></li>
          <!-- <li><a href="includes/doc/TravelClaimInformationApril2013.pdf" onclick="closeMenu();" target="_blank"><i class="fa fa-fw fa-car"></i> Distances Doc (PDF)</a></li>-->
          <li><a href="mailto:travelclaimsupport@nlesd.ca?subject=Travel Claim Support Request" onclick="closeMenu();"><i class="fa fa-fw fa-envelope-o"></i> Contact Us</a></li>
        </ul>
     </li>
  </ul>
</div>


<p>


