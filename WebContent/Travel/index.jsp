<%@ page language="java"
         session="true"
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
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>

<%
  User usr = null;

  usr = (User) session.getAttribute("usr");
    
  TreeMap pending_approval = null;
  Iterator iter = null;
  int c_cnt = 0;
  if(usr.getUserPermissions().containsKey("TRAVEL-CLAIM-SUPERVISOR-VIEW")){
	    pending_approval = usr.getPersonnel().getTravelClaimsPendingApproval();
		if((pending_approval != null)&& (pending_approval.size() > 0)) {
			//count pending approval claims
			iter = pending_approval.entrySet().iterator();
	        while(iter.hasNext()){
	          c_cnt += ((Vector)((Map.Entry)iter.next()).getValue()).size();
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
  DecimalFormat dollar_f =  null;
  TravelBudget budget = null; 
  
   
  claims = usr.getPersonnel().getTravelClaims();
  budget = usr.getPersonnel().getCurrrentTravelBudget();

  
  df = new DecimalFormat("#,##0");
  dollar_f = new DecimalFormat("$#,##0");




 %>

<html>

	<head>
		<title>NLESD - Travel Claim System</title>					

		  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
		    <meta charset="utf-8">
		    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">    		
    		<link href="includes/css/jquery-ui.css" rel="stylesheet" type="text/css"> 
    		<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">  	
   			<link href="includes/css/travel.css" rel="stylesheet" type="text/css">
   			<!-- For mini-icons in menu -->
   			<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
			<script src="includes/js/jquery.min.js"></script>			
    		
    		<script src="includes/js/jquery-ui.js"></script>
    		<script src="includes/js/bootstrap.min.js"></script>
    		<script src="includes/js/travel.js"></script>   
			<script src="includes/js/jquery.maskedinput.min.js"></script>
			<script type="text/javascript" src="includes/js/jquery.timepicker.js"></script>  
					
	</head>

  <body>
  
 
  <div class="mainContainer">
  	   	<div class="section group">	   		
	   		<div class="col full_block topper" align="center">
	   			<script src="includes/js/date.js"></script>	   		
			</div>			
			<div class="full_block center">
			<esd:SecurityAccessRequired permissions="TRAVEL-CLAIM-ADMIN,TRAVEL-CLAIM-SEARCH,TRAVEL-EXPENSE-VIEW-REPORTS,TRAVEL-EXPENSE-SDS-EXPORT">
			<div style="float:left;padding-left:5px;color:Silver;">Logged in as: <span style="text-transform:capitalize;"><%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%></span></div><p>
			
			<div id="divsearch" style="float:right;margin-right:5px;">
								<input type="radio" name="searchtype" value="NAME" checked> Name
								<input type="radio" name="searchtype" value="VENDOR"> Vendor #
								<input type="text" name="srch_txt" id="srch_txt" style="width:150px;" placeholder="Enter Search Term(s)" value="" onfocus="this.select();">								
                				<input type="button" value="GO" onclick="searchclaims();">
					</div>
		
			</esd:SecurityAccessRequired>
				<img src="includes/img/header.png" alt="" width="90%" border="0"><br/>	
				<div align="left"><jsp:include page="includes/menu.jsp" /></div>			
			</div>
			<div class="col full_block content">
				<div class="bodyText">
					<div class="alert alert-danger" style="display:none;" id="mainalert" align="center">
  						<span id="errormessage"></span>
					</div>
					
	                 <div class="alert alert-info"  align="center" style="display:none;">
  						Welcome to our new travel claim system. Please limit submissions and approvals until Wednesday, June 7, 2017 while we undergo testing and resolve minor issues with the new system.
					</div>
	
						<div id="printJob">
						
							<div id="pageContentBody" style="width:100%;">
								<div class="claimFundsInfoBox">	
								<table>
								<tr>
								<td class="claimFundsInfoBoxHTitle1">Name: </td><td><%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%></td>
								</tr>
								<tr>
								<td class="claimFundsInfoBoxHTitle1">Position:&nbsp;</td><td><%=usr.getPersonnel().getPersonnelCategory().getPersonnelCategoryName()%></td>								
								</tr>
								<tr>
								<td class="claimFundsInfoBoxHTitle1">School: </td><td><%=(usr.getPersonnel().getSchool() != null ? usr.getPersonnel().getSchool().getSchoolName() : "NO SCHOOL")%>								
								</td>															
								</tr>								
								</table>				
								
								<img src="includes/img/bar.jpg" height="1" width="100%" style="margin-top:5px;margin-bottom:3px;"/>
								<table>
								<tr>
								<td class="claimFundsInfoBoxHTitle2">Kilometer Usage (<%=(Calendar.getInstance()).get(Calendar.YEAR)%>):&nbsp;</td><td><%=df.format(usr.getPersonnel().getYearToDateKilometerUsage())%> kms</td>
								</tr>
								<tr>
								<td class="claimFundsInfoBoxHTitle2">Total Claimed (<%=Utils.getCurrentSchoolYear()%>):&nbsp;</td><td><span style="color:#FF0000;"><%=dollar_f.format(usr.getPersonnel().getYearToDateClaimTotal())%></span></td>
																
								</tr>
								</table>
		               			<%if(budget != null){ %>
						                <img src="includes/img/bar.jpg" height="1" width="100%" style="margin-top:5px;margin-bottom:3px;"/>
						                <table>
										<tr>
										<td class="claimFundsInfoBoxHTitle3">Against Budget (<%=Utils.getCurrentSchoolYear()%>):&nbsp;</td><td class="claimFundsInfoBoxInfo"></td>
										</tr>
										<tr>
										<td class="claimFundsInfoBoxHTitle3">Pre-submission (<%=Utils.getCurrentSchoolYear()%>):&nbsp;</td><td class="claimFundsInfoBoxInfo"></td>
										</tr>
										<tr>
										<td class="claimFundsInfoBoxHTitle3">Budget (<%=Utils.getCurrentSchoolYear()%>):&nbsp;</td><td class="claimFundsInfoBoxInfo"></td>
										</tr>
										<tr>
										<td class="claimFundsInfoBoxHTitle3">Avail. Funds (<%=Utils.getCurrentSchoolYear()%>):&nbsp;</td><td class="claimFundsInfoBoxInfo"></td>
										</tr>
										</table>					
									
				                <%}%>	
				                 <img src="includes/img/bar.jpg" height="1" width="100%" style="margin-top:5px;margin-bottom:3px;"/>
				                <div align=center style="margin-top:10px;margin-bottom:5px;">
				               <a href="#" class="mclaims" onclick="loadMainDivPage('addTravelClaim.html');"><img src="includes/img/startclaim-off.png" title="Start a New Claim" border=0 class="img-swap"></a>&nbsp;<a href='#'  class="mclaims" onclick="loadMainDivPage('myProfile.html');"><img src="includes/img/myprofile-off.png" border=0 title="View and/or Edit your Profile and Current Claims" class="img-swap"></a>
				               </div>
				                <img src="includes/img/bar.jpg" height="1" width="100%" style="margin-top:5px;margin-bottom:3px;"/>
				              	<br/><div align=center style="padding-bottom:2px;color:#1F4279;"><b>EMPLOYEE TRAVEL POLICY (FIN-401)</b></div>
				              	&nbsp; &middot; <a href="/includes/files/policies/doc/1504103153912.pdf">Travel Policy</a><br/>
				               &nbsp; &middot; <a href="/includes/files/policies/doc/1504103153928.pdf">Policy Regulations</a><br/>
				               &nbsp; &middot; <a href="/includes/files/policies/doc/1505137604356.pdf">Memo to Employees (Sept 2017)</a><br/>
				               &nbsp; &middot; <a href="/includes/files/policies/doc/1505137549028.pdf">Insurance Reimbursement Form</a>
				               </div>
	                			
							Welcome <%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%> to the NLESD Travel Claim system. 
							<br/><br/>Please select a previous claim from the MY CLAIMS &amp; PROFILE menu above (or link at right) to review or continue working on, 
							or click the <b>FILE</b> menu to start a <a href="#" onclick="loadMainDivPage('addTravelClaim.html');">NEW CLAIM</a> or use the link at right. 
							If you have already started a claim for this month, it will be listed under  MY CLAIMS &amp; PROFILE. <br/><br/>
							<span style="color:Red;font-weight:bold;">NOTE: You cannot start a second Monthly Claim for the same month in the current school year.</span><br/><br/>
							Always make sure your information in <a href='#' onclick="loadMainDivPage('myProfile.html');">your profile</a> is up-to-date with your correct mailing address and contact information.
							
							<br/><br/><b>KILOMETERS:</b> When calculating distance and there are multiple route options to and from your destination, you must use the route with the lowest distance when calculating your claim. 
							You can, however, take any route to and from your destination.	To help in your distance calculation, we have a <a href="#" class="mclaims" onclick="closeMenu();loadMainDivPage('calculatedistance.jsp');">Distance Calculator</a> to provide up to three possible route options based on Google Maps. 
							The lowest value kms from the results <i>(or any confirmed written distance reference documentation)</i> should be used. If necessary, the final correct distance may be adjusted by the District before final payment is approved. 
							
	 
				              
							
							
							</div>		
								
						<br/>&nbsp;<br/> 
							<input type="hidden" id="ccnt" name="ccnt" value="<%=c_cnt%>">							
						
						</div>
						<div style="clear:both;"></div>
						<div class="alert alert-warning"><b>NOTE:</b> This is a travel claim system ONLY. All other expenditures MUST be handled through the SDS Purchasing System or through school accounts. Where other expenditures are included, claim processing may be delayed. 
						</div>
						
						<div class="alert alert-info">
<b>SUPPORT:</b> If you are have any questions regarding a travel claim entry or payments, please contact Travel Claim Support <a href="mailto:GoldieGillingham@nlesd.ca?Travel Claim Support">Goldie Gillingham</a> at (709) 757-4623 or <a href="mailto:SherryMiller@nlesd.ca?Travel Claim Support">Sherry Miller</a> at (709) 758-2402. 
For technical assistance, please email <a href="mailto:mssupport@nlesd.ca?subject=Travel Claim Technical Support Request">mssupport@nlesd.ca</a><p>
</div>
						
									
						
						
				</div>
			</div>
		</div>
		

		<div class="section group">
			<div class="col full_block copyright">NLTravelApp 2.9 &copy; 2018 Newfoundland and Labrador English School District</div>
		</div>
</div>

        	<script>
				$('document').ready(function(){
					var test = $("#ccnt").val();
					if(test > 0){
						$("#errormessage").html("<b>NOTICE:</b> You currently have <span style='color:Red;font-size:14px;font-weight:bold;'>" + test + "</span> claim(s) awaiting your approval. Click on the <b>Supervisor</b> menu above to review.<br/>If you are NOT the claiments supervisor for a listed claim, please REJECT it back.");
						$("#mainalert").show();
					}});
		</script>
		
		<br/>&nbsp;<br/>		
  <!-- ENABLE PRINT FORMATTING -->
	<script src="includes/js/jQuery.print.js"></script>	
  </body>

</html>	