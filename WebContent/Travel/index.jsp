<%@ page language="java" session="true"
	import="com.awsd.security.*,
         		 com.awsd.personnel.*,
                 com.awsd.travel.*,
                 com.awsd.travel.bean.*,
                 com.awsd.common.*,
                 java.util.*,
                 java.io.*,
                 java.text.*,com.esdnl.util.*"
	isThreadSafe="false"%>
	
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd"%>

<esd:SecurityCheck />

<%
	User usr = null;
	usr = (User) session.getAttribute("usr");
	TreeMap pending_approval = null;
	Iterator iter = null;	
	int c_cnt = 0;
	if (usr.getUserPermissions().containsKey("TRAVEL-CLAIM-SUPERVISOR-VIEW")) {
		pending_approval = usr.getPersonnel().getTravelClaimsPendingApproval();
		if ((pending_approval != null) && (pending_approval.size() > 0)) {
			//count pending approval claims
			iter = pending_approval.entrySet().iterator();
			while (iter.hasNext()) {
				c_cnt += ((Vector) ((Map.Entry) iter.next()).getValue()).size();
			}
		}
	}
	
	TravelClaims claims = null;
	TreeMap year_map = null;
	TreeMap approved = null;
	TreeMap payment_pending = null;
	TreeMap paid_today = null;
	TravelClaim claim = null;
	Iterator y_iter = null;
	Map.Entry item = null;
	DecimalFormat df = null;
	DecimalFormat dollar_f = null;
	TravelBudget budget = null;
	claims = usr.getPersonnel().getTravelClaims();
	budget = usr.getPersonnel().getCurrrentTravelBudget();
	df = new DecimalFormat("#,##0");
	dollar_f = new DecimalFormat("$#,##0");
	
	ArrayList<TravelClaimKMRate> rates = TravelClaimKMRateDB. getTravelClaimKMRates(); 
	

	
%>


<c:set var="now" value="<%=new java.util.Date() %>" /> 	
<c:set var="theExpiredDate" value="<%=rates.get(0).getEffectiveEndDate() %>" /> 						
<fmt:formatDate value="${now}" pattern="yyyyMMdd" var="todayDate" />
<fmt:formatDate value="${theExpiredDate}" pattern="yyyyMMdd" var="expiredDate" />

<html>
<head>
<title>Travel Claim System</title>
<script>
	$("#loadingSpinner").css("display","none");
</script>
</head>
<body>
<input type="hidden" id="ccnt" name="ccnt" value="<%=c_cnt%>">	
<!-- Bootstrap Grid Variables xs (phones), sm (tablets), md (desktops), and lg (larger desktops). -->
<!-- class no-print for items not to print -->
<div id="pageContentBody">
<div class="alert alert-success" style="display:none;">
<b><i class="fas fa-exclamation-circle"></i> NEW UPDATE!</b><br/>We have added the ability to <b>attach/upload receipt(s)</b> now to a travel claim item for lodging and/or other charges where a receipt maybe required. This will prevent claim processing delays where receipts maybe required.
 </div>




<img class="pageHeaderGraphic" style="max-width:200px;display:none;" src="/MemberServices/Travel/includes/img/tclaim.png" border=0/>
	
<div style="display:block;">	
	Welcome 
		<span style="text-transform:Capitalize;font-weight:bold;">
			<%=usr.getPersonnel().getFirstName().toLowerCase() %> 
			<%=usr.getPersonnel().getLastName().toLowerCase() %>
		</span>
	to the NLESD Travel Claim system.  You are currently classified as 
		<span style="text-transform:Capitalize;font-weight:bold;">
			<%=usr.getPersonnel().getPersonnelCategory().getPersonnelCategoryName().toLowerCase() %>
		</span> 
	at	
		<b><%=(usr.getPersonnel().getSchool() != null? usr.getPersonnel().getSchool().getSchoolName(): "NONE")%></b>. 
	If this is incorrect, please contact your supervisor to have it updated.
	Please select a previous claim from the MY CLAIMS menu above to review or continue working on or start a NEW CLAIM. 
	If you have already started a claim for this month, it will be listed under MY PREVIOUS CLAIMS under MY CLAIMS menu above.
	<br/><br/>
	<span style="color: Red; font-weight: bold;">
		NOTE: You cannot start a second Monthly Claim for the same month in the current school year.
	</span>
	<br/><br/>
		Always make sure your	information in your profile is up-to-date with your correct mailing address and contact information. 
	<br/><br/>
	

	
	<div class="siteSubHeaderBlue">RATES PER KILOMETER:</div>
	Below are the kilometer rates as set by the Provincial Government <a href="https://www.gov.nl.ca/exec/tbs/working-with-us/auto-reimbursement/" target="_blank">Automobile Reimbursement Rates for Using a Private Vehicle at Work</a>.
	<c:if test="${todayDate gt expiredDate}">
	<script>
	$("#claimRateMessage").html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>PLEASE NOTE TRAVEL RATES HAVE EXPIRED</b><br/>Please wait until the official government rates have been approved before updating/editing a claim. You will NOT be able to add or edit a claim until new rates are assigned.");
	$("#claimRateMessage").css("display","block");
	</script>
	</c:if>
	<br/><br/>
	
	<table class="table table-bordered table-striped table-sm" style="padding-top:5px;width:100%;max-width:350px;font-size:11px;">
	<tr style="background-color:#6495ED;color:White;font-weight:bold;">
	<th>TYPE</th>
	<th>RATE</th>	
	</tr>
	<tr>
	<td>BASE</td>
	<td>$<%=rates.get(0).getBaseRate() %> <c:if test="${todayDate gt expiredDate}"><span style="color:Red;">EXPIRED</span></c:if></td>
	</tr>
	<tr>
	<td>APPROVED</td>
	<td>$<%=rates.get(0).getApprovedRate() %> <c:if test="${todayDate gt expiredDate}"><span style="color:Red;">EXPIRED</span></c:if></td>
	</tr>
	</table>
	
	Above  Government Rates are effective <b><%=rates.get(0).getEffectiveStartDateFormatted() %></b> thru to <b><%=rates.get(0).getEffectiveEndDateFormatted() %></b>. 
	<br/><br/>
	All employees are set at the Base rate by default. Positions that have been pre-approved will be set at the Approved rate automatically but those who are not in pre-approved positions require approval from Corporate Services.
	If you need to apply for the Approved rate, please contact Susan Hussey (Email: <a href="mailto:susanhussey@nlesd.ca?subject=Travel Approved Rate Request">susanhussey@nlesd.ca</a> &middot; Tel: 709-758-2382).
	
	<br/><br/>
	<div class="siteSubHeaderBlue">MEAL RATES:</div>
	Below are the maximum meals rates you are allowed to claim per day as outlined by the <a href="https://www.gov.nl.ca/exec/tbs/working-with-us/meal-rates/#rates" target="_blank">Provincial Government Meal Rates Policy</a>.
	
	<br/><br/>
	<table class="table table-bordered table-striped table-sm" style="width:100%;max-width:640px;font-size:11px;">
	<tr style="background-color:#6495ED;color:White;font-weight:bold;">
	<th>LOCATION</th>
	<th>BREAKFAST</th>
	<th>LUNCH</th>
	<th>DINNER</th>
	<th>TOTAL</th>
	</tr>
	<tr>
	<td>NL</td>
	<td>$8.00</td>
	<td>$14.00</td>
	<td>$21.70</td>
	<td>$43.70</td>
	</tr>
	<tr>
	<td>Other Provinces</td>
	<td>$10.15</td>
	<td>$16.40</td>
	<td>$23.65</td>
	<td>$50.20</td>
	</tr>
	<tr>
	<td>USA</td>
	<td>$10.15 (US)</td>
	<td>$16.40 (US)</td>
	<td>$23.65 (US)</td>
	<td>$50.20 (US)</td>
	</tr>
	<tr>
	<td>Other</td>
	<td>$11.25</td>
	<td>$17.95</td>
	<td>$26.00</td>
	<td>$55.20</td>
	</tr>
	</table>
	<br/>
		<div class="siteSubHeaderBlue">YOUR YEAR TO DATE (YTD) TOTALS:</div>
<b>YTD Total km (<%=(Calendar.getInstance()).get(Calendar.YEAR)%>):</b> 

  								<% if(usr.getPersonnel().getYearToDateKilometerUsage()<9000) { %>
                                	  <%=df.format(usr.getPersonnel().getYearToDateKilometerUsage())%>  kms
                                 <%  } else { %>
                                	  <span style="color:Red;">
                                	  <%=df.format(usr.getPersonnel().getYearToDateKilometerUsage())%>  kms  (Set to $<%=rates.get(0).getBaseRate() %> base rate for over 9000km)
                                	  </span>
                                <%  }  %>

<br/>
<b>YTD Total Claimed $ (<%=(Calendar.getInstance()).get(Calendar.YEAR)%>):</b> <%=dollar_f.format(usr.getPersonnel().getCurrentYearClaimTotal())%> <br/>


<!-- <b>FYTD Total km (<%=Utils.getCurrentSchoolYear()%>):</b> 
  <%=df.format(usr.getPersonnel().getYearToDateKilometerUsageFiscalYear())%>  kms 
                              
<br/>
<b>FYTD Total Claimed $ (<%=Utils.getCurrentSchoolYear()%>):</b> <%=dollar_f.format(usr.getPersonnel().getYearToDateClaimTotal())%>
	
-->

	<br/>
	<div class="siteSubHeaderBlue">ENTERING KILOMETERS:</div>
	 When calculating distance and there are multiple route options to and from your destination, you must use the route with the lowest distance when calculating your claim. 
	 You can, however, take any route to and from your destination. To help in your distance calculation, we have a 
	 <a href="#" class="mclaims" onclick="loadingData();loadMainDivPage('calculatedistance.jsp');return false;">Distance Calculator</a> to provide up to three possible route options based
	on Google Maps. The lowest value kms from the results <i>(or any confirmed written distance reference documentation)</i> should	be used. If necessary, the final correct distance may be adjusted
	by the District before final payment is approved.
</div>	
<script>
		$(function() {
			var test = $("#ccnt").val();
			if (test > 0) {
				$("#claimNoticeMessage").html("<span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span><b>NOTICE:</b> You currently have <span style='color:Red;font-size:14px;font-weight:bold;'>"+ test+ "</span> claim(s) awaiting your approval. <br/>Click on the <b>Supervisor</b> menu above to review the(se) outstanding claim(s).<br/>If you are NOT the claiments supervisor for a listed claim, please REJECT it back.");
			$("#claimNoticeMessage").css("display","block");
			} else {
				$("#claimNoticeMessage").css("display","none");				
			}
		});
		
		
		
</script>	

</div>


</body>
</html>