<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 com.awsd.travel.*,
                 com.awsd.travel.bean.*,
                 com.awsd.common.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"
        isThreadSafe="false"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
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
	    payment_pending = usr.getPersonnel().getTravelClaimsPaymentPending();	   
  }
  else
  {   
    payment_pending = null;  
  }

%>

    	<script>
    		$( document ).ready(function() {
			  getpendingtravelclaimsbyletter("All","All");		  
			  
			    $.cookie('backurl', 'claimsPaymentPendingLetter.html', {expires: 1 });
    		});    		
		</script>
<style>
input { border:1px solid silver;}
</style>  

<c:set var="now" value="<%=new java.util.Date()%>" />
 <fmt:formatDate pattern="yyyy" value="${now}" var="thisYear" /> 		


	<img class="pageHeaderGraphic" src="/MemberServices/Travel/includes/img/pending_stamp.png" style="max-width:200px;" border=0/> 
	<div class="siteHeaderBlue">Travel Claims Payment Pending</div>
	Below is a list of travel claims that are currently pending payment sorted by year, name. To sort by a different column, simply click on the column header or use the search.
	<br/><br/>

	            
			<table id="claims-table" class="table table-condensed table-striped table-bordered claimsTable" style="font-size:11px;background-color:White;" width="100%">	
				<thead>
					<tr style="text-transform:uppercase;font-weight:bold;">  	
						<th width="15%">Employee</th>
						<th width="10%">Type</th>
						<th width="30%">Title/Month</th>						
						<th width="10%">Year</th>						
						<th width="15%">Supervisor</th>
						<th width="10%">Region</th>
						<th width="*" >Function</th>
					</tr>
				</thead>
				<tbody>

				</tbody>
			</table>
