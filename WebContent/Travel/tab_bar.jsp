<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
         com.awsd.personnel.*,
                 com.awsd.travel.*,
                 com.awsd.common.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"
        %>

<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="TRAVEL-EXPENSE-VIEW" />

<%
  User usr = null;
  String tab = "";
  int id = -1;
  int cur_status;
  TravelClaim claim = null;
  boolean isAdmin = false;


 
    
  usr = (User) session.getAttribute("usr");
  
 
  
  tab = request.getParameter("tab");
  id = Integer.parseInt(request.getParameter("id"));
  cur_status = Integer.parseInt(request.getParameter("status"));
  
  claim = TravelClaimDB.getClaim(id);
  String claimtitle="";
  if(claim instanceof PDTravelClaim){
	  claimtitle="PD - " + ((PDTravelClaim)claim).getPD().getTitle().replace("'", "&#8217;").replace("\"","");
  }else{
	  claimtitle=Utils.getMonthString(claim.getFiscalMonth()) + " " +  Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear());
  }
  isAdmin = usr.getUserRoles().containsKey("ADMINISTRATOR");
  
 String RealName = "";
 
 RealName = claim.getPersonnel().getFullNameReverse().replace("'", "&#8217;");
%>


		<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">  	
   		<link href="includes/css/travel.css" rel="stylesheet" type="text/css"> 		
   		<script src="includes/js/travel.js"></script>
	
    <table id="tab_bar" width="100%">
    <tr>
     <td class="tab_end" width="100%" height="30" valign="bottom" align="right">
 			<%if((cur_status == TravelClaimStatus.PRE_SUBMISSION.getID())||
            (cur_status == TravelClaimStatus.REJECTED.getID())){%>
            <%if(request.getParameter("hasItems").equalsIgnoreCase("TRUE")){%>
              <img style="padding-right:5px;padding-bottom:2px;" src="includes/img/submit-off.png" class="img-swap" title="Submit this claim for processing." onclick="openModalDialog('<%=id%>','submitclaim','<%=claimtitle%>');">
            <%}%>
            <img style="padding-right:5px;padding-bottom:2px;" src="includes/img/delete-off.png" class="img-swap" title="Delete this claim." onclick="openModalDialog('<%=id%>','deleteclaim','<%=claimtitle%>');">
          <%}else if(usr.getUserPermissions().containsKey("TRAVEL-CLAIM-SUPERVISOR-VIEW")
            && (claim.getSupervisor().getPersonnelID() == usr.getPersonnel().getPersonnelID())
            &&((cur_status == TravelClaimStatus.SUBMITTED.getID()) 
            || (cur_status == TravelClaimStatus.REVIEWED.getID()))){%>
            <img style="padding-right:5px;padding-bottom:2px;" src="includes/img/approve-off.png" class="img-swap" title="Approve this claim." onclick="openModalDialog('<%=id%>','supervisorapprove','<%=claimtitle%>,<%=RealName%>');">
            <img style="padding-right:5px;padding-bottom:2px;" src="includes/img/decline-off.png" class="img-swap" title="Decline this claim." onclick="openModalDialog('<%=id%>','supervisordecline','<%=claimtitle%>,<%=RealName%>');">
            <%}else if(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW")
            &&((cur_status == TravelClaimStatus.APPROVED.getID()) 
            || (cur_status == TravelClaimStatus.PAYMENT_PENDING.getID()))){%>
            <img style="padding-right:5px;padding-bottom:2px;" src="includes/img/pay-off.png" title="Pay this claim." class="img-swap" onclick="openModalDialog('<%=id%>','paytravelclaim','<%=claimtitle%>,<%=RealName%>');">
            <img style="padding-right:5px;padding-bottom:2px;" src="includes/img/pending-off.png" title="Payment pending." class="img-swap" onclick="openModalDialog('<%=id%>','paypendingtravelclaim','<%=claimtitle%>,<%=RealName%>');">
          <%}else{%>
            &nbsp;
          <%}%>
          
          <%if(isAdmin && claim.getPersonnel().getPersonnelID() != usr.getPersonnel().getPersonnelID()){%>
          		<img style="padding-right:10px;padding-bottom:2px;" src="includes/img/delete-off.png" title="Delete this claim." class="img-swap" onclick="openModalDialog('<%=id%>','deleteclaim','<%=claimtitle%>');">
          <%}%> 
          
          <%out.println("<BR>"); %>
        </td>
    </tr>
    
   
    
    
    
      <tr valign="bottom">
		<td>
			<ul class="nav nav-tabs" id="myTab">
			<%if(tab.equalsIgnoreCase("") || tab.equalsIgnoreCase("DETAILS") ){%>
  				<li class="active" style="background-color:#ffffe8;"><a href="#" onclick="loadMainDivPage('viewTravelClaimDetails.html?id=<%=id%>&tab=DETAILS');" data-toggle="tab">Details</a></li>
  				<li><a href="#" onclick="loadMainDivPage('viewTravelClaimHistory.html?id=<%=id%>&tab=HISTORY');" data-toggle="tab">History</a></li>
  				<li><a href="#" onclick="loadMainDivPage('viewTravelClaimNotes.html?id=<%=id%>&tab=NOTES');" data-toggle="tab">Notes</a></li>
			<%}else if (tab.equalsIgnoreCase("HISTORY")){%>
			  	<li><a href="#" onclick="loadMainDivPage('viewTravelClaimDetails.html?id=<%=id%>&tab=DETAILS');" data-toggle="tab">Details</a></li>
  				<li class="active" style="background-color:#ffffe8;"><a href="#" onclick="loadMainDivPage('viewTravelClaimHistory.html?id=<%=id%>&tab=HISTORY');" data-toggle="tab">History</a></li>
  				<li><a href="#" onclick="loadMainDivPage('viewTravelClaimNotes.html?id=<%=id%>&tab=NOTES');" data-toggle="tab">Notes</a></li>
			<%}else if (tab.equalsIgnoreCase("NOTES")){%>
			  	<li><a href="#" onclick="loadMainDivPage('viewTravelClaimDetails.html?id=<%=id%>&tab=DETAILS');" data-toggle="tab">Details</a></li>
  				<li><a href="#" onclick="loadMainDivPage('viewTravelClaimHistory.html?id=<%=id%>&tab=HISTORY');" data-toggle="tab">History</a></li>
  				<li class="active" style="background-color:#ffffe8;"><a href="#" onclick="loadMainDivPage('viewTravelClaimNotes.html?id=<%=id%>&tab=NOTES');" data-toggle="tab">Notes</a></li>
			<%}%>
			</ul>
		</td>
       
      </tr>
    </table>
	
